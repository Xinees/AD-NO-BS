/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Encoder
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.core.Registry
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.RegistrySetBuilder
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.CachedOutput
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.PackOutput$PathProvider
 *  net.minecraft.data.PackOutput$Target
 *  net.minecraft.data.registries.RegistriesDatapackGenerator
 *  net.minecraft.resources.RegistryDataLoader$RegistryData
 *  net.minecraft.resources.RegistryOps
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraftforge.common.ForgeHooks
 *  net.minecraftforge.common.data.DatapackBuiltinEntriesProvider
 *  net.minecraftforge.registries.DataPackRegistriesHooks
 *  net.minecraftforge.registries.ForgeRegistries$Keys
 */
package com.eternal_creations.archies_dino_mod.data;

import com.eternal_creations.archies_dino_mod.common.ADDamageTypes;
import com.eternal_creations.archies_dino_mod.common.structure.ADStructureSets;
import com.eternal_creations.archies_dino_mod.common.structure.ADStructures;
import com.eternal_creations.archies_dino_mod.data.gen.reg.BiomeModifierReg;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.DataPackRegistriesHooks;
import net.minecraftforge.registries.ForgeRegistries;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesRegistryGen
extends DatapackBuiltinEntriesProvider {
    private static final Set<String> SET = Set.of("minecraft", "ec_ad");
    private final CompletableFuture<HolderLookup.Provider> forgeRegistries;
    private final PackOutput forgeOutput;

    public ArchiesRegistryGen(CompletableFuture<HolderLookup.Provider> provider, PackOutput forgeOutput, PackOutput fabricOutput) {
        super(fabricOutput, provider, ArchiesRegistryGen.addCommonRegistries(new RegistrySetBuilder()), SET);
        this.forgeOutput = forgeOutput;
        this.forgeRegistries = provider.thenApply(r -> ArchiesRegistryGen.constructRegistries(r, ArchiesRegistryGen.addForgeAndCommonRegistries(new RegistrySetBuilder())));
    }

    private static RegistrySetBuilder addCommonRegistries(RegistrySetBuilder builder) {
        return builder.m_254916_(Registries.f_268580_, ADDamageTypes::bootstrap).m_254916_(Registries.f_256944_, ADStructures::bootstrap).m_254916_(Registries.f_256998_, ADStructureSets::bootstrap);
    }

    private static RegistrySetBuilder addForgeAndCommonRegistries(RegistrySetBuilder builder) {
        return ArchiesRegistryGen.addCommonRegistries(builder).m_254916_(ForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifierReg::bootstrap);
    }

    private static HolderLookup.Provider constructRegistries(HolderLookup.Provider original, RegistrySetBuilder datapackEntriesBuilder) {
        HashSet builderKeys = new HashSet(datapackEntriesBuilder.getEntryKeys());
        DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().filter(data -> !builderKeys.contains(data.f_243794_())).forEach(data -> datapackEntriesBuilder.m_254916_(data.f_243794_(), context -> {}));
        return datapackEntriesBuilder.m_254929_((RegistryAccess)RegistryAccess.m_206165_((Registry)BuiltInRegistries.f_257047_), original);
    }

    public CompletableFuture<?> m_213708_(CachedOutput output) {
        CompletableFuture fabric = super.m_213708_(output);
        CompletionStage forge = this.forgeRegistries.thenCompose(provider -> {
            RegistryOps dynamicops = RegistryOps.m_255058_((DynamicOps)JsonOps.INSTANCE, (HolderLookup.Provider)provider);
            return CompletableFuture.allOf((CompletableFuture[])DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().flatMap(arg_0 -> this.lambda$run$4(output, provider, (DynamicOps)dynamicops, arg_0)).toArray(CompletableFuture[]::new));
        });
        return CompletableFuture.allOf(new CompletableFuture[]{fabric, forge});
    }

    private <T> Optional<CompletableFuture<?>> dumpForgeRegistryCap(CachedOutput output, HolderLookup.Provider registries, DynamicOps<JsonElement> ops, RegistryDataLoader.RegistryData<T> registryData) {
        ResourceKey key = registryData.f_243794_();
        return registries.m_254861_(key).map(lookup -> {
            PackOutput.PathProvider provider = this.forgeOutput.m_245269_(PackOutput.Target.DATA_PACK, ForgeHooks.prefixNamespace((ResourceLocation)key.m_135782_()));
            return CompletableFuture.allOf((CompletableFuture[])lookup.m_214062_().filter(holder -> SET.contains(holder.m_205785_().m_135782_().m_135827_())).map(holder -> RegistriesDatapackGenerator.m_255283_((Path)provider.m_245731_(holder.m_205785_().m_135782_()), (CachedOutput)output, (DynamicOps)ops, (Encoder)registryData.f_244580_(), (Object)holder.m_203334_())).toArray(CompletableFuture[]::new));
        });
    }

    private /* synthetic */ Stream lambda$run$4(CachedOutput output, HolderLookup.Provider provider, DynamicOps dynamicops, RegistryDataLoader.RegistryData registryData) {
        return this.dumpForgeRegistryCap(output, provider, (DynamicOps<JsonElement>)dynamicops, registryData).stream();
    }
}

