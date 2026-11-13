/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.tags.BiomeTagsProvider
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.tags.BiomeTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.biome.Biomes
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen.tag;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesBiomeTagGen
extends BiomeTagsProvider {
    public ArchiesBiomeTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, "ec_ad", existingFileHelper);
    }

    protected void m_6577_(HolderLookup.Provider provider) {
        this.m_206424_(ADTags.Biomes.SPAWNS_SCULKRAPTORS).m_255204_(Biomes.f_220594_);
        this.m_206424_(ADTags.Biomes.SPAWNS_LUSHSAURS).m_255204_(Biomes.f_151785_);
        this.m_206424_(ADTags.Biomes.SPAWNS_CHORUSOATLS).m_211101_(new ResourceKey[]{Biomes.f_48165_, Biomes.f_48164_, Biomes.f_48163_, Biomes.f_48162_});
        this.m_206424_(ADTags.Biomes.SPAWNS_OBSIDICERATOPSES).m_211101_(new ResourceKey[]{Biomes.f_48200_, Biomes.f_48199_});
        this.m_206424_(ADTags.Biomes.SPAWNS_CORALOSAURS).m_255204_(Biomes.f_48166_);
        this.m_206424_(ADTags.Biomes.SPAWNS_THERIMOSS).addTags(new TagKey[]{BiomeTags.f_207610_});
        this.m_206424_(ADTags.Biomes.SPAWNS_ARCHIE).addTags(new TagKey[]{ADTags.Biomes.SPAWNS_SCULKRAPTORS, ADTags.Biomes.SPAWNS_LUSHSAURS, ADTags.Biomes.SPAWNS_CHORUSOATLS, ADTags.Biomes.SPAWNS_OBSIDICERATOPSES, ADTags.Biomes.SPAWNS_CORALOSAURS, ADTags.Biomes.SPAWNS_THERIMOSS});
        this.m_206424_(ADTags.Biomes.CRATER_VALID).addTags(new TagKey[]{BiomeTags.f_215817_});
        this.m_206424_(ADTags.Biomes.AMBER_FOSSIL_VALID).addTags(new TagKey[]{BiomeTags.f_207623_, BiomeTags.f_207607_});
    }
}

