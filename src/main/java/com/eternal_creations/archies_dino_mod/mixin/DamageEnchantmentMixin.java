/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.DamageEnchantment
 *  net.minecraft.world.item.enchantment.Enchantments
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.items.util.ADWeaponItem;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={DamageEnchantment.class})
public class DamageEnchantmentMixin {
    @Inject(method={"canEnchant"}, at={@At(value="HEAD")}, cancellable=true)
    private void onCanEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Item item = stack.m_41720_();
        if (item instanceof ADWeaponItem) {
            ADWeaponItem weaponItem = (ADWeaponItem)item;
            cir.setReturnValue((Object)(Enchantments.f_44977_ != (DamageEnchantment)this || weaponItem.canSweep() ? 1 : 0));
        }
    }
}

