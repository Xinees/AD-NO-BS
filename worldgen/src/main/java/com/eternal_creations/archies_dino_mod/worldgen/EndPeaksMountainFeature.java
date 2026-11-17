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

public final class EndPeaksMountainFeature extends Feature<NoneFeatureConfiguration> {
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

        MutableBlockPos mutable = new MutableBlockPos();
        boolean placed = false;

        for (int cellX = minCellX; cellX <= maxCellX; cellX++) {
            for (int cellZ = minCellZ; cellZ <= maxCellZ; cellZ++) {
                EndPeaksWorldgen.Mountain mountain = EndPeaksWorldgen.mountainAt(cellX, cellZ);
                if (mountain == null) {
                    continue;
                }

                for (int x = chunkMinX; x < chunkMinX + 16; x++) {
                    for (int z = chunkMinZ; z < chunkMinZ + 16; z++) {
                        int groundY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                        if (groundY <= level.getMinBuildHeight() + 1
                            || !level.getBlockState(new BlockPos(x, groundY - 1, z)).is(Blocks.END_STONE)
                            || !level.getBiome(new BlockPos(x, groundY, z)).is(EndPeaksWorldgen.BIOME)) {
                            continue;
                        }

                        double dx = (x - mountain.centerX()) / (double) mountain.radiusX();
                        double dz = (z - mountain.centerZ()) / (double) mountain.radiusZ();
                        double distance = Math.sqrt(dx * dx + dz * dz);
                        if (distance >= 1.0D) {
                            continue;
                        }

                        double profile = 1.0D;
                        if (distance > mountain.plateauRadius()) {
                            double slope = (1.0D - distance) / (1.0D - mountain.plateauRadius());
                            profile = Math.pow(slope, 0.72D);
                        }

                        int rise = (int) Math.floor(mountain.height() * profile / 2.0D) * 2;
                        if (rise < 2) {
                            continue;
                        }

                        int topY = Math.min(groundY + rise, level.getMaxBuildHeight() - 1);
                        for (int y = groundY; y <= topY; y++) {
                            mutable.set(x, y, z);
                            if (level.getBlockState(mutable).isAir()) {
                                level.setBlock(mutable, Blocks.END_STONE.defaultBlockState(), 2);
                                placed = true;
                            }
                        }
                    }
                }
            }
        }

        return placed;
    }
}
