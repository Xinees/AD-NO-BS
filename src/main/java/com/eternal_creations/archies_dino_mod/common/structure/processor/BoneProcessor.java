/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate$StructureBlockInfo
 */
package com.eternal_creations.archies_dino_mod.common.structure.processor;

import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.structure.processor.ADStructureProcessors;
import com.mojang.serialization.Codec;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BoneProcessor
extends StructureProcessor {
    public static final Codec<BoneProcessor> CODEC = Codec.unit(() -> INSTANCE);
    public static final BoneProcessor INSTANCE = new BoneProcessor();

    public List<StructureTemplate.StructureBlockInfo> m_276976_(ServerLevelAccessor serverLevel, BlockPos offset, BlockPos pos, List<StructureTemplate.StructureBlockInfo> originalBlockInfos, List<StructureTemplate.StructureBlockInfo> processedBlockInfos, StructurePlaceSettings settings) {
        RandomSource random = RandomSource.m_216335_((long)serverLevel.m_6018_().m_7328_()).m_188582_().m_224542_(offset);
        List<StructureTemplate.StructureBlockInfo> bones = processedBlockInfos.stream().filter(structureBlockInfo -> structureBlockInfo.f_74676_().m_60713_(Blocks.f_50453_)).toList();
        StructureTemplate.StructureBlockInfo toBePlaced = bones.get(random.m_188503_(bones.size()));
        int index = processedBlockInfos.indexOf(toBePlaced);
        processedBlockInfos.set(index, new StructureTemplate.StructureBlockInfo(toBePlaced.f_74675_(), ADBlocks.RAW_AMBER_BLOCK.get().m_49966_(), toBePlaced.f_74677_()));
        return processedBlockInfos;
    }

    protected StructureProcessorType<?> m_6953_() {
        return ADStructureProcessors.BONE_PROCESSOR.get();
    }
}

