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
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_HELMET = null;
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_CHESTPLATE = null;
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_LEGGINGS = null;
    public static final ResourceSupplier<LushsaurArmorItem> LUSHSAUR_BOOTS = null;
    public static final ResourceSupplier<Item> CHORUS_FOSSIL = Services.COMMON.registerItem("chorus_fossil", Item::new);
    public static final ResourceSupplier<ChorusScytheItem> CHORUS_SCYTHE = null;
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_HELMET = null;
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_CHESTPLATE = null;
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_LEGGINGS = null;
    public static final ResourceSupplier<ChorusoatlArmorItem> CHORUSOATL_BOOTS = null;
    public static final ResourceSupplier<Item> OBSIDIAN_HORN = null;
    public static final ResourceSupplier<RaptorTalonItem> RAPTOR_TALON = null;
    public static final ResourceSupplier<Item> SCULK_TOXIN = Services.COMMON.registerItem("sculk_toxin", Item::new);
    public static final ResourceSupplier<Item> MOSSY_CLAW = null;
    public static final ResourceSupplier<PusArrowItem> OOZE_TIPPED_ARROW = Services.COMMON.registerItem("ooze_tipped_arrow", PusArrowItem::new);
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_HELMET = null;
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_CHESTPLATE = null;
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_LEGGINGS = null;
    public static final ResourceSupplier<ObsidiceratopsArmorItem> OBSIDICERATOPS_BOOTS = null;
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_HELMET = null;
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_CHESTPLATE = null;
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_LEGGINGS = null;
    public static final ResourceSupplier<SculksaurArmorItem> SCULKSAUR_BOOTS = null;
    public static final ResourceSupplier<Item> CORAL_RIB = null;
    public static final ResourceSupplier<Item> CORAL_STEW = null;
    public static final ResourceSupplier<RibBowItem> RIB_BOW = null;
    public static final ResourceSupplier<SculkBowItem> SCULK_BOW = null;
    public static final ResourceSupplier<ScorchingHornItem> SCORCHING_HORN = null;
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_HELMET = null;
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_CHESTPLATE = null;
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_LEGGINGS = null;
    public static final ResourceSupplier<CoralosaurArmorItem> CORALOSAUR_BOOTS = null;
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_HELMET = null;
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_CHESTPLATE = null;
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_LEGGINGS = null;
    public static final ResourceSupplier<TherimossArmorItem> THERIMOSS_BOOTS = null;
    public static final ResourceSupplier<GuideBookItem> GUIDEBOOK = null;
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_COPPER = null;
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_IRON = null;
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_GOLD = null;
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_DIAMOND = null;
    public static final ResourceSupplier<TherimossClawsItem> THERIMOSS_CLAWS_NETHERITE = null;
    public static final ResourceSupplier<BlockItem> LUSHSAUR_EGG = ADItems.registerBlock(ADBlocks.LUSHSAUR_EGG);
    public static final ResourceSupplier<BlockItem> CHORUSOATL_EGG = ADItems.registerBlock(ADBlocks.CHORUSOATL_EGG);
    public static final ResourceSupplier<BlockItem> SCULKRAPTOR_EGG = null;
    public static final ResourceSupplier<ObsidiceratopsEggItem> OBSIDICERATOPS_EGG = null;
    public static final ResourceSupplier<BlockItem> THERIMOSS_EGG = null;
    public static final ResourceSupplier<SpawnEggItem> LUSHSAUR_SPAWN_EGG = Services.COMMON.registerItem("lushsaur_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.LUSHSAUR, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> CHORUSOATL_SPAWN_EGG = Services.COMMON.registerItem("chorusoatl_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.CHORUSOATL, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> SCULKRAPTOR_SPAWN_EGG = null;
    public static final ResourceSupplier<SpawnEggItem> SCULKSAUR_SPAWN_EGG = Services.COMMON.registerItem("sculksaur_spawn_egg", properties -> Services.COMMON.makeSpawnEgg(ADEntityTypes.SCULKSAUR, (Item.Properties)properties));
    public static final ResourceSupplier<SpawnEggItem> OBSIDICERATOPS_SPAWN_EGG = null;
    public static final ResourceSupplier<SpawnEggItem> THERIMOSS_SPAWN_EGG = null;
    public static final ResourceSupplier<SpawnEggItem> CORALOSAUR_SPAWN_EGG = null;
    public static final ResourceSupplier<TotemItem> LUSH_TOTEM = null;
    public static final ResourceSupplier<TotemItem> BURNING_TOTEM = null;
    public static final ResourceSupplier<TotemItem> TROPIC_TOTEM = null;
    public static final ResourceSupplier<TotemItem> SOARING_TOTEM = null;
    public static final ResourceSupplier<TotemItem> TIDAL_TOTEM = null;
    public static final ResourceSupplier<TotemItem> ECHO_TOTEM = null;
    public static final ResourceSupplier<TotemItem> FEARFUL_TOTEM = null;
    public static final ResourceSupplier<Item> AMBER = Services.COMMON.registerItem("amber", Item::new);
    public static final ResourceSupplier<BlockItem> AMBER_ORE = null;
    public static final ResourceSupplier<BlockItem> RAW_AMBER_BLOCK = null;
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER = null;
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER_STAIRS = null;
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER_SLAB = null;
    public static final ResourceSupplier<BlockItem> POLISHED_AMBER_WALL = null;
    public static final ResourceSupplier<BlockItem> CARVED_AMBER = null;
    public static final ResourceSupplier<BlockItem> AMBER_BRICKS = null;
    public static final ResourceSupplier<BlockItem> AMBER_BRICK_STAIRS = null;
    public static final ResourceSupplier<BlockItem> AMBER_BRICK_SLAB = null;
    public static final ResourceSupplier<BlockItem> AMBER_BRICK_WALL = null;
    public static final ResourceSupplier<BlockItem> CRACKED_AMBER_BRICKS = null;
    public static final ResourceSupplier<BlockItem> AMBER_CHAIN = null;
    public static final ResourceSupplier<BlockItem> AMBER_LAMP = null;

    public static void init() {
    }

    private static <T extends Block> ResourceSupplier<BlockItem> registerBlock(ResourceSupplier<T> block) {
        return Services.COMMON.registerItem(block.id().m_135815_(), properties -> new BlockItem((Block)block.get(), properties));
    }

    private static <T extends TotemBlock> ResourceSupplier<TotemItem> registerTotem(ResourceSupplier<T> block) {
        return Services.COMMON.registerItem(block.id().m_135815_(), properties -> new TotemItem((TotemBlock)((Object)((Object)block.get())), (Item.Properties)properties));
    }
}

