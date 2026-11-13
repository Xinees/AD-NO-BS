/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack
 *  com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.client.particle.effect;

import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent;
import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.client.util.ShakeUtil;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TherimossEffects {
    private static final String LOCATOR_HEAD = "head";
    private static final String LOCATOR_JAW = "jaw";
    private static final String LOCATOR_LAND = "land";
    private static final String LOCATOR_CLAW_ATTACK = "claw_attack";
    private static final String LOCATOR_CLAW_ATTACK2 = "claw_attack2";
    private static final String LOCATOR_BEAK_ATTACK = "beak_attack";
    private static final ParticleEmitterConfig[] EMITTER_ATTACK = TherimossEffects.register("attack/therimoss_attack", 1, 2);
    private static final ParticleEmitterConfig[] EMITTER_BEAK_ATTACK = TherimossEffects.register("beak_attack/therimoss_beak_attack", 0, 2);
    private static final ParticleEmitterConfig EMITTER_FLIES = TherimossEffects.register("flies/therimoss_flies");
    private static final ParticleEmitterConfig EMITTER_EAT = TherimossEffects.register("eat/therimoss_eat");
    private static final ParticleEmitterConfig EMITTER_DIGGING = TherimossEffects.register("therimoss_diging");
    public static final AnimationEventTrack<TherimossEntity> IDLE_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_FLIES, (String)"head"));
    public static final AnimationEventTrack<TherimossEntity> WALK_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, TherimossEffects.stepEvent()).addEvent(0.6f, TherimossEffects.stepEvent());
    public static final AnimationEventTrack<TherimossEntity> RUN_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, TherimossEffects.stepEvent()).addEvent(0.5f, TherimossEffects.stepEvent());
    public static final AnimationEventTrack<TherimossEntity> DEATH_EFFECTS = AnimationEventTrack.of().addEvent(1.85f, ctx -> {
        TherimossEntity entity = (TherimossEntity)ctx.getInstance();
        BlockState stateBelow = entity.m_20075_();
        if (entity.m_20096_() && !stateBelow.m_60795_() && !entity.m_6162_()) {
            ShakeUtil.shakeTailSlam((LivingEntity)entity, 1.0f, 8.0f);
        }
    });
    public static final AnimationEventTrack<TherimossEntity> CLIMB_UP_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.playSound(ADSoundEvents.THERIMOSS_CLIMB_UP));
    public static final AnimationEventTrack<TherimossEntity> CLIMB_DOWN_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.playSound(ADSoundEvents.THERIMOSS_CLIMB_UP));
    public static final AnimationEventTrack<TherimossEntity> EAT_EFFECTS = AnimationEventTrack.of().addEvent(1.8333f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EAT, (String)"jaw")).addEvent(2.4167f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EAT, (String)"jaw"));
    public static final AnimationEventTrack<TherimossEntity> LAND_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_BEAK_ATTACK[0], (String)"land"));
    public static final AnimationEventTrack<TherimossEntity> ATTACK_HIT_EFFECTS = AnimationEventTrack.of().addEvent(0.5f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_ATTACK[1], (String)"claw_attack")).addEvent(0.875f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_ATTACK[0], (String)"claw_attack2")).addEvent(1.1667f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_ATTACK[1], (String)"claw_attack"));
    public static final AnimationEventTrack<TherimossEntity> ATTACK_BITE_EFFECTS = AnimationEventTrack.of().addEvent(1.7917f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_BEAK_ATTACK[0], (String)"beak_attack").and(AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_BEAK_ATTACK[1], (String)"beak_attack")).and(ctx -> ShakeUtil.shakeExplosion((Entity)ctx.getInstance(), 0.85f, 10.0f)));

    private static AnimationEvent<TherimossEntity> stepEvent() {
        return ctx -> {
            TherimossEntity entity = (TherimossEntity)ctx.getInstance();
            if (entity.m_20096_()) {
                float basePitch = entity.m_6162_() ? 1.1f : 0.8f;
                ctx.playSound(ADSoundEvents.THERIMOSS_STEP.get(), SoundSource.NEUTRAL, 1.0f, basePitch + entity.m_9236_().m_213780_().m_188501_() * 0.4f);
                if (!entity.m_6162_()) {
                    ShakeUtil.shakeStep((Entity)ctx.getInstance(), 8.0f);
                }
            }
        };
    }

    private static ParticleEmitterConfig[] register(String name, int startIndex, int count) {
        return ADParticleEmitters.register("therimoss/" + name, startIndex, count);
    }

    private static ParticleEmitterConfig register(String name) {
        return ADParticleEmitters.register("therimoss/" + name);
    }

    public static void init() {
    }
}

