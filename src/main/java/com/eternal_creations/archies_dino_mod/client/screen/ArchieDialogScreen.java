/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractButton
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.ChatType
 *  net.minecraft.network.chat.ChatType$Bound
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.AbstractContainerMenu
 */
package com.eternal_creations.archies_dino_mod.client.screen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.menu.ArchieMenu;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.networking.serverbound.TalkToArchieMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchieDialogScreen
extends AbstractContainerScreen<ArchieMenu> {
    private static final ResourceLocation GUI_LOCATION = Constants.prefix("textures/gui/talk.png");
    private static final int BACKGROUND_WIDTH = 256;
    private static final int BACKGROUND_HEIGHT = 256;
    private static final int TITLE_X = 75;
    private static final int TITLE_Y = -40;
    private static final int SPEECH_X = 48;
    private static final int SPEECH_Y = -20;
    private static final int SPEECH_WRAP_WIDTH = 160;
    private static final int SPEECH_COLOR = 0x101010;
    private static final int TITLE_COLOR = 0x404040;
    public static final String ARCHIE_ON_FOLLOW = ArchieDialogScreen.key("on_follow");
    public static final String ARCHIE_DENY = ArchieDialogScreen.key("deny");
    public static final String ARCHIE_ON_LUSHSAUR = ArchieDialogScreen.key("on_lushsaur");
    public static final String ARCHIE_ON_THERIMOSS = ArchieDialogScreen.key("on_therimoss");
    public static final String ARCHIE_ON_CORALOSAUR = ArchieDialogScreen.key("on_coralosaur");
    public static final String ARCHIE_ON_SCULKRAPTOR = ArchieDialogScreen.key("on_sculkraptor");
    public static final String ARCHIE_ON_SCULKSAUR = ArchieDialogScreen.key("on_sculksaur");
    public static final String ARCHIE_ON_OBSIDICERATOPS = ArchieDialogScreen.key("on_obsidiceratops");
    public static final String ARCHIE_ON_CHORUSOATL = ArchieDialogScreen.key("on_chorusoatl");
    public static final String ARCHIE_ON_TRADE = ArchieDialogScreen.key("on_trade");
    public static final String ARCHIE_ON_STOP = ArchieDialogScreen.key("on_stop");
    @Nullable
    private final ArchieEntity archie;
    private final DialogTree dialogTree;

    public ArchieDialogScreen(ArchieMenu menu, Inventory playerInventory, Component name) {
        super((AbstractContainerMenu)menu, playerInventory, name);
        this.f_97728_ = 75;
        this.f_97729_ = -40;
        this.f_96541_ = Minecraft.m_91087_();
        this.archie = ArchieDialogScreen.findArchie(this.f_96541_.f_91073_);
        DialogTree dialogTree = this.dialogTree = this.archie != null && this.archie.isTamed() ? DialogTree.FRIEND : DialogTree.INTRO;
        if (this.archie != null) {
            DialogOption.playVoiceLine(this.dialogTree.voiceline().get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        }
    }

    private ArchieDialogScreen(ArchieDialogScreen screen, DialogTree tree) {
        super((AbstractContainerMenu)((ArchieMenu)screen.m_6262_()), ((ArchieMenu)screen.m_6262_()).getInventory(), (Component)Component.m_237115_((String)ADStrings.Gui.ARCHIE_TITLE));
        this.f_97728_ = 75;
        this.f_97729_ = -40;
        this.f_96541_ = Minecraft.m_91087_();
        this.archie = screen.archie;
        this.dialogTree = tree;
    }

    private static String key(String path) {
        return "gui.ec_ad.archie." + path;
    }

    public ArchieDialogScreen derive(DialogTree tree) {
        return new ArchieDialogScreen(this, tree);
    }

    @Nullable
    private static ArchieEntity findArchie(@Nullable ClientLevel level) {
        if (level == null) {
            return null;
        }
        for (Entity entity : level.m_104735_()) {
            if (!(entity instanceof ArchieEntity)) continue;
            ArchieEntity archieEntity = (ArchieEntity)entity;
            return archieEntity;
        }
        return null;
    }

    protected void m_7856_() {
        super.m_7856_();
        if (this.archie == null) {
            if (this.f_96541_ != null) {
                this.f_96541_.m_91152_(null);
            }
            return;
        }
        for (ButtonPlacement placement : this.dialogTree.options()) {
            this.m_142416_((GuiEventListener)new DialogButton(this.f_97735_ - 32 + placement.x(), this.f_97736_ + 85 + placement.y(), placement.width(), placement.height(), placement.option()));
        }
    }

    protected void m_280003_(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.m_280614_(this.f_96547_, this.f_96539_, this.f_97728_, this.f_97729_, 0x404040, false);
        this.renderTranslatedText(guiGraphics, (Component)Component.m_237115_((String)this.dialogTree.speech()));
    }

    protected void renderTranslatedText(GuiGraphics guiGraphics, Component text) {
        List lines = this.f_96547_.m_92923_((FormattedText)text, 160);
        for (int i = 0; i < lines.size(); ++i) {
            FormattedCharSequence formattedCharSequence = (FormattedCharSequence)lines.get(i);
            Objects.requireNonNull(this.f_96547_);
            guiGraphics.m_280649_(this.f_96547_, formattedCharSequence, 48, -20 + 9 * i, 0x101010, false);
        }
    }

    protected void m_7286_(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.f_96543_ - 256) / 2;
        int y = (this.f_96544_ - 256) / 2;
        guiGraphics.m_280163_(GUI_LOCATION, x, y, 0.0f, 0.0f, 256, 256, 256, 256);
        if (this.archie == null) {
            return;
        }
        InventoryScreen.m_274545_((GuiGraphics)guiGraphics, (int)this.f_97735_, (int)(this.f_97736_ + 75), (int)45, (float)-25.0f, (float)0.0f, (LivingEntity)this.archie);
    }

    public record DialogTree(ResourceSupplier<SoundEvent> voiceline, String speech, List<ButtonPlacement> options) {
        public static final DialogTree INTRO = new DialogTree(ADSoundEvents.ARCHIE_INTRO, ArchieDialogScreen.key("intro"), List.of(new ButtonPlacement(5, 2, 230, 25, DialogOption.FOLLOW_ME), new ButtonPlacement(5, 32, 230, 25, DialogOption.DO_NOT_FOLLOW_ME), new ButtonPlacement(5, 62, 230, 25, DialogOption.DINO_QUESTION), new ButtonPlacement(5, 92, 230, 25, DialogOption.TRADE)));
        public static final DialogTree FRIEND = new DialogTree(ADSoundEvents.ARCHIE_FRIEND, ArchieDialogScreen.key("friend"), List.of(new ButtonPlacement(5, 2, 230, 35, DialogOption.DINO_QUESTION), new ButtonPlacement(5, 42, 230, 35, DialogOption.TRADE), new ButtonPlacement(5, 82, 230, 35, DialogOption.STOP)));
        public static final DialogTree DINO_INFO = new DialogTree(null, ArchieDialogScreen.key("info"), List.of(new ButtonPlacement(5, 2, 110, 25, DialogOption.LUSHSAUR), new ButtonPlacement(125, 2, 110, 25, DialogOption.THERIMOSS), new ButtonPlacement(5, 32, 110, 25, DialogOption.CORALOSAUR), new ButtonPlacement(125, 32, 110, 25, DialogOption.SCULKRAPTOR), new ButtonPlacement(5, 62, 110, 25, DialogOption.CHORUSOATL), new ButtonPlacement(125, 62, 110, 25, DialogOption.OBSIDICERATOPS), new ButtonPlacement(5, 92, 230, 25, DialogOption.SCULKSAUR)));
    }

    public record DialogOption(String text, BiConsumer<ArchieEntity, LocalPlayer> consumer) {
        @Nullable
        private static ResourceLocation currentVoiceLine = null;
        public static final DialogOption FOLLOW_ME = new DialogOption(ArchieDialogScreen.key("follow"), (archieEntity, localPlayer) -> {
            DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_FOLLOW, ArchieEntity.VoicedChatLog.ARCHIE_ACCEPT);
            Services.NETWORK.sendToServer(new TalkToArchieMessage(archieEntity.m_19879_(), 0));
        });
        public static final DialogOption DO_NOT_FOLLOW_ME = new DialogOption(ArchieDialogScreen.key("no_follow"), (archieEntity, localPlayer) -> {
            DialogOption.closeAndSpeak(archieEntity, ARCHIE_DENY, ArchieEntity.VoicedChatLog.ARCHIE_DENY);
            Services.NETWORK.sendToServer(new TalkToArchieMessage(archieEntity.m_19879_(), 1));
        });
        public static final DialogOption DINO_QUESTION = new DialogOption(ArchieDialogScreen.key("a_question"), (archieEntity, localPlayer) -> {
            Minecraft minecraft = Minecraft.m_91087_();
            Screen patt7965$temp = minecraft.f_91080_;
            if (patt7965$temp instanceof ArchieDialogScreen) {
                ArchieDialogScreen oldScreen = (ArchieDialogScreen)patt7965$temp;
                ArchieDialogScreen newScreen = oldScreen.derive(DialogTree.DINO_INFO);
                newScreen.m_6575_(minecraft, minecraft.m_91268_().m_85445_(), minecraft.m_91268_().m_85446_());
                minecraft.f_91080_ = newScreen;
            }
        });
        public static final DialogOption LUSHSAUR = new DialogOption(ArchieDialogScreen.key("lushsaur"), (archieEntity, localPlayer) -> DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_LUSHSAUR));
        public static final DialogOption THERIMOSS = new DialogOption(ArchieDialogScreen.key("therimoss"), (archieEntity, localPlayer) -> DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_THERIMOSS));
        public static final DialogOption CORALOSAUR = new DialogOption(ArchieDialogScreen.key("coralosaur"), (archieEntity, localPlayer) -> DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_CORALOSAUR));
        public static final DialogOption SCULKRAPTOR = new DialogOption(ArchieDialogScreen.key("sculkraptor"), (archieEntity, localPlayer) -> DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_SCULKRAPTOR));
        public static final DialogOption SCULKSAUR = new DialogOption(ArchieDialogScreen.key("sculksaur"), (archieEntity, localPlayer) -> DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_SCULKSAUR));
        public static final DialogOption OBSIDICERATOPS = new DialogOption(ArchieDialogScreen.key("obsidiceratops"), (archieEntity, localPlayer) -> DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_OBSIDICERATOPS));
        public static final DialogOption CHORUSOATL = new DialogOption(ArchieDialogScreen.key("chorusoatl"), (archieEntity, localPlayer) -> DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_CHORUSOATL));
        public static final DialogOption TRADE = new DialogOption(ArchieDialogScreen.key("trade"), (archieEntity, localPlayer) -> {
            DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_TRADE);
            Services.NETWORK.sendToServer(new TalkToArchieMessage(archieEntity.m_19879_(), 2));
        });
        public static final DialogOption STOP = new DialogOption(ArchieDialogScreen.key("stop"), (archieEntity, localPlayer) -> {
            DialogOption.closeAndSpeak(archieEntity, ARCHIE_ON_STOP, ArchieEntity.VoicedChatLog.ARCHIE_DENY);
            Services.NETWORK.sendToServer(new TalkToArchieMessage(archieEntity.m_19879_(), 1));
        });

        private static void closeAndSpeak(ArchieEntity entity, String msg) {
            Minecraft minecraft = Minecraft.m_91087_();
            minecraft.m_91152_(null);
            minecraft.m_240442_().m_245141_((Component)Component.m_237115_((String)msg), new ChatType.Bound(ChatType.m_240980_((ResourceKey)ChatType.f_130598_, (Entity)entity).f_240859_(), (Component)Component.m_237115_((String)ADStrings.Gui.ARCHIE_TITLE), null));
        }

        public static void closeAndSpeak(ArchieEntity entity, String msg, ArchieEntity.VoicedChatLog voiceline) {
            DialogOption.closeAndSpeak(entity, msg);
            DialogOption.playVoiceLine(voiceline.sound().get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
        }

        public static void playVoiceLine(Holder<SoundEvent> sound, SoundSource source, float volume, float pitch) {
            DialogOption.playVoiceLine((SoundEvent)sound.m_203334_(), source, volume, pitch);
        }

        public static void playVoiceLine(SoundEvent sound, SoundSource source, float volume, float pitch) {
            LocalPlayer player = Minecraft.m_91087_().f_91074_;
            if (player == null) {
                return;
            }
            ClientLevel level = player.f_108545_;
            SoundManager soundManager = Minecraft.m_91087_().m_91106_();
            if (currentVoiceLine != null) {
                soundManager.m_120386_(currentVoiceLine, source);
            }
            SimpleSoundInstance instance = new SimpleSoundInstance(sound, source, volume, pitch, RandomSource.m_216335_((long)level.f_46441_.m_188505_()), player.m_20185_(), player.m_20186_(), player.m_20189_());
            soundManager.m_120367_((SoundInstance)instance);
            currentVoiceLine = instance.m_7904_();
        }
    }

    public record ButtonPlacement(int x, int y, int width, int height, DialogOption option) {
    }

    private class DialogButton
    extends AbstractButton {
        private final BiConsumer<ArchieEntity, LocalPlayer> consumer;

        public DialogButton(int x, int y, int width, int height, DialogOption option) {
            super(x, y, width, height, (Component)Component.m_237115_((String)option.text()));
            this.consumer = option.consumer();
        }

        public void m_5691_() {
            LocalPlayer player = Minecraft.m_91087_().f_91074_;
            ArchieEntity archie = ArchieDialogScreen.this.archie;
            if (player != null && archie != null) {
                this.consumer.accept(archie, player);
            }
        }

        protected void m_168797_(NarrationElementOutput narrationElementOutput) {
            this.m_168802_(narrationElementOutput);
        }
    }
}

