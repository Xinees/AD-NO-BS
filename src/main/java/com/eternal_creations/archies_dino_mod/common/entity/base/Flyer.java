/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

public interface Flyer {
    public void setFlying(boolean var1);

    public boolean isFlying();

    public void setFlightPhase(FlightPhase var1);

    public FlightPhase getFlightPhase();

    default public boolean isPreparingToFly() {
        return this.getFlightPhase() == FlightPhase.PREPARING_TO_FLY;
    }

    default public boolean isPreparingToLand() {
        return this.getFlightPhase() == FlightPhase.PREPARING_TO_LAND;
    }

    public static enum FlightPhase {
        NONE,
        PREPARING_TO_FLY,
        PREPARING_TO_LAND;

    }
}

