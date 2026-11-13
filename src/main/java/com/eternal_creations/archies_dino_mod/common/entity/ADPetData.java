/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.common.entity;

import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record ADPetData(UUID uuid, Component name, BlockPos pos, ResourceLocation dimension) {
    public void write(FriendlyByteBuf buf) {
        buf.m_130077_(this.uuid);
        buf.m_130083_(this.name);
        buf.m_130064_(this.pos);
        buf.m_130085_(this.dimension);
    }

    public static ADPetData read(FriendlyByteBuf buf) {
        UUID uuid = buf.m_130259_();
        Component name = buf.m_130238_();
        BlockPos pos = buf.m_130135_();
        ResourceLocation dimension = buf.m_130281_();
        return new ADPetData(uuid, name, pos, dimension);
    }
}

