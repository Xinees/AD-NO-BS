/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack
 *  com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig
 *  com.astemir.astemirlib.re.client.shader.post.PostProcessEngine
 *  com.astemir.astemirlib.re.client.shader.post.pass.effect.light.ColoredLight
 *  com.astemir.astemirlib.re.client.shader.post.pass.effect.light.ColoredLight$Properties
 *  com.astemir.astemirlib.re.client.shader.post.pass.effect.light.ColoredLight$Type
 *  com.astemir.astemirlib.re.client.shader.post.pass.effect.shockwave.Shockwave
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.client.particle.effect;

import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent;
import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.astemir.astemirlib.re.client.shader.post.PostProcessEngine;
import com.astemir.astemirlib.re.client.shader.post.pass.effect.light.ColoredLight;
import com.astemir.astemirlib.re.client.shader.post.pass.effect.shockwave.Shockwave;
import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.client.util.ShakeUtil;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SculksaurEffects {
    private static final ParticleEmitterConfig[] EMITTER_IDLE = SculksaurEffects.register("idle/sculksaur_idle", 0, 2);
    private static final ParticleEmitterConfig[] EMITTER_WALK = SculksaurEffects.register("walk/sculksaur_walk", 1, 2);
    private static final ParticleEmitterConfig[] EMITTER_RUN = SculksaurEffects.register("run/sculksaur_run", 1, 2);
    private static final ParticleEmitterConfig[] EMITTER_SPAWN = SculksaurEffects.register("spawn/sculksaur_spawn", 0, 3);
    private static final ParticleEmitterConfig[] EMITTER_DEATH = SculksaurEffects.register("death/sculksaur_death", 1, 4);
    private static final ParticleEmitterConfig[] EMITTER_LISTEN = SculksaurEffects.register("listen/sculksaur_listen", 0, 3);
    private static final ParticleEmitterConfig[] EMITTER_ROAR = SculksaurEffects.register("roar/sculksaur_roar", 0, 3);
    public static final ParticleEmitterConfig[] EMITTER_EXPLOSION = SculksaurEffects.register("attacks/explosion/sculksaur_explosion", 2, 5);
    private static final ParticleEmitterConfig EMITTER_DEATH_GROUND = SculksaurEffects.register("death/sculksaur_death_ground");
    private static final ParticleEmitterConfig EMITTER_EXPLOSION_GROUND = SculksaurEffects.register("attacks/explosion/sculksaur_explosion_ground");
    private static final ParticleEmitterConfig EMITTER_EXPLOSION_ROAR = SculksaurEffects.register("attacks/explosion/sculksaur_explosion_roar");
    private static final ParticleEmitterConfig EMITTER_TAIL_SPLASH = SculksaurEffects.register("attacks/tail/sculksaur_tail");
    private static final String LOCATOR_GROUND = "ground";
    private static final String LOCATOR_BODY = "body";
    private static final String LOCATOR_JAW = "jaw";
    private static final String LOCATOR_TAIL = "tail";
    private static final String LOCATOR_ABDOMEN = "abdomen";
    private static final String LOCATOR_FOOT_L = "foot_left";
    private static final String LOCATOR_FOOT_L2 = "foot_left2";
    private static final String LOCATOR_FOOT_R = "foot_right";
    private static final String LOCATOR_FOOT_R2 = "foot_right2";
    private static final String LOCATOR_TENDRIL_1 = "h_tendril1";
    private static final String LOCATOR_TENDRIL_2 = "h_tendril2";
    public static final AnimationEventTrack<SculksaurEntity> WALK_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, SculksaurEffects.stepEvent(null, EMITTER_WALK[1], "foot_right", null, 0.2f)).addEvent(1.0f, SculksaurEffects.stepEvent(null, EMITTER_WALK[1], "foot_left", null, 0.2f));
    public static final AnimationEventTrack<SculksaurEntity> RUN_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, SculksaurEffects.stepEvent(EMITTER_RUN[0], EMITTER_RUN[1], "foot_left", "foot_left2", 0.4f)).addEvent(0.44f, SculksaurEffects.stepEvent(EMITTER_RUN[0], EMITTER_RUN[1], "foot_right", "foot_right2", 0.4f));
    public static final AnimationEventTrack<SculksaurEntity> TAIL_EFFECTS = AnimationEventTrack.of().addEvent(0.33f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_TAIL_SPLASH, (String)"tail"));
    public static final AnimationEventTrack<SculksaurEntity> EXPLOSION_EFFECTS = AnimationEventTrack.of().addEvent(1.12f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"foot_right").and(context -> ShakeUtil.shakeStep((Entity)context.getInstance(), 10.0f))).addEvent(1.56f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"foot_left").and(context -> ShakeUtil.shakeStep((Entity)context.getInstance(), 10.0f))).addEvent(1.96f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"foot_right").and(context -> ShakeUtil.shakeStep((Entity)context.getInstance(), 10.0f))).addEvent(2.32f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"foot_left").and(context -> ShakeUtil.shakeStep((Entity)context.getInstance(), 10.0f))).addEvent(2.64f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"foot_right").and(context -> ShakeUtil.shakeStep((Entity)context.getInstance(), 10.0f))).addEvent(2.92f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EXPLOSION[0], (String)"abdomen")).addEvent(3.32f, SculksaurEffects.wartsParticleEvent()).addEvent(3.68f, SculksaurEffects.wartsParticleEvent()).addEvent(4.16f, SculksaurEffects.tendrilsVibrationEvent()).addEvent(4.52f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EXPLOSION_ROAR, (String)"jaw")).addEvent(4.6f, ctx -> {
        ctx.spawnParticle(EMITTER_RUN[1], LOCATOR_FOOT_L);
        ctx.spawnParticle(EMITTER_ROAR[2], LOCATOR_FOOT_L2);
    }).addEvent(4.64f, SculksaurEffects.explosionGroundEvent()).addEvent(4.68f, SculksaurEffects.explosionFinalEvent()).addEvent(5.12f, SculksaurEffects.tendrilsVibrationEvent());
    public static final AnimationEventTrack<SculksaurEntity> SPAWN_EFFECTS = AnimationEventTrack.of().addEvent(0.1f, context -> ShakeUtil.shakeRoar((Entity)context.getInstance(), 15.0f, 0.85f, 15.0f)).addEvent(0.36f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_SPAWN[1], (String)"ground")).addEvent(1.48f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_SPAWN[0], (String)"ground")).addEvent(1.8f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_SPAWN[1], (String)"ground")).addEvent(4.44f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_SPAWN[2], (String)"ground")).addEvent(5.04f, SculksaurEffects.spawnMultipleGroundParticle(EMITTER_SPAWN[1], 3)).addEvent(7.76f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_SPAWN[2], (String)"ground")).addEvent(10.36f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_SPAWN[0], (String)"foot_left2"));
    public static final AnimationEventTrack<SculksaurEntity> DEATH_EFFECTS = AnimationEventTrack.of().addEvent(1.92f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_DEATH[0], (String)"ground")).addEvent(2.0f, ctx -> {
        SculksaurEntity entity = (SculksaurEntity)ctx.getInstance();
        BlockState stateBelow = entity.m_20075_();
        if (entity.m_20096_() && !stateBelow.m_60795_()) {
            ShakeUtil.shakeTailSlam((LivingEntity)entity, 1.4f, 12.0f);
        }
    }).addEvent(2.4f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_DEATH[1], (String)"body")).addEvent(2.76f, ctx -> {
        ctx.spawnParticle(EMITTER_DEATH[2], LOCATOR_GROUND);
        ctx.spawnParticle(EMITTER_DEATH[3], LOCATOR_GROUND);
    }).addEvent(4.32f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_DEATH_GROUND, (String)"ground"));
    public static final AnimationEventTrack<SculksaurEntity> ROAR_EFFECTS = AnimationEventTrack.of().addEvent(0.64f, SculksaurEffects.roarStartEvent()).addEvent(0.72f, SculksaurEffects.roarJawEvent()).addEvent(0.92f, SculksaurEffects.tendrilsVibrationEvent()).addEvent(2.76f, SculksaurEffects.tendrilsVibrationEvent()).addEvent(4.32f, SculksaurEffects.tendrilsVibrationEvent());
    public static final AnimationEventTrack<SculksaurEntity> LISTEN_EFFECTS = AnimationEventTrack.of().addEvent(0.84f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_LISTEN[1], (String)"h_tendril1")).addEvent(1.32f, SculksaurEffects.tendrilsVibrationEvent()).addEvent(1.84f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_LISTEN[1], (String)"h_tendril2")).addEvent(3.0f, SculksaurEffects.tendrilsVibrationEvent()).addEvent(3.52f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_LISTEN[1], (String)"h_tendril2")).addEvent(3.92f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_LISTEN[1], (String)"h_tendril1")).addEvent(4.56f, SculksaurEffects.tendrilsVibrationEvent());
    public static final AnimationEventTrack<SculksaurEntity> TENDRILS_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, SculksaurEffects.tendrilsSenseEvent());

    private static AnimationEvent<SculksaurEntity> spawnMultipleGroundParticle(ParticleEmitterConfig config, int count) {
        return context -> {
            for (int i = 0; i < count; ++i) {
                context.spawnGroundParticle(config, LOCATOR_GROUND);
            }
        };
    }

    private static AnimationEvent<SculksaurEntity> stepEvent(@Nullable ParticleEmitterConfig p1, ParticleEmitterConfig p2, String foot, @Nullable String foot2, float shake) {
        return context -> {
            SculksaurEntity entity = (SculksaurEntity)context.getInstance();
            ShakeUtil.shakeStep((Entity)entity, 20.0f);
            context.playSound(ADSoundEvents.SCULKSAUR_STEP.get(), SoundSource.NEUTRAL, 1.0f, 0.8f + entity.m_217043_().m_188501_() * 0.4f);
            if (p1 != null && foot2 != null) {
                context.spawnBlockParticle(p1, foot2, entity.m_20075_());
            }
            context.spawnParticle(p2, foot);
        };
    }

    private static AnimationEvent<SculksaurEntity> explosionGroundEvent() {
        return context -> {
            context.spawnParticle(EMITTER_EXPLOSION[1], LOCATOR_GROUND);
            context.spawnParticle(EMITTER_EXPLOSION[2], LOCATOR_GROUND);
            context.spawnParticle(EMITTER_EXPLOSION[3], LOCATOR_GROUND);
            context.spawnParticle(EMITTER_EXPLOSION_GROUND, LOCATOR_GROUND);
        };
    }

    private static AnimationEvent<SculksaurEntity> explosionFinalEvent() {
        return context -> {
            if (PostProcessEngine.INSTANCE.isEnabledInConfig()) {
                ColoredLight.of((ColoredLight.Properties)new ColoredLight.Properties(ColoredLight.Type.POINT, PositionSupplier.ofEntity((Entity)((Entity)context.getInstance()), (boolean)true)).color(0.0f, 0.5f, 0.5f).lifetime(40).intensity(20.0f).radius(20.0f).fadeOut()).spawn();
                Shockwave.of((PositionSupplier)PositionSupplier.ofEntity((Entity)((Entity)context.getInstance()), (boolean)true), (float)20.0f, (float)3.5f, (float)0.8f, (int)40).spawn();
            } else {
                context.spawnParticle(EMITTER_EXPLOSION[4], LOCATOR_JAW);
            }
            ShakeUtil.shakeExplosion((Entity)context.getInstance(), 1.0f, 20.0f);
        };
    }

    private static AnimationEvent<SculksaurEntity> wartsParticleEvent() {
        String[] warts = new String[]{"wart11", "wart9", "wart12", "wart5", "wart3", "wart4", "wart6", "wart10"};
        return context -> {
            for (int i = 0; i < warts.length; ++i) {
                ParticleEmitterConfig cfg = i % 2 == 0 ? EMITTER_IDLE[1] : EMITTER_IDLE[0];
                context.spawnParticle(cfg, warts[i]);
            }
        };
    }

    private static AnimationEvent<SculksaurEntity> tendrilsVibrationEvent() {
        return context -> {
            context.spawnParticle(EMITTER_LISTEN[0], LOCATOR_TENDRIL_1);
            context.spawnParticle(EMITTER_LISTEN[0], LOCATOR_TENDRIL_2);
        };
    }

    private static AnimationEvent<SculksaurEntity> roarStartEvent() {
        return context -> {
            context.spawnParticle(EMITTER_RUN[0], LOCATOR_FOOT_L);
            context.spawnParticle(EMITTER_ROAR[2], LOCATOR_FOOT_L);
        };
    }

    private static AnimationEvent<SculksaurEntity> roarJawEvent() {
        return context -> {
            context.spawnParticle(EMITTER_ROAR[0], LOCATOR_JAW);
            context.spawnParticle(EMITTER_ROAR[1], LOCATOR_JAW);
            ShakeUtil.shakeRoar((Entity)context.getInstance(), 5.0f, 10.0f, 20.0f);
        };
    }

    private static AnimationEvent<SculksaurEntity> tendrilsSenseEvent() {
        return context -> {
            context.spawnParticle(EMITTER_LISTEN[2], LOCATOR_TENDRIL_1);
            context.spawnParticle(EMITTER_LISTEN[2], LOCATOR_TENDRIL_2);
        };
    }

    private static ParticleEmitterConfig[] register(String name, int startIndex, int count) {
        return ADParticleEmitters.register("sculksaur/" + name, startIndex, count);
    }

    private static ParticleEmitterConfig register(String name) {
        return ADParticleEmitters.register("sculksaur/" + name);
    }

    public static void init() {
    }
}

