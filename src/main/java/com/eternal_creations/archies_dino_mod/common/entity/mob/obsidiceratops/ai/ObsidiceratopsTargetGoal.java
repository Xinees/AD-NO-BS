/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.goal.Goal$Flag
 *  net.minecraft.world.entity.ai.goal.target.TargetGoal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.monster.Strider
 *  net.minecraft.world.phys.AABB
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ai;

import com.eternal_creations.archies_dino_mod.common.entity.mob.obsidiceratops.ObsidiceratopsEntity;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.phys.AABB;

public class ObsidiceratopsTargetGoal
extends TargetGoal {
    private final ObsidiceratopsEntity mob;
    private final TargetingConditions targetConditions;
    private LivingEntity potentialTarget;
    private int lingerTimer = 0;

    public ObsidiceratopsTargetGoal(ObsidiceratopsEntity mob) {
        super((Mob)mob, false);
        this.mob = mob;
        this.targetConditions = TargetingConditions.m_148352_().m_26883_(this.m_7623_()).m_26888_(entity -> !(entity instanceof ObsidiceratopsEntity) && !(entity instanceof Strider));
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean m_8036_() {
        if (this.mob.isTamed() || this.mob.m_5448_() != null) {
            this.resetLinger();
            return false;
        }
        ObsidiceratopsEntity.LavaState state = this.mob.getLavaState();
        if (state == ObsidiceratopsEntity.LavaState.NORMAL) {
            this.resetLinger();
            return false;
        }
        if (state == ObsidiceratopsEntity.LavaState.EMPTY) {
            this.findTarget(16.0);
            return this.potentialTarget != null;
        }
        if (state == ObsidiceratopsEntity.LavaState.LOW) {
            this.potentialTarget = this.mob.m_9236_().m_45982_(this.mob.m_9236_().m_6443_(LivingEntity.class, this.getTargetSearchArea(10.0), e -> true), this.targetConditions, (LivingEntity)this.mob, this.mob.m_20185_(), this.mob.m_20188_(), this.mob.m_20189_());
            if (this.potentialTarget != null) {
                ++this.lingerTimer;
                return this.lingerTimer >= 200;
            }
            this.resetLinger();
        }
        return false;
    }

    public void m_8056_() {
        this.mob.m_6710_(this.potentialTarget);
        if (this.mob.isNoActionActive()) {
            this.mob.playActionSynced(this.mob.actionWarn);
        }
        this.resetLinger();
        super.m_8056_();
    }

    public void m_8041_() {
        this.potentialTarget = null;
        this.resetLinger();
        super.m_8041_();
    }

    private void resetLinger() {
        this.lingerTimer = 0;
    }

    protected AABB getTargetSearchArea(double distance) {
        return this.mob.m_20191_().m_82377_(distance, 4.0, distance);
    }

    protected void findTarget(double range) {
        this.potentialTarget = this.mob.m_9236_().m_45982_(this.mob.m_9236_().m_6443_(LivingEntity.class, this.getTargetSearchArea(range), e -> true), this.targetConditions, (LivingEntity)this.mob, this.mob.m_20185_(), this.mob.m_20188_(), this.mob.m_20189_());
    }
}

