/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.LushsaurEntity;
import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class LushsaurGoalHideFromEntities
extends Goal {
    private final LushsaurEntity mob;
    private final double searchDistance;
    private final TargetingConditions targetConditions;
    private int safeTicks = 0;

    public LushsaurGoalHideFromEntities(LushsaurEntity mob, double searchDistance, Predicate<LivingEntity> threatPredicate) {
        this.mob = mob;
        this.searchDistance = searchDistance;
        this.targetConditions = TargetingConditions.m_148353_().m_26883_(searchDistance).m_26888_(threatPredicate);
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    public boolean m_8036_() {
        if (this.mob.isBuried() || this.mob.isTamed() || !LushsaurGoalHideFromEntities.isReadyToHide(this.mob)) {
            return false;
        }
        return this.hasThreatNearby();
    }

    public boolean m_8045_() {
        return this.mob.isBuried() || this.mob.actionBury.isActive() || this.mob.actionEmerge.isActive();
    }

    public void m_8056_() {
        this.safeTicks = 0;
        this.mob.m_21573_().m_26573_();
        if (!this.mob.actionBury.isActive()) {
            this.mob.playActionSynced(this.mob.actionBury);
        }
    }

    public void m_8041_() {
        this.safeTicks = 0;
    }

    public void m_8037_() {
        this.mob.m_21573_().m_26573_();
        if (this.mob.actionBury.isActive() || this.mob.actionEmerge.isActive()) {
            return;
        }
        if (this.mob.isBuried()) {
            if (this.hasThreatNearby()) {
                this.safeTicks = 0;
            } else {
                ++this.safeTicks;
                if (this.safeTicks >= 60) {
                    this.mob.playActionSynced(this.mob.actionEmerge);
                }
            }
        }
    }

    private boolean hasThreatNearby() {
        return this.mob.m_9236_().m_45963_(LivingEntity.class, this.targetConditions, (LivingEntity)this.mob, this.mob.m_20185_(), this.mob.m_20186_(), this.mob.m_20189_(), this.mob.m_20191_().m_82377_(this.searchDistance, this.searchDistance / 2.0, this.searchDistance)) != null;
    }

    private static boolean isReadyToHide(LushsaurEntity mob) {
        return mob.getDigCooldownTicks() <= 0 && mob.isNoActionActive();
    }
}

