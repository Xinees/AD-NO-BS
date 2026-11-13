/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.animal.Wolf
 *  net.minecraft.world.entity.animal.horse.AbstractHorse
 *  net.minecraft.world.entity.monster.Creeper
 *  net.minecraft.world.entity.monster.Ghast
 *  net.minecraft.world.entity.player.Player
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie.targeting;

import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import java.util.EnumSet;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class FriendHurtByTargetGoal
extends TargetGoal {
    private final ArchieEntity archie;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public FriendHurtByTargetGoal(ArchieEntity archie) {
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
            this.ownerLastHurtBy = livingentity.m_21188_();
            if (this.ownerLastHurtBy == null) {
                return false;
            }
            int i = livingentity.m_21213_();
            return i != this.timestamp && this.m_26150_(this.ownerLastHurtBy, TargetingConditions.f_26872_) && FriendHurtByTargetGoal.wantsToAttack(this.ownerLastHurtBy, livingentity);
        }
        return false;
    }

    public static boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        TamableAnimal animal;
        AbstractHorse horse;
        if (target instanceof Creeper || target instanceof Ghast) {
            return false;
        }
        if (target instanceof Wolf) {
            Wolf wolf = (Wolf)target;
            return !wolf.m_21824_() || wolf.m_269323_() != owner;
        }
        if (target instanceof Player && owner instanceof Player && !((Player)owner).m_7099_((Player)target)) {
            return false;
        }
        return !(target instanceof AbstractHorse && (horse = (AbstractHorse)target).m_30614_() || target instanceof TamableAnimal && (animal = (TamableAnimal)target).m_21824_());
    }

    public void m_8056_() {
        this.f_26135_.m_6710_(this.ownerLastHurtBy);
        LivingEntity livingentity = this.archie.m_269323_();
        if (livingentity != null) {
            this.timestamp = livingentity.m_21213_();
        }
        super.m_8056_();
    }
}

