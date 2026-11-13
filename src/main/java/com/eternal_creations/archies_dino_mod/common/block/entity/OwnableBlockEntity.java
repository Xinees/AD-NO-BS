/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.EntityGetter
 */
package com.eternal_creations.archies_dino_mod.common.block.entity;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.EntityGetter;

public interface OwnableBlockEntity {
    @Nullable
    public UUID getOwnerUUID();

    public void setOwnerUUID(UUID var1);

    @Nullable
    public EntityGetter level();

    @Nullable
    default public Player getOwner() {
        EntityGetter level = this.level();
        if (level == null) {
            return null;
        }
        UUID uuid = this.getOwnerUUID();
        return uuid == null ? null : level.m_46003_(uuid);
    }
}

