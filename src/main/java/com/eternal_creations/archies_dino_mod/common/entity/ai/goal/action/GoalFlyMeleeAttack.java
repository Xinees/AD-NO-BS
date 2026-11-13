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
 *  net.minecraft.world.phys.Vec3
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GoalFlyMeleeAttack<T extends PathfinderMob & SyncedActionHolder>
extends GoalActionSelector {
    private final T mob;
    private final double speedModifier;
    private final double retreatSpeedModifier;
    private final int attackCooldown;
    private final int minRetreatDuration;
    private final int maxRetreatDuration;
    private final Condition canAttack;
    private final Condition condition;
    private final Predicate<LivingEntity> targetPredicate;
    private long lastAttackTime;
    private State state = State.APPROACH;
    @Nullable
    private Vec3 retreatTarget;
    private int retreatTicks;
    private int ticksUntilNextPathRecalculation;
    private int strikeTicks;

    private GoalFlyMeleeAttack(T mob, Builder builder) {
        super(builder.actions);
        this.mob = mob;
        this.speedModifier = builder.speedModifier;
        this.retreatSpeedModifier = builder.retreatSpeedModifier;
        this.attackCooldown = builder.attackCooldown;
        this.minRetreatDuration = builder.minRetreatDuration;
        this.maxRetreatDuration = builder.maxRetreatDuration;
        this.targetPredicate = builder.targetPredicate;
        this.canAttack = builder.canAttack;
        this.condition = builder.condition;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (!this.condition.check()) {
            return false;
        }
        long time = this.mob.m_9236_().m_46467_();
        if (time - this.lastAttackTime < (long)this.attackCooldown) {
            return false;
        }
        LivingEntity target = this.mob.m_5448_();
        if (!(target != null && target.m_6084_() && this.targetPredicate.test(target) && this.mob.m_6779_(target))) {
            return false;
        }
        return this.canAttack.check();
    }

    public boolean m_8045_() {
        if (!this.condition.check()) {
            return false;
        }
        if (this.state == State.RETREAT) {
            return this.retreatTicks > 0 && !this.mob.m_21573_().m_26571_();
        }
        if (this.state == State.STRIKE) {
            return this.strikeTicks > 0;
        }
        LivingEntity target = this.mob.m_5448_();
        return target != null && target.m_6084_() && this.targetPredicate.test(target) && this.mob.m_6779_(target);
    }

    public void m_8056_() {
        this.state = State.APPROACH;
        this.mob.m_21561_(true);
        this.ticksUntilNextPathRecalculation = 0;
    }

    public void m_8041_() {
        this.mob.m_21561_(false);
        this.mob.m_21573_().m_26573_();
        this.retreatTarget = null;
    }

    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        LivingEntity target = this.mob.m_5448_();
        if (target == null) {
            return;
        }
        if (this.state == State.APPROACH) {
            WrappedAction selectedAction;
            this.mob.m_21563_().m_24960_((Entity)target, 30.0f, 30.0f);
            double distSqr = EntityUtils.getDistanceSqr(this.mob, (Entity)target);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if (this.ticksUntilNextPathRecalculation == 0) {
                this.mob.m_21573_().m_5624_((Entity)target, this.speedModifier);
                this.ticksUntilNextPathRecalculation = 4 + this.mob.m_217043_().m_188503_(7);
            }
            if ((selectedAction = this.getWeightedAction((Mob)this.mob, distSqr)) != null) {
                ((ActionTargetEntity)this.mob).setActionTargetEntity(target);
                selectedAction.run((SyncedActionHolder)this.mob);
                this.lastAttackTime = this.mob.m_9236_().m_46467_();
                this.state = State.STRIKE;
                this.strikeTicks = 20;
                this.mob.m_20256_(this.mob.m_20184_().m_82542_((double)0.1f, (double)0.1f, (double)0.1f));
            }
        } else if (this.state == State.STRIKE) {
            --this.strikeTicks;
            this.mob.m_21563_().m_24960_((Entity)target, 45.0f, 45.0f);
            this.mob.m_21573_().m_5624_((Entity)target, this.speedModifier);
            if (this.strikeTicks <= 0) {
                this.initiateRetreat(target);
            }
        } else if (this.state == State.RETREAT) {
            --this.retreatTicks;
            if (this.retreatTarget != null) {
                this.mob.m_21573_().m_26519_(this.retreatTarget.f_82479_, this.retreatTarget.f_82480_, this.retreatTarget.f_82481_, this.retreatSpeedModifier);
            }
        }
    }

    private void initiateRetreat(LivingEntity target) {
        Vec3 targetPos;
        this.state = State.RETREAT;
        this.retreatTicks = this.minRetreatDuration + this.mob.m_217043_().m_188503_(this.maxRetreatDuration - this.minRetreatDuration + 1);
        Vec3 mobPos = this.mob.m_20182_();
        Vec3 awayDir = mobPos.m_82546_(targetPos = target.m_20182_());
        if (awayDir.m_82556_() < 0.1) {
            double angle = this.mob.m_217043_().m_188500_() * Math.PI * 2.0;
            awayDir = new Vec3(Math.cos(angle), 0.0, Math.sin(angle));
        } else {
            awayDir = awayDir.m_82541_();
        }
        double distance = 10.0 + this.mob.m_217043_().m_188500_() * 15.0;
        double height = 5.0 + this.mob.m_217043_().m_188500_() * 10.0;
        this.retreatTarget = mobPos.m_82520_(awayDir.f_82479_ * distance, height, awayDir.f_82481_ * distance);
        this.mob.m_21573_().m_26519_(this.retreatTarget.f_82479_, this.retreatTarget.f_82480_, this.retreatTarget.f_82481_, this.retreatSpeedModifier);
    }

    public static Builder of(WrappedAction ... attackActions) {
        return new Builder(attackActions);
    }

    public static class Builder {
        private double speedModifier = 1.2;
        private double retreatSpeedModifier = 1.5;
        private int attackCooldown = 100;
        private int minRetreatDuration = 30;
        private int maxRetreatDuration = 60;
        private final List<WrappedAction> actions;
        private Predicate<LivingEntity> targetPredicate = entity -> true;
        private Condition canAttack = Condition.TRUE;
        private Condition condition = Condition.TRUE;

        public Builder(WrappedAction ... attackActions) {
            this.actions = List.of(attackActions);
        }

        public Builder speed(double speed, double retreatSpeed) {
            this.speedModifier = speed;
            this.retreatSpeedModifier = retreatSpeed;
            return this;
        }

        public Builder attackCooldown(int ticks) {
            this.attackCooldown = ticks;
            return this;
        }

        public Builder retreatDuration(int minTicks, int maxTicks) {
            this.minRetreatDuration = minTicks;
            this.maxRetreatDuration = maxTicks;
            return this;
        }

        public Builder targetFilter(Predicate<LivingEntity> predicate) {
            this.targetPredicate = predicate;
            return this;
        }

        public Builder canAttack(Condition canAttack) {
            this.canAttack = canAttack;
            return this;
        }

        public Builder condition(Condition condition) {
            this.condition = condition;
            return this;
        }

        public <T extends PathfinderMob & SyncedActionHolder> GoalFlyMeleeAttack<T> build(T mob) {
            return new GoalFlyMeleeAttack<T>(mob, this);
        }
    }

    private static enum State {
        APPROACH,
        STRIKE,
        RETREAT;

    }
}

