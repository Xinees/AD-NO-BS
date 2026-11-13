/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack
 *  com.astemir.astemirlib.re.client.particle.BedrockParticleManager
 *  com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.client.particle.effect;

import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent;
import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack;
import com.astemir.astemirlib.re.client.particle.BedrockParticleManager;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitterType;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoralosaurEffects {
    private static final String NAME = "coralosaur";
    private static final ParticleEmitterConfig CORALOSAUR_BUBBLE = CoralosaurEffects.register("coralosaurus_breathable_bubbles");
    private static final ParticleEmitterConfig[] CORALOSAUR_ROAR = CoralosaurEffects.register("coralosaurus_roar", 0, 2);
    private static final ParticleEmitterConfig CORALOSAUR_SLEEP = CoralosaurEffects.register("coralosaurus_sleep");
    private static final ParticleEmitterConfig CORALOSAUR_SLEEP_HEAD = CoralosaurEffects.register("coralosaurus_sleep");
    private static final ParticleEmitterConfig[] CORALOSAUR_SWIM = CoralosaurEffects.register("coralosaurus_swim", 0, 3);
    private static final ParticleEmitterConfig[] CORALOSAUR_UPWATER = CoralosaurEffects.register("coralosaurus_upwater", 0, 4);
    private static final ParticleEmitterConfig CORALOSAUR_UPWATER_EVENT = CoralosaurEffects.register("coralosaurus_upwater_event");
    private static final ParticleEmitterConfig[] CORALOSAUR_WAKE = CoralosaurEffects.register("coralosaurus_wake", 1, 5);
    private static final String BUBBLES = "bubbles";
    private static final String HEAD_1 = "head";
    private static final String HEAD_2 = "head2";
    private static final String HEAD_3 = "head3";
    private static final String BODY_ADULT = "body_adult";
    private static final String BODY_BABY = "body_baby";
    private static final String TAIL = "tail";
    private static final String JAW = "jaw";
    private static final String LEG_LEFT_1 = "leg_left1";
    private static final String LEG_LEFT_2 = "leg_left2";
    private static final String LEG_RIGHT_1 = "leg_right1";
    private static final String LEG_RIGHT_2 = "leg_right2";
    private static final String UPWATER_1 = "upwater";
    private static final String UPWATER_2 = "upwater2";
    private static final String UPWATER_3 = "upwater3";
    public static final AnimationEventTrack<CoralosaurEntity> SLEEP_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SLEEP_HEAD, (String)"head")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SLEEP_HEAD, (String)"head2")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SLEEP_HEAD, (String)"head3")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SLEEP, (String)"body_adult")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SLEEP, (String)"body_baby"));
    public static final AnimationEventTrack<CoralosaurEntity> WAKE_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[2], (String)"body_adult")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[2], (String)"body_baby")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[0], (String)"body_adult")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[0], (String)"body_baby")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[1], (String)"body_adult")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[1], (String)"body_baby")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[1], (String)"tail")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[4], (String)"body_adult")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[4], (String)"body_baby")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[4], (String)"tail"));
    public static final AnimationEventTrack<CoralosaurEntity> SWIM_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SWIM[0], (String)"body_adult")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SWIM[0], (String)"body_baby")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SWIM[2], (String)"leg_left1")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SWIM[2], (String)"leg_left2")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SWIM[2], (String)"leg_right1")).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SWIM[2], (String)"leg_right2")).addEvent(0.0417f, CoralosaurEffects.waterSplashEvent()).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_UPWATER[3], (String)"leg_left1")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_UPWATER[3], (String)"leg_left2")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_UPWATER[3], (String)"leg_right1")).addEvent(0.0833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_UPWATER[3], (String)"leg_right2"));
    public static final AnimationEventTrack<CoralosaurEntity> SPECIAL_EFFECTS = AnimationEventTrack.of().addEvent(0.5417f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_ROAR[0], (String)"jaw")).addEvent(0.625f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_ROAR[1], (String)"leg_left1"));
    public static final AnimationEventTrack<CoralosaurEntity> CAMO_EFFECTS = AnimationEventTrack.of().addEvent(1.4167f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_WAKE[0], (String)"body_adult"));
    public static final AnimationEventTrack<CoralosaurEntity> DEATH_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)CORALOSAUR_SWIM[0], (String)"body_adult"));

    private static AnimationEvent<CoralosaurEntity> waterSplashEvent() {
        return ctx -> {
            CoralosaurEntity entity = (CoralosaurEntity)ctx.getInstance();
            Vec3 pos = entity.m_20182_().m_82520_(0.0, (double)entity.m_20206_(), 0.0);
            if (entity.m_9236_().m_46801_(entity.m_20183_()) && !entity.m_9236_().m_46801_(BlockPos.m_274446_((Position)pos))) {
                ctx.spawnParticle(CORALOSAUR_UPWATER[0], UPWATER_1);
                ctx.spawnParticle(CORALOSAUR_UPWATER[1], UPWATER_1);
                ctx.spawnParticle(CORALOSAUR_UPWATER[2], UPWATER_1);
                ctx.spawnParticle(CORALOSAUR_UPWATER[0], UPWATER_2);
                ctx.spawnParticle(CORALOSAUR_UPWATER[1], UPWATER_2);
                ctx.spawnParticle(CORALOSAUR_UPWATER[2], UPWATER_2);
                ctx.spawnParticle(CORALOSAUR_UPWATER[0], UPWATER_3);
                ctx.spawnParticle(CORALOSAUR_UPWATER[2], UPWATER_3);
            }
        };
    }

    private static ParticleEmitterConfig[] register(String name, int start, int count) {
        return ADParticleEmitters.register("coralosaur/" + name, start, count);
    }

    private static ParticleEmitterConfig register(String name) {
        return ADParticleEmitters.register("coralosaur/" + name);
    }

    public static ParticleEmitterConfig register(ResourceSupplier<ParticleEmitterType> type, String path) {
        return BedrockParticleManager.INSTANCE.registerParticle(type.get(), Constants.prefix("particles/coralosaur/" + path + ".particle.json"));
    }

    public static void init() {
    }
}

