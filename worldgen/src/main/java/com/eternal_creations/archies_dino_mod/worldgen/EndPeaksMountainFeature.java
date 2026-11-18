package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


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
        int chunkMaxX = chunkMinX + 15;
        int chunkMaxZ = chunkMinZ + 15;
        int minCellX = EndPeaksWorldgen.minCellForCenterRange(chunkMinX, 0);
        int maxCellX = EndPeaksWorldgen.maxCellForCenterRange(chunkMaxX, 0);
        int minCellZ = EndPeaksWorldgen.minCellForCenterRange(chunkMinZ, 0);
        int maxCellZ = EndPeaksWorldgen.maxCellForCenterRange(chunkMaxZ, 0);

        boolean placed = false;
        MutableBlockPos mutable = new MutableBlockPos();

        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                EndPeaksWorldgen.Mountain mountain = EndPeaksWorldgen.mountainAt(cellX, cellZ);
                if (mountain == null
                    || Math.floorDiv(mountain.centerX(), 16) != Math.floorDiv(chunkMinX, 16)
                    || Math.floorDiv(mountain.centerZ(), 16) != Math.floorDiv(chunkMinZ, 16)
                    || !isMountainContainedInBiome(level, mountain)) {
                    continue;
                }

                placed |= placeMountain(level, mountain, mutable);
            }
        }

        return placed;
    }

    private static boolean placeMountain(
        WorldGenLevel level,
        EndPeaksWorldgen.Mountain mountain,
        MutableBlockPos mutable
    ) {
        int summitBaseY = getMountainBaseY(level, mountain);
        boolean hasFlatSummit = summitBaseY > level.getMinBuildHeight() + 1
            && isSummitCoreValid(level, mountain);
        if (summitBaseY <= level.getMinBuildHeight() + 1
            || summitBaseY + mountain.height() - 1 >= level.getMaxBuildHeight()) {
            return false;
        }

        int summitTopY = summitBaseY + mountain.height() - 1;
        int minX = mountain.centerX() - mountain.radiusX();
        int maxX = mountain.centerX() + mountain.radiusX();
        int minZ = mountain.centerZ() - mountain.radiusZ();
        int maxZ = mountain.centerZ() + mountain.radiusZ();

        boolean placed = false;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                int rise = getRiseAt(mountain, x, z);
                if (rise < 1 || !isValidMountainColumn(level, x, z)) {
                    continue;
                }

                int groundY = getGroundY(level, x, z);
                boolean summitCore = hasFlatSummit
                    && isSummitCore(x - mountain.centerX(), z - mountain.centerZ());
                int topY = summitCore
                    ? summitTopY
                    : hasFlatSummit
                        ? Math.min(groundY + rise - 1, summitTopY - 1)
                        : groundY + rise - 1;
                if (topY < groundY) {
                    continue;
                }

                for (int y = groundY; y <= topY; y++) {
                    mutable.set(x, y, z);
                    BlockState current = level.getBlockState(mutable);
                    if (!current.isAir() && !current.canBeReplaced()) {
                        break;
                    }

                    level.setBlock(mutable, Blocks.END_STONE.defaultBlockState(), 2);
                    placed = true;
                }
            }
        }

        return placed;
    }

    private static boolean isSummitCoreValid(
        WorldGenLevel level,
        EndPeaksWorldgen.Mountain mountain
    ) {
        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetZ = -1; offsetZ <= 1; offsetZ++) {
                if (!isValidMountainColumn(
                    level,
                    mountain.centerX() + offsetX,
                    mountain.centerZ() + offsetZ
                )) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int getMountainBaseY(
        WorldGenLevel level,
        EndPeaksWorldgen.Mountain mountain
    ) {
        int baseY = Integer.MIN_VALUE;
        int minX = mountain.centerX() - mountain.radiusX();
        int maxX = mountain.centerX() + mountain.radiusX();
        int minZ = mountain.centerZ() - mountain.radiusZ();
        int maxZ = mountain.centerZ() + mountain.radiusZ();

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                if (getRiseAt(mountain, x, z) < 1) {
                    continue;
                }

                int groundY = getGroundY(level, x, z);
                if (groundY > level.getMinBuildHeight() + 1
                    && level.getBlockState(new BlockPos(x, groundY - 1, z)).is(Blocks.END_STONE)) {
                    baseY = Math.max(baseY, groundY);
                }
            }
        }

        return baseY == Integer.MIN_VALUE ? -1 : baseY;
    }

    private static int getGroundY(WorldGenLevel level, int x, int z) {
        return level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x, z);
    }

    private static boolean isValidMountainColumn(WorldGenLevel level, int x, int z) {
        int groundY = getGroundY(level, x, z);
        if (groundY <= level.getMinBuildHeight() + 1
            || groundY >= level.getMaxBuildHeight()
            || !level.getBiome(new BlockPos(x, BIOME_SAMPLE_Y, z)).is(EndPeaksWorldgen.BIOME)
            || !level.getBlockState(new BlockPos(x, groundY - 1, z)).is(Blocks.END_STONE)) {
            return false;
        }

        BlockState surface = level.getBlockState(new BlockPos(x, groundY, z));
        return surface.isAir() || surface.canBeReplaced();
    }

    static boolean isMountainContainedInBiome(
        WorldGenLevel level,
        EndPeaksWorldgen.Mountain mountain
    ) {
        int radiusX = mountain.radiusX() + 1;
        int radiusZ = mountain.radiusZ() + 1;
        int minQuartX = Math.floorDiv(mountain.centerX() - radiusX, 4);
        int maxQuartX = Math.floorDiv(mountain.centerX() + radiusX, 4);
        int minQuartZ = Math.floorDiv(mountain.centerZ() - radiusZ, 4);
        int maxQuartZ = Math.floorDiv(mountain.centerZ() + radiusZ, 4);

        for (int quartX = minQuartX; quartX <= maxQuartX; quartX++) {
            for (int quartZ = minQuartZ; quartZ <= maxQuartZ; quartZ++) {
                int sampleX = quartX * 4 + 2;
                int sampleZ = quartZ * 4 + 2;
                double dx = (sampleX - mountain.centerX()) / (double) radiusX;
                double dz = (sampleZ - mountain.centerZ()) / (double) radiusZ;
                if (dx * dx + dz * dz > 1.0D) {
                    continue;
                }

                if (!level.getBiome(new BlockPos(sampleX, BIOME_SAMPLE_Y, sampleZ))
                    .is(EndPeaksWorldgen.BIOME)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int getRiseAt(EndPeaksWorldgen.Mountain mountain, int x, int z) {
        int offsetX = x - mountain.centerX();
        int offsetZ = z - mountain.centerZ();
        if (isSummitCore(offsetX, offsetZ)) {
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

        int rise = (int) Math.floor(mountain.height() * profile);
        return rise < 1 ? 0 : Math.min(mountain.height() - 1, rise);
    }

    private static boolean isSummitCore(int offsetX, int offsetZ) {
        return Math.abs(offsetX) <= 1 && Math.abs(offsetZ) <= 1;
    }
}
