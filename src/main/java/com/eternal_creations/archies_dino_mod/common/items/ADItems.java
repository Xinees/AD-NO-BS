/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.food.FoodProperties$Builder
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.SpawnEggItem
 *  net.minecraft.world.level.block.Block
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.block.TotemBlock;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.items.GuideBookItem;
import com.eternal_creations.archies_dino_mod.common.items.ObsidiceratopsEggItem;
import com.eternal_creations.archies_dino_mod.common.items.ObsidiceratopsHornItem;
import com.eternal_creations.archies_dino_mod.common.items.PusArrowItem;
import com.eternal_creations.archies_dino_mod.common.items.RaptorTalonItem;
import com.eternal_creations.archies_dino_mod.common.items.ScorchingHornItem;
import com.eternal_creations.archies_dino_mod.common.items.TotemItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.ChorusoatlArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.CoralosaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.LushsaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.ObsidiceratopsArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.SculksaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.TherimossArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.ChorusScytheItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.LushsaurMaceItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.RibBowItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.SculkBowItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.server.ServerConfig;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADItems {
    public static final ResourceSupplier<Item> LUSH_CORE = Services.COMMON.registerItem("lush_core", Item::new);
    public static final ResourceSupplier<LushsaurMaceItem> LUSHSAUR_MACE = Services.COMMON.registerItem("lush_mace", properties -> new LushsaurMaceItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::lushsaurMace));
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_HELMET = Services.COMMON.registerItem("lushsaur_helmet", properties -> new LushsaurArmorItem(ArmorItem.Type.HELMET, (Item.Properties)properties));
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_CHESTPLATE = Services.COMMON.registerItem("lushsaur_chestplate", properties -> new LushsaurArmorItem(ArmorItem.Type.CHESTPLATE, (Item.Properties)properties));
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_LEGGINGS = Services.COMMON.registerItem("lushsaur_leggings", properties -> new LushsaurArmorItem(ArmorItem.Type.LEGGINGS, (Item.Properties)properties));
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_BOOTS = Services.COMMON.registerItem("lushsaur_boots", properties -> new LushsaurArmorItem(ArmorItem.Type.BOOTS, (Item.Properties)properties));
    public static final ResourceSupplier<Item> CHORUS_FOSSIL = Services.COMMON.registerItem("chorus_fossil", Item::new);
    public static final ResourceSupplier<ChorusScytheItem> CHORUS_SCYTHE = Services.COMMON.registerItem("chorus_scythe", properties -> new ChorusScytheItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::chorusScythe));
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_HELMET = Services.COMMON.registerItem("chorusoatl_helmet", properties -> new ChorusoatlArmorItem(ArmorItem.Type.HELMET, (Item.Properties)properties));
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_CHESTPLATE = Services.COMMON.registerItem("chorusoatl_chestplate", properties -> new ChorusoatlArmorItem(ArmorItem.Type.CHESTPLATE, (Item.Properties)properties));
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_LEGGINGS = Services.COMMON.registerItem("chorusoatl_leggings", properties -> new ChorusoatlArmorItem(ArmorItem.Type.LEGGINGS, (Item.Properties)properties));
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_BOOTS = Services.COMMON.registerItem("chorusoatl_boots", properties -> new ChorusoatlArmorItem(ArmorItem.Type.BOOTS, (Item.Properties)properties));
    public static final ResourceSupplier<Item> OBSIDIAN_HORN = Services.COMMON.registerItem("obsidian_horn", ObsidiceratopsHornItem::new);
    public static final ResourceSupplier<RaptorTalonItem> RAPTOR_TALON = Services.COMMON.registerItem("raptor_talon", RaptorTalonItem::new);
    public static final ResourceSupplier<Item> SCULK_TOXIN = Services.COMMON.registerItem("sculk_toxin", Item::new);
    public static final ResourceSupplier<Item> MOSSY_CLAW = Services.COMMON.registerItem("mossy_claw", Item::new);
    public static final ResourceSupplier<PusArrowItem> OOZE_TIPPED_ARROW = Services.COMMON.registerItem("ooze_tipped_arrow", PusArrowItem::new);
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_HELMET = Services.COMMON.registerItem("obsidiceratops_helmet", properties -> new ObsidiceratopsArmorItem(ArmorItem.Type.HELMET, (Item.Properties)properties));
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_CHESTPLATE = Services.COMMON.registerItem("obsidiceratops_chestplate", properties -> new ObsidiceratopsArmorItem(ArmorItem.Type.CHESTPLATE, (Item.Properties)properties));
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_LEGGINGS = Services.COMMON.registerItem("obsidiceratops_leggings", properties -> new ObsidiceratopsArmorItem(ArmorItem.Type.LEGGINGS, (Item.Properties)properties));
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_BOOTS = Services.COMMON.registerItem("obsidiceratops_boots", properties -> new ObsidiceratopsArmorItem(ArmorItem.Type.BOOTS, (Item.Properties)properties));
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_HELMET = Services.COMMON.registerItem("sculksaur_helmet", properties -> new SculksaurArmorItem(ArmorItem.Type.HELMET, (Item.Properties)properties));
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_CHESTPLATE = Services.COMMON.registerItem("sculksaur_chestplate", properties -> new SculksaurArmorItem(ArmorItem.Type.CHESTPLATE, (Item.Properties)properties));
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_LEGGINGS = Services.COMMON.registerItem("sculksaur_leggings", properties -> new SculksaurArmorItem(ArmorItem.Type.LEGGINGS, (Item.Properties)properties));
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_BOOTS = Services.COMMON.registerItem("sculksaur_boots", properties -> new SculksaurArmorItem(ArmorItem.Type.BOOTS, (Item.Properties)properties));
    public static final ResourceSupplier<Item> CORAL_RIB = Services.COMMON.registerItem("coral_rib", Item::new);
    public static final ResourceSupplier<Item> CORAL_STEW = Services.COMMON.registerItem("coral_stew", properties -> new Item(properties.m_41489_(new FoodProperties.Builder().m_38760_(2).m_38758_(0.6f).m_38762_(new MobEffectInstance(MobEffects.f_19608_, 1200, 0), 1.0f).m_38767_())));
    public static final ResourceSupplier<RibBowItem> RIB_BOW = Services.COMMON.registerItem("rib_bow", properties -> new RibBowItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::ribBow));
    public static final ResourceSupplier<SculkBowItem> SCULK_BOW = Services.COMMON.registerItem("sculk_bow", properties -> new SculkBowItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::sculkBow));
    public static final ResourceSupplier<ScorchingHornItem> SCORCHING_HORN = Services.COMMON.registerItem("scorching_horn", properties -> new ScorchingHornItem());
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_HELMET = Services.COMMON.registerItem("coralosaur_helmet", properties -> new CoralosaurArmorItem(ArmorItem.Type.HELMET, (Item.Properties)properties));
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_CHESTPLATE = Services.COMMON.registerItem("coralosaur_chestplate", properties -> new CoralosaurArmorItem(ArmorItem.Type.CHESTPLATE, (Item.Properties)properties));
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_LEGGINGS = Services.COMMON.registerItem("coralosaur_leggings", properties -> new CoralosaurArmorItem(ArmorItem.Type.LEGGINGS, (Item.Properties)properties));
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_BOOTS = Services.COMMON.registerItem("coralosaur_boots", properties -> new CoralosaurArmorItem(ArmorItem.Type.BOOTS, (Item.Properties)properties));
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_HELMET = Services.COMMON.registerItem("therimoss_helmet", properties -> new TherimossArmorItem(ArmorItem.Type.HELMET, (Item.Properties)properties));
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_CHESTPLATE = Services.COMMON.registerItem("therimoss_chestplate", properties -> new TherimossArmorItem(ArmorItem.Type.CHESTPLATE, (Item.Properties)properties));
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_LEGGINGS = Services.COMMON.registerItem("therimoss_leggings", properties -> new TherimossArmorItem(ArmorItem.Type.LEGGINGS, (Item.Properties)properties));
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_BOOTS = Services.COMMON.registerItem("therimoss_boots", properties -> new TherimossArmorItem(ArmorItem.Type.BOOTS, (Item.Properties)properties));
    public static final ResourceSupplier<GuideBookItem> GUIDEBOOK = Services.COMMON.registerItem("guidebook", properties -> new GuideBookItem());
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_COPPER = Services.COMMON.registerItem("theri_claw_copper", properties -> new TherimossClawsItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::copperClaws));
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_IRON = Services.COMMON.registerItem("theri_claw_iron", properties -> new TherimossClawsItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::ironClaws));
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_GOLD = Services.COMMON.registerItem("theri_claw_gold", properties -> new TherimossClawsItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::goldClaws));
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_DIAMOND = Services.COMMON.registerItem("theri_claw_diamond", properties -> new TherimossClawsItem((Item.Properties)properties, ServerConfig.getWeaponSettings()::diamondClaws));
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_NETHERITE = Services.COMMON.registerItem("theri_claw_netherite", properties -> new TherimossClawsItem(properties.m_41486_(), ServerConfig.getWeaponSettings()::netheriteClaws));
    public static final ResourceSupplier<BlockItem> LUSHSAUR_EGG = ADItems.registerBlock(ADBlocks.LUSHSAUR_EGG);
    public static final ResourceSupplier<BlockItem> CHORUSOATL_EGG = ADItems.registerBlock(ADBlocks.CHORUSOATL_EGG);
    public static final ResourceSupplier<BlockItem> SCULKRAPTOR_EGG = ADItems.registerBlock(ADBlocks.SCULKRAPTOR_EGG);
    public static final ResourceSupplier<ObsidiceratopsEggItem> OBSIDICERATOPS_EGG = Services.COMMON.registerItem("obsidiceratops_egg", ObsidiceratopsEggItem::new);
    public static final ResourceSupplier<BlockItem> THERIMOSS_EGG = ADItems.registerBlock(ADBlocks.THERIMOSS_EGG);
    public static final ResourceSupplier<SpawnEggItem> LUSHSAUR_SPAWN_EGG = Services.COMMON.registerItem("lushsaur_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.LUSHSAUR, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> CHORUSOATL_SPAWN_EGG = Services.COMMON.registerItem("chorusoatl_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.CHORUSOATL, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> SCULKRAPTOR_SPAWN_EGG = Services.COMMON.registerItem("sculkraptor_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.SCULKRAPTOR, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> SCULKSAUR_SPAWN_EGG = Services.COMMON.registerItem("sculksaur_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.SCULKSAUR, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> OBSIDICERATOPS_SPAWN_EGG = Services.COMMON.registerItem("obsidiceratops_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.OBSIDICERATOPS, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> THERIMOSS_SPAWN_EGG = Services.COMMON.registerItem("therimoss_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.THERIMOSS, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> CORALOSAUR_SPAWN_EGG = Services.COMMON.registerItem("coralosaur_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.CORALOSAUR, (Item.Properties)properties));
    public static final ResourceSupplier<TotemItem> LUSH_TOTEM = ADItems.registerTotem(ADBlocks.LUSH_TOTEM);
    public static final ResourceSupplier<TotemItem> BURNING_TOTEM = ADItems.registerTotem(ADBlocks.BURNING_TOTEM);
    public static final ResourceSupplier<TotemItem> TROPIC_TOTEM = ADItems.registerTotem(ADBlocks.TROPIC_TOTEM);
    public static final ResourceSupplier<TotemItem> SOARING_TOTEM = ADItems.registerTotem(ADBlocks.SOARING_TOTEM);
    public static final ResourceSupplier<TotemItem> TIDAL_TOTEM = ADItems.registerTotem(ADBlocks.TIDAL_TOTEM);
    public static final ResourceSupplier<TotemItem> ECHO_TOTEM = ADItems.registerTotem(ADBlocks.ECHO_TOTEM);
    public static final ResourceSupplier<TotemItem> FEARFUL_TOTEM = ADItems.registerTotem(ADBlocks.FEARFUL_TOTEM);
    public static final ResourceSupplier<Item> AMBER = Services.COMMON.registerItem("amber", Item::new);
    public static final ResourceSupplier<BlockItem> AMBER_ORE = ADItems.registerBlock(ADBlocks.AMBER_ORE);
    public static final ResourceSupplier<BlockItem> RAW_AMBER_BLOCK = ADItems.registerBlock(ADBlocks.RAW_AMBER_BLOCK);
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER = ADItems.registerBlock(ADBlocks.POLISHED_AMBER);
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER_STAIRS = ADItems.registerBlock(ADBlocks.POLISHED_AMBER_STAIRS);
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER_SLAB = ADItems.registerBlock(ADBlocks.POLISHED_AMBER_SLAB);
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER_WALL = ADItems.registerBlock(ADBlocks.POLISHED_AMBER_WALL);
    public static final ResourceSupplier<BlockItem> CARVED_AMBER = ADItems.registerBlock(ADBlocks.CARVED_AMBER);
    public static final ResourceSupplier<BlockItem> AMBER_BRICKS = ADItems.registerBlock(ADBlocks.AMBER_BRICKS);
    public static final ResourceSupplier<BlockItem> AMBER_BRICK_STAIRS = ADItems.registerBlock(ADBlocks.AMBER_BRICK_STAIRS);
    public static final ResourceSupplier<BlockItem> AMBER_BRICK_SLAB = ADItems.registerBlock(ADBlocks.AMBER_BRICK_SLAB);
    public static final ResourceSupplier<BlockItem> AMBER_BRICK_WALL = ADItems.registerBlock(ADBlocks.AMBER_BRICK_WALL);
    public static final ResourceSupplier<BlockItem> CRACKED_AMBER_BRICKS = ADItems.registerBlock(ADBlocks.CRACKED_AMBER_BRICKS);
    public static final ResourceSupplier<BlockItem> AMBER_CHAIN = ADItems.registerBlock(ADBlocks.AMBER_CHAIN);
    public static final ResourceSupplier<BlockItem> AMBER_LAMP = ADItems.registerBlock(ADBlocks.AMBER_LAMP);

    public static void init() {
    }

    private static <T extends Block> ResourceSupplier<BlockItem> registerBlock(ResourceSupplier<T> block) {
        return Services.COMMON.registerItem(block.id().m_135815_(), properties -> new BlockItem((Block)block.get(), properties));
    }

    private static <T extends TotemBlock> ResourceSupplier<TotemItem> registerTotem(ResourceSupplier<T> block) {
        return Services.COMMON.registerItem(block.id().m_135815_(), properties -> new TotemItem((TotemBlock)((Object)((Object)block.get())), (Item.Properties)properties));
    }
}

