/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai;

public enum ChorusoatlState {
    IDLE,
    FLYING,
    FALLING_DEAD,
    SITTING;

    private static final ChorusoatlState[] VALUES;

    public static ChorusoatlState byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return IDLE;
        }
        return VALUES[id];
    }

    static {
        VALUES = ChorusoatlState.values();
    }
}

