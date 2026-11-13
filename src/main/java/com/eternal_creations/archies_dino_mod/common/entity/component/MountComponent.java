/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.function.Condition
 *  com.astemir.astemirlib.re.core.function.FloatProperty
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Entity$MoveFunction
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.PathfinderMob
 *  net.minecraft.world.entity.Saddleable
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Vector3d
 */
package com.eternal_creations.archies_dino_mod.common.entity.component;

import com.astemir.astemirlib.re.core.function.Condition;
import com.astemir.astemirlib.re.core.function.FloatProperty;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.base.WallClimber;
import com.eternal_creations.archies_dino_mod.common.entity.component.EntityComponent;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MountComponent<T extends PathfinderMob>
implements EntityComponent {
    private static final String NBT_DATA_KEY = "Saddled";
    private final FloatProperty<PathfinderMob> mountMaxSpeed;
    private final Predicate<Player> canBeMounted;
    private final Condition flying;
    private final Condition swimming;
    private final float inertia;
    private final float friction;
    private final float followYawSpeed;
    private final float followHeadSpeed;
    private final float[] passengerOffset;
    private final T mob;
    private float yRot;
    private float yHeadRot;
    private float speed;
    private int passengerRideCooldown;
    private final Vector3d tempVec = new Vector3d();

    public MountComponent(T mob, Definition<T> definition) {
        this.mob = mob;
        this.mountMaxSpeed = definition.mountMaxSpeed;
        this.flying = definition.flying;
        this.swimming = definition.swimming;
        this.canBeMounted = definition.canBeMounted;
        this.inertia = definition.inertia;
        this.friction = definition.friction;
        this.followYawSpeed = definition.followYawSpeed;
        this.followHeadSpeed = definition.followHeadSpeed;
        this.passengerOffset = definition.passengerOffset;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.m_128379_(NBT_DATA_KEY, ((Saddleable)this.mob).m_6254_());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.m_128441_(NBT_DATA_KEY)) {
            ((Mount)this.mob).setSaddled(compound.m_128471_(NBT_DATA_KEY));
        }
    }

    @Override
    public void tickRidden(Player player, Vec3 motion) {
        if (this.passengerRideCooldown > 0) {
            --this.passengerRideCooldown;
        }
        float xRot = player.m_146909_() % 360.0f;
        float yRot = player.m_146908_() % 360.0f;
        if (((Mount)this.mob).canMountRotateHead()) {
            ((PathfinderMob)this.mob).f_20885_ = this.yHeadRot = Mth.m_14189_((float)this.followHeadSpeed, (float)this.yHeadRot, (float)yRot);
            this.mob.m_146926_(Mth.m_14036_((float)xRot, (float)0.0f, (float)30.0f));
            this.mob.m_146922_(this.yHeadRot);
        }
        if (((Mount)this.mob).canMountRotateBody()) {
            this.yRot = Mth.m_14189_((float)this.followYawSpeed, (float)this.yRot, (float)yRot);
            ((PathfinderMob)this.mob).f_20884_ = ((PathfinderMob)this.mob).f_20883_;
            ((PathfinderMob)this.mob).f_20883_ = this.yRot;
        }
    }

    @Override
    public Vec3 travel(Vec3 motion) {
        LivingEntity passenger;
        if (this.mob.m_6084_() && this.mob.m_20160_() && (passenger = this.mob.m_6688_()) instanceof Player) {
            WallClimber climber;
            Player player = (Player)passenger;
            float strafe = player.f_20900_;
            float forward = player.f_20902_;
            T t = this.mob;
            if (t instanceof WallClimber && (climber = (WallClimber)t).isClimbing()) {
                if (forward == 0.0f && strafe == 0.0f) {
                    this.mob.m_20256_(Vec3.f_82478_);
                    return Vec3.f_82478_;
                }
                float speed = (float)(this.mob.m_21133_(Attributes.f_22279_) * 0.5);
                double vy = forward * speed;
                Vec3 rightVec = Vec3.m_82498_((float)0.0f, (float)(this.mob.m_146908_() + 90.0f));
                double vx = rightVec.f_82479_ * (double)(-strafe) * (double)speed;
                double vz = rightVec.f_82481_ * (double)(-strafe) * (double)speed;
                Vec3 forwardVec = Vec3.m_82498_((float)0.0f, (float)this.mob.m_146908_());
                Vec3 climbMove = new Vec3(vx += forwardVec.f_82479_ * 0.05, vy, vz += forwardVec.f_82481_ * 0.05);
                this.mob.m_20256_(climbMove);
                return Vec3.f_82478_;
            }
            strafe *= 0.3f;
            if (forward < 0.0f) {
                forward *= 0.25f;
            }
            float maxSpeed = this.mountMaxSpeed.get(this.mob, 0.0f);
            boolean isMoving = forward != 0.0f || strafe != 0.0f;
            this.speed = isMoving ? Mth.m_14179_((float)this.inertia, (float)this.speed, (float)maxSpeed) : Mth.m_14179_((float)this.friction, (float)this.speed, (float)0.0f);
            boolean isSwimming = this.swimming.check();
            boolean isFlying = this.flying.check();
            if (isSwimming) {
                if (forward <= 0.0f) {
                    forward *= 0.25f;
                }
                float pitchRad = player.m_146909_() * ((float)Math.PI / 180);
                double vertical = (double)forward * -Math.sin(pitchRad);
                double horizontalForward = (double)forward * Math.cos(pitchRad);
                this.mob.m_7910_((float)(this.mob.m_21133_(Attributes.f_22279_) * (double)this.speed));
                return new Vec3((double)strafe, vertical, horizontalForward);
            }
            if (isFlying) {
                float pitchRad = player.m_146909_() * ((float)Math.PI / 180);
                double vertical = (double)forward * -Math.sin(pitchRad);
                double horizontalForward = (double)forward * Math.cos(pitchRad);
                this.mob.m_7910_((float)(this.mob.m_21133_(Attributes.f_22280_) * (double)this.speed));
                return new Vec3((double)strafe, vertical, horizontalForward);
            }
            this.mob.m_7910_((float)(this.mob.m_21133_(Attributes.f_22279_) * (double)this.speed));
            return new Vec3((double)strafe, motion.f_82480_, (double)forward);
        }
        return motion;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.canBeMounted.test(player)) {
            ItemStack itemstack = player.m_21120_(hand);
            Level level = this.mob.m_9236_();
            if (!player.m_36341_() && !this.mob.m_20160_()) {
                if (!level.f_46443_) {
                    player.m_20329_(this.mob);
                }
                return InteractionResult.m_19078_((boolean)level.f_46443_);
            }
            if (!((Saddleable)this.mob).m_6254_() && itemstack.m_150930_(Items.f_42450_)) {
                if (!level.f_46443_) {
                    if (!player.m_150110_().f_35937_) {
                        itemstack.m_41774_(1);
                    }
                    ((Mount)this.mob).setSaddled(true);
                    level.m_6269_(null, this.mob, ((Saddleable)this.mob).m_246265_(), SoundSource.NEUTRAL, 0.5f, 1.0f);
                }
                return InteractionResult.m_19078_((boolean)level.f_46443_);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void dropEquipment() {
    }

    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction callback) {
        this.tempVec.set(this.passengerOffset).rotateY((double)(-((PathfinderMob)this.mob).f_20883_ * ((float)Math.PI / 180)));
        callback.m_20372_(passenger, this.mob.m_20185_() + this.tempVec.x, this.mob.m_20186_() + (double)this.mob.m_20206_() + this.tempVec.y, this.mob.m_20189_() + this.tempVec.z);
    }

    @Override
    public void addPassenger(Entity passenger) {
        if (passenger instanceof Player) {
            this.yRot = this.mob.m_146908_();
            this.yHeadRot = this.mob.m_6080_();
        }
        this.passengerRideCooldown = 20;
    }

    public boolean isNoPassengerCooldown() {
        return this.passengerRideCooldown <= 0;
    }

    public static <T extends PathfinderMob> Definition<T> of(Predicate<Player> canBeMounted) {
        return new Definition(canBeMounted);
    }

    public static class Definition<T extends PathfinderMob> {
        private final Predicate<Player> canBeMounted;
        private FloatProperty<PathfinderMob> mountMaxSpeed;
        private Condition flying;
        private Condition swimming;
        private float inertia = 0.05f;
        private float friction = 0.3f;
        private float followYawSpeed = 0.01f;
        private float followHeadSpeed = 0.03f;
        private float[] passengerOffset = new float[]{0.0f, 0.0f, 0.0f};

        public Definition(Predicate<Player> canBeMounted) {
            this.mountMaxSpeed = (instance, partialTick) -> 1.0f;
            this.canBeMounted = canBeMounted;
            this.flying = Condition.FALSE;
            this.swimming = Condition.FALSE;
        }

        public Definition<T> maxSpeed(FloatProperty<PathfinderMob> speed) {
            this.mountMaxSpeed = speed;
            return this;
        }

        public Definition<T> maxSpeed(float speed) {
            this.mountMaxSpeed = (instance, partialTick) -> speed;
            return this;
        }

        public Definition<T> inertia(float inertia) {
            this.inertia = inertia;
            return this;
        }

        public Definition<T> friction(float friction) {
            this.friction = friction;
            return this;
        }

        public Definition<T> followYawSpeed(float speed) {
            this.followYawSpeed = speed;
            return this;
        }

        public Definition<T> followHeadSpeed(float speed) {
            this.followHeadSpeed = speed;
            return this;
        }

        public Definition<T> flying(Condition condition) {
            this.flying = condition;
            return this;
        }

        public Definition<T> swimming(Condition swimming) {
            this.swimming = swimming;
            return this;
        }

        public Definition<T> passengerOffset(float x, float y, float z) {
            this.passengerOffset = new float[]{x, y, z};
            return this;
        }

        public MountComponent<T> build(T mob) {
            return new MountComponent<T>(mob, this);
        }
    }
}

