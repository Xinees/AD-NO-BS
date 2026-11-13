/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class GoalOwnerHurtByTarget<T extends PathfinderMob>
extends TargetGoal {
    private final T tameAnimal;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public GoalOwnerHurtByTarget(T tameAnimal) {
        super(tameAnimal, false);
        this.tameAnimal = tameAnimal;
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean m_8036_() {
        if (((Pet)this.tameAnimal).isRiddenByOwner()) {
            return false;
        }
        if (((Pet)this.tameAnimal).isTamed()) {
            LivingEntity livingentity = ((OwnableEntity)this.tameAnimal).m_269323_();
            if (livingentity == null) {
                return false;
            }
            this.ownerLastHurtBy = livingentity.m_21188_();
            int i = livingentity.m_21213_();
            return i != this.timestamp && this.m_26150_(this.ownerLastHurtBy, TargetingConditions.f_26872_);
        }
        return false;
    }

    public boolean m_8045_() {
        if (((Pet)this.tameAnimal).isRiddenByOwner()) {
            return false;
        }
        return super.m_8045_();
    }

    public void m_8056_() {
        this.f_26135_.m_6710_(this.ownerLastHurtBy);
        LivingEntity livingentity = ((OwnableEntity)this.tameAnimal).m_269323_();
        if (livingentity != null) {
            this.timestamp = livingentity.m_21213_();
        }
        super.m_8056_();
    }
}

