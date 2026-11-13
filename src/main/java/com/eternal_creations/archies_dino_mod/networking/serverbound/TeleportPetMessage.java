/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.ChatFormatting
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.chunk.ChunkStatus
 */
package com.eternal_creations.archies_dino_mod.networking.serverbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.ADPetData;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkStatus;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class TeleportPetMessage
extends AbstractServerboundMessage {
    public static final ResourceLocation ID = Constants.prefix("teleport_pet");
    private final UUID petUuid;
    private final BlockPos pos;
    private final ResourceLocation dimension;

    public TeleportPetMessage(ADPetData pet) {
        this(pet.uuid(), pet.pos(), pet.dimension());
    }

    public TeleportPetMessage(UUID petUuid, BlockPos pos, ResourceLocation dimension) {
        this.petUuid = petUuid;
        this.pos = pos;
        this.dimension = dimension;
    }

    public static TeleportPetMessage decode(FriendlyByteBuf buf) {
        return new TeleportPetMessage(buf.m_130259_(), buf.m_130135_(), buf.m_130281_());
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_130077_(this.petUuid);
        buf.m_130064_(this.pos);
        buf.m_130085_(this.dimension);
    }

    @Override
    public void execute(ServerPlayer player) {
        Entity entity;
        block10: {
            block9: {
                ResourceKey dimKey = ResourceKey.m_135785_((ResourceKey)Registries.f_256858_, (ResourceLocation)this.dimension);
                ServerLevel petLevel = player.f_8924_.m_129880_(dimKey);
                if (petLevel == null) {
                    player.m_213846_((Component)Component.m_237115_((String)"message.ec_ad.pet_world_unavailable").m_130940_(ChatFormatting.RED));
                    return;
                }
                ChunkPos chunkPos = new ChunkPos(this.pos);
                petLevel.m_6522_(chunkPos.f_45578_, chunkPos.f_45579_, ChunkStatus.f_62326_, true);
                entity = petLevel.m_8791_(this.petUuid);
                if (!(entity instanceof OwnableEntity)) break block9;
                OwnableEntity ownable = (OwnableEntity)entity;
                if (player.m_20148_().equals(ownable.m_21805_())) break block10;
            }
            player.m_213846_((Component)Component.m_237115_((String)"message.ec_ad.pet_not_found").m_130940_(ChatFormatting.RED));
            return;
        }
        ServerLevel playerLevel = (ServerLevel)player.m_9236_();
        if (entity.m_9236_() != playerLevel) {
            entity = entity.m_5489_(playerLevel);
        }
        if (entity != null) {
            entity.m_7678_(player.m_20185_(), player.m_20186_(), player.m_20189_(), player.m_146908_(), player.m_146909_());
            if (entity instanceof TamableAnimal) {
                TamableAnimal tamable = (TamableAnimal)entity;
                tamable.m_21839_(false);
                tamable.m_21837_(false);
            } else if (entity instanceof AbstractComplexMob) {
                AbstractComplexMob complexMob = (AbstractComplexMob)entity;
                complexMob.setSitting(false);
            }
            player.m_213846_((Component)Component.m_237110_((String)"message.ec_ad.pet_teleport_success", (Object[])new Object[]{entity.m_7755_()}).m_130940_(ChatFormatting.GREEN));
        }
    }
}

