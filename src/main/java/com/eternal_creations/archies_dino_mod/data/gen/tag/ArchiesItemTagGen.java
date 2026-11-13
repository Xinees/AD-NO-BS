/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.tags.ItemTagsProvider
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraftforge.common.Tags$Blocks
 *  net.minecraftforge.common.Tags$Items
 *  net.minecraftforge.common.data.ExistingFileHelper
 */
package com.eternal_creations.archies_dino_mod.data.gen.tag;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesBlockTagGen;
import com.eternal_creations.archies_dino_mod.data.util.MultiPlatformProvider;
import com.eternal_creations.archies_dino_mod.data.util.PlatformSpecific;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesItemTagGen
extends ItemTagsProvider
implements PlatformSpecific {
    private PlatformSpecific.Environment environment;

    public ArchiesItemTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, MultiPlatformProvider<ArchiesBlockTagGen> blockTagGen, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagGen.getForgeProvider().m_274426_(), "ec_ad", existingFileHelper);
    }

    protected void m_6577_(HolderLookup.Provider provider) {
        if (this.isForge()) {
            this.addForgeTags(provider);
        }
        this.m_206424_(ADTags.Items.LUSH_TOTEM_FUEL).m_255245_((Object)ADItems.LUSH_CORE.get());
        this.m_206424_(ADTags.Items.BURNING_TOTEM_FUEL).m_255245_((Object)ADItems.OBSIDIAN_HORN.get());
        this.m_206424_(ADTags.Items.TROPIC_TOTEM_FUEL).m_255245_((Object)ADItems.MOSSY_CLAW.get());
        this.m_206424_(ADTags.Items.SOARING_TOTEM_FUEL).m_255245_((Object)ADItems.CHORUS_FOSSIL.get());
        this.m_206424_(ADTags.Items.TIDAL_TOTEM_FUEL).m_255245_((Object)ADItems.CORAL_RIB.get());
        this.m_206424_(ADTags.Items.ECHO_TOTEM_FUEL).m_255245_((Object)ADItems.RAPTOR_TALON.get());
        this.m_206424_(ADTags.Items.FEARFUL_TOTEM_FUEL).m_255245_((Object)ADItems.SCULK_TOXIN.get());
        this.m_206424_(ADTags.Items.CLAW).m_255179_((Object[])new Item[]{ADItems.THERIMOSS_CLAWS_COPPER.get(), ADItems.THERIMOSS_CLAWS_IRON.get(), ADItems.THERIMOSS_CLAWS_GOLD.get(), ADItems.THERIMOSS_CLAWS_DIAMOND.get(), ADItems.THERIMOSS_CLAWS_NETHERITE.get()});
        this.m_206424_(ADTags.Items.TWO_HANDED).m_255179_((Object[])new Item[]{ADItems.CHORUS_SCYTHE.get(), ADItems.LUSHSAUR_MACE.get()}).addTags(new TagKey[]{ADTags.Items.CLAW});
        this.m_206424_(ADTags.Items.LUSHSAUR_EDIBLE_ITEM).m_255245_((Object)Items.f_151079_);
        this.m_206424_(ADTags.Items.CHORUSOATL_EDIBLE_ITEM).m_255245_((Object)Items.f_42730_);
        this.m_206424_(ADTags.Items.OBSIDICERATOPS_EDIBLE_ITEM).m_255245_((Object)Items.f_42258_);
        this.m_206421_(ADTags.Blocks.THERIMOSS_EDIBLE_BLOCKS, ADTags.Items.THERIMOSS_EDIBLE_ITEM);
        this.m_206424_(ADTags.Items.THERIMOSS_EDIBLE_ITEM).m_255179_((Object[])new Item[]{Items.f_42575_, Items.f_42410_, Items.f_41911_, Items.f_42780_, Items.f_41952_, Items.f_41953_});
        this.m_206424_(ADTags.Items.CORALOSAUR_EDIBLE_ITEM).m_255245_((Object)ADItems.CORAL_STEW.get());
        this.m_206424_(ItemTags.f_13151_).m_255245_((Object)ADItems.THERIMOSS_CLAWS_GOLD.get());
        this.m_206421_(BlockTags.f_13030_, ItemTags.f_13138_);
        this.m_206421_(BlockTags.f_13031_, ItemTags.f_13139_);
        this.m_206421_(BlockTags.f_13032_, ItemTags.f_13140_);
    }

    protected void addForgeTags(HolderLookup.Provider provider) {
        this.m_206424_(Tags.Items.ARMORS_HELMETS).m_255179_((Object[])new Item[]{(Item)ADItems.LUSHSAUR_HELMET.get(), (Item)ADItems.CHORUSOATL_HELMET.get(), (Item)ADItems.OBSIDICERATOPS_HELMET.get(), (Item)ADItems.SCULKSAUR_HELMET.get(), (Item)ADItems.CORALOSAUR_HELMET.get(), (Item)ADItems.THERIMOSS_HELMET.get()});
        this.m_206424_(Tags.Items.ARMORS_CHESTPLATES).m_255179_((Object[])new Item[]{(Item)ADItems.LUSHSAUR_CHESTPLATE.get(), (Item)ADItems.CHORUSOATL_CHESTPLATE.get(), (Item)ADItems.OBSIDICERATOPS_CHESTPLATE.get(), (Item)ADItems.SCULKSAUR_CHESTPLATE.get(), (Item)ADItems.CORALOSAUR_CHESTPLATE.get(), (Item)ADItems.THERIMOSS_CHESTPLATE.get()});
        this.m_206424_(Tags.Items.ARMORS_LEGGINGS).m_255179_((Object[])new Item[]{(Item)ADItems.LUSHSAUR_LEGGINGS.get(), (Item)ADItems.CHORUSOATL_LEGGINGS.get(), (Item)ADItems.OBSIDICERATOPS_LEGGINGS.get(), (Item)ADItems.SCULKSAUR_LEGGINGS.get(), (Item)ADItems.CORALOSAUR_LEGGINGS.get(), (Item)ADItems.THERIMOSS_LEGGINGS.get()});
        this.m_206424_(Tags.Items.ARMORS_BOOTS).m_255179_((Object[])new Item[]{(Item)ADItems.LUSHSAUR_BOOTS.get(), (Item)ADItems.CHORUSOATL_BOOTS.get(), (Item)ADItems.OBSIDICERATOPS_BOOTS.get(), (Item)ADItems.SCULKSAUR_BOOTS.get(), (Item)ADItems.CORALOSAUR_BOOTS.get(), (Item)ADItems.THERIMOSS_BOOTS.get()});
        this.m_206421_(Tags.Blocks.ORES, Tags.Items.ORES);
        this.m_206421_(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        this.m_206424_(Tags.Items.GEMS).m_255245_((Object)ADItems.AMBER.get());
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

