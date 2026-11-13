/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.common.ToolAction
 *  net.minecraftforge.common.ToolActions
 *  net.minecraftforge.common.extensions.IForgeItem
 *  org.spongepowered.asm.mixin.Mixin
 */
package com.eternal_creations.archies_dino_mod.mixin.forge;

import com.eternal_creations.archies_dino_mod.common.items.util.ADWeaponItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={ADWeaponItem.class}, remap=false)
public abstract class ADWeaponItemMixin
implements IForgeItem {
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction)) {
            return ((ADWeaponItem)((Object)this)).canSweep();
        }
        return false;
    }
}

