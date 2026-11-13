/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent
 *  com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack
 *  com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig
 *  net.minecraft.world.entity.Entity
 */
package com.eternal_creations.archies_dino_mod.client.particle.effect;

import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent;
import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.client.util.ShakeUtil;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.world.entity.Entity;

public class ChorusoatlEffects {
    private static final ParticleEmitterConfig[] EMITTER_SCREAM = ChorusoatlEffects.register("scream/chorusoatl_scream", 1, 4);
    private static final ParticleEmitterConfig EMITTER_BITE = ChorusoatlEffects.register("chorusoatl_bite");
    private static final ParticleEmitterConfig EMITTER_LAND = ChorusoatlEffects.register("chorusoatl_land");
    private static final ParticleEmitterConfig EMITTER_STOMP = ChorusoatlEffects.register("chorusoatl_stomp");
    private static final ParticleEmitterConfig EMITTER_TAKE_OFF = ChorusoatlEffects.register("chorusoatl_take_off_1");
    private static final String LOCATOR_CLAW1 = "claw1";
    private static final String LOCATOR_CLAW2 = "claw2";
    private static final String LOCATOR_HEAD = "head";
    private static final String LOCATOR_HEAD2 = "head2";
    private static final String LOCATOR_BITE = "bite";
    private static final String LOCATOR_LAND = "land";
    private static final String[] FOOT_BONES = new String[]{"claw1", "claw2", "claw2", "foot2"};
    public static final AnimationEventTrack<ChorusoatlEntity> TAKEOFF_EFFECTS = AnimationEventTrack.of(Entity::m_20096_).addEvent(0.52f, AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_TAKE_OFF, (String[])FOOT_BONES).and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_STOMP, (String[])FOOT_BONES)).when(Entity::m_20096_));
    public static final AnimationEventTrack<ChorusoatlEntity> BITE_EFFECTS = AnimationEventTrack.of().addEvent(1.2f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_BITE, (String)"bite"));
    public static final AnimationEventTrack<ChorusoatlEntity> LAND_EFFECTS = AnimationEventTrack.of(AbstractComplexMob::isAdult).addEvent(0.2f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_LAND, (String)"land"));
    public static final AnimationEventTrack<ChorusoatlEntity> SCREAM_EFFECTS = AnimationEventTrack.of(AbstractComplexMob::isAdult).addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_SCREAM[3], (String)"head2")).addEvent(0.9f, context -> ShakeUtil.shakeUltrasonicScreech((Entity)context.getInstance(), 4.5f, 2.0f, 20.0f)).addEvent(1.16f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_SCREAM[0], (String)"head").and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_SCREAM[1], (String)"head2")).and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_SCREAM[2], (String)"head2")));
    public static final AnimationEventTrack<ChorusoatlEntity> WALK_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_STOMP, (String)"claw2").and(AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_STEP_LIGHT)).when(AbstractComplexMob::isAdult)).addEvent(0.36f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_STEP_HEAVY)).addEvent(0.88f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_STEP_LIGHT)).addEvent(1.04f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_STOMP, (String)"claw1")).addEvent(1.36f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_STEP_HEAVY));
    public static final AnimationEventTrack<ChorusoatlEntity> GLIDE_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_GLIDE, (float)2.0f)).addEvent(2.56f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_GLIDE, (float)2.0f));
    public static final AnimationEventTrack<ChorusoatlEntity> FLY_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_GLIDE, (float)2.0f).and(AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_FLAP_UP, (float)2.0f))).addEvent(0.5f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_FLAP_DOWN, (float)2.0f));
    public static final AnimationEventTrack<ChorusoatlEntity> FLY_IDLE_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_FLAP_UP, (float)2.0f)).addEvent(0.5f, AnimationEvent.playSound(ADSoundEvents.CHORUSOATL_FLAP_DOWN, (float)2.0f));

    private static ParticleEmitterConfig[] register(String name, int startIndex, int count) {
        return ADParticleEmitters.register("chorusoatl/" + name, startIndex, count);
    }

    private static ParticleEmitterConfig register(String name) {
        return ADParticleEmitters.register("chorusoatl/" + name);
    }

    public static void init() {
    }
}

