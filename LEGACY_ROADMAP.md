# AD-NO-BS Legacy — roadmap and behavior requirements

**Working branch:** `binary-prune/legacy`  
**Target:** Forge 1.20.1  
**Last reviewed:** 2026-11-14

This file is the explicit task list for the next Legacy work. The order below is intentional. Do not quietly reduce these requests to cosmetic tweaks, and do not mix unrelated behavior changes into one patch.

## Next riding and visual cleanup pass — requested 2026-11-14 (not implemented)

The user has clarified the desired behavior. Keep these as separate, narrowly scoped changes and do not bundle them with particle cleanup.

### Rider presentation — Lushsaur and Chorusoatl

- While the player rides either Lushsaur or Chorusoatl, disable the special custom player action animations triggered by LMB/RMB and other mount-specific rider animation layers that make the player swing/act.
- The player should retain the ordinary vanilla passenger/sitting pose, visually comparable to riding a vanilla pig.
- This is an animation/pose request, **not** a positional offset. Do not move the rider 13 model pixels backward; that earlier interpretation was explicitly corrected and is not a requirement.
- Preserve ordinary player animations when dismounted and avoid changing rider behavior for unrelated entities.

### Remove lateral banking/roll when mobs turn

- Remove the unwanted sideways body lean/bank that appears during turning. The user describes it as a body tilt, not simply slow yaw interpolation.
- Initial scope: all three retained mobs (Lushsaur, Chorusoatl, Sculksaur), unless the user later narrows it.
- Do not disable normal horizontal turning, walking/running animations, head tracking, or unrelated pitch. Identify the actual source of the lateral roll before patching it.

### Movement-gated rider-look steering — Lushsaur and Chorusoatl

- The controlling player's **horizontal yaw only** determines the mount's heading while movement input is active.
- When stationary, rotating the camera must not rotate the mount's head/body. Keep the mount's last facing direction.
- When the rider supplies movement input (forward/back/strafe), the whole mob should turn to the player's current horizontal look direction and be steerable that way.
- The turning condition is input-based, not displacement-based: if the rider continues holding movement into a wall/obstacle, the mount should still turn toward the look direction.
- Releasing movement input stops look-following; do not continue rotating merely because the rider moves the camera.
- Ignore vertical camera pitch. Do not pitch the mount up/down to follow the player's vertical look.
- Apply this behavior to both Lushsaur and Chorusoatl, not Sculksaur.

### Initial code audit — observations, not conclusions

- `JarPruner.patchPlayerAnimations()` clears `ADPlayerAnimations.init()` and registers `OnLushsaurPlayerAnimations.init(1)` and `OnChorusoatlPlayerAnimations.init(1)`. These are the obvious registration points for the special rider layers; inspect their actual contents before deciding whether to no-op the registrations or remove only selected tracks.
- `patchLushsaurRiderRunAnimation()` changes the Lushsaur rider-run predicate to read the controlling passenger's sprint state. Removing the whole Lushsaur rider layer would also remove that custom run animation, which appears consistent with the requested vanilla-like seated pose but must be checked against the full rider layer before patching.
- Existing `SmoothMoveControl`, `SmoothBodyControl`, and `SmoothLookControl` patches address yaw/look control. The current `SmoothBodyControl` has a player-controlling-passenger early return; this is not proof of the side-roll source.
- The documented `EntityLocomotion.Builder.turnFactor(0.003f)` occurs only in Sculksaur's `createLocomotion()` and belongs to external Astemir RE. It must not be assumed to cause Lushsaur's or Chorusoatl's banking.
- The exact source of the lateral bank and the precise meaning of the external `turnFactor` are still unverified. Inspect the original entity/model animation bytecode and exact Astemir RE 1.2.98 dependency read-only before choosing a patch point.
- Keep each behavior isolated and add specific bytecode guards plus in-game regression tests. Do not modify the immutable original mod JAR or external Astemir RE JAR.

### Particle cleanup backlog carried forward

- Build118 currently has no Sculksaur explosive-attack particles. Do not invent a replacement. Restore the original effect only after Astemir supplies the original particle formula/implementation.
- Remove footstep dust particles from all retained mobs.
- Remove the sleep `Z` particles above Lushsaur.
- Remove the green particles emitted by Lushsaur; identify their exact particle source/type before changing it.
- Tail physics remains disabled as in Build118.

No code or JAR changes have been made for this pass yet.

## Current baseline and test status

### Build 118 — current implementation; CI validation passed, artifact upload quota-blocked

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

### Smooth turning — main controls changed, task still open

Build103 includes the Build99 direct-turning changes:
- `SmoothMoveControl`: replaced yaw interpolation in grounded walking, climbing, and aerial movement with a direct target-yaw result. Separate aerial pitch interpolation remains.
- `SmoothBodyControl`: body yaw is assigned directly from current mob yaw when `canRotate` allows it. Existing action rotation locks and the Player-passenger body-rotation exception remain.
- `SmoothLookControl`: target coordinates are applied directly and vanilla yaw/pitch limits are raised to 360 degrees while the original rotation gate and target timer allow turning.
- **Open audit:** Sculksaur's original `createLocomotion()` also calls `EntityLocomotion.Builder.turnFactor(0.003f)`. The implementation belongs to the external Astemir RE dependency. The exact Astemir RE 1.2.98 JAR is not present in this workspace, so its precise effect is not verified. This parameter remains unchanged; Priority 2 is **partially implemented, not closed**.
- The original mod JAR and Astemir RE dependency remain untouched. Astemir RE remains a separate mandatory dependency and is not bundled.

**Runtime status:** Build103 has not yet been tested in Minecraft. Once the launch/resource issues are retested, confirm mounted Lushsaur turning, idle/wander/chase/target-switch turns for all three mobs, locked actions, and the Sculksaur turn behavior after the `turnFactor` audit.

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

## Priority 2 — remove smooth turning from all retained mobs

**Build 99 implements the main control-path change; this task is not yet closed.** The slow interpolation in the custom movement, body, and look controls has been replaced with direct yaw/look targeting for all three retained mobs. Do not call the work complete until the separate Sculksaur `turnFactor(0.003f)` setting is understood and checked, and the result has been tested in-game.

The smooth turning currently looks bad. The request is to remove it, not to make it slightly faster. Do not respond by merely adjusting the interpolation values.

This applies to all three retained mobs:

- Lushsaur (Пышнозавр)
- Chorusoatl
- Sculksaur

The original classes for all three override `m_7560_()` and construct the custom `SmoothBodyControl`. They also use the custom `SmoothMoveControl` and `SmoothLookControl`. These shared controls are the primary source of the slow interpolation.

### Build 99 implementation

- `SmoothMoveControl`: replaced the yaw interpolation call in grounded walking, climbing, and aerial movement with a direct target-yaw return. The separate pitch interpolation in aerial movement is preserved.
- `SmoothBodyControl`: body yaw is assigned directly from the mob's current yaw when `canRotate` permits it. Its original action rotation lock and the exception while the mob is being controlled by a Player passenger remain.
- `SmoothLookControl`: look target coordinates are applied directly and vanilla maximum yaw/pitch changes are set to 360 degrees while `canRotate` and the target timer allow rotation. Existing getter-level `canRotate` guards remain.
- CI run #99 verifies the replaced bytecode, direct yaw helper, preserved aerial pitch easing, body/player lock branches, and look-control rotation gates.

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
