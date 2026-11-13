/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Pose
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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurEmerging
extends ActionBehaviour<SculksaurEntity> {
    public SculksaurEmerging() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of((Object)MemoryModuleType.f_217786_, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.f_26370_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_26371_, (Object)MemoryStatus.REGISTERED), e -> e.actionSpawn);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        return true;
    }

    @Override
    protected void start(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        sculksaur.m_20124_(Pose.EMERGING);
    }

    @Override
    protected void stop(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        if (sculksaur.m_217003_(Pose.EMERGING)) {
            sculksaur.m_20124_(Pose.STANDING);
        }
        sculksaur.m_6274_().m_21936_(MemoryModuleType.f_217786_);
    }
}

