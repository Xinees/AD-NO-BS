/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.monster.EnderMan
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.phys.AABB
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.chorusoatl.ChorusoatlEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

public class ChorusoatlTargetGoalWarn
extends TargetGoal {
    private final ChorusoatlEntity mob;
    private final TargetingConditions targetConditions;
    private LivingEntity target;

    public ChorusoatlTargetGoalWarn(ChorusoatlEntity mob) {
        super((Mob)mob, true);
        this.mob = mob;
        this.targetConditions = TargetingConditions.m_148352_().m_26883_(this.m_7623_()).m_26888_(entity -> !(entity instanceof ChorusoatlEntity) && !(entity instanceof EnderMan));
    }

    public boolean m_8036_() {
        if (this.mob.isTamed() || this.mob.m_5448_() != null || this.mob.f_19797_ < 40) {
            return false;
        }
        if (this.mob.isFlying()) {
            return false;
        }
        this.findTarget();
        if (this.target == null) {
            return false;
        }
        return !this.isHoldingChorusFruit(this.target);
    }

    public void m_8056_() {
        this.mob.m_6710_(this.target);
        this.mob.playActionSynced(this.mob.actionScream);
    }

    protected AABB getTargetSearchArea(double targetDistance) {
        return this.mob.m_20191_().m_82377_(targetDistance, 4.0, targetDistance);
    }

    protected void findTarget() {
        this.target = this.mob.m_9236_().m_45982_(this.mob.m_9236_().m_6443_(LivingEntity.class, this.getTargetSearchArea(15.0), p_148152_ -> true), this.targetConditions, (LivingEntity)this.mob, this.mob.m_20185_(), this.mob.m_20188_(), this.mob.m_20189_());
    }

    private boolean isHoldingChorusFruit(LivingEntity entity) {
        return entity.m_21055_(Items.f_42730_);
    }
}

