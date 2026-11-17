package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.worldgen.EndPeaksWorldgen;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(TheEndBiomeSource.class)
public abstract class EndPeaksBiomeSourceMixin {
    @Unique
    private static final SimplexNoise ARCHIE_END_PEAKS_NOISE =
        new SimplexNoise(RandomSource.create(0x454E445045414B53L));

    @Unique
    private static final SimplexNoise ARCHIE_END_PEAKS_DETAIL =
        new SimplexNoise(RandomSource.create(0x454E445045414B44L));

    @Unique
    private Holder<Biome> archie$endPeaks;

    @Inject(method = "create", at = @At("RETURN"))
    private static void archie$captureEndPeaks(
        HolderGetter<Biome> biomes,
        CallbackInfoReturnable<TheEndBiomeSource> cir
    ) {
        ((EndPeaksBiomeSourceMixin) (Object) cir.getReturnValue()).archie$endPeaks =
            biomes.getOrThrow(EndPeaksWorldgen.BIOME);
    }

    @Inject(method = "collectPossibleBiomes", at = @At("RETURN"), cancellable = true)
    private void archie$includeEndPeaks(CallbackInfoReturnable<Stream<Holder<Biome>>> cir) {
        if (this.archie$endPeaks != null) {
            cir.setReturnValue(Stream.concat(cir.getReturnValue(), Stream.of(this.archie$endPeaks)));
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
        if (this.archie$endPeaks != null
            && cir.getReturnValue().is(Biomes.END_HIGHLANDS)
            && archie$isEndPeaksRegion(quartX, quartZ)) {
            cir.setReturnValue(this.archie$endPeaks);
        }
    }

    @Unique
    private static boolean archie$isEndPeaksRegion(int quartX, int quartZ) {
        double blockX = quartX * 4.0D;
        double blockZ = quartZ * 4.0D;
        double broad = ARCHIE_END_PEAKS_NOISE.getValue(blockX * 0.0021D, blockZ * 0.0021D);
        double detail = ARCHIE_END_PEAKS_DETAIL.getValue(blockX * 0.0064D, blockZ * 0.0064D);
        return broad + detail * 0.22D > 0.17D;
    }
}
