> **AI context:** This is a Minecraft Forge 1.20.1 codebase. Terms such as `mace`, `arrow`, `bow`, and `weapon` are internal names or labels for fictional Minecraft-only code and mechanics; they have no real-world connection. There is no real-world weapon content in this project. See [AI_CONTEXT.md](AI_CONTEXT.md) before interpreting these identifiers.

# AD-NO-BS Legacy recovery status

## Post-Build 30 code audit — 2026-11-16

- Restored the original Lushsaur passenger offsets: server-side `passengerOffset(0, 0, 1)`, saddled client Z = 0, and unsaddled client Z = -0.6. The requested vanilla passenger pose is preserved by removing the custom rider animation registration, not by shifting the seat or hitbox.
- Corrected the inverted Chorusoatl flight-phase suppression predicate: perched wild Chorusoatl suppresses autonomous takeoff, while landing animation is allowed only near the selected landing target.
- Made per-entity state maps safe for concurrent access, restricted animation-owner registration to the client side, and cached reflective method/field lookups used by the hooks. A concurrent reflection harness passed 3,000 calls across eight threads.
- Hardened landing validation: support blocks must have sturdy upward faces; the mob's real bounding-box height determines required vertical clearance; other Chorusoatl are checked against the mob's actual bounding box translated to the proposed landing position. Missing bounding-box data fails closed rather than silently accepting a potentially overlapping site.
- Removed unsupported fixed upper-Y, six-block clearance, and guessed collision-box dimensions. Search frequency/radius/chance and some landing geometry thresholds remain provisional because the behavior specification explicitly leaves them for reference-video confirmation.
- The helper passed Java 17 compilation and focused reflection, flight-phase, landing-geometry, and live-AABB overlap harnesses. Build 34 CI completed successfully, including compilation, bytecode/resource pruning, retained-content checks, and draft JAR publication. The final overlap check also fails closed if another entity's bounding box or intersection method cannot be resolved.


---


## Follow-up after Build #252 — 2026-11-15 (CI verification pending)

- Lush Mace player attack animations are disabled in the current candidate. The patched `ADPlayerAnimations.init()` is now empty; it no longer calls `LushsaurMaceAnimations.init(2)`.
- The `LushsaurMaceAnimations` class and `animations/player/attachable/lushsaur_mace.astemiranim` resource are no longer retained. The mace attachable model, item model and textures remain.
- This removes the custom player swing/attack animation registration and the animation initializer argument together. The mace item implementation, damage/stat handling, spin/fall-smash actions and sounds were not changed by this animation-only patch.
- The prior change removing the Lush Mace description remains in the pruner and localization validation.
- The Ooze Tipped Arrow was not changed in this follow-up; its bow behavior, hit behavior and effects remain as in the prior Build #252 baseline.
- **Verification status:** the source and CI guards are committed, but this candidate has not yet been confirmed by a completed CI run or a Minecraft launch. Build #252 is still the last confirmed successful build.

---

## Latest integrated candidate — CI #252 — 2026-11-14

