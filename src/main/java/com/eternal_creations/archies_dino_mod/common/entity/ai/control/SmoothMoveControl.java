/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.ai.control.MoveControl
 *  net.minecraft.world.entity.ai.control.MoveControl$Operation
 *  net.minecraft.world.entity.ai.navigation.PathNavigation
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.pathfinder.BlockPathTypes
 *  net.minecraft.world.level.pathfinder.NodeEvaluator
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.joml.Vector3d
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.control;

import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import com.eternal_creations.archies_dino_mod.common.entity.base.Swimmer;
import com.eternal_creations.archies_dino_mod.common.entity.base.WallClimber;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3d;

public class SmoothMoveControl
extends MoveControl {
    protected float rotationSpeed;
    private final Vector3d tempVector = new Vector3d();

    public SmoothMoveControl(Mob mob) {
        this(mob, 0.15f);
    }

    public SmoothMoveControl(Mob mob, float rotationSpeed) {
        this(mob, rotationSpeed, false);
    }

    public SmoothMoveControl(Mob mob, float rotationSpeed, boolean hoversInPlace) {
        super(mob);
        this.rotationSpeed = rotationSpeed;
    }

    public void m_8126_() {
        Swimmer swimmer;
        WallClimber climber;
        Flyer flyer;
        Mob mob = this.f_24974_;
        boolean isFlying = mob instanceof Flyer && (flyer = (Flyer)mob).isFlying();
        Mob mob2 = this.f_24974_;
        boolean isClimbing = mob2 instanceof WallClimber && (climber = (WallClimber)mob2).isClimbing();
        Mob mob3 = this.f_24974_;
        boolean isSwimming = mob3 instanceof Swimmer && (swimmer = (Swimmer)mob3).isSwimmingInLiquid();
        switch (this.f_24981_) {
            case STRAFE: {
                this.tickStrafe();
                break;
            }
            case MOVE_TO: {
                this.tickMoveTo(isFlying, isSwimming, isClimbing);
                break;
            }
            case JUMPING: {
                this.tickJumping(isClimbing);
                break;
            }
            case WAIT: {
                this.tickIdle(isClimbing);
            }
        }
    }

    private void tickMoveTo(boolean isFlying, boolean isSwimming, boolean isClimbing) {
        double dz;
        double dy;
        this.f_24981_ = MoveControl.Operation.WAIT;
        double dx = this.f_24975_ - this.f_24974_.m_20185_();
        double distanceSq = dx * dx + (dy = this.f_24976_ - this.f_24974_.m_20186_()) * dy + (dz = this.f_24977_ - this.f_24974_.m_20189_()) * dz;
        if (distanceSq < 2.5E-7) {
            this.f_24974_.m_21564_(0.0f);
            this.f_24974_.m_21567_(0.0f);
            return;
        }
        double horizontalDistSq = dx * dx + dz * dz;
        if (isFlying || isSwimming) {
            this.tick3DMovement(dx, dy, dz, horizontalDistSq, isFlying);
        } else if (isClimbing) {
            this.tickClimber(dx, dy, dz);
        } else {
            this.tickWalker(dx, dy, dz, horizontalDistSq);
        }
    }

    protected void tick3DMovement(double dx, double dy, double dz, double horizontalDistSq, boolean isFlying) {
        this.f_24974_.m_20242_(true);
        double width = this.f_24974_.m_20205_();
        double height = this.f_24974_.m_20206_();
        this.tempVector.set(this.f_24975_, this.f_24976_, this.f_24977_);
        double edgeDistanceSqr = EntityUtils.getDistanceSqrToPoint(this.f_24974_.m_20191_(), this.tempVector.x, this.tempVector.y, this.tempVector.z, false);
        if (edgeDistanceSqr < 0.01) {
            this.f_24974_.m_7910_(0.0f);
            this.f_24974_.m_21567_(0.0f);
            this.f_24981_ = MoveControl.Operation.WAIT;
            return;
        }
        double slowDownDistance = Math.max(3.0, width * 3.0);
        double edgeDistance = Math.sqrt(edgeDistanceSqr);
        float speedFactor = edgeDistance < slowDownDistance ? (float)(edgeDistance / slowDownDistance) : 1.0f;
        Attribute speedAttribute = isFlying ? Attributes.f_22280_ : Attributes.f_22279_;
        float baseSpeed = (float)(this.f_24978_ * this.f_24974_.m_21133_(speedAttribute));
        float speed = baseSpeed * speedFactor;
        double rotationDeadzone = width * 0.5;
        if (horizontalDistSq > rotationDeadzone * rotationDeadzone) {
            float targetYaw = this.calculateTargetYaw(dx, dz);
            this.f_24974_.m_146922_(this.smoothRotlerp(this.f_24974_.m_146908_(), targetYaw, this.rotationSpeed * 1.5f));
        }
        double horizontalDistance = Math.sqrt(horizontalDistSq);
        float targetPitch = (float)(-(Mth.m_14136_((double)dy, (double)horizontalDistance) * 57.2957763671875));
        this.f_24974_.m_146926_(this.smoothRotlerp(this.f_24974_.m_146909_(), targetPitch, this.rotationSpeed * 1.2f));
        this.f_24974_.m_7910_(speed);
        double ySlowDownDistance = Math.max(1.0, height * 1.5);
        float ySpeed = Math.abs(dy) < ySlowDownDistance ? speed * (float)(Math.abs(dy) / ySlowDownDistance) : speed;
        double yDeadzone = height * 0.05;
        if (Math.abs(dy) < yDeadzone) {
            this.f_24974_.m_21567_(0.0f);
        } else {
            this.f_24974_.m_21567_(dy > 0.0 ? ySpeed / 2.0f : -ySpeed / 2.0f);
        }
    }

    protected void tickClimber(double dx, double dy, double dz) {
        WallClimber climber;
        float speed = (float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_));
        double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (dist < 0.1) {
            this.f_24974_.m_7910_(0.0f);
            this.f_24981_ = MoveControl.Operation.WAIT;
            return;
        }
        double horizontalDistSq = dx * dx + dz * dz;
        if (horizontalDistSq > 0.01) {
            float targetYaw = this.calculateTargetYaw(dx, dz);
            this.f_24974_.m_146922_(this.smoothRotlerp(this.f_24974_.m_146908_(), targetYaw, this.rotationSpeed));
        }
        double vx = dx / dist * (double)speed;
        double vy = dy / dist * (double)speed;
        double vz = dz / dist * (double)speed;
        Mob mob = this.f_24974_;
        if (mob instanceof WallClimber && (climber = (WallClimber)mob).isClimbing()) {
            if (dy < 1.0) {
                vy = (double)speed * 0.5;
            }
            Vec3 forwardVec = Vec3.m_82498_((float)0.0f, (float)this.f_24974_.m_146908_());
            vx += forwardVec.f_82479_ * 0.05;
            vz += forwardVec.f_82481_ * 0.05;
        }
        this.f_24974_.m_20334_(vx, vy, vz);
    }

    protected void tickWalker(double dx, double dy, double dz, double horizontalDistSq) {
        boolean insideBlock;
        float targetYaw = this.calculateTargetYaw(dx, dz);
        this.f_24974_.m_146922_(this.smoothRotlerp(this.f_24974_.m_146908_(), targetYaw, this.rotationSpeed));
        this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
        boolean isClimber = this.f_24974_ instanceof WallClimber;
        if (this.f_24974_.m_20077_() && this.f_24974_.m_203441_(Fluids.f_76195_.m_76145_())) {
            return;
        }
        BlockPos pos = this.f_24974_.m_20183_();
        BlockState state = this.f_24974_.m_9236_().m_8055_(pos);
        VoxelShape shape = state.m_60812_((BlockGetter)this.f_24974_.m_9236_(), pos);
        boolean needsJumpForStep = this.f_24974_.f_19862_ && dy > (double)this.f_24974_.m_274421_() && horizontalDistSq < (double)Math.max(1.0f, this.f_24974_.m_20205_());
        boolean bl = insideBlock = state.m_60819_().m_76178_() && !shape.m_83281_() && this.f_24974_.m_20186_() < shape.m_83297_(Direction.Axis.Y) + (double)pos.m_123342_() && !state.m_204336_(BlockTags.f_13103_) && !state.m_204336_(BlockTags.f_13039_);
        if ((needsJumpForStep && !isClimber || insideBlock) && !isClimber) {
            this.f_24974_.m_21569_().m_24901_();
            this.f_24981_ = MoveControl.Operation.JUMPING;
        }
    }

    private void tickStrafe() {
        float relativeZ;
        float baseSpeed = (float)this.f_24974_.m_21133_(Attributes.f_22279_);
        float speed = (float)this.f_24978_ * baseSpeed;
        float forward = this.f_24979_;
        float right = this.f_24980_;
        float length = Mth.m_14116_((float)(forward * forward + right * right));
        if (length < 1.0f) {
            length = 1.0f;
        }
        forward = forward / length * speed;
        right = right / length * speed;
        float yawRad = this.f_24974_.m_146908_() * ((float)Math.PI / 180);
        float sin = Mth.m_14031_((float)yawRad);
        float cos = Mth.m_14089_((float)yawRad);
        float relativeX = forward * cos - right * sin;
        if (!this.m_24996_(relativeX, relativeZ = right * cos + forward * sin)) {
            this.f_24979_ = 1.0f;
            this.f_24980_ = 0.0f;
        }
        this.f_24974_.m_7910_(speed);
        this.f_24974_.m_21564_(this.f_24979_);
        this.f_24974_.m_21570_(this.f_24980_);
        this.f_24981_ = MoveControl.Operation.WAIT;
    }

    private void tickJumping(boolean isClimbing) {
        this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
        if (this.f_24974_.m_20096_() || isClimbing) {
            this.f_24981_ = MoveControl.Operation.WAIT;
        }
    }

    private void tickIdle(boolean isClimbing) {
        this.f_24974_.m_21564_(0.0f);
        this.f_24974_.m_21567_(0.0f);
        if (isClimbing && this.f_24974_.f_19862_) {
            Vec3 vel = this.f_24974_.m_20184_();
            this.f_24974_.m_20334_(vel.f_82479_, 0.0, vel.f_82481_);
        }
    }

    protected float calculateTargetYaw(double dx, double dz) {
        return (float)(Mth.m_14136_((double)dz, (double)dx) * 57.2957763671875) - 90.0f;
    }

    protected float smoothRotlerp(float sourceAngle, float targetAngle, float speedFactor) {
        float delta = Mth.m_14177_((float)(targetAngle - sourceAngle));
        float step = delta * speedFactor;
        float minStep = 2.5f;
        if (Math.abs(delta) > 0.1f && Math.abs(step) < minStep) {
            step = Math.signum(delta) * Math.min(Math.abs(delta), minStep);
        }
        float maxStepPerTick = 45.0f;
        step = Mth.m_14036_((float)step, (float)(-maxStepPerTick), (float)maxStepPerTick);
        return Mth.m_14177_((float)(sourceAngle + step));
    }

    public boolean m_24996_(float relativeX, float relativeZ) {
        PathNavigation pathnavigation = this.f_24974_.m_21573_();
        NodeEvaluator nodeevaluator = pathnavigation.m_26575_();
        return nodeevaluator.m_8086_((BlockGetter)this.f_24974_.m_9236_(), Mth.m_14107_((double)(this.f_24974_.m_20185_() + (double)relativeX)), this.f_24974_.m_146904_(), Mth.m_14107_((double)(this.f_24974_.m_20189_() + (double)relativeZ))) == BlockPathTypes.WALKABLE;
    }
}

