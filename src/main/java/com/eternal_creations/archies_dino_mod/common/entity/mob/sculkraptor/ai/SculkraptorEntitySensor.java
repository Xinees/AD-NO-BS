/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Iterables
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorEntitySensor
extends NearestLivingEntitySensor<SculkraptorEntity> {
    public Set<MemoryModuleType<?>> m_7163_() {
        return ImmutableSet.copyOf((Iterable)Iterables.concat((Iterable)super.m_7163_(), List.of(MemoryModuleType.f_148194_)));
    }

    protected void doTick(ServerLevel level, SculkraptorEntity entity) {
        super.m_5578_(level, (LivingEntity)entity);
        SculkraptorEntitySensor.getClosest(entity, living -> living.m_6095_() == EntityType.f_20532_).or(() -> SculkraptorEntitySensor.getClosest(entity, living -> living.m_6095_() != EntityType.f_20532_)).ifPresentOrElse(living -> entity.m_6274_().m_21879_(MemoryModuleType.f_148194_, living), () -> entity.m_6274_().m_21936_(MemoryModuleType.f_148194_));
    }

    private static Optional<LivingEntity> getClosest(SculkraptorEntity sculkraptor, Predicate<LivingEntity> predicate) {
        return sculkraptor.m_6274_().m_21952_(MemoryModuleType.f_148204_).stream().flatMap(Collection::stream).filter(sculkraptor::canTargetEntity).filter(predicate).findFirst();
    }

    protected int m_214020_() {
        return 24;
    }

    protected int m_214019_() {
        return 24;
    }
}

