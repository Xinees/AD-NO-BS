/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie.goals;

import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import java.util.EnumSet;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FollowFriendGoal
extends Goal {
    public static final int TELEPORT_WHEN_DISTANCE_IS = 12;
    private static final int MIN_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 2;
    private static final int MAX_HORIZONTAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 3;
    private static final int MAX_VERTICAL_DISTANCE_FROM_PLAYER_WHEN_TELEPORTING = 1;
    private final ArchieEntity archie;
    private LivingEntity owner;
    private final LevelReader level;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final float stopDistance;
    private final float startDistance;
    private float oldWaterCost;

    public FollowFriendGoal(ArchieEntity archie, double speedModifier, float startDistance, float stopDistance) {
        this.archie = archie;
        this.level = archie.m_9236_();
        this.speedModifier = speedModifier;
        this.navigation = archie.m_21573_();
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (this.archie.denyTick > 0) {
            return false;
        }
        LivingEntity livingentity = this.archie.m_269323_();
        if (livingentity == null) {
            return false;
        }
        if (livingentity.m_5833_()) {
            return false;
        }
        if (this.unableToMove()) {
            return false;
        }
        if (this.archie.m_20280_((Entity)livingentity) < (double)(this.startDistance * this.startDistance)) {
            return false;
        }
        this.owner = livingentity;
        return true;
    }

    public boolean m_8045_() {
        if (this.archie.denyTick > 0) {
            return false;
        }
        if (this.navigation.m_26571_()) {
            return false;
        }
        if (this.unableToMove()) {
            return false;
        }
        return !(this.archie.m_20280_((Entity)this.owner) <= (double)(this.stopDistance * this.stopDistance));
    }

    private boolean unableToMove() {
        return this.archie.m_20159_() || this.archie.m_21523_();
    }

    public void m_8056_() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.archie.m_21439_(BlockPathTypes.WATER);
        this.archie.m_21441_(BlockPathTypes.WATER, 0.0f);
    }

    public void m_8041_() {
        this.owner = null;
        this.navigation.m_26573_();
        this.archie.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
    }

    public void m_8037_() {
        this.archie.m_21563_().m_24960_((Entity)this.owner, 10.0f, (float)this.archie.m_8132_());
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.m_183277_(10);
            double distance = this.archie.m_20280_((Entity)this.owner);
            if (distance >= 144.0) {
                this.teleportToOwner();
            } else if (distance > 30.0 && !this.archie.m_6047_()) {
                this.navigation.m_5624_((Entity)this.owner, this.speedModifier * 2.0);
            } else {
                this.navigation.m_5624_((Entity)this.owner, this.speedModifier);
            }
        }
    }

    private void teleportToOwner() {
        BlockPos blockpos = this.owner.m_20183_();
        for (int i = 0; i < 10; ++i) {
            int j = this.randomIntInclusive(-3, 3);
            int k = this.randomIntInclusive(-1, 1);
            int l = this.randomIntInclusive(-3, 3);
            boolean flag = this.maybeTeleportTo(blockpos.m_123341_() + j, blockpos.m_123342_() + k, blockpos.m_123343_() + l);
            if (!flag) continue;
            return;
        }
    }

    private boolean maybeTeleportTo(int x, int y, int z) {
        if (Math.abs((double)x - this.owner.m_20185_()) < 2.0 && Math.abs((double)z - this.owner.m_20189_()) < 2.0) {
            return false;
        }
        if (!this.canTeleportTo(new BlockPos(x, y, z))) {
            return false;
        }
        this.archie.m_7678_((double)x + 0.5, y, (double)z + 0.5, this.archie.m_146908_(), this.archie.m_146909_());
        this.navigation.m_26573_();
        return true;
    }

    private boolean canTeleportTo(BlockPos pos) {
        BlockPathTypes blockpathtypes = WalkNodeEvaluator.m_77604_((BlockGetter)this.level, (BlockPos.MutableBlockPos)pos.m_122032_());
        if (blockpathtypes != BlockPathTypes.WALKABLE) {
            return false;
        }
        BlockState blockstate = this.level.m_8055_(pos.m_7495_());
        if (blockstate.m_60734_() instanceof LeavesBlock) {
            return false;
        }
        BlockPos blockpos = pos.m_121996_((Vec3i)this.archie.m_20183_());
        return this.level.m_45756_((Entity)this.archie, this.archie.m_20191_().m_82338_(blockpos));
    }

    private int randomIntInclusive(int min, int max) {
        return this.archie.m_217043_().m_188503_(max - min + 1) + min;
    }
}

