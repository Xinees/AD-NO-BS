/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.gui.screens.MenuScreens
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.renderer.item.ItemProperties
 *  net.minecraft.client.renderer.item.ItemPropertyFunction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterLayerDefinitions
 *  net.minecraftforge.client.event.EntityRenderersEvent$RegisterRenderers
 *  net.minecraftforge.client.event.ModelEvent$ModifyBakingResult
 *  net.minecraftforge.client.event.RegisterKeyMappingsEvent
 *  net.minecraftforge.client.event.RegisterParticleProvidersEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 */
package com.eternal_creations.archies_dino_mod.client;

import com.eternal_creations.archies_dino_mod.client.ADKeyMappings;
import com.eternal_creations.archies_dino_mod.client.CommonClient;
import com.eternal_creations.archies_dino_mod.client.model.SculkraptorSpitModel;
import com.eternal_creations.archies_dino_mod.client.model.armor.ChorusoatlArmorModel;
import com.eternal_creations.archies_dino_mod.client.model.armor.CoralosaurArmorModel;
import com.eternal_creations.archies_dino_mod.client.model.armor.LushsaurArmorModel;
import com.eternal_creations.archies_dino_mod.client.model.armor.ObsidiceratopsArmorModel;
import com.eternal_creations.archies_dino_mod.client.model.armor.SculksaurArmorModel;
import com.eternal_creations.archies_dino_mod.client.model.armor.TherimossArmorModel;
import com.eternal_creations.archies_dino_mod.client.model.registry.ADModelLayers;
import com.eternal_creations.archies_dino_mod.client.particle.DustPillarParticle;
import com.eternal_creations.archies_dino_mod.client.screen.ArchieDialogScreen;
import com.eternal_creations.archies_dino_mod.client.screen.CoralosaurScreen;
import com.eternal_creations.archies_dino_mod.client.screen.DinoScreen;
import com.eternal_creations.archies_dino_mod.client.screen.TherimossScreen;
import com.eternal_creations.archies_dino_mod.common.items.ADItemProperties;
import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.platform.ForgeClientHelper;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD, value={Dist.CLIENT}, modid="ec_ad")
public class ADClientModBus {
    public static void init() {
        CommonClient.init();
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.m_96206_(ADMenus.ARCHIE.get(), ArchieDialogScreen::new);
        MenuScreens.m_96206_(ADMenus.DINO.get(), DinoScreen::new);
        MenuScreens.m_96206_(ADMenus.THERIMOSS.get(), TherimossScreen::new);
        MenuScreens.m_96206_(ADMenus.CORALOSAUR.get(), CoralosaurScreen::new);
    }

    @SubscribeEvent
    public static void onLoadRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ForgeClientHelper.ENTITY_RENDERERS.forEach((type, renderer) -> event.registerEntityRenderer((EntityType)type.get(), renderer));
        ForgeClientHelper.BLOCK_ENTITY_RENDERERS.forEach((type, renderer) -> event.registerBlockEntityRenderer((BlockEntityType)type.get(), renderer));
    }

    @SubscribeEvent
    @OnlyIn(value=Dist.CLIENT)
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpecial(ADParticles.DUST_PILLAR.get(), (ParticleProvider)new DustPillarParticle.DustPillarProvider());
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ADModelLayers.LUSHSAUR_ARMOR_OUTER, () -> LayerDefinition.m_171565_((MeshDefinition)LushsaurArmorModel.setupOuter(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.LUSHSAUR_ARMOR_INNER, () -> LayerDefinition.m_171565_((MeshDefinition)LushsaurArmorModel.setupInner(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.CHORUSOATL_ARMOR_OUTER, () -> LayerDefinition.m_171565_((MeshDefinition)ChorusoatlArmorModel.setupOuter(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.CHORUSOATL_ARMOR_INNER, () -> LayerDefinition.m_171565_((MeshDefinition)ChorusoatlArmorModel.setupInner(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.OBSIDICERATOPS_ARMOR_OUTER, () -> LayerDefinition.m_171565_((MeshDefinition)ObsidiceratopsArmorModel.setupOuter(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.OBSIDICERATOPS_ARMOR_INNER, () -> LayerDefinition.m_171565_((MeshDefinition)ObsidiceratopsArmorModel.setupInner(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.SCULKSAUR_ARMOR_OUTER, () -> LayerDefinition.m_171565_((MeshDefinition)SculksaurArmorModel.setupOuter(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.SCULKSAUR_ARMOR_INNER, () -> LayerDefinition.m_171565_((MeshDefinition)SculksaurArmorModel.setupInner(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.CORALOSAUR_ARMOR_OUTER, () -> LayerDefinition.m_171565_((MeshDefinition)CoralosaurArmorModel.setupOuter(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.CORALOSAUR_ARMOR_INNER, () -> LayerDefinition.m_171565_((MeshDefinition)CoralosaurArmorModel.setupInner(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.THERIMOSS_ARMOR_OUTER, () -> LayerDefinition.m_171565_((MeshDefinition)TherimossArmorModel.setupOuter(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.THERIMOSS_ARMOR_INNER, () -> LayerDefinition.m_171565_((MeshDefinition)TherimossArmorModel.setupInner(), (int)128, (int)128));
        event.registerLayerDefinition(ADModelLayers.SCULKRAPTOR_SPIT, SculkraptorSpitModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerKeyMappingsEvent(RegisterKeyMappingsEvent event) {
        ADKeyMappings.KEY_MAPPINGS.keySet().forEach(arg_0 -> ((RegisterKeyMappingsEvent)event).register(arg_0));
    }

    @SubscribeEvent
    public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
        ADItemProperties.OVERRIDES.forEach(override -> ItemProperties.register((Item)((Item)override.item().get()), (ResourceLocation)override.name(), (ItemPropertyFunction)override.property()));
    }
}

