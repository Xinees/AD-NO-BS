/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.twohanded;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={AbstractContainerMenu.class})
public abstract class AbstractContainerMenuMixin {
    @Shadow
    @Final
    public NonNullList<Slot> f_38839_;

    @Inject(method={"clicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void archiesDinoMod$blockAllOffhandClicks(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
        boolean isSelectedHotbarSlot;
        boolean isOffhandSlot;
        ItemStack mainHand = player.m_21205_();
        ItemStack carried = ((AbstractContainerMenu)this).m_142621_();
        boolean mainHandTwoHanded = AbstractContainerMenuMixin.archiesDinoMod$isTwoHanded(mainHand);
        boolean carriedTwoHanded = AbstractContainerMenuMixin.archiesDinoMod$isTwoHanded(carried);
        if (clickType == ClickType.SWAP && button == 40 && mainHandTwoHanded) {
            player.f_36096_.m_150429_();
            ci.cancel();
            return;
        }
        if (slotId < 0 || slotId >= this.f_38839_.size()) {
            return;
        }
        Slot slot = (Slot)this.f_38839_.get(slotId);
        boolean bl = isOffhandSlot = slot.f_40218_ == player.m_150109_() && slot.m_150661_() == 40;
        if (isOffhandSlot && (mainHandTwoHanded || carriedTwoHanded)) {
            player.f_36096_.m_150429_();
            ci.cancel();
            return;
        }
        boolean bl2 = isSelectedHotbarSlot = slot.f_40218_ == player.m_150109_() && slot.m_150661_() == player.m_150109_().f_35977_;
        if (isSelectedHotbarSlot && carriedTwoHanded && !player.m_21206_().m_41619_()) {
            player.f_36096_.m_150429_();
            ci.cancel();
        }
    }

    @Inject(method={"clicked"}, at={@At(value="RETURN")})
    private void archiesDinoMod$enforceNoTwoHandedOffhand(int slotId, int button, ClickType clickType, Player player, CallbackInfo ci) {
        ItemStack offhand = player.m_21206_();
        if (AbstractContainerMenuMixin.archiesDinoMod$isTwoHanded(offhand)) {
            player.m_150109_().f_35976_.set(0, (Object)ItemStack.f_41583_);
            if (!player.m_9236_().f_46443_) {
                player.m_36176_(offhand, false);
            }
            player.f_36096_.m_150429_();
        }
    }

    @Unique
    private static boolean archiesDinoMod$isTwoHanded(ItemStack stack) {
        return stack != null && !stack.m_41619_() && stack.m_204117_(ADTags.Items.TWO_HANDED);
    }
}

