/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CoralWallFanBlock
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate$StructureBlockInfo
 */
package com.eternal_creations.archies_dino_mod.common.structure.processor;

import com.eternal_creations.archies_dino_mod.common.structure.processor.ADStructureProcessors;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CoralWallFanBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoralProcessor
extends StructureProcessor {
    public static final Codec<CoralProcessor> CODEC = Codec.unit(() -> INSTANCE);
    public static final CoralProcessor INSTANCE = new CoralProcessor();
    public static final Map<Block, Block> DEAD_TO_LIVING = new HashMap<Block, Block>();

    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader level, BlockPos pos, BlockPos blockPos, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo toBePlaced, StructurePlaceSettings settings) {
        BlockPos wantedPos = toBePlaced.f_74675_();
        if (DEAD_TO_LIVING.containsKey(toBePlaced.f_74676_().m_60734_())) {
            BlockState toPlace = toBePlaced.f_74676_();
            BlockState newState = DEAD_TO_LIVING.get(toPlace.m_60734_()).m_49966_();
            if (toPlace.m_60734_() instanceof CoralWallFanBlock) {
                newState = (BlockState)newState.m_61124_((Property)CoralWallFanBlock.f_49192_, (Comparable)((Direction)toPlace.m_61143_((Property)CoralWallFanBlock.f_49192_)));
            }
            if (toPlace.m_60734_() instanceof SimpleWaterloggedBlock) {
                newState = (BlockState)newState.m_61124_((Property)BlockStateProperties.f_61362_, (Comparable)Boolean.valueOf(true));
            }
            return new StructureTemplate.StructureBlockInfo(wantedPos, newState, toBePlaced.f_74677_());
        }
        return toBePlaced;
    }

    protected StructureProcessorType<?> m_6953_() {
        return ADStructureProcessors.CORAL_PROCESSOR.get();
    }

    static {
        DEAD_TO_LIVING.put(Blocks.f_50579_, Blocks.f_50584_);
        DEAD_TO_LIVING.put(Blocks.f_50580_, Blocks.f_50585_);
        DEAD_TO_LIVING.put(Blocks.f_50581_, Blocks.f_50586_);
        DEAD_TO_LIVING.put(Blocks.f_50582_, Blocks.f_50587_);
        DEAD_TO_LIVING.put(Blocks.f_50583_, Blocks.f_50588_);
        DEAD_TO_LIVING.put(Blocks.f_50589_, Blocks.f_50594_);
        DEAD_TO_LIVING.put(Blocks.f_50590_, Blocks.f_50595_);
        DEAD_TO_LIVING.put(Blocks.f_50591_, Blocks.f_50596_);
        DEAD_TO_LIVING.put(Blocks.f_50592_, Blocks.f_50597_);
        DEAD_TO_LIVING.put(Blocks.f_50593_, Blocks.f_50598_);
        DEAD_TO_LIVING.put(Blocks.f_50547_, Blocks.f_50552_);
        DEAD_TO_LIVING.put(Blocks.f_50548_, Blocks.f_50553_);
        DEAD_TO_LIVING.put(Blocks.f_50549_, Blocks.f_50554_);
        DEAD_TO_LIVING.put(Blocks.f_50550_, Blocks.f_50555_);
        DEAD_TO_LIVING.put(Blocks.f_50551_, Blocks.f_50556_);
        DEAD_TO_LIVING.put(Blocks.f_50557_, Blocks.f_50562_);
        DEAD_TO_LIVING.put(Blocks.f_50558_, Blocks.f_50563_);
        DEAD_TO_LIVING.put(Blocks.f_50559_, Blocks.f_50564_);
        DEAD_TO_LIVING.put(Blocks.f_50560_, Blocks.f_50565_);
        DEAD_TO_LIVING.put(Blocks.f_50561_, Blocks.f_50566_);
    }
}

