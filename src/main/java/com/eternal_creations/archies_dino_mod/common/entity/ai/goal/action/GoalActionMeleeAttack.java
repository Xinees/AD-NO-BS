/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.core.function.Condition
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.level.pathfinder.Path
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action;

import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionSelector;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

public class GoalActionMeleeAttack<T extends PathfinderMob & SyncedActionHolder>
extends GoalActionSelector {
    private final T mob;
    private final double speedModifier;
    private final boolean followingTargetEvenIfNotSeen;
    private final int attackInterval;
    private final Condition canAttack;
    private final Condition condition;
    private final Predicate<LivingEntity> targetPredicate;
    @Nullable
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private long lastCanUseCheck;

    private GoalActionMeleeAttack(T mob, Builder builder) {
        super(builder.actions);
        this.mob = mob;
        this.speedModifier = builder.speedModifier;
        this.followingTargetEvenIfNotSeen = builder.followingTargetEvenIfNotSeen;
        this.attackInterval = builder.attackInterval;
        this.condition = builder.condition;
        this.targetPredicate = builder.targetPredicate;
        this.canAttack = builder.canAttack;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (!this.condition.check()) {
            return false;
        }
        long time = this.mob.m_9236_().m_46467_();
        if (time - this.lastCanUseCheck < 20L) {
            return false;
        }
        this.lastCanUseCheck = time;
        LivingEntity target = this.mob.m_5448_();
        if (!(target != null && target.m_6084_() && this.targetPredicate.test(target) && this.mob.m_6779_(target))) {
            return false;
        }
        this.path = this.mob.m_21573_().m_6570_((Entity)target, 0);
        if (this.path != null) {
            return true;
        }
        double distSqr = EntityUtils.getHorizontalDistanceSqr(this.mob, (Entity)target);
        return this.isAnyActionPossible(distSqr);
    }

    public boolean m_8045_() {
        if (!this.condition.check()) {
            return false;
        }
        LivingEntity target = this.mob.m_5448_();
        if (target == null) {
            return false;
        }
        if (!target.m_6084_()) {
            return false;
        }
        if (!this.targetPredicate.test(target)) {
            return false;
        }
        if (!this.mob.m_6779_(target)) {
            return false;
        }
        if (!this.followingTargetEvenIfNotSeen) {
            return !this.mob.m_21573_().m_26571_();
        }
        return this.mob.m_21444_(target.m_20183_());
    }

    public void m_8056_() {
        this.mob.m_21573_().m_26536_(this.path, this.speedModifier);
        this.mob.m_21561_(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
    }

    public void m_8041_() {
        this.mob.m_21561_(false);
        this.mob.m_21573_().m_26573_();
    }

    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        LivingEntity target = this.mob.m_5448_();
        if (target != null) {
            this.mob.m_21563_().m_24960_((Entity)target, 30.0f, 30.0f);
            double distSqr = EntityUtils.getHorizontalDistanceSqr(this.mob, (Entity)target);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.mob.m_21574_().m_148306_((Entity)target)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0 && this.pathedTargetY == 0.0 && this.pathedTargetZ == 0.0 || target.m_20275_(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= 1.0 || this.mob.m_217043_().m_188501_() < 0.05f)) {
                this.pathedTargetX = target.m_20185_();
                this.pathedTargetY = target.m_20186_();
                this.pathedTargetZ = target.m_20189_();
                this.ticksUntilNextPathRecalculation = 4 + this.mob.m_217043_().m_188503_(7);
                if (distSqr > 1024.0) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (distSqr > 256.0) {
                    this.ticksUntilNextPathRecalculation += 5;
                }
                if (!this.mob.m_21573_().m_5624_((Entity)target, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }
                this.ticksUntilNextPathRecalculation = this.m_183277_(this.ticksUntilNextPathRecalculation);
            }
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAction(target, distSqr);
        }
    }

    protected void checkAndPerformAction(LivingEntity target, double distanceToTarget) {
        WrappedAction selectedAction;
        if (this.ticksUntilNextAttack <= 0 && this.canAttack.check() && (selectedAction = this.getWeightedAction((Mob)this.mob, distanceToTarget)) != null) {
            this.ticksUntilNextAttack = this.m_183277_(this.attackInterval);
            ((ActionTargetEntity)this.mob).setActionTargetEntity(target);
            selectedAction.run((SyncedActionHolder)this.mob);
        }
    }

    public static Builder of(WrappedAction ... attackActions) {
        return new Builder(attackActions);
    }

    public static class Builder {
        private double speedModifier = 1.0;
        private boolean followingTargetEvenIfNotSeen = false;
        private int attackInterval = 20;
        private final List<WrappedAction> actions;
        private Predicate<LivingEntity> targetPredicate = entity -> true;
        private Condition canAttack = Condition.TRUE;
        private Condition condition = Condition.TRUE;

        public Builder(WrappedAction ... attackActions) {
            this.actions = List.of(attackActions);
        }

        public Builder canAttack(Condition canAttack) {
            this.canAttack = canAttack;
            return this;
        }

        public Builder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public Builder speed(double speed) {
            this.speedModifier = speed;
            return this;
        }

        public Builder followEvenIfHidden() {
            this.followingTargetEvenIfNotSeen = true;
            return this;
        }

        public Builder interval(int ticks) {
            this.attackInterval = ticks;
            return this;
        }

        public Builder targetFilter(Predicate<LivingEntity> predicate) {
            this.targetPredicate = predicate;
            return this;
        }

        public <T extends PathfinderMob & SyncedActionHolder> GoalActionMeleeAttack<T> build(T mob) {
            return new GoalActionMeleeAttack<T>(mob, this);
        }
    }
}

