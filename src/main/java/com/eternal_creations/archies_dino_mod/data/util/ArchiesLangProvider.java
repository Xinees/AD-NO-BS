/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.data.PackOutput
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.damagesource.DamageType
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraftforge.common.data.LanguageProvider
 *  net.minecraftforge.registries.RegistryObject
 */
package com.eternal_creations.archies_dino_mod.data.util;

import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.KeyMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ArchiesLangProvider
extends LanguageProvider {
    public static final Map<String, String> AUTO_GENERATOR = new HashMap<String, String>();

    public ArchiesLangProvider(PackOutput output) {
        super(output, "ec_ad", "en_us");
    }

    protected final void addTranslations() {
        AUTO_GENERATOR.forEach((arg_0, arg_1) -> ((ArchiesLangProvider)this).add(arg_0, arg_1));
        this.addCustomTranslations();
    }

    protected abstract void addCustomTranslations();

    public void addBlockAndDesc(Supplier<? extends Block> key, String name, String ... desc) {
        this.add(key.get(), name);
        for (int i = 0; i < desc.length; ++i) {
            this.add(key.get().m_7705_() + ".description_" + i, desc[i]);
        }
    }

    public void addItemAndDesc(Supplier<? extends Item> key, String name, String ... desc) {
        this.add(key.get(), name);
        for (int i = 0; i < desc.length; ++i) {
            this.add(key.get().m_5524_() + ".description_" + i, desc[i]);
        }
    }

    public void addCreativeTab(ResourceSupplier<CreativeModeTab> tab, String name) {
        this.add("item_group.ec_ad." + Objects.requireNonNull(tab.id()).m_135815_(), name);
    }

    public void addAttribute(RegistryObject<Attribute> tab, String name) {
        this.add("attribute.ec_ad." + Objects.requireNonNull(tab.getId()).m_135815_(), name);
    }

    public void addKeyMapping(KeyMapping key, String name) {
        this.add(key.m_90860_(), name);
    }

    public void addAdvancement(String key, String title, String desc) {
        this.add("advancement.ec_ad." + key, title);
        this.add("advancement.ec_ad." + key + ".desc", desc);
    }

    public void addEntityAndEgg(ResourceSupplier<? extends EntityType<?>> entity, String name) {
        this.addEntityType(entity, name);
        this.add("item.ec_ad." + Objects.requireNonNull(entity.id()).m_135815_() + "_spawn_egg", name + " Spawn Egg");
    }

    public void addSubtitle(RegistryObject<SoundEvent> sound, String name) {
        String[] splitSoundName = Objects.requireNonNull(sound.getId()).m_135815_().split("\\.", 3);
        this.add("subtitles.ec_ad." + splitSoundName[0] + "." + splitSoundName[2], name);
    }

    public void addDamageType(ResourceKey<DamageType> damageType, String message, @Nullable String usingItemMessage, @Nullable String byPlayerMessage) {
        String baseKey = "death.attack.ec_ad." + damageType.m_135782_().m_135815_();
        this.add(baseKey, message);
        if (usingItemMessage != null) {
            this.add(baseKey + ".item", usingItemMessage);
        }
        if (byPlayerMessage != null) {
            this.add(baseKey + ".player", byPlayerMessage);
        }
    }
}

