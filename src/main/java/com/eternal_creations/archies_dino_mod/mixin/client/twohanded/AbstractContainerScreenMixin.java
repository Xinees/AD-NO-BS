/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.client.twohanded;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.mixin.client.twohanded.SlotWrapperAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={AbstractContainerScreen.class})
public class AbstractContainerScreenMixin {
    @Unique
    private static final ResourceLocation archiesDinoMod$LOCKED_ICON = Constants.prefix("textures/gui/locked_slot.png");

    @Inject(method={"renderSlot"}, at={@At(value="TAIL")})
    private void archiesDinoMod$drawLockedSlot(GuiGraphics guiGraphics, Slot slot, CallbackInfo ci) {
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91074_ == null) {
            return;
        }
        LocalPlayer player = mc.f_91074_;
        ItemStack mainHand = player.m_21205_();
        if (AbstractContainerScreenMixin.archiesDinoMod$isTwoHanded(mainHand) && AbstractContainerScreenMixin.archiesDinoMod$isOffhandSlot(slot, (Player)player)) {
            guiGraphics.m_280168_().m_85836_();
            guiGraphics.m_280168_().m_252880_(0.0f, 0.0f, 300.0f);
            guiGraphics.m_280509_(slot.f_40220_, slot.f_40221_, slot.f_40220_ + 16, slot.f_40221_ + 16, 0xFFFFFFF);
            guiGraphics.m_280163_(archiesDinoMod$LOCKED_ICON, slot.f_40220_, slot.f_40221_, 0.0f, 0.0f, 16, 16, 16, 16);
            guiGraphics.m_280168_().m_85849_();
        }
    }

    @Unique
    private static boolean archiesDinoMod$isOffhandSlot(Slot slot, Player player) {
        Slot effective = slot;
        if (slot instanceof SlotWrapperAccessor) {
            SlotWrapperAccessor wrapper = (SlotWrapperAccessor)slot;
            effective = wrapper.archiesDinoMod$getTarget();
        }
        return effective.f_40218_ == player.m_150109_() && effective.m_150661_() == 40;
    }

    @Unique
    private static boolean archiesDinoMod$isTwoHanded(ItemStack stack) {
        return stack.m_204117_(ADTags.Items.TWO_HANDED);
    }
}

