/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package com.eternal_creations.archies_dino_mod.util;

import net.minecraft.world.entity.Entity;

public interface IBoatWithLead {
    public boolean archies_Dino_Mod$isLeashed();

    public Entity archies_Dino_Mod$getLeashHolder();

    public void archies_Dino_Mod$setLeashedTo(Entity var1, boolean var2);

    public void archies_Dino_Mod$setDelayedLeashHolderId(int var1);
}

