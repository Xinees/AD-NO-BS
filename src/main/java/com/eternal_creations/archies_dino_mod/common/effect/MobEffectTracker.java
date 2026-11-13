/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitter
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.effect;

import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitter;
import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MobEffectTracker
extends MobEffect {
    private static final int INTERVAL = 80;

    protected MobEffectTracker() {
        super(MobEffectCategory.HARMFUL, -16777216);
    }

    public void m_6742_(LivingEntity living, int amplifier) {
        if (living.f_19797_ % 80 == 0) {
            Level level = living.m_9236_();
            if (level instanceof ServerLevel) {
                ServerLevel level2 = (ServerLevel)level;
                level2.m_220400_((Entity)living, ADGameEvents.PUS_ARROW_AGGRO.get(), living.m_20182_());
                level2.m_6263_(null, living.m_20185_(), living.m_20186_(), living.m_20189_(), ADSoundEvents.SCULKRAPTOR_SCULK_TRACKER.get(), living.m_5720_(), 3.0f, living.m_217043_().m_188501_() * 0.25f + 0.75f);
            } else {
                ADParticles.SCULKRAPTOR_PROJECTILE_IMPACT_0.get().spawnClient(living.m_9236_(), PositionSupplier.ofEntity((Entity)living, (boolean)true), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
            }
        }
    }

    public boolean m_6584_(int duration, int amplifier) {
        return true;
    }
}

