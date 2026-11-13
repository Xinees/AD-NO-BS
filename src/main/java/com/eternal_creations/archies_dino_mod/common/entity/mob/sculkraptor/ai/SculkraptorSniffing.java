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
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.ActionBehaviour;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorAI;
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
public class SculkraptorSniffing<E extends SculkraptorEntity>
extends ActionBehaviour<E> {
    private static final double ANGER_FROM_SNIFFING_MAX_DISTANCE_XZ = 12.0;
    private static final double ANGER_FROM_SNIFFING_MAX_DISTANCE_Y = 40.0;

    public SculkraptorSniffing() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of((Object)MemoryModuleType.f_217785_, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_26370_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_26371_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_148194_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_217783_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_217772_, (Object)MemoryStatus.REGISTERED), e -> e.actionSniff);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E owner) {
        if (((AbstractComplexMob)owner).isTamed()) {
            return false;
        }
        return ((SculkraptorEntity)owner).m_6274_().m_21952_(MemoryModuleType.f_217768_).orElse(false) == false;
    }

    @Override
    protected boolean canStillUse(ServerLevel level, E sculkraptor, long gameTime) {
        return true;
    }

    @Override
    protected void start(ServerLevel level, E sculkraptor, long gameTime) {
        sculkraptor.m_5496_(ADSoundEvents.SCULKRAPTOR_SNIFF.get(), 5.0f, 1.0f);
    }

    @Override
    protected void stop(ServerLevel level, E sculkraptor, long gameTime) {
        if (sculkraptor.m_217003_(Pose.SNIFFING)) {
            sculkraptor.m_20124_(Pose.STANDING);
        }
        ((SculkraptorEntity)sculkraptor).m_6274_().m_21936_(MemoryModuleType.f_217785_);
        ((SculkraptorEntity)sculkraptor).m_6274_().m_21952_(MemoryModuleType.f_148194_).filter(arg_0 -> sculkraptor.canTargetEntity(arg_0)).ifPresent(living -> {
            if (sculkraptor.m_216992_((Entity)living, 12.0, 40.0)) {
                sculkraptor.increaseAngerAt((Entity)living);
            }
            if (!sculkraptor.m_6274_().m_21874_(MemoryModuleType.f_217783_)) {
                SculkraptorAI.setDisturbanceLocation(sculkraptor, living.m_20183_());
            }
        });
    }
}

