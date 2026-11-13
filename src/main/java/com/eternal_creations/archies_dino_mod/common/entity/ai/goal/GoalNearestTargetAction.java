/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GoalNearestTargetAction<T extends LivingEntity>
extends TargetGoal {
    private final Class<T> targetType;
    private final int randomInterval;
    private final double customRadiusXZ;
    private final double customRadiusY;
    private final boolean useConeCheck;
    private final double coneAngleMinDot;
    private final TargetHandler onTargetFound;
    private final Runnable onTargetLost;
    @Nullable
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    private GoalNearestTargetAction(Mob mob, Builder<T> builder) {
        super(mob, builder.mustSee, builder.mustReach);
        this.targetType = builder.targetType;
        this.randomInterval = builder.randomInterval;
        this.customRadiusXZ = builder.radiusXZ;
        this.customRadiusY = builder.radiusY;
        this.useConeCheck = builder.useConeCheck;
        this.coneAngleMinDot = builder.coneAngleMinDot;
        this.onTargetFound = builder.onTargetFound;
        this.onTargetLost = builder.onTargetLost;
        Predicate<LivingEntity> customPredicate = builder.customPredicate;
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
        this.targetConditions = TargetingConditions.m_148352_().m_26883_(this.m_7623_()).m_26888_(customPredicate);
        if (builder.useConeCheck) {
            this.targetConditions.m_148355_();
        }
    }

    public boolean m_8036_() {
        Pet pet;
        if (this.randomInterval > 0 && this.f_26135_.m_217043_().m_188503_(this.randomInterval) != 0) {
            return false;
        }
        Mob mob = this.f_26135_;
        if (mob instanceof Pet && (pet = (Pet)mob).isRiddenByOwner()) {
            return false;
        }
        this.findTarget();
        return this.target != null;
    }

    protected AABB getTargetSearchArea() {
        double rangeXZ = this.customRadiusXZ > 0.0 ? this.customRadiusXZ : this.m_7623_();
        double rangeY = this.customRadiusY > 0.0 ? this.customRadiusY : 4.0;
        return this.f_26135_.m_20191_().m_82377_(rangeXZ, rangeY, rangeXZ);
    }

    protected void findTarget() {
        List entities = this.f_26135_.m_9236_().m_6443_(this.targetType, this.getTargetSearchArea(), entity -> true);
        this.target = this.f_26135_.m_9236_().m_45982_(entities, this.targetConditions, (LivingEntity)this.f_26135_, this.f_26135_.m_20185_(), this.f_26135_.m_20188_(), this.f_26135_.m_20189_());
        if (this.target != null && this.useConeCheck && !this.checkConeOfSight(this.target)) {
            this.target = null;
        }
    }

    private boolean checkConeOfSight(LivingEntity potentialTarget) {
        double dz;
        if (!this.f_26135_.m_21574_().m_148306_((Entity)potentialTarget)) {
            return false;
        }
        double dx = potentialTarget.m_20185_() - this.f_26135_.m_20185_();
        double distanceSq = dx * dx + (dz = potentialTarget.m_20189_() - this.f_26135_.m_20189_()) * dz;
        if (distanceSq <= 2.25) {
            return true;
        }
        double distanceXZ = Math.sqrt(distanceSq);
        Vec3 lookVec = Vec3.m_82498_((float)0.0f, (float)this.f_26135_.m_146908_());
        double dotProduct = (dx /= distanceXZ) * lookVec.f_82479_ + (dz /= distanceXZ) * lookVec.f_82481_;
        return dotProduct >= this.coneAngleMinDot;
    }

    public void m_8056_() {
        this.f_26135_.m_6710_(this.target);
        this.f_26137_ = this.target;
        if (this.target != null) {
            this.onTargetFound.handle(this.f_26135_, this.target);
        }
        super.m_8056_();
    }

    public void m_8041_() {
        super.m_8041_();
        this.target = null;
        this.onTargetLost.run();
    }

    public boolean m_8045_() {
        Pet pet;
        boolean canContinue = super.m_8045_();
        Mob mob = this.f_26135_;
        if (mob instanceof Pet && (pet = (Pet)mob).isRiddenByOwner()) {
            return false;
        }
        if (!canContinue) {
            this.onTargetLost.run();
        }
        return canContinue;
    }

    public static <T extends LivingEntity> Builder<T> of(Class<T> targetType) {
        return new Builder<T>(targetType);
    }

    public static class Builder<T extends LivingEntity> {
        private final Class<T> targetType;
        private int randomInterval = 10;
        private boolean mustSee = true;
        private boolean mustReach = false;
        private double radiusXZ = -1.0;
        private double radiusY = -1.0;
        private boolean useConeCheck = false;
        private double coneAngleMinDot = 0.0;
        private TargetHandler onTargetFound = (mob, target) -> {};
        private Runnable onTargetLost = () -> {};
        private Predicate<LivingEntity> customPredicate = entity -> true;

        public Builder(Class<T> targetType) {
            this.targetType = targetType;
        }

        public Builder<T> interval(int ticks) {
            this.randomInterval = ticks;
            return this;
        }

        public Builder<T> mustSee(boolean mustSee) {
            this.mustSee = mustSee;
            return this;
        }

        public Builder<T> mustReach(boolean mustReach) {
            this.mustReach = mustReach;
            return this;
        }

        public Builder<T> customRadius(double radiusXZ, double radiusY) {
            this.radiusXZ = radiusXZ;
            this.radiusY = radiusY;
            return this;
        }

        public Builder<T> withConeFov(float fovDegrees) {
            this.useConeCheck = true;
            this.coneAngleMinDot = Math.cos(Math.toRadians(fovDegrees / 2.0f));
            return this;
        }

        public Builder<T> onTargetFound(TargetHandler handler) {
            this.onTargetFound = handler;
            return this;
        }

        public Builder<T> onTargetLost(Runnable runnable) {
            this.onTargetLost = runnable;
            return this;
        }

        public Builder<T> filter(Predicate<LivingEntity> predicate) {
            this.customPredicate = predicate;
            return this;
        }

        public GoalNearestTargetAction<T> build(Mob mob) {
            return new GoalNearestTargetAction(mob, this);
        }
    }

    @FunctionalInterface
    public static interface TargetHandler {
        public void handle(Mob var1, LivingEntity var2);
    }
}

