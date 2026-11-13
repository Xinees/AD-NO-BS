/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai.ChorusoatlAI;
import java.util.EnumSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ChorusoatlGoalTakeOffForTarget
extends Goal {
    private final ChorusoatlEntity mob;
    private final double minDistanceSqr;

    public ChorusoatlGoalTakeOffForTarget(ChorusoatlEntity mob, double minDistance) {
        this.mob = mob;
        this.minDistanceSqr = minDistance * minDistance;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    public boolean m_8036_() {
        if (this.mob.isFlying() || !ChorusoatlAI.isNoActionUsed(this.mob) || this.mob.isSitting()) {
            return false;
        }
        LivingEntity target = this.mob.m_5448_();
        if (target == null || !target.m_6084_()) {
            LivingEntity owner = this.mob.m_269323_();
            if (owner != null) {
                target = owner;
            } else {
                return false;
            }
        }
        return this.mob.m_20280_((Entity)target) > this.minDistanceSqr;
    }

    public void m_8056_() {
        this.mob.playActionSynced(this.mob.actionUp);
    }
}

