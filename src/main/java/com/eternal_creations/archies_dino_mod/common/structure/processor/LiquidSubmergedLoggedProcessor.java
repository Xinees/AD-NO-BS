/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate$StructureBlockInfo
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.Fluids
 */
package com.eternal_creations.archies_dino_mod.common.structure.processor;

import com.eternal_creations.archies_dino_mod.common.structure.piece.CustomStructurePiece;
import com.eternal_creations.archies_dino_mod.common.structure.processor.ADStructureProcessors;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LiquidSubmergedLoggedProcessor
extends StructureProcessor {
    public static final Codec<LiquidSubmergedLoggedProcessor> CODEC = Codec.unit(() -> INSTANCE);
    public static final LiquidSubmergedLoggedProcessor INSTANCE = new LiquidSubmergedLoggedProcessor(CustomStructurePiece.KeepLiquids.KEEP_LIQUIDS);
    private final CustomStructurePiece.KeepLiquids keepLiquids;

    public LiquidSubmergedLoggedProcessor(CustomStructurePiece.KeepLiquids keepLiquids) {
        this.keepLiquids = keepLiquids;
    }

    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader level, BlockPos pos, BlockPos blockPos, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo toBePlaced, StructurePlaceSettings settings) {
        BlockPos wantedPos = toBePlaced.f_74675_();
        if (this.keepLiquids == CustomStructurePiece.KeepLiquids.KEEP_LIQUIDS && !level.m_6425_(wantedPos).m_192917_((Fluid)Fluids.f_76193_)) {
            return toBePlaced;
        }
        if (toBePlaced.f_74676_().m_60734_() instanceof SimpleWaterloggedBlock) {
            return new StructureTemplate.StructureBlockInfo(wantedPos, (BlockState)toBePlaced.f_74676_().m_61124_((Property)BlockStateProperties.f_61362_, (Comparable)Boolean.valueOf(true)), toBePlaced.f_74677_());
        }
        if (toBePlaced.f_74676_().m_60795_() || toBePlaced.f_74676_().m_247087_()) {
            return new StructureTemplate.StructureBlockInfo(wantedPos, Blocks.f_49990_.m_49966_(), toBePlaced.f_74677_());
        }
        return toBePlaced;
    }

    protected StructureProcessorType<?> m_6953_() {
        return ADStructureProcessors.LIQUID_SUBMERGED_LOGGER.get();
    }
}

