/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL$TypeReference
 *  com.mojang.datafixers.types.Type
 *  com.mojang.serialization.Codec
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.Util
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.datafix.fixes.References
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
 *  net.minecraft.world.entity.ai.village.poi.PoiType
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.schedule.Activity
 *  net.minecraft.world.flag.FeatureFlags
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
 *  net.minecraft.world.level.block.entity.BlockEntityType$Builder
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.levelgen.structure.StructureType
 *  net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType
 *  net.minecraftforge.common.ForgeSpawnEggItem
 *  net.minecraftforge.registries.DeferredRegister
 *  net.minecraftforge.registries.ForgeRegistries
 *  net.minecraftforge.registries.IForgeRegistry
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.platform;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.block.entity.BlockEntityTypeSupplier;
import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import com.eternal_creations.archies_dino_mod.entity.ForgeEntityPart;
import com.eternal_creations.archies_dino_mod.platform.services.ICommonHelper;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Codec;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.flag.FeatureFlags;
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
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ForgeCommonHelper
implements ICommonHelper {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.BLOCK_ENTITY_TYPES, (String)"ec_ad");
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ENTITY_TYPES, (String)"ec_ad");
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.SOUND_EVENTS, (String)"ec_ad");
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.PARTICLE_TYPES, (String)"ec_ad");
    public static final DeferredRegister<GameEvent> GAME_EVENTS = DeferredRegister.create((ResourceKey)Registries.f_256827_, (String)"ec_ad");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.ITEMS, (String)"ec_ad");
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.BLOCKS, (String)"ec_ad");
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create((ResourceKey)Registries.f_279569_, (String)"ec_ad");
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create((ResourceKey)Registries.f_256929_, (String)"ec_ad");
    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create((ResourceKey)Registries.f_256937_, (String)"ec_ad");
    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create((ResourceKey)Registries.f_257025_, (String)"ec_ad");
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create((ResourceKey)Registries.f_257023_, (String)"ec_ad");
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create((ResourceKey)Registries.f_256786_, (String)"ec_ad");
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create((ResourceKey)BuiltInRegistries.f_256897_.m_123023_(), (String)"ec_ad");
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create((ResourceKey)Registries.f_256938_, (String)"ec_ad");
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create((ResourceKey)Registries.f_256798_, (String)"ec_ad");
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create((IForgeRegistry)ForgeRegistries.RECIPE_SERIALIZERS, (String)"ec_ad");
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.RECIPE_TYPES, (String)"ec_ad");
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create((IForgeRegistry)ForgeRegistries.POI_TYPES, (String)"ec_ad");
    public static final Map<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier>> ATTRIBUTES = new HashMap<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier>>();

    @Override
    public ResourceSupplier<SoundEvent> registerSoundEvent(String id, Function<ResourceLocation, SoundEvent> soundEvent) {
        ResourceLocation location = Constants.prefix(id);
        return new ResourceSupplier<SoundEvent>((Supplier<SoundEvent>)SOUND_EVENTS.register(id, () -> (SoundEvent)soundEvent.apply(location)), location);
    }

    @Override
    public <T extends ParticleType<?>> ResourceSupplier<T> registerParticleType(String id, Supplier<T> particle) {
        return new ResourceSupplier(PARTICLE_TYPES.register(id, particle), Constants.prefix(id));
    }

    @Override
    @SafeVarargs
    public final <T extends BlockEntity, G extends Block> ResourceSupplier<BlockEntityType<T>> registerBlockEntityType(String id, BlockEntityTypeSupplier<T> supplier, Supplier<G> ... blockSuppliers) {
        ResourceLocation location = Constants.prefix(id);
        return new ResourceSupplier<BlockEntityType<T>>(BLOCK_ENTITY_TYPES.register(id, () -> {
            Block[] blocks = new Block[blockSuppliers.length];
            for (int i = 0; i < blockSuppliers.length; ++i) {
                blocks[i] = (Block)blockSuppliers[i].get();
            }
            Type type = Util.m_137456_((DSL.TypeReference)References.f_16781_, (String)id);
            return BlockEntityType.Builder.m_155273_(supplier::create, (Block[])blocks).m_58966_(type);
        }), location);
    }

    @Override
    public <T extends Entity> ResourceSupplier<EntityType<T>> registerEntityType(String id, EntityType.Builder<T> builder) {
        return new ResourceSupplier<EntityType<T>>(ENTITY_TYPES.register(id, () -> builder.m_20712_(id)), Constants.prefix(id));
    }

    @Override
    public <T extends LivingEntity> void registerEntityAttributes(Supplier<EntityType<T>> entityType, Supplier<AttributeSupplier> attributes) {
        ATTRIBUTES.put(entityType, attributes);
    }

    @Override
    public <T extends Item> ResourceSupplier<T> registerItem(String id, Function<Item.Properties, T> item) {
        return new ResourceSupplier(ITEMS.register(id, () -> (Item)item.apply(new Item.Properties())), Constants.prefix(id));
    }

    @Override
    public <T extends Mob> SpawnEggItem makeSpawnEgg(ResourceSupplier<EntityType<T>> type, Item.Properties properties) {
        return new ForgeSpawnEggItem(type, -1, -1, properties);
    }

    @Override
    public <T extends Block> ResourceSupplier<T> registerBlock(String id, Supplier<T> block) {
        return new ResourceSupplier(BLOCKS.register(id, block), Constants.prefix(id));
    }

    @Override
    public <T extends AbstractContainerMenu> ResourceSupplier<MenuType<T>> registerMenu(String id, BiFunction<Integer, Inventory, T> factory) {
        return new ResourceSupplier<MenuType<T>>(MENU_TYPES.register(id, () -> new MenuType(factory::apply, FeatureFlags.f_244332_)), Constants.prefix(id));
    }

    @Override
    public ResourceSupplier<CreativeModeTab> registerCreativeTab(String id, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator displayItems) {
        return new ResourceSupplier<CreativeModeTab>((Supplier<CreativeModeTab>)TABS.register(id, () -> CreativeModeTab.builder().m_257941_((Component)Component.m_237115_((String)("item_group.ec_ad." + id))).m_257737_(icon).m_257501_(displayItems).m_257652_()), Constants.prefix(id));
    }

    @Override
    public ResourceSupplier<GameEvent> registerGameEvent(String id, int notificationRadius) {
        ResourceLocation location = Constants.prefix(id);
        return new ResourceSupplier<GameEvent>((Supplier<GameEvent>)GAME_EVENTS.register(id, () -> new GameEvent(id, notificationRadius)), location);
    }

    @Override
    public <T extends MobEffect> ResourceSupplier<T> registerMobEffect(String id, Supplier<T> effectSupplier) {
        return new ResourceSupplier(MOB_EFFECTS.register(id, effectSupplier), Constants.prefix(id));
    }

    @Override
    public <T extends Sensor<?>> ResourceSupplier<SensorType<T>> registerSensorType(String id, Supplier<T> sensor) {
        return new ResourceSupplier<SensorType<T>>(SENSOR_TYPES.register(id, () -> new SensorType(sensor)), Constants.prefix(id));
    }

    @Override
    public ResourceSupplier<Activity> registerActivity(String id) {
        return new ResourceSupplier<Activity>((Supplier<Activity>)ACTIVITIES.register(id, () -> new Activity(id)), Constants.prefix(id));
    }

    @Override
    public <T> ResourceSupplier<MemoryModuleType<T>> registerMemoryModuleType(String id, @Nullable Codec<T> codec) {
        return new ResourceSupplier<MemoryModuleType<T>>(MEMORY_MODULE_TYPES.register(id, () -> new MemoryModuleType(Optional.ofNullable(codec))), Constants.prefix(id));
    }

    @Override
    public ResourceSupplier<StructurePieceType> registerStructurePieceType(String id, StructurePieceType typeSupplier) {
        return new ResourceSupplier<StructurePieceType>((Supplier<StructurePieceType>)STRUCTURE_PIECE_TYPES.register(id, () -> typeSupplier), Constants.prefix(id));
    }

    @Override
    public <T extends Recipe<?>> ResourceSupplier<RecipeType<T>> registerRecipeType(String id, Supplier<RecipeType<T>> recipe) {
        return new ResourceSupplier<RecipeType<T>>(RECIPE_TYPES.register(id, recipe), Constants.prefix(id));
    }

    @Override
    public <T extends Recipe<?>> ResourceSupplier<RecipeSerializer<T>> registerRecipeSerializer(String id, Supplier<RecipeSerializer<T>> serializer) {
        return new ResourceSupplier<RecipeSerializer<T>>(RECIPE_SERIALIZERS.register(id, serializer), Constants.prefix(id));
    }

    @Override
    public <T extends StructureProcessorType<?>> ResourceSupplier<T> registerStructureProcessorType(String id, Supplier<T> processorType) {
        return new ResourceSupplier(STRUCTURE_PROCESSOR_TYPES.register(id, processorType), Constants.prefix(id));
    }

    @Override
    public <T extends StructureType<?>> ResourceSupplier<T> registerStructureType(String id, Supplier<T> structureType) {
        return new ResourceSupplier(STRUCTURE_TYPES.register(id, structureType), Constants.prefix(id));
    }

    @Override
    public <T extends Entity> IPartEntity<T> makePartEntity(T parent, float width, float height) {
        return new ForgeEntityPart<T>(parent, width, height);
    }
}

