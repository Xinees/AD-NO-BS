/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package com.eternal_creations.archies_dino_mod.networking.serverbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class TalkToArchieMessage
extends AbstractServerboundMessage {
    public static final ResourceLocation ID = Constants.prefix("talk_archie");
    public static final int ACCEPT = 0;
    public static final int DENY = 1;
    public static final int TRADE = 2;
    private static final Codec<TalkToArchieMessage> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group((App)Codec.INT.fieldOf("archie").forGetter(talkToArchieMessage -> talkToArchieMessage.archie), (App)Codec.INT.fieldOf("option").forGetter(talkToArchieMessage -> talkToArchieMessage.option)).apply((Applicative)recordCodecBuilder, TalkToArchieMessage::new));
    private final int archie;
    private final int option;

    public TalkToArchieMessage(int archie, int option) {
        this.archie = archie;
        this.option = option;
    }

    public static TalkToArchieMessage decode(FriendlyByteBuf buf) {
        return (TalkToArchieMessage)buf.m_271872_(CODEC);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_272073_(CODEC, (Object)this);
    }

    @Override
    public void execute(ServerPlayer player) {
        ArchieEntity archieEntity;
        ArchieEntity archie;
        ServerLevel level = player.m_284548_();
        Entity entity = level.m_6815_(this.archie);
        ArchieEntity archieEntity2 = archie = entity instanceof ArchieEntity ? (archieEntity = (ArchieEntity)entity) : null;
        if (archie == null) {
            return;
        }
        switch (this.option) {
            case 0: {
                archie.tame((Player)player);
                break;
            }
            case 1: {
                archie.deny();
                if (archie.m_269323_() != player) break;
                archie.setTamed(false);
                break;
            }
            case 2: {
                archie.m_7189_((Player)player);
                archie.m_45301_((Player)player, archie.m_5446_(), 1);
            }
        }
    }
}

