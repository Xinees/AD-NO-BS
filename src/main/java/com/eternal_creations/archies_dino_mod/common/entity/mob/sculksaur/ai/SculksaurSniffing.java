/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.ActionBehaviour;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai.SculksaurAI;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurSniffing<E extends SculksaurEntity>
extends ActionBehaviour<E> {
    private static final double ANGER_FROM_SNIFFING_MAX_DISTANCE_XZ = 12.0;
    private static final double ANGER_FROM_SNIFFING_MAX_DISTANCE_Y = 40.0;

    public SculksaurSniffing() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of((Object)MemoryModuleType.f_217785_, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_26370_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_26371_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_148194_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_217783_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_217772_, (Object)MemoryStatus.REGISTERED), e -> e.actionListen);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, E sculksaur, long gameTime) {
        return true;
    }

    @Override
    protected void start(ServerLevel level, E sculksaur, long gameTime) {
        sculksaur.m_5496_(ADSoundEvents.SCULKSAUR_SNIFF.get(), 5.0f, 1.0f);
    }

    @Override
    protected void stop(ServerLevel level, E sculksaur, long gameTime) {
        if (sculksaur.m_217003_(Pose.SNIFFING)) {
            sculksaur.m_20124_(Pose.STANDING);
        }
        ((SculksaurEntity)sculksaur).m_6274_().m_21936_(MemoryModuleType.f_217785_);
        ((SculksaurEntity)sculksaur).m_6274_().m_21952_(MemoryModuleType.f_148194_).filter(arg_0 -> sculksaur.canTargetEntity(arg_0)).ifPresent(living -> {
            if (sculksaur.m_216992_((Entity)living, 12.0, 40.0)) {
                sculksaur.increaseAngerAt((Entity)living);
            }
            if (!sculksaur.m_6274_().m_21874_(MemoryModuleType.f_217783_)) {
                SculksaurAI.setDisturbanceLocation(sculksaur, living.m_20183_());
            }
        });
    }
}

