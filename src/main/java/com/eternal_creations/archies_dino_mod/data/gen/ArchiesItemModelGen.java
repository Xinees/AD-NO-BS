/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.PackOutput
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.client.model.generators.ItemModelBuilder
 *  net.minecraftforge.client.model.generators.ItemModelProvider
 *  net.minecraftforge.client.model.generators.ModelFile
 *  net.minecraftforge.client.model.generators.ModelFile$UncheckedModelFile
 *  net.minecraftforge.common.data.ExistingFileHelper
 *  net.minecraftforge.registries.ForgeRegistries
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesItemModelGen
extends ItemModelProvider {
    public ArchiesItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "ec_ad", existingFileHelper);
    }

    protected void registerModels() {
        this.generated(ADItems.LUSHSAUR_MACE);
        this.generated(ADItems.CHORUS_SCYTHE);
        this.generated(ADItems.GUIDEBOOK);
        this.generated(ADItems.LUSHSAUR_EGG);
        this.generated(ADItems.CHORUSOATL_EGG);
        this.generated(ADItems.SCULKRAPTOR_EGG);
        this.generated(ADItems.OBSIDICERATOPS_EGG);
        this.generated(ADItems.THERIMOSS_EGG);
        this.generated(ADItems.LUSHSAUR_SPAWN_EGG);
        this.generated(ADItems.CHORUSOATL_SPAWN_EGG);
        this.generated(ADItems.OBSIDICERATOPS_SPAWN_EGG);
        this.generated(ADItems.THERIMOSS_SPAWN_EGG);
        this.generated(ADItems.SCULKSAUR_SPAWN_EGG);
        this.generated(ADItems.CORALOSAUR_SPAWN_EGG);
        this.generated(ADItems.SCULK_TOXIN);
        this.generated(ADItems.MOSSY_CLAW);
        this.generated(ADItems.OOZE_TIPPED_ARROW);
        this.generated(ADItems.CHORUS_FOSSIL);
        this.generated(ADItems.LUSH_CORE);
        this.generated(ADItems.CORAL_RIB);
        this.generated(ADItems.CORAL_STEW);
        this.generated(ADItems.LUSHSAUR_HELMET);
        this.generated(ADItems.LUSHSAUR_CHESTPLATE);
        this.generated(ADItems.LUSHSAUR_LEGGINGS);
        this.generated(ADItems.LUSHSAUR_BOOTS);
        this.generated(ADItems.CHORUSOATL_HELMET);
        this.generated(ADItems.CHORUSOATL_CHESTPLATE);
        this.generated(ADItems.CHORUSOATL_LEGGINGS);
        this.generated(ADItems.CHORUSOATL_BOOTS);
        this.generated(ADItems.LUSHSAUR_HELMET);
        this.generated(ADItems.LUSHSAUR_CHESTPLATE);
        this.generated(ADItems.LUSHSAUR_LEGGINGS);
        this.generated(ADItems.LUSHSAUR_BOOTS);
        this.generated(ADItems.OBSIDICERATOPS_HELMET);
        this.generated(ADItems.OBSIDICERATOPS_CHESTPLATE);
        this.generated(ADItems.OBSIDICERATOPS_LEGGINGS);
        this.generated(ADItems.OBSIDICERATOPS_BOOTS);
        this.generated(ADItems.SCULKSAUR_HELMET);
        this.generated(ADItems.SCULKSAUR_CHESTPLATE);
        this.generated(ADItems.SCULKSAUR_LEGGINGS);
        this.generated(ADItems.SCULKSAUR_BOOTS);
        this.generated(ADItems.CORALOSAUR_HELMET);
        this.generated(ADItems.CORALOSAUR_CHESTPLATE);
        this.generated(ADItems.CORALOSAUR_LEGGINGS);
        this.generated(ADItems.CORALOSAUR_BOOTS);
        this.generated(ADItems.THERIMOSS_HELMET);
        this.generated(ADItems.THERIMOSS_CHESTPLATE);
        this.generated(ADItems.THERIMOSS_LEGGINGS);
        this.generated(ADItems.THERIMOSS_BOOTS);
        this.generated(ADItems.THERIMOSS_CLAWS_COPPER);
        this.generated(ADItems.THERIMOSS_CLAWS_IRON);
        this.generated(ADItems.THERIMOSS_CLAWS_GOLD);
        this.generated(ADItems.THERIMOSS_CLAWS_DIAMOND);
        this.generated(ADItems.THERIMOSS_CLAWS_NETHERITE);
        this.generated(ADItems.RAPTOR_TALON);
        this.generated(ADItems.SCULKRAPTOR_SPAWN_EGG);
        this.generated(ADItems.SCULKSAUR_SPAWN_EGG);
        this.generated(ADItems.SCORCHING_HORN);
        ItemModelBuilder seekerPulling0 = this.bowItem("rib_bow_pulling_0", Constants.prefix("item/rib_bow_pulling_0"));
        ItemModelBuilder seekerPulling1 = this.bowItem("rib_bow_pulling_1", Constants.prefix("item/rib_bow_pulling_1"));
        ItemModelBuilder seekerPulling2 = this.bowItem("rib_bow_pulling_2", Constants.prefix("item/rib_bow_pulling_2"));
        this.bowTex(ADItems.RIB_BOW, (ModelFile)seekerPulling0, (ModelFile)seekerPulling1, (ModelFile)seekerPulling2);
        this.generated(ADItems.SCULK_BOW);
        this.generated(ADItems.AMBER);
        this.block(ADBlocks.LUSH_TOTEM);
        this.block(ADBlocks.BURNING_TOTEM);
        this.block(ADBlocks.TROPIC_TOTEM);
        this.block(ADBlocks.SOARING_TOTEM);
        this.block(ADBlocks.TIDAL_TOTEM);
        this.block(ADBlocks.ECHO_TOTEM);
        this.block(ADBlocks.FEARFUL_TOTEM);
        this.block(ADBlocks.AMBER_ORE);
        this.block(ADBlocks.RAW_AMBER_BLOCK);
        this.block(ADBlocks.POLISHED_AMBER);
        this.block(ADBlocks.POLISHED_AMBER_STAIRS);
        this.block(ADBlocks.POLISHED_AMBER_SLAB);
        this.wallInventory("polished_amber_wall", this.modLoc("block/polished_amber"));
        this.block(ADBlocks.AMBER_BRICKS);
        this.block(ADBlocks.AMBER_BRICK_STAIRS);
        this.block(ADBlocks.AMBER_BRICK_SLAB);
        this.wallInventory("amber_brick_wall", this.modLoc("block/amber_bricks"));
        this.block(ADBlocks.CRACKED_AMBER_BRICKS);
        this.block(ADBlocks.CARVED_AMBER);
        this.generated(ADItems.AMBER_CHAIN);
        this.generated(ADItems.AMBER_LAMP);
    }

    private void block(ResourceSupplier<? extends Block> block) {
        ResourceLocation loc = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey((Object)block.get()));
        ((ItemModelBuilder)this.getBuilder(loc.toString())).parent((ModelFile)new ModelFile.UncheckedModelFile(loc.m_135827_() + ":block/" + loc.m_135815_()));
    }

    private void spawnEgg(ResourceSupplier<? extends Item> item) {
        ResourceLocation loc = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey((Object)item.get().m_5456_()));
        ((ItemModelBuilder)this.getBuilder(loc.toString())).parent((ModelFile)new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }

    private void generated(ResourceSupplier<? extends Item> item) {
        ResourceLocation loc = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey((Object)item.get().m_5456_()));
        this.generated(loc);
    }

    private void generated(ResourceLocation loc) {
        ((ItemModelBuilder)((ItemModelBuilder)this.getBuilder(loc.toString())).parent((ModelFile)new ModelFile.UncheckedModelFile("item/generated"))).texture("layer0", new ResourceLocation(loc.m_135827_(), "item/" + loc.m_135815_()));
    }

    private ItemModelBuilder bowItem(String name, ResourceLocation ... layers) {
        ItemModelBuilder builder = (ItemModelBuilder)this.withExistingParent(name, "item/bow");
        for (int i = 0; i < layers.length; ++i) {
            builder = (ItemModelBuilder)builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }

    private void bowTex(ResourceSupplier<? extends Item> item, ModelFile pull0, ModelFile pull1, ModelFile pull2) {
        this.bowItem(item.id().m_135815_(), Constants.prefix("item/" + item.id().m_135815_())).override().predicate(ResourceLocation.withDefaultNamespace((String)"pulling"), 1.0f).model(pull0).end().override().predicate(ResourceLocation.withDefaultNamespace((String)"pulling"), 1.0f).predicate(ResourceLocation.withDefaultNamespace((String)"pull"), 0.65f).model(pull1).end().override().predicate(ResourceLocation.withDefaultNamespace((String)"pulling"), 1.0f).predicate(ResourceLocation.withDefaultNamespace((String)"pull"), 0.9f).model(pull2).end();
    }
}

