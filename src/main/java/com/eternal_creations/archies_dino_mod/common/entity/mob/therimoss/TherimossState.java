/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss;

public enum TherimossState {
    IDLE,
    FALLING,
    CLIMBING,
    SITTING;

    private static final TherimossState[] VALUES;

    public static TherimossState byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return IDLE;
        }
        return VALUES[id];
    }

    static {
        VALUES = TherimossState.values();
    }
}

