/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.networking.clientbound.AskShooterIfHitBoneMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import com.eternal_creations.archies_dino_mod.util.IModifiedArrow;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={AbstractArrow.class})
public abstract class AbstractArrowMixin
implements IModifiedArrow {
    @Unique
    private static final EntityDataAccessor<Boolean> archies_Dino_Mod$RIB_BOW_ARROW = SynchedEntityData.m_135353_(AbstractArrow.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);

    @Inject(method={"defineSynchedData"}, at={@At(value="TAIL")})
    private void onDefineSynchedData(CallbackInfo ci) {
        ((AbstractArrow)this).m_20088_().m_135372_(archies_Dino_Mod$RIB_BOW_ARROW, (Object)false);
    }

    @Inject(method={"getWaterInertia"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetWaterInertia(CallbackInfoReturnable<Float> cir) {
        if (((Boolean)((AbstractArrow)this).m_20088_().m_135370_(archies_Dino_Mod$RIB_BOW_ARROW)).booleanValue()) {
            cir.setReturnValue((Object)Float.valueOf(0.999f));
        }
    }

    @Override
    @Unique
    public void archiesDinoMod$setShotFromRibBow(boolean noInertia) {
        ((AbstractArrow)this).m_20088_().m_135381_(archies_Dino_Mod$RIB_BOW_ARROW, (Object)noInertia);
    }

    @Inject(method={"addAdditionalSaveData"}, at={@At(value="TAIL")})
    private void onAddAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.m_128379_("AD_ShotFromRibBow", ((Boolean)((AbstractArrow)this).m_20088_().m_135370_(archies_Dino_Mod$RIB_BOW_ARROW)).booleanValue());
    }

    @Inject(method={"readAdditionalSaveData"}, at={@At(value="TAIL")})
    private void onReadAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.m_128441_("AD_ShotFromRibBow")) {
            ((AbstractArrow)this).m_20088_().m_135381_(archies_Dino_Mod$RIB_BOW_ARROW, (Object)tag.m_128471_("AD_ShotFromRibBow"));
        }
    }

    @Inject(method={"tick"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/phys/Vec3;horizontalDistance()D", ordinal=1)})
    private void onTick(CallbackInfo ci) {
        AbstractArrow dis = (AbstractArrow)this;
        if (((Boolean)dis.m_20088_().m_135370_(archies_Dino_Mod$RIB_BOW_ARROW)).booleanValue()) {
            IModifiedArrow.archiesDinoMod$tick(dis);
        }
        if (dis.m_9236_().f_46443_) {
            ClientUtil.handleArrowTick(dis);
        }
    }

    @Inject(method={"onHitEntity"}, at={@At(value="HEAD")})
    private void onOnHit(EntityHitResult result, CallbackInfo ci) {
        Entity entity;
        if (result.m_6662_() != HitResult.Type.MISS && (entity = result.m_82443_()) instanceof SculksaurEntity) {
            SculksaurEntity sculksaur = (SculksaurEntity)entity;
            AbstractArrow dis = (AbstractArrow)this;
            Entity entity2 = dis.m_19749_();
            if (entity2 instanceof ServerPlayer) {
                ServerPlayer player = (ServerPlayer)entity2;
                Services.NETWORK.sendToClient(player, new AskShooterIfHitBoneMessage(sculksaur.m_19879_(), player.m_20148_(), dis.m_20182_(), dis.m_20184_(), null));
            }
        }
    }
}

