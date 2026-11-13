/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.MinecraftServer
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin.archie;

import com.eternal_creations.archies_dino_mod.util.handlers.ArchieHandler;
import com.eternal_creations.archies_dino_mod.util.handlers.SculksaurHandler;
import java.util.function.BooleanSupplier;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MinecraftServer.class})
public abstract class MinecraftServerMixin {
    @Inject(method={"tickChildren"}, at={@At(value="INVOKE", target="Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", ordinal=2)})
    private void tickMixin(BooleanSupplier hasTimeLeft, CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer)this;
        ArchieHandler.getArchieHandler(server, handler -> handler.tick(server));
        SculksaurHandler.getSculksaurHandler(server, handler -> handler.tick(server));
    }
}

