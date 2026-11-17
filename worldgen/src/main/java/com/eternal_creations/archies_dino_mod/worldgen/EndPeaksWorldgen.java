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
    public static final ResourceKey<Biome> BIOME = ResourceKey.create(
        Registries.BIOME,
        new ResourceLocation("archie_dinosaurs", "end_peaks")
    );

    public static final int CELL_SIZE = 112;
    public static final int MAX_RADIUS = 40;
    public static final int MAX_CENTER_OFFSET = 12;

    private EndPeaksWorldgen() {
    }

    public static Mountain mountainAt(int cellX, int cellZ) {
        long hash = mix(cellX, cellZ);
        if ((hash & 3L) == 0L) {
            return null;
        }

        int centerX = cellX * CELL_SIZE + CELL_SIZE / 2
            + (int) ((hash >>> 8) % (MAX_CENTER_OFFSET * 2L + 1L)) - MAX_CENTER_OFFSET;
        int centerZ = cellZ * CELL_SIZE + CELL_SIZE / 2
            + (int) ((hash >>> 13) % (MAX_CENTER_OFFSET * 2L + 1L)) - MAX_CENTER_OFFSET;
        int radiusX = 26 + (int) ((hash >>> 19) & 14L);
        int radiusZ = 26 + (int) ((hash >>> 23) & 14L);
        int height = 32 + (int) ((hash >>> 27) & 18L);
        double plateauRadius = 0.27D + ((hash >>> 33) & 7L) * 0.012D;

        return new Mountain(centerX, centerZ, radiusX, radiusZ, height, plateauRadius, hash);
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
        return getRegionNoise(quartX, quartZ) > 0.08D;
    }

    public static boolean isEndPeaksMidlandsRegion(int quartX, int quartZ) {
        return getRegionNoise(quartX, quartZ) > 0.48D;
    }

    private static double getRegionNoise(int quartX, int quartZ) {
        double blockX = quartX * 4.0D;
        double blockZ = quartZ * 4.0D;
        double broad = BIOME_NOISE.getValue(blockX * 0.0021D, blockZ * 0.0021D);
        double detail = BIOME_DETAIL.getValue(blockX * 0.0064D, blockZ * 0.0064D);
        return broad + detail * 0.22D;
    }

    public static int snapToChunkInterior(int coordinate) {
        int chunkStart = Math.floorDiv(coordinate, 16) * 16;
        int local = coordinate - chunkStart;
        if (local < 6) {
            local = 6;
        } else if (local > 9) {
            local = 9;
        }
        return chunkStart + local;
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
