/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action;

import com.eternal_creations.archies_dino_mod.common.entity.ai.goal.action.WrappedAction;
import java.util.List;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class GoalActionSelector
extends Goal {
    private final List<WrappedAction> actions;

    public GoalActionSelector(List<WrappedAction> actions) {
        this.actions = actions;
    }

    public boolean isAnyActionPossible(double distance) {
        for (WrappedAction action : this.actions) {
            if (!action.test(distance)) continue;
            return true;
        }
        return false;
    }

    public WrappedAction getWeightedAction(Mob mob, double distance) {
        int size = this.actions.size();
        if (size == 0) {
            return null;
        }
        double totalWeight = 0.0;
        int validCount = 0;
        WrappedAction lastValidAction = null;
        for (WrappedAction weightedAction : this.actions) {
            if (!weightedAction.test(distance)) continue;
            totalWeight += (double)weightedAction.weight();
            ++validCount;
            lastValidAction = weightedAction;
        }
        if (validCount == 0) {
            return null;
        }
        if (validCount == 1) {
            return lastValidAction;
        }
        if (totalWeight <= 0.0) {
            for (int i = 0; i < size; ++i) {
                WrappedAction weightedAction;
                weightedAction = this.actions.get(i);
                if (!weightedAction.test(distance)) continue;
                return weightedAction;
            }
            return null;
        }
        double randomValue = mob.m_217043_().m_188500_() * totalWeight;
        double currentSum = 0.0;
        for (int i = 0; i < size; ++i) {
            WrappedAction weightedAction = this.actions.get(i);
            if (!weightedAction.test(distance) || !(randomValue <= (currentSum += (double)weightedAction.weight()))) continue;
            return weightedAction;
        }
        return lastValidAction;
    }

    public void m_8037_() {
        super.m_8037_();
        for (WrappedAction action : this.actions) {
            action.tick();
        }
    }
}

