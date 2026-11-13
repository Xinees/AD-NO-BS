/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 */
package com.eternal_creations.archies_dino_mod.networking.serverbound;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractServerboundMessage {
    public abstract ResourceLocation getID();

    public abstract void encode(FriendlyByteBuf var1);

    public abstract void execute(ServerPlayer var1);
}

