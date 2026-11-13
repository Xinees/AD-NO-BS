/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.stats.Stats
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.ExperienceOrb
 *  net.minecraft.world.entity.ai.goal.Goal
 *  net.minecraft.world.entity.ai.targeting.TargetingConditions
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.level.GameRules
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.common.entity.base.EggLaying;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class GoalBreedWithEgg<T extends Animal>
extends Goal {
    private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.m_148353_().m_148355_().m_26883_(20.0);
    private final T animal;
    private final Level level;
    private final double seeDistance;
    private final double breedDistance;
    private final double speedModifier;
    @Nullable
    protected T partner;
    private int loveTime;

    public GoalBreedWithEgg(T animal, double speedModifier, double seeDistance, double breedDistance) {
        this.animal = animal;
        this.level = animal.m_9236_();
        this.speedModifier = speedModifier;
        this.seeDistance = seeDistance;
        this.breedDistance = breedDistance * breedDistance;
    }

    public boolean m_8036_() {
        if (!this.animal.m_27593_()) {
            return false;
        }
        if (((EggLaying)this.animal).hasEgg()) {
            return false;
        }
        this.partner = this.getFreePartner();
        return this.partner != null;
    }

    public void m_8041_() {
        this.partner = null;
        this.loveTime = 0;
    }

    public void m_8037_() {
        if (this.partner == null) {
            return;
        }
        this.animal.m_21563_().m_24960_(this.partner, 10.0f, (float)this.animal.m_8132_());
        this.animal.m_21573_().m_5624_(this.partner, this.speedModifier);
        ++this.loveTime;
        if (this.loveTime >= this.m_183277_(60) && this.animal.m_20280_(this.partner) < this.breedDistance) {
            this.breed();
        }
    }

    @Nullable
    private T getFreePartner() {
        List list = this.level.m_45971_(this.animal.getClass(), PARTNER_TARGETING, this.animal, this.animal.m_20191_().m_82400_(this.seeDistance));
        double d0 = Double.MAX_VALUE;
        Animal animal = null;
        for (Animal partner : list) {
            if (!this.animal.m_7848_(partner) || !(this.animal.m_20280_((Entity)partner) < d0)) continue;
            animal = partner;
            d0 = this.animal.m_20280_((Entity)partner);
        }
        return (T)animal;
    }

    protected void breed() {
        if (this.partner == null) {
            return;
        }
        ServerPlayer serverplayer = this.animal.m_27592_();
        if (serverplayer == null && this.partner.m_27592_() != null) {
            serverplayer = this.partner.m_27592_();
        }
        if (serverplayer != null) {
            serverplayer.m_36220_(Stats.f_12937_);
            CriteriaTriggers.f_10581_.m_147278_(serverplayer, this.animal, this.partner, null);
        }
        ((EggLaying)this.animal).setHasEgg(true);
        this.animal.m_146762_(6000);
        this.partner.m_146762_(6000);
        this.animal.m_27594_();
        this.partner.m_27594_();
        RandomSource randomsource = this.animal.m_217043_();
        if (this.level.m_46469_().m_46207_(GameRules.f_46135_)) {
            this.level.m_7967_((Entity)new ExperienceOrb(this.level, this.animal.m_20185_(), this.animal.m_20186_(), this.animal.m_20189_(), randomsource.m_188503_(7) + 1));
        }
    }
}

