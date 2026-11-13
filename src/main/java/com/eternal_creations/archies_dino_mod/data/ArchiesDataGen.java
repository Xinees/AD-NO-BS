/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.DataGenerator
 *  net.minecraft.data.DataProvider
 *  net.minecraft.data.PackOutput
 *  net.minecraftforge.common.data.ExistingFileHelper
 *  net.minecraftforge.common.data.ForgeAdvancementProvider
 *  net.minecraftforge.data.event.GatherDataEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.eternal_creations.archies_dino_mod.data;

import com.eternal_creations.archies_dino_mod.data.ArchiesRegistryGen;
import com.eternal_creations.archies_dino_mod.data.gen.ArchieTradeGen;
import com.eternal_creations.archies_dino_mod.data.gen.ArchiesAdvancementGen;
import com.eternal_creations.archies_dino_mod.data.gen.ArchiesBlockStateGen;
import com.eternal_creations.archies_dino_mod.data.gen.ArchiesItemModelGen;
import com.eternal_creations.archies_dino_mod.data.gen.ArchiesLangGen;
import com.eternal_creations.archies_dino_mod.data.gen.ArchiesLootGen;
import com.eternal_creations.archies_dino_mod.data.gen.ArchiesRecipeProvider;
import com.eternal_creations.archies_dino_mod.data.gen.ArchiesSoundGen;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesBiomeTagGen;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesBlockTagGen;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesDamageTypeTagGen;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesDimensionTypeTagGen;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesEntityTagGen;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesGameEventTagGen;
import com.eternal_creations.archies_dino_mod.data.gen.tag.ArchiesItemTagGen;
import com.eternal_creations.archies_dino_mod.data.util.MultiPlatformProvider;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid="ec_ad", bus=Mod.EventBusSubscriber.Bus.MOD)
public class ArchiesDataGen {
    private static final String FABRIC_PROPERTY = "archies.fabric.datagen.path";
    private static final String FORGE_PROPERTY = "archies.forge.datagen.path";

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = event.getGenerator().getPackOutput();
        PackOutput fabricOutput = new PackOutput(Path.of(System.getProperty(FABRIC_PROPERTY), new String[0]));
        PackOutput forgeOutput = new PackOutput(Path.of(System.getProperty(FORGE_PROPERTY), new String[0]));
        CompletableFuture lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();
        ArchiesRegistryGen datapackProvider = new ArchiesRegistryGen(lookupProvider, forgeOutput, fabricOutput);
        CompletableFuture registryProvider = datapackProvider.getRegistryProvider();
        generator.addProvider(event.includeServer(), (DataProvider)datapackProvider);
        generator.addProvider(event.includeServer(), (DataProvider)new ArchiesLootGen(output));
        generator.addProvider(event.includeServer(), (DataProvider)new ForgeAdvancementProvider(output, registryProvider, helper, List.of(new ArchiesAdvancementGen())));
        MultiPlatformProvider blockTagGen = (MultiPlatformProvider)generator.addProvider(event.includeServer(), MultiPlatformProvider.of(packOutput -> new ArchiesBlockTagGen(packOutput, registryProvider, helper), forgeOutput, fabricOutput));
        generator.addProvider(event.includeServer(), MultiPlatformProvider.of(packOutput -> new ArchiesItemTagGen(packOutput, registryProvider, blockTagGen, helper), forgeOutput, fabricOutput));
        generator.addProvider(event.includeServer(), MultiPlatformProvider.of(packOutput -> new ArchiesEntityTagGen(packOutput, registryProvider, helper), forgeOutput, fabricOutput));
        generator.addProvider(event.includeServer(), (DataProvider)new ArchiesBiomeTagGen(output, registryProvider, helper));
        generator.addProvider(event.includeServer(), (DataProvider)new ArchiesGameEventTagGen(output, registryProvider, helper));
        generator.addProvider(event.includeServer(), (DataProvider)new ArchiesDamageTypeTagGen(output, registryProvider, helper));
        generator.addProvider(event.includeServer(), (DataProvider)new ArchiesDimensionTypeTagGen(output, registryProvider, helper));
        generator.addProvider(event.includeClient(), (DataProvider)new ArchieTradeGen(output));
        generator.addProvider(event.includeClient(), (DataProvider)new ArchiesBlockStateGen(output, helper));
        generator.addProvider(event.includeClient(), (DataProvider)new ArchiesItemModelGen(output, helper));
        generator.addProvider(event.includeServer(), MultiPlatformProvider.of(ArchiesRecipeProvider::new, forgeOutput, fabricOutput));
        generator.addProvider(event.includeClient(), (DataProvider)new ArchiesSoundGen(output, helper));
        generator.addProvider(event.includeClient(), (DataProvider)new ArchiesLangGen(output));
    }
}

