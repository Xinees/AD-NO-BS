# AD-NO-BS Legacy — roadmap and behavior requirements

**Working branch:** `binary-prune/legacy`  
**Target:** Forge 1.20.1  
**Last reviewed:** 2026-11-14

This file is the explicit task list for the next Legacy work. The order below is intentional. Do not quietly reduce these requests to cosmetic tweaks, and do not mix unrelated behavior changes into one patch.

## Current baseline and test status

### Build 114 — latest CI-verified JAR; runtime test pending

- Latest CI: [run #136 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43538851094).
- Final implementation commit: [`bbfc8b57`](https://github.com/Xinees/AD-NO-BS/commit/bbfc8b573e9953341515e394ab71ce95f1520a8d).
- Artifact ID: `14090903394`; ZIP digest: `sha256:d813ffe31ef092bd664a300bca5a58f0eb1ee64a3f509cafce49c4daa902a348`.
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
- **Runtime status:** CI and downloaded-JAR checks passed. Build114 has **not** yet been launched in Minecraft. In-game confirmation is still required for the visible shell shape, same-origin appearance, half-second delay, motion, density at roughly five blocks, and attack/unrelated-mob regression checks.

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

**Build114 implements the requested two sequential spherical pulses from one origin and passes CI; in-game acceptance is still pending.**

### Required result

- Two hollow expanding spheres, both spawning at the exact same attack origin.
- The first sphere appears first. The second appears about half a second later; the spacing between wavefronts must be caused by the delay, not different initial radii.
- Each sphere expands evenly in every direction.
- Use the actual vanilla `SONIC_BOOM` particle type, not `SCULK_SOUL`.
- The count must be restrained and readable at roughly five blocks. Avoid filling the volume or creating a dense cloud.
- Remove the other attack-specific particle bursts/emitters from this event. Keep the original camera shake on the first event.
- Do not use Astemir's shader/post-process shockwave, colored-light effect, screen-space distortion, or a replacement custom 3D distortion.

### What the vanilla particle requires

In Minecraft 1.20.1, `SONIC_BOOM` is rendered by `SonicBoomParticle`, which extends `HugeExplosionParticle`. Its provider interprets the first extra scalar as the vanilla particle size and does not apply the supplied XYZ velocity components. Passing a radial XYZ vector to `Level.addParticle` cannot make the exact vanilla Sonic Boom particle travel.

Build114 retains the exact `SONIC_BOOM` type, vanilla sprite/animation, full-bright rendering and 1.5-block quad size. A narrowly scoped subclass adds radial movement only for particles emitted by the Sculksaur event. Unmarked calls still delegate to the vanilla provider.

### Build114 implementation

- The first event remains at `4.68s`. It reads the Sculksaur's X/Z and half-height-adjusted Y once, stores those coordinates by entity in a `WeakHashMap`, and creates the first sphere at that point.
- A separate second event is added at `5.18s`: a delay of exactly `0.50s` or 10 game ticks at 20 TPS. It consumes and removes the saved XYZ origin and creates the second sphere at the exact same point. It does not read the entity position again, so mob movement during the delay cannot shift the second origin.
- Each sphere contains **128 particles**, for **256 total** across the two pulses. Directions use a Fibonacci-sphere distribution.
- Every particle receives radial velocity with magnitude `0.315` blocks/tick = **6.30 blocks/second**. The vanilla 16-tick lifetime yields about 5.04 blocks of travel for each pulse.
- At the moment the second pulse starts, the first wavefront has travelled 3.15 blocks. Each pulse lasts 0.80s; the entire two-pulse sequence ends 1.30s after the first spawn.
- At a 5.04-block radius, the average spacing between Fibonacci-distributed particles is about 1.49 blocks, close to the vanilla quad size of 1.5 blocks. This should avoid the excessive density of the earlier 768-particle-per-wave version while keeping the outline nearly continuous. This is a geometry estimate only; check actual visibility in-game.
- The original camera shake `ShakeUtil.shakeExplosion(entity, 1.0f, 20.0f)` remains on the first event. The second visual pulse does not replay it. The original tendril/vibration event at `5.12s` remains between the two sphere spawns.
- **Callback isolation:** the second AnimationEvent factory is cloned from the original, but its ASM `invokedynamic` bootstrap-argument array is cloned before changing the callback handle. The original factory must still target `lambda$explosionFinalEvent$12`; the second factory targets only `archiesDinoMod$lambdaSecondSonicBoomEvent`. The pruner checks this to prevent the first event from accidentally being redirected to the second callback.
- No attack AI, target selection, original event time, damage, range, knockback, cooldown, attack action, sound, or unrelated Sculksaur animation/particle track was changed. The second event is visual-only.
- Build113-to-Build114 archive comparison: 475 entries in each archive; no additions/removals; only `SculksaurEffects.class` changed. No resource, model, texture, sound, or JSON entry changed.
- CI run #136 verifies exact `SONIC_BOOM` registration/provider behavior, 128 positive iterations per wave (`SIPUSH 128`), common saved origin, 10-tick timing, radial velocity, callback separation, camera shake, retained JAR structure, and existing controls/renderers. The downloaded JAR was also independently inspected with `javap` and compared entry-by-entry. Bytecode tests cannot prove the visual result in Minecraft.

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
