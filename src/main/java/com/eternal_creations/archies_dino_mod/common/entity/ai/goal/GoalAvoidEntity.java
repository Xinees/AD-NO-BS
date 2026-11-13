/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.function.Condition
 *  com.astemir.astemirlib.re.core.math.AMath
 *  com.google.common.base.Predicates
 *  javax.annotation.Nullable
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntitySelector
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.ai.util.GoalUtils
 *  net.minecraft.world.entity.ai.util.RandomPos
 *  net.minecraft.world.level.pathfinder.Path
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.astemir.astemirlib.re.core.function.Condition;
import com.astemir.astemirlib.re.core.math.AMath;
import com.google.common.base.Predicates;
import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class GoalAvoidEntity<T extends LivingEntity>
extends Goal {
    private final PathfinderMob mob;
    private final PathNavigation pathNav;
    private final Class<T> avoidClass;
    private final Condition shouldUse;
    private final TargetingConditions avoidEntityTargeting;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    private final double awareDistance;
    private final float runDistance;
    private final boolean keepFighting;
    private final boolean mustSee;
    @Nullable
    protected Path path;
    @Nullable
    protected T avoidTarget;

    private GoalAvoidEntity(PathfinderMob mob, Builder<T> builder) {
        this.mob = mob;
        this.avoidClass = builder.avoidClass;
        this.awareDistance = builder.awareDistance;
        this.runDistance = builder.runDistance;
        this.walkSpeedModifier = builder.walkSpeedModifier;
        this.sprintSpeedModifier = builder.runSpeedModifier;
        this.keepFighting = builder.keepFighting;
        this.shouldUse = builder.shouldUse;
        this.mustSee = builder.mustSee;
        this.pathNav = mob.m_21573_();
        this.avoidEntityTargeting = TargetingConditions.m_148352_().m_26883_(this.awareDistance).m_26888_(((Predicate<LivingEntity>)EntitySelector.f_20406_::test).and(builder.avoidPredicate));
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    public boolean m_8036_() {
        if (!this.shouldUse.check()) {
            return false;
        }
        this.avoidTarget = this.mob.m_9236_().m_45982_(this.mob.m_9236_().m_6443_(this.avoidClass, this.mob.m_20191_().m_82377_(this.awareDistance, 3.0, this.awareDistance), entity -> true), this.avoidEntityTargeting, (LivingEntity)this.mob, this.mob.m_20185_(), this.mob.m_20186_(), this.mob.m_20189_());
        if (this.avoidTarget == null) {
            return false;
        }
        if (this.mustSee) {
            float mobAngle;
            float targetAngle;
            float angleDiff;
            if (!this.mob.m_21574_().m_148306_(this.avoidTarget)) {
                return false;
            }
            double dx = this.avoidTarget.m_20185_() - this.mob.m_20185_();
            double dz = this.avoidTarget.m_20189_() - this.mob.m_20189_();
            if ((dx != 0.0 || dz != 0.0) && AMath.abs((float)(angleDiff = AMath.wrapDegrees((float)((targetAngle = (float)(Math.atan2(dz, dx) * 57.2957763671875) - 90.0f) - (mobAngle = this.mob.m_146908_()))))) > 90.0f) {
                return false;
            }
        }
        if (this.mob.m_5448_() != null && this.keepFighting) {
            return false;
        }
        Vec3 escapePos = this.getBestEscapePos(this.avoidTarget.m_20182_(), 16, 7);
        if (escapePos == null) {
            return false;
        }
        if (this.avoidTarget.m_20275_(escapePos.f_82479_, escapePos.f_82480_, escapePos.f_82481_) < this.avoidTarget.m_20280_((Entity)this.mob)) {
            return false;
        }
        this.path = this.pathNav.m_26524_(escapePos.f_82479_, escapePos.f_82480_, escapePos.f_82481_, 0);
        return this.path != null;
    }

    public boolean m_8045_() {
        return !this.pathNav.m_26571_();
    }

    public void m_8056_() {
        this.pathNav.m_26536_(this.path, this.walkSpeedModifier);
    }

    public void m_8041_() {
        this.avoidTarget = null;
    }

    public void m_8037_() {
        if (this.avoidTarget == null) {
            return;
        }
        if (this.mob.m_20280_(this.avoidTarget) < (double)this.runDistance) {
            this.pathNav.m_26517_(this.sprintSpeedModifier);
        } else {
            this.pathNav.m_26517_(this.walkSpeedModifier);
        }
    }

    @Nullable
    private Vec3 getBestEscapePos(Vec3 targetPos, int radius, int yRange) {
        Vec3 bestPos = null;
        double maxDistanceSqr = -1.0;
        double moveX = this.mob.m_20185_() - targetPos.f_82479_;
        double moveZ = this.mob.m_20189_() - targetPos.f_82481_;
        boolean restricted = GoalUtils.m_148442_((PathfinderMob)this.mob, (int)radius);
        RandomSource random = this.mob.m_217043_();
        PathNavigation nav = this.mob.m_21573_();
        for (int i = 0; i < 10; ++i) {
            double distSqr;
            BlockPos finalBlockPos;
            BlockPos rawDir = RandomPos.m_217855_((RandomSource)random, (int)radius, (int)yRange, (int)0, (double)moveX, (double)moveZ, (double)1.5707963705062866);
            if (rawDir == null || GoalUtils.m_148451_((BlockPos)(finalBlockPos = RandomPos.m_217863_((PathfinderMob)this.mob, (int)radius, (RandomSource)random, (BlockPos)rawDir)), (PathfinderMob)this.mob) || GoalUtils.m_148454_((boolean)restricted, (PathfinderMob)this.mob, (BlockPos)finalBlockPos) || GoalUtils.m_148448_((PathNavigation)nav, (BlockPos)finalBlockPos) || GoalUtils.m_148458_((PathfinderMob)this.mob, (BlockPos)finalBlockPos) || !((distSqr = targetPos.m_82531_((double)finalBlockPos.m_123341_(), (double)finalBlockPos.m_123342_(), (double)finalBlockPos.m_123343_())) > maxDistanceSqr)) continue;
            maxDistanceSqr = distSqr;
            bestPos = Vec3.m_82539_((Vec3i)finalBlockPos);
        }
        return bestPos;
    }

    public static <T extends LivingEntity> Builder<T> of(Class<T> toAvoidClass) {
        return new Builder<T>(toAvoidClass);
    }

    public static class Builder<T extends LivingEntity> {
        private final Class<T> avoidClass;
        private double awareDistance = 16.0;
        private float runDistance = 7.0f;
        private double walkSpeedModifier = 1.0;
        private double runSpeedModifier = 1.2;
        private boolean keepFighting;
        private boolean mustSee = true;
        private Condition shouldUse = Condition.TRUE;
        private Predicate<LivingEntity> avoidPredicate = Predicates.alwaysTrue();

        public Builder(Class<T> avoidClass) {
            this.avoidClass = avoidClass;
        }

        public Builder<T> awareDistance(double distance) {
            this.awareDistance = distance;
            return this;
        }

        public Builder<T> runDistance(float distance) {
            this.runDistance = distance;
            return this;
        }

        public Builder<T> walkSpeed(double speed) {
            this.walkSpeedModifier = speed;
            return this;
        }

        public Builder<T> runSpeed(double speed) {
            this.runSpeedModifier = speed;
            return this;
        }

        public Builder<T> keepFighting() {
            this.keepFighting = true;
            return this;
        }

        public Builder<T> canSeeAnywhere() {
            this.mustSee = false;
            return this;
        }

        public Builder<T> speed(double speed) {
            this.walkSpeedModifier = speed;
            this.runSpeedModifier = speed;
            return this;
        }

        public Builder<T> shouldUse(Condition shouldUse) {
            this.shouldUse = shouldUse;
            return this;
        }

        public Builder<T> avoidPredicate(Predicate<LivingEntity> predicate) {
            this.avoidPredicate = predicate;
            return this;
        }

        public GoalAvoidEntity<T> build(PathfinderMob mob) {
            return new GoalAvoidEntity(mob, this);
        }
    }
}

