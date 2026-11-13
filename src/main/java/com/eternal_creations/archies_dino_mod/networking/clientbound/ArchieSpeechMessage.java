/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.core.Holder
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.ChatType
 *  net.minecraft.network.chat.ChatType$Bound
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.networking.clientbound;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.ArchieDialogScreen;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AbstractClientboundMessage;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class ArchieSpeechMessage
extends AbstractClientboundMessage {
    public static final ResourceLocation ID = Constants.prefix("archie_speech");
    private final String text;
    @Nullable
    private final Holder<SoundEvent> sound;
    private final SoundSource source;
    private final float volume;
    private final float pitch;

    public ArchieSpeechMessage(String text, @Nullable Holder<SoundEvent> sound, SoundSource source, float volume, float pitch) {
        this.text = text;
        this.sound = sound;
        this.source = source;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static ArchieSpeechMessage chat(String text) {
        return new ArchieSpeechMessage(text, null, SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    public static ArchieSpeechMessage voiced(String text, SoundEvent sound) {
        return new ArchieSpeechMessage(text, (Holder<SoundEvent>)BuiltInRegistries.f_256894_.m_263177_((Object)sound), SoundSource.NEUTRAL, 1.0f, 1.0f);
    }

    public static ArchieSpeechMessage decode(FriendlyByteBuf buffer) {
        String text = buffer.m_130277_();
        Holder sound = buffer.readBoolean() ? buffer.m_263187_(BuiltInRegistries.f_256894_.m_206115_(), SoundEvent::m_263214_) : null;
        return new ArchieSpeechMessage(text, (Holder<SoundEvent>)sound, (SoundSource)buffer.m_130066_(SoundSource.class), buffer.readFloat(), buffer.readFloat());
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        buf.m_130070_(this.text);
        buf.writeBoolean(this.sound != null);
        if (this.sound != null) {
            buf.m_263218_(BuiltInRegistries.f_256894_.m_206115_(), this.sound, (b, soundEvent) -> soundEvent.m_263231_(b));
        }
        buf.m_130068_((Enum)this.source);
        buf.writeFloat(this.volume);
        buf.writeFloat(this.pitch);
    }

    @Override
    public void execute() {
        Handler.handle(this);
    }

    private static class Handler {
        private Handler() {
        }

        private static void handle(ArchieSpeechMessage message) {
            Minecraft mc = Minecraft.m_91087_();
            mc.execute(() -> {
                if (mc.f_91073_ == null) {
                    return;
                }
                ChatType.Bound bound = ChatType.m_240968_((ResourceKey)ChatType.f_130598_, (RegistryAccess)mc.f_91073_.m_9598_(), (Component)Component.m_237115_((String)ADStrings.Gui.ARCHIE_TITLE));
                mc.m_240442_().m_245141_((Component)Component.m_237115_((String)message.text), bound);
                if (message.sound != null) {
                    ArchieDialogScreen.DialogOption.playVoiceLine(message.sound, message.source, message.volume, message.pitch);
                }
            });
        }
    }
}

