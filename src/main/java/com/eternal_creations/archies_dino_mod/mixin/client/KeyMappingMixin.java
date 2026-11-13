/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Implements
 *  org.spongepowered.asm.mixin.Interface
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.client;

import com.eternal_creations.archies_dino_mod.client.ADKeyMappings;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.Map;
import java.util.function.BiConsumer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={KeyMapping.class})
@Implements(value={@Interface(iface=ADKeyMappings.KeyMappingExtension.class, prefix="archiesDinoModKeyMapping$")})
public abstract class KeyMappingMixin
implements ADKeyMappings.KeyMappingExtension {
    @Shadow
    @Final
    private static Map<String, KeyMapping> f_90809_;
    @Shadow
    private InputConstants.Key f_90816_;
    @Shadow
    private int f_90818_;
    @Shadow
    private boolean f_90817_;

    public // Could not load outer class - annotation placement on inner may be incorrect
     @NotNull InputConstants.Key archiesDinoModKeyMapping$getKey() {
        return this.f_90816_;
    }

    public int archiesDinoModKeyMapping$getClickCount() {
        return this.f_90818_;
    }

    public void archiesDinoModKeyMapping$setClickCount(int count) {
        this.f_90818_ = count;
    }

    public void archiesDinoModKeyMapping$setDown(boolean down) {
        this.f_90817_ = down;
    }

    @Inject(method={"click"}, at={@At(value="HEAD")}, cancellable=true)
    private static void archiesDinoMod$onClick(InputConstants.Key key, CallbackInfo ci) {
        KeyMappingMixin.archiesDinoMod$process(key, (accessor, active) -> {
            if (active.booleanValue()) {
                accessor.setClickCount(accessor.getClickCount() + 1);
            }
        });
        ci.cancel();
    }

    @Inject(method={"set"}, at={@At(value="HEAD")}, cancellable=true)
    private static void archiesDinoMod$onSet(InputConstants.Key key, boolean held, CallbackInfo ci) {
        KeyMappingMixin.archiesDinoMod$process(key, (accessor, active) -> accessor.setDown(held && active != false));
        ci.cancel();
    }

    @Inject(method={"same"}, at={@At(value="HEAD")}, cancellable=true)
    private void archiesDinoMod$onSame(KeyMapping binding, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof ADKeyMappings.KeyMappingConditional || binding instanceof ADKeyMappings.KeyMappingConditional) {
            cir.setReturnValue((Object)false);
        }
    }

    @Unique
    private static void archiesDinoMod$process(InputConstants.Key key, BiConsumer<ADKeyMappings.KeyMappingExtension, Boolean> action) {
        LocalPlayer player = Minecraft.m_91087_().f_91074_;
        boolean hasActiveConditional = false;
        if (player != null) {
            ADKeyMappings.KeyMappingExtension accessor;
            for (KeyMapping m : f_90809_.values()) {
                ADKeyMappings.KeyMappingConditional c;
                accessor = (ADKeyMappings.KeyMappingExtension)m;
                if (!accessor.getKey().equals((Object)key) || !(m instanceof ADKeyMappings.KeyMappingConditional) || !(c = (ADKeyMappings.KeyMappingConditional)m).isActive(player)) continue;
                hasActiveConditional = true;
                break;
            }
            for (KeyMapping m : f_90809_.values()) {
                boolean bl;
                accessor = (ADKeyMappings.KeyMappingExtension)m;
                if (!accessor.getKey().equals((Object)key)) continue;
                if (m instanceof ADKeyMappings.KeyMappingConditional) {
                    ADKeyMappings.KeyMappingConditional c = (ADKeyMappings.KeyMappingConditional)m;
                    bl = c.isActive(player);
                } else {
                    bl = !hasActiveConditional;
                }
                boolean isActive = bl;
                action.accept(accessor, isActive);
            }
        }
    }
}

