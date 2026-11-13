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
    public static final ResourceSupplier<Block> OBSIDICERATOPS_EGG = ADBlocks.registerBlock("obsidiceratops_egg", ObsidiceratopsEggBlock::new);
    public static final ResourceSupplier<Block> THERIMOSS_EGG = ADBlocks.registerBlock("therimoss_egg", TherimossEggBlock::new);
    public static final ResourceSupplier<Block> SCULKRAPTOR_EGG = ADBlocks.registerBlock("sculkraptor_egg", SculkraptorEggBlock::new);
    public static final ResourceSupplier<TotemBlock> LUSH_TOTEM = ADBlocks.registerBlock("lush_totem", () -> new TotemBlock(ADEntityTypes.LUSHSAUR, ADSoundEvents.LUSHSAUR_IDLE, "master_lushsaur", () -> MobEffects.f_19598_, 0, ADTags.Items.LUSH_TOTEM_FUEL));
    public static final ResourceSupplier<TotemBlock> BURNING_TOTEM = ADBlocks.registerBlock("burning_totem", () -> new TotemBlock(ADEntityTypes.OBSIDICERATOPS, ADSoundEvents.OBSIDICERATOPS_IDLE, "master_obsidiceratops", () -> MobEffects.f_19607_, 0, ADTags.Items.BURNING_TOTEM_FUEL));
    public static final ResourceSupplier<TotemBlock> TROPIC_TOTEM = ADBlocks.registerBlock("tropic_totem", () -> new TotemBlock(ADEntityTypes.THERIMOSS, ADSoundEvents.THERIMOSS_IDLE, "master_therimoss", ADMobEffects.HUNGER_REDUCTION, 255, ADTags.Items.TROPIC_TOTEM_FUEL));
    public static final ResourceSupplier<TotemBlock> SOARING_TOTEM = ADBlocks.registerBlock("soaring_totem", () -> new TotemBlock(ADEntityTypes.CHORUSOATL, ADSoundEvents.CHORUSOATL_IDLE, "master_chorusoatl", ADMobEffects.FALL_NEGATION, 0, ADTags.Items.SOARING_TOTEM_FUEL));
    public static final ResourceSupplier<TotemBlock> TIDAL_TOTEM = ADBlocks.registerBlock("tidal_totem", () -> new TotemBlock(ADEntityTypes.CORALOSAUR, ADSoundEvents.CORALOSAUR_IDLE, "master_coralosaur", () -> MobEffects.f_19608_, 0, ADTags.Items.TIDAL_TOTEM_FUEL));
    public static final ResourceSupplier<TotemBlock> ECHO_TOTEM = ADBlocks.registerBlock("echo_totem", () -> new TotemBlock(ADEntityTypes.SCULKRAPTOR, ADSoundEvents.SCULKRAPTOR_IDLE, "master_sculkraptor", () -> MobEffects.f_19600_, 0, ADTags.Items.ECHO_TOTEM_FUEL));
    public static final ResourceSupplier<TotemBlock> FEARFUL_TOTEM = ADBlocks.registerBlock("fearful_totem", () -> new TotemBlock(ADEntityTypes.SCULKSAUR, ADSoundEvents.SCULKSAUR_IDLE, "master_sculksaur", () -> MobEffects.f_19606_, 0, ADTags.Items.FEARFUL_TOTEM_FUEL));
    public static final ResourceSupplier<Block> AMBER_ORE = ADBlocks.registerBlock("amber_ore", () -> new AmberOre(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(3.0f, 3.0f).m_60953_(state -> 10), (IntProvider)UniformInt.m_146622_((int)3, (int)7)));
    public static final ResourceSupplier<Block> RAW_AMBER_BLOCK = ADBlocks.registerBlock("raw_amber_block", () -> new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> POLISHED_AMBER = ADBlocks.registerBlock("polished_amber", () -> new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> POLISHED_AMBER_STAIRS = ADBlocks.registerBlock("polished_amber_stairs", () -> new StairBlock(POLISHED_AMBER.get().m_49966_(), BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)){});
    public static final ResourceSupplier<Block> POLISHED_AMBER_SLAB = ADBlocks.registerBlock("polished_amber_slab", () -> new SlabBlock(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> POLISHED_AMBER_WALL = ADBlocks.registerBlock("polished_amber_wall", () -> new WallBlock(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> CARVED_AMBER = ADBlocks.registerBlock("carved_amber", () -> new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> AMBER_BRICKS = ADBlocks.registerBlock("amber_bricks", () -> new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> AMBER_BRICK_STAIRS = ADBlocks.registerBlock("amber_brick_stairs", () -> new StairBlock(AMBER_BRICKS.get().m_49966_(), BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)){});
    public static final ResourceSupplier<Block> AMBER_BRICK_SLAB = ADBlocks.registerBlock("amber_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> AMBER_BRICK_WALL = ADBlocks.registerBlock("amber_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<Block> CRACKED_AMBER_BRICKS = ADBlocks.registerBlock("cracked_amber_bricks", () -> new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60999_().m_60913_(1.5f, 6.0f)));
    public static final ResourceSupplier<ChainBlock> AMBER_CHAIN = ADBlocks.registerBlock("amber_chain", () -> new ChainBlock(BlockBehaviour.Properties.m_284310_().m_280606_().m_60999_().m_60913_(5.0f, 6.0f).m_60918_(SoundType.f_56728_).m_60955_()));
    public static final ResourceSupplier<AmberLampBlock> AMBER_LAMP = ADBlocks.registerBlock("amber_lamp", () -> new AmberLampBlock(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_280606_().m_60999_().m_60978_(3.5f).m_60918_(SoundType.f_56762_).m_60953_(state -> 15).m_60955_().m_278166_(PushReaction.DESTROY)));

    public static void init() {
    }

    private static <T extends Block> ResourceSupplier<T> registerBlock(String id, Supplier<T> block) {
        return Services.COMMON.registerBlock(id, block);
    }
}

