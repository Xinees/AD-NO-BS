/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack
 *  com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.client.particle.effect;

import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent;
import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.client.util.ShakeUtil;
import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LushsaurEffects {
    private static final ParticleEmitterConfig[] EMITTER_CHARGE_TRAIL = LushsaurEffects.register("charge/lushsaur_charge_trail", 0, 3);
    private static final ParticleEmitterConfig[] EMITTER_ROAR = LushsaurEffects.register("roar/lushsaur_roar", 0, 2);
    private static final ParticleEmitterConfig[] EMITTER_UP_GROUND = LushsaurEffects.register("up/lushsaur_up_ground", 0, 2);
    private static final ParticleEmitterConfig EMITTER_UP = LushsaurEffects.register("up/lushsaur_up");
    private static final ParticleEmitterConfig EMITTER_TAIL_STOMP = LushsaurEffects.register("lushsaur_attack3");
    private static final ParticleEmitterConfig EMITTER_DOWN_GROUND = LushsaurEffects.register("lushsaur_down_ground");
    private static final ParticleEmitterConfig EMITTER_EAT = LushsaurEffects.register("lushsaur_eat");
    private static final ParticleEmitterConfig EMITTER_REST = LushsaurEffects.register("lushsaur_rest");
    private static final String LOCATOR_GROUND = "ground";
    private static final String LOCATOR_GROUND2 = "ground2";
    private static final String LOCATOR_GROUND3 = "ground3";
    private static final String LOCATOR_JAW = "jaw";
    private static final String LOCATOR_HEAD = "head";
    private static final String LOCATOR_HEAD2 = "head2";
    private static final String LOCATOR_MOSS = "moss";
    public static final AnimationEventTrack<LushsaurEntity> UP_EFFECTS = AnimationEventTrack.of().addEvent(0.33f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_UP_GROUND[0], (String)"ground")).addEvent(1.45f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_UP, (String)"ground2").and(AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_UP_GROUND[1], (String)"ground2")));
    public static final AnimationEventTrack<LushsaurEntity> DOWN_EFFECTS = AnimationEventTrack.of().addEvent(0.95f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_DOWN_GROUND, (String)"ground"));
    public static final AnimationEventTrack<LushsaurEntity> REST_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_REST, (String)"head")).addEvent(0.0f, AnimationEvent.playSound(ADSoundEvents.LUSHSAUR_REST));
    public static final AnimationEventTrack<LushsaurEntity> TAIL_STOMP_EFFECTS = AnimationEventTrack.of().addEvent(0.88f, ctx -> {
        LushsaurEntity entity = (LushsaurEntity)ctx.getInstance();
        BlockState stateBelow = entity.m_20075_();
        if (entity.m_20096_() && !stateBelow.m_60795_()) {
            if (entity.m_6162_()) {
                ShakeUtil.shakeTailSlam((LivingEntity)entity, 1.0f, 8.0f);
            } else {
                ShakeUtil.shakeTailSlam((LivingEntity)entity, 2.0f, 15.0f);
                ctx.spawnBlockParticle(EMITTER_TAIL_STOMP, LOCATOR_GROUND2, stateBelow);
            }
        }
    });
    public static final AnimationEventTrack<LushsaurEntity> DEATH_EFFECTS = AnimationEventTrack.of().addEvent(1.0f, ctx -> {
        LushsaurEntity entity = (LushsaurEntity)ctx.getInstance();
        BlockState stateBelow = entity.m_20075_();
        if (entity.m_20096_() && !stateBelow.m_60795_() && !entity.m_6162_()) {
            ShakeUtil.shakeTailSlam((LivingEntity)entity, 1.2f, 8.0f);
        }
    });
    public static final AnimationEventTrack<LushsaurEntity> TAIL_SWING_EFFECTS = AnimationEventTrack.of().addEvent(1.0f, ctx -> {
        LushsaurEntity entity = (LushsaurEntity)ctx.getInstance();
        if (entity.m_20096_()) {
            float pitch = (entity.m_6162_() ? 1.1f : 0.8f) + entity.m_217043_().m_188501_() * 0.4f;
            ctx.playSound(ADSoundEvents.LUSHSAUR_STEP.get(), 1.0f, pitch);
            if (!entity.m_6162_()) {
                ShakeUtil.shakeStep((Entity)entity, 10.0f);
            }
        }
    });
    public static final AnimationEventTrack<LushsaurEntity> ROAR_EFFECTS = AnimationEventTrack.of().addEvent(0.58f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_ROAR[0], (String)"jaw")).addEvent(0.625f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_ROAR[1], (String)"head2"));
    public static final AnimationEventTrack<LushsaurEntity> EAT_EFFECTS = AnimationEventTrack.of().addEvent(0.28f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EAT, (String)"jaw"));
    public static final AnimationEventTrack<LushsaurEntity> WALK_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, LushsaurEffects.stepEvent()).addEvent(0.5f, LushsaurEffects.stepEvent()).addEvent(1.0f, LushsaurEffects.stepEvent()).addEvent(1.5f, LushsaurEffects.stepEvent());
    public static final AnimationEventTrack<LushsaurEntity> RUN_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, LushsaurEffects.stepEvent()).addEvent(0.2f, LushsaurEffects.stepEvent()).addEvent(0.3f, LushsaurEffects.stepEvent()).addEvent(0.0f, ctx -> {
        LushsaurEntity entity = (LushsaurEntity)ctx.getInstance();
        EntityLocomotion locomotion = entity.getLocomotion();
        float runWeight = locomotion.getRunWeight(1.0f);
        if (entity.m_20096_() && runWeight > 0.5f || 1.0f - runWeight > 0.5f) {
            if (!entity.m_6162_()) {
                BlockState state = entity.m_9236_().m_8055_(entity.m_20183_().m_7495_());
                ctx.spawnBlockParticle(EMITTER_CHARGE_TRAIL[0], LOCATOR_GROUND3, state);
                ctx.spawnParticle(EMITTER_CHARGE_TRAIL[1], LOCATOR_GROUND3);
            }
            ctx.spawnParticle(EMITTER_CHARGE_TRAIL[2], LOCATOR_MOSS);
        }
    });

    private static AnimationEvent<LushsaurEntity> stepEvent() {
        return ctx -> {
            LushsaurEntity entity = (LushsaurEntity)ctx.getInstance();
            if (entity.m_20096_()) {
                float pitch = (entity.m_6162_() ? 1.1f : 0.8f) + entity.m_217043_().m_188501_() * 0.4f;
                ctx.playSound(ADSoundEvents.LUSHSAUR_STEP.get(), 1.0f, pitch);
                if (!entity.m_6162_()) {
                    ShakeUtil.shakeStep((Entity)entity, 5.0f);
                }
            }
        };
    }

    private static ParticleEmitterConfig[] register(String name, int startIndex, int count) {
        return ADParticleEmitters.register("lushsaur/" + name, startIndex, count);
    }

    private static ParticleEmitterConfig register(String name) {
        return ADParticleEmitters.register("lushsaur/" + name);
    }

    public static void init() {
    }
}

