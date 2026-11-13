/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.item.ClampedItemPropertyFunction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.weapon.RibBowItem;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.List;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ADItemProperties {
    public static final List<Override<?>> OVERRIDES = List.of(new Override<RibBowItem>(ADItems.RIB_BOW, new ResourceLocation("pull"), (itemStack, clientLevel, livingEntity, i) -> {
        if (livingEntity == null) {
            return 0.0f;
        }
        return livingEntity.m_21211_() != itemStack ? 0.0f : (float)(itemStack.m_41779_() - livingEntity.m_21212_()) / 20.0f;
    }), new Override<RibBowItem>(ADItems.RIB_BOW, new ResourceLocation("pulling"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.m_6117_() && livingEntity.m_21211_() == itemStack ? 1.0f : 0.0f), new Override<Item>(ADItems.OBSIDIAN_HORN, new ResourceLocation("tooting"), (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.m_6117_() && livingEntity.m_21211_() == itemStack ? 1.0f : 0.0f));

    public record Override<T extends Item>(ResourceSupplier<T> item, ResourceLocation name, ClampedItemPropertyFunction property) {
    }
}

