/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.lushsaur.ai;

public enum LushsaurState {
    IDLE,
    CHARGING,
    SITTING,
    BURIED;

    private static final LushsaurState[] VALUES;

    public static LushsaurState byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return IDLE;
        }
        return VALUES[id];
    }

    static {
        VALUES = LushsaurState.values();
    }
}

