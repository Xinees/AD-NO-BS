/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.networking.clientbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AbstractClientboundMessage;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class SyncSettingsMessage
extends AbstractClientboundMessage {
    public static final ResourceLocation ID = Constants.prefix("sync_settings");
    private final ServerConfig config;

    public SyncSettingsMessage(ServerConfig config) {
        this.config = config;
    }

    public static SyncSettingsMessage decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.m_130260_();
        return new SyncSettingsMessage(ServerConfig.createFrom(tag == null ? new CompoundTag() : tag));
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_130079_(this.config.toTag());
    }

    @Override
    public void execute() {
        ServerConfig.setInstance(this.config);
    }
}

