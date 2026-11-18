package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.util.RandomSource;

import java.util.Optional;

public final class EndPeaksChorusFeature extends Feature<NoneFeatureConfiguration> {
    private static final int[][] CLUSTER_OFFSETS = {
        {0, 0},
        {0, 6},
        {0, -6},
    };
    private static final double GROUP_RADIUS_OFFSET = 4.0D;
    private static final int GROUND_PLANT_EXCLUSION_MARGIN = 16;
    private static final int GROUND_PLANT_CHANCE = 4;
    private static final int GROUND_PLANT_ATTEMPTS = 8;

    public EndPeaksChorusFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        if (!Level.END.equals(level.getLevel().dimension())) {
            return false;
        }

        RandomSource random = context.random();
        BlockPos origin = context.origin();
        int chunkMinX = Math.floorDiv(origin.getX(), 16) * 16;
        int chunkMinZ = Math.floorDiv(origin.getZ(), 16) * 16;
        int chunkMaxX = chunkMinX + 15;
        int chunkMaxZ = chunkMinZ + 15;
        int minCellX = EndPeaksWorldgen.minCellForCenterRange(chunkMinX, 0);
        int maxCellX = EndPeaksWorldgen.maxCellForCenterRange(chunkMaxX, 0);
        int minCellZ = EndPeaksWorldgen.minCellForCenterRange(chunkMinZ, 0);
        int maxCellZ = EndPeaksWorldgen.maxCellForCenterRange(chunkMaxZ, 0);

        boolean placed = false;

        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                EndPeaksWorldgen.Mountain mountain = EndPeaksWorldgen.mountainAt(cellX, cellZ);
                if (mountain == null
                    || Math.floorDiv(mountain.centerX(), 16) != Math.floorDiv(chunkMinX, 16)
                    || Math.floorDiv(mountain.centerZ(), 16) != Math.floorDiv(chunkMinZ, 16)
                    || !EndPeaksMountainFeature.isMountainContainedInBiome(level, mountain)) {
                    continue;
                }

                int groupCount = 3 + (int) ((mountain.hash() >>> 60) & 1L);
                double angleOffset = ((mountain.hash() >>> 40) & 0xFFFFL) / 65536.0D
                    * Math.PI * 2.0D;

                for (int group = 0; group < groupCount; group++) {
                    double angle = angleOffset + Math.PI * 2.0D * group / groupCount;
                    double cos = Math.cos(angle);
                    double sin = Math.sin(angle);
                    int clusterX = mountain.centerX()
                        + (int) Math.round(cos * (mountain.radiusX() + GROUP_RADIUS_OFFSET));
                    int clusterZ = mountain.centerZ()
                        + (int) Math.round(sin * (mountain.radiusZ() + GROUP_RADIUS_OFFSET));

                    BlockPos[] plantPositions = new BlockPos[CLUSTER_OFFSETS.length];
                    boolean clusterValid = true;

                    for (int i = 0; i < CLUSTER_OFFSETS.length; i++) {
                        int localX = CLUSTER_OFFSETS[i][0];
                        int localZ = CLUSTER_OFFSETS[i][1];
                        int x = clusterX + (int) Math.round(localX * cos - localZ * sin);
                        int z = clusterZ + (int) Math.round(localX * sin + localZ * cos);
                        if (!level.getBiome(new BlockPos(x, 64, z)).is(EndPeaksWorldgen.BIOME)) {
                            clusterValid = false;
                            break;
                        }

                        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                        BlockPos surfacePos = new BlockPos(x, y, z);
                        if (y <= level.getMinBuildHeight() + 1
                            || !level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                            || !level.getBlockState(surfacePos).isAir()
                            || !level.getBiome(surfacePos).is(EndPeaksWorldgen.BIOME)) {
                            clusterValid = false;
                            break;
                        }
                        plantPositions[i] = surfacePos;
                    }

                    if (!clusterValid) {
                        continue;
                    }

                    for (BlockPos plantPos : plantPositions) {
                        placed |= Feature.CHORUS_PLANT.place(new FeaturePlaceContext<>(
                            Optional.empty(),
                            level,
                            context.chunkGenerator(),
                            random,
                            plantPos,
                            NoneFeatureConfiguration.INSTANCE
                        ));
                    }
                }
            }
        }

        if (random.nextInt(GROUND_PLANT_CHANCE) == 0) {
            for (int attempt = 0; attempt < GROUND_PLANT_ATTEMPTS; attempt++) {
                int x = chunkMinX + 6 + random.nextInt(4);
                int z = chunkMinZ + 6 + random.nextInt(4);
                if (isNearPlannedMountain(x, z)) {
                    continue;
                }

                int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                BlockPos surfacePos = new BlockPos(x, y, z);
                if (y <= level.getMinBuildHeight() + 1
                    || !level.getBiome(new BlockPos(x, 64, z)).is(EndPeaksWorldgen.BIOME)
                    || !level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                    || !level.getBlockState(surfacePos).isAir()
                    || !level.getBiome(surfacePos).is(EndPeaksWorldgen.BIOME)) {
                    continue;
                }

                placed |= Feature.CHORUS_PLANT.place(new FeaturePlaceContext<>(
                    Optional.empty(),
                    level,
                    context.chunkGenerator(),
                    random,
                    surfacePos,
                    NoneFeatureConfiguration.INSTANCE
                ));
                break;
            }
        }

        return placed;
    }

    private static boolean isNearPlannedMountain(int x, int z) {
        int searchRadius = EndPeaksWorldgen.MAX_RADIUS + GROUND_PLANT_EXCLUSION_MARGIN;
        int minCellX = EndPeaksWorldgen.minCellForCenterRange(x, searchRadius);
        int maxCellX = EndPeaksWorldgen.maxCellForCenterRange(x, searchRadius);
        int minCellZ = EndPeaksWorldgen.minCellForCenterRange(z, searchRadius);
        int maxCellZ = EndPeaksWorldgen.maxCellForCenterRange(z, searchRadius);

        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                EndPeaksWorldgen.Mountain mountain = EndPeaksWorldgen.mountainAt(cellX, cellZ);
                if (mountain == null) {
                    continue;
                }

                double dx = (x - mountain.centerX())
                    / (double) (mountain.radiusX() + GROUND_PLANT_EXCLUSION_MARGIN);
                double dz = (z - mountain.centerZ())
                    / (double) (mountain.radiusZ() + GROUND_PLANT_EXCLUSION_MARGIN);
                if (dx * dx + dz * dz < 1.0D) {
                    return true;
                }
            }
        }
        return false;
    }
}
