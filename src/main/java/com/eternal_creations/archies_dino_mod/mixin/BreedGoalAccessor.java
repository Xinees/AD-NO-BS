/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.goal.BreedGoal
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package com.eternal_creations.archies_dino_mod.mixin;

import net.minecraft.world.entity.ai.goal.BreedGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={BreedGoal.class})
public interface BreedGoalAccessor {
    @Accessor(value="speedModifier")
    public double getSpeedModifier();

    @Accessor(value="loveTime")
    public int getLoveTime();

    @Accessor(value="loveTime")
    public void setLoveTime(int var1);
}

