/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie.targeting;

import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.targeting.FriendHurtByTargetGoal;
import java.util.EnumSet;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FriendHurtTargetGoal
extends TargetGoal {
    private final ArchieEntity archie;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public FriendHurtTargetGoal(ArchieEntity archie) {
        super((Mob)archie, false);
        this.archie = archie;
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean m_8036_() {
        if (this.archie.isTamed()) {
            LivingEntity livingentity = this.archie.m_269323_();
            if (livingentity == null) {
                return false;
            }
            this.ownerLastHurt = livingentity.m_21214_();
            if (this.ownerLastHurt == null) {
                return false;
            }
            int i = livingentity.m_21215_();
            return i != this.timestamp && this.m_26150_(this.ownerLastHurt, TargetingConditions.f_26872_) && FriendHurtByTargetGoal.wantsToAttack(this.ownerLastHurt, livingentity);
        }
        return false;
    }

    public void m_8056_() {
        this.f_26135_.m_6710_(this.ownerLastHurt);
        LivingEntity livingentity = this.archie.m_269323_();
        if (livingentity != null) {
            this.timestamp = livingentity.m_21215_();
        }
        super.m_8056_();
    }
}

