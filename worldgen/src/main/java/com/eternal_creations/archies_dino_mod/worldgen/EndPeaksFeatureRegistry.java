package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = "archie_dinosaurs", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EndPeaksFeatureRegistry {
    @SubscribeEvent
    public static void registerFeatures(RegisterEvent event) {
        event.register(Registries.FEATURE, helper -> {
            helper.register(
                new ResourceLocation("archie_dinosaurs", "end_peaks_mountains"),
                new EndPeaksMountainFeature()
            );
            helper.register(
                new ResourceLocation("archie_dinosaurs", "end_peaks_chorus"),
                new EndPeaksChorusFeature()
            );
        });
    }

    private EndPeaksFeatureRegistry() {
    }
}
