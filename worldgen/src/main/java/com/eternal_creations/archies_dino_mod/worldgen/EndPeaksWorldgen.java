package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public final class EndPeaksWorldgen {
    private static final int REGION_CELL_SIZE = 960;
    private static final int REGION_BASE_RADIUS = 235;
    private static final int REGION_JITTER = 80;

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
        if ((hash & 31L) >= 10L) {
            return null;
        }

        int centerX = cellX * CELL_SIZE + CELL_SIZE / 2
            + (int) ((hash >>> 8) % (MAX_CENTER_OFFSET * 2L + 1L)) - MAX_CENTER_OFFSET;
        int centerZ = cellZ * CELL_SIZE + CELL_SIZE / 2
            + (int) ((hash >>> 13) % (MAX_CENTER_OFFSET * 2L + 1L)) - MAX_CENTER_OFFSET;
        int radiusX = 5 + (int) ((hash >>> 19) & 1L);
        int radiusZ = 5 + (int) ((hash >>> 23) & 1L);
        int height = 15 + (int) ((hash >>> 27) % 6L);
        double plateauRadius = 0.48D + ((hash >>> 33) & 3L) * 0.02D;

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
        return getRegionNoiseValue(quartX, quartZ) > 0.0D;
    }

    public static double getRegionNoiseValue(int quartX, int quartZ) {
        return containsRegion(quartX * 4, quartZ * 4) ? 1.0D : -1.0D;
    }

    private static boolean containsRegion(int blockX, int blockZ) {
        int cellX = Math.floorDiv(blockX, REGION_CELL_SIZE);
        int cellZ = Math.floorDiv(blockZ, REGION_CELL_SIZE);

        for (int candidateX = cellX - 1; candidateX <= cellX + 1; candidateX++) {
            for (int candidateZ = cellZ - 1; candidateZ <= cellZ + 1; candidateZ++) {
                long hash = mix(candidateX ^ 0x51ED270B, candidateZ ^ 0x367CD507);
                boolean originRegion = candidateX == 0 && candidateZ == 0;
                if ((hash & 3L) != 0L) {
                    if (!originRegion) {
                        continue;
                    }
                }

                int jitterX = (int) Long.remainderUnsigned(hash >>> 8, REGION_JITTER * 2L + 1L)
                    - REGION_JITTER;
                int jitterZ = (int) Long.remainderUnsigned(hash >>> 19, REGION_JITTER * 2L + 1L)
                    - REGION_JITTER;
                int centerX = candidateX * REGION_CELL_SIZE + (originRegion ? 0 : jitterX);
                int centerZ = candidateZ * REGION_CELL_SIZE + (originRegion ? 0 : jitterZ);

                double dx = blockX - centerX;
                double dz = blockZ - centerZ;
                double distanceSquared = dx * dx + dz * dz;
                double angle = Math.atan2(dz, dx);
                double phaseA = phase(hash, 27);
                double phaseB = phase(hash, 43);
                double phaseC = phase(hash, 57);
                double radius = REGION_BASE_RADIUS
                    + Math.sin(angle * 3.0D + phaseA) * 15.0D
                    + Math.cos(angle * 5.0D + phaseB) * 10.0D
                    + Math.sin(angle * 7.0D + phaseC) * 6.0D;

                if (distanceSquared <= radius * radius) {
                    return true;
                }
            }
        }

        return false;
    }

    private static double phase(long hash, int shift) {
        return ((hash >>> shift) & 0xFFFFL) / 65535.0D * Math.PI * 2.0D;
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
