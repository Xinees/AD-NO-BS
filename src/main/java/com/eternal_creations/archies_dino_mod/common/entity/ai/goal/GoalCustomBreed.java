/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ai.goal.BreedGoal
 *  net.minecraft.world.entity.animal.Animal
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.mixin.BreedGoalAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;

public class GoalCustomBreed
extends BreedGoal {
    private final double range;

    public GoalCustomBreed(Animal animal, double speedModifier, double range) {
        super(animal, speedModifier);
        this.range = range;
    }

    public GoalCustomBreed(Animal animal, double speedModifier, double range, Class<? extends Animal> partnerClass) {
        super(animal, speedModifier, partnerClass);
        this.range = range;
    }

    public void m_8037_() {
        if (this.f_25115_ == null) {
            return;
        }
        this.f_25113_.m_21563_().m_24960_((Entity)this.f_25115_, 10.0f, (float)this.f_25113_.m_8132_());
        this.f_25113_.m_21573_().m_5624_((Entity)this.f_25115_, ((BreedGoalAccessor)((Object)this)).getSpeedModifier());
        int newLoveTime = ((BreedGoalAccessor)((Object)this)).getLoveTime() + 1;
        ((BreedGoalAccessor)((Object)this)).setLoveTime(newLoveTime);
        if (newLoveTime >= this.m_183277_(60) && this.f_25113_.m_20280_((Entity)this.f_25115_) < this.range) {
            this.m_8026_();
        }
    }
}

