/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.inventory.MenuType
 */
package com.eternal_creations.archies_dino_mod.common.menu;

import com.eternal_creations.archies_dino_mod.common.menu.ArchieMenu;
import com.eternal_creations.archies_dino_mod.common.menu.CoralosaurMenu;
import com.eternal_creations.archies_dino_mod.common.menu.DinoMenu;
import com.eternal_creations.archies_dino_mod.common.menu.TherimossMenu;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.inventory.MenuType;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADMenus {
    public static final ResourceSupplier<MenuType<ArchieMenu>> ARCHIE = Services.COMMON.registerMenu("archie", ArchieMenu::new);
    public static final ResourceSupplier<MenuType<DinoMenu>> DINO = Services.COMMON.registerMenu("dino", DinoMenu::new);
    public static final ResourceSupplier<MenuType<TherimossMenu>> THERIMOSS = Services.COMMON.registerMenu("therimoss", TherimossMenu::new);
    public static final ResourceSupplier<MenuType<CoralosaurMenu>> CORALOSAUR = Services.COMMON.registerMenu("coralosaur", CoralosaurMenu::new);

    public static void init() {
    }
}

