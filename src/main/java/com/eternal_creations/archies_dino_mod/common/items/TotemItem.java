/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.ChatFormatting
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.items;

import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.block.TotemBlock;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieTrades;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TotemItem
extends BlockItem {
    protected final ResourceSupplier<? extends EntityType<?>> dinoType;

    public TotemItem(TotemBlock totem, Item.Properties properties) {
        super((Block)totem, properties);
        this.dinoType = totem.getDinoType();
    }

    public TotemBlock getBlock() {
        return (TotemBlock)super.m_40614_();
    }

    protected boolean m_40610_(BlockPlaceContext context, BlockState state) {
        Player player = context.m_43723_();
        if (player != null && player.m_7500_() || ArchieTrades.hasAdvancement(player, this.getBlock().getMastery())) {
            return super.m_40610_(context, state);
        }
        if (player != null) {
            player.m_5661_((Component)Component.m_237110_((String)ADStrings.Messages.TOTEM_CANT_PLACE, (Object[])new Object[]{this.dinoType.get().m_20676_()}).m_130940_(ChatFormatting.RED), true);
        }
        return false;
    }
}

