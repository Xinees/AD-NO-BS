/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.util.Unit
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.ActionBehaviour;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorSonicBoom
extends ActionBehaviour<SculkraptorEntity> {
    private static final int DISTANCE_XZ = 15;
    private static final int DISTANCE_Y = 20;
    private static final double KNOCKBACK_VERTICAL = 0.5;
    private static final double KNOCKBACK_HORIZONTAL = 2.5;
    public static final int COOLDOWN = 120;
    private static final int TICKS_BEFORE_PLAYING_SOUND = Mth.m_14165_((double)17.6);
    public static final float DURATION = 2.25f;
    private static final int DURATION_IN_TICKS = Mth.m_14167_((float)45.0f);

    public SculkraptorSonicBoom() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(ADMemoryModuleTypes.IS_READY_TO_SCREAM.get(), (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.f_217775_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_217776_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_217777_, (Object)MemoryStatus.REGISTERED), e -> e.actionAttackSonicBoom);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, SculkraptorEntity sculkraptor) {
        LivingEntity target = sculkraptor.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElse(null);
        if (target == null) {
            return false;
        }
        if (!sculkraptor.isNoActionActive()) {
            return false;
        }
        for (SculkraptorEntity other : level.m_45976_(SculkraptorEntity.class, sculkraptor.m_20191_().m_82400_(32.0))) {
            if (other.isReadyToScream()) continue;
            Brain<SculkraptorEntity> brain = other.m_6274_();
            long left = brain.m_147341_(MemoryModuleType.f_217775_);
            if (left > 0L) {
                brain.m_21882_(MemoryModuleType.f_217775_, (Object)Unit.INSTANCE, Math.min(1L, left - 2L));
            }
            return false;
        }
        return true;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, SculkraptorEntity sculksaur, long gameTime) {
        return true;
    }

    @Override
    protected void start(ServerLevel level, SculkraptorEntity sculksaur, long gameTime) {
        sculksaur.m_6274_().m_21882_(MemoryModuleType.f_26373_, (Object)true, (long)DURATION_IN_TICKS);
        sculksaur.m_6274_().m_21882_(MemoryModuleType.f_217777_, (Object)Unit.INSTANCE, (long)TICKS_BEFORE_PLAYING_SOUND);
        level.m_7605_((Entity)sculksaur, (byte)62);
    }

    @Override
    protected void tick(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        sculkraptor.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(sculkraptor::stareAtTarget);
        if (!sculkraptor.m_6274_().m_21874_(MemoryModuleType.f_217777_) && !sculkraptor.m_6274_().m_21874_(MemoryModuleType.f_217776_)) {
            sculkraptor.m_6274_().m_21882_(MemoryModuleType.f_217776_, (Object)Unit.INSTANCE, (long)DURATION_IN_TICKS);
            sculkraptor.m_6274_().m_21952_(MemoryModuleType.f_26372_).filter(sculkraptor::canTargetEntity).filter(living -> sculkraptor.m_216992_((Entity)living, 15.0, 20.0)).ifPresent(target -> {
                Vec3 vec3 = sculkraptor.m_20182_().m_82520_(0.0, (double)1.6f, 0.0);
                Vec3 vec31 = target.m_146892_().m_82546_(vec3);
                Vec3 vec32 = vec31.m_82541_();
                for (int i = 1; i < Mth.m_14107_((double)vec32.m_82553_()) + 7; ++i) {
                    Vec3 vec34 = vec3.m_82549_(vec32.m_82490_((double)i));
                    level.m_8767_((ParticleOptions)ParticleTypes.f_235902_, vec34.f_82479_, vec34.f_82480_, vec34.f_82481_, 1, 0.0, 0.0, 0.0, 0.0);
                }
                if (target.m_6469_(level.m_269111_().m_269285_((Entity)sculkraptor), Math.max(EntityUtils.multiplyDamage((Mob)sculkraptor, target, 0.33333334f), 1.0f))) {
                    target.m_147207_(new MobEffectInstance((MobEffect)ADMobEffects.DAZED.get(), 60, 0, false, false, true), (Entity)sculkraptor);
                }
                double d1 = 0.5 * (1.0 - target.m_21133_(Attributes.f_22278_));
                double d0 = 2.5 * (1.0 - target.m_21133_(Attributes.f_22278_));
                target.m_5997_(vec32.m_7096_() * d0, vec32.m_7098_() * d1, vec32.m_7094_() * d0);
            });
        }
    }

    @Override
    protected void stop(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        SculkraptorSonicBoom.setCooldown(sculkraptor, 120);
    }

    public static void setCooldown(SculkraptorEntity sculkraptor, int cooldown) {
        sculkraptor.m_6274_().m_21882_(MemoryModuleType.f_217775_, (Object)Unit.INSTANCE, (long)(cooldown + sculkraptor.m_217043_().m_188503_(cooldown)));
    }
}

