/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.Brain
 *  net.minecraft.world.entity.ai.behavior.Behavior
 *  net.minecraft.world.entity.ai.behavior.EntityTracker
 *  net.minecraft.world.entity.ai.behavior.PositionTracker
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.entity.ai.memory.WalkTarget
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.util.DefaultRandomPos
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour;

import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomMoveSink
extends Behavior<PathfinderMob> {
    private static final int MAX_COOLDOWN_BEFORE_RETRYING = 40;
    private final MemoryModuleType<WalkTarget> targeting;
    private int remainingCooldown;
    @Nullable
    private Path path;
    @Nullable
    private BlockPos lastTargetPos;
    private float speedModifier;

    public CustomMoveSink(MemoryModuleType<WalkTarget> targeting) {
        super((Map)ImmutableMap.of((Object)MemoryModuleType.f_26326_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_26377_, (Object)MemoryStatus.VALUE_ABSENT, targeting, (Object)MemoryStatus.VALUE_PRESENT), 150, 1000);
        this.targeting = targeting;
    }

    public CustomMoveSink(MemoryModuleType<WalkTarget> targeting, MemoryModuleType<?> absentee) {
        super((Map)ImmutableMap.of((Object)MemoryModuleType.f_26326_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_26377_, (Object)MemoryStatus.VALUE_ABSENT, absentee, (Object)MemoryStatus.VALUE_ABSENT, targeting, (Object)MemoryStatus.VALUE_PRESENT), 150, 200);
        this.targeting = targeting;
    }

    protected boolean checkExtraStartConditions(ServerLevel level, PathfinderMob mob) {
        if (this.remainingCooldown > 0 && this.targeting != ADMemoryModuleTypes.WALK_COMMAND.get()) {
            --this.remainingCooldown;
            return false;
        }
        Brain brain = mob.m_6274_();
        WalkTarget walktarget = (WalkTarget)brain.m_21952_(this.targeting).get();
        if (!mob.m_21444_(walktarget.m_26420_().m_6675_())) {
            return false;
        }
        boolean reachedTarget = this.reachedTarget(mob, walktarget);
        if (!reachedTarget && this.tryComputePath(mob, walktarget, level.m_46467_())) {
            this.lastTargetPos = walktarget.m_26420_().m_6675_();
            return true;
        }
        brain.m_21936_(this.targeting);
        if (reachedTarget) {
            brain.m_21936_(MemoryModuleType.f_26326_);
        }
        return false;
    }

    protected boolean canStillUse(ServerLevel level, PathfinderMob mob, long gameTime) {
        if (this.targeting == ADMemoryModuleTypes.WALK_COMMAND.get()) {
            return mob.m_6274_().m_21952_(this.targeting).isPresent();
        }
        if (this.path != null && this.lastTargetPos != null) {
            Optional walkTarget = mob.m_6274_().m_21952_(this.targeting);
            boolean isTargetSpectator = walkTarget.map(CustomMoveSink::isWalkTargetSpectator).orElse(false);
            PathNavigation navigation = mob.m_21573_();
            return !navigation.m_26571_() && walkTarget.isPresent() && !this.reachedTarget(mob, (WalkTarget)walkTarget.get()) && !isTargetSpectator;
        }
        return false;
    }

    protected void stop(ServerLevel level, PathfinderMob mob, long gameTime) {
        if (mob.m_6274_().m_21874_(this.targeting) && !this.reachedTarget(mob, (WalkTarget)mob.m_6274_().m_21952_(this.targeting).get()) && mob.m_21573_().m_26577_()) {
            this.remainingCooldown = level.m_213780_().m_188503_(40);
        }
        mob.m_21573_().m_26573_();
        mob.m_6274_().m_21936_(this.targeting);
        mob.m_6274_().m_21936_(MemoryModuleType.f_26377_);
        this.path = null;
    }

    protected void start(ServerLevel level, PathfinderMob mob, long gameTime) {
        mob.m_6274_().m_21879_(MemoryModuleType.f_26377_, (Object)this.path);
        mob.m_21573_().m_26536_(this.path, (double)this.speedModifier);
    }

    protected void tick(ServerLevel level, PathfinderMob mob, long gameTime) {
        WalkTarget walktarget;
        Path path = mob.m_21573_().m_26570_();
        Brain brain = mob.m_6274_();
        if (this.path != null && path == null) {
            mob.m_21573_().m_26536_(this.path, (double)this.speedModifier);
        } else if (this.targeting == ADMemoryModuleTypes.WALK_COMMAND.get() && path == null) {
            walktarget = brain.m_21952_(this.targeting).orElse(null);
            if (walktarget == null) {
                return;
            }
            this.tryComputePath(mob, walktarget, level.m_46467_());
            mob.m_21573_().m_26536_(this.path, (double)this.speedModifier);
        }
        if (this.path != path) {
            this.path = path;
            brain.m_21879_(MemoryModuleType.f_26377_, (Object)path);
        }
        if (path != null && this.lastTargetPos != null && (walktarget = (WalkTarget)brain.m_21952_(this.targeting).get()).m_26420_().m_6675_().m_123331_((Vec3i)this.lastTargetPos) > 4.0 && this.tryComputePath(mob, walktarget, level.m_46467_())) {
            this.lastTargetPos = walktarget.m_26420_().m_6675_();
            this.start(level, mob, gameTime);
        }
    }

    private boolean tryComputePath(PathfinderMob mob, WalkTarget target, long time) {
        BlockPos blockpos = target.m_26420_().m_6675_();
        this.path = mob.m_21573_().m_7864_(blockpos, 0);
        this.speedModifier = target.m_26421_();
        Brain brain = mob.m_6274_();
        if (this.reachedTarget(mob, target)) {
            brain.m_21936_(MemoryModuleType.f_26326_);
        } else {
            boolean flag;
            boolean bl = flag = this.path != null && this.path.m_77403_();
            if (flag) {
                brain.m_21936_(MemoryModuleType.f_26326_);
            } else if (!brain.m_21874_(MemoryModuleType.f_26326_)) {
                brain.m_21879_(MemoryModuleType.f_26326_, (Object)time);
            }
            if (this.path != null) {
                return true;
            }
            Vec3 vec3 = DefaultRandomPos.m_148412_((PathfinderMob)mob, (int)10, (int)7, (Vec3)Vec3.m_82539_((Vec3i)blockpos), (double)1.5707963705062866);
            if (vec3 != null) {
                this.path = mob.m_21573_().m_26524_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0);
                return this.path != null;
            }
        }
        return false;
    }

    private boolean reachedTarget(PathfinderMob mob, WalkTarget target) {
        return target.m_26420_().m_6675_().m_123333_((Vec3i)mob.m_20183_()) <= target.m_26422_();
    }

    private static boolean isWalkTargetSpectator(WalkTarget walkTarget) {
        PositionTracker positiontracker = walkTarget.m_26420_();
        if (positiontracker instanceof EntityTracker) {
            EntityTracker entitytracker = (EntityTracker)positiontracker;
            return entitytracker.m_147481_().m_5833_();
        }
        return false;
    }
}

