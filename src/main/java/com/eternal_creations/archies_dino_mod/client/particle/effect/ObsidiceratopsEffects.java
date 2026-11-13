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
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.client.particle.effect;

import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEvent;
import com.astemir.astemirlib.re.client.model.core.animation.event.AnimationEventTrack;
import com.astemir.astemirlib.re.client.particle.ParticleEmitterConfig;
import com.eternal_creations.archies_dino_mod.client.particle.ADParticleEmitters;
import com.eternal_creations.archies_dino_mod.client.util.ShakeUtil;
import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ObsidiceratopsEffects {
    private static final ParticleEmitterConfig[] EMITTER_AGRO = ObsidiceratopsEffects.register("agro/obsidiceratops_agro", 1, 4);
    private static final ParticleEmitterConfig EMITTER_DRINK = ObsidiceratopsEffects.register("drink/obsidiceratops_drink");
    private static final ParticleEmitterConfig[] EMITTER_EAT = ObsidiceratopsEffects.register("eat/obsidiceratops_eat", 0, 3);
    private static final ParticleEmitterConfig[] EMITTER_RUN = ObsidiceratopsEffects.register("run/obsidiceratops_run", 0, 2);
    private static final ParticleEmitterConfig EMITTER_ATTACK = ObsidiceratopsEffects.register("obsidiceratops_attack");
    private static final String LOCATOR_NOSE1 = "nose1";
    private static final String LOCATOR_NOSE2 = "nose2";
    private static final String LOCATOR_JAW = "jaw";
    private static final String LOCATOR_LEFT_LEG = "leftLeg";
    private static final String LOCATOR_RIGHT_LEG = "rightLeg";
    private static final String LOCATOR_LEFT_LEG2 = "leftLeg2";
    private static final String LOCATOR_RIGHT_LEG2 = "rightLeg2";
    public static final AnimationEventTrack<ObsidiceratopsEntity> ATTACK_HIT_EFFECTS = AnimationEventTrack.of().addEvent(0.875f, ObsidiceratopsEffects.noseSteamEvent());
    public static final AnimationEventTrack<ObsidiceratopsEntity> ATTACK_TAIL_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, ObsidiceratopsEffects.noseSteamEvent()).addEvent(0.5f, ObsidiceratopsEffects.stepEvent()).addEvent(0.625f, ObsidiceratopsEffects.groundStrikeEvent()).addEvent(1.0f, ObsidiceratopsEffects.stepEvent()).addEvent(1.1667f, ObsidiceratopsEffects.groundStrikeEvent());
    public static final AnimationEventTrack<ObsidiceratopsEntity> EAT_EFFECTS = AnimationEventTrack.of().addEvent(0.9167f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EAT[0], (String)"jaw").and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_DRINK, (String)"jaw"))).addEvent(1.375f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EAT[0], (String)"jaw").and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_DRINK, (String)"jaw"))).addEvent(2.125f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EAT[1], (String)"jaw").and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_EAT[2], (String)"jaw")));
    public static final AnimationEventTrack<ObsidiceratopsEntity> WARN_EFFECTS = AnimationEventTrack.of().addEvent(0.5833f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_AGRO[1], (String)"jaw")).addEvent(2.0417f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_AGRO[0], (String)"leftLeg").and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_AGRO[2], (String)"leftLeg")).and(ObsidiceratopsEffects.noseSteamEvent())).addEvent(2.5417f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_AGRO[0], (String)"leftLeg").and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_AGRO[2], (String)"leftLeg")).and(ObsidiceratopsEffects.noseSteamEvent()));
    public static final AnimationEventTrack<ObsidiceratopsEntity> DRINK1_EFFECTS = AnimationEventTrack.of().addEvent(2.6667f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_DRINK, (String)"jaw"));
    public static final AnimationEventTrack<ObsidiceratopsEntity> DRINK2_EFFECTS = AnimationEventTrack.of().addEvent(6.5417f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_DRINK, (String)"jaw"));
    public static final AnimationEventTrack<ObsidiceratopsEntity> WALK_EFFECTS = AnimationEventTrack.of(entity -> entity.m_20096_() && !entity.m_20075_().m_60713_(Blocks.f_49991_)).addEvent(0.125f, ObsidiceratopsEffects.stepEvent().and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"leftLeg"))).addEvent(0.5417f, ObsidiceratopsEffects.stepEvent().and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"rightLeg2"))).addEvent(1.125f, ObsidiceratopsEffects.stepEvent().and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"rightLeg"))).addEvent(2.125f, ObsidiceratopsEffects.stepEvent().and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"leftLeg2")));
    public static final AnimationEventTrack<ObsidiceratopsEntity> DEATH_EFFECTS = AnimationEventTrack.of().addEvent(1.0f, ctx -> {
        ObsidiceratopsEntity entity = (ObsidiceratopsEntity)ctx.getInstance();
        BlockState stateBelow = entity.m_20075_();
        if (entity.m_20096_() && !stateBelow.m_60795_() && !entity.m_6162_()) {
            ShakeUtil.shakeTailSlam((LivingEntity)entity, 1.0f, 6.0f);
        }
    });
    public static final AnimationEventTrack<ObsidiceratopsEntity> RUN_EFFECTS = AnimationEventTrack.of(entity -> entity.m_20096_() && !entity.m_20075_().m_60713_(Blocks.f_49991_)).addEvent(0.0417f, AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"leftLeg").and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"leftLeg2")).and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"rightLeg")).and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_RUN[0], (String)"rightLeg2"))).addEvent(0.0417f, ObsidiceratopsEffects.stepEvent().and(AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"leftLeg")).and(AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"leftLeg2")).and(AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"rightLeg")).and(AnimationEvent.spawnGroundParticle((ParticleEmitterConfig)EMITTER_RUN[1], (String)"rightLeg2"))).addEvent(0.125f, ObsidiceratopsEffects.noseSteamEvent()).addEvent(0.1667f, ObsidiceratopsEffects.stepEvent()).addEvent(0.2917f, ObsidiceratopsEffects.stepEvent()).addEvent(0.5833f, ObsidiceratopsEffects.stepEvent());
    public static final AnimationEventTrack<ObsidiceratopsEntity> CHARGE_EFFECTS = AnimationEventTrack.of().addEvent(0.0f, ObsidiceratopsEffects.stepEvent()).addEvent(0.125f, ObsidiceratopsEffects.noseSteamEvent()).addEvent(0.2f, ObsidiceratopsEffects.stepEvent()).addEvent(0.3f, ObsidiceratopsEffects.stepEvent());

    private static AnimationEvent<ObsidiceratopsEntity> noseSteamEvent() {
        return AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_AGRO[3], (String)LOCATOR_NOSE1).and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_AGRO[3], (String)LOCATOR_NOSE2));
    }

    private static AnimationEvent<ObsidiceratopsEntity> groundStrikeEvent() {
        return AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_ATTACK, (String)LOCATOR_LEFT_LEG).and(AnimationEvent.spawnParticle((ParticleEmitterConfig)EMITTER_ATTACK, (String)LOCATOR_RIGHT_LEG));
    }

    private static AnimationEvent<ObsidiceratopsEntity> stepEvent() {
        return ctx -> {
            ObsidiceratopsEntity entity = (ObsidiceratopsEntity)ctx.getInstance();
            if (entity.m_20096_() && !entity.m_20075_().m_60713_(Blocks.f_49991_)) {
                float basePitch;
                float f = basePitch = entity.m_6162_() ? 1.1f : 0.8f;
                if (!entity.m_6162_()) {
                    ShakeUtil.shakeStep((Entity)entity, 6.0f);
                }
                ctx.playSound(ADSoundEvents.OBSIDICERATOPS_STEP.get(), SoundSource.NEUTRAL, 1.0f, basePitch + entity.m_9236_().m_213780_().m_188501_() * 0.4f);
            }
        };
    }

    private static ParticleEmitterConfig[] register(String name, int startIndex, int count) {
        return ADParticleEmitters.register("obsidiceratops/" + name, startIndex, count);
    }

    private static ParticleEmitterConfig register(String name) {
        return ADParticleEmitters.register("obsidiceratops/" + name);
    }

    public static void init() {
    }
}

