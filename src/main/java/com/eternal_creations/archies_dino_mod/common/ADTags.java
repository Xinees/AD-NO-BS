/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.damagesource.DamageType
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.dimension.DimensionType
 *  net.minecraft.world.level.gameevent.GameEvent
 */
package com.eternal_creations.archies_dino_mod.common;

import com.eternal_creations.archies_dino_mod.Constants;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADTags {

    public static class DamageTypes {
        public static final TagKey<DamageType> HURTS_COMBAT_SCULKSAUR_EXTRA = TagKey.m_203882_((ResourceKey)Registries.f_268580_, (ResourceLocation)Constants.prefix("hurts_combat_sculksaur_extra"));
    }

    public static class GameEvents {
        public static final TagKey<GameEvent> SCULKSAUR_CAN_LISTEN = TagKey.m_203882_((ResourceKey)Registries.f_256827_, (ResourceLocation)Constants.prefix("sculksaur_can_listen"));
        public static final TagKey<GameEvent> SCULKRAPTOR_WILD_CAN_LISTEN = TagKey.m_203882_((ResourceKey)Registries.f_256827_, (ResourceLocation)Constants.prefix("sculkraptor_wild_can_listen"));
        public static final TagKey<GameEvent> SCULKRAPTOR_TAMED_CAN_LISTEN = TagKey.m_203882_((ResourceKey)Registries.f_256827_, (ResourceLocation)Constants.prefix("sculkraptor_tamed_can_listen"));
        public static final TagKey<GameEvent> SCULKRAPTOR_IGNORE_COOLDOWN = TagKey.m_203882_((ResourceKey)Registries.f_256827_, (ResourceLocation)Constants.prefix("sculkraptor_ignore_cooldwon"));
    }

    public static class DimensionTypes {
        public static final TagKey<DimensionType> CHORUSOATL_EGG_PREFERRED = TagKey.m_203882_((ResourceKey)Registries.f_256787_, (ResourceLocation)Constants.prefix("chorusoatl_egg_preferred"));
    }

    public static class Biomes {
        public static final TagKey<Biome> SPAWNS_LUSHSAURS = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("spawns_lushsaurs"));
        public static final TagKey<Biome> SPAWNS_CHORUSOATLS = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("spawns_chorusoatls"));
        public static final TagKey<Biome> SPAWNS_SCULKRAPTORS = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("spawns_sculkraptors"));
        public static final TagKey<Biome> SPAWNS_OBSIDICERATOPSES = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("spawns_obsidiceratopses"));
        public static final TagKey<Biome> SPAWNS_CORALOSAURS = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("spawns_coralosaurs"));
        public static final TagKey<Biome> SPAWNS_THERIMOSS = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("spawns_therimoss"));
        public static final TagKey<Biome> SPAWNS_ARCHIE = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("spawns_archie"));
        public static final TagKey<Biome> CRATER_VALID = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("crater_valid"));
        public static final TagKey<Biome> AMBER_FOSSIL_VALID = TagKey.m_203882_((ResourceKey)Registries.f_256952_, (ResourceLocation)Constants.prefix("amber_fossil_valid"));
    }

    public static class Items {
        public static final TagKey<Item> LUSH_TOTEM_FUEL = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("lush_totem_fuel"));
        public static final TagKey<Item> BURNING_TOTEM_FUEL = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("burning_totem_fuel"));
        public static final TagKey<Item> TROPIC_TOTEM_FUEL = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("tropic_totem_fuel"));
        public static final TagKey<Item> SOARING_TOTEM_FUEL = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("soaring_totem_fuel"));
        public static final TagKey<Item> TIDAL_TOTEM_FUEL = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("tidal_totem_fuel"));
        public static final TagKey<Item> ECHO_TOTEM_FUEL = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("echo_totem_fuel"));
        public static final TagKey<Item> FEARFUL_TOTEM_FUEL = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("fearful_totem_fuel"));
        public static final TagKey<Item> CLAW = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("claw"));
        public static final TagKey<Item> TWO_HANDED = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("two_handed"));
        public static final TagKey<Item> LUSHSAUR_EDIBLE_ITEM = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("lushsaur_edible"));
        public static final TagKey<Item> CHORUSOATL_EDIBLE_ITEM = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("chorusoatl_edible"));
        public static final TagKey<Item> OBSIDICERATOPS_EDIBLE_ITEM = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("obsidiceratops_edible"));
        public static final TagKey<Item> THERIMOSS_EDIBLE_ITEM = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("therimoss_edible"));
        public static final TagKey<Item> CORALOSAUR_EDIBLE_ITEM = TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)Constants.prefix("coralosaur_edible"));
    }

    public static class Blocks {
        public static final TagKey<Block> LUSHSAUR_BREAK_IMMUNE_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("lushsaur_break_immune_blocks"));
        public static final TagKey<Block> LUSHSAUR_BREAK_VEGETATION_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("lushsaur_break_vegetation_blocks"));
        public static final TagKey<Block> LUSHSAUR_EDIBLE_BLOCK = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("lushsaur_edible"));
        public static final TagKey<Block> LUSHSAUR_EGG_HATCHABLE_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("lushsaur_egg_hatchable"));
        public static final TagKey<Block> LUSHSAUR_VALID_SPAWN = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("lushsaur_valid_spawn"));
        public static final TagKey<Block> CHORUSOATL_EGG_HATCHABLE_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("chorusoatl_egg_hatchable"));
        public static final TagKey<Block> CHORUSOATL_EGG_CHORUS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("chorusoatl_egg_chorus"));
        public static final TagKey<Block> CHORUSOATL_VALID_SPAWN = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("chorusoatl_valid_spawn"));
        public static final TagKey<Block> OBSIDICERATOPS_EGG_HATCHABLE_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("obsidiceratops_egg_hatchable"));
        public static final TagKey<Block> OBSIDICERATOPS_VALID_SPAWN = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("obsidiceratops_valid_spawn"));
        public static final TagKey<Block> OBSIDICERATOPS_WILD_BREAK = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("wild_obsidiceratops_breaks"));
        public static final TagKey<Block> THERIMOSS_EGG_HATCHABLE_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("therimoss_egg_hatchable"));
        public static final TagKey<Block> THERIMOSS_EDIBLE_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("therimoss_edible_blocks"));
        public static final TagKey<Block> THERIMOSS_VALID_SPAWN = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("therimoss_valid_spawn"));
        public static final TagKey<Block> THERIMOSS_PROTECTED_BLOCKS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("therimoss_protected"));
        public static final TagKey<Block> SCULKRAPTOR_VALID_SPAWN = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("sculkraptor_valid_spawn"));
        public static final TagKey<Block> TOTEMS = TagKey.m_203882_((ResourceKey)Registries.f_256747_, (ResourceLocation)Constants.prefix("totems"));
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> DINOSAUR = TagKey.m_203882_((ResourceKey)Registries.f_256939_, (ResourceLocation)Constants.prefix("dinosaur"));
        public static final TagKey<EntityType<?>> DINO_RESISTS_DAMAGE_FROM = TagKey.m_203882_((ResourceKey)Registries.f_256939_, (ResourceLocation)Constants.prefix("dino_resists_damage_from"));
        public static final TagKey<EntityType<?>> SCULK = TagKey.m_203882_((ResourceKey)Registries.f_256939_, (ResourceLocation)Constants.prefix("sculk"));
        public static final TagKey<EntityType<?>> CORALOSAUR_TURN = TagKey.m_203882_((ResourceKey)Registries.f_256939_, (ResourceLocation)Constants.prefix("coralosaur_turn"));
    }
}

