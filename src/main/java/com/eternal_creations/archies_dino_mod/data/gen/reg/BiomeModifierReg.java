/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderGetter
 *  net.minecraft.core.HolderSet
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.worldgen.BootstapContext
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.biome.MobSpawnSettings$SpawnerData
 *  net.minecraftforge.common.world.BiomeModifier
 *  net.minecraftforge.common.world.ForgeBiomeModifiers$AddSpawnsBiomeModifier
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 */
package com.eternal_creations.archies_dino_mod.data.gen.reg;

import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BiomeModifierReg {
    public static void bootstrap(BootstapContext<BiomeModifier> ctx) {
        HolderGetter biomeLookup = ctx.m_255420_(Registries.f_256952_);
        ADEntityTypes.SPAWN_INFO.forEach(spawnInfo -> ctx.m_255272_(BiomeModifierReg.key(spawnInfo.supplier().id()), (Object)ForgeBiomeModifiers.AddSpawnsBiomeModifier.singleSpawn((HolderSet)((HolderSet)biomeLookup.m_254901_(spawnInfo.biomes()).orElseThrow()), (MobSpawnSettings.SpawnerData)new MobSpawnSettings.SpawnerData(spawnInfo.supplier().get(), spawnInfo.weight(), spawnInfo.minCount(), spawnInfo.maxCount()))));
    }

    private static ResourceKey<BiomeModifier> key(ResourceLocation location) {
        return ResourceKey.m_135785_((ResourceKey)ForgeRegistries.Keys.BIOME_MODIFIERS, (ResourceLocation)location.m_266382_("_spawns"));
    }
}

