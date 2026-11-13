/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai;

public enum ObsidiceratopsState {
    IDLE,
    CHARGING,
    SITTING;

    private static final ObsidiceratopsState[] VALUES;

    public static ObsidiceratopsState byId(int id) {
        if (id < 0 || id >= VALUES.length) {
            return IDLE;
        }
        return VALUES[id];
    }

    static {
        VALUES = ObsidiceratopsState.values();
    }
}

