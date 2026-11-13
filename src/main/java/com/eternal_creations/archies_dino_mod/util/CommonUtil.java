/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.phys.AABB
 */
package com.eternal_creations.archies_dino_mod.util;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CommonUtil {
    public static void checkWalls(ServerLevel level, AABB area, boolean toWater) {
        CommonUtil.checkWalls(level, area, toWater, null);
    }

    public static void checkWalls(ServerLevel level, AABB area, boolean toWater, @Nullable TagKey<Block> tag) {
        int minX = Mth.m_14107_((double)area.f_82288_);
        int minY = Mth.m_14107_((double)area.f_82289_);
        int minZ = Mth.m_14107_((double)area.f_82290_);
        int maxX = Mth.m_14107_((double)area.f_82291_);
        int maxY = Mth.m_14107_((double)area.f_82292_);
        int manZ = Mth.m_14107_((double)area.f_82293_);
        boolean worked = false;
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= manZ; ++z) {
                    BlockPos pos = new BlockPos(x, y - 1, z);
                    BlockState state = level.m_8055_(pos);
                    if (tag != null && !state.m_204336_(tag) || state.m_60795_() || state.m_204336_(BlockTags.f_215822_) || !level.m_46469_().m_46207_(GameRules.f_46132_) || state.m_204336_(BlockTags.f_13069_)) continue;
                    worked = CommonUtil.removeBlock(level, pos, toWater) || worked;
                }
            }
        }
        if (worked) {
            BlockPos pos = new BlockPos(minX + level.f_46441_.m_188503_(maxX - minX + 1), minY + level.f_46441_.m_188503_(maxY - minY + 1), minZ + level.f_46441_.m_188503_(manZ - minZ + 1));
            level.m_46796_(2008, pos, 0);
        }
    }

    public static boolean removeBlock(ServerLevel level, BlockPos pos, boolean toWater) {
        return toWater ? level.m_7731_(pos, Fluids.f_76193_.m_76145_().m_76188_(), 3) : level.m_7471_(pos, false);
    }
}

