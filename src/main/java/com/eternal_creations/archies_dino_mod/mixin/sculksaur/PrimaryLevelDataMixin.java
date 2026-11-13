/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.LevelSettings
 *  net.minecraft.world.level.levelgen.WorldOptions
 *  net.minecraft.world.level.storage.LevelVersion
 *  net.minecraft.world.level.storage.PrimaryLevelData
 *  net.minecraft.world.level.storage.PrimaryLevelData$SpecialWorldProperty
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.sculksaur;

import com.eternal_creations.archies_dino_mod.util.handlers.SculksaurHandler;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.storage.LevelVersion;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={PrimaryLevelData.class})
public class PrimaryLevelDataMixin
implements SculksaurHandler.iLevelDataWithSculksaur {
    @Unique
    @Nullable
    private SculksaurHandler archies_Dino_Mod$SculksaurHandler = null;

    @Override
    public SculksaurHandler archies_Dino_Mod$getSculksaurHandler() {
        if (this.archies_Dino_Mod$SculksaurHandler == null) {
            this.archies_Dino_Mod$SculksaurHandler = new SculksaurHandler();
        }
        return this.archies_Dino_Mod$SculksaurHandler;
    }

    @Override
    public void archies_Dino_Mod$setSculksaurHandler(SculksaurHandler handler) {
        this.archies_Dino_Mod$SculksaurHandler = handler;
    }

    @Inject(method={"parse"}, at={@At(value="RETURN")})
    private static <T> void mixinParse(Dynamic<T> dynamic, DataFixer fixerUpper, int playerDataVersion, @Nullable CompoundTag loadedPlayerTag, LevelSettings settings, LevelVersion levelVersion, PrimaryLevelData.SpecialWorldProperty specialWorldProperty, WorldOptions worldOptions, Lifecycle worldGenSettingsLifecycle, CallbackInfoReturnable<PrimaryLevelData> cir) {
        SculksaurHandler.iLevelDataWithSculksaur levelData = (SculksaurHandler.iLevelDataWithSculksaur)cir.getReturnValue();
        levelData.archies_Dino_Mod$setSculksaurHandler(SculksaurHandler.read(dynamic));
    }

    @Inject(method={"setTagData"}, at={@At(value="RETURN")})
    private void mixinSetTagData(RegistryAccess registry, CompoundTag nbt, CompoundTag playerNBT, CallbackInfo ci) {
        this.archies_Dino_Mod$getSculksaurHandler().write(nbt);
    }
}

