/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.EntityHitResult
 */
package com.eternal_creations.archies_dino_mod.common.entity.projectile;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PusTippedArrowEntity
extends AbstractArrow {
    public static final ResourceLocation TEXTURE = Constants.prefix("textures/entity/projectiles/pus_tipped_arrow.png");
    private static final int EFFECT_DURATION = 200;

    public PusTippedArrowEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public PusTippedArrowEntity(Level level, LivingEntity shooter) {
        super(ADEntityTypes.PUS_TIPPED_ARROW.get(), shooter, level);
        this.m_5602_((Entity)shooter);
        this.m_6034_(shooter.m_20185_(), shooter.m_20188_() - 0.1, shooter.m_20189_());
    }

    protected void m_8060_(BlockHitResult result) {
        Level level = this.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_220407_(ADGameEvents.PUS_ARROW_AGGRO.get(), result.m_82425_(), GameEvent.Context.m_223722_((BlockState)serverLevel.m_8055_(result.m_82425_())));
        }
        super.m_8060_(result);
    }

    protected void m_5790_(EntityHitResult result) {
        if (result.m_82443_().m_6095_().m_204039_(ADTags.EntityTypes.SCULK)) {
            this.m_36781_(this.m_36789_() * 4.0);
        } else {
            this.m_36781_(this.m_36789_() * 2.0);
        }
        super.m_5790_(result);
    }

    protected void m_7761_(LivingEntity living) {
        living.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 200, 0));
        living.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 0));
        living.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 0));
        Level level = living.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_220400_((Entity)living, ADGameEvents.PUS_ARROW_AGGRO.get(), living.m_20182_());
        }
    }

    protected ItemStack m_7941_() {
        return new ItemStack((ItemLike)ADItems.OOZE_TIPPED_ARROW.get());
    }
}

