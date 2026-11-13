package com.eternal_creations.archies_dino_mod;

import com.eternal_creations.archies_dino_mod.client.ADClientModBus;
import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.platform.ForgeCommonHelper;
import com.eternal_creations.archies_dino_mod.platform.ForgeNetworkHelper;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
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

@Mod("ec_ad")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesDinosaurs {
    public ArchiesDinosaurs(FMLJavaModLoadingContext context) {
        CommonClass.init();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ADClientModBus::init);
        ForgeNetworkHelper.init();
        registerCommon(context.getModEventBus());
    }

    private void registerCommon(IEventBus bus) {
        bus.addListener(this::registerAttributes);
        bus.addListener(this::init);
        ForgeCommonHelper.BLOCKS.register(bus);
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
    }

    private void registerAttributes(EntityAttributeCreationEvent event) {
        ForgeCommonHelper.ATTRIBUTES.forEach((entityType, attributes) ->
            event.put((EntityType) entityType.get(), (AttributeSupplier) attributes.get()));
    }

    private void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() ->
            ((Object2IntOpenHashMap) VibrationSystem.f_279561_).put(ADGameEvents.PUS_ARROW_AGGRO.get(), 64));
    }

    @Mod.EventBusSubscriber(modid = "ec_ad", bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
            ground(event, ADEntityTypes.LUSHSAUR);
            ground(event, ADEntityTypes.CHORUSOATL);
            ground(event, ADEntityTypes.SCULKSAUR);
        }

        private static <T extends Mob> void ground(SpawnPlacementRegisterEvent event, Supplier<EntityType<T>> type) {
            event.register(type.get(), SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_,
                SpawnPlacementRegisterEvent.Operation.REPLACE);
        }
    }
}
