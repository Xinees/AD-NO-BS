/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.core.system.TransformSystem
 *  com.astemir.astemirlib.re.common.extension.EntityExtension
 *  com.astemir.astemirlib.re.core.math.Transform
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.astemir.astemirlib.re.client.model.core.system.TransformSystem;
import com.astemir.astemirlib.re.common.extension.EntityExtension;
import com.astemir.astemirlib.re.core.math.Transform;
import com.eternal_creations.archies_dino_mod.client.model.render.entity.mob.CoralosaurClient;
import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.networking.serverbound.CoralosaurChestMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={MultiPlayerGameMode.class})
public class MultiplayerGameModeMixin {
    @Unique
    private static final double archies_Dino_Mod$DISTANCE = 1.0;

    @Inject(method={"interact"}, at={@At(value="HEAD")}, cancellable=true)
    private void archiesDinoMod$onInteract(Player player, Entity target, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (target instanceof CoralosaurEntity) {
            CoralosaurEntity coralosaur = (CoralosaurEntity)target;
            float partialTick = Minecraft.m_91087_().m_91297_();
            double nearestDistance = Double.MAX_VALUE;
            int nearestChest = -1;
            boolean status = false;
            for (int chest = 0; chest < 8; ++chest) {
                Vec3 pos = MultiplayerGameModeMixin.archiesDinoMod$locatorPos(coralosaur, CoralosaurClient.CONTAINERS[chest]);
                double distance = MultiplayerGameModeMixin.archiesDinoMod$isLookingAtModelSpot(player.m_20299_(partialTick), player.m_20252_(partialTick), (Entity)coralosaur, pos);
                if (distance > 1.0 || !(distance < nearestDistance)) continue;
                nearestDistance = distance;
                nearestChest = chest;
                status = coralosaur.hasChest(chest);
            }
            if (nearestChest == -1) {
                return;
            }
            ItemStack stack = player.m_21120_(hand);
            if (!status) {
                if (stack.m_150930_(Items.f_42009_)) {
                    Services.NETWORK.sendToServer(new CoralosaurChestMessage(coralosaur.m_19879_(), nearestChest, true));
                    cir.setReturnValue((Object)InteractionResult.SUCCESS);
                }
            } else {
                Services.NETWORK.sendToServer(new CoralosaurChestMessage(coralosaur.m_19879_(), nearestChest, false));
                cir.setReturnValue((Object)InteractionResult.SUCCESS);
            }
        }
    }

    @Unique
    private static Vec3 archiesDinoMod$locatorPos(CoralosaurEntity coralosaur, String locator) {
        TransformSystem system = EntityExtension.as((Entity)coralosaur).getCurrentTransformSystem();
        if (system == null) {
            return coralosaur.m_20182_();
        }
        Transform transform = system.getLocatorTransform((Object)coralosaur, locator);
        return new Vec3((double)transform.getX(), (double)transform.getY(), (double)transform.getZ());
    }

    @Unique
    private static double archiesDinoMod$isLookingAtModelSpot(Vec3 playerHeadPos, Vec3 lookDirection, Entity targetEntity, Vec3 modelSpotPos) {
        Vec3 toSpot = targetEntity.m_20182_().m_82549_(modelSpotPos).m_82546_(playerHeadPos);
        double distanceToSpot = toSpot.m_82553_();
        double dotProduct = lookDirection.m_82526_(toSpot);
        double cosTheta = dotProduct / distanceToSpot;
        double angularDeviation = Math.acos(Math.max(-1.0, Math.min(1.0, cosTheta)));
        return angularDeviation * distanceToSpot;
    }
}

