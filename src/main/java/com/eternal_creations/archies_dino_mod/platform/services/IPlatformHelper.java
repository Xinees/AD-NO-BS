/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.platform.services;

import java.nio.file.Path;

public interface IPlatformHelper {
    public String getPlatformName();

    public boolean isModLoaded(String var1);

    public boolean isDevelopmentEnvironment();

    default public String getEnvironmentName() {
        return this.isDevelopmentEnvironment() ? "development" : "production";
    }

    public Path getConfigDir();
}

