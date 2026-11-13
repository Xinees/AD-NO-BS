/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.Util
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.ExtraCodecs
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.random.Weight
 *  net.minecraft.util.random.WeightedEntry
 *  net.minecraft.util.random.WeightedRandomList
 *  net.minecraft.world.level.LevelHeightAccessor
 *  net.minecraft.world.level.NoiseColumn
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.RandomState
 *  net.minecraft.world.level.levelgen.WorldgenRandom
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.levelgen.structure.Structure$GenerationContext
 *  net.minecraft.world.level.levelgen.structure.Structure$GenerationStub
 *  net.minecraft.world.level.levelgen.structure.Structure$StructureSettings
 *  net.minecraft.world.level.levelgen.structure.StructurePiece
 *  net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate
 */
package com.eternal_creations.archies_dino_mod.common.structure;

import com.eternal_creations.archies_dino_mod.common.structure.piece.CustomStructurePiece;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class GenericTemplateStructure
extends Structure {
    protected final List<Setup> setups;

    public GenericTemplateStructure(Structure.StructureSettings settings, List<Setup> setups) {
        super(settings);
        this.setups = setups;
    }

    public GenericTemplateStructure(Structure.StructureSettings settings, Setup setup) {
        this(settings, List.of(setup));
    }

    public Optional<Structure.GenerationStub> m_214086_(Structure.GenerationContext context) {
        WorldgenRandom random = context.f_226626_();
        Setup pickedSetup = null;
        if (this.setups.size() > 1) {
            float f = 0.0f;
            for (Setup setup : this.setups) {
                f += setup.weight();
            }
            float weight = random.m_188501_();
            for (Setup setup : this.setups) {
                if (!((weight -= setup.weight() / f) < 0.0f)) continue;
                pickedSetup = setup;
                break;
            }
        } else {
            pickedSetup = this.setups.get(0);
        }
        if (pickedSetup == null) {
            throw new IllegalStateException();
        }
        Setup finalSetup = pickedSetup;
        CustomStructurePiece.Properties properties = new CustomStructurePiece.Properties(GenericTemplateStructure.sample(random, finalSetup.airPocketProbability()), finalSetup.keepLiquids, this.m_226620_(), finalSetup.delta, finalSetup.processor);
        ResourceLocation resourceLocation = this.selectTemplate(finalSetup, random);
        StructureTemplate template = context.f_226625_().m_230359_(resourceLocation);
        Rotation rotation = (Rotation)Util.m_214670_((Object[])Rotation.values(), (RandomSource)random);
        Mirror mirror = random.m_188501_() < 0.5f ? Mirror.NONE : Mirror.FRONT_BACK;
        BlockPos pivot = new BlockPos(template.m_163801_().m_123341_() / 2, 0, template.m_163801_().m_123343_() / 2);
        BoundingBox boundingBox = template.m_74598_(context.f_226628_().m_45615_(), rotation, pivot, mirror);
        BlockPos finalPos = this.adjustPos(finalSetup, context, properties, boundingBox, 0);
        return Optional.of(new Structure.GenerationStub(finalPos, builder -> this.putAndPlace((StructurePiecesBuilder)builder, finalPos, context, finalSetup, properties, location, rotation, mirror, pivot)));
    }

    protected ResourceLocation selectTemplate(Setup finalSetup, WorldgenRandom random) {
        if (finalSetup.structures().size() == 1) {
            return finalSetup.structures().get(0).location();
        }
        return WeightedRandomList.m_146328_(finalSetup.structures()).m_216829_(random.m_213769_()).orElse(finalSetup.structures().get(0)).location();
    }

    protected void putAndPlace(StructurePiecesBuilder builder, BlockPos finalPos, Structure.GenerationContext context, Setup finalSetup, CustomStructurePiece.Properties properties, ResourceLocation location, Rotation rotation, Mirror mirror, BlockPos pivot) {
        builder.m_142679_((StructurePiece)new CustomStructurePiece(context.f_226625_(), finalPos, finalSetup.placement(), properties, location, rotation, mirror, pivot));
    }

    protected static boolean sample(WorldgenRandom random, float probability) {
        if (probability == 0.0f) {
            return false;
        }
        if (probability == 1.0f) {
            return true;
        }
        return random.m_188501_() < probability;
    }

    public BlockPos adjustPos(Setup finalSetup, Structure.GenerationContext context, CustomStructurePiece.Properties properties, BoundingBox boundingBox, int code) {
        WorldgenRandom random = context.f_226626_();
        ChunkGenerator chunkGenerator = context.f_226622_();
        LevelHeightAccessor heightAccessor = context.f_226629_();
        RandomState randomState = context.f_226624_();
        BlockPos center = boundingBox.m_162394_();
        int baseHeight = chunkGenerator.m_214096_(center.m_123341_(), center.m_123343_(), finalSetup.placement().getHeightmap(), heightAccessor, randomState);
        int suitableY = this.findSuitableY((RandomSource)random, chunkGenerator, finalSetup, properties.airPocket(), baseHeight, boundingBox.m_71057_(), boundingBox, heightAccessor, randomState);
        return new BlockPos(center.m_123341_(), suitableY, center.m_123343_());
    }

    protected int findSuitableY(RandomSource random, ChunkGenerator chunkGenerator, Setup setup, boolean airPocket, int baseHeight, int ySpan, BoundingBox boundingBox, LevelHeightAccessor heightAccessor, RandomState randomState) {
        int suitableY;
        int height = heightAccessor.m_141937_() + 15;
        switch (setup.placement) {
            case SUNKEN: {
                int finalHeight = Math.min(chunkGenerator.m_6337_() - ySpan - 1, baseHeight + setup.depth.orElse(0));
                break;
            }
            case BURIED: {
                int finalHeight = Math.max(chunkGenerator.m_6337_() + 1, baseHeight) + setup.depth.orElse(0);
                break;
            }
            case IN_THE_SKY: {
                int top = heightAccessor.m_151558_();
                return baseHeight + Mth.m_216287_((RandomSource)random, (int)((top - baseHeight) / 5), (int)((top - baseHeight) / 4));
            }
            case IN_NETHER: {
                int finalHeight;
                if (airPocket) {
                    finalHeight = Mth.m_216287_((RandomSource)random, (int)32, (int)100);
                    break;
                }
                if (random.m_188501_() < 0.5f) {
                    finalHeight = Mth.m_216287_((RandomSource)random, (int)27, (int)29);
                    break;
                }
                finalHeight = Mth.m_216287_((RandomSource)random, (int)29, (int)100);
                break;
            }
            case IN_MOUNTAIN: {
                int finalHeight = GenericTemplateStructure.getRandomWithinInterval(random, 70, baseHeight - ySpan);
                break;
            }
            case UNDERGROUND: {
                int finalHeight = GenericTemplateStructure.getRandomWithinInterval(random, height, baseHeight - ySpan);
                break;
            }
            case PARTLY_BURIED: {
                int finalHeight = baseHeight - Mth.m_216287_((RandomSource)random, (int)ySpan, (int)((int)((float)ySpan * 1.5f)));
                break;
            }
            default: {
                int finalHeight = baseHeight + setup.depth.orElse(0);
            }
        }
        ImmutableList list1 = ImmutableList.of((Object)new BlockPos(boundingBox.m_162395_(), 0, boundingBox.m_162398_()), (Object)new BlockPos(boundingBox.m_162399_(), 0, boundingBox.m_162398_()), (Object)new BlockPos(boundingBox.m_162395_(), 0, boundingBox.m_162401_()), (Object)new BlockPos(boundingBox.m_162399_(), 0, boundingBox.m_162401_()));
        List<NoiseColumn> list = list1.stream().map(blockPos -> chunkGenerator.m_214184_(blockPos.m_123341_(), blockPos.m_123343_(), heightAccessor, randomState)).toList();
        Heightmap.Types types = setup.placement.getHeightmap();
        for (suitableY = finalHeight; suitableY > height; --suitableY) {
            int i1 = 0;
            for (NoiseColumn noisecolumn : list) {
                BlockState blockstate = noisecolumn.m_183556_(suitableY);
                if (!types.m_64299_().test(blockstate) || ++i1 != 3) continue;
                return suitableY;
            }
        }
        return suitableY;
    }

    private static int getRandomWithinInterval(RandomSource randomSource, int min, int max) {
        return min < max ? Mth.m_216287_((RandomSource)randomSource, (int)min, (int)max) : max;
    }

    public record Setup(CustomStructurePiece.VerticalPlacement placement, List<WeightedResource> structures, float airPocketProbability, float weight, CustomStructurePiece.KeepLiquids keepLiquids, int delta, Optional<Integer> depth, CustomStructurePiece.ArchieProcessor processor) {
        public static final Codec<Setup> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)CustomStructurePiece.VerticalPlacement.CODEC.fieldOf("placement").forGetter(Setup::placement), (App)ExtraCodecs.m_144637_((Codec)WeightedResource.CODEC.listOf()).fieldOf("structure_locations").forGetter(Setup::structures), (App)Codec.floatRange((float)0.0f, (float)1.0f).fieldOf("air_pocket_probability").forGetter(Setup::airPocketProbability), (App)Codec.FLOAT.fieldOf("weight").forGetter(Setup::weight), (App)CustomStructurePiece.KeepLiquids.CODEC.fieldOf("keep_liquids").forGetter(Setup::keepLiquids), (App)Codec.INT.fieldOf("delta").forGetter(Setup::delta), (App)Codec.INT.optionalFieldOf("depth").forGetter(Setup::depth), (App)CustomStructurePiece.ArchieProcessor.CODEC.fieldOf("archie_processor").forGetter(Setup::processor)).apply((Applicative)instance, Setup::new));
    }

    public record WeightedResource(ResourceLocation location, int weight) implements WeightedEntry
    {
        public static final Codec<WeightedResource> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)ResourceLocation.f_135803_.fieldOf("location").forGetter(WeightedResource::location), (App)ExtraCodecs.f_144629_.fieldOf("weight").forGetter(WeightedResource::weight)).apply((Applicative)instance, WeightedResource::new));

        public Weight m_142631_() {
            return Weight.m_146282_((int)this.weight);
        }
    }
}

