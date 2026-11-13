/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.decoration.HangingEntity
 *  net.minecraft.world.entity.decoration.LeashFenceKnotEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.Boat
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.Nullable
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin.coralosaur;

import com.eternal_creations.archies_dino_mod.common.entity.mob.coralosaur.CoralosaurEntity;
import com.eternal_creations.archies_dino_mod.util.IBoatWithLead;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={Boat.class})
public abstract class BoatMixin
extends Entity
implements IBoatWithLead {
    @Unique
    @Nullable
    private Entity archies_Dino_Mod$leashHolder;
    @Unique
    private int archies_Dino_Mod$delayedLeashHolderId;
    @Unique
    @Nullable
    private CompoundTag archies_Dino_Mod$leashInfoTag;

    public BoatMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    @Nullable
    public Entity archies_Dino_Mod$getLeashHolder() {
        if (this.archies_Dino_Mod$leashHolder == null && this.archies_Dino_Mod$delayedLeashHolderId != 0 && this.m_9236_().f_46443_) {
            this.archies_Dino_Mod$leashHolder = this.m_9236_().m_6815_(this.archies_Dino_Mod$delayedLeashHolderId);
        }
        return this.archies_Dino_Mod$leashHolder;
    }

    @Override
    public void archies_Dino_Mod$setLeashedTo(Entity leashHolder, boolean broadcastPacket) {
        Level level;
        this.archies_Dino_Mod$leashHolder = leashHolder;
        this.archies_Dino_Mod$leashInfoTag = null;
        if (!this.m_9236_().f_46443_ && broadcastPacket && (level = this.m_9236_()) instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_7726_().m_8445_((Entity)this, (Packet)new ClientboundSetEntityLinkPacket((Entity)this, this.archies_Dino_Mod$leashHolder));
        }
    }

    @Override
    public boolean archies_Dino_Mod$isLeashed() {
        return this.archies_Dino_Mod$leashHolder != null;
    }

    @Override
    public void archies_Dino_Mod$setDelayedLeashHolderId(int leashHolderID) {
        this.archies_Dino_Mod$delayedLeashHolderId = leashHolderID;
        this.archies_Dino_Mod$dropLeash(false, false);
    }

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    private void onTick(CallbackInfo ci) {
        Entity entity;
        if (this.m_9236_().f_46443_) {
            return;
        }
        if (this.archies_Dino_Mod$leashInfoTag != null) {
            this.archies_Dino_Mod$restoreLeashFromSave();
        }
        if (!(this.archies_Dino_Mod$leashHolder == null || this.m_6084_() && this.archies_Dino_Mod$leashHolder.m_6084_())) {
            this.archies_Dino_Mod$dropLeash(true, true);
        }
        if ((entity = this.archies_Dino_Mod$getLeashHolder()) != null && entity.m_9236_() == this.m_9236_()) {
            float distance = this.m_20270_(entity);
            if (distance > 20.0f) {
                this.archies_Dino_Mod$dropLeash(true, true);
            } else if ((double)distance > 6.0) {
                double x = (entity.m_20185_() - this.m_20185_()) / (double)distance;
                double z = (entity.m_20189_() - this.m_20189_()) / (double)distance;
                this.m_20256_(this.m_20184_().m_82520_(Math.copySign(x * x * 0.4, x), 0.0, Math.copySign(z * z * 0.4, z)));
            }
        }
    }

    @Inject(method={"interact"}, at={@At(value="HEAD")}, cancellable=true)
    private void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        List list = player.m_9236_().m_6443_(CoralosaurEntity.class, player.m_20191_().m_82400_(32.0), coralosaur -> coralosaur.isOwnedBy((LivingEntity)player) && coralosaur.m_6254_() && coralosaur.m_21523_());
        double closest = -1.0;
        CoralosaurEntity coralosaur2 = null;
        for (CoralosaurEntity coralosaurEntity : list) {
            double d1 = coralosaurEntity.m_20280_((Entity)player);
            if (closest != -1.0 && !(d1 < closest)) continue;
            closest = d1;
            coralosaur2 = coralosaurEntity;
        }
        if (coralosaur2 != null) {
            coralosaur2.m_21455_(true, false);
            this.archies_Dino_Mod$setLeashedTo((Entity)coralosaur2, true);
            cir.setReturnValue((Object)InteractionResult.SUCCESS);
        }
    }

    @Unique
    private void archies_Dino_Mod$restoreLeashFromSave() {
        if (this.archies_Dino_Mod$leashInfoTag != null && this.m_9236_() instanceof ServerLevel) {
            if (this.archies_Dino_Mod$leashInfoTag.m_128403_("UUID")) {
                UUID uUID = this.archies_Dino_Mod$leashInfoTag.m_128342_("UUID");
                Entity entity = ((ServerLevel)this.m_9236_()).m_8791_(uUID);
                if (entity != null) {
                    this.archies_Dino_Mod$setLeashedTo(entity, true);
                    return;
                }
            } else if (this.archies_Dino_Mod$leashInfoTag.m_128425_("X", 99) && this.archies_Dino_Mod$leashInfoTag.m_128425_("Y", 99) && this.archies_Dino_Mod$leashInfoTag.m_128425_("Z", 99)) {
                BlockPos blockPos = NbtUtils.m_129239_((CompoundTag)this.archies_Dino_Mod$leashInfoTag);
                this.archies_Dino_Mod$setLeashedTo((Entity)LeashFenceKnotEntity.m_31844_((Level)this.m_9236_(), (BlockPos)blockPos), true);
                return;
            }
            if (this.f_19797_ > 100) {
                this.m_19998_((ItemLike)Items.f_42655_);
                this.archies_Dino_Mod$leashInfoTag = null;
            }
        }
    }

    @Unique
    private void archies_Dino_Mod$dropLeash(boolean broadcastPacket, boolean dropLeash) {
        if (this.archies_Dino_Mod$leashHolder != null) {
            this.archies_Dino_Mod$leashHolder = null;
            this.archies_Dino_Mod$leashInfoTag = null;
            if (!this.m_9236_().f_46443_ && dropLeash) {
                this.m_19998_((ItemLike)Items.f_42655_);
            }
            if (!this.m_9236_().f_46443_ && broadcastPacket && this.m_9236_() instanceof ServerLevel) {
                ((ServerLevel)this.m_9236_()).m_7726_().m_8445_((Entity)this, (Packet)new ClientboundSetEntityLinkPacket((Entity)this, null));
            }
        }
    }

    @Inject(method={"addAdditionalSaveData"}, at={@At(value="TAIL")})
    private void onAddAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (this.archies_Dino_Mod$leashHolder != null) {
            CompoundTag compoundTag = new CompoundTag();
            if (this.archies_Dino_Mod$leashHolder instanceof LivingEntity) {
                UUID uUID = this.archies_Dino_Mod$leashHolder.m_20148_();
                compoundTag.m_128362_("UUID", uUID);
            } else if (this.archies_Dino_Mod$leashHolder instanceof HangingEntity) {
                BlockPos blockPos = ((HangingEntity)this.archies_Dino_Mod$leashHolder).m_31748_();
                compoundTag.m_128405_("X", blockPos.m_123341_());
                compoundTag.m_128405_("Y", blockPos.m_123342_());
                compoundTag.m_128405_("Z", blockPos.m_123343_());
            }
            tag.m_128365_("Leash", (Tag)compoundTag);
        } else if (this.archies_Dino_Mod$leashInfoTag != null) {
            tag.m_128365_("Leash", (Tag)this.archies_Dino_Mod$leashInfoTag.m_6426_());
        }
    }

    @Inject(method={"readAdditionalSaveData"}, at={@At(value="TAIL")})
    private void onReadAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.m_128425_("Leash", 10)) {
            this.archies_Dino_Mod$leashInfoTag = tag.m_128469_("Leash");
        }
    }
}

