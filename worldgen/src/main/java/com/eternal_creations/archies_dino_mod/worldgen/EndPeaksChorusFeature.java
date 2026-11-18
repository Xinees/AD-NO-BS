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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class EndPeaksChorusFeature extends Feature<NoneFeatureConfiguration> {
    private static final int MIN_GROUPS = 3;
    private static final int MAX_GROUPS = 4;
    private static final int GROUP_EDGE_MIN = 8;
    private static final int GROUP_EDGE_RANGE = 7;
    private static final int GROUP_CENTER_MIN_SPACING = 14;
    private static final int TREE_ROOT_MIN_SPACING = 5;
    private static final int TREE_ROOT_GROUP_SPACING = 7;
    private static final int TREE_ROOT_MAX_OFFSET = 7;
    private static final int GROUP_POSITION_ATTEMPTS = 64;
    private static final int TREE_POSITION_ATTEMPTS = 64;
    private static final int GROUND_PLANT_EXCLUSION_MARGIN = 34;
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
        int minCellX = EndPeaksWorldgen.minCellForCenterRange(
            chunkMinX,
            GROUND_PLANT_EXCLUSION_MARGIN
        );
        int maxCellX = EndPeaksWorldgen.maxCellForCenterRange(
            chunkMaxX,
            GROUND_PLANT_EXCLUSION_MARGIN
        );
        int minCellZ = EndPeaksWorldgen.minCellForCenterRange(
            chunkMinZ,
            GROUND_PLANT_EXCLUSION_MARGIN
        );
        int maxCellZ = EndPeaksWorldgen.maxCellForCenterRange(
            chunkMaxZ,
            GROUND_PLANT_EXCLUSION_MARGIN
        );

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

                List<List<BlockPos>> groves = createGroves(mountain);
                for (List<BlockPos> grove : groves) {
                    BlockPos[] plantPositions = new BlockPos[grove.size()];
                    boolean groveValid = true;

                    for (int i = 0; i < grove.size(); i++) {
                        BlockPos root = grove.get(i);
                        int x = root.getX();
                        int z = root.getZ();
                        int y = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                        BlockPos surface = new BlockPos(x, y, z);
                        if (y <= level.getMinBuildHeight() + 1
                            || !level.getBiome(new BlockPos(x, 64, z)).is(EndPeaksWorldgen.BIOME)
                            || !level.getBiome(surface).is(EndPeaksWorldgen.BIOME)
                            || !level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                            || !level.getBlockState(surface).isAir()) {
                            groveValid = false;
                            break;
                        }
                        plantPositions[i] = surface;
                    }

                    if (!groveValid) {
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
                BlockPos surface = new BlockPos(x, y, z);
                if (y <= level.getMinBuildHeight() + 1
                    || !level.getBiome(new BlockPos(x, 64, z)).is(EndPeaksWorldgen.BIOME)
                    || !level.getBiome(surface).is(EndPeaksWorldgen.BIOME)
                    || !level.getBlockState(new BlockPos(x, y - 1, z)).is(Blocks.END_STONE)
                    || !level.getBlockState(surface).isAir()) {
                    continue;
                }

                placed |= Feature.CHORUS_PLANT.place(new FeaturePlaceContext<>(
                    Optional.empty(),
                    level,
                    context.chunkGenerator(),
                    random,
                    surface,
                    NoneFeatureConfiguration.INSTANCE
                ));
                break;
            }
        }

        return placed;
    }

    private static List<List<BlockPos>> createGroves(EndPeaksWorldgen.Mountain mountain) {
        int groupCount = MIN_GROUPS
            + (int) Long.remainderUnsigned(mountain.hash() >>> 60, MAX_GROUPS - MIN_GROUPS + 1L);
        List<BlockPos> groupCenters = new ArrayList<>(groupCount);

        for (int group = 0; group < groupCount; group++) {
            for (int attempt = 0; attempt < GROUP_POSITION_ATTEMPTS; attempt++) {
                long hash = positionHash(mountain, group, -1, attempt);
                double angle = unit(hash >>> 8) * Math.PI * 2.0D;
                int edgeOffset = GROUP_EDGE_MIN
                    + (int) Long.remainderUnsigned(hash >>> 24, GROUP_EDGE_RANGE);
                int radiusX = mountain.radiusX() + edgeOffset;
                int radiusZ = mountain.radiusZ() + edgeOffset;
                int x = mountain.centerX() + (int) Math.round(Math.cos(angle) * radiusX);
                int z = mountain.centerZ() + (int) Math.round(Math.sin(angle) * radiusZ);
                BlockPos candidate = new BlockPos(x, 0, z);

                if (isSpacedFromAll(candidate, groupCenters, GROUP_CENTER_MIN_SPACING)) {
                    groupCenters.add(candidate);
                    break;
                }
            }
        }

        List<List<BlockPos>> groves = new ArrayList<>(groupCenters.size());
        List<BlockPos> allRoots = new ArrayList<>(groupCenters.size() * 3);

        for (int group = 0; group < groupCenters.size(); group++) {
            BlockPos center = groupCenters.get(group);
            List<BlockPos> roots = new ArrayList<>(3);

            for (int tree = 0; tree < 3; tree++) {
                for (int attempt = 0; attempt < TREE_POSITION_ATTEMPTS; attempt++) {
                    long hash = positionHash(mountain, group, tree, attempt);
                    double angle = unit(hash >>> 8) * Math.PI * 2.0D;
                    int offset = 1
                        + (int) Long.remainderUnsigned(hash >>> 24, TREE_ROOT_MAX_OFFSET);
                    int x = center.getX() + (int) Math.round(Math.cos(angle) * offset);
                    int z = center.getZ() + (int) Math.round(Math.sin(angle) * offset);
                    BlockPos candidate = new BlockPos(x, 0, z);

                    if (!isSpacedFromAll(candidate, roots, TREE_ROOT_MIN_SPACING)
                        || !isSpacedFromAll(candidate, allRoots, TREE_ROOT_GROUP_SPACING)) {
                        continue;
                    }

                    roots.add(candidate);
                    break;
                }
            }

            if (roots.size() == 3) {
                groves.add(roots);
                allRoots.addAll(roots);
            }
        }

        return groves;
    }

    private static long positionHash(
        EndPeaksWorldgen.Mountain mountain,
        int group,
        int tree,
        int attempt
    ) {
        int x = (int) (mountain.hash() >>> 32)
            + group * 0x9E3779B9
            + (tree + 2) * 0x85EBCA6B
            + attempt * 0xC2B2AE35;
        int z = (int) mountain.hash()
            ^ group * 0x27D4EB2D
            ^ (tree + 3) * 0x165667B1
            ^ attempt * 0xD3A2646C;
        return EndPeaksWorldgen.mix(x, z);
    }

    private static double unit(long value) {
        return Long.remainderUnsigned(value, 1_000_003L) / 1_000_003.0D;
    }

    private static boolean isSpacedFromAll(
        BlockPos candidate,
        List<BlockPos> positions,
        int minimumDistance
    ) {
        int minimumDistanceSquared = minimumDistance * minimumDistance;
        for (BlockPos position : positions) {
            int dx = candidate.getX() - position.getX();
            int dz = candidate.getZ() - position.getZ();
            if (dx * dx + dz * dz < minimumDistanceSquared) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNearPlannedMountain(int x, int z) {
        int mountainSearchRadius =
            GROUND_PLANT_EXCLUSION_MARGIN + EndPeaksWorldgen.MAX_RADIUS;
        int minCellX = EndPeaksWorldgen.minCellForCenterRange(x, mountainSearchRadius);
        int maxCellX = EndPeaksWorldgen.maxCellForCenterRange(x, mountainSearchRadius);
        int minCellZ = EndPeaksWorldgen.minCellForCenterRange(z, mountainSearchRadius);
        int maxCellZ = EndPeaksWorldgen.maxCellForCenterRange(z, mountainSearchRadius);

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
