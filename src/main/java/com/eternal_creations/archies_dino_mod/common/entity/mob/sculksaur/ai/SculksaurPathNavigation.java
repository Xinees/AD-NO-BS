/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.function.Condition
 *  com.mojang.blaze3d.MethodsReturnNonnullByDefault
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.core.Direction
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.Node
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 *  net.minecraft.world.phys.AABB
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai;

import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.NavigationType;
import com.eternal_creations.archies_dino_mod.common.entity.ai.control.pathfinder.SmoothPathNavigation;
import com.eternal_creations.archies_dino_mod.mixin.sculksaur.WalkNodeEvaluatorAccessor;
import com.mojang.blaze3d.MethodsReturnNonnullByDefault;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurPathNavigation
extends SmoothPathNavigation {
    private static final IgnoreWallsNavigation IGNORE_WALLS_NAVIGATION = new IgnoreWallsNavigation();

    public SculksaurPathNavigation(Mob mob, Level level, Condition useAlternative) {
        super(mob, level, NavigationType.GROUND, Set.of(IGNORE_WALLS_NAVIGATION), () -> {
            if (useAlternative.check()) {
                return IGNORE_WALLS_NAVIGATION;
            }
            return NavigationType.GROUND;
        });
    }

    static class IgnoreWallsNavigation
    extends NavigationType.GroundNavigation {
        IgnoreWallsNavigation() {
        }

        @Override
        public NodeEvaluator createEvaluator() {
            return new WalkNodeEvaluator(){

                public boolean m_77634_(AABB boundingBox) {
                    return false;
                }

                @Nullable
                protected Node m_164725_(int x, int y, int z, int verticalDeltaLimit, double nodeFloorLevel, Direction direction, BlockPathTypes pathType) {
                    ((WalkNodeEvaluatorAccessor)((Object)this)).getCollisionCache().clear();
                    return super.m_164725_(x, y, z, verticalDeltaLimit, nodeFloorLevel, direction, pathType);
                }
            };
        }
    }
}

