/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.levelgen.structure.StructureType
 */
package com.eternal_creations.archies_dino_mod.common.structure;

import com.eternal_creations.archies_dino_mod.common.structure.CustomTemplateStructure;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ADStructureTypes {
    public static final ResourceSupplier<StructureType<CustomTemplateStructure>> CUSTOM_TEMPLATE = Services.COMMON.registerStructureType("custom_template", () -> () -> CustomTemplateStructure.CODEC);

    public static void init() {
    }
}

