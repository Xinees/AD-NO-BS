/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.trading.MerchantOffer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.archie;

import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={MerchantOffer.class})
public class MerchantOfferMixin {
    @Inject(method={"isRequiredItem"}, at={@At(value="HEAD")}, cancellable=true)
    private void onCheck(ItemStack offer, ItemStack cost, CallbackInfoReturnable<Boolean> cir) {
        if (cost.m_41720_() instanceof ADArmorItem && cost.m_150930_(offer.m_41720_()) && cost.m_41768_()) {
            cir.setReturnValue((Object)true);
        }
    }
}

