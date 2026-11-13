/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.Mth
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.food.FoodData
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.effect.MobEffectHungerReduction;
import com.eternal_creations.archies_dino_mod.common.trigger.ADCriteriaTriggers;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={Player.class})
public abstract class PlayerMixin
extends LivingEntity {
    @Unique
    private static final int archies_Dino_Mod$SPOT_DISTANCE = 25;
    @Shadow
    protected FoodData f_36097_;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    private void archiesDinoMod$enforceTwoHanded(CallbackInfo ci) {
        Player player = (Player)this;
        if (player instanceof ServerPlayer) {
            ServerPlayer player2 = (ServerPlayer)player;
            if (this.f_19797_ % 40 == 0) {
                Vec3 viewVector = this.m_20252_(1.0f).m_82541_();
                player2.m_284548_().m_6249_((Entity)this, this.m_20191_().m_82400_(25.0), entity -> {
                    double scale = 25.0;
                    if (entity instanceof LivingEntity) {
                        LivingEntity living = (LivingEntity)entity;
                        scale *= living.m_20968_((Entity)player2);
                    }
                    if (entity.m_20280_((Entity)player2) > scale * scale) {
                        return false;
                    }
                    Vec3 entityPos = new Vec3(entity.m_20185_(), entity.m_20188_(), entity.m_20189_());
                    Vec3 posDiff = entityPos.m_82546_(this.m_146892_());
                    double distance = posDiff.m_82553_();
                    double dot = viewVector.m_82526_(posDiff = posDiff.m_82541_());
                    return dot > (double)0.85f - 0.025 / distance && this.archies_Dino_Mod$hasLongLineOfSight((Entity)entity, 25);
                }).forEach(entity -> ADCriteriaTriggers.SPOTTED_ENTITY.trigger(player2, (Entity)entity));
            }
            ItemStack mainHand = player2.m_21205_();
            ItemStack offHand = player2.m_21206_();
            if (PlayerMixin.archiesDinoMod$isTwoHanded(mainHand) && !offHand.m_41619_()) {
                ItemStack offhandCopy = offHand.m_41777_();
                player2.m_150109_().f_35976_.set(0, (Object)ItemStack.f_41583_);
                if (!player2.m_36356_(offhandCopy)) {
                    player2.m_7197_(offhandCopy, false, false);
                }
                player2.f_36096_.m_150429_();
            }
        }
    }

    @Unique
    public boolean archies_Dino_Mod$hasLongLineOfSight(Entity entity, int distance) {
        if (entity.m_9236_() != this.m_9236_()) {
            return false;
        }
        Vec3 vec3 = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
        Vec3 vec31 = new Vec3(entity.m_20185_(), entity.m_20188_(), entity.m_20189_());
        if (vec31.m_82554_(vec3) > (double)(distance * distance)) {
            return false;
        }
        return this.m_9236_().m_45547_(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity)this)).m_6662_() == HitResult.Type.MISS;
    }

    @Unique
    private static boolean archiesDinoMod$isTwoHanded(ItemStack stack) {
        return stack.m_204117_(ADTags.Items.TWO_HANDED);
    }

    @Inject(method={"causeFoodExhaustion"}, at={@At(value="INVOKE", target="Lnet/minecraft/world/food/FoodData;addExhaustion(F)V")}, cancellable=true)
    private void onCauseFoodExhaustion(float exhaustion, CallbackInfo ci) {
        Player dis = (Player)this;
        MobEffectInstance effect = dis.m_21124_((MobEffect)ADMobEffects.HUNGER_REDUCTION.get());
        if (effect == null) {
            return;
        }
        double reduction = MobEffectHungerReduction.calculateExhaustion(effect);
        ci.cancel();
        if (!Mth.m_14082_((double)reduction, (double)1.0)) {
            this.f_36097_.m_38703_((float)((double)exhaustion * (1.0 - reduction)));
        }
    }
}

