package com.eternal_creations.archies_dino_mod.common.tab;

import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ADCreativeTabs {
    public static final ResourceSupplier<CreativeModeTab> MOD_TAB = Services.COMMON.registerCreativeTab(
        "items", () -> new ItemStack((ItemLike) ADItems.LUSH_CORE.get()), (parameters, output) -> {
            output.m_246326_((ItemLike) ADItems.LUSH_CORE.get());
            output.m_246326_((ItemLike) ADItems.CHORUS_FOSSIL.get());
            output.m_246326_((ItemLike) ADItems.SCULK_TOXIN.get());
            output.m_246326_((ItemLike) ADItems.LUSHSAUR_MACE.get());
            output.m_246326_((ItemLike) ADItems.OOZE_TIPPED_ARROW.get());
        });

    public static void init() { }
}
