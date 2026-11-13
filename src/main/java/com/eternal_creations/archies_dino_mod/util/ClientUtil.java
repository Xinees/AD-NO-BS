/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.world.physics.CollisionContext
 *  com.astemir.astemirlib.re.common.world.physics.CollisionContext$Mutable
 *  com.astemir.astemirlib.re.common.world.physics.Raycast
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.util;

import com.astemir.astemirlib.re.common.world.physics.CollisionContext;
import com.astemir.astemirlib.re.common.world.physics.Raycast;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AskShooterIfHitBoneMessage;
import com.eternal_creations.archies_dino_mod.networking.serverbound.PopZitMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ClientUtil {
    private static final Pattern KEY_PATTERN = Pattern.compile("\\{key:(.*?)}");

    public static void handleArrowTick(AbstractArrow arrow) {
        LocalPlayer player;
        Entity entity = arrow.m_19749_();
        if (entity instanceof LocalPlayer && Minecraft.m_91087_().f_91074_ == (player = (LocalPlayer)entity)) {
            ClientUtil.handleArrowTick(arrow, player, player.f_108545_);
        }
    }

    public static void handleArrowTick(AbstractArrow arrow, LocalPlayer player, ClientLevel level) {
        CollisionContext.Mutable out = CollisionContext.create();
        for (Entity entity : level.m_104735_()) {
            Vec3 delta;
            CollisionContext context;
            SculksaurEntity sculksaur;
            Vec3 pos = arrow.m_20318_(Minecraft.m_91087_().m_91297_());
            if (!(entity instanceof SculksaurEntity) || !(sculksaur = (SculksaurEntity)entity).m_6921_().m_82390_(pos) || !(context = ClientUtil.rayCastSpecificMob((Entity)sculksaur, pos, delta = arrow.m_20184_(), sculksaur.m_6921_().m_82309_(), 0.5, null, out)).hasHitBone() || !Objects.requireNonNull(context.getHitBoneName()).contains("wart")) continue;
            Services.NETWORK.sendToServer(new PopZitMessage(sculksaur.m_20148_(), player.m_20148_(), context.getHitPos(), context.getHitBoneName()));
        }
    }

    public static void handleArrowHit(AskShooterIfHitBoneMessage message) {
        Entity entity;
        Vec3 newPos = message.getArrowPos();
        if (Minecraft.m_91087_().f_91073_ != null && (entity = Minecraft.m_91087_().f_91073_.m_6815_(message.getVictim())) instanceof SculksaurEntity) {
            SculksaurEntity sculksaur = (SculksaurEntity)entity;
            CollisionContext.Mutable out = CollisionContext.create();
            for (int i = 0; i < 20; ++i) {
                if (!sculksaur.m_6921_().m_82390_(newPos)) {
                    return;
                }
                CollisionContext context = ClientUtil.rayCastSpecificMob((Entity)sculksaur, newPos, message.getArrowDelta(), sculksaur.m_6921_().m_82309_(), 0.5, message.getBoneName(), out);
                if (context.hasHitBone() && Objects.requireNonNull(context.getHitBoneName()).contains("wart")) {
                    Services.NETWORK.sendToServer(new PopZitMessage(sculksaur.m_20148_(), message.getOwner(), context.getHitPos(), context.getHitBoneName()));
                    return;
                }
                newPos = newPos.m_82549_(message.getArrowDelta());
            }
        }
    }

    public static void addMultilineTooltip(String key, List<Component> output, Object ... args) {
        String translated = I18n.m_118938_((String)key, (Object[])args);
        Matcher matcher = KEY_PATTERN.matcher(translated);
        translated = matcher.replaceAll(mr -> Component.m_237117_((String)mr.group(1)).getString());
        for (String line : translated.split("\n")) {
            output.add((Component)Component.m_237113_((String)line));
        }
    }

    private static CollisionContext rayCastSpecificMob(Entity target, Vec3 pos, Vec3 delta, double entityInflation, double boneInflation, @Nullable String targetBoneName, CollisionContext.Mutable out) {
        Raycast.rayCastBone((Entity)target, (double)pos.f_82479_, (double)pos.f_82480_, (double)pos.f_82481_, (double)delta.f_82479_, (double)delta.f_82480_, (double)delta.f_82481_, (double)entityInflation, (double)boneInflation, (String)targetBoneName, (CollisionContext.Mutable)out);
        return out;
    }
}

