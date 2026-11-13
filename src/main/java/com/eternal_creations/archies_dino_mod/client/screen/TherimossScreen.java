/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  org.joml.Quaternionf
 */
package com.eternal_creations.archies_dino_mod.client.screen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.DinoScreen;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.menu.TherimossMenu;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.joml.Quaternionf;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossScreen
extends AbstractContainerScreen<TherimossMenu> {
    private static final ResourceLocation HORSE_INVENTORY_LOCATION = Constants.prefix("textures/gui/therimoss.png");
    @Nullable
    protected final AbstractComplexMount mount;
    private final Quaternionf tempQuat;
    private float xMouse;
    private float yMouse;

    public TherimossScreen(TherimossMenu menu, Inventory playerInventory, Component name) {
        AbstractComplexMount entity;
        Entity entity2 = playerInventory.f_35978_.m_20202_();
        this(menu, playerInventory, entity2 instanceof AbstractComplexMount ? (entity = (AbstractComplexMount)entity2) : null);
    }

    public TherimossScreen(TherimossMenu menu, Inventory playerInventory, @Nullable AbstractComplexMount mount) {
        super((AbstractContainerMenu)menu, playerInventory, (Component)(mount != null ? mount.m_5446_() : Component.m_237119_()));
        this.tempQuat = new Quaternionf();
        this.mount = mount;
        this.f_97726_ = 176;
        this.f_97727_ = 188;
        this.f_97728_ = 8;
        this.f_97729_ = 6;
        this.f_97730_ = 8;
        this.f_97731_ = this.f_97727_ - 94;
    }

    protected void m_7286_(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.f_96543_ - this.f_97726_) / 2;
        int j = (this.f_96544_ - this.f_97727_) / 2;
        guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i, j, 0, 0, this.f_97726_, this.f_97727_);
        if (this.mount == null) {
            return;
        }
        DinoScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i + 88, j + 57, 6, this.xMouse, this.yMouse, (LivingEntity)this.mount, this.tempQuat);
        guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 70, j + 69, 0, this.f_97727_, 18, 18);
        guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 88, j + 69, 18, this.f_97727_, 18, 18);
    }

    public void m_88315_(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(guiGraphics);
        this.xMouse = mouseX;
        this.yMouse = mouseY;
        super.m_88315_(guiGraphics, mouseX, mouseY, partialTick);
        this.m_280072_(guiGraphics, mouseX, mouseY);
    }
}

