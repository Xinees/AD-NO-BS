/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.Slot
 */
package com.eternal_creations.archies_dino_mod.client.screen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.common.menu.CoralosaurMenu;
import com.eternal_creations.archies_dino_mod.networking.serverbound.CoralosaurChestMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoralosaurScreen
extends AbstractContainerScreen<CoralosaurMenu> {
    private static final ResourceLocation HORSE_INVENTORY_LOCATION = Constants.prefix("textures/gui/coralo.png");
    @Nullable
    private final CoralosaurEntity coralosaur;
    private final List<Button> opens;
    private float xMouse;
    private float yMouse;

    public CoralosaurScreen(CoralosaurMenu menu, Inventory playerInventory, Component name) {
        CoralosaurEntity entity;
        Entity entity2 = playerInventory.f_35978_.m_20202_();
        this(menu, playerInventory, entity2 instanceof CoralosaurEntity ? (entity = (CoralosaurEntity)entity2) : null);
    }

    public CoralosaurScreen(CoralosaurMenu menu, Inventory playerInventory, @Nullable CoralosaurEntity coralosaur) {
        super((AbstractContainerMenu)menu, playerInventory, (Component)(coralosaur != null ? coralosaur.m_5446_() : Component.m_237119_()));
        this.opens = new ArrayList<Button>();
        this.coralosaur = coralosaur;
        this.f_97726_ = 176;
        this.f_97727_ = 188;
        this.f_97728_ = 8;
        this.f_97729_ = 6;
        this.f_97730_ = 8;
        this.f_97731_ = this.f_97727_ - 94;
    }

    protected void m_7856_() {
        super.m_7856_();
        if (this.coralosaur == null) {
            return;
        }
        this.opens.clear();
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(0);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 0, false));
        }).m_252987_(this.f_97735_ + 7, this.f_97736_ + 15, 36, 18).m_253136_()));
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(1);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 1, false));
        }).m_252987_(this.f_97735_ + 7, this.f_97736_ + 33, 36, 18).m_253136_()));
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(2);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 2, false));
        }).m_252987_(this.f_97735_ + 7, this.f_97736_ + 51, 36, 18).m_253136_()));
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(3);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 3, false));
        }).m_252987_(this.f_97735_ + 7, this.f_97736_ + 69, 36, 18).m_253136_()));
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(4);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 4, false));
        }).m_252987_(this.f_97735_ + 133, this.f_97736_ + 15, 36, 18).m_253136_()));
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(5);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 5, false));
        }).m_252987_(this.f_97735_ + 133, this.f_97736_ + 33, 36, 18).m_253136_()));
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(6);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 6, false));
        }).m_252987_(this.f_97735_ + 133, this.f_97736_ + 51, 36, 18).m_253136_()));
        this.opens.add((Button)this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237115_((String)ADStrings.Gui.CORALOSAUR_OPEN_BUTTON), button -> {
            button.f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(7);
            Services.NETWORK.sendToServer(new CoralosaurChestMessage(this.coralosaur.m_19879_(), 7, false));
        }).m_252987_(this.f_97735_ + 133, this.f_97736_ + 69, 36, 18).m_253136_()));
        for (int i = 0; i < 8; ++i) {
            this.opens.get((int)i).f_93623_ = this.coralosaur.m_6254_() && this.coralosaur.hasChest(i);
        }
    }

    protected void m_181908_() {
        super.m_181908_();
        this.m_232761_();
    }

    protected void m_6597_(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.m_6597_(slot, slotId, mouseButton, type);
        this.m_232761_();
    }

    protected void m_7286_(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.f_96543_ - this.f_97726_) / 2;
        int j = (this.f_96544_ - this.f_97727_) / 2;
        guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i, j, 0, 0, this.f_97726_, this.f_97727_);
        if (this.coralosaur == null) {
            return;
        }
        InventoryScreen.m_274545_((GuiGraphics)guiGraphics, (int)(i + 88), (int)(j + 57), (int)6, (float)((float)(i + 51) - this.xMouse), (float)((float)(j + 75 - 50) - this.yMouse), (LivingEntity)this.coralosaur);
        guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 79, j + 69, 0, this.f_97727_, 18, 18);
        if (this.coralosaur.m_6254_()) {
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 15, 18, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 33, 18, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 51, 18, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 69, 18, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 15, 18, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 33, 18, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 51, 18, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 69, 18, this.f_97727_, 18, 18);
        } else {
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 15, 36, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 33, 36, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 51, 36, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 43, j + 69, 36, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 15, 36, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 33, 36, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 51, 36, this.f_97727_, 18, 18);
            guiGraphics.m_280218_(HORSE_INVENTORY_LOCATION, i + 115, j + 69, 36, this.f_97727_, 18, 18);
        }
    }

    public void m_88315_(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(guiGraphics);
        this.xMouse = mouseX;
        this.yMouse = mouseY;
        super.m_88315_(guiGraphics, mouseX, mouseY, partialTick);
        this.m_280072_(guiGraphics, mouseX, mouseY);
    }
}

