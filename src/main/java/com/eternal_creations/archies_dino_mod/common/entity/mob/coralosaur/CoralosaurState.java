/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur;

public enum CoralosaurState {
    IDLE,
    CHARGING,
    SITTING,
    BURIED;

    private static final CoralosaurState[] VALUES;

    public static CoralosaurState byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return IDLE;
        }
        return VALUES[id];
    }

    static {
        VALUES = CoralosaurState.values();
    }
}

