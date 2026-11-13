/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.util.ExtraCodecs
 *  net.minecraft.world.level.levelgen.structure.Structure$StructureSettings
 *  net.minecraft.world.level.levelgen.structure.StructureType
 */
package com.eternal_creations.archies_dino_mod.common.structure;

import com.eternal_creations.archies_dino_mod.common.structure.ADStructureTypes;
import com.eternal_creations.archies_dino_mod.common.structure.GenericTemplateStructure;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomTemplateStructure
extends GenericTemplateStructure {
    public static final Codec<CustomTemplateStructure> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)CustomTemplateStructure.m_226567_((RecordCodecBuilder.Instance)instance), (App)ExtraCodecs.m_144637_((Codec)GenericTemplateStructure.Setup.CODEC.listOf()).fieldOf("setups").forGetter(structure -> structure.setups)).apply((Applicative)instance, CustomTemplateStructure::new));

    public CustomTemplateStructure(Structure.StructureSettings settings, List<GenericTemplateStructure.Setup> setups) {
        super(settings, setups);
    }

    public CustomTemplateStructure(Structure.StructureSettings settings, GenericTemplateStructure.Setup setup) {
        super(settings, setup);
    }

    public StructureType<?> m_213658_() {
        return ADStructureTypes.CUSTOM_TEMPLATE.get();
    }
}

