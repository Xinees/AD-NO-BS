/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.world.physics.AABBf
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.joml.Matrix4f
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss;

import com.astemir.astemirlib.re.common.world.physics.AABBf;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;

public class ClimbController {
    private final TherimossEntity mob;
    private final AABBf localSensorBox = new AABBf();
    private final AABBf worldSensorBox = new AABBf();
    private final Matrix4f transformMatrix = new Matrix4f();
    private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
    private int climbTicks = 0;
    private int wallLostTicks = 0;
    private float climbYaw = 0.0f;
    private boolean yawLocked = false;

    public ClimbController(TherimossEntity mob) {
        this.mob = mob;
    }

    private float getHalfWidth() {
        return this.mob.m_20205_() * 0.5f;
    }

    private float getHeight() {
        return this.mob.m_20206_();
    }

    private float getShoulderOffset() {
        return this.getHalfWidth() * 0.68f;
    }

    public boolean travel() {
        if (this.mob.actions.isAscendingOrDescendingOnWall()) {
            this.mob.m_20256_(Vec3.f_82478_);
            return true;
        }
        if (this.mob.isClimbing() && this.mob.m_6688_() == null) {
            this.mob.m_20256_(Vec3.f_82478_);
            return true;
        }
        return false;
    }

    public void tick() {
        boolean climbing = this.mob.isClimbing();
        boolean actionActive = this.mob.actions.isAscendingOrDescendingOnWall();
        if (climbing || actionActive) {
            if (!this.yawLocked) {
                if (this.mob.m_9236_().m_5776_()) {
                    this.climbYaw = this.getNearestGridYaw();
                }
                this.yawLocked = true;
            }
            this.mob.setAirTicks(0);
            this.mob.m_146922_(this.climbYaw);
            this.mob.f_19859_ = this.climbYaw;
            this.mob.f_20883_ = this.climbYaw;
            this.mob.f_20884_ = this.climbYaw;
            this.mob.f_20885_ = this.climbYaw;
            this.mob.f_20886_ = this.climbYaw;
        } else {
            this.yawLocked = false;
        }
        if (this.climbTicks > 0) {
            --this.climbTicks;
        }
        if (actionActive) {
            this.mob.climbVelocity = 0.0f;
            return;
        }
        if (!climbing) {
            boolean facing;
            this.mob.climbVelocity = 0.0f;
            if (this.climbTicks > 0 || !this.isRiderPushingForward()) {
                return;
            }
            float gridYaw = this.getNearestGridYaw();
            boolean bl = facing = Math.abs(Mth.m_14177_((float)(this.mob.m_146908_() - gridYaw))) <= 35.0f;
            if (facing && !this.mob.m_9236_().m_5776_()) {
                this.climbYaw = gridYaw;
                if (this.tryFindLedgeAndMantle()) {
                    this.mob.m_146922_(this.climbYaw);
                    this.mob.f_20883_ = this.climbYaw;
                    this.mob.m_20256_(Vec3.f_82478_);
                    this.mob.playActionSynced(this.mob.actions.climbFinish);
                    this.climbTicks = 40;
                    this.wallLostTicks = 0;
                    return;
                }
            }
            if (this.canStartClimbing()) {
                this.climbYaw = gridYaw;
                this.mob.m_146922_(this.climbYaw);
                this.mob.f_20883_ = this.climbYaw;
                this.mob.m_20256_(Vec3.f_82478_);
                this.mob.playActionSynced(this.mob.actions.climbStart);
                this.mob.climbVelocity = 0.0f;
                this.climbTicks = 40;
                this.wallLostTicks = 0;
            }
        } else {
            if (!this.mob.m_9236_().m_5776_()) {
                if (this.isRiderPushingForward() && this.tryFindLedgeAndMantle()) {
                    this.mob.playActionSynced(this.mob.actions.climbFinish);
                    this.climbTicks = 40;
                    this.wallLostTicks = 0;
                    return;
                }
                if (!this.hasWallInFront()) {
                    ++this.wallLostTicks;
                    if (this.wallLostTicks > 4) {
                        this.mob.setClimbing(false);
                        this.wallLostTicks = 0;
                    }
                } else {
                    this.wallLostTicks = 0;
                }
            }
            Vec3 vel = this.mob.m_20184_();
            this.mob.climbVelocity = (float)vel.m_82553_();
            if (this.mob.m_20096_() && this.climbTicks <= 0 && this.isRiderTryingToClimbDown()) {
                this.mob.playActionSynced(this.mob.actions.climbStop);
                this.climbTicks = 40;
            }
        }
    }

    private float getNearestGridYaw() {
        return Mth.m_14177_((float)((float)Math.round(this.mob.m_146908_() / 90.0f) * 90.0f));
    }

