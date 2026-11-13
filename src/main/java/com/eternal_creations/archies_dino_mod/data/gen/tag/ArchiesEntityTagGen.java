/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.tags.EntityTypeTagsProvider
 *  net.minecraft.tags.EntityTypeTags
 *  net.minecraft.world.entity.EntityType
 *  net.minecraftforge.common.Tags$EntityTypes
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen.tag;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.data.util.PlatformSpecific;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesEntityTagGen
extends EntityTypeTagsProvider
implements PlatformSpecific {
    private PlatformSpecific.Environment environment;

    public ArchiesEntityTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper helper) {
        super(output, provider, "ec_ad", helper);
    }

    protected void m_6577_(HolderLookup.Provider provider) {
        if (this.isForge()) {
            this.addForgeTags(provider);
        }
        this.m_206424_(ADTags.EntityTypes.DINOSAUR).m_255179_((Object[])new EntityType[]{ADEntityTypes.CHORUSOATL.get(), ADEntityTypes.CORALOSAUR.get(), ADEntityTypes.LUSHSAUR.get(), ADEntityTypes.OBSIDICERATOPS.get(), ADEntityTypes.SCULKRAPTOR.get(), ADEntityTypes.SCULKSAUR.get(), ADEntityTypes.THERIMOSS.get()});
        this.m_206424_(ADTags.EntityTypes.DINO_RESISTS_DAMAGE_FROM).m_255179_((Object[])new EntityType[]{EntityType.f_217015_, EntityType.f_20460_});
        this.m_206424_(ADTags.EntityTypes.SCULK).m_255179_((Object[])new EntityType[]{EntityType.f_217015_, ADEntityTypes.SCULKRAPTOR.get(), ADEntityTypes.SCULKSAUR.get()});
        this.m_206424_(ADTags.EntityTypes.CORALOSAUR_TURN).m_255179_((Object[])new EntityType[]{EntityType.f_20480_, EntityType.f_20489_, EntityType.f_20556_, EntityType.f_20519_, EntityType.f_20516_, EntityType.f_20455_, EntityType.f_147034_});
        this.m_206424_(EntityTypeTags.f_13123_).m_255245_(ADEntityTypes.PUS_TIPPED_ARROW.get());
    }

    protected void addForgeTags(HolderLookup.Provider provider) {
        this.m_206424_(Tags.EntityTypes.BOSSES).m_255245_(ADEntityTypes.SCULKSAUR.get());
    }

    @Override
    public void setEnvironment(PlatformSpecific.Environment environment) {
        this.environment = environment;
    }

    @Override
    public PlatformSpecific.Environment getEnvironment() {
        return this.environment;
    }
}

