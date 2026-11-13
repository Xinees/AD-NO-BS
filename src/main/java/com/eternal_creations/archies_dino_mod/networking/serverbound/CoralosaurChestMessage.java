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
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.Container
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ChestMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 */
package com.eternal_creations.archies_dino_mod.networking.serverbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class CoralosaurChestMessage
extends AbstractServerboundMessage {
    public static final ResourceLocation ID = Constants.prefix("open_coralosaur_chest");
    private static final Codec<CoralosaurChestMessage> CODEC = RecordCodecBuilder.create(recordCodecBuilder -> recordCodecBuilder.group((App)Codec.INT.fieldOf("id").forGetter(CoralosaurChestMessage::getId), (App)Codec.INT.fieldOf("chest").forGetter(CoralosaurChestMessage::getChest), (App)Codec.BOOL.fieldOf("add").forGetter(CoralosaurChestMessage::isAdd)).apply((Applicative)recordCodecBuilder, CoralosaurChestMessage::new));
    private final int id;
    private final int chest;
    private final boolean add;

    public CoralosaurChestMessage(int id, int chest, boolean add) {
        this.id = id;
        this.chest = chest;
        this.add = add;
    }

    public static CoralosaurChestMessage decode(FriendlyByteBuf buf) {
        return (CoralosaurChestMessage)buf.m_271872_(CODEC);
    }

    public int getId() {
        return this.id;
    }

    public int getChest() {
        return this.chest;
    }

    public boolean isAdd() {
        return this.add;
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
        Entity entity = player.m_9236_().m_6815_(this.id);
        if (entity instanceof CoralosaurEntity) {
            CoralosaurEntity coralosaur = (CoralosaurEntity)entity;
            if (this.isAdd() && !coralosaur.hasChest(this.chest)) {
                ItemStack stack = player.m_21205_();
                if (!stack.m_150930_(Items.f_42009_)) {
                    stack = player.m_21206_();
                }
                if (stack.m_150930_(Items.f_42009_)) {
                    stack.m_41774_(1);
                    coralosaur.setChest(this.chest, true);
                }
            } else if (coralosaur.hasChest(this.chest)) {
                player.m_5893_((MenuProvider)new CoralosaurMenu(coralosaur, coralosaur.m_5446_(), this.chest));
            }
        }
    }

    private record CoralosaurMenu(CoralosaurEntity coralosaur, Component name, int chest) implements MenuProvider
    {
        public Component m_5446_() {
            return this.name;
        }

        public AbstractContainerMenu m_7208_(int containerId, Inventory playerInventory, Player player) {
            return ChestMenu.m_39246_((int)containerId, (Inventory)playerInventory, (Container)((Container)this.coralosaur.containers.get(this.chest)));
        }
    }
}

