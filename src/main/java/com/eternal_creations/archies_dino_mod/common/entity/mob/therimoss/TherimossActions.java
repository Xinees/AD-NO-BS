/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.ClimbController;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.therimoss.TherimossState;
import com.eternal_creations.archies_dino_mod.common.entity.projectile.TherimossProjectileEntity;
import com.eternal_creations.archies_dino_mod.common.entity.utils.EntityUtils;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TherimossActions {
    public final ActionState up = ActionState.of((ActionType)ADActionTypes.UP, (float)3.5f, (float)0.4f, (float)0.6f).exitTime(3.0f).onTick(tick -> entity.setState(TherimossState.IDLE));
    public final ActionState down = ActionState.of((ActionType)ADActionTypes.DOWN, (float)3.0f, (float)0.4f, (float)0.6f).exitTime(2.4f).onTick(tick -> entity.setState(TherimossState.SITTING));
    public final ActionState climbStart = ActionState.of((ActionType)ADActionTypes.CLIMB_START, (float)1.1667f, (float)0.2f, (float)0.2f).exitTime(0.95f).onStart(() -> {
        entity.m_20256_(Vec3.f_82478_);
        entity.setClimbing(true);
    });
    public ActionState climbStop = ActionState.of((ActionType)ADActionTypes.CLIMB_STOP, (float)1.3333f, (float)0.2f, (float)0.2f).exitTime(1.2f).onTick(tick -> {
        if (tick == 10) {
            entity.setClimbing(false);
        }
    });
    public ActionState climbFinish = ActionState.of((ActionType)ADActionTypes.CLIMB_FINISH, (float)3.0f, (float)0.2f, (float)0.2f).exitTime(2.8f).onStart(() -> ClimbController.mantleStart(entity)).onTick(tick -> {
        if (tick == 10) {
            entity.setClimbing(false);
        }
        ClimbController.mantleTick(entity, tick);
    });
    public ActionState eat = ActionState.of((ActionType)ADActionTypes.EAT, (float)4.25f, (float)0.2f, (float)0.6f).exitTime(3.9f).onTick(tick -> {
        if (tick == 10) {
            entity.destroyActionTargetBlock(false);
            entity.m_216990_(ADSoundEvents.THERIMOSS_EAT.get());
        }
    });
    public ActionState jump = ActionState.of((ActionType)ADActionTypes.JUMP, (float)0.75f, (float)0.2f, (float)0.4f).exitTime(0.7f).onTick(tick -> TherimossActions.handleJumping(entity, tick));
    public ActionState land = ActionState.of((ActionType)ADActionTypes.LAND, (float)1.25f, (float)0.2f, (float)0.4f).exitTime(1.1f);
    public ActionState attackHit = ActionState.of((ActionType)ADActionTypes.ATTACK_HIT, (float)2.0f, (float)0.2f, (float)0.8f).exitTime(1.5f).onStart(() -> entity.playSound(ADSoundEvents.THERIMOSS_ATTACK_MELEE)).onTick(tick -> TherimossActions.handleAttackHit(entity, tick));
    public ActionState attackBite = ActionState.of((ActionType)ADActionTypes.ATTACK_BITE, (float)3.5f, (float)0.2f, (float)0.8f).exitTime(3.0f).onStart(() -> entity.playSound(ADSoundEvents.THERIMOSS_ATTACK_BEAK)).onTick(tick -> TherimossActions.handleAttackBite(entity, tick));
    public ActionState attackShoot = ActionState.of((ActionType)ADActionTypes.ATTACK_SHOOT, (float)2.75f, (float)0.2f, (float)0.8f).exitTime(2.2f).onStart(() -> entity.playSound(ADSoundEvents.THERIMOSS_ATTACK_THROW)).onTick(tick -> TherimossActions.handleAttackThrow(entity, tick));
    public ActionState death = ActionState.of((ActionType)ADActionTypes.DEATH, (float)2.38f, (float)0.2f, (float)0.0f).holdOnLastFrame();

    public TherimossActions(TherimossEntity entity) {
    }

    public boolean isAscendingOrDescendingOnWall() {
        return this.climbStart.isActive() || this.climbStop.isActive() || this.climbFinish.isActive();
    }

    public Set<ActionState> all() {
        return Set.of(this.up, this.down, this.climbStart, this.climbStop, this.climbFinish, this.eat, this.jump, this.land, this.attackHit, this.attackBite, this.attackShoot, this.death);
    }

    private static void handleAttackHit(TherimossEntity entity, int tick) {
        float knockback = 0.8f;
        if (tick == 11 || tick == 20 || tick == 27) {
            entity.attackActionTargetEntity(0.625f, knockback);
        }
    }

    private static void handleAttackBite(TherimossEntity entity, int tick) {
        float attackRadius = entity.m_6162_() ? 3.0f : 5.0f;
        float knockbackPower = 2.0f;
        if (tick == 35) {
            EntityUtils.attackForward((Mob)entity, attackRadius, -1.0f, knockbackPower, 1.25f, damaged -> {});
        }
    }

    private static void handleAttackThrow(TherimossEntity entity, int tick) {
        TherimossProjectileEntity projectile;
        Level level = entity.m_9236_();
        if (tick == 12) {
            if (!level.f_46443_) {
                projectile = new TherimossProjectileEntity(level, (LivingEntity)entity);
                projectile.m_6034_(entity.m_20185_(), entity.m_20188_(), entity.m_20189_());
                level.m_7967_((Entity)projectile);
                entity.setHeldProjectile(projectile);
            }
            Vec3 viewVec = EntityUtils.getViewVector((LivingEntity)entity);
            BlockPos blockPos = BlockPos.m_274561_((double)(entity.m_20185_() + viewVec.f_82479_ * 2.0), (double)entity.m_20186_(), (double)(entity.m_20189_() + viewVec.f_82481_ * 2.0));
            level.m_142052_(blockPos, entity.m_20075_());
        }
        if (!level.f_46443_ && tick == 34 && (projectile = entity.getHeldProjectile()) != null) {
            double z;
            double y;
            double x;
            LivingEntity target = entity.m_5448_();
            if (target != null) {
                x = target.m_20185_() - entity.m_20185_();
                y = target.m_20188_() - projectile.m_20186_();
                z = target.m_20189_() - entity.m_20189_();
            } else {
                Vec3 view = EntityUtils.getViewVector((LivingEntity)entity);
                x = view.f_82479_;
                y = view.f_82480_;
                z = view.f_82481_;
            }
            projectile.m_6686_(x, y, z, 1.75f, 2.0f);
            entity.setHeldProjectile(null);
        }
    }

    private static void handleJumping(TherimossEntity entity, int tick) {
        if (tick == 1 && entity.isClimbing()) {
            entity.wallRotation.setTarget(45.0f, 0.2f);
        }
        if (tick == 10) {
            entity.playSound(ADSoundEvents.THERIMOSS_STEP);
            entity.jumpFar();
            entity.wallRotation.setTarget(0.0f, 0.3f);
        }
    }
}

