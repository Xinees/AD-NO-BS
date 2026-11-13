/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderGetter
 *  net.minecraft.core.HolderSet
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.worldgen.BootstapContext
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.GenerationStep$Decoration
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  net.minecraft.world.level.levelgen.structure.Structure$StructureSettings
 *  net.minecraft.world.level.levelgen.structure.StructureSpawnOverride
 *  net.minecraft.world.level.levelgen.structure.TerrainAdjustment
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.eternal_creations.archies_dino_mod.common.structure;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.structure.CustomTemplateStructure;
import com.eternal_creations.archies_dino_mod.common.structure.GenericTemplateStructure;
import com.eternal_creations.archies_dino_mod.common.structure.piece.CustomStructurePiece;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADStructures {
    public static final int AIR_IS_AIR = 1;
    public static final int AIR_IS_REPLACED = 0;
    public static final ResourceKey<Structure> CORALOSAUR_NEST = ADStructures.registerKey("coralosaur_nest");
    public static final ResourceKey<Structure> LUSHSAUR = ADStructures.registerKey("lushsaur");
    public static final ResourceKey<Structure> OBSIDICERATOPS = ADStructures.registerKey("obsidiceratops");
    public static final ResourceKey<Structure> THERIMOSS = ADStructures.registerKey("therimoss");
    public static final ResourceKey<Structure> CRATER = ADStructures.registerKey("crater");
    public static final ResourceKey<Structure> BIG_CRATER = ADStructures.registerKey("big_crater");
    public static final ResourceKey<Structure> AMBER_FOSSIL = ADStructures.registerKey("amber_fossil");

    public static ResourceKey<Structure> registerKey(String name) {
        return ResourceKey.m_135785_((ResourceKey)Registries.f_256944_, (ResourceLocation)Constants.prefix(name));
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> biomes, Map<MobCategory, StructureSpawnOverride> mobOverrides, GenerationStep.Decoration step, TerrainAdjustment adjustment) {
        return new Structure.StructureSettings(biomes, mobOverrides, step, adjustment);
    }

    private static Structure.StructureSettings structure(HolderSet<Biome> biomes, GenerationStep.Decoration step, TerrainAdjustment adjustment) {
        return ADStructures.structure(biomes, Map.of(), step, adjustment);
    }

    public static void bootstrap(BootstapContext<Structure> context) {
        HolderGetter holdergetter = context.m_255420_(Registries.f_256952_);
        context.m_255272_(CORALOSAUR_NEST, (Object)new CustomTemplateStructure(ADStructures.structure((HolderSet<Biome>)holdergetter.m_254956_(ADTags.Biomes.SPAWNS_CORALOSAURS), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE), new GenericTemplateStructure.Setup(CustomStructurePiece.VerticalPlacement.SUNKEN, ADStructures.asResourceList("coralosaur_nest"), 0.0f, 1.0f, CustomStructurePiece.KeepLiquids.FORCE_WATER, 0, Optional.of(-42), CustomStructurePiece.ArchieProcessor.PROCESS_CORAL)));
        context.m_255272_(LUSHSAUR, (Object)new CustomTemplateStructure(ADStructures.structure((HolderSet<Biome>)holdergetter.m_254956_(ADTags.Biomes.SPAWNS_LUSHSAURS), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), new GenericTemplateStructure.Setup(CustomStructurePiece.VerticalPlacement.UNDERGROUND, List.of(new GenericTemplateStructure.WeightedResource(Constants.prefix("lushsaur"), 9), new GenericTemplateStructure.WeightedResource(Constants.prefix("lushsaur_kid"), 1)), 1.0f, 1.0f, CustomStructurePiece.KeepLiquids.IGNORE_LIQUIDS, 1, Optional.empty(), CustomStructurePiece.ArchieProcessor.NONE)));
        context.m_255272_(OBSIDICERATOPS, (Object)new CustomTemplateStructure(ADStructures.structure((HolderSet<Biome>)holdergetter.m_254956_(ADTags.Biomes.SPAWNS_OBSIDICERATOPSES), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), new GenericTemplateStructure.Setup(CustomStructurePiece.VerticalPlacement.IN_NETHER, ADStructures.asResourceList("obsidiceratops"), 1.0f, 1.0f, CustomStructurePiece.KeepLiquids.IGNORE_LIQUIDS, 1, Optional.empty(), CustomStructurePiece.ArchieProcessor.NONE)));
        context.m_255272_(THERIMOSS, (Object)new CustomTemplateStructure(ADStructures.structure((HolderSet<Biome>)holdergetter.m_254956_(ADTags.Biomes.SPAWNS_THERIMOSS), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), new GenericTemplateStructure.Setup(CustomStructurePiece.VerticalPlacement.ON_LAND_SURFACE, ADStructures.asResourceList("therimoss"), 1.0f, 1.0f, CustomStructurePiece.KeepLiquids.IGNORE_LIQUIDS, 1, Optional.empty(), CustomStructurePiece.ArchieProcessor.NONE)));
        context.m_255272_(CRATER, (Object)new CustomTemplateStructure(ADStructures.structure((HolderSet<Biome>)holdergetter.m_254956_(ADTags.Biomes.CRATER_VALID), GenerationStep.Decoration.TOP_LAYER_MODIFICATION, TerrainAdjustment.BEARD_THIN), new GenericTemplateStructure.Setup(CustomStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR, List.of(new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/crater_1"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/crater_2"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/crater_3"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/crater_4"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/crater_5"), 1)), 1.0f, 1.0f, CustomStructurePiece.KeepLiquids.KEEP_LIQUIDS, 4, Optional.empty(), CustomStructurePiece.ArchieProcessor.PROCESS_CRATER)));
        context.m_255272_(BIG_CRATER, (Object)new CustomTemplateStructure(ADStructures.structure((HolderSet<Biome>)holdergetter.m_254956_(ADTags.Biomes.CRATER_VALID), GenerationStep.Decoration.TOP_LAYER_MODIFICATION, TerrainAdjustment.BEARD_THIN), new GenericTemplateStructure.Setup(CustomStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR, List.of(new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/big_crater_1"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/big_crater_2"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/big_crater_3"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/big_crater_4"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("crater/big_crater_5"), 1)), 1.0f, 1.0f, CustomStructurePiece.KeepLiquids.KEEP_LIQUIDS, 5, Optional.empty(), CustomStructurePiece.ArchieProcessor.PROCESS_CRATER)));
        context.m_255272_(AMBER_FOSSIL, (Object)new CustomTemplateStructure(ADStructures.structure((HolderSet<Biome>)holdergetter.m_254956_(ADTags.Biomes.AMBER_FOSSIL_VALID), GenerationStep.Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), new GenericTemplateStructure.Setup(CustomStructurePiece.VerticalPlacement.ON_OCEAN_FLOOR, List.of(new GenericTemplateStructure.WeightedResource(Constants.prefix("fossil/ribcage_1"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("fossil/ribcage_2"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("fossil/ribcage_3"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("fossil/ribcage_4"), 1), new GenericTemplateStructure.WeightedResource(Constants.prefix("fossil/ribcage_5"), 1)), 0.0f, 1.0f, CustomStructurePiece.KeepLiquids.KEEP_LIQUIDS, 1, Optional.empty(), CustomStructurePiece.ArchieProcessor.PROCESS_BONE)));
    }

    @Contract(value="_ -> new")
    public static @Unmodifiable List<GenericTemplateStructure.WeightedResource> asResourceList(String ... set) {
        return Arrays.stream(set).map(path -> new GenericTemplateStructure.WeightedResource(Constants.prefix(path), 1)).toList();
    }
}

