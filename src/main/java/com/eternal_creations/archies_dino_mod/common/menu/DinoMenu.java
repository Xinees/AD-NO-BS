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
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.MenuType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 */
package com.eternal_creations.archies_dino_mod.common.menu;

import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.menu.ADMenus;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DinoMenu
extends AbstractContainerMenu {
    protected final Container container;
    @Nullable
    protected final AbstractComplexMount mount;

    protected DinoMenu(@Nullable MenuType<?> menuType, int containerId, Container container, @Nullable AbstractComplexMount mount) {
        super(menuType, containerId);
        this.container = container;
        this.mount = mount;
    }

    public DinoMenu(int containerId, Inventory inventory) {
        AbstractComplexMount entityCoralosaur;
        Entity entity = inventory.f_35978_.m_20202_();
        this(containerId, inventory, entity instanceof AbstractComplexMount ? (entityCoralosaur = (AbstractComplexMount)entity) : null);
    }

    public DinoMenu(int containerId, Inventory playerInventory, @Nullable AbstractComplexMount mount) {
        this(ADMenus.DINO.get(), containerId, (Container)(mount == null ? new SimpleContainer(9) : mount.getInventory()), mount);
        if (mount == null) {
            return;
        }
        this.container.m_5856_(playerInventory.f_35978_);
        this.m_38897_(new SaddleSlot(this.container, 0, 80, 70));
        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.m_38897_(new Slot((Container)playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 106 + i1 * 18));
            }
        }
        for (int j1 = 0; j1 < 9; ++j1) {
            this.m_38897_(new Slot((Container)playerInventory, j1, 8 + j1 * 18, 164));
        }
    }

    public boolean m_6875_(Player player) {
        if (this.mount == null) {
            return false;
        }
        return this.container.m_6542_(player) && this.mount.m_6084_() && this.mount.m_20270_((Entity)player) < 8.0f;
    }

    public ItemStack m_7648_(Player player, int index) {
        ItemStack itemstack = ItemStack.f_41583_;
        Slot slot = (Slot)this.f_38839_.get(index);
        if (slot.m_6657_()) {
            ItemStack itemstack1 = slot.m_7993_();
            itemstack = itemstack1.m_41777_();
            int i = this.container.m_6643_();
            if (index < i) {
                if (!this.m_38903_(itemstack1, i, this.f_38839_.size(), true)) {
                    return ItemStack.f_41583_;
                }
            } else if (this.m_38853_(1).m_5857_(itemstack1) && !this.m_38853_(1).m_6657_()) {
                if (!this.m_38903_(itemstack1, 1, 2, false)) {
                    return ItemStack.f_41583_;
                }
            } else if (this.m_38853_(0).m_5857_(itemstack1)) {
                if (!this.m_38903_(itemstack1, 0, 1, false)) {
                    return ItemStack.f_41583_;
                }
            } else if (i <= 2 || !this.m_38903_(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k ? !this.m_38903_(itemstack1, i, j, false) : (index >= i && index < j ? !this.m_38903_(itemstack1, j, k, false) : !this.m_38903_(itemstack1, j, j, false))) {
                    return ItemStack.f_41583_;
                }
                return ItemStack.f_41583_;
            }
            if (itemstack1.m_41619_()) {
                slot.m_269060_(ItemStack.f_41583_);
            } else {
                slot.m_6654_();
            }
        }
        return itemstack;
    }

    public void m_6877_(Player player) {
        super.m_6877_(player);
        this.container.m_5785_(player);
    }

    public static class SaddleSlot
    extends Slot {
        public SaddleSlot(Container container, int slot, int x, int y) {
            super(container, slot, x, y);
        }

        public boolean m_5857_(ItemStack stack) {
            return stack.m_150930_(Items.f_42450_);
        }

        public int m_6641_() {
            return 1;
        }
    }
}

