/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.gameevent.GameEvent
 */
package com.eternal_creations.archies_dino_mod.common;

import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.level.gameevent.GameEvent;

public class ADGameEvents {
    public static final ResourceSupplier<GameEvent> PUS_ARROW_AGGRO = Services.COMMON.registerGameEvent("pus_arrow_aggro", 64);
    public static final ResourceSupplier<GameEvent> SCULKSAUR_ARMOR_DISTRACTION = null;
    public static final ResourceSupplier<GameEvent> TRACKER_AGGRO = null;
    public static final ResourceSupplier<GameEvent> SCULKRAPTOR_NEST_AGGRO = null;

    public static void init() {
    }
}

