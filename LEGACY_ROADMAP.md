# AD-NO-BS Legacy — roadmap and behavior requirements

## Latest integrated candidate — Build #236 — 2026-11-14

- [GitHub Actions #236](https://github.com/Xinees/AD-NO-BS/actions/runs/43639067236) passed all steps: immutable source-JAR integrity, bytecode pruning, resource migration, retained controller/renderer audits, framework override checks, the Lushsaur saddle-capability gate, and final JAR structure/content checks. It published [draft release #236](https://github.com/Xinees/AD-NO-BS/releases/tag/untagged-f5c91031847f1a7fef48).
- Output: `archie_dinosaurs-legacy-forge-1.20.1.jar`, 468 entries, 2,488,497 bytes. [Download Build #236](https://github.com/Xinees/AD-NO-BS/releases/download/untagged-f5c91031847f1a7fef48/archie_dinosaurs-legacy-forge-1.20.1.jar).
- **Localization repaired:** the retained English and Russian language files now contain actual display names for the creative tab, all three spawn eggs, retained items, and both eggs. CI asserts that every required key exists in both locales, is nonempty, and is not equal to the raw translation key.
- **Lushsaur saddle path corrected:** Build #232's Lushsaur-only `mobInteract` guard did not cover the shared parent saddle branch that actually consumes the item and plays the equip sound. Build #236 preserves the original Lushsaur `mobInteract` method and adds a capability gate inside `AbstractComplexMount.mobInteract`: after confirming the held item is a saddle, it checks `Saddleable.isSaddleable()` before entering the shared code that calls `setSaddled` and plays the sound. Lushsaur reports `false`, so this branch is skipped without consuming the saddle or playing the saddle sound. Other mounts keep their existing behavior. The existing Lushsaur-only `isSaddleable=false`, `isSaddled=false`, forced-empty saddle state, and slot-0 empty-only predicate remain.
- The final JAR is still fully migrated to `archie_dinosaurs`; Build #236 verifies the four retained entity IDs and 468-entry archive. The original source JAR remains immutable with SHA-256 `8df20e08d46f778a6e975b60b0cc7e19ff674264834812015537d7ef0fb121b1`.
- **Build207 vs Build212 speed finding:** the Lushsaur max-speed patch was unchanged between those source revisions. No arbitrary speed constant was added; the reported in-game speed difference remains unverified at runtime.
- **Runtime status:** Build #236 passed bytecode and archive checks, but no Minecraft launch was performed in this session. The saddle rejection, sound/inventory behavior, creative-tab/item translations, startup, entity spawning, rider alignment, and movement speed still need in-game acceptance testing. CI checks do not prove the interaction behaves correctly in a running game.

---

## Runtime regression found after Build119 and root-cause correction — 2026-11-14

### Follow-up after the first in-game screenshot — 2026-11-14

- **Lushsaur passenger hitbox and rider model are corrected separately.** `MountComponent.positionRider()` now uses `passengerOffset(0, 0, 0.0625)` instead of `(0, 0, 1)`, moving the passenger entity/hitbox 15 pixels back toward the body center. The only used unsaddled visual `MountPoint` is now Z = `+0.15`, which moves the rendered model 3 pixels forward relative to the previous screenshot. The unused saddled visual point remains original. CI #212 verifies both exact values; visual acceptance still requires an in-game check.
- Chorusoatl's original lateral flight banking through `flyingRoll` has been restored. The latest successful roll-preservation test is [CI #189](https://github.com/Xinees/AD-NO-BS/actions/runs/43615959523).
- Lushsaur and Sculksaur still have only the `RollOnRotation` root operation removed. Their other root operations, including `SmoothBodyRotation` and `GRUMM_ROTATION`, are retained; the GRUMM array index is adjusted after removing the roll entry.
- The occasional 180-degree turns and erratic head tracking were traced to separate Build99 patches, not to removal of `RollOnRotation`. Those patches had bypassed `SmoothMoveControl.smoothRotlerp`, replaced `SmoothBodyControl.m_8121_()` with direct body-yaw assignment, and made the horizontal coordinates in `SmoothLookControl.m_8128_()` update immediately.
- Reverted all three Build99 controller patches in implementation commit [`a57cc2d`](https://github.com/Xinees/AD-NO-BS/commit/a57cc2dd202a7e72fd8d37cb934b9957a9b93be7). The original controller classes are now retained from the source JAR: shortest-angle yaw interpolation (maximum step 45 degrees per tick), original body/head stabilization, and original XYZ look-target interpolation.
- The `RollOnRotation` removal remains a renderer-only change for Lushsaur and Sculksaur; Chorusoatl's original `flyingRoll` flight banking remains intact. No new custom turn-rate value was added. The existing original turn is already bounded; decide whether it needs further tuning only after observing the restored behavior in-game.
- Unified controller/roll baseline: [CI #196](https://github.com/Xinees/AD-NO-BS/actions/runs/43618034281) passed all checks, verified 468 JAR entries, and preserved non-roll root operations.
- Earlier visual/egg baseline: [CI #207](https://github.com/Xinees/AD-NO-BS/actions/runs/43620810294), superseded by #212.
- **Previous passenger/egg baseline (superseded by Build #221):** [CI #212](https://github.com/Xinees/AD-NO-BS/actions/runs/43626888055) passed every step. Implementation commit [`1073c87`](https://github.com/Xinees/AD-NO-BS/commit/1073c87069440ff9a80c47d44a37ad369187b902) centers the Lushsaur passenger hitbox, moves the rendered model 3 px forward, and blocks Lushsaur saddle support; workflow guard commit [`3b5b93a`](https://github.com/Xinees/AD-NO-BS/commit/3b5b93af3abc27f86434399469198a0784dd7bcf) verifies those requirements. Existing resource/egg commit `d234cb8` supplies the six Chorusoatl egg substrates. Output: 468 entries, 2,480,913 bytes, SHA-256 `2a0cb70ebde25b38c62694ad30f4d6ccbf42a25d0b600da035f0281a9478f02c`, in [draft release #212](https://github.com/Xinees/AD-NO-BS/releases/tag/untagged-656f417caba722492ba3).

---

Astemir's first Minecraft runtime launch of Build119 reached the integrated world, then failed while constructing retained mount entities. The supplied launch log identifies `java.lang.VerifyError: Inconsistent stackmap frames at branch target 163` in `MountComponent.tickRidden`. The same class failure affected both Lushsaur and Chorusoatl creation. The chunk-read message was a consequence of this class-verification failure, not evidence of a corrupt world.

**Root cause:** the earlier `patchRiderLookSteering` inserted an idle-input early return plus hand-authored `FrameNode` entries around the look/pitch logic. The pruner writes classes with `ClassWriter.COMPUTE_MAXS`, not `COMPUTE_FRAMES`, so existing StackMap frames were retained rather than recomputed for the new control-flow paths. The injected pitch/rotation path reached the old body-rotation branch with local slots 5 and 6 as `TOP`, while the retained frame at bytecode offset 163 declared both as `float`. The JVM correctly rejected the class.

**Correction:** implementation commit [`dd18eec`](https://github.com/Xinees/AD-NO-BS/commit/dd18eec88d390fe8958e434a6aeb75632008d3d8) removes the early return and all manually injected frames from `tickRidden`. Horizontal-input gating is now applied branchlessly to the existing `canMountRotateHead` and `canMountRotateBody` results, for Lushsaur and Chorusoatl only. Pitch is neutralized with a branchless multiplier for those two mounts. Other mounts retain the original capability results and rider pitch.

The pruner now asserts that this patch does not change the method's original branch count or FrameNode count. The workflow additionally checks the two input gates, pitch multiplier, and the original three branches.

**CI candidate #187:** [run #187](https://github.com/Xinees/AD-NO-BS/actions/runs/43612358618) completed successfully. It verified 468 JAR entries and a final size of 2,480,325 bytes, and uploaded the JAR to the draft release [`legacy-auto-187`](https://github.com/Xinees/AD-NO-BS/releases/tag/untagged-229ce353686cf570f4f4).

**Runtime status:** the stack-map root cause is corrected and the candidate passes the CI bytecode/structure guards. It still requires a fresh in-game launch and riding acceptance test; do not mark it runtime-accepted until that test is reported successful.

---

**Working branch:** `binary-prune/legacy`  
**Target:** Forge 1.20.1  
**Last reviewed:** 2026-11-14

This file is the explicit task list for the next Legacy work. The order below is intentional. Do not quietly reduce these requests to cosmetic tweaks, and do not mix unrelated behavior changes into one patch.

## Current baseline and test status

### Build119 implementation status — rider controls, roll and particle cleanup

**Implementation commit:** `d9ae4f5bac2040ffe7788fd24174becf9f933f8a`  
**Latest CI guard commit:** `c9f46bded6d8900ac96c96e8eb3f48aa771d4d82`  
**CI run #176:** [43608521634](https://github.com/Xinees/AD-NO-BS/actions/runs/43608521634)

The pruner compiled and rebuilt from the untouched original JAR. Renderer descriptor checks, the new riding/particle bytecode guards, and final retained-JAR structure validation all passed. The only failed step was uploading the JAR artifact because the repository's GitHub Actions artifact-storage quota is full.

Runner output: `ec_ad-legacy-forge-1.20.1.jar`, **468 entries**, **2,480,309 bytes**. The runner produced and validated the JAR, but the artifact could not be downloaded because the upload step was rejected by GitHub. No SHA-256 is recorded for this runner copy.

**Runtime status:** Build119 has not yet been launched in Minecraft. Treat all behavior below as structurally/bytecode-verified, not runtime-confirmed.

### Rider pose and Lushsaur seat position

- `ADPlayerAnimations.init()` no longer registers `OnLushsaurPlayerAnimations` or `OnChorusoatlPlayerAnimations`. The player therefore uses the normal vanilla passenger pose; this also removes the custom rider run/action layer while mounted. Dismounted player animations are unaffected.
- Keep the player entity and hitbox centered at the original server passenger point (`passengerOffset(0, 0, 1)`). Move only the rendered player model 15 pixels backward within that hitbox by adding +0.9375 local Z to the **unsaddled client-only `MountPoint`**: unsaddled Z = `+0.3374999761581421`. The unused saddled point remains original and must not receive this offset. Never change server `passengerOffset` to achieve this visual alignment.
- Chorusoatl receives the vanilla-like seated pose but no 13-pixel offset.

### Chorusoatl egg hatching

- Original `canHatch()` first checks the `chorusoatl_egg_preferred` dimension tag. It contains `minecraft:the_end`; in that dimension the egg immediately passes `canHatch` without checking substrate, light, or nearby chorus blocks.
- Outside the preferred End dimension, the egg requires block light = 0 and sky light = 0; the block directly below it must be in `chorusoatl_egg_hatchable`; and at least five blocks within a 7×7×7 cube (radius 3) must be in `chorusoatl_egg_chorus`.
- The original hatchable-block tag contained only `minecraft:end_stone`. The retained resource tag is extended to include `minecraft:end_stone_bricks`, `minecraft:purpur_block`, `minecraft:purpur_pillar`, `minecraft:obsidian`, and `minecraft:bedrock` as well. `minecraft:end_stone` remains.
- The nearby chorus requirement is unchanged: at least five `minecraft:chorus_plant` or `minecraft:chorus_flower` blocks in that radius. The preferred End dimension still bypasses these substrate/light/chorus requirements as in the original source.
- CI #207 validates all six substrate entries and preserves `minecraft:the_end` in the preferred-dimension tag.

## Build #212 — Lushsaur saddle removal and passenger alignment

**Implementation and CI status: complete. Runtime acceptance: pending.**

- Lushsaur now rejects saddle interaction before the shared superclass interaction can tame/equip/play the saddle path.
- `Saddleable.isSaddleable()` (`m_6741_`) returns false for Lushsaur; `isSaddled()` (`m_6254_`) always returns false.
- Lushsaur's `setSaddled(boolean)` override forces the superclass saddle slot empty. Build #221 replaces the original broad equipment filter with a slot-specific predicate: slot 0 accepts only an empty stack, empty-slot clearing remains valid, and other slots retain their original predicate. These restrictions are Lushsaur-only; the shared `AbstractComplexMount` implementation and other mounts remain unchanged.
- The player entity/hitbox is moved 15 pixels toward the center through the Lushsaur `MountComponent` passenger offset: `(0, 0, 0.0625)`.
- The unsaddled rendered player model is moved 3 pixels forward relative to the previous screenshot: its client `MountPoint` Z is `+0.15`. The unused saddled mount point is unchanged.
- Chorusoatl egg hatchability includes End Stone, End Stone Bricks, Purpur Block, Purpur Pillar, Obsidian and Bedrock. Existing dark-place and nearby-chorus conditions outside the preferred End dimension remain unchanged.

### In-game acceptance tests

1. Try applying a saddle to an adult owned Lushsaur. It must reject the interaction, not become saddled, and not consume the saddle.
2. Ride and control the Lushsaur without a saddle. Confirm the passenger hitbox is centered on the body and the rendered model is 3 pixels forward relative to the previous screenshot. Check movement, sprint, yaw steering and E inventory.
3. Confirm Chorusoatl and any other unaffected mounts retain their original saddle behavior.
4. Place Chorusoatl eggs on all six supported substrates outside the End in full darkness and with at least five chorus plants/flowers within radius 3; confirm hatching. Confirm the End's preferred-dimension behavior remains unchanged.
5. Confirm the remaining rider pose, flight roll, non-roll root operations, original walking/head controllers, and unrelated attacks behave as before.

**CI:** [run #212](https://github.com/Xinees/AD-NO-BS/actions/runs/43626888055) passed all structural and bytecode checks. **Do not mark the visual/gameplay behavior runtime-accepted until Astemir tests this JAR in Minecraft.**

---

### Lushsaur inventory and E key

- Lushsaur's custom mount-menu opener `m_213583_(Player)` is now a no-op, so it cannot open the Lushsaur container screen.
- The client-side `LocalPlayer.sendOpenInventory()` hook detects Lushsaur as the current vehicle and opens the player's normal `InventoryScreen`; it cancels the custom riding-inventory request.
- Chorusoatl and Sculksaur inventory behavior is unchanged.
- The superclass's internal saddle/equipment slot remains as an internal compatibility detail; it is no longer exposed through a Lushsaur mount GUI. This avoids deleting shared equipment/control paths unrelated to the requested screen removal.

### Lateral roll removal

- LushsaurClient and SculksaurClient no longer register their `RollOnRotation` root operation. The remaining root operations are preserved and the array/Grumm rotation index is adjusted.
- Chorusoatl's original `flyingRoll` animation path is preserved. The accidental zero-substitution was removed; CI #189 confirms the original `flyingRoll` field read and `ChasingFloat.get()` call remain.
- Horizontal yaw, head tracking, vertical pitch, gait and run animations are preserved by the bytecode patch. In-game visual confirmation is still required.

### Movement-gated look steering

- In `MountComponent.tickRidden`, Lushsaur and Chorusoatl stop applying rider yaw to head/body while both movement inputs (`xxa` and `zza`) are zero.
- When forward/back/strafe input is active, the original head/body yaw-follow logic remains active; rider pitch is neutralized for these two mounts.
- The condition is based on input, not actual displacement, so a mount pressed against a wall still follows the horizontal look direction.
- Sculksaur is not changed by this steering gate.

### Particle cleanup

- Lushsaur's sleep `REST_EFFECTS` no longer spawns `EMITTER_REST` at the `head` locator; its original rest sound remains.
- Lushsaur's `RUN_EFFECTS` charge-trail callback (`lambda$static$3`) is disabled. This is the source that emitted the ground3 and moss charge-trail particles.
- Lushsaur's original `stepEvent()` contained sound and camera shake but no particle call; it did not need a footstep-particle patch.
- Chorusoatl's two `EMITTER_STOMP` particle events were removed from `WALK_EFFECTS`; light/heavy footstep sounds remain.
- Sculksaur's footstep callback now retains `SCULKSAUR_STEP` sound and `ShakeUtil.shakeStep`, but emits no block/ordinary particles.
- **Sculksaur explosive-attack particles remain completely absent.** Do not recreate them until Astemir supplies the original particle formula/implementation.

### Preserved Build118 behavior and limits

- Tail physics remains disabled as in Build118.
- Sculksaur's attack, camera shake and non-footstep event tracks remain as in Build118; no substitute explosion-particle system was added.
- Lushsaur's separate item-chain physics and Sculksaur leg IK remain.
- The original input JAR and external Astemir RE 1.2.98 JAR were not modified.
- A temporary read-only inspection workflow was used for bytecode analysis and has been deleted. The main `binary-prune.yml` workflow remains the production build.

### Required in-game acceptance test

1. Ride Lushsaur and Chorusoatl; verify the player simply sits like on a vanilla pig and no custom arm/action animations play.
2. Ride Lushsaur unsaddled only; verify the passenger hitbox is centered after the 15-pixel server offset correction and the rendered model is 3 pixels forward relative to the previous screenshot. Verify the saddle restriction and E inventory behavior.
3. Move both mounts while rotating the camera; verify body and head follow horizontal yaw only while movement input is held. Stop moving and rotate the camera; verify the mount keeps its heading. Test while blocked against a wall.
4. Test running/turning on all three mobs; verify lateral roll is gone while ordinary gait, yaw, head movement and pitch remain.
5. Verify no footstep dust on any retained mob, no Lushsaur sleep Z, and no Lushsaur green charge trail. Verify footstep sounds and Sculksaur step camera feedback remain.
6. Place Chorusoatl eggs on each supported substrate (End Stone, End Stone Bricks, Purpur Block, Purpur Pillar, Obsidian, Bedrock) outside the End in darkness, with at least five nearby chorus plants/flowers; confirm hatching. In the End, confirm preferred-dimension behavior remains unchanged.
7. Confirm unrelated attacks, taming, inventory/equipment behavior on Chorusoatl, and Sculksaur non-footstep particle tracks.

---

### Build 118 — previous implementation; superseded by Build119

- Implementation commit: [`1f39f51`](https://github.com/Xinees/AD-NO-BS/commit/1f39f518cdc12d2e9016be4f1dee19a423b3de7c). CI guard commit: [`57641de`](https://github.com/Xinees/AD-NO-BS/commit/57641de2df0b562dbc1023c7ec8b2ca41d2a256d).
- GitHub Actions [run #152](https://github.com/Xinees/AD-NO-BS/actions/runs/43594713011) passed compilation, pruning, retained renderer descriptors, bytecode guards, and final 473-entry JAR validation. Artifact upload alone failed because GitHub Actions storage quota was full. Runner output: 473 entries, 2,492,713 bytes.
- Local JAR: `ec_ad-legacy-forge-1.20.1-build118.jar`; size 2,492,895 bytes; SHA-256 `d0208dbdd64f2e3ac7b79f980c1ba40796471893d42cfe6603d3db0f73895f96`. The local JAR was produced by applying the final bytecode changes to the verified Build117 output and repacking; its ZIP bytes are not claimed identical to the runner output.
- Compared with Build117, only `ADClientModBus.class`, `LushsaurClient.class`, `SculksaurClient.class`, and `SculksaurEffects.class` change; the two generated Sonic Boom particle classes are removed; all remaining resource entries are byte-identical.
- Sculksaur explosion particles are fully disconnected: the final event now performs only the original camera shake, the ground callback does nothing, the delayed second-pulse event is removed, and `SculksaurSonicBoomParticle` plus its provider are absent. The vanilla SONIC_BOOM provider is restored. The original 4.68s explosion/camera event and 5.12s tendril/vibration event remain; other Sculksaur particle tracks are unchanged.
- Tail physics is disabled on Lushsaur's adult chain (`tail1`, `tail2`, `mase`) and baby chain (`tail`, `tail2`), and on Sculksaur's `tail1`/`tail2` chain. Lushsaur's separate `string`/`beetroot` physics and Sculksaur's leg IK are preserved. Chorusoatl's renderer has no PhysicsModule tail chain.
- Attack damage, range, knockback, targeting, AI, cooldown, sound, and unrelated mob behavior are unchanged.
- Local verification: ZIP integrity, 473-entry count, expected class removals/changes, no explosion particle classes/helpers, no tail bone names in the two setupPhysics methods, preservation of Lushsaur item-chain bones and Sculksaur leg IK. Resources remain byte-identical to Build117.
- **Runtime status:** Build118 has not been launched in Minecraft. Test the tail pose/animation and other visual regressions in-game.

### Build 117 — previous implementation; superseded by Build118



- Speed/animation implementation: [`240b410`](https://github.com/Xinees/AD-NO-BS/commit/240b410263ee03d2e42b406b704ddd14e917f4ab); corrected frame indexing: [`a342122`](https://github.com/Xinees/AD-NO-BS/commit/a3421223430d58aa6b42baec9c2267e394ce4d7f); 20-block lifetime adjustment: [`51f9d4e`](https://github.com/Xinees/AD-NO-BS/commit/51f9d4ecf5fe6a14c3be20115db7567c773ae208).
- GitHub Actions [run #146](https://github.com/Xinees/AD-NO-BS/actions/runs/43592112280) passed compilation, pruning, bytecode checks, and retained-JAR validation. Artifact upload alone failed because GitHub Actions storage quota was full. Runner output: 475 entries, 2,495,933 bytes.
- Local Build117 JAR: `ec_ad-legacy-forge-1.20.1-build117.jar`; 2,496,082 bytes; SHA-256 `3df15538caf48cce83b056e74d1dceca5b2bb676f5c1560bc610d8da1449e628`.
- The local archive has 475 entries. Compared with Build116, only `SculksaurEffects.class` and `SculksaurSonicBoomParticle.class` differ; all other classes and resources are unchanged.
- Both hollow vanilla `SONIC_BOOM` waves use the same saved XYZ origin. The first starts at `4.68s`; the second at `4.88s`, exactly `0.20s` / 4 ticks later.
- Each sphere contains **256 Fibonacci-distributed particles** (512 total). Radial speed is `0.6` blocks/tick = **12.00 blocks/second**.
- The moving particle lifetime is explicitly **34 ticks**. At 12 blocks/second this gives **20.4 blocks** of nominal travel, reaching 20 blocks after approximately 33⅓ ticks.
- The vanilla `SONIC_BOOM` uses a 16-frame sprite sequence. Its animation clock loops `0..15` while real particle age and lifetime continue monotonically: before calling the vanilla tick, temporary age is set to `realAge % 16 - 1` to compensate for vanilla incrementing age before choosing the sprite. Afterwards, true age/lifetime are restored, then radial movement is applied once.
- The first wave travels about `2.4` blocks when the second starts. Original first-wave camera shake and tendril/vibration remain. Damage, attack range, knockback, AI, cooldown, sound, and unrelated mob behavior are unchanged.
- CI run #146 verifies the 4-tick delay, 12-block/second velocity, 256 points per sphere, explicit 34-tick lifetime, 16-frame loop with corrected age indexing, same saved origin, callback isolation, and JAR structure.
- Local validation: ZIP integrity passed; `javap` parsed both changed classes and StackMapTable; JVM `-Xverify:all` loaded the particle class against isolated Minecraft API stubs; a synthetic tick test confirmed that 16 frames repeat for 34 ticks while the particle moves 20.4 blocks. This is bytecode/lifecycle validation, not a Minecraft runtime test.
- **Runtime status:** not yet launched in Minecraft. Confirm actual ~20-block travel, same-origin appearance, 4-tick stagger, looping animation, reduced density, and regressions in-game.

### Build 116 — previous implementation; superseded by Build117

- Build116 used a 5-tick delay, 10.00-block/second radial speed, 256 particles per sphere, and 40-tick lifetime. Build117 supersedes it with a 4-tick delay, 12.00-block/second speed, 34-tick range-matched lifetime, and an independently looping 16-frame animation.
### Build 115 — previous implementation; superseded by Build116

- Code and workflow commit: [`1227531`](https://github.com/Xinees/AD-NO-BS/commit/122753133976e08be4cdbf166b080d6724200978).
- GitHub Actions [run #141](https://github.com/Xinees/AD-NO-BS/actions/runs/43589059252) passed compilation, pruning, class/resource checks, and all Sculksaur bytecode guards. Artifact upload failed solely because GitHub Actions storage quota was full. The runner's generated JAR was 475 entries and 2,495,873 bytes.
- A local JAR was separately materialized from Build114's verified output by applying the exact Build115 changes to the two affected classes. It has 475 entries; only `SculksaurEffects.class` and `SculksaurSonicBoomParticle.class` differ. All resources and all other classes are unchanged.
- Local JAR size: `2,495,900` bytes; SHA-256: `f758b1b218c94563332dc4c7a68d46d5ca051ac77eef5481452a4afbfa43dc0c`.
- Both hollow spherical waves use the same saved XYZ origin. The first starts at `4.68s`; the second at `4.83s`, exactly `0.15s` / 3 ticks later.
- Each sphere contains **512 Fibonacci-distributed vanilla `SONIC_BOOM` particles** (1,024 total). Radial velocity is `0.33` blocks/tick = **6.60 blocks/second** at 20 TPS.
- The custom particle constructor explicitly calls `Particle.setLifetime(40)`: 40 ticks / 2 seconds, giving a nominal radial path of **13.2 blocks** at the configured speed. Actual visibility and travel still require an in-game test.
- The first wave has travelled about `0.99` blocks when the second starts. At 13.2 blocks radius, the mean Fibonacci-neighbor spacing is about `2.07` blocks for 512 points per sphere.
- The original first-wave camera shake and tendril/vibration track remain. The effect is visual-only; damage, attack range, knockback, AI, cooldown, sound, and unrelated mob behavior are unchanged.
- **Runtime status:** not yet launched in Minecraft. In-game verification remains required for actual travel, sphere visibility/density, timing, and regressions.

### Build 114 — previous build; superseded by Build115

- Latest CI: [run #139 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43539402377).
- Final implementation commit: [`bbfc8b57`](https://github.com/Xinees/AD-NO-BS/commit/bbfc8b573e9953341515e394ab71ce95f1520a8d).
- Artifact ID: `14091057287`; ZIP digest: `sha256:73cf3445b65e0fb8f028dbb501df50ef8a3682fa15359c946e7b12ded60a7c05`.
- Output JAR: `ec_ad-legacy-forge-1.20.1.jar`; size: `2,495,858` bytes; SHA-256: `1e5d6351b7dccef1224380def5e3364f52adad0ad525ff8ce8c33bc9a1453743`.
- Build114 now creates **two sequential hollow spherical pulses from one and the same XYZ origin**. The first event remains at `4.68s`; the second event is scheduled at `5.18s`, exactly `0.50s` (10 ticks at 20 TPS) later. This is not a pair of spatially offset shells.
- The first event reads the Sculksaur's XYZ position and half-height-adjusted Y once, saves those exact coordinates in a `WeakHashMap`, and creates the first sphere there. The second event consumes and removes that saved origin and creates the trailing sphere at those same coordinates; it does not re-read the entity position after the delay.
- Each sphere contains exactly 128 Fibonacci-distributed vanilla `SONIC_BOOM` particles: 256 total per attack. Each particle moves along its unit radial direction at `0.315` blocks/tick = **6.30 blocks/second**. The vanilla 16-tick lifetime means about 5.04 blocks of travel per pulse.
- The first wave has travelled 3.15 blocks when the second spawns. Each wave lasts 0.80s; the complete two-pulse sequence ends 1.30s after the first spawn. The average Fibonacci neighbor spacing at the 5.04-block radius is approximately 1.49 blocks, close to the vanilla 1.5-block particle quad size. These are calculated values; the visual density still needs confirmation in Minecraft.
- The original first-wave camera shake remains and is not replayed by the second pulse. The original final event time (`4.68s`) and the tendril/vibration event (`5.12s`) remain. The additional second sphere event is at `5.18s`.
- The original `SONIC_BOOM` particle type, vanilla sprite-set provider, full-bright rendering, quad size, and age animation remain. The custom subclass adds only radial movement for particles emitted by this Sculksaur effect; unmarked provider calls continue to use vanilla `SonicBoomParticle.Provider`.
- A real ASM cloning hazard was found and fixed: the cloned second factory originally shared the first factory's `invokedynamic` bootstrap-argument array. Build114 clones that argument array before changing the second callback. The pruner verifies the first factory still targets `lambda$explosionFinalEvent$12`; the second targets only `archiesDinoMod$lambdaSecondSonicBoomEvent`.
- No attack AI, target selection, original event time, damage, range, knockback, cooldown, attack action, sound, or unrelated Sculksaur animation/particle track was changed. The second sphere is a visual-only event.
- Build113-to-Build114 archive comparison: 475 entries before and after; no entries added or removed; exactly one changed class, `SculksaurEffects.class`. No resource, model, texture, sound, or JSON entry changed.
- CI run #136 verifies the exact particle type/provider, 128 positive stream iterations per sphere (`SIPUSH 128`), 10-tick delay, saved/reused origin, callbacks, equal radial velocity components, camera-shake behavior, ZIP integrity, retained content, and existing mob controls/renderers. The downloaded JAR was independently inspected with `javap` and compared entry-by-entry with Build113.
- The final guard-only CI run #139 also checks the `javap -v` `BootstrapMethods` table: the first and second event factories must use different bootstrap indices and point to their respective callbacks. Its downloaded JAR has the same SHA-256 as the independently inspected Build114 archive.
- **Runtime status:** CI and downloaded-JAR checks passed. Build114 has **not** yet been launched in Minecraft. In-game confirmation is still required for the visible shell shape, same-origin appearance, three-tick delay, motion, density at roughly thirteen blocks, and attack/unrelated-mob regression checks.

### Build 113 — superseded sequential-pulse precursor

- Build113 restored the exact vanilla `SONIC_BOOM` particle type and added a narrowly scoped moving subclass/provider, but it created two shells simultaneously at separate initial radii. That was not the required effect and Build114 supersedes it.
- Build113 used 768 particles per shell (1,536 total) at 0.32 blocks/tick. These settings are superseded by Build114's 128 particles per pulse, same origin, 0.50-second stagger, and 0.315 blocks/tick speed.

### Build 112 — rejected particle substitution

- Build112 passed its earlier CI, but it used vanilla `SCULK_SOUL` instead of the explicitly requested `SONIC_BOOM`. Its CI checked for the wrong particle type, so that success did not satisfy the task. Build112/123 are not accepted as the Sonic Boom visual fix.

### Build 111 — previous baseline

- CI: [run #116 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43528408869); workflow/guard commit: `8a22d56e01f1bc9b5d08db522ec87fccfef97e50`.
- Code commits: [`127bb60d`](https://github.com/Xinees/AD-NO-BS/commit/127bb60d49cae787e26b8e3feaa594ea4b86174d), [`669bc901`](https://github.com/Xinees/AD-NO-BS/commit/669bc901d48893c3b7f81056b1f7a720ac72283a).
- Artifact ID: `14085958100`; ZIP digest: `sha256:cc0bf23229b7cbc931f70035227a29b42e5933d039fd3f2f411376bb375e9f7b`.
- JAR size: `2,493,447` bytes; SHA-256: `5b01626519d9b02934f932c58d5ffe8fd9210851ba3630725f2347726a03c456`.
- Build110 runtime feedback: Lushsaur and turning are confirmed working; Sculksaur sometimes pitched its head unnaturally toward the ground.
- Build111 fixes the over-snapped vertical look: horizontal yaw stays instant, while `smoothY` is interpolated with the existing `interpolationSpeed` and the original `maxSpeedLimit` is restored for pitch.
- Build111's Sonic Boom shell used the correct `SONIC_BOOM` type, but its particles were static because vanilla SonicBoomParticle ignores XYZ velocity. Build113 and Build114 correct that limitation without changing the particle type.
- Build110-to-Build111 comparison: 473 entries in both JARs, no added/removed entries, and only `SculksaurEffects.class` plus `SmoothLookControl.class` changed.
- **Runtime status:** Build111 passed CI and post-download archive/bytecode inspection only. It still needed an in-game check for Sculksaur's spawn/idle gaze and the shape/speed/spacing of Sonic Boom.

### Mob turning and head tracking — original controllers restored

The Build99/Build103 direct-turning rewrite is superseded and has been removed from the active pruner in commit `a57cc2d`.

- `SmoothMoveControl`: original shortest-angle `smoothRotlerp` is restored for grounded walking, climbing, and aerial yaw. The original helper clamps each yaw step to 45 degrees per tick. Original vertical/pitch handling is also retained.
- `SmoothBodyControl`: the entire original body/head stabilization algorithm is retained, including action rotation gates, the Player-passenger lock, head-stability timing, and smooth body/head recovery.
- `SmoothLookControl`: the original interpolation of `smoothX`, `smoothY`, and `smoothZ` is retained, along with its original yaw/pitch limits and rotation guards.
- The removal of `RollOnRotation` is separate and renderer-only; it does not replace or disable these AI controls.
- **Open audit:** Sculksaur's original `createLocomotion()` also calls `EntityLocomotion.Builder.turnFactor(0.003f)`. The implementation belongs to the external Astemir RE dependency. The exact Astemir RE 1.2.98 JAR is not present in this workspace, so its precise effect remains unverified. Do not alter this value without inspecting that exact dependency.
- No new custom turning speed has been added. First test the restored original motion. If further smoothing is still required, it should be a narrowly scoped, shortest-angle rate cap rather than a rewrite of all three controllers.

**CI status:** [run #195](https://github.com/Xinees/AD-NO-BS/actions/runs/43617699213) passes controller-preservation and structural checks. **Runtime status:** still requires in-game acceptance testing for idle/wander/chase/target-switch turns, head tracking, action locks, all three mobs, and the Sculksaur `turnFactor` question.

### Previous completed visual change — Build 94



Build 94 removed the Sculksaur Sonic Boom post-process distortion and old attack-wave emitters, replacing them with a vanilla `SCULK_SOUL` sphere while preserving the camera shake and the existing gameplay attack. Its CI run #94 succeeded. The effect has not yet received visual in-game confirmation.

### Previous sprint baseline — Build 81

Build 81 added the Lushsaur-only vanilla mounted-sprint permission path in `LocalPlayerMixin` and changed the mounted movement-speed callback to read the controlling passenger's sprint state while retaining the feeding speed boost. Build 99 preserves that behavior and corrects the mounted run-animation predicates.

### Build 78 — user-reported historical test

The player mounted the Lushsaur and held W. The Lushsaur began with its walking animation; after roughly three seconds the running animation appeared, but actual movement speed did not increase. Build 78 was not correct. The user's newer report confirms that sprint input is now accepted through Ctrl and double-tap W; Build 99 contains the animation synchronization change, which still requires runtime confirmation.

## Priority 1 — replace the Sculksaur Sonic Boom visual effect

**Build117 implements the requested same-origin pulses with a 4-tick delay, 12.00-block/second speed, 256 particles per sphere, a 34-tick lifetime, and a looping 16-frame SONIC_BOOM animation. CI bytecode checks pass; in-game acceptance is still pending.**

### Required result

- Two hollow expanding spheres, both spawning at the exact same attack origin.
- The first sphere appears first. The second follows exactly 4 game ticks (0.20 seconds) later; the spacing between wavefronts must be caused by the delay, not different initial radii.
- Each sphere expands evenly in every direction.
- Use the actual vanilla `SONIC_BOOM` particle type, not `SCULK_SOUL`.
- The particle count is reduced to keep the spherical outline readable at a nominal range of 20 blocks. Avoid filling the volume or creating a dense cloud.
- Remove the other attack-specific particle bursts/emitters from this event. Keep the original camera shake on the first event.
- Do not use Astemir's shader/post-process shockwave, colored-light effect, screen-space distortion, or a replacement custom 3D distortion.

### What the vanilla particle requires

In Minecraft 1.20.1, `SONIC_BOOM` is rendered by `SonicBoomParticle`, which extends `HugeExplosionParticle`. Its provider interprets the first extra scalar as the vanilla particle size and does not apply the supplied XYZ velocity components. Passing a radial XYZ vector to `Level.addParticle` cannot make the exact vanilla Sonic Boom particle travel.

Build117 retains the exact `SONIC_BOOM` type, full-bright rendering and 1.5-block quad size; its vanilla 16-frame sprite animation now loops for the full particle lifetime. A narrowly scoped subclass adds radial movement only for particles emitted by the Sculksaur event. Unmarked calls still delegate to the vanilla provider.

### Build117 implementation

- The first event remains at `4.68s`. It reads the Sculksaur's X/Z and half-height-adjusted Y once, stores those coordinates by entity in a `WeakHashMap`, and creates the first sphere at that point.
- A separate second event starts at `4.88s`: a delay of exactly `0.20s` or 4 game ticks at 20 TPS. It consumes and removes the saved XYZ origin and creates the second sphere at the exact same point. It does not read the entity position again, so mob movement during the delay cannot shift the second origin.
- Each sphere contains **256 particles**, for **512 total** across the two pulses. Directions use a Fibonacci-sphere distribution.
- Every particle receives radial velocity with magnitude `0.6` blocks/tick = **12.00 blocks/second**. The custom particle lifetime is explicitly set to **34 ticks**; the nominal path length is **20.4 blocks** for each pulse.
- At the moment the second pulse starts, the first wavefront has travelled about 2.4 blocks. Each pulse lasts 1.7s; the entire two-pulse sequence ends 1.9s after the first spawn.
- At a 20.4-block radius, 256 Fibonacci-distributed particles have an estimated average neighbor spacing of about 4.52 blocks. This avoids returning to the earlier 768-particle-per-wave configuration while increasing point density for the larger radius. This is a geometry estimate only; check actual visibility in-game.
- The original camera shake `ShakeUtil.shakeExplosion(entity, 1.0f, 20.0f)` remains on the first event. The second visual pulse does not replay it. The original tendril/vibration event at `5.12s` remains unchanged.
- **Callback isolation:** the second AnimationEvent factory is cloned from the original, but its ASM `invokedynamic` bootstrap-argument array is cloned before changing the callback handle. The original factory must still target `lambda$explosionFinalEvent$12`; the second factory targets only `archiesDinoMod$lambdaSecondSonicBoomEvent`. The pruner checks this to prevent the first event from accidentally being redirected to the second callback.
- No attack AI, target selection, original event time, damage, range, knockback, cooldown, attack action, sound, or unrelated Sculksaur animation/particle track was changed. The second event is visual-only.
- Build116-to-Build117 archive comparison: 475 entries in each archive; no additions/removals; only `SculksaurEffects.class` and `SculksaurSonicBoomParticle.class` changed. No resource, model, texture, sound, or JSON entry changed.
- CI run #146 verifies exact `SONIC_BOOM` registration/provider behavior, 256 positive iterations per wave (`SIPUSH 256`), common saved origin, 4-tick timing, 12-block/second radial velocity, 34-tick particle lifetime, 16-frame sprite loop with corrected age indexing, callback separation, camera shake, retained JAR structure, and existing controls/renderers. The locally materialized Build115 JAR was independently inspected with `javap` and compared entry-by-entry. Bytecode tests cannot prove the visual result in Minecraft.

### Preserve the gameplay attack

This request is about the wave's visuals. Keep the existing Sonic Boom gameplay behavior unless a separate bug is found and reported first:

- target selection and attack conditions;
- timing and cooldown;
- damage, range, knockback, and vertical impulse;
- attack action/animation and sound;
- Sculksaur AI and vibration/listening behavior.

Do not remove or rewrite the entire `SculksaurEffects` system. Keep unrelated idle, walk, run, tail, roar, listen, spawn, and death effects.

**Camera shake decision:** `ShakeUtil.shakeExplosion(...)` is separate from the shader `Shockwave` object. Build 94 preserves it; only the post-process distortion and attack-specific emitter spawns are removed.

### Acceptance tests

1. Trigger Sonic Boom with post-processing enabled and disabled. The visual result must be the same vanilla-particle sphere in both cases.
2. Confirm there is no screen-space warp, colored shader light, or custom 3D shockwave.
3. Confirm the sphere is hollow and expands outward evenly.
4. Confirm no old explosion emitter burst accompanies it.
5. Confirm all unrelated Sculksaur particle effects still work.
6. Confirm attack damage, knockback, range, sound, cooldown, and timing match the pre-change behavior.

## Priority 2 — natural, bounded turning and stable head tracking

**Current implementation:** original `SmoothMoveControl`, `SmoothBodyControl`, and `SmoothLookControl` behavior has been restored. Build99/Build103 direct-turning patches are no longer part of the active pruner. The latest unified candidate is [CI #195](https://github.com/Xinees/AD-NO-BS/actions/runs/43617699213).

The requirement is no longer to force instant turns. Sudden 180-degree snaps should be avoided while preserving responsive movement and the original body/head stabilization. The original `smoothRotlerp` already uses shortest-angle yaw interpolation with a maximum 45-degree step per tick. Do not replace it with an arbitrary, slower interpolation before testing this restored baseline.

This applies to all three retained mobs:

- Lushsaur (Пышнозавр)
- Chorusoatl
- Sculksaur

The original classes use the custom `SmoothMoveControl`, `SmoothBodyControl`, and `SmoothLookControl`. Their original algorithms are now retained. The no-roll visual patch is separate and must not modify these controls.

### Current controller state

- `SmoothMoveControl`: original `smoothRotlerp` calls restored for 3D movement, climbing, and walking; maximum yaw step is 45 degrees per tick.
- `SmoothBodyControl`: original head-stability timer, smooth body rotation, smooth return of head toward the front, action lock, and Player-passenger exception restored.
- `SmoothLookControl`: original interpolation for X/Y/Z look target and original yaw/pitch speed limits restored.

### Unresolved Sculksaur locomotion parameter

The original Sculksaur has a separate `EntityLocomotion.Builder.turnFactor(0.003f)` call in `createLocomotion()`. This call does not appear in Lushsaur or Chorusoatl's corresponding builders. The implementation of `turnFactor` lives inside the separate Astemir RE JAR, which is intentionally not modified or bundled and is not available in the current workspace. The parameter's precise effect cannot be proved from the call site alone.

**Do not remove the call, change its value, or treat it as harmless without checking the exact Astemir RE 1.2.98 JAR.** The change is intentionally left untouched in Build 99. Once the dependency is available for read-only inspection, determine whether `turnFactor` applies any additional turn interpolation or controls a separate locomotion property. Only then decide whether it belongs in this task.


### Required result

- When a mob needs to face a new direction, its body turns decisively rather than slowly easing/lerping around.
- Remove the smooth body/head turning behavior from all three retained mobs.
- Do not leave a second smoothing path behind in locomotion/turn-factor code.
- Preserve intentional rotation locks during attacks, death, emergence, burial, sitting, or other locked actions where the existing behavior explicitly prevents turning.
- Do not change animation playback, IK, tail motion, neck motion, movement speed, AI target choice, or attack behavior as part of this task.

### Acceptance tests

Test each of the three mobs while idle, wandering, chasing a target, and changing target direction sharply. Also test Lushsaur while ridden. They should turn promptly without jitter, snapping back and forth, or breaking their attack/animation lockouts.

## Priority 3 — Lushsaur-only physical platform collision

Add the physical collision/platform behavior **to the Lushsaur only**. The intended feel is similar to standing on a Happy Ghast: an entity can stand on the dinosaur's upper surface and remain supported while it moves, without having to mount it.

The requested behavior is more than merely preventing two entity hitboxes from overlapping:

- A player can stand on top of the Lushsaur without falling through it.
- The player can walk around and move relative to the Lushsaur while remaining on its moving surface.
- The player is not forcibly mounted or locked to a passenger seat.
- The collision must remain stable while the Lushsaur moves and turns.
- Chorusoatl and Sculksaur must not receive this platform behavior.

### Proposed collision gate supplied by the user

```java
@Override
public boolean canBeCollidedWith() {
    return SPIN.get(this);
}
```

Treat this as the user's intended Lushsaur-only collision gate: collision is enabled according to the Lushsaur's `SPIN` state. Preserve that intent unless a source-level audit proves the gate is incompatible with the required standing/platform behavior. Do not copy the override into the other mobs.

### Important implementation distinction

`canBeCollidedWith()` alone must not be assumed to provide the full Happy-Ghast-like platform behavior. Audit entity collision eligibility, pushability, the Lushsaur's collision AABB, top-surface collision resolution, and how a supported player inherits the moving platform's displacement. Do not confuse entity-to-entity collision with collision against world blocks.

### Acceptance tests

1. Stand on the Lushsaur without riding it.
2. Confirm the player does not fall through the body.
3. Walk forward, backward, sideways, and around on its upper surface.
4. Move and turn the Lushsaur while the player remains supported and can still move relative to it.
5. Toggle the `SPIN` condition and verify the intended collision gate.
6. Confirm normal entity pushing/pathfinding does not become broken and that no equivalent platform collision appears on Chorusoatl or Sculksaur.

## Standing scope rules

- Keep the original mod JAR and Astemir RE JAR immutable.
- Continue building by pruning the original JAR; do not replace retained original systems with newly invented behavior without a demonstrated need.
- Keep the Legacy scope limited to Lushsaur, Chorusoatl, Sculksaur, and their required content.
- Every task must be isolated, bytecode/resource-verified, and followed by an in-game regression test.
- A successful GitHub Actions run proves structural checks passed; it does not prove an in-game behavior is correct.
- If an additional bug or risky side effect is discovered, report it clearly and explain the consequence. Do not quietly fix unrelated behavior without approval.
