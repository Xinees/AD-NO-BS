/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2ObjectFunction
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import it.unimi.dsi.fastutil.ints.Int2ObjectFunction;

public interface StateDriven {
    public void setStateId(byte var1);

    public byte getStateId();

    default public void setState(Enum<?> state) {
        this.setStateId((byte)state.ordinal());
    }

    default public <T extends Enum<?>> T getState(Int2ObjectFunction<T> selector) {
        return (T)((Enum)selector.apply((int)this.getStateId()));
    }
}

