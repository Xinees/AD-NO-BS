/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.client;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.GuidebookScreen;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LocalPlayer.class})
public abstract class LocalPlayerMixin {
    @Shadow
    @Final
    protected Minecraft f_108619_;

    @Inject(method={"openItemGui"}, at={@At(value="HEAD")})
    public void archiesDinoMod$onOpenBook(ItemStack stack, InteractionHand hand, CallbackInfo ci) {
        if (stack.m_150930_((Item)ADItems.GUIDEBOOK.get())) {
            this.f_108619_.m_91152_((Screen)new GuidebookScreen());
        }
    }
}

