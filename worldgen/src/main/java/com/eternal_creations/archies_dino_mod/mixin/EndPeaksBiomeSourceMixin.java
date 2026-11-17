package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.worldgen.EndPeaksWorldgen;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Mixin(TheEndBiomeSource.class)
public abstract class EndPeaksBiomeSourceMixin {
    @Unique
    private static final Logger ARCHIE_LOGGER = LogUtils.getLogger();
    @Unique
    private static final AtomicBoolean archie$loggedCreate = new AtomicBoolean();
    @Unique
    private static final AtomicBoolean archie$loggedCollection = new AtomicBoolean();
    @Unique
    private static final AtomicBoolean archie$loggedMissingHolder = new AtomicBoolean();
    @Unique
    private static final AtomicBoolean archie$loggedSelection = new AtomicBoolean();
    @Unique
    private static final Set<String> archie$loggedVanillaBiomes = ConcurrentHashMap.newKeySet();

    @Unique
    private Holder<Biome> archie$endPeaks;

    @Inject(method = "create", at = @At("RETURN"))
    private static void archie$captureEndPeaks(
        HolderGetter<Biome> biomes,
        CallbackInfoReturnable<TheEndBiomeSource> cir
    ) {
        Holder<Biome> holder = biomes.getOrThrow(EndPeaksWorldgen.BIOME);
        ((EndPeaksBiomeSourceMixin) (Object) cir.getReturnValue()).archie$endPeaks = holder;
        if (archie$loggedCreate.compareAndSet(false, true)) {
            String key = holder.unwrapKey().map(value -> value.location().toString()).orElse("unbound");
            ARCHIE_LOGGER.info("[End Peaks] TheEndBiomeSource.create hooked; biome holder={}", key);
        }
    }

    @Inject(method = "collectPossibleBiomes", at = @At("RETURN"), cancellable = true)
    private void archie$includeEndPeaks(CallbackInfoReturnable<Stream<Holder<Biome>>> cir) {
        if (this.archie$endPeaks != null) {
            cir.setReturnValue(Stream.concat(cir.getReturnValue(), Stream.of(this.archie$endPeaks)));
            if (archie$loggedCollection.compareAndSet(false, true)) {
                ARCHIE_LOGGER.info("[End Peaks] Added archie_dinosaurs:end_peaks to possibleBiomes()");
            }
        } else if (archie$loggedMissingHolder.compareAndSet(false, true)) {
            ARCHIE_LOGGER.warn("[End Peaks] collectPossibleBiomes ran before the custom biome holder was captured");
        }
    }

    @Inject(method = "getNoiseBiome", at = @At("RETURN"), cancellable = true)
    private void archie$selectEndPeaks(
        int quartX,
        int quartY,
        int quartZ,
        Climate.Sampler sampler,
        CallbackInfoReturnable<Holder<Biome>> cir
    ) {
        if (this.archie$endPeaks == null) {
            if (archie$loggedMissingHolder.compareAndSet(false, true)) {
                ARCHIE_LOGGER.error("[End Peaks] getNoiseBiome is active, but its custom biome holder is null");
            }
            return;
        }

        Holder<Biome> vanillaBiome = cir.getReturnValue();
        String vanillaKey = vanillaBiome.unwrapKey().map(value -> value.location().toString()).orElse("unbound");
        double noise = EndPeaksWorldgen.getRegionNoiseValue(quartX, quartZ);
        if (archie$loggedVanillaBiomes.add(vanillaKey)) {
            ARCHIE_LOGGER.info(
                "[End Peaks] Sampling TheEndBiomeSource: vanillaBiome={}, firstQuart=({}, {}), regionNoise={}",
                vanillaKey,
                quartX,
                quartZ,
                noise
            );
        }

        boolean outerEndBiome = vanillaBiome.is(Biomes.END_HIGHLANDS)
            || vanillaBiome.is(Biomes.END_MIDLANDS)
            || vanillaBiome.is(Biomes.END_BARRENS)
            || vanillaBiome.is(Biomes.SMALL_END_ISLANDS);
        if (outerEndBiome && noise > 0.05D) {
            cir.setReturnValue(this.archie$endPeaks);
            if (archie$loggedSelection.compareAndSet(false, true)) {
                ARCHIE_LOGGER.info(
                    "[End Peaks] Selection active at quart=({}, {}), vanillaBiome={}, regionNoise={}",
                    quartX,
                    quartZ,
                    vanillaKey,
                    noise
                );
            }
        }
    }
}
