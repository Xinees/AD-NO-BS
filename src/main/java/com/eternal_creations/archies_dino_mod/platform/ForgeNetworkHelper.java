/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraftforge.network.NetworkDirection
 *  net.minecraftforge.network.NetworkEvent$Context
 *  net.minecraftforge.network.NetworkRegistry
 *  net.minecraftforge.network.PacketDistributor
 *  net.minecraftforge.network.simple.SimpleChannel
 */
package com.eternal_creations.archies_dino_mod.platform;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AbstractClientboundMessage;
import com.eternal_creations.archies_dino_mod.networking.clientbound.ArchieSpeechMessage;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AskShooterIfHitBoneMessage;
import com.eternal_creations.archies_dino_mod.networking.clientbound.ScorchingHornScreenMessage;
import com.eternal_creations.archies_dino_mod.networking.clientbound.SyncSettingsMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.CoralosaurChestMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.MountControlMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.PopZitMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.TalkToArchieMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.TeleportPetMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.UseArmorAbilityMessage;
import com.eternal_creations.archies_dino_mod.platform.services.INetworkHelper;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ForgeNetworkHelper
implements INetworkHelper {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel((ResourceLocation)Constants.prefix("main"), () -> "1", "1"::equals, "1"::equals);

    public static void init() {
        int id = -1;
        CHANNEL.registerMessage(++id, UseArmorAbilityMessage.class, UseArmorAbilityMessage::encode, UseArmorAbilityMessage::decode, Handler::onMessage, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(++id, CoralosaurChestMessage.class, CoralosaurChestMessage::encode, CoralosaurChestMessage::decode, Handler::onMessage, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(++id, TalkToArchieMessage.class, TalkToArchieMessage::encode, TalkToArchieMessage::decode, Handler::onMessage, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(++id, TeleportPetMessage.class, TeleportPetMessage::encode, TeleportPetMessage::decode, Handler::onMessage, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(++id, MountControlMessage.class, MountControlMessage::encode, MountControlMessage::decode, Handler::onMessage, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(++id, PopZitMessage.class, PopZitMessage::encode, PopZitMessage::decode, Handler::onMessage, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(++id, SyncSettingsMessage.class, SyncSettingsMessage::encode, SyncSettingsMessage::decode, ForgeNetworkHelper::toClientMessage, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(++id, ScorchingHornScreenMessage.class, ScorchingHornScreenMessage::encode, ScorchingHornScreenMessage::decode, ForgeNetworkHelper::toClientMessage, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(++id, AskShooterIfHitBoneMessage.class, AskShooterIfHitBoneMessage::encode, AskShooterIfHitBoneMessage::decode, ForgeNetworkHelper::toClientMessage, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(++id, ArchieSpeechMessage.class, ArchieSpeechMessage::encode, ArchieSpeechMessage::decode, ForgeNetworkHelper::toClientMessage, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    @Override
    public void sendToServer(AbstractServerboundMessage payload) {
        CHANNEL.send(PacketDistributor.SERVER.noArg(), (Object)payload);
    }

    @Override
    public void sendToClient(ServerPlayer player, AbstractClientboundMessage payload) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), (Object)payload);
    }

    public static void toClientMessage(AbstractClientboundMessage message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(message::execute);
        ctx.get().setPacketHandled(true);
    }

    public static class Handler {
        public static void onMessage(AbstractServerboundMessage message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> Optional.ofNullable(((NetworkEvent.Context)ctx.get()).getSender()).ifPresent(message::execute));
            ctx.get().setPacketHandled(true);
        }
    }
}

