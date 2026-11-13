/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 */
package com.eternal_creations.archies_dino_mod.platform.services;

import com.eternal_creations.archies_dino_mod.networking.clientbound.AbstractClientboundMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper {
    public void sendToServer(AbstractServerboundMessage var1);

    public void sendToClient(ServerPlayer var1, AbstractClientboundMessage var2);
}