- [GitHub Actions #252](https://github.com/Xinees/AD-NO-BS/actions/runs/43651963428) completed successfully. It passed source-JAR integrity, class/resource pruning, controller/renderer audits, mount regression checks, the new Lush Mace/Ooze Arrow behavior checks, final 502-entry JAR validation and draft-release publication.
- Current implementation/validation commit: `346b60d10182184fcf23d2c6c4d2c06e5e1282da`. Output: **502 entries**, **2,547,437 bytes**.
- Download: [`archie_dinosaurs-legacy-forge-1.20.1.jar`](https://github.com/Xinees/AD-NO-BS/releases/download/untagged-d029ad29810418ef8e73/archie_dinosaurs-legacy-forge-1.20.1.jar). Draft release: [Legacy CI build #252](https://github.com/Xinees/AD-NO-BS/releases/tag/untagged-d029ad29810418ef8e73).
- **Lush Mace restored:** normal attack, fall-smash scaling, spin attack with 30-second cooldown, original default stats, player attack/sync hooks, attachable model and animation, sound events and tooltip/localization.
- **Ooze Tipped Arrow restored:** original projectile/item, entity registry and renderer, custom damage type, 200-tick blindness/nausea/poison on hit, ×2 damage against ordinary targets and ×4 against sculk-tagged targets. The `pus_arrow_aggro` event is included in the vanilla vibration and Warden-listening tags.
- Exact crafting: 4 Ooze Tipped Arrows from Sculk Toxin + wooden rod tag + feather tag; 1 Lush Mace from Lush Core + 2 Dripstone Blocks. Recipe advancements and creative-tab entries are included. The arrow's original visible name is Sculk Tipped Arrow / Стрела со скалком.
- The provided 1.0.5 JAR was used for original item reference. CI still uses the pinned, untouched 1.0.4 input JAR; source SHA-256 remains `8df20e08d46f778a6e975b60b0cc7e19ff674264834812015537d7ef0fb121b1`. Astemir RE remains a separate mandatory dependency.
- **Runtime status:** CI validates the compiled classes, mixin registrations, item/entity registries, recipes, resources and archive. It does not prove live Minecraft behavior. Test the two recipes, mace model/animation/normal and fall attacks/spin/cooldown/sounds, arrow hit effects/damage/sculk vibration response/pickup, then recheck the previously working three mobs, eggs, ride controls and Lushsaur no-saddle behavior.
---



## Latest runtime regression and corrected candidate — 2026-11-14

- Build119 was launched by Astemir with Minecraft 1.20.1, Forge 47.4.28, Java 17.0.15 and Astemir RE 1.2.98. The log shows a JVM `VerifyError` in `MountComponent.tickRidden` at branch target 163 when Lushsaur/Chorusoatl mount entities were constructed.
- Root cause: the former steering patch added an idle-input early return and hand-authored stack-map frames around the original method's look/pitch logic. The pruner uses `ClassWriter.COMPUTE_MAXS`, so existing frames were retained rather than recomputed for the injected control-flow path; at the old body-rotation target, local slots 5 and 6 were `TOP` while the retained frame expected `float`.
- Corrected by implementation commit [`dd18eec`](https://github.com/Xinees/AD-NO-BS/commit/dd18eec88d390fe8958e434a6aeb75632008d3d8): no early return and no manually added frames in `tickRidden`; branchless horizontal input gates are applied to the original head/body rotation results; rider pitch is zeroed only for Lushsaur and Chorusoatl.
- Added a pruner invariant that the patch must preserve the original branch count and FrameNode count in `tickRidden`. The workflow separately verifies the expected two horizontal-input gates, pitch multiplier, and original three branches.
- Latest successful CI: [run #187](https://github.com/Xinees/AD-NO-BS/actions/runs/43612358618). Output: 468 entries, 2,480,325 bytes. The validated JAR is in [draft release #187](https://github.com/Xinees/AD-NO-BS/releases/tag/untagged-229ce353686cf570f4f4).
- **Runtime status:** corrected candidate is ready for Astemir's in-game retest. The CI result verifies the class/resource transformation and the stack-map-preservation invariants, but it is not a substitute for launching the candidate in Minecraft. Do not mark it final until the world loads and both mounts pass the riding tests.

---

## Follow-up from the first rider screenshot — 2026-11-14

- Removed the mistaken 13-pixel seat displacement completely. The original Lushsaur server `passengerOffset(0, 0, 1)` and client mount-point offsets (saddled Z = 0, unsaddled Z = -0.6) must remain unchanged. The requested vanilla pig-like rider pose is achieved by disabling only the custom rider animation registration.
- Restored Chorusoatl flight banking: the patch that replaced `flyingRoll.get(partialTick)` with zero was deleted. Successful CI [#189](https://github.com/Xinees/AD-NO-BS/actions/runs/43615959523) verifies the original `flyingRoll` field use and `ChasingFloat.get()` call remain.
- Lushsaur and Sculksaur keep the no-`RollOnRotation` model change. That patch only edits the renderer's `onInitialize` root-operation array and preserves `SHAKING`, `SmoothBodyRotation`, and `GRUMM_ROTATION`; it does not rewrite AI or mob yaw/look controls.
- The erratic body/head turning was traced to separate Build99 direct-turning changes in `SmoothMoveControl`, `SmoothBodyControl`, and `SmoothLookControl`, not to roll removal. Those three patches and their helper were deleted in commit [`a57cc2d`](https://github.com/Xinees/AD-NO-BS/commit/a57cc2dd202a7e72fd8d37cb934b9957a9b93be7); the source JAR's bounded shortest-angle yaw, body/head stabilization, and XYZ look interpolation are now retained.
- CI [#191](https://github.com/Xinees/AD-NO-BS/actions/runs/43617393858) failed because of an incorrectly escaped AWK pattern in the new seat-position check, not because of mod bytecode. The check was corrected in workflow commit `9516d40`; CI #192 tests it. The later diagnostic workflow commit is `9256863`.
- No custom turn-rate patch is present. The original `smoothRotlerp` already caps each yaw step at 45 degrees per tick; assess this restored behavior in-game before adding another limit.

---

## Current branch status — 2026-11-14 (Build119)

- Working branch: `binary-prune/legacy`.
- Current implementation commit: [`d9ae4f5`](https://github.com/Xinees/AD-NO-BS/commit/d9ae4f5bac2040ffe7788fd24174becf9f933f8a).
- Latest CI guard commit: [`c9f46bd`](https://github.com/Xinees/AD-NO-BS/commit/c9f46bded6d8900ac96c96e8eb3f48aa771d4d82).
- **Build119** compiled successfully from the immutable original mod JAR. Renderer descriptors, functional bytecode guards for riding/particles, and final retained-JAR checks all passed in [GitHub Actions run #176](https://github.com/Xinees/AD-NO-BS/actions/runs/43608521634).
- Runner output: `ec_ad-legacy-forge-1.20.1.jar`, 468 entries, 2,480,309 bytes. Artifact upload alone failed because GitHub Actions artifact storage quota was full; the runner-produced JAR was validated but is not downloadable as a workflow artifact. No SHA-256 was recorded for this runner copy.
- **Runtime status:** Build119 has not yet been launched in Minecraft. Bytecode/structure verification is not proof of visual/gameplay behavior. Astemir must test the acceptance checklist in `LEGACY_ROADMAP.md`.
- The current riding pass is implemented in `tools/JarPruner.java`: vanilla passenger pose for Lushsaur/Chorusoatl; original Lushsaur server/client passenger coordinates are retained (no 13-pixel displacement); no Lushsaur mount menu and E opens player inventory; lateral root roll removed only from Lushsaur/Sculksaur, while Chorusoatl's original `flyingRoll` flight banking is preserved; movement-input-gated horizontal-yaw steering for Lushsaur/Chorusoatl.
- Lushsaur's internal saddle/equipment slot remains inside the shared mount implementation, but its custom GUI is inaccessible. This preserves equipment/control compatibility without exposing a separate Lushsaur inventory screen.
- Particle cleanup is implemented: Lushsaur sleep Z and green charge-trail particles removed; Chorusoatl walking dust events removed while retaining step sounds; Sculksaur footstep particles removed while retaining sound/camera feedback. Lushsaur's step event had no particle emitter in the source.
- **Sculksaur explosion particles are still entirely absent.** Wait for Astemir's original formula before restoring them; do not invent a replacement.
- Tail physics remains disabled as in Build118. Lushsaur item-chain physics and Sculksaur leg IK are preserved.
- The original mod JAR and external Astemir RE 1.2.98 dependency remain untouched.
- Temporary read-only inspection workflow `.github/workflows/inspect-legacy.yml` has been deleted; the production `binary-prune.yml` workflow remains.
- Build118 is now the previous baseline. Its original implementation/CI details remain documented below for handoff.

---

## Historical notes preserved from 2026-11-13

The following sections are historical project notes. The current build and pending tests are recorded at the top of this file.


The following sections are historical project notes. The current build and pending tests are recorded at the top of this file.

## Last confirmed build at that time

- Last user-tested playable baseline: `EC-AD Legacy Forge 1.20.1 fixed4`, Direct Legacy JAR Prune build 11.
- Latest corrected build: Direct Legacy JAR Prune build 12.
- Build 12 commit: `6e0256b37d1e18cbc71becaf2f484f308e38026c`
- GitHub Actions run: `43484175090` (run number 70, success)
- Artifact ID: `14065973294`
- JAR: `ec_ad-legacy-forge-1.20.1.jar`
- JAR size: `2,487,207` bytes
- JAR SHA-256: `b446563f8fc220bef676d44a6d4f452370af82a8800f5dd524dc4c1feb1434d7`

Build 12 is structurally verified by CI. The user has confirmed build 11 launched, the three mobs worked, and the creative tab was present. Build 12 still needs the same in-game regression tests before its crash fixes are considered runtime-confirmed.

## Goal and retained scope

Produce a Forge 1.20.1 Legacy build from the untouched original Archie's Dinosaurs JAR. Retain only:
- Mobs: Lushsaur, Chorusoatl, Sculksaur
- Items: Lush Core, Chorus Fossil, Sculk Toxin, Lush Mace and Ooze Tipped Arrow
- Required hatchable eggs, spawn eggs, models, textures, animations, sounds, AI, particles, client rendering, mount inventory and riding controls

The three mobs and their ordinary behavior are reported working. Do not alter their behavior, animations, stats, attacks, or balance beyond fixing the broken mounted-attack dispatch and the explicitly requested Lushsaur no-saddle control exception.

## Immutable inputs

- Original mod: `ec_ad-forge-1.20.1-1.0.4.jar`
- Astemir RE: `astemirlib_re-1.20.1-forge-1.2.98-SNAPSHOT.jar`
- Both inputs are immutable. Do not overwrite, patch, repackage, or otherwise modify either input JAR.
- Astemir RE is a separate mandatory dependency in the `mods` folder. Do not embed another copy inside the Legacy JAR.




## Latest deliverable — Direct Legacy JAR Prune build 15

- Implementation commit: `e7384e3692db4a1bcfece9159475efb5d0570642`
- CI guard correction commit: `6cb6a2b2fcfa17a9ecbcd094e356bad1bc49ef51`
- GitHub Actions run: `43489550907` (success)
- Artifact ID: `14069416706`
- Artifact ZIP digest: `sha256:47bd6bb9a1cfe2f999c1cd62b5307ed93f5528d8541d7353e4e3e4ed3376069e`
- Output JAR: `ec_ad-legacy-forge-1.20.1.jar`
- Output JAR size: `2,492,577` bytes
- Output JAR SHA-256: `f965df099e2f0d307d863a5001d1de618b3378fbafc619505f9a5feae008c3de`

Build 15 includes the missing `DinoMenu.m_7648_(Player,int)` quick-move implementation, restores the Forge client tick subscriber `ADClientForgeBus` that dispatches LMB/RMB key mappings, keeps the server-side `MountControlMessage.execute(ServerPlayer)` dispatch and concrete ride-attack handlers, and adds the Lushsaur-only no-saddle controlling-passenger override.

The Lushsaur override is bytecode-verified. When saddled it delegates to `AbstractComplexMount.m_6688_()` unchanged. When unsaddled it returns the first passenger only when the Lushsaur is adult, not sitting, and the passenger is a Player who owns it. The original `MountComponent.travel()` uses `m_6688_()` to read the controlling Player's movement input. No corresponding override is added to Chorusoatl; the shared saddle behavior is unchanged.

CI verifies the quick-move descriptor, client tick handler and lambda, mounted-attack handlers and server dispatch, Lushsaur control guards and superclass saddle path, absence of a Lushsaur override in Chorusoatl, ZIP integrity, retained assets, creative-tab order, and absence of excluded classes. The artifact's ZIP integrity was also checked after download; Astemir RE is not embedded.

**Runtime status:** structurally verified only. The user must test this exact build in-game. In particular: shift-click saddles into the saddle slot for applicable mounts; test LMB/RMB mounted attacks; test riding an adult tamed Lushsaur without a saddle; and confirm Chorusoatl retains its prior saddle requirement. Do not call these runtime-confirmed until the user reports the test results.

## Build 14 change — Lushsaur no-saddle control

- Build 14 implementation commit: `e7384e3692db4a1bcfece9159475efb5d0570642` (initial CI guard needed a shell-check correction; no artifact was published)
- Add a Lushsaur-only override of `AbstractComplexMount.m_6688_()`.
- When saddled, Lushsaur delegates to the original superclass implementation unchanged.
- When unsaddled, Lushsaur returns a controlling passenger only when the first passenger is a Player, the dinosaur is adult, the dinosaur is not sitting, and that Player is its owner. Otherwise it returns null.
- This matches the existing Lushsaur mount predicate; no new mount eligibility, tame progression, movement speed, animation, saddle rendering, or attack behavior is introduced.
- Chorusoatl and Sculksaur keep the original shared saddle-gated control implementation. CI explicitly verifies the override is present only in Lushsaur and that both saddle and owner checks are retained in its bytecode.

## Build 13 findings — quick-move menu and mounted input dispatch

- Build 13 commit: `df9f9dc4bc18fbfe2564088495bcad8e4d56d296`
- GitHub Actions run: `43488701464` (success)
- Added the missing `DinoMenu.m_7648_(Player,int): ItemStack` implementation to the exact callback retention list. This is vanilla's `quickMoveStack` override; shift-clicking a saddle into the mount slot invokes it. CI now verifies the complete method descriptor.
- A bytecode scan of the untouched original JAR found `client/ADClientForgeBus`, an `@Mod.EventBusSubscriber` whose `onClientTick(ClientTickEvent)` method processes the key-action map. That entire class was missing from the previous pruned JAR. Consequently the registered LMB/RMB mappings had no client tick dispatcher and never sent `MountControlMessage`.
- The next build restores `ADClientForgeBus` as a client root and verifies both `onClientTick` and its `lambda$onClientTick$0` callback, plus both concrete mob `onRiderAttack` overrides and the server dispatch call.
- These are infrastructure restorations only; no attack animations, damage values, AI, or mob behavior are being redesigned.

## Build 12 changes and checks

- Preserve four required method implementations by exact owner and descriptor, instead of broadly keeping methods on every class with an external parent. This avoids reopening unrelated class dependencies.
- Restore `ADModifiers$1.modify(...)`, required by Astemir's `Modifier` interface during mounted-entity rendering.
- Restore `DinoMenu.m_6875_(Player)`, required by `AbstractContainerMenu`.
- Restore `DinoScreen.m_7286_(GuiGraphics,float,int,int)`, required by `AbstractContainerScreen`.
- Restore `MountControlMessage.execute(ServerPlayer)`, the server-side handler for LMB/RMB mounted attacks. It dispatches to `Mount.onRiderAttack(...)`.
- Remove `AbstractArrowMixin.<clinit>` alongside the removed rib-bow data accessor. Retain unrelated vanilla-arrow tick and Sculksaur arrow-hit handling.
- Set the creative tab order deterministically:
  1. Lushsaur Spawn Egg
  2. Chorusoatl Spawn Egg
  3. Sculksaur Spawn Egg
  4. Lushsaur Egg
  5. Chorusoatl Egg
  6. Lush Core
  7. Sculk Toxin
  8. Chorus Fossil
- CI verifies all four callback methods, the absence of the obsolete arrow accessor from the mixin, the exact creative-tab order, ZIP integrity, required resources, and removal of excluded entity/weapon classes.

Build 12 final structural counts:
- 230 retained class files
- 345 removed class files
- 469 retained resource/JAR entries
- 848 removed resource entries
- No excluded Archie, Coralosaur, Obsidiceratops, Sculkraptor, or Therimoss entity classes in the output

An intermediate build 11 repair attempt briefly retained `ArchieEntity$ArchieSettings` because its method-retention rule was too broad. That attempt failed CI and was replaced with exact callback retention. Build 12 passes the excluded-class check.

## Crash reports reviewed

The five root TXT files uploaded on `main` were reviewed: `bow.txt`, `lush.txt`, `lush2.txt`, `random.txt`, and `random_num2.txt`.

1. `bow.txt`: firing a normal vanilla bow with no dinosaur nearby crashed with `NoSuchFieldError: archies_Dino_Mod$RIB_BOW_ARROW`. The mixin field was removed but its static initializer still attempted to assign it.
2. `lush.txt` and `random.txt`: rendering a ridden Lushsaur/Chorusoatl crashed with `AbstractMethodError` for `ADModifiers$1.modify(...)`.
3. `lush2.txt`: opening the mount inventory exposed the missing `DinoMenu.m_6875_` and `DinoScreen.m_7286_` overrides.
4. The test note in `random.txt` also reports that mounted LMB/RMB attacks did nothing. The build 11 artifact lacked `MountControlMessage.execute(ServerPlayer)`, so the network message decoded but its server-side attack handler was absent.
5. `random_num2.txt` is an additional gameplay test session; no additional distinct exception was found in the inspected log.

## Lushsaur unsaddled riding and saddle rejection — implemented, runtime test pending

- The Lushsaur-only `m_6688_()` override allows the tamed owner to control an adult Lushsaur while it is unsaddled; non-owners, babies, sitting Lushsaurs, and other mounts do not receive this exception.
- Build #221 rejects saddle interaction before the shared superclass path; the interaction returns `FAIL`, so the saddle is not consumed.
- `isSaddleable()` and `isSaddled()` return false. `setSaddled(true)` delegates to the superclass with false.
- The equipment-slot override filters only Lushsaur slot 0 (the saddle slot), allowing only an empty stack. This preserves slot clearing and leaves any other equipment slot's original predicate untouched.
- The implementation is Lushsaur-specific. The shared `AbstractComplexMount` logic and saddle requirements for Chorusoatl remain unchanged.
- **Runtime acceptance is pending:** in Minecraft, try normal right-click saddle use and inventory/slot insertion paths, confirm the saddle is not consumed or retained, then verify unsaddled owner control still works and other mounts retain their saddle behavior.

## Someday / uncertain idea — separate Ancient Chorusoatl (not near-term)

This is a deliberately deferred design idea, not part of the current repair scope.

The user's older favorite builds had two distinct Chorusoatl mobs:
- Regular Chorusoatl
- Ancient Chorusoatl

They were separate entity types, not baby/adult variants of one entity. The intended historical behavior was that either one could be tamed independently and either one could be ridden.

This conflicts with the current Legacy behavior, where the small/young Chorusoatl is a growth stage: it can be tamed, fed, and grow into the larger form. A separate Ancient entity raises unresolved questions about which entity owns the growth progression and whether a young regular Chorusoatl should still grow into the current adult regular form.

Requirements already stated:
- Ancient Chorusoatl must be a genuinely separate mob/entity, not just a texture, model, or age variant.
- Regular and Ancient Chorusoatl must each be independently tameable.
- Both must support riding.
- Preserve the current young-Chorusoatl feeding/growth behavior unless a future design decision explicitly changes it.

Open design questions for that future task:
- What is the exact relationship, if any, between young regular Chorusoatl, adult regular Chorusoatl, and Ancient Chorusoatl?
- Does Ancient have its own baby/growth cycle, or is it a separate fixed-stage species/form?
- Which distinct taming item/progression, stats, attacks, animations, spawn rules, and mount controls should Ancient use?
- How should the two forms appear in spawn eggs, registrations, creative tab, and entity data without breaking old worlds or existing tamed Chorusoatls?

Do not implement this during the current crash/creative-tab repair cycle. Revisit only after the stable Legacy build is verified in-game and the user explicitly returns to this idea.

## Required in-game retest of Build #221

1. Launch Forge 1.20.1 with Astemir RE installed separately.
2. Confirm the entity IDs resolve: `archie_dinosaurs:lushsaur`, `archie_dinosaurs:chorusoatl`, `archie_dinosaurs:sculksaur`, and `archie_dinosaurs:chorusoatl_spit`.
3. Try saddle interaction and any normal inventory/slot insertion path on Lushsaur. Confirm the saddle is not consumed, inserted, or reported as equipped.
4. Ride an adult tamed Lushsaur without a saddle. Compare walking and sprinting speed against Build207 and test both with and without the feed-boost timer active. The source diff alone did not identify the reported speed difference.
5. Confirm Chorusoatl retains its existing saddle behavior and that item/block IDs remain under `ec_ad`.
6. Verify rider hitbox/model alignment and the six Chorusoatl egg substrates, including preferred-dimension behavior in the End.
7. Retest mounted LMB/RMB attacks and unrelated retained behavior.

A successful structural CI build is not proof of in-game behavior; do not mark these checks runtime-accepted until tested.
