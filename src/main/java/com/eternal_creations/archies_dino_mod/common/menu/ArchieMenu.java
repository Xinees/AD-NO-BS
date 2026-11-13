/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.item.ItemStack
 */
package com.eternal_creations.archies_dino_mod.common.menu;

import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchieMenu
extends AbstractContainerMenu {
    private final Inventory inventory;

    public ArchieMenu(int containerId, Inventory inventory) {
        super(ADMenus.ARCHIE.get(), containerId);
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public ItemStack m_7648_(Player player, int index) {
        return ItemStack.f_41583_;
    }

    public boolean m_6875_(Player player) {
        return true;
    }
}

