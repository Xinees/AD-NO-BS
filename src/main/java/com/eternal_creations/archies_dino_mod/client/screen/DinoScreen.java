/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.math.AMath
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 */
package com.eternal_creations.archies_dino_mod.client.screen;

import com.astemir.astemirlib.re.core.math.AMath;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMount;
import com.eternal_creations.archies_dino_mod.common.menu.DinoMenu;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DinoScreen
extends AbstractContainerScreen<DinoMenu> {
    private static final ResourceLocation HORSE_INVENTORY_LOCATION = Constants.prefix("textures/gui/dino.png");
    @Nullable
    protected final AbstractComplexMount mount;
    private float xMouse;
    private float yMouse;
    private final Quaternionf tempQuat;

    public DinoScreen(DinoMenu menu, Inventory playerInventory, Component name) {
        AbstractComplexMount entity;
        Entity entity2 = playerInventory.f_35978_.m_20202_();
        this(menu, playerInventory, entity2 instanceof AbstractComplexMount ? (entity = (AbstractComplexMount)entity2) : null);
    }

    public DinoScreen(DinoMenu menu, Inventory playerInventory, @Nullable AbstractComplexMount mount) {
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
        guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 79, j + 69, 0, this.f_97727_, 18, 18);
    }

    public void m_88315_(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(guiGraphics);
        this.xMouse = mouseX;
        this.yMouse = mouseY;
        super.m_88315_(guiGraphics, mouseX, mouseY, partialTick);
        this.m_280072_(guiGraphics, mouseX, mouseY);
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics guiGraphics, int x, int y, int scale, float mouseX, float mouseY, LivingEntity entity, @NotNull Quaternionf tempQuat) {
        float dx = mouseX - (float)x;
        float dy = mouseY - ((float)y - (float)scale * 3.0f);
        float yaw = AMath.atan((float)(dx / 40.0f));
        float pitch = AMath.atan((float)(dy / 40.0f));
        float cancelWorldYaw = AMath.toRadians((float)(180.0f + entity.f_20883_));
        tempQuat.identity().rotateZ((float)Math.PI).rotateX(-pitch * 0.35f).rotateY(cancelWorldYaw + yaw);
        InventoryScreen.m_280432_((GuiGraphics)guiGraphics, (int)x, (int)y, (int)scale, (Quaternionf)tempQuat, null, (LivingEntity)entity);
    }
}

