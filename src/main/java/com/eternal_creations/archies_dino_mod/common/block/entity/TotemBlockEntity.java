/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.block.entity;

import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TotemBlockEntity
extends BlockEntity {
    public TotemBlockEntity(BlockPos pos, BlockState blockState) {
        super(ADBlockEntityTypes.TOTEM_BLOCK.get(), pos, blockState);
    }
}