    private boolean isRiderPushingForward() {
        LivingEntity rider = this.mob.m_6688_();
        if (this.mob.isClimbing()) {
            return rider == null || rider.f_20902_ > 0.1f;
        }
        if (!this.mob.m_20096_()) {
            if (this.mob.f_19862_) {
                return true;
            }
            if (rider != null && rider.f_20902_ > 0.1f) {
                return true;
            }
            Vec3 v = this.mob.m_20184_();
            float yawRad = this.getNearestGridYaw() * ((float)Math.PI / 180);
            double dot = v.f_82479_ * (double)(-Mth.m_14031_((float)yawRad)) + v.f_82481_ * (double)Mth.m_14089_((float)yawRad);
            return dot > 0.02;
        }
        return rider == null || rider.f_20902_ > 0.1f;
    }

    private boolean isRiderTryingToClimbDown() {
        LivingEntity rider = this.mob.m_6688_();
        return rider != null ? rider.f_20902_ < -0.1f : this.mob.m_20184_().f_82480_ < -0.01;
    }

    public static void mantleStart(TherimossEntity entity) {
        entity.m_20256_(Vec3.f_82478_);
        entity.setMantleStartPos(entity.m_20183_());
    }

    public static void mantleTick(TherimossEntity entity, int tick) {
        Level level = entity.m_9236_();
        int moveDuration = 40;
        int totalDuration = 56;
        entity.getMantlePos().ifPresent(mantlePos -> {
            double targetX = (double)mantlePos.m_123341_() + 0.5;
            double targetY = mantlePos.m_123342_();
            double targetZ = (double)mantlePos.m_123343_() + 0.5;
            if (tick <= moveDuration) {
                float progress = (float)tick / (float)moveDuration;
                float horizontalProgress = (float)Mth.m_14197_((double)progress);
                entity.getMantleStartPos().ifPresent(startMantlePos -> {
                    double startX = (double)startMantlePos.m_123341_() + 0.5;
                    double startY = startMantlePos.m_123342_();
                    double startZ = (double)startMantlePos.m_123343_() + 0.5;
                    double curX = Mth.m_14139_((double)horizontalProgress, (double)startX, (double)targetX);
                    double curZ = Mth.m_14139_((double)horizontalProgress, (double)startZ, (double)targetZ);
                    double arcHeight = (double)entity.m_20206_() * 0.11 * (double)Mth.m_14031_((float)(progress * (float)Math.PI));
                    double curY = Mth.m_14139_((double)horizontalProgress, (double)startY, (double)targetY) + arcHeight;
                    entity.f_19854_ = entity.m_20185_();
                    entity.f_19855_ = entity.m_20186_();
                    entity.f_19856_ = entity.m_20189_();
                    entity.m_6034_(curX, curY, curZ);
                    entity.m_20256_(Vec3.f_82478_);
                });
            } else {
                entity.m_6034_(targetX, targetY, targetZ);
                entity.m_20256_(Vec3.f_82478_);
            }
            if (tick >= totalDuration - 1) {
                entity.m_6034_(targetX, targetY, targetZ);
                entity.m_20256_(Vec3.f_82478_);
                if (!level.m_5776_() || entity.m_6109_()) {
                    entity.setClimbing(false);
                    entity.setAirTicks(0);
                    if (entity.climbController != null) {
                        entity.climbController.setClimbTicks(35);
                    }
                }
            }
        });
    }

    public void setClimbTicks(int ticks) {
        this.climbTicks = ticks;
    }

    private boolean checkLocalVolume(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float yaw) {
        this.localSensorBox.set(minX, minY, minZ, maxX, maxY, maxZ);
        this.transformMatrix.identity().translate((float)this.mob.m_20185_(), (float)this.mob.m_20186_(), (float)this.mob.m_20189_()).rotateY(-yaw * ((float)Math.PI / 180));
        this.localSensorBox.transform(this.transformMatrix, this.worldSensorBox);
        return this.hasBlockCollisions(this.worldSensorBox);
    }

    private boolean isClimbableWall(BlockState state) {
        return !state.m_60795_() && state.m_280555_() && !state.m_204336_(BlockTags.f_13035_) && !state.m_60713_(Blocks.f_50571_);
    }

