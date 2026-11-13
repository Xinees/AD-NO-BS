/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.ObjectSelectionList
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.client.screen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.entity.ADPetData;
import com.eternal_creations.archies_dino_mod.networking.serverbound.TeleportPetMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScorchingHornScreen
extends Screen {
    private static final ResourceLocation BACKGROUND = Constants.prefix("textures/gui/scorching_horn.png");
    private static final String TITLE = "gui.ec_ad.scorching_horn.title";
    private static final String SEARCH_HINT = "gui.ec_ad.scorching_horn.search_hint";
    private static final String BTN_TELEPORT = "gui.ec_ad.scorching_horn.teleport";
    private final List<ADPetData> allPets;
    private EditBox searchBox;
    private PetList petList;
    private Button teleportButton;
    private final int screenWidth = 256;
    private final int screenHeight = 172;
    private int leftPos;
    private int topPos;

    public ScorchingHornScreen(List<ADPetData> pets) {
        super((Component)Component.m_237115_((String)TITLE));
        this.allPets = pets;
    }

    protected void m_7856_() {
        this.leftPos = (this.f_96543_ - this.screenWidth) / 2;
        this.topPos = (this.f_96544_ - this.screenHeight) / 2;
        this.searchBox = new EditBox(this.f_96547_, this.leftPos + 18, this.topPos + 20, 220, 14, (Component)Component.m_237119_());
        this.searchBox.m_257771_((Component)Component.m_237115_((String)SEARCH_HINT).m_130940_(ChatFormatting.DARK_GRAY));
        this.searchBox.m_94151_(this::onSearchChanged);
        this.searchBox.m_94199_(30);
        this.m_7787_((GuiEventListener)this.searchBox);
        int listTop = this.topPos + 38;
        int listBottom = this.topPos + 138;
        int itemHeight = 28;
        this.petList = new PetList(this.f_96541_, 220, listBottom - listTop, listTop + 2, listBottom - 2, itemHeight);
        this.petList.m_93507_(this.leftPos + 18);
        this.m_7787_((GuiEventListener)this.petList);
        this.refreshPetList("");
        this.teleportButton = Button.m_253074_((Component)Component.m_237115_((String)BTN_TELEPORT), btn -> {
            PetList.PetEntry selected = (PetList.PetEntry)this.petList.m_93511_();
            if (selected != null) {
                this.teleportPet(selected.data);
            }
        }).m_252987_(this.leftPos + 18, this.topPos + 143, 106, 20).m_253136_();
        this.teleportButton.f_93623_ = false;
        this.m_142416_((GuiEventListener)this.teleportButton);
        this.m_142416_((GuiEventListener)Button.m_253074_((Component)CommonComponents.f_130656_, btn -> this.m_7379_()).m_252987_(this.leftPos + 132, this.topPos + 143, 106, 20).m_253136_());
    }

    private void onSearchChanged(String query) {
        this.refreshPetList(query.trim().toLowerCase());
    }

    private void refreshPetList(String filter) {
        this.petList.m_93516_();
        this.petList.setSelected(null);
        this.petList.m_93410_(0.0);
        for (ADPetData pet : this.allPets) {
            String name = pet.name().getString().toLowerCase();
            String dim = pet.dimension().m_135815_().toLowerCase();
            if (!filter.isEmpty() && !name.contains(filter) && !dim.contains(filter)) continue;
            this.petList.addPet(pet);
        }
        this.updateButtonState();
    }

    public void updateButtonState() {
        if (this.teleportButton != null) {
            this.teleportButton.f_93623_ = this.petList.m_93511_() != null;
        }
    }

    private void teleportPet(ADPetData pet) {
        Services.NETWORK.sendToServer(new TeleportPetMessage(pet));
        this.m_7379_();
    }

    public void m_88315_(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.m_280218_(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.screenWidth, this.screenHeight);
        guiGraphics.m_280614_(this.f_96547_, this.f_96539_, this.leftPos + 18, this.topPos + 8, 0x3F3F3F, false);
        int listLeft = this.leftPos + 18;
        int listTop = this.topPos + 38;
        int listWidth = 220;
        int listHeight = 100;
        guiGraphics.m_280509_(listLeft, listTop, listLeft + listWidth, listTop + listHeight, -15461356);
        guiGraphics.m_280637_(listLeft, listTop, listWidth, listHeight, -13158601);
        guiGraphics.m_280588_(listLeft + 1, listTop + 1, listLeft + listWidth - 1, listTop + listHeight - 1);
        this.petList.m_88315_(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.m_280618_();
        this.searchBox.m_88315_(guiGraphics, mouseX, mouseY, partialTick);
        super.m_88315_(guiGraphics, mouseX, mouseY, partialTick);
    }

    public boolean m_7043_() {
        return false;
    }

    public class PetList
    extends ObjectSelectionList<PetEntry> {
        public PetList(Minecraft mc, int width, int height, int top, int bottom, int itemHeight) {
            super(mc, width, height, top, bottom, itemHeight);
            this.m_93488_(false);
            this.m_93496_(false);
        }

        public void addPet(ADPetData data) {
            this.m_7085_((AbstractSelectionList.Entry)new PetEntry(data));
        }

        public void m_93516_() {
            super.m_93516_();
        }

        public int m_5747_() {
            return this.f_93393_ + 6;
        }

        public int m_5759_() {
            return 196;
        }

        protected int m_5756_() {
            return this.f_93393_ + 208;
        }

        protected void m_240140_(@NotNull GuiGraphics guiGraphics, int top, int width, int height, int outerColor, int innerColor) {
        }

        public void setSelected(@Nullable PetEntry entry) {
            super.m_6987_((AbstractSelectionList.Entry)entry);
            ScorchingHornScreen.this.updateButtonState();
        }

        public class PetEntry
        extends ObjectSelectionList.Entry<PetEntry> {
            public final ADPetData data;

            public PetEntry(ADPetData data) {
                this.data = data;
            }

            public void m_6311_(@NotNull GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovered, float partialTick) {
                int locColor;
                int nameColor;
                int borderColor;
                int bgColor;
                boolean isSelected = PetList.this.m_93511_() == this;
                int cardTop = top + 1;
                int cardHeight = height - 2;
                int cardRight = left + width;
                int cardBottom = cardTop + cardHeight;
                if (isSelected) {
                    bgColor = -14404537;
                    borderColor = -11566928;
                    nameColor = 0xFFFFFF;
                    locColor = 8960230;
                } else if (isHovered) {
                    bgColor = -14145496;
                    borderColor = -11908534;
                    nameColor = 0xFFFFFF;
                    locColor = 0xAAAAAA;
                } else {
                    bgColor = -14935012;
                    borderColor = -14277082;
                    nameColor = 0xCCCCCC;
                    locColor = 0x666666;
                }
                guiGraphics.m_280509_(left, cardTop, cardRight, cardBottom, bgColor);
                if (isSelected || isHovered) {
                    guiGraphics.m_280637_(left, cardTop, width, cardHeight, borderColor);
                }
                guiGraphics.m_280614_(ScorchingHornScreen.this.f_96547_, this.data.name(), left + 6, cardTop + 2, nameColor, false);
                String dim = this.data.dimension().m_135815_();
                String formattedDim = Character.toUpperCase(dim.charAt(0)) + dim.substring(1);
                String loc = String.format("%s \u00a78\u2022\u00a7r %d, %d, %d", formattedDim, this.data.pos().m_123341_(), this.data.pos().m_123342_(), this.data.pos().m_123343_());
                guiGraphics.m_280056_(ScorchingHornScreen.this.f_96547_, loc, left + 6, cardTop + 12, locColor, false);
            }

            public boolean m_6375_(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    PetList.this.setSelected(this);
                    return true;
                }
                return false;
            }

            @NotNull
            public Component m_142172_() {
                return this.data.name();
            }
        }
    }
}

