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

public final class EndPeaksChorusFeature extends Feature<NoneFeatureConfiguration> {
    private static final int[][] CLUSTER_OFFSETS = {
        {0, 0},
        {2, 1},
        {-2, 2},
    };
    private static final double GROUP_RADIUS_OFFSET = 1.5D;

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
        int featureMargin = EndPeaksWorldgen.MAX_RADIUS
            + EndPeaksWorldgen.MAX_CENTER_OFFSET + 5;
        int minCellX = Math.floorDiv(chunkMinX - featureMargin, EndPeaksWorldgen.CELL_SIZE);
        int maxCellX = Math.floorDiv(chunkMinX + 15 + featureMargin, EndPeaksWorldgen.CELL_SIZE);
        int minCellZ = Math.floorDiv(chunkMinZ - featureMargin, EndPeaksWorldgen.CELL_SIZE);
        int maxCellZ = Math.floorDiv(chunkMinZ + 15 + featureMargin, EndPeaksWorldgen.CELL_SIZE);

        boolean placed = false;

        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                EndPeaksWorldgen.Mountain mountain = EndPeaksWorldgen.mountainAt(cellX, cellZ);
                if (mountain == null
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
                    boolean clusterInsideBiome = true;
                    for (int i = 0; i < CLUSTER_OFFSETS.length; i++) {
                        int localX = CLUSTER_OFFSETS[i][0];
                        int localZ = CLUSTER_OFFSETS[i][1];
                        int x = clusterX + (int) Math.round(localX * cos - localZ * sin);
                        int z = clusterZ + (int) Math.round(localX * sin + localZ * cos);
                        if (!level.getBiome(new BlockPos(x, 64, z)).is(EndPeaksWorldgen.BIOME)) {
                            clusterInsideBiome = false;
                            break;
                        }
                        plantPositions[i] = new BlockPos(x, 0, z);
                    }

                    if (!clusterInsideBiome) {
                        continue;
                    }

                    for (BlockPos plantPos : plantPositions) {
                        int x = plantPos.getX();
                        int z = plantPos.getZ();
                        if (x < chunkMinX || x >= chunkMinX + 16
                            || z < chunkMinZ || z >= chunkMinZ + 16) {
                            continue;
                        }

                        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                        BlockPos surfacePos = new BlockPos(x, y, z);
                        if (y <= level.getMinBuildHeight() + 1
                            || !level.getBiome(surfacePos).is(EndPeaksWorldgen.BIOME)
                            || !level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                            || !level.getBlockState(surfacePos).isAir()) {
                            continue;
                        }

                        BlockPos plant = new BlockPos(x, y, z);
                        placed |= Feature.CHORUS_PLANT.place(new FeaturePlaceContext<>(
                            java.util.Optional.empty(),
                            level,
                            context.chunkGenerator(),
                            random,
                            plant,
                            NoneFeatureConfiguration.INSTANCE
                        ));
                    }
                }
            }
        }

        if (random.nextInt(32) == 0) {
            int x = chunkMinX + 6 + random.nextInt(4);
            int z = chunkMinZ + 6 + random.nextInt(4);
            int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
            BlockPos surfacePos = new BlockPos(x, y, z);
            if (y > level.getMinBuildHeight() + 1
                && level.getBiome(surfacePos).is(EndPeaksWorldgen.BIOME)
                && level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                && level.getBlockState(surfacePos).isAir()) {
                placed |= Feature.CHORUS_PLANT.place(new FeaturePlaceContext<>(
                    java.util.Optional.empty(),
                    level,
                    context.chunkGenerator(),
                    random,
                    surfacePos,
                    NoneFeatureConfiguration.INSTANCE
                ));
            }
        }

        return placed;
    }
}
