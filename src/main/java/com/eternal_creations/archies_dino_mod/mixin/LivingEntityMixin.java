/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.core.Direction$Plane
 *  net.minecraft.core.particles.BlockParticleOption
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.Vec3
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.items.armor.LushsaurArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.armor.ObsidiceratopsArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.common.items.weapon.TherimossClawsItem;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={LivingEntity.class})
public class LivingEntityMixin {
    @Inject(method={"getVisibilityPercent"}, at={@At(value="RETURN")}, cancellable=true)
    private void archiesDinoMod$onGetVisibilityPercent(Entity lookingEntity, CallbackInfoReturnable<Double> cir) {
        Map<EquipmentSlot, ItemStack> armorSet;
        LivingEntity living = (LivingEntity)this;
        if (living.m_20163_() && (armorSet = ADArmorItem.getArmorSet(living)).size() == 4 && armorSet.get(EquipmentSlot.HEAD).m_41720_() instanceof LushsaurArmorItem) {
            double dz;
            boolean moving;
            double dx = living.m_20185_() - living.f_19854_;
            boolean bl = moving = dx * dx + (dz = living.m_20189_() - living.f_19856_) * dz > 1.0E-7;
            if (moving) {
                cir.setReturnValue((Object)(cir.getReturnValueD() * 0.5));
            } else {
                cir.setReturnValue((Object)(cir.getReturnValueD() * 0.0));
            }
        }
    }

    @Inject(method={"travel"}, at={@At(value="TAIL")})
    private void archiesDinoMod$applyWallSlide(Vec3 travelVector, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)this;
        if (entity.m_21093_(stack -> stack.m_41720_() instanceof TherimossClawsItem) && entity.f_19862_ && !entity.m_20096_() && !entity.m_20072_()) {
            Vec3 velocity = entity.m_20184_();
            double maxSlideSpeed = -0.15;
            if (velocity.f_82480_ < maxSlideSpeed) {
                entity.m_20334_(velocity.f_82479_, maxSlideSpeed, velocity.f_82481_);
                entity.f_19789_ = 0.0f;
                if (entity.m_9236_().f_46443_) {
                    this.archiesDinoMod$spawnWallParticles(entity);
                }
            }
        }
    }

    @Inject(method={"causeFallDamage"}, at={@At(value="HEAD")}, cancellable=true)
    private void archiesDinoMod$cancelFallDamage(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity)this;
        if (entity.m_21093_(stack -> stack.m_41720_() instanceof TherimossClawsItem) && (entity.f_19862_ || entity.m_20184_().f_82480_ > -0.35)) {
            cir.setReturnValue((Object)false);
        }
    }

    @Inject(method={"canStandOnFluid"}, at={@At(value="HEAD")}, cancellable=true)
    public void archiesDinoMod$canStandOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living;
        if (fluidState.m_205070_(FluidTags.f_13132_) && ADArmorItem.checkFullSet(living = (LivingEntity)this, ObsidiceratopsArmorItem.class)) {
            if (living.m_20163_()) {
                return;
            }
            if (living.m_204036_(FluidTags.f_13132_) >= 0.4) {
                return;
            }
            cir.setReturnValue((Object)true);
        }
    }

    @Unique
    private void archiesDinoMod$spawnWallParticles(LivingEntity entity) {
        RandomSource random = entity.m_217043_();
        Level level = entity.m_9236_();
        if (random.m_188503_(2) != 0) {
            return;
        }
        BlockPos entityPos = entity.m_20183_();
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            BlockPos wallPos = entityPos.m_121945_(dir);
            BlockState state = level.m_8055_(wallPos);
            if (!state.m_280296_()) continue;
            double px = entity.m_20185_() + (double)dir.m_122429_() * ((double)entity.m_20205_() / 2.0);
            double py = entity.m_20186_() + random.m_188500_() * (double)entity.m_20206_() * 0.8;
            double pz = entity.m_20189_() + (double)dir.m_122431_() * ((double)entity.m_20205_() / 2.0);
            if (dir.m_122434_() == Direction.Axis.Z) {
                px += (random.m_188500_() - 0.5) * ((double)entity.m_20205_() * 0.8);
            } else {
                pz += (random.m_188500_() - 0.5) * ((double)entity.m_20205_() * 0.8);
            }
            level.m_7106_((ParticleOptions)new BlockParticleOption(ParticleTypes.f_123794_, state), px, py, pz, 0.0, 0.1, 0.0);
            break;
        }
    }

    @Inject(method={"causeFallDamage"}, at={@At(value="HEAD")}, cancellable=true)
    private void beforeFallDamage(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity dis = (LivingEntity)this;
        if (!dis.m_9236_().f_46443_ && dis.m_21124_((MobEffect)ADMobEffects.FALL_NEGATION.get()) != null) {
            cir.setReturnValue((Object)false);
        }
    }
}

