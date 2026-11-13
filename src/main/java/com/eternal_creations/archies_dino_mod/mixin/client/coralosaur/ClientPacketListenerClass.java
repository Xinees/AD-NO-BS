/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.network.PacketListener
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.PacketUtils
 *  net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket
 *  net.minecraft.util.thread.BlockableEventLoop
 *  net.minecraft.world.entity.Entity
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.client.coralosaur;

import com.eternal_creations.archies_dino_mod.util.IBoatWithLead;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ClientPacketListener.class})
public class ClientPacketListenerClass {
    @Shadow
    @Final
    private Minecraft f_104888_;
    @Shadow
    private ClientLevel f_104889_;

    @Inject(method={"handleEntityLinkPacket"}, at={@At(value="TAIL")})
    private void onLink(ClientboundSetEntityLinkPacket packet, CallbackInfo ci) {
        PacketUtils.m_131363_((Packet)packet, (PacketListener)((ClientPacketListener)this), (BlockableEventLoop)this.f_104888_);
        Entity entity = this.f_104889_.m_6815_(packet.m_133172_());
        if (entity instanceof IBoatWithLead) {
            IBoatWithLead boat = (IBoatWithLead)entity;
            boat.archies_Dino_Mod$setDelayedLeashHolderId(packet.m_133175_());
        }
    }
}

