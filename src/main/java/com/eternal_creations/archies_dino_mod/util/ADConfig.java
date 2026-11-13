/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  com.google.gson.stream.JsonWriter
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.nbt.Tag
 *  org.slf4j.Logger
 */
package com.eternal_creations.archies_dino_mod.util;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import org.slf4j.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ADConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final String name;
    protected boolean loaded = false;

    public ADConfig(String name) {
        this.name = name;
    }

    public abstract CompoundTag toTag();

    public abstract void fromTag(CompoundTag var1);

    private Path getFilePath() {
        return Services.PLATFORM.getConfigDir().resolve("ec_ad").resolve(this.name + ".json");
    }

    public void loadOrCreate() {
        if (this.loaded) {
            return;
        }
        Path filePath = this.getFilePath();
        if (Files.exists(filePath, new LinkOption[0])) {
            try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);){
                JsonElement json = JsonParser.parseReader((Reader)reader);
                Tag tag = (Tag)JsonOps.INSTANCE.convertTo((DynamicOps)NbtOps.f_128958_, json);
                if (tag instanceof CompoundTag) {
                    CompoundTag compound = (CompoundTag)tag;
                    this.fromTag(compound);
                }
            }
            catch (Exception e) {
                Constants.LOG.error("Failed to read config '{}', using defaults", (Object)this.name, (Object)e);
            }
        }
        this.loaded = true;
        this.saveToDisk();
    }

    public void saveToDisk() {
        try {
            JsonElement json = (JsonElement)NbtOps.f_128958_.convertTo((DynamicOps)JsonOps.INSTANCE, (Tag)this.toTag());
            Path filePath = this.getFilePath();
            Files.createDirectories(filePath.getParent(), new FileAttribute[0]);
            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, new OpenOption[0]);
                 JsonWriter jsonWriter = GSON.newJsonWriter((Writer)writer);){
                GSON.toJson(json, jsonWriter);
            }
        }
        catch (Exception e) {
            Constants.LOG.error("Failed to save config '{}'", (Object)this.name, (Object)e);
        }
    }

    public static <T> Tag encode(Codec<T> codec, T value) {
        return (Tag)codec.encodeStart((DynamicOps)NbtOps.f_128958_, value).getOrThrow(false, arg_0 -> ((Logger)Constants.LOG).error(arg_0));
    }

    public static <T> T decode(Codec<T> codec, @Nullable Tag tag, T fallback) {
        if (tag == null) {
            return fallback;
        }
        return codec.parse((DynamicOps)NbtOps.f_128958_, (Object)tag).result().orElse(fallback);
    }

    protected static final class Setting<T> {
        private final String name;
        private final Function<T, CompoundTag> writer;
        private final BiFunction<CompoundTag, T, T> reader;
        private T value;

        public Setting(String name, T defaultValue, Function<T, CompoundTag> writer, BiFunction<CompoundTag, T, T> reader) {
            this.name = name;
            this.value = defaultValue;
            this.writer = writer;
            this.reader = reader;
        }

        public T getValue() {
            return this.value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void write(CompoundTag parent) {
            parent.m_128365_(this.name, (Tag)this.writer.apply(this.value));
        }

        public void read(CompoundTag parent) {
            if (parent.m_128425_(this.name, 10)) {
                this.value = this.reader.apply(parent.m_128469_(this.name), (CompoundTag)this.value);
            }
        }
    }
}

