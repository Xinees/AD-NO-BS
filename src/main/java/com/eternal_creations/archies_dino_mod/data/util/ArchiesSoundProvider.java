/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.PackOutput
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraftforge.common.data.ExistingFileHelper
 *  net.minecraftforge.common.data.SoundDefinition
 *  net.minecraftforge.common.data.SoundDefinition$Sound
 *  net.minecraftforge.common.data.SoundDefinition$SoundType
 *  net.minecraftforge.common.data.SoundDefinitionsProvider
 */
package com.eternal_creations.archies_dino_mod.data.util;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.data.util.ArchiesLangProvider;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ArchiesSoundProvider
extends SoundDefinitionsProvider {
    protected ArchiesSoundProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, "ec_ad", helper);
    }

    public void generateNewSoundWithSubtitle(Supplier<SoundEvent> event, int numberOfSounds, String subtitle, float volume, float pitch) {
        this.generateNewSound(event, numberOfSounds, volume, pitch, subtitle);
    }

    public void generateNewSoundWithSubtitle(Supplier<SoundEvent> event, int numberOfSounds, String subtitle) {
        this.generateNewSound(event, numberOfSounds, 1.0f, 1.0f, subtitle);
    }

    public void generateNewSound(Supplier<SoundEvent> event, int numberOfSounds) {
        this.generateNewSound(event, numberOfSounds, 1.0f, 1.0f, null);
    }

    public void generateNewSound(Supplier<SoundEvent> event, int numberOfSounds, float volume, float pitch, @Nullable String subtitle) {
        String path = Objects.requireNonNull(event.get().m_11660_()).m_135815_().replaceAll("\\.", "/");
        this.generateNewSound(event, path, numberOfSounds, volume, pitch, subtitle);
    }

    public void generateNewSound(Supplier<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, float volume, float pitch, @Nullable String subtitle) {
        SoundDefinition definition = SoundDefinition.definition();
        if (subtitle != null) {
            this.createSubtitleAndLangEntry(event, definition, subtitle);
        }
        for (int i = 1; i <= numberOfSounds; ++i) {
            definition.with(SoundDefinition.Sound.sound((ResourceLocation)Constants.prefix(baseSoundDirectory + String.valueOf(numberOfSounds > 1 ? Integer.valueOf(i) : "")), (SoundDefinition.SoundType)SoundDefinition.SoundType.SOUND).volume(volume).pitch(pitch));
        }
        this.add(event, definition);
    }

    public void generateNewSoundMC(Supplier<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, @Nullable String subtitle) {
        SoundDefinition definition = SoundDefinition.definition();
        if (subtitle != null) {
            this.createSubtitleAndLangEntry(event, definition, subtitle);
        }
        for (int i = 1; i <= numberOfSounds; ++i) {
            definition.with(SoundDefinition.Sound.sound((ResourceLocation)ResourceLocation.withDefaultNamespace((String)(baseSoundDirectory + i)), (SoundDefinition.SoundType)SoundDefinition.SoundType.SOUND));
        }
        this.add(event, definition);
    }

    public void generateExistingSoundWithSubtitle(Supplier<SoundEvent> event, SoundEvent referencedSound, String subtitle) {
        this.generateExistingSoundWithSubtitle(event, referencedSound, subtitle, 1.0f, 1.0f);
    }

    public void generateWithExistingSound(Supplier<SoundEvent> event, SoundEvent referencedSound, String subtitle) {
        SoundDefinition definition = SoundDefinition.definition().with(SoundDefinition.Sound.sound((ResourceLocation)referencedSound.m_11660_(), (SoundDefinition.SoundType)SoundDefinition.SoundType.EVENT));
        this.createSubtitleAndLangEntry(event, definition, subtitle);
        this.add(event, definition);
    }

    public void generateExistingSoundWithSubtitle(Supplier<SoundEvent> event, SoundEvent referencedSound, String subtitle, float volume, float pitch) {
        SoundDefinition definition = SoundDefinition.definition();
        this.createSubtitleAndLangEntry(event, definition, subtitle);
        this.add(event, definition.with(SoundDefinition.Sound.sound((ResourceLocation)referencedSound.m_11660_(), (SoundDefinition.SoundType)SoundDefinition.SoundType.EVENT).volume(volume).pitch(pitch)));
    }

    public void generateExistingSoundWithSubtitle(Supplier<SoundEvent> event, SoundEvent referencedSound, float volume, float pitch) {
        this.add(event, SoundDefinition.definition().with(SoundDefinition.Sound.sound((ResourceLocation)referencedSound.m_11660_(), (SoundDefinition.SoundType)SoundDefinition.SoundType.EVENT).volume(volume).pitch(pitch)));
    }

    public void makeStepSound(Supplier<SoundEvent> event, SoundEvent referencedSound) {
        this.add(event, SoundDefinition.definition().subtitle("subtitles.block.generic.footsteps").with(SoundDefinition.Sound.sound((ResourceLocation)referencedSound.m_11660_(), (SoundDefinition.SoundType)SoundDefinition.SoundType.EVENT)));
    }

    public void makeNewStepSound(Supplier<SoundEvent> event, int numberOfSounds) {
        String[] splitSoundName = Objects.requireNonNull(event.get().m_11660_()).m_135815_().split("\\.", 3);
        if (splitSoundName.length == 3) {
            this.makeNewStepSound(event, splitSoundName[1] + "/" + splitSoundName[2], numberOfSounds);
        } else if (splitSoundName.length == 2) {
            this.makeNewStepSound(event, splitSoundName[1], numberOfSounds);
        }
    }

    public void makeNewStepSound(Supplier<SoundEvent> event, String baseSoundDirectory, int numberOfSounds) {
        SoundDefinition definition = SoundDefinition.definition();
        for (int i = 1; i <= numberOfSounds; ++i) {
            definition.with(SoundDefinition.Sound.sound((ResourceLocation)Constants.prefix(baseSoundDirectory + String.valueOf(numberOfSounds > 1 ? Integer.valueOf(i) : "")), (SoundDefinition.SoundType)SoundDefinition.SoundType.SOUND));
        }
        this.add(event, definition.subtitle("subtitles.block.generic.footsteps"));
    }

    public void makeNewGenericSound(Supplier<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, @Nullable String type) {
        SoundDefinition definition = SoundDefinition.definition();
        for (int i = 1; i <= numberOfSounds; ++i) {
            definition.with(SoundDefinition.Sound.sound((ResourceLocation)Constants.prefix(baseSoundDirectory + String.valueOf(numberOfSounds > 1 ? Integer.valueOf(i) : "")), (SoundDefinition.SoundType)SoundDefinition.SoundType.SOUND));
        }
        this.add(event, type != null ? definition.subtitle("subtitles.block.generic." + type) : definition);
    }

    public void makeMusicDisc(Supplier<SoundEvent> event, String discName) {
        this.add(event, SoundDefinition.definition().with(SoundDefinition.Sound.sound((ResourceLocation)Constants.prefix("music/" + discName), (SoundDefinition.SoundType)SoundDefinition.SoundType.SOUND).stream()));
    }

    public void generateParrotSound(Supplier<SoundEvent> event, SoundEvent referencedSound, String subtitle) {
        SoundDefinition definition = SoundDefinition.definition();
        this.createSubtitleAndLangEntry(event, definition, subtitle);
        this.add(event, definition.with(SoundDefinition.Sound.sound((ResourceLocation)referencedSound.m_11660_(), (SoundDefinition.SoundType)SoundDefinition.SoundType.EVENT).pitch(1.8f).volume(0.6f)));
    }

    private void createSubtitleAndLangEntry(Supplier<SoundEvent> event, SoundDefinition definition, String subtitle) {
        String[] splitSoundName = Objects.requireNonNull(event.get().m_11660_()).m_135815_().split("\\.", 2);
        String subtitleKey = "subtitles.ec_ad." + splitSoundName[0] + "." + splitSoundName[1];
        definition.subtitle(subtitleKey);
        ArchiesLangProvider.AUTO_GENERATOR.put(subtitleKey, subtitle);
    }
}

