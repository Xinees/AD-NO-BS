# AD-NO-BS Legacy — roadmap and behavior requirements

**Working branch:** `binary-prune/legacy`  
**Target:** Forge 1.20.1  
**Last reviewed:** 2026-11-14

This file is the explicit task list for the next Legacy work. The order below is intentional. Do not quietly reduce these requests to cosmetic tweaks, and do not mix unrelated behavior changes into one patch.

## Current baseline and test status

### Build 81

- Commit: `eeec7201f55dd1252d201284048d8468312d6a01`
- GitHub Actions run: [#81](https://github.com/Xinees/AD-NO-BS/actions/runs/43507305757) — successful.
- Artifact ID: `14075429830`
- JAR SHA-256: `e46600a329c32e34554157fac2964ab7ebf10f9b747a39ab4592ed7bca479b53`
- CI verifies the added LocalPlayer sprint gate, the Lushsaur mounted speed callback, retained content, and JAR structure.
- **Runtime status: not yet confirmed by the user.** The user's report in this chat concerns build 78, not build 81.

### Build 78 — user-reported test result

The player mounts the Lushsaur and holds W. The Lushsaur begins with its walking animation. After roughly three seconds, the running animation appears, but the actual movement speed does not increase. This is a mismatch: animation says "run", movement still behaves like walking.

Build 81 adds the next part of the fix that build 78 did not contain: the client-side `LocalPlayerMixin` gate for `LocalPlayer.vehicleCanSprint(Entity)`. It grants the vanilla vehicle-sprint check only when the vehicle is a Lushsaur and the local player is the Lushsaur's actual controlling passenger. Build 81 also uses the controlling passenger's sprint state in the Lushsaur mount speed callback.

Do not mark mounted sprint as fixed based on CI alone. The user must test build 81 in-game.

## Priority 1 — replace the Sculksaur Sonic Boom visual effect

**This is the first visual rework to implement.** The current 3D/post-processing distortion is explicitly unwanted. Remove it completely from the Sculksaur's sonic-wave attack; do not merely lower its intensity, shrink it, or tune the distortion until it is "less noticeable".

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

### Preserve the gameplay attack

This request is about the wave's visuals. Keep the existing Sonic Boom gameplay behavior unless a separate bug is found and reported first:

- target selection and attack conditions;
- timing and cooldown;
- damage, range, knockback, and vertical impulse;
- attack action/animation and sound;
- Sculksaur AI and vibration/listening behavior.

Do not remove or rewrite the entire `SculksaurEffects` system. Keep unrelated idle, walk, run, tail, roar, listen, spawn, and death effects.

**Open check before implementation:** `ShakeUtil.shakeExplosion(...)` is a camera-shake call in the same final event, not the shader `Shockwave` object. Confirm whether it is part of the unwanted distortion before removing it; do not silently remove camera shake as collateral.

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
