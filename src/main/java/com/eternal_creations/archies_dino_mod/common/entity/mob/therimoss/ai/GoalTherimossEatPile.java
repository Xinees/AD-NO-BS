/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.ai;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GoalTherimossEatPile
extends Goal {
    private static final int START_PILE_SIZE = 20;
    private static final int MIN_PILE_SIZE = 15;
    private static final int PILE_RADIUS = 3;
    private final TherimossEntity mob;
    private final WrappedAction eatAction;
    private final double speedModifier;
    private final int searchRangeXZ;
    private final int searchRangeY;
    @Nullable
    private BlockPos pileCenter;
    @Nullable
    private BlockPos currentTargetBlock;
    private int ticksUntilNextPathRecalculation;
    private long lastCanUseCheck;
    private int eatCooldown = 0;
    private int stuckTimer = 0;

    public GoalTherimossEatPile(TherimossEntity mob, WrappedAction eatAction, double speedModifier, int searchRangeXZ, int searchRangeY) {
        this.mob = mob;
        this.eatAction = eatAction;
        this.speedModifier = speedModifier;
        this.searchRangeXZ = searchRangeXZ;
        this.searchRangeY = searchRangeY;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public static boolean isFoodBlock(BlockState state) {
        return state.m_204336_(ADTags.Blocks.THERIMOSS_EDIBLE_BLOCKS);
    }

    public boolean m_8036_() {
        if (this.mob.isTamed()) {
            return false;
        }
        long time = this.mob.m_9236_().m_46467_();
        if (time - this.lastCanUseCheck < 20L) {
            return false;
        }
        this.lastCanUseCheck = time;
        BlockPos foundPile = this.findInitialPile();
        if (foundPile != null) {
            this.pileCenter = foundPile;
            this.currentTargetBlock = this.findNextBlockInPile(this.pileCenter);
            return this.currentTargetBlock != null;
        }
        return false;
    }

    public boolean m_8045_() {
        if (this.pileCenter == null || this.mob.isTamed()) {
            return false;
        }
        if (this.stuckTimer >= 100) {
            return false;
        }
        int remainingBlocks = this.countPileBlocks(this.mob.m_9236_(), this.pileCenter);
        return remainingBlocks >= 15;
    }

    public void m_8056_() {
        this.ticksUntilNextPathRecalculation = 0;
        this.eatCooldown = 0;
        this.stuckTimer = 0;
        this.mob.setFlag((byte)4, true);
        this.mob.resetEatenFoodBlocks();
        this.moveToCurrentTarget();
    }

    public void m_8041_() {
        this.mob.m_21573_().m_26573_();
        this.pileCenter = null;
        this.currentTargetBlock = null;
        this.stuckTimer = 0;
        this.mob.setFlag((byte)4, false);
    }

    public void m_8037_() {
        double reachDist;
        if (this.pileCenter == null) {
            return;
        }
        if (this.currentTargetBlock == null || !GoalTherimossEatPile.isFoodBlock(this.mob.m_9236_().m_8055_(this.currentTargetBlock))) {
            this.currentTargetBlock = this.findNextBlockInPile(this.pileCenter);
            if (this.currentTargetBlock == null) {
                this.m_8041_();
                return;
            }
            this.moveToCurrentTarget();
        }
        this.mob.m_21563_().m_24950_((double)this.currentTargetBlock.m_123341_() + 0.5, (double)this.currentTargetBlock.m_123342_() + 0.5, (double)this.currentTargetBlock.m_123343_() + 0.5, 30.0f, 30.0f);
        double distSqr = this.mob.m_20275_((double)this.currentTargetBlock.m_123341_() + 0.5, (double)this.currentTargetBlock.m_123342_() + 0.5, (double)this.currentTargetBlock.m_123343_() + 0.5);
        if (--this.ticksUntilNextPathRecalculation <= 0) {
            this.ticksUntilNextPathRecalculation = 10 + this.mob.m_217043_().m_188503_(10);
            this.moveToCurrentTarget();
        }
        if (distSqr <= (reachDist = (double)this.mob.m_20205_() + 1.2) * reachDist) {
            if (this.eatCooldown <= 0) {
                this.mob.setActionTargetBlock(this.currentTargetBlock);
                this.mob.incrementEatenFoodBlocks();
                this.eatAction.run(this.mob);
                this.eatCooldown = 60;
                this.stuckTimer = 0;
                this.currentTargetBlock = this.findNextBlockInPile(this.pileCenter);
                if (this.currentTargetBlock != null) {
                    this.moveToCurrentTarget();
                }
            }
        } else {
            this.stuckTimer = this.mob.m_21573_().m_26571_() || this.mob.m_20184_().m_82556_() < 0.001 ? ++this.stuckTimer : 0;
        }
        if (this.eatCooldown > 0) {
            --this.eatCooldown;
        }
    }

    private void moveToCurrentTarget() {
        if (this.currentTargetBlock != null) {
            this.mob.m_21573_().m_26519_((double)this.currentTargetBlock.m_123341_() + 0.5, (double)this.currentTargetBlock.m_123342_() + 0.5, (double)this.currentTargetBlock.m_123343_() + 0.5, this.speedModifier);
        }
    }

    @Nullable
    private BlockPos findInitialPile() {
        Level level = this.mob.m_9236_();
        BlockPos mobPos = this.mob.m_20183_();
        for (int y = -this.searchRangeY; y <= this.searchRangeY; ++y) {
            for (int x = -this.searchRangeXZ; x <= this.searchRangeXZ; ++x) {
                for (int z = -this.searchRangeXZ; z <= this.searchRangeXZ; ++z) {
                    int count;
                    BlockPos checkPos = mobPos.m_7918_(x, y, z);
                    if (!GoalTherimossEatPile.isFoodBlock(level.m_8055_(checkPos)) || (count = this.countPileBlocks(level, checkPos)) < 20) continue;
                    return checkPos;
                }
            }
        }
        return null;
    }

    @Nullable
    private BlockPos findNextBlockInPile(BlockPos center) {
        Level level = this.mob.m_9236_();
        Optional<BlockPos> bestBlock = BlockPos.m_121990_((BlockPos)center.m_7918_(-3, -3, -3), (BlockPos)center.m_7918_(3, 3, 3)).filter(pos -> GoalTherimossEatPile.isFoodBlock(level.m_8055_(pos))).map(BlockPos::m_7949_).min(Comparator.comparingInt(pos -> -pos.m_123342_()).thenComparingDouble(pos -> this.mob.m_20275_((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5)));
        return bestBlock.orElse(null);
    }

    private int countPileBlocks(Level level, BlockPos center) {
        int count = 0;
        BlockPos.MutableBlockPos mut = new BlockPos.MutableBlockPos();
        for (int dx = -3; dx <= 3; ++dx) {
            for (int dy = -3; dy <= 3; ++dy) {
                for (int dz = -3; dz <= 3; ++dz) {
                    mut.m_122178_(center.m_123341_() + dx, center.m_123342_() + dy, center.m_123343_() + dz);
                    if (!GoalTherimossEatPile.isFoodBlock(level.m_8055_((BlockPos)mut))) continue;
                    ++count;
                }
            }
        }
        return count;
    }
}

