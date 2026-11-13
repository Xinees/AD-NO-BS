/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.inventory.Slot
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package com.eternal_creations.archies_dino_mod.mixin.client.twohanded;

import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets={"net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen$SlotWrapper"})
public interface SlotWrapperAccessor {
    @Accessor(value="target")
    public Slot archiesDinoMod$getTarget();
}

