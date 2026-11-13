/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.util.AirAndWaterRandomPos
 *  net.minecraft.world.entity.ai.util.GoalUtils
 *  net.minecraft.world.entity.ai.util.LandRandomPos
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class GoalStrollLandAndAir
extends Goal {
    private final Flyer flyer;
    private final PathfinderMob mob;
    protected final double walkSpeedModifier;
    protected final double flySpeedModifier;
    protected int interval;
    protected int intervalAir;
    protected boolean forceTrigger;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    @Nullable
    private BlockPos roamCenter;
    private final double maxRoamRadius;
    private final int airClearance;
    private final int maxDistanceFar;
    private final int maxDistanceNear;
    private final int yRange;
    private final int groundThreshold;
    private final int targetFlyingHeight;
    private final int minTargetDistanceToGround;
    private final int maxAttempts;
    private final int maxRaycastDepth;
    private final int yDriftRange;
    private final int landHorizontalDistance;
    private final int landVerticalDistance;

    private GoalStrollLandAndAir(PathfinderMob mob, Builder builder) {
        Flyer f;
        this.mob = mob;
        this.flyer = mob instanceof Flyer ? (f = (Flyer)mob) : null;
        this.walkSpeedModifier = builder.walkSpeedModifier;
        this.flySpeedModifier = builder.flySpeedModifier;
        this.interval = builder.interval;
        this.intervalAir = builder.intervalAir;
        this.maxRoamRadius = builder.maxRoamRadius;
        this.airClearance = builder.airClearance;
        this.maxDistanceFar = builder.maxDistanceFar;
        this.maxDistanceNear = builder.maxDistanceNear;
        this.yRange = builder.yRange;
        this.groundThreshold = builder.groundThreshold;
        this.targetFlyingHeight = builder.targetFlyingHeight;
        this.minTargetDistanceToGround = builder.minTargetDistanceToGround;
        this.maxAttempts = builder.maxAttempts;
        this.maxRaycastDepth = builder.maxRaycastDepth;
        this.yDriftRange = builder.yDriftRange;
        this.landHorizontalDistance = builder.landHorizontalDistance;
        this.landVerticalDistance = builder.landVerticalDistance;
    }

    public boolean m_8036_() {
        int adjustedDelay;
        if (this.mob.m_6688_() != null) {
            return false;
        }
        if (!(this.forceTrigger || (adjustedDelay = GoalStrollLandAndAir.m_186073_((int)(this.isFlying() ? this.intervalAir : this.interval))) > 0 && this.mob.m_217043_().m_188503_(adjustedDelay) == 0)) {
            return false;
        }
        Vec3 vec3 = this.getPosition();
        if (vec3 == null) {
            return false;
        }
        this.wantedX = vec3.f_82479_;
        this.wantedY = vec3.f_82480_;
        this.wantedZ = vec3.f_82481_;
        this.forceTrigger = false;
        return true;
    }

    public boolean m_8045_() {
        return !this.mob.m_21573_().m_26571_() && this.mob.m_6688_() == null;
    }

    public void m_8056_() {
        this.mob.m_21573_().m_26519_(this.wantedX, this.wantedY, this.wantedZ, this.isFlying() ? this.flySpeedModifier : this.walkSpeedModifier);
    }

    public void m_8041_() {
    }

    @Nullable
    protected Vec3 getPosition() {
        if (this.flyer == null) {
            return this.getLandPosition();
        }
        if (this.flyer.isFlying()) {
            if (this.flyer.isPreparingToLand()) {
                return this.getLandPosition();
            }
            Vec3 airPos = this.getAirPosition();
            return airPos != null ? airPos : this.getLandPosition();
        }
        this.roamCenter = this.mob.m_20183_();
        return this.getLandPosition();
    }

    @Nullable
    private Vec3 getAirPosition() {
        double mobDz;
        double mobDx;
        double currentDistSq2D;
        if (this.roamCenter == null) {
            this.roamCenter = this.mob.m_20183_();
        }
        boolean far = (currentDistSq2D = (mobDx = this.mob.m_20185_() - ((double)this.roamCenter.m_123341_() + 0.5)) * mobDx + (mobDz = this.mob.m_20189_() - ((double)this.roamCenter.m_123343_() + 0.5)) * mobDz) > this.maxRoamRadius * this.maxRoamRadius;
        int distanceToGround = this.getDistanceToGround(this.mob.m_20183_());
        int maxDistance = far ? this.maxDistanceFar : this.maxDistanceNear;
        int yBias = distanceToGround < this.groundThreshold ? this.targetFlyingHeight - distanceToGround : this.mob.m_217043_().m_188503_(this.yDriftRange) - this.yDriftRange / 2;
        Vec3 direction = far ? Vec3.m_82512_((Vec3i)this.roamCenter).m_82546_(this.mob.m_20182_()).m_82541_() : this.mob.m_20252_(0.0f);
        boolean restriction = GoalUtils.m_148442_((PathfinderMob)this.mob, (int)maxDistance);
        for (int i = 0; i < this.maxAttempts; ++i) {
            double dz;
            BlockPos pos = AirAndWaterRandomPos.m_148365_((PathfinderMob)this.mob, (int)maxDistance, (int)this.yRange, (int)yBias, (double)direction.f_82479_, (double)direction.f_82481_, (double)1.5707963267948966, (boolean)restriction);
            if (pos == null) continue;
            double minAllowedDistance = (float)maxDistance * 0.75f;
            double dx = (double)pos.m_123341_() + 0.5 - this.mob.m_20185_();
            double distanceToTargetSq2D = dx * dx + (dz = (double)pos.m_123343_() + 0.5 - this.mob.m_20189_()) * dz;
            if (distanceToTargetSq2D < minAllowedDistance * minAllowedDistance) continue;
            double targetDx = (double)pos.m_123341_() + 0.5 - ((double)this.roamCenter.m_123341_() + 0.5);
            double targetDz = (double)pos.m_123343_() + 0.5 - ((double)this.roamCenter.m_123343_() + 0.5);
            double targetDistSq2D = targetDx * targetDx + targetDz * targetDz;
            if (!far ? targetDistSq2D > this.maxRoamRadius * this.maxRoamRadius : targetDistSq2D >= currentDistSq2D) continue;
            int targetDistanceToGround = this.getDistanceToGround(pos);
            if (targetDistanceToGround < this.minTargetDistanceToGround || !this.hasAirClearance(pos)) continue;
            return Vec3.m_82539_((Vec3i)pos);
        }
        return null;
    }

    private int getDistanceToGround(BlockPos startPos) {
        int distance = 0;
        BlockPos.MutableBlockPos mutablePos = startPos.m_122032_();
        int minBuildHeight = this.mob.m_9236_().m_141937_();
        while (mutablePos.m_123342_() > minBuildHeight) {
            mutablePos.m_122184_(0, -1, 0);
            if (!GoalUtils.m_148461_((PathfinderMob)this.mob, (BlockPos)mutablePos) && ++distance <= this.maxRaycastDepth) continue;
            break;
        }
        return distance;
    }

    private boolean hasAirClearance(BlockPos pos) {
        for (int i = 0; i < this.airClearance; ++i) {
            if (this.mob.m_9236_().m_8055_(pos.m_6630_(i)).m_60795_()) continue;
            return false;
        }
        return true;
    }

    private boolean isFlying() {
        return this.flyer != null && this.flyer.isFlying();
    }

    @Nullable
    private Vec3 getLandPosition() {
        Vec3 pos;
        if (!this.isFlying()) {
            return LandRandomPos.m_148488_((PathfinderMob)this.mob, (int)this.landHorizontalDistance, (int)this.landVerticalDistance);
        }
        BlockPos mobPos = this.mob.m_20183_();
        int distanceToGround = this.getDistanceToGround(mobPos);
        if (distanceToGround <= this.maxRaycastDepth) {
            BlockPos landingSpot = mobPos.m_6625_(distanceToGround);
            BlockPos ground = landingSpot.m_7495_();
            if (this.mob.m_9236_().m_6425_(ground).m_76178_() && !this.mob.m_9236_().m_8055_(landingSpot).m_278721_()) {
                return Vec3.m_82539_((Vec3i)landingSpot);
            }
        }
        if ((pos = LandRandomPos.m_148488_((PathfinderMob)this.mob, (int)this.landHorizontalDistance, (int)this.landVerticalDistance)) != null) {
            return pos;
        }
        return Vec3.m_82539_((Vec3i)mobPos.m_6625_(Math.min(distanceToGround, this.landVerticalDistance)));
    }

    public void trigger() {
        this.forceTrigger = true;
    }

    public static Builder of() {
        return new Builder();
    }

    public static class Builder {
        private double walkSpeedModifier = 1.0;
        private double flySpeedModifier = 1.0;
        private int interval = 120;
        private int intervalAir = 40;
        private double maxRoamRadius = 64.0;
        private int airClearance = 3;
        private int maxDistanceFar = 16;
        private int maxDistanceNear = 12;
        private int yRange = 5;
        private int groundThreshold = 20;
        private int targetFlyingHeight = 25;
        private int minTargetDistanceToGround = 15;
        private int maxAttempts = 15;
        private int maxRaycastDepth = 40;
        private int yDriftRange = 5;
        private int landHorizontalDistance = 10;
        private int landVerticalDistance = 7;

        public Builder speed(double walkSpeed, double flySpeed) {
            this.walkSpeedModifier = walkSpeed;
            this.flySpeedModifier = flySpeed;
            return this;
        }

        public Builder intervals(int interval, int intervalAir) {
            this.interval = interval;
            this.intervalAir = intervalAir;
            return this;
        }

        public Builder maxRoamRadius(double maxRoamRadius) {
            this.maxRoamRadius = maxRoamRadius;
            return this;
        }

        public Builder airClearance(int airClearance) {
            this.airClearance = airClearance;
            return this;
        }

        public Builder maxDistance(int near, int far) {
            this.maxDistanceNear = near;
            this.maxDistanceFar = far;
            return this;
        }

        public Builder yRange(int yRange) {
            this.yRange = yRange;
            return this;
        }

        public Builder groundThreshold(int groundThreshold) {
            this.groundThreshold = groundThreshold;
            return this;
        }

        public Builder targetFlyingHeight(int targetFlyingHeight) {
            this.targetFlyingHeight = targetFlyingHeight;
            return this;
        }

        public Builder minTargetDistanceToGround(int minTargetDistanceToGround) {
            this.minTargetDistanceToGround = minTargetDistanceToGround;
            return this;
        }

        public Builder maxAttempts(int maxAttempts) {
            this.maxAttempts = maxAttempts;
            return this;
        }

        public Builder maxRaycastDepth(int maxRaycastDepth) {
            this.maxRaycastDepth = maxRaycastDepth;
            return this;
        }

        public Builder yDriftRange(int yDriftRange) {
            this.yDriftRange = yDriftRange;
            return this;
        }

        public Builder landRange(int horizontal, int vertical) {
            this.landHorizontalDistance = horizontal;
            this.landVerticalDistance = vertical;
            return this;
        }

        public GoalStrollLandAndAir build(PathfinderMob mob) {
            return new GoalStrollLandAndAir(mob, this);
        }
    }
}

