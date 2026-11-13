/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.util;

import javax.annotation.Nullable;
import net.minecraft.world.phys.Vec3;

public interface iPlayerWithMace {
    public void archies_Dino_Mod$clearCurrentExplosion();

    public void archies_Dino_Mod$tryClearCurrentExplosion();

    @Nullable
    public Vec3 archies_Dino_Mod$currentExplosionImpactPos();

    public void archies_Dino_Mod$setCurrentExplosionImpactPos(Vec3 var1);

    public boolean archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion();

    public void archies_Dino_Mod$setIgnoreFallDamageFromCurrentExplosion(boolean var1);

    public void archies_Dino_Mod$setSpawnExtraParticlesOnFall(boolean var1);

    public boolean archies_Dino_Mod$spawnExtraParticlesOnFall();
}

