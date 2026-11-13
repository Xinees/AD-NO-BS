/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Connection
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.players.PlayerList
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.networking.clientbound.SyncSettingsMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PlayerList.class})
public class PlayerListMixin {
    @Inject(method={"placeNewPlayer"}, at={@At(value="TAIL")})
    private void onPlaceNewPlayer(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        Services.NETWORK.sendToClient(player, new SyncSettingsMessage(ServerConfig.getInstance()));
    }
}

