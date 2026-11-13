/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.core.function.Condition
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.Path
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action;

import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionSelector;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetBlock;
import java.util.EnumSet;
import java.util.List;
import java.util.function.BiPredicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;

public class GoalActionBlockInteract<T extends PathfinderMob & SyncedActionHolder>
extends GoalActionSelector {
    private final T mob;
    private final double speedModifier;
    private final int cooldownInterval;
    private final Condition canInteract;
    private final BiPredicate<BlockState, BlockPos> blockPredicate;
    private final int searchRangeXZ;
    private final int searchRangeY;
    @Nullable
    private Path path;
    private BlockPos targetBlock;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private long lastCanUseCheck;
    private long lastActionTime = -1L;
    private boolean actionPerformed;

    private GoalActionBlockInteract(T mob, Builder builder) {
        super(builder.actions);
        this.mob = mob;
        this.speedModifier = builder.speedModifier;
        this.cooldownInterval = builder.cooldownInterval;
        this.blockPredicate = builder.blockPredicate;
        this.searchRangeXZ = builder.searchRangeXZ;
        this.searchRangeY = builder.searchRangeY;
        this.canInteract = builder.canInteract;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (!this.canInteract.check()) {
            return false;
        }
        long time = this.mob.m_9236_().m_46467_();
        if (this.lastActionTime >= 0L && time - this.lastActionTime < (long)this.cooldownInterval) {
            return false;
        }
        if (time - this.lastCanUseCheck < 20L) {
            return false;
        }
        this.lastCanUseCheck = time;
        this.targetBlock = this.findTargetBlock();
        if (this.targetBlock == null) {
            return false;
        }
        this.path = this.mob.m_21573_().m_7864_(this.targetBlock, 0);
        if (this.path != null) {
            return true;
        }
        double distSqr = this.mob.m_20275_((double)this.targetBlock.m_123341_() + 0.5, (double)this.targetBlock.m_123342_() + 0.5, (double)this.targetBlock.m_123343_() + 0.5);
        return this.isAnyActionPossible(Math.sqrt(distSqr));
    }

    public boolean m_8045_() {
        if (!this.canInteract.check()) {
            return false;
        }
        if (this.actionPerformed) {
            return false;
        }
        if (this.targetBlock == null) {
            return false;
        }
        BlockState state = this.mob.m_9236_().m_8055_(this.targetBlock);
        if (!this.blockPredicate.test(state, this.targetBlock)) {
            return false;
        }
        return !this.mob.m_21573_().m_26571_();
    }

    public void m_8056_() {
        this.mob.m_21573_().m_26536_(this.path, this.speedModifier);
        this.mob.m_21561_(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.actionPerformed = false;
    }

    public void m_8041_() {
        this.mob.m_21561_(false);
        this.mob.m_21573_().m_26573_();
        this.targetBlock = null;
    }

    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        if (this.targetBlock != null) {
            this.mob.m_21563_().m_24950_((double)this.targetBlock.m_123341_() + 0.5, (double)this.targetBlock.m_123342_() + 0.5, (double)this.targetBlock.m_123343_() + 0.5, 30.0f, 30.0f);
            double distSqr = this.mob.m_20275_((double)this.targetBlock.m_123341_() + 0.5, (double)this.targetBlock.m_123342_() + 0.5, (double)this.targetBlock.m_123343_() + 0.5);
            double dist = Math.sqrt(distSqr);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if (this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || this.targetBlock.m_203198_(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.mob.m_217043_().m_188501_() < 0.05f)) {
                this.pathedTargetX = (double)this.targetBlock.m_123341_() + 0.5;
                this.pathedTargetY = (double)this.targetBlock.m_123342_() + 0.5;
                this.pathedTargetZ = (double)this.targetBlock.m_123343_() + 0.5;
                this.ticksUntilNextPathRecalculation = 4 + this.mob.m_217043_().m_188503_(7);
                if (distSqr > 1024.0) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (distSqr > 256.0) {
                    this.ticksUntilNextPathRecalculation += 5;
                }
                if (!this.mob.m_21573_().m_26519_(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
                this.ticksUntilNextPathRecalculation = this.m_183277_(this.ticksUntilNextPathRecalculation);
            }
            this.checkAndPerformAction(dist);
        }
    }

    protected void checkAndPerformAction(double distanceToTarget) {
        WrappedAction selectedAction;
        if (this.canInteract.check() && (selectedAction = this.getWeightedAction((Mob)this.mob, (float)distanceToTarget)) != null) {
            this.lastActionTime = this.mob.m_9236_().m_46467_();
            this.actionPerformed = true;
            ((ActionTargetBlock)this.mob).setActionTargetBlock(this.targetBlock);
            selectedAction.run((SyncedActionHolder)this.mob);
        }
    }

    @Nullable
    private BlockPos findTargetBlock() {
        BlockPos mobPos = this.mob.m_20183_();
        for (int y = -this.searchRangeY; y <= this.searchRangeY; ++y) {
            for (int x = -this.searchRangeXZ; x <= this.searchRangeXZ; ++x) {
                for (int z = -this.searchRangeXZ; z <= this.searchRangeXZ; ++z) {
                    BlockPos pos = mobPos.m_7918_(x, y, z);
                    BlockState state = this.mob.m_9236_().m_8055_(pos);
                    if (!this.blockPredicate.test(state, pos)) continue;
                    return pos;
                }
            }
        }
        return null;
    }

    public static Builder of(WrappedAction ... actions) {
        return new Builder(actions);
    }

    public static class Builder {
        private final List<WrappedAction> actions;
        private double speedModifier = 1.0;
        private int cooldownInterval = 20;
        private BiPredicate<BlockState, BlockPos> blockPredicate = (state, pos) -> false;
        private int searchRangeXZ = 8;
        private int searchRangeY = 4;
        private Condition canInteract = Condition.TRUE;

        public Builder(WrappedAction ... actions) {
            this.actions = List.of(actions);
        }

        public Builder canInteract(Condition canInteract) {
            this.canInteract = canInteract;
            return this;
        }

        public Builder speed(double speed) {
            this.speedModifier = speed;
            return this;
        }

        public Builder interval(int ticks) {
            this.cooldownInterval = ticks;
            return this;
        }

        public Builder blockFilter(BiPredicate<BlockState, BlockPos> predicate) {
            this.blockPredicate = predicate;
            return this;
        }

        public Builder searchRange(int xz, int y) {
            this.searchRangeXZ = xz;
            this.searchRangeY = y;
            return this;
        }

        public <T extends PathfinderMob & SyncedActionHolder> GoalActionBlockInteract<T> build(T mob) {
            return new GoalActionBlockInteract<T>(mob, this);
        }
    }
}

