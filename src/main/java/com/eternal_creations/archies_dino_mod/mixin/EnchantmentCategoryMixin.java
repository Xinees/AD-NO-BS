/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.items.util.ADWeaponItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets={"net.minecraft.world.item.enchantment.EnchantmentCategory$6"})
public abstract class EnchantmentCategoryMixin {
    @Inject(method={"canEnchant"}, at={@At(value="RETURN")}, cancellable=true)
    private void canEnchantWeapon(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (item instanceof ADWeaponItem) {
            ADWeaponItem weaponItem = (ADWeaponItem)item;
            cir.setReturnValue((Object)weaponItem.canSweep());
        }
    }
}

