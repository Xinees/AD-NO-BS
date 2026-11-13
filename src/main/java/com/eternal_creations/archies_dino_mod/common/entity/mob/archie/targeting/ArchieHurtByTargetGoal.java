/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.level.GameRules
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie.targeting;

import java.util.EnumSet;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchieHurtByTargetGoal
extends TargetGoal {
    private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.m_148352_().m_148355_().m_26893_();
    private int timestamp;

    public ArchieHurtByTargetGoal(PathfinderMob mob) {
        super((Mob)mob, true);
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean m_8036_() {
        int i = this.f_26135_.m_21213_();
        LivingEntity lastHurtByMob = this.f_26135_.m_21188_();
        if (i != this.timestamp && lastHurtByMob != null) {
            if (lastHurtByMob.m_6095_() == EntityType.f_20532_ && this.f_26135_.m_9236_().m_46469_().m_46207_(GameRules.f_46127_)) {
                return false;
            }
            return this.m_26150_(lastHurtByMob, HURT_BY_TARGETING);
        }
        return false;
    }

    public void m_8056_() {
        this.f_26135_.m_6710_(this.f_26135_.m_21188_());
        this.f_26137_ = this.f_26135_.m_5448_();
        this.timestamp = this.f_26135_.m_21213_();
        this.f_26138_ = 300;
        super.m_8056_();
    }
}

