/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai;

import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai.ChorusoatlAI;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;

public class ChorusoatlGoalFollowPack
extends Goal {
    private final ChorusoatlEntity mob;
    private ChorusoatlEntity leader;
    private final float minDistanceSqr;
    private final float maxDistanceSqr;
    private final float searchRange;
    private final double airFlySpeed;
    private final double landWalkSpeed;
    private int timeToRecalcPath;

    public ChorusoatlGoalFollowPack(ChorusoatlEntity mob, double landWalkSpeed, double airFlySpeed, float minDistance, float maxDistance, float searchRange) {
        this.mob = mob;
        this.landWalkSpeed = landWalkSpeed;
        this.airFlySpeed = airFlySpeed;
        this.minDistanceSqr = minDistance * minDistance;
        this.maxDistanceSqr = maxDistance * maxDistance;
        this.searchRange = searchRange;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (this.mob.m_5448_() != null || !ChorusoatlAI.isNoActionUsed(this.mob) || this.mob.m_27593_() || this.mob.getFlightPhase() == Flyer.FlightPhase.PREPARING_TO_LAND) {
            return false;
        }
        List list = this.mob.m_9236_().m_45976_(ChorusoatlEntity.class, this.mob.m_20191_().m_82377_((double)this.searchRange, 8.0, (double)this.searchRange));
        ChorusoatlEntity nearestAlly = null;
        double nearestDistSqr = Double.MAX_VALUE;
        for (ChorusoatlEntity ally : list) {
            double distSqr;
            if (ally == this.mob || !ally.m_6084_() || this.mob.isFlying() && !ally.isFlying() || !((distSqr = this.mob.m_20280_((Entity)ally)) < nearestDistSqr)) continue;
            nearestDistSqr = distSqr;
            nearestAlly = ally;
        }
        if (nearestAlly == null || nearestDistSqr < (double)this.minDistanceSqr || nearestDistSqr > (double)this.maxDistanceSqr) {
            return false;
        }
        this.leader = nearestAlly;
        return true;
    }

    public boolean m_8045_() {
        if (!this.leader.m_6084_() || this.mob.m_5448_() != null || !ChorusoatlAI.isNoActionUsed(this.mob) || this.mob.m_27593_()) {
            return false;
        }
        double distSqr = this.mob.m_20280_((Entity)this.leader);
        return distSqr >= (double)this.minDistanceSqr && distSqr <= (double)this.maxDistanceSqr;
    }

    public void m_8056_() {
        this.timeToRecalcPath = 0;
    }

    public void m_8041_() {
        this.leader = null;
        this.mob.m_21573_().m_26573_();
    }

    public void m_8037_() {
        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.m_183277_(this.mob.isFlying() ? 250 : 120);
            double speed = this.mob.isFlying() ? this.airFlySpeed : this.landWalkSpeed;
            this.mob.m_21573_().m_26519_(this.leader.m_20185_() + (double)Mth.m_216283_((RandomSource)this.mob.m_217043_(), (float)-5.0f, (float)5.0f), this.leader.m_20186_() + (double)Mth.m_216283_((RandomSource)this.mob.m_217043_(), (float)-5.0f, (float)5.0f), this.leader.m_20189_() + (double)Mth.m_216283_((RandomSource)this.mob.m_217043_(), (float)-5.0f, (float)5.0f), speed);
        }
    }
}

