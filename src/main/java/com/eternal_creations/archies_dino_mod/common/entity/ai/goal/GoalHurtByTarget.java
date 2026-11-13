/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GoalHurtByTarget
extends HurtByTargetGoal {
    private final Predicate<LivingEntity> targetPredicate;

    public GoalHurtByTarget(PathfinderMob mob, Predicate<LivingEntity> targetPredicate, Class<?> ... toIgnoreDamage) {
        super(mob, (Class[])toIgnoreDamage);
        this.targetPredicate = targetPredicate;
    }

    public GoalHurtByTarget(PathfinderMob mob, Class<?> ... toIgnoreDamage) {
        super(mob, (Class[])toIgnoreDamage);
        this.targetPredicate = target -> true;
    }

    protected boolean m_26150_(@Nullable LivingEntity potentialTarget, @NotNull TargetingConditions targetPredicate) {
        if (potentialTarget == null) {
            return false;
        }
        if (!this.targetPredicate.test(potentialTarget)) {
            return false;
        }
        if (!this.f_26135_.m_6779_(potentialTarget)) {
            return false;
        }
        return super.m_26150_(potentialTarget, targetPredicate);
    }

    public boolean m_8036_() {
        Pet pet;
        Mob mob = this.f_26135_;
        if (mob instanceof Pet && (pet = (Pet)mob).isRiddenByOwner()) {
            return false;
        }
        return super.m_8036_();
    }

    public boolean m_8045_() {
        Pet pet;
        Mob mob = this.f_26135_;
        if (mob instanceof Pet && (pet = (Pet)mob).isRiddenByOwner()) {
            return false;
        }
        if (this.f_26137_ != null) {
            if (!this.targetPredicate.test((LivingEntity)this.f_26135_)) {
                return false;
            }
            if (!this.f_26135_.m_6779_(this.f_26137_)) {
                return false;
            }
        }
        return super.m_8045_();
    }
}

