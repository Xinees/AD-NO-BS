/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.world.physics.CollisionContext
 *  com.astemir.astemirlib.re.common.world.physics.CollisionContext$Mutable
 *  com.astemir.astemirlib.re.common.world.physics.Raycast
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.util.Mth
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.MobType
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.utils;

import com.astemir.astemirlib.re.common.world.physics.CollisionContext;
import com.astemir.astemirlib.re.common.world.physics.Raycast;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EntityUtils {
    private EntityUtils() {
    }

    public static double getDistanceSqr(Entity e1, Entity e2) {
        return EntityUtils.getDistanceSqr(e1.m_20191_(), e2.m_20191_(), false);
    }

    public static double getHorizontalDistanceSqr(Entity e1, Entity e2) {
        return EntityUtils.getDistanceSqr(e1.m_20191_(), e2.m_20191_(), true);
    }

    public static double getDistanceSqrToPoint(Entity entity, Position point) {
        return EntityUtils.getDistanceSqrToPoint(entity.m_20191_(), point, false);
    }

    public static double getHorizontalDistanceSqrToBlock(Entity entity, BlockPos pos) {
        return EntityUtils.getDistanceSqrToPoint(entity.m_20191_(), (double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5, true);
    }

    public static double getHorizontalDistanceSqrToPoint(Entity entity, Position point) {
        return EntityUtils.getDistanceSqrToPoint(entity.m_20191_(), point, true);
    }

    public static double getDistanceSqrToBlock(Entity entity, BlockPos pos) {
        return EntityUtils.getDistanceSqrToPoint(entity.m_20191_(), (double)pos.m_123341_() + 0.5, pos.m_123342_(), (double)pos.m_123343_() + 0.5, false);
    }

    public static boolean hasReachedPoint(Entity entity, BlockPos point, double marginHorizontal, double marginVertical) {
        return EntityUtils.hasReachedPoint(entity, (double)point.m_123341_() + 0.5, point.m_123342_(), (double)point.m_123343_() + 0.5, marginHorizontal, marginVertical);
    }

    public static boolean hasReachedPoint(Entity entity, Position point, double marginHorizontal, double marginVertical) {
        return EntityUtils.hasReachedPoint(entity, point.m_7096_(), point.m_7098_(), point.m_7094_(), marginHorizontal, marginVertical);
    }

    public static boolean hasReachedPoint(Entity entity, double x, double y, double z, double marginHorizontal, double marginVertical) {
        AABB box = entity.m_20191_();
        double dx = Math.max(0.0, Math.max(box.f_82288_ - x, x - box.f_82291_));
        double dy = Math.max(0.0, Math.max(box.f_82289_ - y, y - box.f_82292_));
        double dz = Math.max(0.0, Math.max(box.f_82290_ - z, z - box.f_82293_));
        double horizontalDistSqr = dx * dx + dz * dz;
        return horizontalDistSqr <= marginHorizontal * marginHorizontal && dy <= marginVertical;
    }

    public static double getDistanceSqr(AABB b1, AABB b2, boolean horizontalOnly) {
        double dx = Math.max(0.0, Math.max(b1.f_82288_ - b2.f_82291_, b2.f_82288_ - b1.f_82291_));
        double dz = Math.max(0.0, Math.max(b1.f_82290_ - b2.f_82293_, b2.f_82290_ - b1.f_82293_));
        if (horizontalOnly) {
            return dx * dx + dz * dz;
        }
        double dy = Math.max(0.0, Math.max(b1.f_82289_ - b2.f_82292_, b2.f_82289_ - b1.f_82292_));
        return dx * dx + dy * dy + dz * dz;
    }

    public static double getDistanceSqrToPoint(AABB box, Position point, boolean horizontalOnly) {
        return EntityUtils.getDistanceSqrToPoint(box, point.m_7096_(), point.m_7098_(), point.m_7094_(), horizontalOnly);
    }

    public static double getDistanceSqrToPoint(AABB box, double x, double y, double z, boolean horizontalOnly) {
        double dx = Math.max(0.0, Math.max(box.f_82288_ - x, x - box.f_82291_));
        double dz = Math.max(0.0, Math.max(box.f_82290_ - z, z - box.f_82293_));
        if (horizontalOnly) {
            return dx * dx + dz * dz;
        }
        double dy = Math.max(0.0, Math.max(box.f_82289_ - y, y - box.f_82292_));
        return dx * dx + dy * dy + dz * dz;
    }

    public static float multiplyDamage(Mob mob, LivingEntity target, float damageMultiplier) {
        AgeableMob ageableMob;
        if (mob instanceof AgeableMob && (ageableMob = (AgeableMob)mob).m_6162_()) {
            damageMultiplier /= 3.0f;
        }
        return ((float)mob.m_21133_(Attributes.f_22281_) + EnchantmentHelper.m_44833_((ItemStack)mob.m_21205_(), (MobType)target.m_6336_())) * damageMultiplier;
    }

    public static float multiplyKnockback(Mob mob, float knockbackMultiplier) {
        return ((float)mob.m_21133_(Attributes.f_22282_) + (float)EnchantmentHelper.m_44894_((LivingEntity)mob)) * knockbackMultiplier;
    }

    public static boolean attackTarget(Mob mob, @Nullable LivingEntity target, float damageMul, float knockbackMul) {
        if (target == null) {
            return false;
        }
        if (!mob.m_217066_(target)) {
            return false;
        }
        if (!mob.m_6779_(target)) {
            return false;
        }
        damageMul = EntityUtils.multiplyDamage(mob, target, damageMul);
        knockbackMul = EntityUtils.multiplyKnockback(mob, knockbackMul);
        if (target.m_6469_(mob.m_269291_().m_269333_((LivingEntity)mob), damageMul)) {
            Vec3 toTarget = new Vec3(target.m_20185_() - mob.m_20185_(), 0.0, target.m_20189_() - mob.m_20189_()).m_82541_();
            target.m_147240_((double)knockbackMul, -toTarget.f_82479_, -toTarget.f_82481_);
            return true;
        }
        return false;
    }

    public static void attackForward(Mob mob, float attackRadius, float attackAngleCos, float knockbackMul, float damageMul, Consumer<LivingEntity> postDamageEffect) {
        if (mob.m_9236_().m_5776_()) {
            return;
        }
        AABB searchArea = mob.m_20191_().m_82377_((double)attackRadius, (double)(mob.m_20206_() * 0.5f), (double)attackRadius);
        List targets = mob.m_9236_().m_45976_(LivingEntity.class, searchArea);
        Vec3 lookVector = EntityUtils.getViewVector((LivingEntity)mob);
        for (LivingEntity target : targets) {
            DamageSource damageSource;
            if (target == mob || !mob.m_6779_(target)) continue;
            float damage = EntityUtils.multiplyDamage(mob, target, damageMul);
            float knockback = EntityUtils.multiplyKnockback(mob, knockbackMul);
            Vec3 toTarget = new Vec3(target.m_20185_() - mob.m_20185_(), 0.0, target.m_20189_() - mob.m_20189_()).m_82541_();
            Vec3 flatLook = new Vec3(lookVector.f_82479_, 0.0, lookVector.f_82481_).m_82541_();
            double dotProduct = flatLook.m_82526_(toTarget);
            if (!(dotProduct >= (double)attackAngleCos) || !target.m_6469_(damageSource = mob.m_269291_().m_269333_((LivingEntity)mob), damage)) continue;
            postDamageEffect.accept(target);
            target.m_147240_((double)knockback, -toTarget.f_82479_, -toTarget.f_82481_);
        }
    }

    public static void breakBlocksForward(Mob mob, double reachDistance, float breakRadius, float chance, boolean dropResources, Predicate<BlockState> predicate) {
        Vec3 impactPoint;
        Level level = mob.m_9236_();
        if (level.m_5776_()) {
            return;
        }
        CollisionContext.Mutable collision = CollisionContext.create();
        Raycast.rayCastBlocks((Level)level, (Entity)mob, (double)reachDistance, predicate, (CollisionContext.Mutable)collision);
        if (collision.hasHit()) {
            impactPoint = new Vec3(collision.getHitPos().x, collision.getHitPos().y, collision.getHitPos().z);
        } else {
            Vec3 eyePos = mob.m_146892_();
            impactPoint = eyePos.m_82549_(mob.m_20154_().m_82490_(reachDistance));
        }
        int r = Mth.m_14167_((float)breakRadius);
        double radiusSq = breakRadius * breakRadius;
        BlockPos centerPos = BlockPos.m_274446_((Position)impactPoint);
        for (BlockPos pos : BlockPos.m_121976_((int)(centerPos.m_123341_() - r), (int)(centerPos.m_123342_() - r), (int)(centerPos.m_123343_() - r), (int)(centerPos.m_123341_() + r), (int)(centerPos.m_123342_() + r), (int)(centerPos.m_123343_() + r))) {
            BlockState state;
            if (!(pos.m_203193_((Position)impactPoint) <= radiusSq) || (state = level.m_8055_(pos)).m_60795_() || !(chance >= 1.0f) && !(level.f_46441_.m_188501_() < chance) || !predicate.test(state)) continue;
            level.m_46953_(pos, dropResources, (Entity)mob);
        }
    }

    public static Vec3 getViewVector(LivingEntity livingEntity) {
        float xRot = livingEntity.m_146909_();
        float yRot = livingEntity.f_20883_;
        float f = xRot * ((float)Math.PI / 180);
        float f1 = -yRot * ((float)Math.PI / 180);
        float f2 = Mth.m_14089_((float)f1);
        float f3 = Mth.m_14031_((float)f1);
        float f4 = Mth.m_14089_((float)f);
        float f5 = Mth.m_14031_((float)f);
        return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4)).m_82541_();
    }
}

