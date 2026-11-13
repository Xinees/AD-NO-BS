/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.tags.DamageTypeTagsProvider
 *  net.minecraft.tags.DamageTypeTags
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen.tag;

import com.eternal_creations.archies_dino_mod.common.ADDamageTypes;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesDamageTypeTagGen
extends DamageTypeTagsProvider {
    public ArchiesDamageTypeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, "ec_ad", existingFileHelper);
    }

    protected void m_6577_(HolderLookup.Provider provider) {
        this.m_206424_(ADTags.DamageTypes.HURTS_COMBAT_SCULKSAUR_EXTRA).m_255204_(ADDamageTypes.PUS_ARROW);
        this.m_206424_(DamageTypeTags.f_268524_).m_255204_(ADDamageTypes.PUS_ARROW);
    }
}

