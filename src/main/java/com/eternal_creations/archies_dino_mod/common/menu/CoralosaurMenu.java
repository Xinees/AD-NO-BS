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
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package com.eternal_creations.archies_dino_mod.common.menu;

import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import com.eternal_creations.archies_dino_mod.common.menu.DinoMenu;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoralosaurMenu
extends DinoMenu {
    public CoralosaurMenu(int containerId, Inventory inventory) {
        CoralosaurEntity entityCoralosaur;
        Entity entity = inventory.f_35978_.m_20202_();
        this(containerId, inventory, entity instanceof CoralosaurEntity ? (entityCoralosaur = (CoralosaurEntity)entity) : null);
    }

    public CoralosaurMenu(int containerId, Inventory playerInventory, @Nullable CoralosaurEntity coralosaur) {
        super(ADMenus.CORALOSAUR.get(), containerId, (Container)(coralosaur != null ? coralosaur.getInventory() : new SimpleContainer(9)), coralosaur);
        if (coralosaur == null) {
            return;
        }
        this.container.m_5856_(playerInventory.f_35978_);
        this.m_38897_(new DinoMenu.SaddleSlot(this.container, 0, 80, 70));
        this.m_38897_(new ChestSlot(this, 1, 44, 16));
        this.m_38897_(new ChestSlot(this, 2, 44, 34));
        this.m_38897_(new ChestSlot(this, 3, 44, 52));
        this.m_38897_(new ChestSlot(this, 4, 44, 70));
        this.m_38897_(new ChestSlot(this, 5, 116, 16));
        this.m_38897_(new ChestSlot(this, 6, 116, 34));
        this.m_38897_(new ChestSlot(this, 7, 116, 52));
        this.m_38897_(new ChestSlot(this, 8, 116, 70));
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.m_38897_(new Slot((Container)playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 106 + i1 * 18));
            }
        }
        for (int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)playerInventory, j1, 8 + j1 * 18, 164));
        }
    }

    @Override
    public ItemStack m_7648_(Player player, int index) {
        Slot slot = (Slot)this.f_38839_.get(index);
        if (!slot.m_6657_()) {
            return ItemStack.f_41583_;
        }
        ItemStack stack = slot.m_7993_();
        ItemStack original = stack.m_41777_();
        int containerEnd = this.container.m_6643_();
        int hotbarStart = containerEnd + 27;
        int hotbarEnd = hotbarStart + 9;
        if (index < containerEnd) {
            if (!this.m_38903_(stack, containerEnd, hotbarEnd, true)) {
                return ItemStack.f_41583_;
            }
        } else {
            boolean moved = false;
            if (!this.m_38853_(0).m_6657_() && this.m_38853_(0).m_5857_(stack)) {
                moved = this.m_38903_(stack, 0, 1, false);
            } else if (this.m_38853_(1).m_6659_()) {
                moved = this.m_38903_(stack, 1, containerEnd, false);
            }
            if (!moved) {
                moved = index < hotbarStart ? this.m_38903_(stack, hotbarStart, hotbarEnd, false) : this.m_38903_(stack, containerEnd, hotbarStart, false);
            }
            if (!moved) {
                return ItemStack.f_41583_;
            }
        }
        if (stack.m_41619_()) {
            slot.m_269060_(ItemStack.f_41583_);
        } else {
            slot.m_6654_();
        }
        if (stack.m_41613_() == original.m_41613_()) {
            return ItemStack.f_41583_;
        }
        slot.m_142406_(player, stack);
        return original;
    }

    public static class ChestSlot
    extends Slot {
        private final CoralosaurMenu menu;

        public ChestSlot(CoralosaurMenu menu, int slot, int x, int y) {
            super(menu.container, slot, x, y);
            this.menu = menu;
        }

        public boolean m_6659_() {
            CoralosaurEntity coralosaur;
            AbstractComplexMount abstractComplexMount = this.menu.mount;
            return abstractComplexMount instanceof CoralosaurEntity && (coralosaur = (CoralosaurEntity)abstractComplexMount).m_6254_();
        }

        public int m_6641_() {
            return 1;
        }

        public int m_5866_(ItemStack stack) {
            return 1;
        }
    }
}

