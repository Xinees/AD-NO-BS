/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.world.item.ItemCombatAction
 *  com.astemir.astemirlib.re.common.world.item.ItemCombatAction$Handler
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 *  org.spongepowered.asm.mixin.injection.callback.LocalCapture
 */
package com.eternal_creations.archies_dino_mod.mixin.client;

import com.astemir.astemirlib.re.common.world.item.ItemCombatAction;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={Minecraft.class})
public abstract class MinecraftMixin {
    @Shadow
    @Nullable
    public LocalPlayer f_91074_;
    @Shadow
    @Nullable
    public MultiPlayerGameMode f_91072_;

    @Inject(method={"startAttack"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;")}, cancellable=true, locals=LocalCapture.CAPTURE_FAILSOFT)
    private void onStartAttack(CallbackInfoReturnable<Boolean> cir, ItemStack stack, boolean flag) {
        Item item = stack.m_41720_();
        if (item instanceof ItemCombatAction.Handler) {
            ItemCombatAction.Handler handler = (ItemCombatAction.Handler)item;
            if (this.f_91074_ != null) {
                ItemCombatAction action = handler.getCombatAction(stack);
                if (action == null || !action.shouldUseCustomAttack()) {
                    return;
                }
                float strengthScale = this.f_91074_.m_36403_(Minecraft.m_91087_().m_91297_());
                if (strengthScale < 1.0f) {
                    cir.setReturnValue((Object)false);
                }
            }
        }
    }

    @Inject(method={"continueAttack"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/phys/BlockHitResult;getDirection()Lnet/minecraft/core/Direction;")}, cancellable=true)
    private void onContinueAttack(boolean leftClick, CallbackInfo ci) {
        ItemStack stack;
        Item item;
        if (this.f_91074_ != null && (item = (stack = this.f_91074_.m_21120_(InteractionHand.MAIN_HAND)).m_41720_()) instanceof ItemCombatAction.Handler) {
            ItemCombatAction.Handler handler = (ItemCombatAction.Handler)item;
            ItemCombatAction action = handler.getCombatAction(stack);
            if (action == null || !action.shouldUseCustomAttack()) {
                return;
            }
            float strengthScale = this.f_91074_.m_36403_(Minecraft.m_91087_().m_91297_());
            if (strengthScale < 1.0f && this.f_91072_ != null) {
                ci.cancel();
                this.f_91072_.m_105276_();
            }
        }
    }
}

