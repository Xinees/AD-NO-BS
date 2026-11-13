/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Holder
 *  net.minecraft.core.HolderGetter
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.worldgen.BootstapContext
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.levelgen.structure.StructureSet
 *  net.minecraft.world.level.levelgen.structure.StructureSet$StructureSelectionEntry
 *  net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement
 *  net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType
 *  net.minecraft.world.level.levelgen.structure.placement.StructurePlacement
 *  net.minecraft.world.level.levelgen.structure.placement.StructurePlacement$ExclusionZone
 *  net.minecraft.world.level.levelgen.structure.placement.StructurePlacement$FrequencyReductionMethod
 */
package com.eternal_creations.archies_dino_mod.common.structure;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.structure.ADStructures;
import java.util.List;
import java.util.Optional;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADStructureSets {
    public static final ResourceKey<StructureSet> CORALOSAUR_NEST = ADStructureSets.registerKey("coralosaur_nest");
    public static final ResourceKey<StructureSet> LUSHSAUR = ADStructureSets.registerKey("lushsaur");
    public static final ResourceKey<StructureSet> OBSIDICERATOPS = ADStructureSets.registerKey("obsidiceratops");
    public static final ResourceKey<StructureSet> THERIMOSS = ADStructureSets.registerKey("therimoss");
    public static final ResourceKey<StructureSet> CRATER = ADStructureSets.registerKey("crater");
    public static final ResourceKey<StructureSet> BIG_CRATER = ADStructureSets.registerKey("big_crater");
    public static final ResourceKey<StructureSet> AMBER_FOSSIL = ADStructureSets.registerKey("amber_fossil");

    private static ResourceKey<StructureSet> registerKey(String name) {
        return ResourceKey.m_135785_((ResourceKey)Registries.f_256998_, (ResourceLocation)Constants.prefix(name));
    }

    public static void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter structures = context.m_255420_(Registries.f_256944_);
        context.m_255272_(CORALOSAUR_NEST, (Object)new StructureSet(List.of(new StructureSet.StructureSelectionEntry((Holder)structures.m_255043_(ADStructures.CORALOSAUR_NEST), 1)), (StructurePlacement)ADStructureSets.createRandomSpread(7, 5, RandomSpreadType.LINEAR, 254884681, Optional.empty())));
        context.m_255272_(LUSHSAUR, (Object)new StructureSet(List.of(new StructureSet.StructureSelectionEntry((Holder)structures.m_255043_(ADStructures.LUSHSAUR), 1)), (StructurePlacement)ADStructureSets.createRandomSpread(6, 5, RandomSpreadType.LINEAR, 172332262, Optional.empty())));
        context.m_255272_(OBSIDICERATOPS, (Object)new StructureSet(List.of(new StructureSet.StructureSelectionEntry((Holder)structures.m_255043_(ADStructures.OBSIDICERATOPS), 1)), (StructurePlacement)ADStructureSets.createRandomSpread(4, 2, RandomSpreadType.LINEAR, 745231145, Optional.empty())));
        context.m_255272_(THERIMOSS, (Object)new StructureSet(List.of(new StructureSet.StructureSelectionEntry((Holder)structures.m_255043_(ADStructures.THERIMOSS), 1)), (StructurePlacement)ADStructureSets.createRandomSpread(7, 6, RandomSpreadType.LINEAR, 5211633, Optional.empty())));
        context.m_255272_(CRATER, (Object)new StructureSet(List.of(new StructureSet.StructureSelectionEntry((Holder)structures.m_255043_(ADStructures.CRATER), 1)), (StructurePlacement)ADStructureSets.createRandomSpread(24, 12, RandomSpreadType.LINEAR, 59585624, Optional.empty())));
        context.m_255272_(BIG_CRATER, (Object)new StructureSet(List.of(new StructureSet.StructureSelectionEntry((Holder)structures.m_255043_(ADStructures.BIG_CRATER), 1)), (StructurePlacement)ADStructureSets.createRandomSpread(36, 18, RandomSpreadType.LINEAR, 654682354, Optional.of(new StructurePlacement.ExclusionZone((Holder)context.m_255420_(Registries.f_256998_).m_255043_(CRATER), 2)))));
        context.m_255272_(AMBER_FOSSIL, (Object)new StructureSet(List.of(new StructureSet.StructureSelectionEntry((Holder)structures.m_255043_(ADStructures.AMBER_FOSSIL), 1)), (StructurePlacement)ADStructureSets.createRandomSpread(18, 9, RandomSpreadType.LINEAR, 362774234, Optional.empty())));
    }

    public static RandomSpreadStructurePlacement createRandomSpread(int pSpacing, int pSeparation, RandomSpreadType pSpreadType, int pSalt, Optional<StructurePlacement.ExclusionZone> zone) {
        return new RandomSpreadStructurePlacement(Vec3i.f_123288_, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0f, pSalt, zone, pSpacing, pSeparation, pSpreadType);
    }
}

