/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class GoalFollowParent<T extends Animal>
extends Goal {
    private final T animal;
    @Nullable
    private Animal parent;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final double stopDistance;
    private final double startDistance;
    private float oldWaterCost;

    public GoalFollowParent(T animal, double speedModifier, double startDistance, double stopDistance) {
        this.animal = animal;
        this.speedModifier = speedModifier;
        this.navigation = animal.m_21573_();
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (this.animal.m_146764_() >= 0) {
            return false;
        }
        if (this.unableToMove()) {
            return false;
        }
        List list = this.animal.m_9236_().m_45976_(this.animal.getClass(), this.animal.m_20191_().m_82377_(8.0, 4.0, 8.0));
        Animal nearestParent = null;
        double nearestDistanceSqr = Double.MAX_VALUE;
        for (Animal potentialParent : list) {
            double distanceSqr;
            if (potentialParent.m_146764_() < 0 || !((distanceSqr = this.animal.m_20280_((Entity)potentialParent)) < nearestDistanceSqr)) continue;
            nearestDistanceSqr = distanceSqr;
            nearestParent = potentialParent;
        }
        if (nearestParent == null) {
            return false;
        }
        if (nearestDistanceSqr < this.startDistance * this.startDistance) {
            return false;
        }
        this.parent = nearestParent;
        return true;
    }

    public boolean m_8045_() {
        if (this.animal.m_146764_() >= 0) {
            return false;
        }
        if (this.parent == null || !this.parent.m_6084_()) {
            return false;
        }
        if (this.unableToMove()) {
            return false;
        }
        double distanceSqr = this.animal.m_20280_((Entity)this.parent);
        return distanceSqr >= this.stopDistance * this.stopDistance && distanceSqr <= 256.0;
    }

    private boolean unableToMove() {
        return this.animal.m_20159_() || this.animal.m_21523_();
    }

    public void m_8056_() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.animal.m_21439_(BlockPathTypes.WATER);
        this.animal.m_21441_(BlockPathTypes.WATER, 0.0f);
    }

    public void m_8041_() {
        this.parent = null;
        this.navigation.m_26573_();
        this.animal.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
    }

    public void m_8037_() {
        if (this.parent != null) {
            this.animal.m_21563_().m_24960_((Entity)this.parent, 30.0f, (float)this.animal.m_8132_());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.m_183277_(10);
                this.navigation.m_5624_((Entity)this.parent, this.speedModifier);
            }
        }
    }
}

