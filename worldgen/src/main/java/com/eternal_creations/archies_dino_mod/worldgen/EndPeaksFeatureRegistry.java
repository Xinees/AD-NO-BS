package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "archie_dinosaurs", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EndPeaksFeatureRegistry {
    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        Feature<?> mountains = new EndPeaksMountainFeature();
        mountains.setRegistryName(new ResourceLocation("archie_dinosaurs", "end_peaks_mountains"));
        event.getRegistry().register(mountains);

        Feature<?> chorus = new EndPeaksChorusFeature();
        chorus.setRegistryName(new ResourceLocation("archie_dinosaurs", "end_peaks_chorus"));
        event.getRegistry().register(chorus);
    }

    private EndPeaksFeatureRegistry() {
    }
}
