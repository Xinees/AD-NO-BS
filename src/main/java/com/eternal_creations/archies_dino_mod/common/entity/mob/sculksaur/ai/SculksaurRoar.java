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
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.behavior.BehaviorUtils
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.ActionBehaviour;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurRoar
extends ActionBehaviour<SculksaurEntity> {
    private static final int ROAR_ANGER_INCREASE = 20;

    public SculksaurRoar() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of((Object)MemoryModuleType.f_217782_, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_ABSENT), e -> e.actionRoar);
    }

    @Override
    protected void start(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        Brain<SculksaurEntity> brain = sculksaur.m_6274_();
        brain.m_21936_(MemoryModuleType.f_26370_);
        LivingEntity livingentity = (LivingEntity)brain.m_21952_(MemoryModuleType.f_217782_).get();
        BehaviorUtils.m_22595_((LivingEntity)sculksaur, (LivingEntity)livingentity);
        sculksaur.m_20124_(Pose.ROARING);
        sculksaur.increaseAngerAt((Entity)livingentity, 20, false);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        return true;
    }

    @Override
    protected void stop(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        if (sculksaur.m_217003_(Pose.ROARING)) {
            sculksaur.m_20124_(Pose.STANDING);
        }
        sculksaur.m_6274_().m_21952_(MemoryModuleType.f_217782_).ifPresent(sculksaur::m_6710_);
        sculksaur.m_6274_().m_21936_(MemoryModuleType.f_217782_);
        sculksaur.setEnraged(true);
    }
}

