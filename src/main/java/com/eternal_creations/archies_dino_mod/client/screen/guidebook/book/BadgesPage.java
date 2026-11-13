/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.advancements.Advancement
 *  net.minecraft.advancements.AdvancementProgress
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientAdvancements
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.book;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.book.PageSpread;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.CategoryData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.PageData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIContext;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Sprite;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.render.RenderState;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.mixin.client.ClientAdvancementsAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Locale;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class BadgesPage
extends PageSpread {
    private static final String BADGES_PATH = "textures/gui/guidebook/badges/";
    private static final ResourceLocation BUTTONS_ON = Constants.prefix("textures/gui/guidebook/badges/stamps/on.png");
    private static final ResourceLocation BUTTONS_OFF = Constants.prefix("textures/gui/guidebook/badges/stamps/off.png");
    private static final ResourceLocation BACKGROUND = Constants.prefix("textures/gui/guidebook/badges/background.png");

    public BadgesPage(String path, float width, float height) {
        super(new CategoryData("badges", BACKGROUND, null, null).disableMarkers(), new PageData(Constants.prefix(BADGES_PATH + path + ".png")), width, height);
        this.addButtons(ADEntityTypes.LUSHSAUR.get(), 115.0f, 115.0f, StampButton.Type.VALUES);
        this.addButtons(ADEntityTypes.THERIMOSS.get(), 115.0f, 175.0f, StampButton.Type.VALUES);
        this.addButtons(ADEntityTypes.CORALOSAUR.get(), 115.0f, 235.0f, StampButton.Type.VALUES);
        this.addButtons(ADEntityTypes.OBSIDICERATOPS.get(), 367.0f, 55.0f, StampButton.Type.VALUES);
        this.addButtons(ADEntityTypes.SCULKRAPTOR.get(), 367.0f, 115.0f, StampButton.Type.VALUES);
        this.addButtons(ADEntityTypes.CHORUSOATL.get(), 367.0f, 175.0f, StampButton.Type.VALUES);
        this.addButtons(ADEntityTypes.SCULKSAUR.get(), 367.0f, 235.0f, StampButton.Type.SPOT, StampButton.Type.KILL, StampButton.Type.MASTER);
    }

    private void addButtons(EntityType<?> entityType, float x, float y, StampButton.Type ... types) {
        for (int i = 0; i < types.length; ++i) {
            int offset = i * 38;
            this.addChild(new StampButton(entityType, types[i], x + (float)offset, y));
        }
    }

    private static class StampButton
    extends AbstractWidget {
        private final Sprite lockedSprite;
        private final Sprite unlockedSprite;
        private final ResourceLocation advancementId;
        private final Component description;
        private final boolean unlocked;

        protected StampButton(EntityType<?> entityType, Type type, float x, float y) {
            super(x, y, 36.0f, 35.0f);
            String entityPath = BuiltInRegistries.f_256780_.m_7981_(entityType).m_135815_();
            String advKey = type.name().toLowerCase(Locale.ROOT) + "_" + entityPath;
            this.advancementId = Constants.prefix(advKey);
            this.description = Component.m_237115_((String)("advancement." + advKey + ".desc"));
            this.lockedSprite = Sprite.of(BUTTONS_OFF, this.bounds(), 576.0f, 320.0f);
            this.unlockedSprite = Sprite.of(BUTTONS_ON, this.bounds(), 576.0f, 320.0f);
            this.unlocked = StampButton.hasAdvancement(this.advancementId);
        }

        @Override
        protected void renderContent(UIContext ctx, RenderState state) {
            super.renderContent(ctx, state);
            ctx.drawSprite(this.unlocked ? this.unlockedSprite : this.lockedSprite, 0.0f, 0.0f, this.bounds.width(), this.bounds.height());
            boolean hovered = this.isHovered(ctx);
            if (hovered) {
                int mouseX = (int)ctx.mouseX();
                int mouseY = (int)ctx.mouseY();
                PoseStack poseStack = ctx.graphics().m_280168_();
                poseStack.m_85836_();
                ctx.graphics().m_280557_(ctx.font(), this.description, mouseX, mouseY);
                poseStack.m_85849_();
            }
        }

        public static boolean hasAdvancement(ResourceLocation id) {
            ClientPacketListener conn = Minecraft.m_91087_().m_91403_();
            if (conn == null) {
                return false;
            }
            ClientAdvancements advs = conn.m_105145_();
            Advancement adv = advs.m_104396_().m_139337_(id);
            if (adv == null) {
                return false;
            }
            AdvancementProgress progress = ((ClientAdvancementsAccessor)advs).archiesDinoMod$getProgress().get(adv);
            return progress != null && progress.m_8193_();
        }

        public static enum Type {
            SPOT,
            KILL,
            TAME,
            MASTER;

            public static final Type[] VALUES;

            static {
                VALUES = Type.values();
            }
        }
    }
}

