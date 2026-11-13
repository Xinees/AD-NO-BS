/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 */
package com.eternal_creations.archies_dino_mod.common.block.entity;

import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.block.entity.BlockEntityTypeSupplier;
import com.eternal_creations.archies_dino_mod.common.block.entity.TotemBlockEntity;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityChorusoatlEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityLushsaurEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityObsidiceratopsEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntitySculkraptorEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityTherimossEgg;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ADBlockEntityTypes {
    public static final ResourceSupplier<BlockEntityType<BlockEntityLushsaurEgg>> LUSHSAUR_EGG = ADBlockEntityTypes.registerBlockEntity("lushsaur_egg", BlockEntityLushsaurEgg::new, ADBlocks.LUSHSAUR_EGG);
    public static final ResourceSupplier<BlockEntityType<BlockEntityChorusoatlEgg>> CHORUSOATL_EGG = ADBlockEntityTypes.registerBlockEntity("chorusoatl_egg", BlockEntityChorusoatlEgg::new, ADBlocks.CHORUSOATL_EGG);
    public static final ResourceSupplier<BlockEntityType<BlockEntityObsidiceratopsEgg>> OBSIDICERATOPS_EGG = null;
    public static final ResourceSupplier<BlockEntityType<BlockEntitySculkraptorEgg>> SCULKRAPTOR_EGG = null;
    public static final ResourceSupplier<BlockEntityType<BlockEntityTherimossEgg>> THERIMOSS_EGG = null;
    public static final ResourceSupplier<BlockEntityType<TotemBlockEntity>> TOTEM_BLOCK = null;

    public static void init() {
    }

    @SafeVarargs
    private static <T extends BlockEntity, G extends Block> ResourceSupplier<BlockEntityType<T>> registerBlockEntity(String id, BlockEntityTypeSupplier<T> supplier, ResourceSupplier<G> ... blocks) {
        return Services.COMMON.registerBlockEntityType(id, supplier, blocks);
    }
}

