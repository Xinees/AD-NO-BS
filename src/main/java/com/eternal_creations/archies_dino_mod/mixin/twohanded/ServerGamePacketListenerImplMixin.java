/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.protocol.game.ServerboundPlayerActionPacket
 *  net.minecraft.network.protocol.game.ServerboundPlayerActionPacket$Action
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.network.ServerGamePacketListenerImpl
 *  net.minecraft.world.item.ItemStack
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.twohanded;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ServerGamePacketListenerImpl.class})
public abstract class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer f_9743_;

    @Inject(method={"handlePlayerAction"}, at={@At(value="HEAD")}, cancellable=true)
    public void archiesDinoMod$handleAction(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
        ItemStack mainHand;
        if (packet.m_134285_() == ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND && ServerGamePacketListenerImplMixin.archiesDinoMod$isTwoHanded(mainHand = this.f_9743_.m_21205_())) {
            this.f_9743_.f_8906_.m_215201_(packet.m_237987_());
            this.f_9743_.f_36096_.m_150429_();
            ci.cancel();
        }
    }

    @Unique
    private static boolean archiesDinoMod$isTwoHanded(ItemStack stack) {
        return stack.m_204117_(ADTags.Items.TWO_HANDED);
    }
}

