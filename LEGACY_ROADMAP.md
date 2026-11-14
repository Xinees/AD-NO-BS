# AD-NO-BS Legacy — roadmap and behavior requirements

**Working branch:** `binary-prune/legacy`  
**Target:** Forge 1.20.1  
**Last reviewed:** 2026-11-14

This file is the explicit task list for the next Legacy work. The order below is intentional. Do not quietly reduce these requests to cosmetic tweaks, and do not mix unrelated behavior changes into one patch.

## Current baseline and test status

### Build 113 — latest CI-verified JAR; runtime test pending

- Latest CI: [run #127 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43535858739).
- Final code/CI commit: [`481251ab`](https://github.com/Xinees/AD-NO-BS/commit/481251ab558bc9f8a8f88ac1a1c127ae6661bc75).
- Artifact ID: `14089138883`; ZIP digest: `sha256:7b695c626191a600f8ce09ec79ab1c4b02ce9033e6f1424ce8f6c3ca68aa7e80`.
- Output JAR: `ec_ad-legacy-forge-1.20.1.jar`; size: `2,495,453` bytes; SHA-256: `e8be7d42b2be79aeefe5bcd03face06d8f9a5da43fa15052d9ea890f06375228`.
- Build113 restores the **actual vanilla `SONIC_BOOM`** particle type for the moving shell. It does not substitute `SCULK_SOUL`. A small subclass of vanilla `SonicBoomParticle` keeps its original sprite set, render type, full-bright rendering, size, and 16-tick age animation, adding movement only to particles created by the Sculksaur effect.
- The Forge sprite-set provider delegates unmarked `SONIC_BOOM` creation to vanilla `SonicBoomParticle.Provider`. A thread-local marker is set only around the Sculksaur helper's synchronous `Level.addParticle` call and is removed on both success and exception paths. Ordinary, unmarked vanilla Sonic Boom particles therefore keep the vanilla static behavior.
- Two hollow Fibonacci-distributed shells: 768 particles per layer (1,536 total), initial radii 0.55 and 0.05 blocks, 0.50-block gap, radial speed 0.32 blocks/tick (6.4 blocks/second, about 14% above the 5.612 blocks/second sprint reference). At the vanilla 16-tick lifetime, a particle travels about 5.12 blocks; estimated ending radii are 5.67 and 5.17 blocks.
- Camera shake remains. The old post-process distortion, colored light, jaw-emitter fallback, and ground-wave emitters remain disconnected. No animation-event timing, attack AI, damage, range, knockback, cooldown, attack animation, or sound logic changed. Other Sculksaur particle tracks are untouched.
- Build112-to-Build113 JAR comparison: 473 entries before and 475 after; two generated client particle classes added; only `SculksaurEffects.class` and `ADClientModBus.class` changed; no entries removed and no model, texture, sound, JSON, or mob gameplay class changed.
- CI verifies the exact `SONIC_BOOM` type, sprite-set registration, vanilla provider fallback, moving subclass/base tick behavior, both 768-particle layers, all 1,536 particles, radial velocity and spacing, camera shake, ZIP integrity, retained JAR content, and existing controls/renderers. The downloaded JAR's bytecode and entry comparison were independently inspected.
- **Runtime status:** CI and downloaded-JAR checks passed, but Build113 has **not** been launched in Minecraft by this audit. In-game confirmation is still required for the actual shell appearance/movement/density at roughly five blocks and for attack/unrelated-mob regressions.

### Build 112 — rejected particle substitution

- Build112 passed its earlier CI, but it used vanilla `SCULK_SOUL` instead of the explicitly requested `SONIC_BOOM`. Its CI checked for the wrong particle type, so that success did not satisfy the task. Build112/123 are not accepted as the Sonic Boom visual fix.
- Build113 supersedes it with the actual `SONIC_BOOM` particle and a narrowly scoped moving subclass/provider.

### Build 111 — previous baseline

- CI: [run #116 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43528408869); workflow/guard commit: `8a22d56e01f1bc9b5d08db522ec87fccfef97e50`.
- Code commits: [`127bb60d`](https://github.com/Xinees/AD-NO-BS/commit/127bb60d49cae787e26b8e3feaa594ea4b86174d), [`669bc901`](https://github.com/Xinees/AD-NO-BS/commit/669bc901d48893c3b7f81056b1f7a720ac72283a).
- Artifact ID: `14085958100`; ZIP digest: `sha256:cc0bf23229b7cbc931f70035227a29b42e5933d039fd3f2f411376bb375e9f7b`.
- JAR size: `2,493,447` bytes; SHA-256: `5b01626519d9b02934f932c58d5ffe8fd9210851ba3630725f2347726a03c456`.
- Build110 runtime feedback: Lushsaur and turning are confirmed working; Sculksaur sometimes pitched its head unnaturally toward the ground.
- Build111 fixes the over-snapped vertical look: horizontal yaw stays instant, while `smoothY` is interpolated with the existing `interpolationSpeed` and the original `maxSpeedLimit` is restored for pitch.
- Build111's Sonic Boom shell used the correct `SONIC_BOOM` type, but its particles were static because vanilla SonicBoomParticle ignores XYZ velocity. Build113 corrects that limitation without changing the particle type.
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

**Build113 contains the requested `SONIC_BOOM` shell and passes CI; in-game acceptance is still pending.** Build112 was a rejected substitution: it used `SCULK_SOUL` despite the explicit request for `SONIC_BOOM`. Do not treat Build112 or its CI result as satisfying this task.

### Required result

- The wave is a **hollow, expanding sphere made from the actual vanilla `SONIC_BOOM` particle type**.
- The sphere is an outline/shell, not a filled ball, disc, flat ring, beam, or opaque cloud.
- Two shells start 0.50 blocks apart; the trailing layer follows the leading layer.
- The radius expands evenly in all directions from the attack origin.
- The wave should remain readable at roughly five blocks without burying the Sculksaur or terrain in visual clutter.
- Remove the other attack-specific particle bursts/emitters from this event. The spherical wave should be the clear visual for this event.
- Do not use Astemir's shader/post-process shockwave, colored-light effect, screen-space distortion, or a replacement custom 3D distortion.

### What the vanilla particle requires

In Minecraft 1.20.1, `SONIC_BOOM` is rendered by `SonicBoomParticle`, which extends `HugeExplosionParticle`. Its provider treats the first extra scalar as the vanilla particle size and does not apply the supplied XYZ velocity components. Simply passing radial XYZ values to `Level.addParticle` cannot make the exact vanilla Sonic Boom particle travel.

The correct solution must retain the `SONIC_BOOM` type and vanilla sprite/animation behavior while adding movement only to the particles emitted by this Sculksaur event. Replacing the type with `SCULK_SOUL` is not an acceptable workaround.

### Build113 implementation

- The Sculksaur sphere helper calls `ParticleTypes.SONIC_BOOM` (`f_235902_`) for all 1,536 particles.
- A small `SculksaurSonicBoomParticle` subclass extends vanilla `SonicBoomParticle`. It invokes the vanilla constructor with the same sprite set and size behavior; its tick invokes `HugeExplosionParticle.tick()` to preserve the original frame animation and 16-tick lifetime, then applies its radial movement.
- The generated provider is registered through Forge's `RegisterParticleProvidersEvent.registerSpriteSet`, reusing the vanilla Sonic Boom sprite set. Unmarked calls delegate to the vanilla `SonicBoomParticle.Provider`; only the Sculksaur helper marks its synchronous creation call for movement. The ThreadLocal marker is removed on success and exception paths.
- A Fibonacci-sphere distribution creates 768 unique directions per layer. Both layers reuse the same directions so each trailing particle follows its corresponding leading particle with an initial radial separation of exactly 0.50 blocks.
- The leading shell starts at radius 0.55 blocks; the trailing shell starts at radius 0.05 blocks. Both receive the same radial velocity magnitude of 0.32 blocks/tick (6.4 blocks/second; about 14% above the 5.612 blocks/second sprint reference).
- Vanilla Sonic Boom's 16-tick lifetime means about 5.12 blocks of travel. Approximate final radii are 5.67 and 5.17 blocks. The 768-point distribution gives an estimated particle spacing of roughly 0.7 blocks near that radius; the 1.5-block vanilla quad size causes overlap. Actual visual readability still requires an in-game check.
- The final animation event still triggers the sphere helper and the original camera shake `ShakeUtil.shakeExplosion(entity, 1.0f, 20.0f)`. The ground-event callback remains a no-op.
- No attack AI, target selection, event-track timing, damage, range, knockback, cooldown, attack animation, sound, or unrelated Sculksaur particle track was changed.
- Build112-to-Build113 archive comparison: two new generated client classes added, exactly two classes changed (`SculksaurEffects.class`, `ADClientModBus.class`), no entries removed, and no resources/models/textures/sounds/JSON changed.
- CI run #127 verifies exact `SONIC_BOOM` registration, vanilla-provider fallback for unmarked calls, custom particle inheritance and tick path, both 768-point layers, 1,536 total particles, equal radial speed components, initial radii/spacing, preserved camera shake, and retained JAR structure. The downloaded JAR was separately inspected with `javap` and compared entry-by-entry. These checks establish structure and bytecode, not in-game appearance.

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
