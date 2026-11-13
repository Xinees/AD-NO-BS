/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.FireBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate$StructureBlockInfo
 */
package com.eternal_creations.archies_dino_mod.common.structure.processor;

import com.eternal_creations.archies_dino_mod.common.structure.processor.ADStructureProcessors;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CraterProcessor
extends StructureProcessor {
    public static final Codec<CraterProcessor> CODEC = Codec.unit(() -> INSTANCE);
    public static final CraterProcessor INSTANCE = new CraterProcessor();

    @Nullable
    public StructureTemplate.StructureBlockInfo m_7382_(LevelReader level, BlockPos pos, BlockPos blockPos, StructureTemplate.StructureBlockInfo info, StructureTemplate.StructureBlockInfo toBePlaced, StructurePlaceSettings settings) {
        if (toBePlaced.f_74676_().m_204336_(BlockTags.f_144274_)) {
            BlockState current = level.m_8055_(toBePlaced.f_74675_());
            if (!current.m_204336_(BlockTags.f_144274_)) {
                return null;
            }
            return toBePlaced;
        }
        if (toBePlaced.f_74676_().m_60713_(Blocks.f_50016_)) {
            BlockState current = level.m_8055_(toBePlaced.f_74675_());
            if (current.m_204336_(BlockTags.f_13106_)) {
                if (level instanceof ServerLevelAccessor) {
                    ServerLevelAccessor accessor = (ServerLevelAccessor)level;
                    RandomSource random = RandomSource.m_216335_((long)accessor.m_6018_().m_7328_()).m_188582_().m_224542_(toBePlaced.f_74675_());
                    accessor.m_186460_(toBePlaced.f_74675_(), Blocks.f_50083_, 30 + random.m_188503_(10));
                }
                return new StructureTemplate.StructureBlockInfo(toBePlaced.f_74675_(), (BlockState)Blocks.f_50083_.m_49966_().m_61124_((Property)FireBlock.f_53413_, (Comparable)Boolean.valueOf(true)), toBePlaced.f_74677_());
            }
            return toBePlaced;
        }
        return toBePlaced;
    }

    protected StructureProcessorType<?> m_6953_() {
        return ADStructureProcessors.CRATER_PROCESSOR.get();
    }
}

