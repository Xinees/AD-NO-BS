/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.MerchantContainer
 *  net.minecraft.world.inventory.MerchantMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.archie;

import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={MerchantMenu.class})
public abstract class MerchantMenuMixin
extends AbstractContainerMenu {
    @Shadow
    @Final
    private MerchantContainer f_40028_;

    protected MerchantMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @Inject(method={"moveFromInventoryToPaymentSlot"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMoveTo(int paymentSlotIndex, ItemStack cost, CallbackInfo ci) {
        if (cost.m_41720_() instanceof ADArmorItem && cost.m_41768_()) {
            ci.cancel();
            for (int i = 3; i < 39; ++i) {
                ItemStack offer = ((Slot)this.f_38839_.get(i)).m_7993_();
                if (offer.m_41619_() || !cost.m_150930_(offer.m_41720_())) continue;
                ItemStack itemstack1 = this.f_40028_.m_8020_(paymentSlotIndex);
                int j = itemstack1.m_41619_() ? 0 : itemstack1.m_41613_();
                int k = Math.min(cost.m_41741_() - j, offer.m_41613_());
                ItemStack itemstack2 = offer.m_41777_();
                int l = j + k;
                offer.m_41774_(k);
                itemstack2.m_41764_(l);
                this.f_40028_.m_6836_(paymentSlotIndex, itemstack2);
                if (l >= cost.m_41741_()) break;
            }
        }
    }
}

