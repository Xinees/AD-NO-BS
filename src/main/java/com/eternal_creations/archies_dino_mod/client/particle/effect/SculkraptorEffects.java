/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack
 *  com.astemir.astemirlib.re.client.particle.BedrockParticleManager
 *  com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig
 *  com.astemir.astemirlib.re.common.world.entity.EntityLocomotion
 *  com.astemir.astemirlib.re.core.math.Transform
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 */
package com.eternal_creations.archies_dino_mod.client.particle.effect;

import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent;
import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack;
import com.astemir.astemirlib.re.client.particle.BedrockParticleManager;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.astemir.astemirlib.re.common.world.entity.EntityLocomotion;
import com.astemir.astemirlib.re.core.math.Transform;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.SculkraptorSpitEntity;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class SculkraptorEffects {
    private static final String HEAD_LOCATOR = "head";
    private static final String PROJECTILE_LOCATOR = "locator";
    public static final ParticleEmitterConfig EMITTER_RANGE_ATTACK = SculkraptorEffects.register("sculkraptor_range_attack");
    public static final ParticleEmitterConfig SCULKRAPTOR_PROJECTILE_1 = SculkraptorEffects.register("projectile/sculkraptor_projectile_1");
    public static final ParticleEmitterConfig SCULKRAPTOR_PROJECTILE_2 = SculkraptorEffects.register("projectile/sculkraptor_projectile_2");
    public static final AnimationEventTrack<SculkraptorEntity> WALK_EFFECTS = AnimationEventTrack.of().addEvent(0.13f, SculkraptorEffects.stepEvent()).addEvent(0.54f, SculkraptorEffects.stepEvent());
    public static final AnimationEventTrack<SculkraptorEntity> RUN_EFFECTS = AnimationEventTrack.of().addEvent(0.08f, SculkraptorEffects.stepEvent()).addEvent(0.33f, SculkraptorEffects.stepEvent());
    public static final AnimationEventTrack<SculkraptorSpitEntity> PROJECTILE_EFFECTS = AnimationEventTrack.of().addEvent(0.01f, SculkraptorEffects.spitEvent());
    public static final AnimationEventTrack<SculkraptorEffects> RANGED_ATTACK_EFFECTS = AnimationEventTrack.of().addEvent(1.5f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RANGE_ATTACK, (String)"head"));

    private static AnimationEvent<SculkraptorSpitEntity> spitEvent() {
        return context -> {
            SculkraptorSpitEntity entity = (SculkraptorSpitEntity)((Object)((Object)context.getInstance()));
            Transform transform = context.getModelSystem().getLocatorTransform((Object)entity, PROJECTILE_LOCATOR);
            BedrockParticleManager.INSTANCE.createEmitterOnEntity(SCULKRAPTOR_PROJECTILE_1, (Entity)entity, transform, 1.0f).setAliveCondition(() -> ((SculkraptorSpitEntity)entity).m_6084_());
            BedrockParticleManager.INSTANCE.createEmitterOnEntity(SCULKRAPTOR_PROJECTILE_2, (Entity)entity, transform, 1.0f).setAliveCondition(() -> ((SculkraptorSpitEntity)entity).m_6084_());
        };
    }

    private static AnimationEvent<SculkraptorEntity> stepEvent() {
        return context -> {
            SculkraptorEntity entity = (SculkraptorEntity)context.getInstance();
            EntityLocomotion locomotion = entity.getLocomotion();
            float runWeight = locomotion.getRunWeight(1.0f);
            if (runWeight > 0.5f) {
                context.playSound(ADSoundEvents.SCULKRAPTOR_STEP.get(), SoundSource.NEUTRAL, 1.0f, 0.8f + entity.m_217043_().m_188501_() * 0.4f);
            }
        };
    }

    private static ParticleEmitterConfig[] register(String name, int startIndex, int count) {
        return ADParticleEmitters.register("sculkraptor/" + name, startIndex, count);
    }

    private static ParticleEmitterConfig register(String name) {
        return ADParticleEmitters.register("sculkraptor/" + name);
    }

    public static void init() {
    }
}

