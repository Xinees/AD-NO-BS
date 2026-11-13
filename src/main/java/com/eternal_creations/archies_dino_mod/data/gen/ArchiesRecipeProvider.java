/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.CriterionTriggerInstance
 *  net.minecraft.advancements.critereon.ImpossibleTrigger$TriggerInstance
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.data.recipes.RecipeCategory
 *  net.minecraft.data.recipes.RecipeProvider
 *  net.minecraft.data.recipes.ShapedRecipeBuilder
 *  net.minecraft.data.recipes.ShapelessRecipeBuilder
 *  net.minecraft.data.recipes.SimpleCookingRecipeBuilder
 *  net.minecraft.data.recipes.SingleItemRecipeBuilder
 *  net.minecraft.data.recipes.SmithingTransformRecipeBuilder
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraftforge.common.Tags$Items
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.data.util.PlatformSpecific;
import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesRecipeProvider
extends RecipeProvider
implements PlatformSpecific {
    private PlatformSpecific.Environment environment;

    public ArchiesRecipeProvider(PackOutput output) {
        super(output);
    }

    protected void m_245200_(Consumer<FinishedRecipe> output) {
        this.registerWeapons(output);
        this.registerArmor(output);
        this.registerToolsAndMisc(output);
        this.registerAmber(output);
        this.registerTotems(output);
    }

    private void registerWeapons(Consumer<FinishedRecipe> output) {
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)((ItemLike)ADItems.LUSHSAUR_MACE.get())).m_126130_("W").m_126130_("S").m_126130_("S").m_126127_(Character.valueOf('W'), (ItemLike)ADItems.LUSH_CORE.get()).m_126127_(Character.valueOf('S'), (ItemLike)Blocks.f_152537_).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.LUSH_CORE.get()))).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)((ItemLike)ADItems.CHORUS_SCYTHE.get())).m_126130_("WW").m_126130_(" I").m_126130_(" I").m_126127_(Character.valueOf('W'), (ItemLike)ADItems.CHORUS_FOSSIL.get()).m_126124_(Character.valueOf('I'), this.selectIngredient(Tags.Items.INGOTS_GOLD, Items.f_42417_)).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.CHORUS_FOSSIL.get()))).m_176498_(output);
        ShapedRecipeBuilder.m_246608_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)((ItemLike)ADItems.OOZE_TIPPED_ARROW.get()), (int)4).m_126130_("W").m_126130_("I").m_126130_("S").m_126127_(Character.valueOf('W'), (ItemLike)ADItems.SCULK_TOXIN.get()).m_126124_(Character.valueOf('I'), this.selectIngredient(Tags.Items.RODS_WOODEN, Items.f_42398_)).m_126124_(Character.valueOf('S'), this.selectIngredient(Tags.Items.FEATHERS, Items.f_42402_)).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.SCULK_TOXIN.get()))).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)((ItemLike)ADItems.RIB_BOW.get())).m_126130_("DW ").m_126130_("I W").m_126130_("DW ").m_126127_(Character.valueOf('W'), (ItemLike)ADItems.CORAL_RIB.get()).m_126124_(Character.valueOf('I'), this.selectIngredient(Tags.Items.STRING, Items.f_42401_)).m_126127_(Character.valueOf('D'), (ItemLike)ADItems.RAPTOR_TALON.get()).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.CORAL_RIB.get()))).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)((ItemLike)ADItems.SCULK_BOW.get())).m_126130_("IW ").m_126130_("S W").m_126130_("IW ").m_126127_(Character.valueOf('W'), (ItemLike)Items.f_42585_).m_126127_(Character.valueOf('I'), (ItemLike)ADItems.SCULK_TOXIN.get()).m_126127_(Character.valueOf('S'), (ItemLike)ADItems.OBSIDIAN_HORN.get()).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.SCULK_TOXIN.get()))).m_176498_(output);
        Ingredient leather = this.selectIngredient(Tags.Items.LEATHER, Items.f_42454_);
        ArchiesRecipeProvider.clawRecipe((ItemLike)ADItems.THERIMOSS_CLAWS_COPPER.get(), this.selectIngredient(Tags.Items.INGOTS_COPPER, Items.f_151052_), leather, output);
        ArchiesRecipeProvider.clawRecipe((ItemLike)ADItems.THERIMOSS_CLAWS_IRON.get(), this.selectIngredient(Tags.Items.INGOTS_IRON, Items.f_42416_), leather, output);
        ArchiesRecipeProvider.clawRecipe((ItemLike)ADItems.THERIMOSS_CLAWS_GOLD.get(), this.selectIngredient(Tags.Items.INGOTS_GOLD, Items.f_42417_), leather, output);
        ArchiesRecipeProvider.clawRecipe((ItemLike)ADItems.THERIMOSS_CLAWS_DIAMOND.get(), this.selectIngredient(Tags.Items.GEMS_DIAMOND, Items.f_42415_), leather, output);
        this.netheriteUpgrade((ItemLike)ADItems.THERIMOSS_CLAWS_DIAMOND.get(), (ItemLike)ADItems.THERIMOSS_CLAWS_NETHERITE.get(), "netherite_therimoss_claws", output);
    }

    private void registerArmor(Consumer<FinishedRecipe> output) {
        ArchiesRecipeProvider.defaultArmorRecipe((ItemLike)ADItems.LUSH_CORE.get(), (ItemLike)ADItems.LUSHSAUR_HELMET.get(), (ItemLike)ADItems.LUSHSAUR_CHESTPLATE.get(), (ItemLike)ADItems.LUSHSAUR_LEGGINGS.get(), (ItemLike)ADItems.LUSHSAUR_BOOTS.get(), output);
        ArchiesRecipeProvider.defaultArmorRecipe((ItemLike)ADItems.CHORUS_FOSSIL.get(), (ItemLike)ADItems.CHORUSOATL_HELMET.get(), (ItemLike)ADItems.CHORUSOATL_CHESTPLATE.get(), (ItemLike)ADItems.CHORUSOATL_LEGGINGS.get(), (ItemLike)ADItems.CHORUSOATL_BOOTS.get(), output);
        ArchiesRecipeProvider.defaultArmorRecipe((ItemLike)ADItems.SCULK_TOXIN.get(), (ItemLike)ADItems.SCULKSAUR_HELMET.get(), (ItemLike)ADItems.SCULKSAUR_CHESTPLATE.get(), (ItemLike)ADItems.SCULKSAUR_LEGGINGS.get(), (ItemLike)ADItems.SCULKSAUR_BOOTS.get(), output);
        ArchiesRecipeProvider.defaultArmorRecipe((ItemLike)ADItems.OBSIDIAN_HORN.get(), (ItemLike)ADItems.OBSIDICERATOPS_HELMET.get(), (ItemLike)ADItems.OBSIDICERATOPS_CHESTPLATE.get(), (ItemLike)ADItems.OBSIDICERATOPS_LEGGINGS.get(), (ItemLike)ADItems.OBSIDICERATOPS_BOOTS.get(), output);
        ArchiesRecipeProvider.defaultArmorRecipe((ItemLike)ADItems.CORAL_RIB.get(), (ItemLike)ADItems.CORALOSAUR_HELMET.get(), (ItemLike)ADItems.CORALOSAUR_CHESTPLATE.get(), (ItemLike)ADItems.CORALOSAUR_LEGGINGS.get(), (ItemLike)ADItems.CORALOSAUR_BOOTS.get(), output);
        ArchiesRecipeProvider.defaultArmorRecipe((ItemLike)ADItems.MOSSY_CLAW.get(), (ItemLike)ADItems.THERIMOSS_HELMET.get(), (ItemLike)ADItems.THERIMOSS_CHESTPLATE.get(), (ItemLike)ADItems.THERIMOSS_LEGGINGS.get(), (ItemLike)ADItems.THERIMOSS_BOOTS.get(), output);
    }

    private void registerToolsAndMisc(Consumer<FinishedRecipe> output) {
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.TOOLS, (ItemLike)((ItemLike)ADItems.SCORCHING_HORN.get())).m_126130_("W ").m_126130_("IW").m_126127_(Character.valueOf('W'), (ItemLike)ADItems.OBSIDIAN_HORN.get()).m_126124_(Character.valueOf('I'), this.selectIngredient(Tags.Items.INGOTS_GOLD, Items.f_42417_)).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.OBSIDIAN_HORN.get()))).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.FOOD, (ItemLike)((ItemLike)ADItems.CORAL_STEW.get())).m_126130_("ITW").m_126130_(" S ").m_126127_(Character.valueOf('W'), (ItemLike)Items.f_42292_).m_126127_(Character.valueOf('T'), (ItemLike)Items.f_42293_).m_126127_(Character.valueOf('I'), (ItemLike)Items.f_42290_).m_126127_(Character.valueOf('S'), (ItemLike)Items.f_42399_).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)Items.f_42292_)).m_176498_(output);
        ShapelessRecipeBuilder.m_245498_((RecipeCategory)RecipeCategory.MISC, (ItemLike)((ItemLike)ADItems.GUIDEBOOK.get())).m_126209_((ItemLike)Items.f_42517_).m_126209_((ItemLike)Items.f_42594_).m_126209_((ItemLike)Items.f_41910_).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)Items.f_42517_)).m_176498_(output);
    }

    private static void clawRecipe(ItemLike result, Ingredient material, Ingredient leather, Consumer<FinishedRecipe> output) {
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)result).m_126130_("D D").m_126130_("SWS").m_126130_(" Y ").m_126124_(Character.valueOf('W'), material).m_126127_(Character.valueOf('S'), (ItemLike)ADItems.CORAL_RIB.get()).m_126127_(Character.valueOf('D'), (ItemLike)ADItems.MOSSY_CLAW.get()).m_126124_(Character.valueOf('Y'), leather).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.MOSSY_CLAW.get()))).m_176498_(output);
    }

    private static void defaultArmorRecipe(ItemLike baseItem, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots, Consumer<FinishedRecipe> output) {
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)helmet).m_126130_("OOO").m_126130_("O O").m_126127_(Character.valueOf('O'), baseItem).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)baseItem)).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)chestplate).m_126130_("O O").m_126130_("OOO").m_126130_("OOO").m_126127_(Character.valueOf('O'), baseItem).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)baseItem)).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)leggings).m_126130_("OOO").m_126130_("O O").m_126130_("O O").m_126127_(Character.valueOf('O'), baseItem).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)baseItem)).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.COMBAT, (ItemLike)boots).m_126130_("O O").m_126130_("O O").m_126127_(Character.valueOf('O'), baseItem).m_126132_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)baseItem)).m_176498_(output);
    }

    private void registerAmber(Consumer<FinishedRecipe> output) {
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.RAW_AMBER_BLOCK.get())).m_126130_("AA").m_126130_("AA").m_126127_(Character.valueOf('A'), (ItemLike)ADItems.AMBER.get()).m_126132_("has_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.AMBER.get()))).m_176498_(output);
        ShapelessRecipeBuilder.m_246517_((RecipeCategory)RecipeCategory.MISC, (ItemLike)((ItemLike)ADItems.AMBER.get()), (int)4).m_126209_((ItemLike)ADBlocks.RAW_AMBER_BLOCK.get()).m_126132_("has_amber_block", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.RAW_AMBER_BLOCK.get()))).m_126140_(output, Constants.prefix("amber_from_amber_block"));
        ShapedRecipeBuilder.m_246608_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()), (int)4).m_126130_("AA").m_126130_("AA").m_126127_(Character.valueOf('A'), (ItemLike)ADBlocks.RAW_AMBER_BLOCK.get()).m_126132_("has_amber_block", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.RAW_AMBER_BLOCK.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_247600_(output, (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()), (ItemLike)((ItemLike)ADBlocks.RAW_AMBER_BLOCK.get()));
        ArchiesRecipeProvider.m_176710_((ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER_STAIRS.get()), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.POLISHED_AMBER.get()})).m_126132_("has_polished_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_247600_(output, (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER_STAIRS.get()), (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()));
        ArchiesRecipeProvider.m_247552_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER_SLAB.get()), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.POLISHED_AMBER.get()})).m_126132_("has_polished_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_247298_(output, (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER_SLAB.get()), (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()), (int)2);
        ArchiesRecipeProvider.m_245864_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER_WALL.get()), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.POLISHED_AMBER.get()})).m_126132_("has_polished_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_247600_(output, (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER_WALL.get()), (ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()));
        ShapedRecipeBuilder.m_246608_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()), (int)4).m_126130_("AA").m_126130_("AA").m_126127_(Character.valueOf('A'), (ItemLike)ADBlocks.POLISHED_AMBER.get()).m_126132_("has_polished_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()))).m_176498_(output);
        SingleItemRecipeBuilder.m_245264_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.RAW_AMBER_BLOCK.get()}), (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get())).m_126132_("has_amber_block", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.RAW_AMBER_BLOCK.get()))).m_126140_(output, Constants.prefix("amber_bricks_from_amber_block_stonecutting"));
        SingleItemRecipeBuilder.m_245264_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.POLISHED_AMBER.get()}), (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get())).m_126132_("has_polished_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.POLISHED_AMBER.get()))).m_126140_(output, Constants.prefix("amber_bricks_from_polished_amber_stonecutting"));
        ArchiesRecipeProvider.m_176710_((ItemLike)((ItemLike)ADBlocks.AMBER_BRICK_STAIRS.get()), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.AMBER_BRICKS.get()})).m_126132_("has_amber_bricks", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_247600_(output, (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICK_STAIRS.get()), (ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()));
        ArchiesRecipeProvider.m_247552_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICK_SLAB.get()), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.AMBER_BRICKS.get()})).m_126132_("has_amber_bricks", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_247298_(output, (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICK_SLAB.get()), (ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()), (int)2);
        ArchiesRecipeProvider.m_245864_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICK_WALL.get()), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.AMBER_BRICKS.get()})).m_126132_("has_amber_bricks", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_247600_(output, (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.AMBER_BRICK_WALL.get()), (ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()));
        SimpleCookingRecipeBuilder.m_246179_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.AMBER_BRICKS.get()}), (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.CRACKED_AMBER_BRICKS.get()), (float)0.1f, (int)200).m_126132_("has_amber_bricks", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()))).m_176498_(output);
        ArchiesRecipeProvider.m_246451_((RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.CARVED_AMBER.get()), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.AMBER_BRICK_SLAB.get()})).m_126132_("has_amber_brick_slab", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.AMBER_BRICK_SLAB.get()))).m_176498_(output);
        SingleItemRecipeBuilder.m_245264_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{(ItemLike)ADBlocks.AMBER_BRICKS.get()}), (RecipeCategory)RecipeCategory.BUILDING_BLOCKS, (ItemLike)((ItemLike)ADBlocks.CARVED_AMBER.get())).m_126132_("has_amber_bricks", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADBlocks.AMBER_BRICKS.get()))).m_126140_(output, Constants.prefix("chiseled_amber_from_amber_bricks_stonecutting"));
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.DECORATIONS, (ItemLike)((ItemLike)ADBlocks.AMBER_CHAIN.get())).m_126130_("N").m_126130_("A").m_126130_("N").m_126127_(Character.valueOf('A'), (ItemLike)ADItems.AMBER.get()).m_126124_(Character.valueOf('N'), this.selectIngredient(Tags.Items.NUGGETS_IRON, Items.f_42749_)).m_126132_("has_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.AMBER.get()))).m_176498_(output);
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.DECORATIONS, (ItemLike)((ItemLike)ADBlocks.AMBER_LAMP.get())).m_126130_(" A ").m_126130_("AGA").m_126130_(" A ").m_126127_(Character.valueOf('A'), (ItemLike)ADItems.AMBER.get()).m_126124_(Character.valueOf('G'), this.selectIngredient(Tags.Items.DUSTS_GLOWSTONE, Items.f_42525_)).m_126132_("has_amber", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)((ItemLike)ADItems.AMBER.get()))).m_176498_(output);
    }

    private void registerTotems(Consumer<FinishedRecipe> output) {
        ArchiesRecipeProvider.totemRecipe((ItemLike)ADBlocks.LUSH_TOTEM.get(), ADTags.Items.LUSH_TOTEM_FUEL, output);
        ArchiesRecipeProvider.totemRecipe((ItemLike)ADBlocks.BURNING_TOTEM.get(), ADTags.Items.BURNING_TOTEM_FUEL, output);
        ArchiesRecipeProvider.totemRecipe((ItemLike)ADBlocks.TROPIC_TOTEM.get(), ADTags.Items.TROPIC_TOTEM_FUEL, output);
        ArchiesRecipeProvider.totemRecipe((ItemLike)ADBlocks.SOARING_TOTEM.get(), ADTags.Items.SOARING_TOTEM_FUEL, output);
        ArchiesRecipeProvider.totemRecipe((ItemLike)ADBlocks.TIDAL_TOTEM.get(), ADTags.Items.TIDAL_TOTEM_FUEL, output);
        ArchiesRecipeProvider.totemRecipe((ItemLike)ADBlocks.ECHO_TOTEM.get(), ADTags.Items.ECHO_TOTEM_FUEL, output);
        ArchiesRecipeProvider.totemRecipe((ItemLike)ADBlocks.FEARFUL_TOTEM.get(), ADTags.Items.FEARFUL_TOTEM_FUEL, output);
    }

    private static void totemRecipe(ItemLike totem, TagKey<Item> dinoDrop, Consumer<FinishedRecipe> output) {
        ShapedRecipeBuilder.m_245327_((RecipeCategory)RecipeCategory.DECORATIONS, (ItemLike)totem).m_126130_("AAA").m_126130_("AWA").m_126130_("A A").m_126127_(Character.valueOf('A'), (ItemLike)ADItems.AMBER.get()).m_206416_(Character.valueOf('W'), dinoDrop).m_126132_("impossible", (CriterionTriggerInstance)new ImpossibleTrigger.TriggerInstance()).m_176498_(output);
    }

    private void netheriteUpgrade(ItemLike baseItem, ItemLike resultItem, String name, Consumer<FinishedRecipe> output) {
        SmithingTransformRecipeBuilder.m_266555_((Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{Items.f_265918_}), (Ingredient)Ingredient.m_43929_((ItemLike[])new ItemLike[]{baseItem}), (Ingredient)this.selectIngredient(Tags.Items.INGOTS_NETHERITE, Items.f_42418_), (RecipeCategory)RecipeCategory.COMBAT, (Item)resultItem.m_5456_()).m_266439_("has_item", (CriterionTriggerInstance)ArchiesRecipeProvider.m_125977_((ItemLike)baseItem)).m_266371_(output, Constants.prefix(name + "_upgrade"));
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

