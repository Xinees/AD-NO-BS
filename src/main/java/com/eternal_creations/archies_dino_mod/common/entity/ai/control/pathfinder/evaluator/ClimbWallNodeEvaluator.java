/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Plane
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.pathfinder.FlyNodeEvaluator
 *  net.minecraft.world.level.pathfinder.Node
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.evaluator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.FlyNodeEvaluator;
import net.minecraft.world.level.pathfinder.Node;
import org.jetbrains.annotations.Nullable;

public class ClimbWallNodeEvaluator
extends FlyNodeEvaluator {
    @Nullable
    protected Node m_262834_(int x, int y, int z) {
        Node node = super.m_262834_(x, y, z);
        if (node == null) {
            return null;
        }
        if (this.isAdjacentToSolidWall(x, y, z)) {
            return node;
        }
        return null;
    }

    private boolean isAdjacentToSolidWall(int x, int y, int z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            pos.m_122178_(x + direction.m_122429_(), y, z + direction.m_122431_());
            BlockState state = this.f_77312_.m_8055_((BlockPos)pos);
            if (!state.m_280555_() && !state.m_60804_((BlockGetter)this.f_77312_, (BlockPos)pos)) continue;
            return true;
        }
        pos.m_122178_(x, y - 1, z);
        BlockState floorState = this.f_77312_.m_8055_((BlockPos)pos);
        return floorState.m_280555_() || floorState.m_60804_((BlockGetter)this.f_77312_, (BlockPos)pos);
    }
}

