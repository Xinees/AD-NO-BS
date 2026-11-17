package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Optional;

public final class EndPeaksChorusFeature extends Feature<NoneFeatureConfiguration> {
    private static final int[][] CLUSTER_OFFSETS = {
        {0, 0},
        {2, 1},
        {-2, 2},
    };

    public EndPeaksChorusFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        int chunkMinX = Math.floorDiv(origin.getX(), 16) * 16;
        int chunkMinZ = Math.floorDiv(origin.getZ(), 16) * 16;
        int minCellX = Math.floorDiv(chunkMinX - EndPeaksWorldgen.MAX_RADIUS
            - EndPeaksWorldgen.MAX_CENTER_OFFSET - 16, EndPeaksWorldgen.CELL_SIZE);
        int maxCellX = Math.floorDiv(chunkMinX + 31 + EndPeaksWorldgen.MAX_RADIUS
            + EndPeaksWorldgen.MAX_CENTER_OFFSET, EndPeaksWorldgen.CELL_SIZE);
        int minCellZ = Math.floorDiv(chunkMinZ - EndPeaksWorldgen.MAX_RADIUS
            - EndPeaksWorldgen.MAX_CENTER_OFFSET - 16, EndPeaksWorldgen.CELL_SIZE);
        int maxCellZ = Math.floorDiv(chunkMinZ + 31 + EndPeaksWorldgen.MAX_RADIUS
            + EndPeaksWorldgen.MAX_CENTER_OFFSET, EndPeaksWorldgen.CELL_SIZE);

        boolean placed = false;

        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                EndPeaksWorldgen.Mountain mountain = EndPeaksWorldgen.mountainAt(cellX, cellZ);
                if (mountain == null) {
                    continue;
                }

                int centerY = level.getHeight(
                    Heightmap.Types.WORLD_SURFACE_WG,
                    mountain.centerX(),
                    mountain.centerZ()
                );
                if (centerY <= level.getMinBuildHeight()
                    || !level.getBiome(new BlockPos(mountain.centerX(), centerY, mountain.centerZ()))
                        .is(EndPeaksWorldgen.BIOME)) {
                    continue;
                }

                int groupCount = 2 + (int) ((mountain.hash() >>> 60) & 1L);
                double angleOffset = ((mountain.hash() >>> 40) & 0xFFFFL) / 65536.0D
                    * Math.PI * 2.0D;

                for (int group = 0; group < groupCount; group++) {
                    double angle = angleOffset + Math.PI * 2.0D * group / groupCount;
                    int targetX = mountain.centerX()
                        + (int) Math.round(Math.cos(angle) * (mountain.radiusX() + 6.0D));
                    int targetZ = mountain.centerZ()
                        + (int) Math.round(Math.sin(angle) * (mountain.radiusZ() + 6.0D));
                    int clusterX = EndPeaksWorldgen.snapToChunkInterior(targetX);
                    int clusterZ = EndPeaksWorldgen.snapToChunkInterior(targetZ);

                    if (clusterX < chunkMinX || clusterX >= chunkMinX + 16
                        || clusterZ < chunkMinZ || clusterZ >= chunkMinZ + 16) {
                        continue;
                    }

                    for (int[] offset : CLUSTER_OFFSETS) {
                        int x = clusterX + offset[0];
                        int z = clusterZ + offset[1];
                        if (x < chunkMinX || x >= chunkMinX + 16
                            || z < chunkMinZ || z >= chunkMinZ + 16) {
                            continue;
                        }

                        if (!level.getBiome(new BlockPos(x, centerY, z)).is(EndPeaksWorldgen.BIOME)) {
                            continue;
                        }

                        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                        if (y <= level.getMinBuildHeight() + 1
                            || !level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                            || !level.getBlockState(new BlockPos(x, y, z)).isAir()) {
                            continue;
                        }

                        BlockPos plantPos = new BlockPos(x, y, z);
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

        if (random.nextInt(16) == 0) {
            int x = chunkMinX + 6 + random.nextInt(4);
            int z = chunkMinZ + 6 + random.nextInt(4);
            if (level.getBiome(new BlockPos(x, origin.getY(), z)).is(EndPeaksWorldgen.BIOME)) {
                int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                if (y > level.getMinBuildHeight() + 1
                    && level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                    && level.getBlockState(new BlockPos(x, y, z)).isAir()) {
                    placed |= Feature.CHORUS_PLANT.place(new FeaturePlaceContext<>(
                        Optional.empty(),
                        level,
                        context.chunkGenerator(),
                        random,
                        new BlockPos(x, y, z),
                        NoneFeatureConfiguration.INSTANCE
                    ));
                }
            }
        }

        return placed;
    }
}
