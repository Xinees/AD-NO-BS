/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.behavior.Behavior
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.SculkraptorEntity;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorFlee
extends Behavior<SculkraptorEntity> {
    private final float speedMultiplier;
    @Nullable
    private Path currentPath = null;

    public SculkraptorFlee(float speedMultiplier) {
        super((Map)ImmutableMap.of((Object)MemoryModuleType.f_26370_, (Object)MemoryStatus.REGISTERED, ADMemoryModuleTypes.FLEE_PATH.get(), (Object)MemoryStatus.VALUE_PRESENT), 200);
        this.speedMultiplier = speedMultiplier;
    }

    @Nullable
    public static Path createPath(SculkraptorEntity sculkraptor, LivingEntity toAvoid) {
        Vec3 vec3 = DefaultRandomPos.m_148407_((PathfinderMob)sculkraptor, (int)16, (int)7, (Vec3)toAvoid.m_20182_());
        if (vec3 == null) {
            return null;
        }
        if (toAvoid.m_20275_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_) < toAvoid.m_20280_((Entity)sculkraptor)) {
            return null;
        }
        return sculkraptor.m_21573_().m_26524_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0);
    }

    protected boolean canStillUse(ServerLevel level, SculkraptorEntity entity, long gameTime) {
        return entity.m_6274_().m_21874_(ADMemoryModuleTypes.FLEE_PATH.get());
    }

    protected boolean m_7773_(long gameTime) {
        return false;
    }

    protected void tick(ServerLevel level, SculkraptorEntity sculkraptor, long gameTime) {
        Path path = sculkraptor.m_6274_().m_21952_(ADMemoryModuleTypes.FLEE_PATH.get()).orElse(null);
        if (this.currentPath != path) {
            sculkraptor.m_21573_().m_26536_(path, (double)this.speedMultiplier);
            this.currentPath = path;
        }
    }
}

