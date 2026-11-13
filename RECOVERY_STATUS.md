# AD-NO-BS Legacy recovery status

Last updated: 2026-11-13
Working branch: `binary-prune/legacy`

## Current confirmed build

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
