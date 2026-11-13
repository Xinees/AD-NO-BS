/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.schedule.Activity
 */
package com.eternal_creations.archies_dino_mod.common.entity;

import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.entity.schedule.Activity;

public class ADActivities {
    public static final ResourceSupplier<Activity> CHASE = Services.COMMON.registerActivity("chase");
    public static final ResourceSupplier<Activity> SCREAM = null;
    public static final ResourceSupplier<Activity> FLEE = null;

    public static void init() {
    }
}

