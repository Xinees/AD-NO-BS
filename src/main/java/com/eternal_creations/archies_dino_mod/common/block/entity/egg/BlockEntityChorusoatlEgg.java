/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.block.entity.egg;

import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityChorusoatlEgg
extends BlockEntity {
    public BlockEntityChorusoatlEgg(BlockPos pos, BlockState blockState) {
        super(ADBlockEntityTypes.CHORUSOATL_EGG.get(), pos, blockState);
    }
}

