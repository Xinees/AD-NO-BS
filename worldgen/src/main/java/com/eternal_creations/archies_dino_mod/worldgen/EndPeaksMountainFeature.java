package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class EndPeaksMountainFeature extends Feature<NoneFeatureConfiguration> {
    private static final int BIOME_SAMPLE_Y = 64;

    public EndPeaksMountainFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        if (!Level.END.equals(level.getLevel().dimension())) {
            return false;
        }

        BlockPos origin = context.origin();
        int chunkMinX = Math.floorDiv(origin.getX(), 16) * 16;
        int chunkMinZ = Math.floorDiv(origin.getZ(), 16) * 16;
        int minCellX = Math.floorDiv(chunkMinX - EndPeaksWorldgen.MAX_RADIUS
            - EndPeaksWorldgen.MAX_CENTER_OFFSET, EndPeaksWorldgen.CELL_SIZE);
        int maxCellX = Math.floorDiv(chunkMinX + 15 + EndPeaksWorldgen.MAX_RADIUS
            + EndPeaksWorldgen.MAX_CENTER_OFFSET, EndPeaksWorldgen.CELL_SIZE);
        int minCellZ = Math.floorDiv(chunkMinZ - EndPeaksWorldgen.MAX_RADIUS
            - EndPeaksWorldgen.MAX_CENTER_OFFSET, EndPeaksWorldgen.CELL_SIZE);
        int maxCellZ = Math.floorDiv(chunkMinZ + 15 + EndPeaksWorldgen.MAX_RADIUS
            + EndPeaksWorldgen.MAX_CENTER_OFFSET, EndPeaksWorldgen.CELL_SIZE);

        List<EndPeaksWorldgen.Mountain> mountains = new ArrayList<>();
        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                EndPeaksWorldgen.Mountain mountain = EndPeaksWorldgen.mountainAt(cellX, cellZ);
                if (mountain != null && isMountainContainedInBiome(level, mountain)) {
                    mountains.add(mountain);
                }
            }
        }

        if (mountains.isEmpty()) {
            return false;
        }

        MutableBlockPos mutable = new MutableBlockPos();
        boolean placed = false;

        for (int x = chunkMinX; x < chunkMinX + 16; x++) {
            for (int z = chunkMinZ; z < chunkMinZ + 16; z++) {
                int groundY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                if (groundY <= level.getMinBuildHeight() + 1) {
                    continue;
                }

                BlockPos surfacePos = new BlockPos(x, groundY, z);
                if (!level.getBlockState(new BlockPos(x, groundY - 1, z)).is(Blocks.END_STONE)
                    || !level.getBiome(surfacePos).is(EndPeaksWorldgen.BIOME)) {
                    continue;
                }

                int maxRise = 0;
                for (EndPeaksWorldgen.Mountain mountain : mountains) {
                    maxRise = Math.max(maxRise, getRiseAt(mountain, x, z));
                }

                if (maxRise < 2) {
                    continue;
                }

                int topY = Math.min(groundY + maxRise - 1, level.getMaxBuildHeight() - 1);
                for (int y = groundY; y <= topY; y++) {
                    mutable.set(x, y, z);
                    if (level.getBlockState(mutable).isAir()) {
                        level.setBlock(mutable, Blocks.END_STONE.defaultBlockState(), 2);
                        placed = true;
                    }
                }
            }
        }

        return placed;
    }

    static boolean isMountainContainedInBiome(
        WorldGenLevel level,
        EndPeaksWorldgen.Mountain mountain
    ) {
        Set<Long> checkedQuartCells = new HashSet<>();
        MutableBlockPos samplePos = new MutableBlockPos();

        for (int x = mountain.centerX() - mountain.radiusX();
             x <= mountain.centerX() + mountain.radiusX(); x++) {
            for (int z = mountain.centerZ() - mountain.radiusZ();
                 z <= mountain.centerZ() + mountain.radiusZ(); z++) {
                double dx = (x - mountain.centerX()) / (double) mountain.radiusX();
                double dz = (z - mountain.centerZ()) / (double) mountain.radiusZ();
                if (dx * dx + dz * dz >= 1.0D) {
                    continue;
                }

                int quartX = Math.floorDiv(x, 4);
                int quartZ = Math.floorDiv(z, 4);
                long quartKey = ((long) quartX << 32) | (quartZ & 0xFFFFFFFFL);
                if (!checkedQuartCells.add(quartKey)) {
                    continue;
                }

                samplePos.set(quartX * 4 + 2, BIOME_SAMPLE_Y, quartZ * 4 + 2);
                if (!level.getBiome(samplePos).is(EndPeaksWorldgen.BIOME)) {
                    return false;
                }
            }
        }

        return !checkedQuartCells.isEmpty();
    }

    private static int getRiseAt(EndPeaksWorldgen.Mountain mountain, int x, int z) {
        int offsetX = x - mountain.centerX();
        int offsetZ = z - mountain.centerZ();
        if (isSummitBlock(mountain, offsetX, offsetZ)) {
            return mountain.height();
        }

        double dx = offsetX / (double) mountain.radiusX();
        double dz = offsetZ / (double) mountain.radiusZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        double angle = Math.atan2(dz, dx);
        double phase = ((mountain.hash() >>> 40) & 0xFFFFL) / 65536.0D * Math.PI * 2.0D;
        double secondaryPhase =
            ((mountain.hash() >>> 24) & 0xFFFFL) / 65536.0D * Math.PI * 2.0D;
        double ridgeShape = 1.0D
            + Math.sin(angle * 3.0D + phase) * 0.055D
            + Math.cos(angle * 5.0D + secondaryPhase) * 0.025D;
        distance /= ridgeShape;

        if (distance >= 1.0D) {
            return 0;
        }

        double profile = 1.0D;
        if (distance > mountain.plateauRadius()) {
            double slope = (1.0D - distance) / (1.0D - mountain.plateauRadius());
            profile = Math.pow(slope, 0.82D);
        }

        return (int) Math.floor(mountain.height() * profile);
    }

    private static boolean isSummitBlock(
        EndPeaksWorldgen.Mountain mountain,
        int offsetX,
        int offsetZ
    ) {
        if (Math.abs(offsetX) <= 1 && Math.abs(offsetZ) <= 1) {
            return true;
        }

        boolean summitEdge = (Math.abs(offsetX) == 2 && Math.abs(offsetZ) <= 1)
            || (Math.abs(offsetZ) == 2 && Math.abs(offsetX) <= 1);
        if (!summitEdge) {
            return false;
        }

        int localX = (int) (mountain.hash() >>> 32) + offsetX * 17;
        int localZ = (int) mountain.hash() + offsetZ * 31;
        return (EndPeaksWorldgen.mix(localX, localZ) & 3L) == 0L;
    }
}
