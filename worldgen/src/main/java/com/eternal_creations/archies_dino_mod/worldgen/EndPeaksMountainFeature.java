package com.eternal_creations.archies_dino_mod.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

import java.util.HashSet;
import java.util.Set;

public final class EndPeaksMountainFeature extends Feature<NoneFeatureConfiguration> {
    private static final int BIOME_SAMPLE_Y = 64;
    private static final int ISLAND_EDGE_MARGIN = 10;
    private static final int STRUCTURE_START_SEARCH_MARGIN = 4;
    private static final int ISLAND_EDGE_SAMPLE_COUNT = 32;

    public EndPeaksMountainFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        if (!Level.END.equals(level.getLevel().dimension())) {
            return false;
        }

        WorldGenRegion region = (WorldGenRegion) level;
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

                int summitBaseY = getSummitBaseY(level, mountain);
                boolean flatSummit = summitBaseY > level.getMinBuildHeight() + 1
                    && summitBaseY + mountain.height() - 1 < level.getMaxBuildHeight();
                if (hasStructureInMountainPath(
                    level,
                    region,
                    mountain,
                    summitBaseY,
                    flatSummit
                )) {
                    continue;
                }

                placed |= placeMountain(
                    level,
                    mountain,
                    mutable,
                    summitBaseY,
                    flatSummit
                );
            }
        }

        return placed;
    }

    private static boolean placeMountain(
        WorldGenLevel level,
        EndPeaksWorldgen.Mountain mountain,
        MutableBlockPos mutable,
        int summitBaseY,
        boolean flatSummit
    ) {
        int summitTopY = flatSummit
            ? summitBaseY + mountain.height() - 1
            : -1;
        int minX = mountain.centerX() - mountain.radiusX();
        int maxX = mountain.centerX() + mountain.radiusX();
        int minZ = mountain.centerZ() - mountain.radiusZ();
        int maxZ = mountain.centerZ() + mountain.radiusZ();

        boolean placed = false;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                int rise = getRiseAt(mountain, x, z);
                if (rise < 1) {
                    continue;
                }

                int groundY = getGroundY(level, x, z);
                if (!isValidMountainColumn(level, x, z, groundY)) {
                    continue;
                }

                boolean summitBlock = flatSummit
                    && isSummitBlock(
                        mountain,
                        x - mountain.centerX(),
                        z - mountain.centerZ()
                    );
                int topY = summitBlock
                    ? summitTopY
                    : Math.min(
                        groundY + rise - 1,
                        level.getMaxBuildHeight() - 1
                    );
                if (flatSummit && !summitBlock) {
                    topY = Math.min(topY, summitTopY - 1);
                }
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

    private static int getSummitBaseY(
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
                if (!isSummitBlock(
                    mountain,
                    x - mountain.centerX(),
                    z - mountain.centerZ()
                )) {
                    continue;
                }

                int groundY = getGroundY(level, x, z);
                if (!isValidMountainColumn(level, x, z, groundY)) {
                    return -1;
                }

                baseY = Math.max(baseY, groundY);
            }
        }

        return baseY == Integer.MIN_VALUE ? -1 : baseY;
    }

    private static int getGroundY(WorldGenLevel level, int x, int z) {
        return level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
    }

    private static boolean isValidMountainColumn(
        WorldGenLevel level,
        int x,
        int z,
        int groundY
    ) {
        if (groundY <= level.getMinBuildHeight() + 1
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
        int expandedRadiusX = mountain.radiusX() + ISLAND_EDGE_MARGIN;
        int expandedRadiusZ = mountain.radiusZ() + ISLAND_EDGE_MARGIN;
        Set<Long> checkedQuartCells = new HashSet<>();
        MutableBlockPos samplePos = new MutableBlockPos();

        for (int x = mountain.centerX() - expandedRadiusX;
             x <= mountain.centerX() + expandedRadiusX; x++) {
            for (int z = mountain.centerZ() - expandedRadiusZ;
                 z <= mountain.centerZ() + expandedRadiusZ; z++) {
                double dx = (x - mountain.centerX()) / (double) expandedRadiusX;
                double dz = (z - mountain.centerZ()) / (double) expandedRadiusZ;
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

        for (int x = mountain.centerX() - mountain.radiusX();
             x <= mountain.centerX() + mountain.radiusX(); x++) {
            for (int z = mountain.centerZ() - mountain.radiusZ();
                 z <= mountain.centerZ() + mountain.radiusZ(); z++) {
                double dx = (x - mountain.centerX()) / (double) mountain.radiusX();
                double dz = (z - mountain.centerZ()) / (double) mountain.radiusZ();
                if (dx * dx + dz * dz >= 1.0D) {
                    continue;
                }

                int groundY = getGroundY(level, x, z);
                if (groundY <= level.getMinBuildHeight() + 1
                    || !level.getBlockState(new BlockPos(x, groundY - 1, z)).is(Blocks.END_STONE)) {
                    return false;
                }
            }
        }

        for (int sample = 0; sample < ISLAND_EDGE_SAMPLE_COUNT; sample++) {
            double angle = Math.PI * 2.0D * sample / ISLAND_EDGE_SAMPLE_COUNT;
            int x = mountain.centerX()
                + (int) Math.round(Math.cos(angle) * expandedRadiusX);
            int z = mountain.centerZ()
                + (int) Math.round(Math.sin(angle) * expandedRadiusZ);
            int groundY = getGroundY(level, x, z);
            if (groundY <= level.getMinBuildHeight() + 1
                || !level.getBlockState(new BlockPos(x, groundY - 1, z)).is(Blocks.END_STONE)
                || !level.getBiome(new BlockPos(x, BIOME_SAMPLE_Y, z)).is(EndPeaksWorldgen.BIOME)) {
                return false;
            }
        }

        return !checkedQuartCells.isEmpty();
    }

    private static boolean hasStructureInMountainPath(
        WorldGenLevel level,
        WorldGenRegion region,
        EndPeaksWorldgen.Mountain mountain,
        int summitBaseY,
        boolean flatSummit
    ) {
        if (!level.getLevel().structureManager().shouldGenerateStructures()) {
            return false;
        }

        int minX = mountain.centerX() - mountain.radiusX();
        int maxX = mountain.centerX() + mountain.radiusX();
        int minZ = mountain.centerZ() - mountain.radiusZ();
        int maxZ = mountain.centerZ() + mountain.radiusZ();
        int summitTopY = flatSummit
            ? summitBaseY + mountain.height() - 1
            : -1;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                int rise = getRiseAt(mountain, x, z);
                if (rise < 1) {
                    continue;
                }

                int groundY = getGroundY(level, x, z);
                if (groundY <= level.getMinBuildHeight() + 1
                    || !level.getBlockState(new BlockPos(x, groundY - 1, z)).is(Blocks.END_STONE)) {
                    continue;
                }

                int topY = groundY + rise - 1;
                boolean summitBlock = flatSummit
                    && isSummitBlock(
                        mountain,
                        x - mountain.centerX(),
                        z - mountain.centerZ()
                    );
                if (summitBlock) {
                    topY = summitTopY;
                } else if (flatSummit) {
                    topY = Math.min(topY, summitTopY - 1);
                }

                topY = Math.min(topY, level.getMaxBuildHeight() - 1);
                minY = Math.min(minY, groundY);
                maxY = Math.max(maxY, topY);
            }
        }

        if (minY == Integer.MAX_VALUE || maxY < minY) {
            return false;
        }

        BoundingBox mountainBounds = new BoundingBox(
            minX,
            minY,
            minZ,
            maxX,
            maxY,
            maxZ
        );
        StructureManager structureManager =
            level.getLevel().structureManager().forWorldGenRegion(region);
        Set<StructureStart> inspectedStarts = new HashSet<>();

        int minChunkX = Math.floorDiv(minX, 16);
        int maxChunkX = Math.floorDiv(maxX, 16);
        int minChunkZ = Math.floorDiv(minZ, 16);
        int maxChunkZ = Math.floorDiv(maxZ, 16);

        for (int chunkX = minChunkX - STRUCTURE_START_SEARCH_MARGIN;
             chunkX <= maxChunkX + STRUCTURE_START_SEARCH_MARGIN; chunkX++) {
            for (int chunkZ = minChunkZ - STRUCTURE_START_SEARCH_MARGIN;
                 chunkZ <= maxChunkZ + STRUCTURE_START_SEARCH_MARGIN; chunkZ++) {
                for (StructureStart start : structureManager.startsForStructure(
                    new ChunkPos(chunkX, chunkZ),
                    structure -> true
                )) {
                    if (!start.isValid()
                        || !inspectedStarts.add(start)
                        || !start.getBoundingBox().intersects(mountainBounds)) {
                        continue;
                    }

                    for (StructurePiece piece : start.getPieces()) {
                        BoundingBox pieceBounds = piece.getBoundingBox();
                        if (!pieceBounds.intersects(mountainBounds)) {
                            continue;
                        }

                        int pieceMinX = Math.max(minX, pieceBounds.minX());
                        int pieceMaxX = Math.min(maxX, pieceBounds.maxX());
                        int pieceMinZ = Math.max(minZ, pieceBounds.minZ());
                        int pieceMaxZ = Math.min(maxZ, pieceBounds.maxZ());

                        for (int x = pieceMinX; x <= pieceMaxX; x++) {
                            for (int z = pieceMinZ; z <= pieceMaxZ; z++) {
                                double dx = (x - mountain.centerX()) / (double) mountain.radiusX();
                                double dz = (z - mountain.centerZ()) / (double) mountain.radiusZ();
                                if (dx * dx + dz * dz >= 1.0D) {
                                    continue;
                                }

                                int rise = getRiseAt(mountain, x, z);
                                if (rise < 1) {
                                    continue;
                                }

                                int groundY = getGroundY(level, x, z);
                                int columnTopY = groundY + rise - 1;
                                boolean summitBlock = flatSummit
                                    && isSummitBlock(
                                        mountain,
                                        x - mountain.centerX(),
                                        z - mountain.centerZ()
                                    );
                                if (summitBlock) {
                                    columnTopY = summitTopY;
                                } else if (flatSummit) {
                                    columnTopY = Math.min(columnTopY, summitTopY - 1);
                                }

                                if (pieceBounds.minY() <= columnTopY
                                    && pieceBounds.maxY() >= groundY) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private static int getRiseAt(EndPeaksWorldgen.Mountain mountain, int x, int z) {
        double distance = getNormalizedDistance(
            mountain,
            x - mountain.centerX(),
            z - mountain.centerZ()
        );
        if (distance >= 1.0D) {
            return 0;
        }

        double profile = 1.0D;
        if (distance > mountain.plateauRadius()) {
            double slope = (1.0D - distance) / (1.0D - mountain.plateauRadius());
            profile = Math.pow(slope, 0.72D);
        }

        int rise = (int) Math.floor(mountain.height() * profile);
        return rise < 1 ? 0 : Math.min(mountain.height(), rise);
    }

    private static boolean isSummitBlock(
        EndPeaksWorldgen.Mountain mountain,
        int offsetX,
        int offsetZ
    ) {
        return getNormalizedDistance(mountain, offsetX, offsetZ) <= mountain.plateauRadius();
    }

    private static double getNormalizedDistance(
        EndPeaksWorldgen.Mountain mountain,
        int offsetX,
        int offsetZ
    ) {
        double dx = offsetX / (double) mountain.radiusX();
        double dz = offsetZ / (double) mountain.radiusZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        double angle = Math.atan2(dz, dx);
        double phaseA = ((mountain.hash() >>> 40) & 0xFFFFL) / 65536.0D * Math.PI * 2.0D;
        double phaseB = ((mountain.hash() >>> 24) & 0xFFFFL) / 65536.0D * Math.PI * 2.0D;
        double phaseC = ((mountain.hash() >>> 8) & 0xFFFFL) / 65536.0D * Math.PI * 2.0D;
        double ridgeShape = 1.0D
            + Math.sin(angle * 3.0D + phaseA) * 0.075D
            + Math.cos(angle * 5.0D + phaseB) * 0.035D
            + Math.sin(angle * 7.0D + phaseC) * 0.018D;
        return distance / ridgeShape;
    }
}