    private boolean hasBlockCollisions(AABBf box) {
        Level level = this.mob.m_9236_();
        int minX = Mth.m_14143_((float)box.minX);
        int maxX = Mth.m_14143_((float)box.maxX);
        int minY = Mth.m_14143_((float)box.minY);
        int maxY = Mth.m_14143_((float)box.maxY);
        int minZ = Mth.m_14143_((float)box.minZ);
        int maxZ = Mth.m_14143_((float)box.maxZ);
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    this.mutablePos.m_122178_(x, y, z);
                    BlockState state = level.m_8055_((BlockPos)this.mutablePos);
                    if (!this.isClimbableWall(state)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColumn(float localX, float minY, float maxY, float reachForward, float yaw) {
        float halfW = this.getHalfWidth();
        float forwardMin = halfW - 0.1f;
        float forwardMax = halfW + reachForward;
        float colRadius = Math.max(0.12f, halfW * 0.18f);
        return this.checkLocalVolume(localX - colRadius, minY, forwardMin, localX + colRadius, maxY, forwardMax, yaw);
    }

    public boolean hasWallInFront() {
        float yaw = this.climbYaw;
        float shoulder = this.getShoulderOffset();
        float maxY = this.getHeight() * 0.95f;
        float minY = this.getHeight() * 0.15f;
        boolean center = this.checkColumn(0.0f, minY, maxY, 0.35f, yaw);
        boolean left = this.checkColumn(-shoulder, minY, maxY, 0.35f, yaw);
        boolean right = this.checkColumn(shoulder, minY, maxY, 0.35f, yaw);
        return center && (left || right);
    }

    public boolean canStartClimbing() {
        boolean onGround = this.mob.m_20096_();
        float h = this.getHeight();
        float shoulder = this.getShoulderOffset();
        float targetGridYaw = this.getNearestGridYaw();
        float yawDiff = Math.abs(Mth.m_14177_((float)(this.mob.m_146908_() - targetGridYaw)));
        if (yawDiff > 35.0f) {
            return false;
        }
        float yawRad = targetGridYaw * ((float)Math.PI / 180);
        double probe = (double)this.getHalfWidth() + 0.3;
        int bx = Mth.m_14107_((double)(this.mob.m_20185_() + (double)(-Mth.m_14031_((float)yawRad)) * probe));
        int bz = Mth.m_14107_((double)(this.mob.m_20189_() + (double)Mth.m_14089_((float)yawRad) * probe));
        int y0 = Mth.m_14107_((double)(this.mob.m_20186_() + 0.1));
        int y1 = onGround ? Mth.m_14107_((double)(this.mob.m_20186_() + Math.max(1.5, (double)h * 0.6))) : y0 + 1;
        for (int y = y0; y <= y1; ++y) {
            this.mutablePos.m_122178_(bx, y, bz);
            if (this.isClimbableWall(this.mob.m_9236_().m_8055_((BlockPos)this.mutablePos))) continue;
            return false;
        }
        float minY = onGround ? Math.max(0.8f, this.mob.m_274421_() - 0.3f) : h * 0.15f;
        float maxY = h * 0.95f;
        float reach = onGround ? 0.35f : 0.55f;
        boolean center = this.checkColumn(0.0f, minY, maxY, reach, targetGridYaw);
        boolean left = this.checkColumn(-shoulder, minY, maxY, reach, targetGridYaw);
        boolean right = this.checkColumn(shoulder, minY, maxY, reach, targetGridYaw);
        if (onGround) {
            return center && left && right;
        }
        return center && (left || right);
    }

    private boolean tryFindLedgeAndMantle() {
        boolean climbing = this.mob.isClimbing();
        Level level = this.mob.m_9236_();
        float yaw = climbing ? this.climbYaw : this.getNearestGridYaw();
        float h = this.getHeight();
        float halfW = this.getHalfWidth();
        float minLedge = climbing ? 0.5f : this.mob.m_274421_() + 0.05f;
        float maxLedge = h * 0.9f;
        float yawRad = yaw * ((float)Math.PI / 180);
        double probe = (double)halfW + 0.3;
        int bx = Mth.m_14107_((double)(this.mob.m_20185_() + (double)(-Mth.m_14031_((float)yawRad)) * probe));
        int bz = Mth.m_14107_((double)(this.mob.m_20189_() + (double)Mth.m_14089_((float)yawRad) * probe));
        double baseY = this.mob.m_20186_();
        int clearance = Mth.m_14167_((float)h);
        for (int cy = Mth.m_14107_((double)(baseY + (double)maxLedge)); cy >= Mth.m_14107_((double)baseY); --cy) {
            double topY;
            double ledgeH;
            VoxelShape shape;
            this.mutablePos.m_122178_(bx, cy, bz);
            BlockState state = level.m_8055_((BlockPos)this.mutablePos);
            if (state.m_60795_() || (shape = state.m_60812_((BlockGetter)level, (BlockPos)this.mutablePos)).m_83281_() || (ledgeH = (topY = (double)cy + shape.m_83297_(Direction.Axis.Y)) - baseY) < (double)minLedge || ledgeH > (double)maxLedge) continue;
            boolean clear = true;
            for (int i = 1; i <= clearance; ++i) {
                this.mutablePos.m_122178_(bx, cy + i, bz);
                if (level.m_8055_((BlockPos)this.mutablePos).m_60812_((BlockGetter)level, (BlockPos)this.mutablePos).m_83281_()) continue;
                clear = false;
                break;
            }
            if (!clear) continue;
            this.mob.setMantlePos(new BlockPos(bx, Mth.m_14165_((double)(topY - 0.001)), bz));
            return true;
        }
        return false;
    }
}

