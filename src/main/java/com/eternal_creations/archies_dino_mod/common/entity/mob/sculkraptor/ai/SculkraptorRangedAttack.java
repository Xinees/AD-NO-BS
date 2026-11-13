/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.behavior.BehaviorUtils
 *  net.minecraft.world.entity.ai.behavior.EntityTracker
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.ActionBehaviour;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorSonicBoom;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorRangedAttack
extends ActionBehaviour<SculkraptorEntity> {
    public SculkraptorRangedAttack() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of((Object)MemoryModuleType.f_26371_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_217775_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_PRESENT), e -> e.actionAttackRanged);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, SculkraptorEntity sculkraptor) {
        LivingEntity target = SculkraptorRangedAttack.getAttackTarget(sculkraptor);
        if (!sculkraptor.isNoActionActive()) {
            return false;
        }
        return BehaviorUtils.m_22667_((LivingEntity)sculkraptor, (LivingEntity)target) && SculkraptorRangedAttack.isWithinAttackRange(sculkraptor, target);
    }

    public static boolean isWithinAttackRange(SculkraptorEntity sculkraptor, LivingEntity target) {
        return sculkraptor.m_19950_((Entity)target, 15.0) && !sculkraptor.m_19950_((Entity)target, 3.0);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        return sculkraptor.m_6274_().m_21874_(MemoryModuleType.f_26372_) && this.checkExtraStartConditions(level, sculkraptor);
    }

    @Override
    protected void tick(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        LivingEntity livingentity = SculkraptorRangedAttack.getAttackTarget(sculkraptor);
        this.lookAtTarget(sculkraptor, livingentity);
    }

    private void lookAtTarget(SculkraptorEntity sculkraptor, LivingEntity target) {
        sculkraptor.m_6274_().m_21879_(MemoryModuleType.f_26371_, (Object)new EntityTracker((Entity)target, true));
    }

    private static LivingEntity getAttackTarget(SculkraptorEntity sculkraptor) {
        return (LivingEntity)sculkraptor.m_6274_().m_21952_(MemoryModuleType.f_26372_).get();
    }

    @Override
    protected void stop(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        SculkraptorSonicBoom.setCooldown(sculkraptor, 120);
    }
}

