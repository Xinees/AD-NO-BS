/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.data.PackOutput
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.ChainBlock
 *  net.minecraft.world.level.block.LanternBlock
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.WallBlock
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraftforge.client.model.generators.BlockModelBuilder
 *  net.minecraftforge.client.model.generators.BlockStateProvider
 *  net.minecraftforge.client.model.generators.ModelFile
 *  net.minecraftforge.client.model.generators.ModelFile$ExistingModelFile
 *  net.minecraftforge.client.model.generators.VariantBlockStateBuilder
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesBlockStateGen
extends BlockStateProvider {
    public ArchiesBlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, "ec_ad", exFileHelper);
    }

    protected void registerStatesAndModels() {
        this.blockEntity(ADBlocks.LUSHSAUR_EGG);
        this.blockEntity(ADBlocks.CHORUSOATL_EGG);
        this.blockEntity(ADBlocks.OBSIDICERATOPS_EGG);
        this.blockEntity(ADBlocks.THERIMOSS_EGG);
        this.blockEntity(ADBlocks.SCULKRAPTOR_EGG);
        this.horizontalBlock(ADBlocks.LUSH_TOTEM);
        this.horizontalBlock(ADBlocks.BURNING_TOTEM);
        this.horizontalBlock(ADBlocks.TROPIC_TOTEM);
        this.horizontalBlock(ADBlocks.SOARING_TOTEM);
        this.horizontalBlock(ADBlocks.TIDAL_TOTEM);
        this.horizontalBlock(ADBlocks.ECHO_TOTEM);
        this.horizontalBlock(ADBlocks.FEARFUL_TOTEM);
        this.simpleBlock(ADBlocks.AMBER_ORE.get());
        this.simpleBlock(ADBlocks.RAW_AMBER_BLOCK.get());
        this.simpleBlock(ADBlocks.POLISHED_AMBER.get());
        this.stairsBlock((StairBlock)ADBlocks.POLISHED_AMBER_STAIRS.get(), this.blockTexture(ADBlocks.POLISHED_AMBER.get()));
        this.slabBlock((SlabBlock)ADBlocks.POLISHED_AMBER_SLAB.get(), this.blockTexture(ADBlocks.POLISHED_AMBER.get()), this.blockTexture(ADBlocks.POLISHED_AMBER.get()));
        this.wallBlock((WallBlock)ADBlocks.POLISHED_AMBER_WALL.get(), this.blockTexture(ADBlocks.POLISHED_AMBER.get()));
        this.simpleBlock(ADBlocks.AMBER_BRICKS.get());
        this.stairsBlock((StairBlock)ADBlocks.AMBER_BRICK_STAIRS.get(), this.blockTexture(ADBlocks.AMBER_BRICKS.get()));
        this.slabBlock((SlabBlock)ADBlocks.AMBER_BRICK_SLAB.get(), this.blockTexture(ADBlocks.AMBER_BRICKS.get()), this.blockTexture(ADBlocks.AMBER_BRICKS.get()));
        this.wallBlock((WallBlock)ADBlocks.AMBER_BRICK_WALL.get(), this.blockTexture(ADBlocks.AMBER_BRICKS.get()));
        this.simpleBlock(ADBlocks.CRACKED_AMBER_BRICKS.get());
        this.simpleBlock(ADBlocks.CARVED_AMBER.get());
        this.chain();
        this.lantern();
    }

    private void chain() {
        ModelFile.ExistingModelFile chain = this.models().getExistingFile(this.modLoc("block/amber_chain"));
        ((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)this.getVariantBuilder((Block)ADBlocks.AMBER_CHAIN.get()).partialState().with((Property)ChainBlock.f_55923_, (Comparable)Direction.Axis.X).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(false)).modelForState().modelFile((ModelFile)chain).rotationX(90).rotationY(90).addModel()).partialState().with((Property)ChainBlock.f_55923_, (Comparable)Direction.Axis.Y).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(false)).modelForState().modelFile((ModelFile)chain).addModel()).partialState().with((Property)ChainBlock.f_55923_, (Comparable)Direction.Axis.Z).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(false)).modelForState().modelFile((ModelFile)chain).rotationX(90).addModel()).partialState().with((Property)ChainBlock.f_55923_, (Comparable)Direction.Axis.X).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(true)).modelForState().modelFile((ModelFile)chain).rotationX(90).rotationY(90).addModel()).partialState().with((Property)ChainBlock.f_55923_, (Comparable)Direction.Axis.Y).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(true)).modelForState().modelFile((ModelFile)chain).addModel()).partialState().with((Property)ChainBlock.f_55923_, (Comparable)Direction.Axis.Z).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(true)).modelForState().modelFile((ModelFile)chain).rotationX(90).addModel();
    }

    private void lantern() {
        ModelFile.ExistingModelFile lanternModel = this.models().getExistingFile(this.modLoc("block/amber_lantern"));
        ModelFile.ExistingModelFile hangingModel = this.models().getExistingFile(this.modLoc("block/amber_lantern_hanging"));
        ((VariantBlockStateBuilder)((VariantBlockStateBuilder)((VariantBlockStateBuilder)this.getVariantBuilder((Block)ADBlocks.AMBER_LAMP.get()).partialState().with((Property)LanternBlock.f_153459_, (Comparable)Boolean.valueOf(false)).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(false)).modelForState().modelFile((ModelFile)lanternModel).addModel()).partialState().with((Property)LanternBlock.f_153459_, (Comparable)Boolean.valueOf(true)).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(false)).modelForState().modelFile((ModelFile)hangingModel).addModel()).partialState().with((Property)LanternBlock.f_153459_, (Comparable)Boolean.valueOf(false)).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(true)).modelForState().modelFile((ModelFile)lanternModel).addModel()).partialState().with((Property)LanternBlock.f_153459_, (Comparable)Boolean.valueOf(true)).with((Property)LanternBlock.f_153460_, (Comparable)Boolean.valueOf(true)).modelForState().modelFile((ModelFile)hangingModel).addModel();
    }

    private <T extends Block> void horizontalBlock(ResourceSupplier<T> block) {
        this.horizontalBlock((Block)block.get(), (ModelFile)this.models().getExistingFile(block.id()));
    }

    private <T extends Block> void blockEntity(ResourceSupplier<T> block) {
        this.simpleBlock((Block)block.get(), (ModelFile)((BlockModelBuilder)this.models().getBuilder(block.id().toString())).texture("particle", block.id().m_135827_() + ":block/" + block.id().m_135815_()));
    }
}

