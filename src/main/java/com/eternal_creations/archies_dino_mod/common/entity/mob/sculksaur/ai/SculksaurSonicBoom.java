/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.Mth
 *  net.minecraft.util.Unit
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.memory.MemoryModuleType
 *  net.minecraft.world.entity.ai.memory.MemoryStatus
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.ai;

import com.eternal_creations.archies_dino_mod.common.entity.ai.behaviour.ActionBehaviour;
import com.eternal_creations.archies_dino_mod.common.entity.mob.sculksaur.SculksaurEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculksaurSonicBoom
extends ActionBehaviour<SculksaurEntity> {
    private static final int DISTANCE_XZ = 15;
    private static final int DISTANCE_Y = 20;
    private static final double KNOCKBACK_VERTICAL = 0.5;
    private static final double KNOCKBACK_HORIZONTAL = 2.5;
    public static final int COOLDOWN = 80;
    private static final int TICKS_BEFORE_PLAYING_SOUND = Mth.m_14165_((double)100.0);
    public static final float DURATION = 7.76f;
    private static final int DURATION_IN_TICKS = Mth.m_14167_((float)155.20001f);

    public SculksaurSonicBoom() {
        super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of((Object)MemoryModuleType.f_26372_, (Object)MemoryStatus.VALUE_PRESENT, (Object)MemoryModuleType.f_217775_, (Object)MemoryStatus.VALUE_ABSENT, (Object)MemoryModuleType.f_217776_, (Object)MemoryStatus.REGISTERED, (Object)MemoryModuleType.f_217777_, (Object)MemoryStatus.REGISTERED), e -> e.actionAttackSonicBoom);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, SculksaurEntity sculksaur) {
        LivingEntity target = sculksaur.m_6274_().m_21952_(MemoryModuleType.f_26372_).orElse(null);
        if (target == null) {
            return false;
        }
        if (!sculksaur.isNoActionActive()) {
            return false;
        }
        return !sculksaur.m_216992_((Entity)target, 10.0, 5.0) && sculksaur.m_216992_((Entity)target, 25.0, 30.0);
    }

    @Override
    protected boolean canStillUse(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        return true;
    }

    @Override
    protected void start(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        sculksaur.m_6274_().m_21882_(MemoryModuleType.f_26373_, (Object)true, (long)DURATION_IN_TICKS);
        sculksaur.m_6274_().m_21882_(MemoryModuleType.f_217777_, (Object)Unit.INSTANCE, (long)TICKS_BEFORE_PLAYING_SOUND);
        level.m_7605_((Entity)sculksaur, (byte)62);
    }

    @Override
    protected void tick(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        sculksaur.m_6274_().m_21952_(MemoryModuleType.f_26372_).ifPresent(sculksaur::stareAtTarget);
        if (!sculksaur.m_6274_().m_21874_(MemoryModuleType.f_217777_) && !sculksaur.m_6274_().m_21874_(MemoryModuleType.f_217776_)) {
            sculksaur.m_6274_().m_21882_(MemoryModuleType.f_217776_, (Object)Unit.INSTANCE, (long)(DURATION_IN_TICKS - TICKS_BEFORE_PLAYING_SOUND));
            sculksaur.m_9236_().m_45933_((Entity)sculksaur, sculksaur.m_20191_().m_82400_(50.0)).forEach(entity -> {
                LivingEntity living;
                if (entity instanceof LivingEntity && sculksaur.canTargetEntity((Entity)(living = (LivingEntity)entity))) {
                    Vec3 vec3 = sculksaur.m_20182_().m_82520_(0.0, (double)1.6f, 0.0);
                    Vec3 vec31 = living.m_146892_().m_82546_(vec3);
                    Vec3 vec32 = vec31.m_82541_();
                    sculksaur.m_19998_((ItemLike)ADItems.SCULK_TOXIN.get());
                    if (living.m_6469_(level.m_269111_().m_269285_((Entity)sculksaur), EntityUtils.multiplyDamage((Mob)sculksaur, living, 2.5f))) {
                        living.m_147207_(new MobEffectInstance(MobEffects.f_216964_, 640, 0), (Entity)sculksaur);
                        double d1 = 0.5 * (1.0 - living.m_21133_(Attributes.f_22278_));
                        double d0 = 2.5 * (1.0 - living.m_21133_(Attributes.f_22278_));
                        living.m_5997_(vec32.m_7096_() * d0, vec32.m_7098_() * d1, vec32.m_7094_() * d0);
                    }
                }
            });
        }
    }

    @Override
    protected void stop(ServerLevel level, SculksaurEntity sculksaur, long gameTime) {
        SculksaurSonicBoom.setCooldown((LivingEntity)sculksaur, 1000);
    }

    public static void setCooldown(LivingEntity entity, int cooldown) {
        entity.m_6274_().m_21882_(MemoryModuleType.f_217775_, (Object)Unit.INSTANCE, (long)cooldown);
    }
}

