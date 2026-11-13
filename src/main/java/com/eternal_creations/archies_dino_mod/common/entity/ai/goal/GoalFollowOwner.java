/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class GoalFollowOwner<T extends PathfinderMob>
extends Goal {
    private final T tamable;
    private LivingEntity owner;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final double stopDistance;
    private final double startDistance;
    private float oldWaterCost;

    public GoalFollowOwner(T tamable, double speedModifier, double startDistance, double stopDistance) {
        this.tamable = tamable;
        this.speedModifier = speedModifier;
        this.navigation = tamable.m_21573_();
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        Flyer flyer;
        LivingEntity livingentity = ((OwnableEntity)this.tamable).m_269323_();
        if (livingentity == null || livingentity.m_5833_() || this.unableToMove()) {
            return false;
        }
        T t = this.tamable;
        if (t instanceof Flyer && (flyer = (Flyer)t).isFlying() && livingentity.m_20096_() && this.tamable.m_20280_((Entity)livingentity) > this.stopDistance * this.stopDistance) {
            this.owner = livingentity;
            return true;
        }
        if (this.tamable.m_20280_((Entity)livingentity) < this.startDistance * this.startDistance) {
            return false;
        }
        this.owner = livingentity;
        return true;
    }

    public boolean m_8045_() {
        Flyer flyer;
        if (this.owner == null || !this.owner.m_6084_() || this.owner.m_5833_() || this.unableToMove()) {
            return false;
        }
        T t = this.tamable;
        if (t instanceof Flyer && (flyer = (Flyer)t).isFlying() && this.owner.m_20096_()) {
            return !this.tamable.m_20096_();
        }
        return this.tamable.m_20280_((Entity)this.owner) > this.stopDistance * this.stopDistance;
    }

    private boolean unableToMove() {
        return this.tamable.m_20159_() || this.tamable.m_21523_() || ((Pet)this.tamable).isSitting();
    }

    public void m_8056_() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tamable.m_21439_(BlockPathTypes.WATER);
        this.tamable.m_21441_(BlockPathTypes.WATER, 0.0f);
    }

    public void m_8041_() {
        Flyer flyer;
        T t = this.tamable;
        if (t instanceof Flyer && (flyer = (Flyer)t).isFlying() && this.owner != null && this.owner.m_20096_()) {
            flyer.setFlightPhase(Flyer.FlightPhase.PREPARING_TO_LAND);
        }
        this.owner = null;
        this.navigation.m_26573_();
        this.tamable.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
    }

    public void m_8037_() {
        if (this.owner == null) {
            return;
        }
        this.tamable.m_21563_().m_24960_((Entity)this.owner, 10.0f, (float)this.tamable.m_8132_());
        T t = this.tamable;
        if (t instanceof Flyer) {
            Flyer flyer = (Flyer)t;
            this.handleFlyerLogic(flyer);
        }
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.m_183277_(10);
            this.navigation.m_5624_((Entity)this.owner, this.speedModifier);
        }
    }

    private void handleFlyerLogic(Flyer flyer) {
        if (this.tamable.m_20096_() && flyer.isFlying()) {
            flyer.setFlying(false);
            flyer.setFlightPhase(Flyer.FlightPhase.NONE);
            return;
        }
        if (this.owner.m_20096_() && flyer.isFlying()) {
            double distanceSq = this.tamable.m_20280_((Entity)this.owner);
            if (distanceSq <= this.startDistance * this.startDistance && !flyer.isPreparingToLand()) {
                flyer.setFlightPhase(Flyer.FlightPhase.PREPARING_TO_LAND);
            }
        } else if (!this.owner.m_20096_() && flyer.isPreparingToLand()) {
            flyer.setFlightPhase(Flyer.FlightPhase.NONE);
        }
    }
}

