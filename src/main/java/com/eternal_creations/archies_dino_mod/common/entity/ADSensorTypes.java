/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.ai.sensing.SensorType
 */
package com.eternal_creations.archies_dino_mod.common.entity;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculkraptor.ai.SculkraptorEntitySensor;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntitySensor;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.entity.ai.sensing.SensorType;

public class ADSensorTypes {
    public static final ResourceSupplier<SensorType<SculksaurEntitySensor>> SCULKSAUR_SENSOR = Services.COMMON.registerSensorType("sculksaur_sensor", SculksaurEntitySensor::new);
    public static final ResourceSupplier<SensorType<SculkraptorEntitySensor>> SCULKRAPTOR_SENSOR = null;

    public static void init() {
    }
}

