# AD-NO-BS Legacy recovery status

## Latest visual passenger and Chorusoatl egg candidate — CI #207 — 2026-11-14

- [GitHub Actions #207](https://github.com/Xinees/AD-NO-BS/actions/runs/43620810294) completed successfully. It passed source-JAR verification, pruning, original controller audit, renderer descriptor validation, riding/behavior bytecode checks, final 468-entry JAR validation, and draft-release publication.
- Implementation commit [`d62003d`](https://github.com/Xinees/AD-NO-BS/commit/d62003dfdb6fe0ce160e2b311c9359d6439981a5) originally shifted both Lushsaur client passenger anchors. After Astemir clarified that Lushsaur is rideable only without a saddle, follow-up commit [`9eba750`](https://github.com/Xinees/AD-NO-BS/commit/9eba7504768fc62abaf18fd7bac4c0c420dfa663) removes the saddled-anchor change. Current source shifts only the unsaddled visual anchor to `+0.3374999761581421`; the server `passengerOffset(0, 0, 1)` and unused saddled anchor remain original. The player's entity/hitbox is not moved.
- Resource/egg commit [`d234cb8`](https://github.com/Xinees/AD-NO-BS/commit/d234cb8c732f2a414a7fecc2635de9b07073d4db) extends `data/ec_ad/tags/blocks/chorusoatl_egg_hatchable.json` to include End Stone, End Stone Bricks, Purpur Block, Purpur Pillar, Obsidian and Bedrock.
- Original egg conditions: `minecraft:the_end` is in `chorusoatl_egg_preferred`, and `canHatch()` returns true immediately in that dimension, without substrate/light/chorus checks. Outside the End, the egg still requires zero block/sky light, a hatchable block directly below, and at least five `chorus_plant`/`chorus_flower` blocks within radius 3. The new substrate list changes only the directly-below block tag.
- Workflow guard commit [`adf8e3b`](https://github.com/Xinees/AD-NO-BS/commit/adf8e3b2e6665477a28158c0b9ce447ac6d62877) originally checked both visual offsets. Follow-up guard commit [`46b587d`](https://github.com/Xinees/AD-NO-BS/commit/46b587d2d40635f85fe530ce8229c5e16bf5fefa) changes the requirement to unsaddled-only: the server passenger offset remains original, the unused saddled anchor stays original, and only the unsaddled player model shifts 15 pixels.
- Output: 468 entries, 2,480,685 bytes. Download: [`ec_ad-legacy-forge-1.20.1.jar`](https://github.com/Xinees/AD-NO-BS/releases/download/untagged-77424350b9bac3fad2e7/ec_ad-legacy-forge-1.20.1.jar). Draft release: [Legacy CI build #207](https://github.com/Xinees/AD-NO-BS/releases/tag/untagged-77424350b9bac3fad2e7).
- **Runtime status:** Build #207 passed CI, but its published JAR predates the unsaddled-only visual-anchor correction. Current source includes that correction and updated guards but has not yet been built. The next build must also implement the queued Lushsaur saddle restriction below. Runtime checks remain required for rider alignment and all egg substrates.

---


## Queued for the next build — remove Lushsaur saddle support

- **Not implemented yet.** Lushsaur must be rideable only without a saddle; remove every normal path that lets a saddle be equipped/applied to it.
- Preserve unsaddled owner riding, movement input, sprint, passenger position and the 15-pixel visual-only offset. Keep the server passenger hitbox centered.
- Scope the change to Lushsaur. Do not globally alter shared saddle/equipment behavior for Chorusoatl or other mounts. Do not assume that disabling the mount GUI is sufficient; inspect interaction and equipment-slot paths.
- Acceptance: a saddle cannot equip or alter Lushsaur state and is not consumed on rejection; unsaddled owner riding still works; other mounts' saddle behavior remains unchanged.

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
- Items: Lush Core, Chorus Fossil, Sculk Toxin
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

## Pending Lushsaur behavior task — no saddle required

Lushsaur (Пышнозавр) must be controllable without a saddle once tamed:
- Tame it.
- Mount it as its owner.
- Allow normal player movement control without putting a saddle into its inventory.
- Do not require the ugly saddle model/attachment for Lushsaur control.
- Preserve saddle requirements and behavior for Chorusoatl and Sculksaur unless separately proven otherwise.

Technical cause identified in the original behavior:
- `MountComponent.of(...)` for Lushsaur already permits an adult owner to mount while not sitting; it does not require a saddle in its mount predicate.
- `AbstractComplexMount.m_6688_()` currently returns the controlling passenger only when `m_6254_()` reports a saddle.
- `MountComponent.travel(...)` uses `mob.m_6688_()` to obtain player input, so an unsaddled Lushsaur carries its owner but does not treat them as its controller.
- The planned narrow change is a Lushsaur-only override of `m_6688_()`: keep the superclass path when saddled, but return the first passenger when that passenger is the tamed Lushsaur's owner. Do not change the shared `AbstractComplexMount` saddle rules.

This behavior change is recorded for the next task; it is not part of build 12.

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

## Required in-game retest of build 12

1. Launch Forge 1.20.1 with Astemir RE installed separately.
2. Fire a normal vanilla bow with no mod entities nearby.
3. Spawn and ride Lushsaur and Chorusoatl; verify passenger rendering no longer crashes.
4. Open and close each mount inventory repeatedly; verify no server/client crash.
5. Test LMB and RMB mounted attacks for all three retained mobs and confirm each server-side action executes.
6. Confirm the creative tab order and all eight entries.
7. After those tests pass, implement the Lushsaur no-saddle control exception and test it independently.

A successful structural CI build is not proof that the game runtime crash paths have been cleared.
