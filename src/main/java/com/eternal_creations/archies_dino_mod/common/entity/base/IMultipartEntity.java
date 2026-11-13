/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface IMultipartEntity {
    public IPartEntity<?>[] getIParts();

    public boolean hurtFromPart(IPartEntity<?> var1, DamageSource var2, float var3);

    public InteractionResult interactFromPart(IPartEntity<?> var1, Player var2, InteractionHand var3);

    default public boolean isPartOfMe(Entity entity) {
        for (IPartEntity<?> iPart : this.getIParts()) {
            if (iPart.getPartId() != entity.m_19879_()) continue;
            return true;
        }
        return false;
    }
}

