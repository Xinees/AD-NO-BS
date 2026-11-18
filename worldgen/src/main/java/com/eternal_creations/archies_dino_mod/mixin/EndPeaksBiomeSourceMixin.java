package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.worldgen.EndPeaksWorldgen;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
    private static final AtomicBoolean archie$loggedConstructor = new AtomicBoolean();
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

    @Inject(method = "<init>", at = @At("TAIL"))
    private void archie$captureEndPeaks(
        Holder<Biome> centerBiome,
        Holder<Biome> highlandsBiome,
        Holder<Biome> midlandsBiome,
        Holder<Biome> smallIslandsBiome,
        Holder<Biome> barrensBiome,
        CallbackInfo ci
    ) {
        try {
            this.archie$endPeaks = archie$resolveEndPeaks(highlandsBiome);
            if (archie$loggedConstructor.compareAndSet(false, true)) {
                String key = this.archie$endPeaks.unwrapKey()
                    .map(value -> value.location().toString())
                    .orElse("unbound");
                ARCHIE_LOGGER.info(
                    "[End Peaks] TheEndBiomeSource constructor hooked; biome holder={}",
                    key
                );
            }
        } catch (RuntimeException exception) {
            if (archie$loggedMissingHolder.compareAndSet(false, true)) {
                ARCHIE_LOGGER.error(
                    "[End Peaks] Failed to resolve the custom biome holder from the End biome registry",
                    exception
                );
            }
        }
    }

    @Unique
    @SuppressWarnings("unchecked")
    private static Holder<Biome> archie$resolveEndPeaks(Holder<Biome> vanillaBiome) {
        if (!(vanillaBiome instanceof Holder.Reference<?> reference)) {
            throw new IllegalStateException(
                "Vanilla End biome holder is not registry-backed: " + vanillaBiome.getClass().getName()
            );
        }

        HolderOwner<?> owner =
            ((EndPeaksHolderReferenceAccessor) (Object) reference).archie$getOwner();

        if (owner instanceof Registry<?> registry) {
            return ((Registry<Biome>) registry).getHolderOrThrow(EndPeaksWorldgen.BIOME);
        }

        if (owner instanceof HolderGetter<?> getter) {
            return ((HolderGetter<Biome>) getter).getOrThrow(EndPeaksWorldgen.BIOME);
        }

        throw new IllegalStateException(
            "The End biome holder owner cannot look up registry entries: " + owner.getClass().getName()
        );
    }

    @Inject(method = "collectPossibleBiomes", at = @At("RETURN"), cancellable = true)
    private void archie$includeEndPeaks(CallbackInfoReturnable<Stream<Holder<Biome>>> cir) {
        if (this.archie$endPeaks != null) {
            cir.setReturnValue(Stream.concat(cir.getReturnValue(), Stream.of(this.archie$endPeaks)));
            if (archie$loggedCollection.compareAndSet(false, true)) {
                ARCHIE_LOGGER.info("[End Peaks] Added archie_dinosaurs:end_peaks to possibleBiomes()");
            }
        } else if (archie$loggedMissingHolder.compareAndSet(false, true)) {
            ARCHIE_LOGGER.error(
                "[End Peaks] collectPossibleBiomes ran without a holder captured by the TheEndBiomeSource constructor"
            );
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
                ARCHIE_LOGGER.error(
                    "[End Peaks] getNoiseBiome is active, but the constructor did not capture the custom biome holder"
                );
            }
            return;
        }

        Holder<Biome> vanillaBiome = cir.getReturnValue();
        String vanillaKey = vanillaBiome.unwrapKey()
            .map(value -> value.location().toString())
            .orElse("unbound");
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
            || vanillaBiome.is(Biomes.END_BARRENS);
        if (outerEndBiome && EndPeaksWorldgen.isEndPeaksRegion(quartX, quartZ)) {
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
