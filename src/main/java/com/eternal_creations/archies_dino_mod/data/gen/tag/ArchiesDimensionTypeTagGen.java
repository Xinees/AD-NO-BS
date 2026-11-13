/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.tags.TagsProvider
 *  net.minecraft.world.level.dimension.BuiltinDimensionTypes
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen.tag;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesDimensionTypeTagGen
extends TagsProvider<DimensionType> {
    public ArchiesDimensionTypeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.f_256787_, lookupProvider, "ec_ad", existingFileHelper);
    }

    protected void m_6577_(HolderLookup.Provider provider) {
        this.m_206424_(ADTags.DimensionTypes.CHORUSOATL_EGG_PREFERRED).m_255204_(BuiltinDimensionTypes.f_223540_);
    }
}

