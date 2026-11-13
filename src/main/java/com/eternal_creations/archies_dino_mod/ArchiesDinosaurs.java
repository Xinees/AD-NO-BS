/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.annotations.MethodsReturnNonnullByDefault
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.SpawnPlacements$Type
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier
 *  net.minecraft.world.level.gameevent.vibrations.VibrationSystem
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.event.entity.EntityAttributeCreationEvent
 *  net.minecraftforge.event.entity.SpawnPlacementRegisterEvent
 *  net.minecraftforge.event.entity.SpawnPlacementRegisterEvent$Operation
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.DistExecutor
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 */
package com.eternal_creations.archies_dino_mod;

import com.eternal_creations.archies_dino_mod.CommonClass;
import com.eternal_creations.archies_dino_mod.client.ADClientModBus;
import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.platform.ForgeCommonHelper;
import com.eternal_creations.archies_dino_mod.platform.ForgeNetworkHelper;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value="ec_ad")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesDinosaurs {
    public ArchiesDinosaurs(FMLJavaModLoadingContext context) {
        CommonClass.init();
        DistExecutor.unsafeRunWhenOn((Dist)Dist.CLIENT, () -> ADClientModBus::init);
        ForgeNetworkHelper.init();
        this.registerCommon(context.getModEventBus());
    }

    private void registerCommon(IEventBus bus) {
        bus.addListener(this::registerAttributes);
        bus.addListener(this::init);
        ForgeCommonHelper.BLOCKS.register(bus);
        ForgeCommonHelper.POI_TYPES.register(bus);
        ForgeCommonHelper.BLOCK_ENTITY_TYPES.register(bus);
        ForgeCommonHelper.ENTITY_TYPES.register(bus);
        ForgeCommonHelper.ITEMS.register(bus);
        ForgeCommonHelper.SOUND_EVENTS.register(bus);
        ForgeCommonHelper.PARTICLE_TYPES.register(bus);
        ForgeCommonHelper.TABS.register(bus);
        ForgeCommonHelper.GAME_EVENTS.register(bus);
        ForgeCommonHelper.MOB_EFFECTS.register(bus);
        ForgeCommonHelper.SENSOR_TYPES.register(bus);
        ForgeCommonHelper.ACTIVITIES.register(bus);
        ForgeCommonHelper.MEMORY_MODULE_TYPES.register(bus);
        ForgeCommonHelper.STRUCTURE_PIECE_TYPES.register(bus);
        ForgeCommonHelper.STRUCTURE_PROCESSOR_TYPES.register(bus);
        ForgeCommonHelper.STRUCTURE_TYPES.register(bus);
        ForgeCommonHelper.MENU_TYPES.register(bus);
        ForgeCommonHelper.RECIPE_TYPES.register(bus);
        ForgeCommonHelper.RECIPE_SERIALIZERS.register(bus);
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        ForgeCommonHelper.ATTRIBUTES.forEach((entityType, attributes) -> event.put((EntityType)entityType.get(), (AttributeSupplier)attributes.get()));
    }

    public void init(FMLCommonSetupEvent evt) {
        evt.enqueueWork(() -> {
            ((Object2IntOpenHashMap)VibrationSystem.f_279561_).put((Object)ADGameEvents.PUS_ARROW_AGGRO.get(), 64);
            ((Object2IntOpenHashMap)VibrationSystem.f_279561_).put((Object)ADGameEvents.SCULKSAUR_ARMOR_DISTRACTION.get(), 32);
            ((Object2IntOpenHashMap)VibrationSystem.f_279561_).put((Object)ADGameEvents.TRACKER_AGGRO.get(), 64);
            ((Object2IntOpenHashMap)VibrationSystem.f_279561_).put((Object)ADGameEvents.SCULKRAPTOR_NEST_AGGRO.get(), 128);
        });
    }

    @Mod.EventBusSubscriber(modid="ec_ad", bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            ModBusEvents.ground(event, ADEntityTypes.LUSHSAUR);
            ModBusEvents.ground(event, ADEntityTypes.CHORUSOATL);
            ModBusEvents.ground(event, ADEntityTypes.SCULKRAPTOR);
            ModBusEvents.ground(event, ADEntityTypes.SCULKSAUR);
            ModBusEvents.ground(event, ADEntityTypes.OBSIDICERATOPS);
            ModBusEvents.ground(event, ADEntityTypes.THERIMOSS);
            event.register(ADEntityTypes.CORALOSAUR.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }

        private static <T extends Mob> void ground(SpawnPlacementRegisterEvent event, Supplier<EntityType<T>> type) {
            event.register(type.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_, SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
    }
}

