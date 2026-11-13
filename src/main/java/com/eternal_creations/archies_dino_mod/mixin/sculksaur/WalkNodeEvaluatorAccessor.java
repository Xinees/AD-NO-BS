/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  net.minecraft.world.level.pathfinder.WalkNodeEvaluator
 *  net.minecraft.world.phys.AABB
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package com.eternal_creations.archies_dino_mod.mixin.sculksaur;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={WalkNodeEvaluator.class})
public interface WalkNodeEvaluatorAccessor {
    @Accessor(value="collisionCache")
    public Object2BooleanMap<AABB> getCollisionCache();
}

