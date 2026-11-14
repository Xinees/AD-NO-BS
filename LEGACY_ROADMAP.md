# AD-NO-BS Legacy — roadmap and behavior requirements

**Working branch:** `binary-prune/legacy`  
**Target:** Forge 1.20.1  
**Last reviewed:** 2026-11-14

This file is the explicit task list for the next Legacy work. The order below is intentional. Do not quietly reduce these requests to cosmetic tweaks, and do not mix unrelated behavior changes into one patch.

## Current baseline and test status

### Build 112 — latest CI-verified JAR; runtime test pending

- Latest CI: [run #122 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43533375677).
- Implementation and strengthened CI commit: [`71ab3d2d`](https://github.com/Xinees/AD-NO-BS/commit/71ab3d2d483615054f3a4fc0d664286fb4c89172).
- Artifact ID: `14089055419`; ZIP digest: `sha256:46ece3e402ebad1a3c25bc0341e5539366d37ade33f046c5b305a93373e0ef57`.
- Output JAR: `ec_ad-legacy-forge-1.20.1.jar`; size: `2,493,438` bytes; SHA-256: `112f021a32fd6933b23e42271b0c63b7c6efd43aa77dd3479d09d64c3c7c3735`.
- Build112 fixes the Build111 particle-motion failure. In Minecraft 1.20.1, `SONIC_BOOM` is a `SonicBoomParticle` derived from `HugeExplosionParticle`; its constructor takes one additional scalar, not a full XYZ velocity vector. Passing radial XYZ values to it cannot produce a moving spherical shell. Build112 uses vanilla `SCULK_SOUL`, whose particle provider accepts all three velocity components.
- The effect is two hollow Fibonacci-distributed spherical shells with 768 particles each (1,536 total). The initial radii are 0.55 and 0.05 blocks, so the layers start 0.50 blocks apart. Each particle receives the same normalized radial direction scaled by 0.32 blocks/tick at spawn (about 14% faster than ordinary sprinting).
- Camera shake is preserved. The old post-process distortion, colored light, jaw emitter fallback, and ground-wave emitters remain disconnected. No animation-event timing, attack AI, damage, range, knockback, cooldown, attack animation, or sound logic was changed.
- Build111-to-Build112 archive comparison: all 473 entries remain; no entries were added or removed; exactly one class changed, `SculksaurEffects.class`.
- CI verifies the `SCULK_SOUL` particle type, 768 points per shell, 1,536 total points, both layer divisors, all three 0.32 velocity components, the 0.50-block radial spacing, preserved camera shake, no legacy emitters/distortion, and retained JAR structure. The downloaded JAR was independently checked for ZIP integrity and bytecode.
- **Runtime status:** CI and post-download structural/bytecode inspection passed. Build112 has **not** been launched in Minecraft. In-game checks are still required for the actual shell appearance, outward movement, spacing and density at roughly five blocks, plus attack and unrelated-mob regression checks.

### Build 111 — previous baseline

- CI: [run #116 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43528408869); workflow/guard commit: `8a22d56e01f1bc9b5d08db522ec87fccfef97e50`.
- Code commits: [`127bb60d`](https://github.com/Xinees/AD-NO-BS/commit/127bb60d49cae787e26b8e3feaa594ea4b86174d), [`669bc901`](https://github.com/Xinees/AD-NO-BS/commit/669bc901d48893c3b7f81056b1f7a720ac72283a).
- Artifact ID: `14085958100`; ZIP digest: `sha256:cc0bf23229b7cbc931f70035227a29b42e5933d039fd3f2f411376bb375e9f7b`.
- JAR size: `2,493,447` bytes; SHA-256: `5b01626519d9b02934f932c58d5ffe8fd9210851ba3630725f2347726a03c456`.
- Build110 runtime feedback: Lushsaur and turning are confirmed working; Sculksaur sometimes pitched its head unnaturally toward the ground.
- Build111 fixes the over-snapped vertical look: horizontal yaw stays instant, while `smoothY` is interpolated with the existing `interpolationSpeed` and the original `maxSpeedLimit` is restored for pitch.
- Build111 replaces `SCULK_SOUL` with vanilla `SONIC_BOOM` particles. It creates two concentric expanding spherical layers, 384 particles each (768 total), at radial offsets 0.30 and 0.06 blocks. Particle speed increases from 0.72 to 0.82. Camera shake is preserved; old post-process distortion and ground-wave emitters remain disconnected.
- Build110-to-Build111 comparison: 473 entries in both JARs, no added/removed entries, and only `SculksaurEffects.class` plus `SmoothLookControl.class` changed.
- CI verifies the pitch interpolation, horizontal yaw limit, two particle layers, `SONIC_BOOM` particle type, and JAR structure. Original input JAR and Astemir RE remain untouched/separate.
- **Runtime status:** Build111 has passed CI and post-download archive/bytecode inspection only. It still needs an in-game check for Sculksaur's spawn/idle gaze and the shape/speed/spacing of Sonic Boom; re-confirm Lushsaur sprint and the other turning behavior afterward.

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

**Build112 implements the moving vanilla-particle shell; in-game acceptance tests remain.** The old 3D/post-processing distortion is removed. Build111's native `SONIC_BOOM` points did not move radially because that particle does not use all three supplied velocity components.

### Required result

- The wave is a **hollow, expanding sphere made only from vanilla Minecraft particles**.
- The sphere is an outline/shell, not a filled ball, disc, flat ring, beam, or opaque cloud.
- Its radius expands visibly and evenly from the attack's origin.
- The effect remains readable at normal gameplay distances and does not bury the Sculksaur or nearby terrain in clutter.
- During this wave, remove the other attack-specific particle bursts/emitters. The wave should be the one clear visual effect for that event.
- Do not use Astemir's shader/post-process shockwave, colored-light effect, screen-space distortion, or a replacement custom 3D distortion.

### What the original bytecode shows

The sonic-attack animation event track is `SculksaurEffects.EXPLOSION_EFFECTS`. In the original JAR, its ground event is scheduled at about `4.64` and its final explosion event at about `4.68`; a later tendril/vibration event is scheduled at about `5.12`.

The original `explosionFinalEvent()` checks `PostProcessEngine.INSTANCE.isEnabledInConfig()`. When enabled, it spawns Astemir `ColoredLight` and `Shockwave`; when disabled, it instead spawns an entry from `EMITTER_EXPLOSION` at the jaw. The ground/final events also reference the old explosion emitter set and a ground emitter.

That is the exact visual path to replace. Audit all call sites before deleting resources: the explosion-specific emitters should no longer be spawned by this wave, but unrelated Sculksaur particle tracks must remain intact.

### Build 112 implementation

- The final animation event still calls one isolated sphere helper; the ground-event callback remains a no-op. Camera shake through `ShakeUtil.shakeExplosion(entity, 1.0f, 20.0f)` is preserved.
- The helper uses vanilla `ParticleTypes.SCULK_SOUL`. Its 1.20.1 provider constructs a particle with separate X, Y, and Z velocity components, allowing each particle to travel along the normalized direction from the sphere center.
- A Fibonacci-sphere distribution creates 768 unique directions per layer. The two layers reuse the same directions, so each trailing particle follows the corresponding leading particle with an initial radial separation of exactly 0.50 blocks.
- The leading shell starts at radius 0.55 blocks; the trailing shell starts at radius 0.05 blocks. Both receive the same radial velocity magnitude of 0.32 blocks/tick at spawn.
- Total spawn count is 1,536 particles. At a five-block radius this is substantially denser than the prior 384-particle layers while remaining a hollow shell rather than a filled volume.
- The `SONIC_BOOM` type is not used for this moving shell: its 1.20.1 particle implementation is `SonicBoomParticle` derived from `HugeExplosionParticle`, and its constructor accepts one additional scalar instead of a 3D velocity vector. A custom particle registration/mixin would be required to make that exact particle travel radially; this build deliberately avoids adding that extra client infrastructure.
- No attack AI, target selection, event-track timing, damage, range, knockback, cooldown, attack animation, or sound code was changed. Other Sculksaur particle tracks are not touched.
- CI run #119 verifies the particle type, helper descriptors, two 768-point layers, 1,536 total spawns, identical XYZ velocity components, layer spacing, preserved camera shake, and absence of the removed distortion and ground emitters. This is bytecode verification only; it cannot confirm the visual result in-game.

### Build 94 implementation (historical; superseded by Build112)

- `lambda$explosionFinalEvent$12` now calls a dedicated vanilla-particle sphere helper. The helper creates 1,536 points with a Fibonacci-sphere distribution, starts them 0.15 blocks from the center, and sends them outward along their radial direction at 0.72 blocks/tick. The particle is vanilla `ParticleTypes.SCULK_SOUL`.
- The origin is the Sculksaur's horizontal center and half its bounding-box height above its feet. This produces a 3D shell rather than the previous mixture of discs, rings, and ground effects.
- `lambda$explosionGroundEvent$11` is now a no-op, so the four old ground-wave emitters are no longer spawned. The final jaw-emitter fallback is also gone.
- `PostProcessEngine`, `Shockwave`, and `ColoredLight` are no longer called from the Sonic Boom event, so the visual no longer depends on the post-processing setting.
- `ShakeUtil.shakeExplosion(entity, 1.0f, 20.0f)` remains. It is the original camera shake, not the removed screen-space distortion.
- The existing event-track callbacks/timestamps and every other `SculksaurEffects` track were left unchanged. The attack AI and combat implementation were not edited.
- CI run #94 verifies the two patched event methods, helper descriptors, vanilla particle type and spawn call, preserved camera shake, and absence of old wave calls. This proves bytecode structure only; it does not prove how dense or smooth the sphere looks in the game.
- Old explosion particle asset files remain in the pruned JAR but are no longer spawned by either Sonic Boom event method. They were not deleted because this pass only disconnects the attack-specific visual call path.


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
