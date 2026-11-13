/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.GoalSelector
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package com.eternal_creations.archies_dino_mod.mixin.coralosaur;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={Mob.class})
public interface MobAccessor {
    @Accessor(value="leashInfoTag")
    @Nullable
    public CompoundTag getLeashInfoTag();

    @Accessor(value="leashHolder")
    @Nullable
    public Entity getFieldLeashHolder();

    @Invoker(value="restoreLeashFromSave")
    public void invokeRestoreLeashFromSave();

    @Accessor(value="goalSelector")
    public GoalSelector getGoalSelector();

    @Accessor(value="targetSelector")
    public GoalSelector getTargetSelector();
}

