/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.sensing.Sensor
 *  net.minecraft.world.entity.ai.sensing.SensorType
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.CreativeModeTab$DisplayItemsGenerator
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.SpawnEggItem
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.levelgen.structure.StructureType
 *  net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.platform.services;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.block.entity.BlockEntityTypeSupplier;
import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import com.eternal_creations.archies_dino_mod.common.recipe.ADRecipeTypes;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import com.mojang.serialization.Codec;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import org.jetbrains.annotations.Nullable;

public interface ICommonHelper {
    public ResourceSupplier<SoundEvent> registerSoundEvent(String var1, Function<ResourceLocation, SoundEvent> var2);

    public <T extends ParticleType<?>> ResourceSupplier<T> registerParticleType(String var1, Supplier<T> var2);

    public <T extends BlockEntity, G extends Block> ResourceSupplier<BlockEntityType<T>> registerBlockEntityType(String var1, BlockEntityTypeSupplier<T> var2, Supplier<G> ... var3);

    public <T extends Entity> ResourceSupplier<EntityType<T>> registerEntityType(String var1, EntityType.Builder<T> var2);

    public <T extends LivingEntity> void registerEntityAttributes(Supplier<EntityType<T>> var1, Supplier<AttributeSupplier> var2);

    public <T extends Item> ResourceSupplier<T> registerItem(String var1, Function<Item.Properties, T> var2);

    public <T extends Mob> SpawnEggItem makeSpawnEgg(ResourceSupplier<EntityType<T>> var1, Item.Properties var2);

    public <T extends Block> ResourceSupplier<T> registerBlock(String var1, Supplier<T> var2);

    public <T extends AbstractContainerMenu> ResourceSupplier<MenuType<T>> registerMenu(String var1, BiFunction<Integer, Inventory, T> var2);

    public ResourceSupplier<CreativeModeTab> registerCreativeTab(String var1, Supplier<ItemStack> var2, CreativeModeTab.DisplayItemsGenerator var3);

    public ResourceSupplier<GameEvent> registerGameEvent(String var1, int var2);

    public <T extends MobEffect> ResourceSupplier<T> registerMobEffect(String var1, Supplier<T> var2);

    public <T extends Sensor<?>> ResourceSupplier<SensorType<T>> registerSensorType(String var1, Supplier<T> var2);

    public ResourceSupplier<Activity> registerActivity(String var1);

    public <T> ResourceSupplier<MemoryModuleType<T>> registerMemoryModuleType(String var1, @Nullable Codec<T> var2);

    public ResourceSupplier<StructurePieceType> registerStructurePieceType(String var1, StructurePieceType var2);

    public <T extends Recipe<?>> ResourceSupplier<RecipeType<T>> registerRecipeType(String var1, Supplier<RecipeType<T>> var2);

    default public <T extends Recipe<?>> ResourceSupplier<RecipeType<T>> registerRecipeType(String id) {
        return this.registerRecipeType(id, () -> ADRecipeTypes.simple(Constants.prefix(id)));
    }

    public <T extends Recipe<?>> ResourceSupplier<RecipeSerializer<T>> registerRecipeSerializer(String var1, Supplier<RecipeSerializer<T>> var2);

    public <T extends StructureProcessorType<?>> ResourceSupplier<T> registerStructureProcessorType(String var1, Supplier<T> var2);

    public <T extends StructureType<?>> ResourceSupplier<T> registerStructureType(String var1, Supplier<T> var2);

    public <T extends Entity> IPartEntity<T> makePartEntity(T var1, float var2, float var3);
}

