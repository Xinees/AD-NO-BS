/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 */
package com.eternal_creations.archies_dino_mod.common.structure.processor;

import com.eternal_creations.archies_dino_mod.common.structure.processor.BoneProcessor;
import com.eternal_creations.archies_dino_mod.common.structure.processor.CoralProcessor;
import com.eternal_creations.archies_dino_mod.common.structure.processor.CraterProcessor;
import com.eternal_creations.archies_dino_mod.common.structure.processor.LiquidSubmergedLoggedProcessor;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public class ADStructureProcessors {
    public static final ResourceSupplier<StructureProcessorType<LiquidSubmergedLoggedProcessor>> LIQUID_SUBMERGED_LOGGER = Services.COMMON.registerStructureProcessorType("liquid_submerged_logger", () -> () -> LiquidSubmergedLoggedProcessor.CODEC);
    public static final ResourceSupplier<StructureProcessorType<CoralProcessor>> CORAL_PROCESSOR = Services.COMMON.registerStructureProcessorType("coral_processor", () -> () -> CoralProcessor.CODEC);
    public static final ResourceSupplier<StructureProcessorType<BoneProcessor>> BONE_PROCESSOR = Services.COMMON.registerStructureProcessorType("bone_processor", () -> () -> BoneProcessor.CODEC);
    public static final ResourceSupplier<StructureProcessorType<CraterProcessor>> CRATER_PROCESSOR = Services.COMMON.registerStructureProcessorType("crater_processor", () -> () -> CraterProcessor.CODEC);

    public static void init() {
    }
}

