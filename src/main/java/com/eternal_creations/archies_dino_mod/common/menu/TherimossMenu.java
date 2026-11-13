/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.Container
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package com.eternal_creations.archies_dino_mod.common.menu;

import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import com.eternal_creations.archies_dino_mod.common.menu.DinoMenu;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossMenu
extends DinoMenu {
    public TherimossMenu(int containerId, Inventory inventory) {
        TherimossEntity therimoss;
        Entity entity = inventory.f_35978_.m_20202_();
        this(containerId, inventory, entity instanceof TherimossEntity ? (therimoss = (TherimossEntity)entity) : null);
    }

    public TherimossMenu(int containerId, Inventory playerInventory, final @Nullable TherimossEntity therimoss) {
        super(ADMenus.THERIMOSS.get(), containerId, (Container)(therimoss != null ? therimoss.getInventory() : new SimpleContainer(2)), therimoss);
        if (therimoss == null) {
            return;
        }
        this.container.m_5856_(playerInventory.f_35978_);
        this.m_38897_(new DinoMenu.SaddleSlot(this.container, 0, 71, 70));
        this.m_38897_(new Slot(this.container, 1, 89, 70){

            public boolean m_5857_(ItemStack stack) {
                return stack.m_41720_() instanceof TherimossClawsItem && !this.m_6657_() && therimoss.m_6741_();
            }

            public boolean m_6659_() {
                return therimoss.m_6741_();
            }
        });
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.m_38897_(new Slot((Container)playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 106 + i1 * 18));
            }
        }
        for (int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)playerInventory, j1, 8 + j1 * 18, 164));
        }
    }
}

