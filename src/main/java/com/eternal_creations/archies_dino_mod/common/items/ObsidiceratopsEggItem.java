/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ObsidiceratopsEggItem
extends BlockItem {
    public ObsidiceratopsEggItem(Item.Properties properties) {
        super(ADBlocks.OBSIDICERATOPS_EGG.get(), properties);
    }

    public void m_6883_(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        Player player;
        if (level.f_46443_ || !(entity instanceof Player) || (player = (Player)entity).m_7500_() || player.m_5833_() || player.m_5825_()) {
            return;
        }
        if (level.f_46441_.m_188503_(80) == 0) {
            player.m_6469_(level.m_269111_().m_269387_(), 1.0f);
            player.m_7311_(5);
            level.m_6269_(null, (Entity)player, ADSoundEvents.OBSIDICERATOPS_EGG_SCORCH.get(), SoundSource.PLAYERS, 0.5f, 1.2f);
        }
    }
}

