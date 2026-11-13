# AD-NO-BS Legacy — roadmap and behavior requirements

**Working branch:** `binary-prune/legacy`  
**Target:** Forge 1.20.1  
**Last reviewed:** 2026-11-14

This file is the explicit task list for the next Legacy work. The order below is intentional. Do not quietly reduce these requests to cosmetic tweaks, and do not mix unrelated behavior changes into one patch.

## Current baseline and test status

### Build 94 — latest CI-verified JAR

- Animation patch implementation: [commit 338f774](https://github.com/Xinees/AD-NO-BS/commit/338f774b613ff886accb50b2438bc1e57a3a01a6); ASM invokedynamic node correction: [commit ff48c64](https://github.com/Xinees/AD-NO-BS/commit/ff48c64b91a56998d9742049c7ab66d791b44a51).
- Sonic Boom visual patch: [commit 642be30](https://github.com/Xinees/AD-NO-BS/commit/642be309f50e8330ff24f48b3ce08d6abd3d6f64).
- CI verification: [run #94 — success](https://github.com/Xinees/AD-NO-BS/actions/runs/43516535668).
- Artifact ID: `14080273953`; artifact ZIP digest: `sha256:1862f5120d2d95167f856fd05c29e7769c9841ecbc44952fc8558551fcfddc72`.
- Output JAR SHA-256: `c3ded081be4631e747c26ee1b68261adec81aa0752993e861fdc5b9f9c7d90a8`; size: `2,493,690` bytes.
- The user's current report confirms that Ctrl and double-tap W both activate mounted sprint. Build 94 corrects the delayed run-animation transition by making the mounted Lushsaur's locomotion run condition read the controlling passenger's `isSprinting()`. The rider's own run animation uses the same condition. When the Lushsaur has no controlling passenger, its pre-existing AI locomotion test is retained.
- The Sculksaur Sonic Boom final visual event now spawns a hollow shell of 1,536 vanilla `SCULK_SOUL` particles, distributed over a sphere and moving radially outward at 0.72 blocks/tick. The sphere is centered at the Sculksaur's body center. The old post-process `Shockwave` and `ColoredLight`, jaw emitter fallback, and old ground-wave emitters are removed from the attack's event methods.
- The camera shake `ShakeUtil.shakeExplosion(entity, 1.0f, 20.0f)` is intentionally preserved; it is separate from the screen-space distortion that was removed.
- Only the two Sonic Boom visual event methods in `SculksaurEffects` were changed. The attack AI, target selection, cooldown, range, damage, knockback, animation event schedule, sounds, listening/vibration behavior, and unrelated Sculksaur particle tracks remain untouched.
- **Runtime status:** CI verifies the transformed bytecode, helper descriptors, vanilla particle call, retained camera shake, and absence of the old wave calls. Build 94 has not yet been visually tested in-game. Test mounted walking for more than three seconds and sprint activation; test Sonic Boom with post-processing both enabled and disabled.

### Previous sprint baseline — Build 81

Build 81 added the Lushsaur-only vanilla mounted-sprint permission path in `LocalPlayerMixin` and changed the mounted movement-speed callback to read the controlling passenger's sprint state while retaining the feeding speed boost. Build 94 preserves that behavior and corrects only the animation predicates.

### Build 78 — user-reported historical test

The player mounted the Lushsaur and held W. The Lushsaur began with its walking animation; after roughly three seconds the running animation appeared, but actual movement speed did not increase. Build 78 was not correct. The user's newer report confirms that sprint input is now accepted through Ctrl and double-tap W; only the run-animation timing remained to be fixed.

## Priority 1 — replace the Sculksaur Sonic Boom visual effect

**Implemented in Build 94; in-game acceptance tests remain.** The current 3D/post-processing distortion is explicitly unwanted and has been removed from the Sculksaur's sonic-wave attack; it was not merely toned down.

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

### Build 94 implementation

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

**The smooth turning currently looks bad. The request is to remove it, not to make it slightly faster.** Do not respond by merely adjusting the interpolation values.

This applies to all three retained mobs:

- Lushsaur (Пышнозавр)
- Chorusoatl
- Sculksaur

The original classes for all three override `m_7560_()` and construct the custom `SmoothBodyControl`. Its parameters include a low body interpolation factor (`0.07`) and a stable-head timing window. This is the known common source to inspect first.

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
