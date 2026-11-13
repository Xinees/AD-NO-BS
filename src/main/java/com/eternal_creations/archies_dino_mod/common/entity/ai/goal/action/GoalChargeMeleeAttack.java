/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.core.function.Condition
 *  com.astemir.astemirlib.re.core.math.AMath
 *  net.minecraft.sounds.SoundEvent
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
import com.astemir.astemirlib.re.core.math.AMath;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.GoalActionSelector;
import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import com.eternal_creations.archies_dino_mod.common.entity.base.ActionTargetEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.ChargeAttacker;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import org.jetbrains.annotations.Nullable;

public class GoalChargeMeleeAttack<T extends PathfinderMob & ActionTargetEntity>
extends GoalActionSelector {
    private final T mob;
    private final WrappedAction warnAction;
    private final Supplier<SoundEvent> chargeSound;
    private final Condition canAttack;
    private final double chargeSpeedModifier;
    private final int warnTicks;
    private final int chargeMaxTicks;
    private final int cooldownTicks;
    private final double triggerMinDistanceSqr;
    private AttackPhase state = AttackPhase.NONE;
    private int stateTicks;
    private int ticksUntilNextPathRecalculation;
    private long lastUseTime;

    public GoalChargeMeleeAttack(T mob, double speedModifier, double triggerMinDistance, int warnTicks, int chargeMaxTicks, int cooldownTicks, WrappedAction warnAction, List<WrappedAction> attackActions, Supplier<SoundEvent> chargeSound, Condition canAttack) {
        super(attackActions);
        this.mob = mob;
        this.chargeSpeedModifier = speedModifier;
        this.warnTicks = warnTicks;
        this.chargeMaxTicks = chargeMaxTicks;
        this.cooldownTicks = cooldownTicks;
        this.triggerMinDistanceSqr = triggerMinDistance * triggerMinDistance;
        this.warnAction = warnAction;
        this.canAttack = canAttack;
        this.chargeSound = chargeSound;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        long gameTime = this.mob.m_9236_().m_46467_();
        if (gameTime - this.lastUseTime < (long)this.cooldownTicks) {
            return false;
        }
        LivingEntity target = this.mob.m_5448_();
        if (target == null || !target.m_6084_() || !this.canAttack.check()) {
            return false;
        }
        double distanceSqr = this.mob.m_20280_((Entity)target);
        if (distanceSqr < this.triggerMinDistanceSqr) {
            return false;
        }
        @Nullable Path path = this.mob.m_21573_().m_6570_((Entity)target, 0);
        return path != null;
    }

    public boolean m_8045_() {
        LivingEntity target = this.mob.m_5448_();
        if (target == null || !target.m_6084_()) {
            return false;
        }
        return this.state != AttackPhase.NONE;
    }

    public void m_8056_() {
        this.transitionToAligning();
    }

    public void m_8041_() {
        this.state = AttackPhase.NONE;
        this.mob.m_21573_().m_26573_();
        ((ChargeAttacker)this.mob).setCharging(false);
        this.lastUseTime = this.mob.m_9236_().m_46467_();
    }

    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        super.m_8037_();
        LivingEntity target = this.mob.m_5448_();
        if (target == null) {
            this.m_8041_();
            return;
        }
        this.mob.m_21563_().m_24960_((Entity)target, 30.0f, 30.0f);
        this.stateTicks = Math.max(this.stateTicks - 1, 0);
        switch (this.state) {
            case ALIGNING: {
                this.mob.m_21573_().m_26573_();
                double dx = target.m_20185_() - this.mob.m_20185_();
                double dz = target.m_20189_() - this.mob.m_20189_();
                float targetYaw = AMath.atan2((float)((float)dz), (float)((float)dx)) * 57.295776f - 90.0f;
                float yawDifference = Math.abs(AMath.wrapDegrees((float)(targetYaw - this.mob.m_146908_())));
                if (!(yawDifference < 15.0f) && this.stateTicks > 0) break;
                this.transitionToWarning();
                break;
            }
            case WARNING: {
                this.mob.m_21573_().m_26573_();
                if (this.stateTicks > 0) break;
                this.transitionToCharging(target);
                break;
            }
            case CHARGING: {
                this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
                double distanceSqr = this.mob.m_20280_((Entity)target);
                if (this.mob.m_217066_(target)) {
                    this.transitionToAttacking(target, distanceSqr);
                    return;
                }
                if (this.stateTicks <= 0) {
                    this.m_8041_();
                    return;
                }
                if (this.ticksUntilNextPathRecalculation > 0) break;
                this.mob.m_21573_().m_5624_((Entity)target, this.chargeSpeedModifier);
                this.ticksUntilNextPathRecalculation = 4 + this.mob.m_217043_().m_188503_(5);
                break;
            }
            case ATTACKING: {
                this.m_8041_();
            }
        }
    }

    private void transitionToAligning() {
        this.state = AttackPhase.ALIGNING;
        this.stateTicks = 15;
        this.mob.m_21573_().m_26573_();
    }

    private void transitionToWarning() {
        this.state = AttackPhase.WARNING;
        this.stateTicks = this.warnTicks;
        this.mob.m_21573_().m_26573_();
        if (this.warnAction != null) {
            this.warnAction.run((SyncedActionHolder)this.mob);
        }
    }

    private void transitionToCharging(LivingEntity target) {
        this.state = AttackPhase.CHARGING;
        this.stateTicks = this.chargeMaxTicks;
        this.ticksUntilNextPathRecalculation = 0;
        this.mob.m_21573_().m_5624_((Entity)target, this.chargeSpeedModifier);
        ((ChargeAttacker)this.mob).setCharging(true);
        this.mob.m_216990_(this.chargeSound.get());
    }

    private void transitionToAttacking(LivingEntity target, double distanceSqr) {
        this.state = AttackPhase.ATTACKING;
        ((ActionTargetEntity)this.mob).setActionTargetEntity(target);
        WrappedAction action = this.getWeightedAction((Mob)this.mob, distanceSqr);
        if (action != null) {
            action.run((SyncedActionHolder)this.mob);
        }
        ((ChargeAttacker)this.mob).setCharging(false);
    }

    private static enum AttackPhase {
        NONE,
        ALIGNING,
        WARNING,
        CHARGING,
        ATTACKING;

    }
}

