/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.entity.player.Player
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.RootContainer;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.PopupManager;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Viewport2D;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class GuidebookScreen
extends Screen {
    public static final int BOOK_WIDTH = 576;
    public static final int BOOK_HEIGHT = 320;
    public static final float SCALE_FACTOR = 0.75f;
    private final UIContext context = UIContext.of();
    private final Viewport2D viewport = Viewport2D.of(576.0f, 320.0f, 0.75f);
    private final RootContainer root = RootContainer.of(576.0f, 320.0f);
    private final PopupManager popupManager = new PopupManager();

    public GuidebookScreen() {
        super((Component)Component.m_237119_());
    }

    protected void m_7856_() {
        super.m_7856_();
        if (this.f_96541_ == null) {
            return;
        }
        this.popupManager.close();
        this.f_96541_.m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)SoundEvents.f_11713_, (float)1.0f));
        this.root.triggerAppear();
    }

    public void m_88315_(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(graphics);
        if (this.f_96541_ == null) {
            return;
        }
        this.viewport.update(this.f_96543_, this.f_96544_);
        this.context.prepare(graphics, (Player)this.f_96541_.f_91074_, this.viewport.toLocalX(mouseX), this.viewport.toLocalY(mouseY), Minecraft.m_91087_().m_91296_(), 576, 320, this.f_96543_, this.f_96544_, this.popupManager);
        PoseStack pose = graphics.m_280168_();
        pose.m_85836_();
        this.viewport.apply(pose);
        this.root.render(this.context);
        pose.m_85849_();
        this.popupManager.renderBackground(this.context);
        graphics.m_280262_();
        pose.m_85836_();
        this.viewport.apply(pose);
        this.popupManager.render(this.context);
        pose.m_85849_();
    }

    public void m_86600_() {
        this.popupManager.tick();
        this.root.tick();
    }

    public void m_7379_() {
        super.m_7379_();
        this.root.close();
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (this.popupManager.isOpen()) {
            return this.popupManager.mouseClicked(this.context, button);
        }
        if (this.root.mouseClicked(this.context, button)) {
            return true;
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        if (this.popupManager.isOpen() && (keyCode == 256 || Objects.requireNonNull(this.f_96541_).f_91066_.f_92092_.m_90832_(keyCode, scanCode))) {
            this.popupManager.close();
            return true;
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    public boolean m_7043_() {
        return false;
    }
}

