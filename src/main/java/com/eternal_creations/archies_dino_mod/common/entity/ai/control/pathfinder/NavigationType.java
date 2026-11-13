/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.FlyNodeEvaluator
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.level.pathfinder.SwimNodeEvaluator
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder;

import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.SmoothPathNavigation;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.evaluator.ClimbWallNodeEvaluator;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public interface NavigationType {
    public static final NavigationType GROUND = new GroundNavigation();
    public static final NavigationType SWIM = new SwimNavigation();
    public static final NavigationType FLIGHT = new FlyNavigation();
    public static final NavigationType CLIMB = new ClimbNavigation();
    public static final Set<NavigationType> ALL = Set.of(GROUND, SWIM, FLIGHT, CLIMB);

    public Path createPath(Mob var1, SmoothPathNavigation var2, @NotNull Set<BlockPos> var3, int var4, boolean var5, int var6, float var7);

    public NodeEvaluator createEvaluator();

    public boolean canUpdatePath(Mob var1, SmoothPathNavigation var2);

    public boolean canMoveDirectly(Mob var1, SmoothPathNavigation var2, Vec3 var3, Vec3 var4);

    public boolean checkForGround();

    public static boolean isClearForMovementBetween(Mob mob, Vec3 fromPos, Vec3 toPos, boolean allowSwimming) {
        Vec3 vec3 = new Vec3(toPos.f_82479_, toPos.f_82480_ + (double)mob.m_20206_() * 0.5, toPos.f_82481_);
        return mob.m_9236_().m_45547_(new ClipContext(fromPos, vec3, ClipContext.Block.COLLIDER, allowSwimming ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, (Entity)mob)).m_6662_() == HitResult.Type.MISS;
    }

    public static BlockPos adjustGroundTargetPos(Level level, BlockPos pos) {
        BlockState state = level.m_8055_(pos);
        BlockPos below = pos.m_7495_();
        if (state.m_60795_() && level.m_8055_(below).m_60804_((BlockGetter)level, below)) {
            return pos;
        }
        if (state.m_60795_()) {
            BlockPos blockpos = below;
            while (blockpos.m_123342_() > level.m_141937_() && level.m_8055_(blockpos).m_60795_()) {
                blockpos = blockpos.m_7495_();
            }
            if (blockpos.m_123342_() > level.m_141937_()) {
                return blockpos.m_7494_();
            }
            return pos;
        }
        if (state.m_60804_((BlockGetter)level, pos)) {
            BlockPos blockpos1 = pos.m_7494_();
            while (blockpos1.m_123342_() < level.m_151558_() && level.m_8055_(blockpos1).m_280296_()) {
                blockpos1 = blockpos1.m_7494_();
            }
            return blockpos1;
        }
        return pos;
    }

    public static class GroundNavigation
    implements NavigationType {
        @Override
        public Path createPath(Mob mob, SmoothPathNavigation navigation, @NotNull Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy, float followRange) {
            if (targets.isEmpty()) {
                return null;
            }
            Level level = mob.m_9236_();
            Set<Object> adjustedTargets = targets;
            if (targets.size() == 1) {
                BlockPos original = targets.iterator().next();
                BlockPos adjusted = NavigationType.adjustGroundTargetPos(level, original);
                if (adjusted != original) {
                    adjustedTargets = Collections.singleton(adjusted);
                }
            } else {
                HashSet set = Sets.newHashSetWithExpectedSize((int)targets.size());
                for (BlockPos pos : targets) {
                    set.add(NavigationType.adjustGroundTargetPos(level, pos));
                }
                adjustedTargets = set;
            }
            return navigation.createPathDefault(adjustedTargets, regionOffset, offsetUpward, accuracy, followRange);
        }

        @Override
        public NodeEvaluator createEvaluator() {
            return new WalkNodeEvaluator();
        }

        @Override
        public boolean canUpdatePath(Mob mob, SmoothPathNavigation navigation) {
            return mob.m_20096_() || navigation.m_26574_() || mob.m_20159_();
        }

        @Override
        public boolean canMoveDirectly(Mob mob, SmoothPathNavigation navigation, Vec3 fromPos, Vec3 toPos) {
            int mobWidth = Mth.m_14167_((float)mob.m_20205_());
            int mobHeight = Mth.m_14167_((float)mob.m_20206_());
            Vec3 direction = toPos.m_82546_(fromPos);
            double distance = direction.m_82553_();
            if (distance < 1.0E-5) {
                return true;
            }
            Level level = mob.m_9236_();
            Vec3 step = direction.m_82490_(1.0 / distance);
            for (double d = 0.0; d < distance; d += 0.5) {
                Vec3 checkPos = fromPos.m_82549_(step.m_82490_(d));
                BlockPos baseBlock = BlockPos.m_274446_((Position)checkPos);
                int radius = mobWidth / 2;
                for (int x = -radius; x <= radius; ++x) {
                    for (int z = -radius; z <= radius; ++z) {
                        for (int y = 0; y < mobHeight; ++y) {
                            BlockPos offsetBlock = baseBlock.m_7918_(x, y, z);
                            if (level.m_8055_(offsetBlock).m_60795_() || !level.m_8055_(offsetBlock).m_60804_((BlockGetter)level, offsetBlock)) continue;
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        @Override
        public boolean checkForGround() {
            return true;
        }
    }

    public static class SwimNavigation
    extends FlyNavigation {
        @Override
        public NodeEvaluator createEvaluator() {
            return new SwimNodeEvaluator(false);
        }

        @Override
        public boolean canUpdatePath(Mob mob, SmoothPathNavigation navigation) {
            return navigation.m_26574_();
        }
    }

    public static class FlyNavigation
    implements NavigationType {
        @Override
        public Path createPath(Mob mob, SmoothPathNavigation navigation, @NotNull Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy, float followRange) {
            return navigation.createPathDefault(targets, regionOffset, offsetUpward, accuracy, followRange);
        }

        @Override
        public NodeEvaluator createEvaluator() {
            return new FlyNodeEvaluator();
        }

        @Override
        public boolean canUpdatePath(Mob mob, SmoothPathNavigation navigation) {
            return navigation.m_26576_() && navigation.m_26574_() || !mob.m_20159_();
        }

        @Override
        public boolean canMoveDirectly(Mob mob, SmoothPathNavigation navigation, Vec3 fromPos, Vec3 toPos) {
            return NavigationType.isClearForMovementBetween(mob, fromPos, toPos, true);
        }

        @Override
        public boolean checkForGround() {
            return false;
        }
    }

    public static class ClimbNavigation
    extends FlyNavigation {
        @Override
        public NodeEvaluator createEvaluator() {
            return new ClimbWallNodeEvaluator();
        }
    }
}

