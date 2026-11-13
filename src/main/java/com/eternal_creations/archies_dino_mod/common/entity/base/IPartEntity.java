/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import net.minecraft.world.entity.Entity;

public interface IPartEntity<T extends Entity> {
    public T getParent();

    public void setPartId(int var1);

    public int getPartId();

    public <P extends Entity> P asPart();

    public void setPartPos(double var1, double var3, double var5);

    public void resize(float var1, float var2);
}

