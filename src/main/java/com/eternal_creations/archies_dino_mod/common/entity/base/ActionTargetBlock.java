/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

public interface ActionTargetBlock {
    default public boolean destroyActionTargetBlock(boolean drop) {
        BlockPos tempPos = this.getActionTargetBlock();
        if (tempPos != null) {
            Entity entity = (Entity)this;
            entity.m_9236_().m_46953_(tempPos, drop, entity);
            this.setActionTargetBlock(null);
            return true;
        }
        return false;
    }

    public void setActionTargetBlock(BlockPos var1);

    public BlockPos getActionTargetBlock();
}

