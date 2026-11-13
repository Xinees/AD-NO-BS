/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.common.Tags$Blocks
 *  net.minecraftforge.common.data.BlockTagsProvider
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen.tag;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.data.util.PlatformSpecific;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesBlockTagGen
extends BlockTagsProvider
implements PlatformSpecific {
    private PlatformSpecific.Environment environment;

    public ArchiesBlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, "ec_ad", helper);
    }

    protected void m_6577_(HolderLookup.Provider provider) {
        if (this.isForge()) {
            this.addForgeTags(provider);
        }
        this.m_206424_(ADTags.Blocks.LUSHSAUR_EDIBLE_BLOCK).m_255179_((Object[])new Block[]{Blocks.f_152542_, Blocks.f_152543_, Blocks.f_152539_});
        this.m_206424_(ADTags.Blocks.LUSHSAUR_BREAK_VEGETATION_BLOCKS).m_255179_((Object[])new Block[]{Blocks.f_50571_, Blocks.f_152545_, Blocks.f_152544_, Blocks.f_152543_, Blocks.f_152538_, Blocks.f_152541_, Blocks.f_152542_, Blocks.f_50196_}).m_206428_(BlockTags.f_13035_);
        this.m_206424_(ADTags.Blocks.LUSHSAUR_BREAK_IMMUNE_BLOCKS).m_255179_((Object[])new Block[]{Blocks.f_50080_, Blocks.f_50723_}).m_206428_(BlockTags.f_13070_);
        this.m_206424_(ADTags.Blocks.LUSHSAUR_EGG_HATCHABLE_BLOCKS).m_255179_((Object[])new Block[]{Blocks.f_50440_, Blocks.f_152544_});
        this.m_206424_(ADTags.Blocks.LUSHSAUR_VALID_SPAWN).m_255179_((Object[])new Block[]{Blocks.f_152543_, Blocks.f_152544_, Blocks.f_50129_});
        this.m_206424_(ADTags.Blocks.CHORUSOATL_EGG_HATCHABLE_BLOCKS).m_255245_((Object)Blocks.f_50259_);
        this.m_206424_(ADTags.Blocks.CHORUSOATL_EGG_CHORUS).m_255179_((Object[])new Block[]{Blocks.f_50490_, Blocks.f_50491_});
        this.m_206424_(ADTags.Blocks.CHORUSOATL_VALID_SPAWN).m_255245_((Object)Blocks.f_50259_);
        this.m_206424_(ADTags.Blocks.OBSIDICERATOPS_EGG_HATCHABLE_BLOCKS).m_255179_((Object[])new Block[]{Blocks.f_50699_, Blocks.f_50134_, Blocks.f_50450_});
        this.m_206424_(ADTags.Blocks.OBSIDICERATOPS_VALID_SPAWN).m_255179_((Object[])new Block[]{Blocks.f_50699_, Blocks.f_50134_});
        this.m_206424_(ADTags.Blocks.OBSIDICERATOPS_WILD_BREAK).m_255179_((Object[])new Block[]{Blocks.f_50451_, Blocks.f_50692_}).m_206428_(BlockTags.f_13035_);
        this.m_206424_(ADTags.Blocks.THERIMOSS_EGG_HATCHABLE_BLOCKS).m_255245_((Object)Blocks.f_50440_);
        this.m_206424_(ADTags.Blocks.THERIMOSS_EDIBLE_BLOCKS).m_255179_((Object[])new Block[]{Blocks.f_50186_, Blocks.f_50133_, Blocks.f_50145_, Blocks.f_256831_, Blocks.f_152471_, Blocks.f_50180_, Blocks.f_50181_, Blocks.f_50182_});
        this.m_206424_(ADTags.Blocks.THERIMOSS_VALID_SPAWN).m_255179_((Object[])new Block[]{Blocks.f_50440_, Blocks.f_50599_});
        this.m_206424_(ADTags.Blocks.THERIMOSS_PROTECTED_BLOCKS).m_206428_(BlockTags.f_13106_).m_255245_((Object)Blocks.f_50571_);
        this.m_206424_(ADTags.Blocks.SCULKRAPTOR_VALID_SPAWN).m_255245_((Object)Blocks.f_220855_);
        this.m_206424_(ADTags.Blocks.TOTEMS).m_255179_((Object[])new Block[]{(Block)ADBlocks.LUSH_TOTEM.get(), (Block)ADBlocks.BURNING_TOTEM.get(), (Block)ADBlocks.TROPIC_TOTEM.get(), (Block)ADBlocks.SOARING_TOTEM.get(), (Block)ADBlocks.TIDAL_TOTEM.get(), (Block)ADBlocks.ECHO_TOTEM.get(), (Block)ADBlocks.FEARFUL_TOTEM.get()});
        this.m_206424_(BlockTags.f_144282_).m_255179_((Object[])new Block[]{(Block)ADBlocks.LUSH_TOTEM.get(), (Block)ADBlocks.BURNING_TOTEM.get(), (Block)ADBlocks.TROPIC_TOTEM.get(), (Block)ADBlocks.SOARING_TOTEM.get(), (Block)ADBlocks.TIDAL_TOTEM.get(), (Block)ADBlocks.ECHO_TOTEM.get(), (Block)ADBlocks.FEARFUL_TOTEM.get(), ADBlocks.AMBER_ORE.get(), ADBlocks.RAW_AMBER_BLOCK.get(), ADBlocks.POLISHED_AMBER.get(), ADBlocks.POLISHED_AMBER_STAIRS.get(), ADBlocks.POLISHED_AMBER_SLAB.get(), ADBlocks.POLISHED_AMBER_WALL.get(), ADBlocks.AMBER_BRICKS.get(), ADBlocks.AMBER_BRICK_STAIRS.get(), ADBlocks.AMBER_BRICK_SLAB.get(), ADBlocks.AMBER_BRICK_WALL.get(), ADBlocks.CRACKED_AMBER_BRICKS.get(), ADBlocks.CARVED_AMBER.get(), (Block)ADBlocks.AMBER_CHAIN.get(), (Block)ADBlocks.AMBER_LAMP.get()});
        this.m_206424_(BlockTags.f_144286_).m_255179_((Object[])new Block[]{ADBlocks.AMBER_ORE.get(), ADBlocks.RAW_AMBER_BLOCK.get()});
        this.m_206424_(BlockTags.f_13030_).m_255179_((Object[])new Block[]{ADBlocks.POLISHED_AMBER_STAIRS.get(), ADBlocks.AMBER_BRICK_STAIRS.get()});
        this.m_206424_(BlockTags.f_13031_).m_255179_((Object[])new Block[]{ADBlocks.POLISHED_AMBER_SLAB.get(), ADBlocks.AMBER_BRICK_SLAB.get()});
        this.m_206424_(BlockTags.f_13032_).m_255179_((Object[])new Block[]{ADBlocks.POLISHED_AMBER_WALL.get(), ADBlocks.AMBER_BRICK_WALL.get()});
        this.m_206424_(BlockTags.f_13079_).m_255245_((Object)ADBlocks.RAW_AMBER_BLOCK.get());
    }

    protected void addForgeTags(HolderLookup.Provider provider) {
        this.m_206424_(Tags.Blocks.ORES).m_255245_((Object)ADBlocks.AMBER_ORE.get());
        this.m_206424_(Tags.Blocks.STORAGE_BLOCKS).m_255245_((Object)ADBlocks.RAW_AMBER_BLOCK.get());
    }

    @Override
    public void setEnvironment(PlatformSpecific.Environment environment) {
        this.environment = environment;
    }

    @Override
    public PlatformSpecific.Environment getEnvironment() {
        return this.environment;
    }
}

