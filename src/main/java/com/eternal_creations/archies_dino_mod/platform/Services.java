/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.system.NonnullDefault
 */
package com.eternal_creations.archies_dino_mod.platform;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.platform.services.IClientHelper;
import com.eternal_creations.archies_dino_mod.platform.services.ICommonHelper;
import com.eternal_creations.archies_dino_mod.platform.services.INetworkHelper;
import com.eternal_creations.archies_dino_mod.platform.services.IPlatformHelper;
import java.util.ServiceLoader;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class Services {
    public static final IPlatformHelper PLATFORM = Services.load(IPlatformHelper.class);
    public static final IClientHelper CLIENT = Services.load(IClientHelper.class);
    public static final ICommonHelper COMMON = Services.load(ICommonHelper.class);
    public static final INetworkHelper NETWORK = Services.load(INetworkHelper.class);

    public static <T> T load(Class<T> clazz) {
        T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}

