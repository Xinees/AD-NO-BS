/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.valueproviders.IntProvider
 *  net.minecraft.util.valueproviders.UniformInt
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.ChainBlock
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.properties.NoteBlockInstrument
 *  net.minecraft.world.level.material.MapColor
 *  net.minecraft.world.level.material.PushReaction
 */
package com.eternal_creations.archies_dino_mod.common.block;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.AmberLampBlock;
import com.eternal_creations.archies_dino_mod.common.block.AmberOre;
import com.eternal_creations.archies_dino_mod.common.block.TotemBlock;
import com.eternal_creations.archies_dino_mod.common.block.eggs.ChorusoatlEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.eggs.LushsaurEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.eggs.ObsidiceratopsEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.eggs.SculkraptorEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.eggs.TherimossEggBlock;
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADBlocks {
    public static final ResourceSupplier<Block> LUSHSAUR_EGG = ADBlocks.registerBlock("lushsaur_egg", LushsaurEggBlock::new);
    public static final ResourceSupplier<Block> CHORUSOATL_EGG = ADBlocks.registerBlock("chorusoatl_egg", ChorusoatlEggBlock::new);
    public static final ResourceSupplier<Block> OBSIDICERATOPS_EGG = null;
    public static final ResourceSupplier<Block> THERIMOSS_EGG = null;
    public static final ResourceSupplier<Block> SCULKRAPTOR_EGG = null;
    public static final ResourceSupplier<TotemBlock> LUSH_TOTEM = null;
    public static final ResourceSupplier<TotemBlock> BURNING_TOTEM = null;
    public static final ResourceSupplier<TotemBlock> TROPIC_TOTEM = null;
    public static final ResourceSupplier<TotemBlock> SOARING_TOTEM = null;
    public static final ResourceSupplier<TotemBlock> TIDAL_TOTEM = null;
    public static final ResourceSupplier<TotemBlock> ECHO_TOTEM = null;
    public static final ResourceSupplier<TotemBlock> FEARFUL_TOTEM = null;
    public static final ResourceSupplier<Block> AMBER_ORE = null;
    public static final ResourceSupplier<Block> RAW_AMBER_BLOCK = null;
    public static final ResourceSupplier<Block> POLISHED_AMBER = null;
    public static final ResourceSupplier<Block> POLISHED_AMBER_STAIRS = null;
    public static final ResourceSupplier<Block> POLISHED_AMBER_SLAB = null;
    public static final ResourceSupplier<Block> POLISHED_AMBER_WALL = null;
    public static final ResourceSupplier<Block> CARVED_AMBER = null;
    public static final ResourceSupplier<Block> AMBER_BRICKS = null;
    public static final ResourceSupplier<Block> AMBER_BRICK_STAIRS = null;
    public static final ResourceSupplier<Block> AMBER_BRICK_SLAB = null;
    public static final ResourceSupplier<Block> AMBER_BRICK_WALL = null;
    public static final ResourceSupplier<Block> CRACKED_AMBER_BRICKS = null;
    public static final ResourceSupplier<ChainBlock> AMBER_CHAIN = null;
    public static final ResourceSupplier<AmberLampBlock> AMBER_LAMP = null;

    public static void init() {
    }

    private static <T extends Block> ResourceSupplier<T> registerBlock(String id, Supplier<T> block) {
        return Services.COMMON.registerBlock(id, block);
    }
}

