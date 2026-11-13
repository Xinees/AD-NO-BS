# AD-NO-BS Legacy recovery status

Last updated: 2026-11-13
Working branch: `binary-prune/legacy`
Last confirmed playable baseline: `EC-AD Legacy Forge 1.20.1 fixed4`, Direct Legacy JAR Prune build 11; current repair cycle: build 12
Baseline GitHub Actions run: `43459256389` (run number 68)
Baseline artifact: `14054748256`
Baseline commit: `8503044eedfffe1c7fcc7a07f315c1ce807065e4`

## Goal and retained scope

Produce a Forge 1.20.1 Legacy build from the untouched original Archie's Dinosaurs JAR. Retain only:
- Mobs: Lushsaur, Chorusoatl, Sculksaur
- Items: Lush Core, Chorus Fossil, Sculk Toxin
- Required hatchable eggs, spawn eggs, models, textures, animations, sounds, AI, particles, client rendering, mount inventory and riding controls

The user's in-game verification confirms that this baseline launches, the three mobs work, and the mod's creative tab is present. Do not treat that as confirmation that the specific actions listed below are stable: the crash logs show repeatable failures in those paths.

## Immutable inputs

- Original mod: `ec_ad-forge-1.20.1-1.0.4.jar`
- Astemir RE: `astemirlib_re-1.20.1-forge-1.2.98-SNAPSHOT.jar`
- Both inputs are immutable. Do not overwrite, patch, repackage, or otherwise modify either input JAR.
- Astemir RE is an external mandatory dependency in `mods`. Do not embed another copy inside the Legacy JAR.

## Confirmed baseline state

- Direct ASM pruning and resource pruning complete successfully in build 11.
- Baseline artifact is a valid ZIP/JAR and retains the three intended entity packages.
- Baseline build is runtime-playable according to the user's test, with mobs and the creative tab working.
- The previous renderer-provider and egg-renderer descriptor fixes are present.
- Build 11 is not yet acceptable as a stable release because the crash log collection demonstrates four distinct broken paths.

## Crash log findings (main-branch test uploads)

The five root TXT files uploaded on `main` were reviewed: `bow.txt`, `lush.txt`, `lush2.txt`, `random.txt`, and `random_num2.txt`.

1. `bow.txt`: firing an ordinary vanilla bow, without a dinosaur being present, crashes with `NoSuchFieldError: archies_Dino_Mod$RIB_BOW_ARROW`. `AbstractArrowMixin` had its field removed but its static initializer still wrote to that field.
2. `lush.txt` and `random.txt`: rendering a ridden Lushsaur/Chorusoatl crashes with `AbstractMethodError` for `ADModifiers$1.modify(...)`. The anonymous class still implements Astemir's `Modifier` interface, but the override was stripped.
3. `lush2.txt`: opening the mount inventory triggers missing framework overrides: `DinoMenu.m_6875_(Player)` and `DinoScreen.m_7286_(GuiGraphics,float,int,int)`.
4. The user's test note at the beginning of `random.txt` also identifies a separate functional regression: LMB/RMB mounted attacks do not dispatch. Inspection of the baseline artifact shows `MountControlMessage.execute(ServerPlayer)` was stripped even though `MountControlMessage` extends the internal abstract `AbstractServerboundMessage`.
5. `random_num2.txt` is an additional test session and does not introduce another distinct exception in the inspected log section.

## Fixes being applied

- Preserve only the four proven framework/network callback implementations by exact owner + descriptor. This restores required virtual/interface dispatch without reopening unrelated methods and accidentally retaining removed entities.
- Remove the obsolete `AbstractArrowMixin.<clinit>` together with the removed rib-bow accessor. Keep unrelated arrow tick and Sculksaur hit handling.
- Make creative-tab population deterministic and ordered: three spawn eggs first, then two hatchable eggs, then Lush Core, Sculk Toxin, and Chorus Fossil.
- Add CI regression checks for all four missing callback methods, the removed bow field reference, the creative-tab order, and the absence of removed entity classes.

These are bytecode/build-time fixes. They still require a fresh in-game retest before being marked runtime-confirmed.

## Pending item and behavior work

- Lushsaur (Пышнозавр) should not require a saddle for player control. After it is tamed, the player should be able to mount it and control it normally without inserting a saddle. The user specifically wants this because the current saddle model looks poor. Preserve the original behavior for the other species unless testing proves otherwise.
- Keep Lush Core, Sculk Toxin, and Chorus Fossil in the creative tab even though they currently have no practical use.
- Do not change other mob behavior, attacks, animations, stats, or balance: the user reports that everything else currently works. Only restore the broken mounted attack input path and address the explicit saddle exception above.

## Required retest sequence after CI

1. Launch Forge 1.20.1 with Astemir RE installed separately.
2. Fire a normal vanilla bow with no mod entities nearby.
3. Spawn and ride each of the three retained mobs; confirm no render crash.
4. Open and close each mount inventory repeatedly; confirm no server/client crash.
5. Test LMB and RMB mounted attacks for each supported mob and check that server-side actions execute.
6. Verify the creative tab order and that all eight retained entries are present.
7. Only then continue with the Lushsaur no-saddle control change and final verification.

Do not describe a structurally successful CI artifact as fully runtime-tested.
