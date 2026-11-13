package com.eternal_creations.archies_dino_mod.client;

import com.eternal_creations.archies_dino_mod.client.particle.DustPillarParticle;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.platform.ForgeClientHelper;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT}, modid = "ec_ad")
public class ADClientModBus {
    public static void init() { CommonClient.init(); }

    @SubscribeEvent
    public static void onLoadRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ForgeClientHelper.ENTITY_RENDERERS.forEach((type, renderer) -> event.registerEntityRenderer((EntityType) type.get(), renderer));
        ForgeClientHelper.BLOCK_ENTITY_RENDERERS.forEach((type, renderer) -> event.registerBlockEntityRenderer((BlockEntityType) type.get(), renderer));
    }

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ADParticles.DUST_PILLAR.get(), (ParticleProvider) new DustPillarParticle.DustPillarProvider());
    }

    @SubscribeEvent
    public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        ADKeyMappings.KEY_MAPPINGS.keySet().forEach(event::register);
    }
}
