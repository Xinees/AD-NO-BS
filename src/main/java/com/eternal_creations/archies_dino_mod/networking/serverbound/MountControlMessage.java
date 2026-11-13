/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 */
package com.eternal_creations.archies_dino_mod.networking.serverbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.networking.serverbound.AbstractServerboundMessage;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class MountControlMessage
extends AbstractServerboundMessage {
    public static final ResourceLocation ID = Constants.prefix("mount_control");
    private final int mountId;
    private final Mount.AttackType attackType;

    public MountControlMessage(int mountId, Mount.AttackType attackType) {
        this.mountId = mountId;
        this.attackType = attackType;
    }

    public static MountControlMessage decode(FriendlyByteBuf buf) {
        return new MountControlMessage(buf.readInt(), (Mount.AttackType)buf.m_130066_(Mount.AttackType.class));
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.mountId);
        buf.m_130068_((Enum)this.attackType);
    }

    @Override
    public void execute(ServerPlayer player) {
        Mount mount;
        ServerLevel level = player.m_284548_();
        Entity entity = level.m_6815_(this.mountId);
        if (entity instanceof Mount && (mount = (Mount)entity).mount().isNoPassengerCooldown()) {
            mount.onRiderAttack(entity.m_6688_(), this.attackType);
        }
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }
}

