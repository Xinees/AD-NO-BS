package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public final class EndPeaksWorldgen {
    private static final SimplexNoise BIOME_NOISE =
        new SimplexNoise(new LegacyRandomSource(0x454E445045414B53L));
    private static final SimplexNoise BIOME_DETAIL =
        new SimplexNoise(new LegacyRandomSource(0x454E445045414B44L));
    private static final double REGION_THRESHOLD = 0.18D;
    private static final double BIOME_NOISE_SCALE = 0.0016D;
    private static final double BIOME_DETAIL_SCALE = 0.0048D;
    private static final double BIOME_DETAIL_WEIGHT = 0.18D;
    public static final ResourceKey<Biome> BIOME = ResourceKey.create(
        Registries.BIOME,
        new ResourceLocation("archie_dinosaurs", "end_peaks")
    );

    public static final int CELL_SIZE = 24;
    public static final int MAX_RADIUS = 6;
    public static final int MAX_CENTER_OFFSET = 2;

    private EndPeaksWorldgen() {
    }

    public static Mountain mountainAt(int cellX, int cellZ) {
        long hash = mix(cellX, cellZ);
        if ((hash & 31L) >= 9L) {
            return null;
        }

        int centerX = cellX * CELL_SIZE + CELL_SIZE / 2
            + (int) ((hash >>> 8) % (MAX_CENTER_OFFSET * 2L + 1L)) - MAX_CENTER_OFFSET;
        int centerZ = cellZ * CELL_SIZE + CELL_SIZE / 2
            + (int) ((hash >>> 13) % (MAX_CENTER_OFFSET * 2L + 1L)) - MAX_CENTER_OFFSET;
        int radiusX = 5 + (int) ((hash >>> 19) & 1L);
        int radiusZ = 5 + (int) ((hash >>> 23) & 1L);
        int height = 12 + (int) ((hash >>> 27) % 5L) * 2;
        double plateauRadius = 0.26D + ((hash >>> 33) & 3L) * 0.02D;

        return new Mountain(centerX, centerZ, radiusX, radiusZ, height, plateauRadius, hash);
    }

    public static int minCellForCenterRange(int minBlock, int centerRadius) {
        int minimumCellCenter = minBlock - centerRadius - CELL_SIZE / 2 - MAX_CENTER_OFFSET;
        return -Math.floorDiv(-minimumCellCenter, CELL_SIZE);
    }

    public static int maxCellForCenterRange(int maxBlock, int centerRadius) {
        int maximumCellCenter = maxBlock + centerRadius - CELL_SIZE / 2 + MAX_CENTER_OFFSET;
        return Math.floorDiv(maximumCellCenter, CELL_SIZE);
    }

    public static long mix(int x, int z) {
        long value = (long) x * 341873128712L
            + (long) z * 132897987541L
            + 0x9E3779B97F4A7C15L;
        value = (value ^ (value >>> 30)) * 0xBF58476D1CE4E5B9L;
        value = (value ^ (value >>> 27)) * 0x94D049BB133111EBL;
        return value ^ (value >>> 31);
    }

    public static boolean isEndPeaksRegion(int quartX, int quartZ) {
        return getRegionNoiseValue(quartX, quartZ) > REGION_THRESHOLD;
    }

    public static double getRegionNoiseValue(int quartX, int quartZ) {
        double blockX = quartX * 4.0D;
        double blockZ = quartZ * 4.0D;
        double broad = BIOME_NOISE.getValue(
            blockX * BIOME_NOISE_SCALE,
            blockZ * BIOME_NOISE_SCALE
        );
        double detail = BIOME_DETAIL.getValue(
            blockX * BIOME_DETAIL_SCALE,
            blockZ * BIOME_DETAIL_SCALE
        );
        return broad + detail * BIOME_DETAIL_WEIGHT;
    }

    public record Mountain(
        int centerX,
        int centerZ,
        int radiusX,
        int radiusZ,
        int height,
        double plateauRadius,
        long hash
    ) {
    }
}
