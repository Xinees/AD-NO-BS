/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.function.Condition
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.control.BodyRotationControl
 *  net.minecraft.world.entity.player.Player
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.control;

import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.common.entity.base.Flyer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.player.Player;

public class SmoothBodyControl
extends BodyRotationControl {
    private final Mob mob;
    private int headStableTime;
    private float lastStableYHeadRot;
    private final float maxHeadYRot;
    private final int stableTimeLimit;
    private final float angleThreshold;
    private final float bodyLerpSpeed;
    private Condition canRotate;

    public SmoothBodyControl(Mob mob, float maxHeadYRot, int stableTimeLimit, float angleThreshold, float bodyLerpSpeed) {
        super(mob);
        this.mob = mob;
        this.maxHeadYRot = maxHeadYRot;
        this.stableTimeLimit = stableTimeLimit;
        this.angleThreshold = angleThreshold;
        this.bodyLerpSpeed = bodyLerpSpeed;
        this.canRotate = () -> true;
    }

    public SmoothBodyControl(Mob mob) {
        this(mob, mob.m_8085_(), 15, 10.0f, 0.08f);
    }

    public void m_8121_() {
        Flyer flyer;
        if (!this.canRotate.check()) {
            return;
        }
        if (this.mob.m_20160_() && this.mob.m_6688_() instanceof Player) {
            return;
        }
        Mob mob = this.mob;
        if (mob instanceof Flyer && (flyer = (Flyer)mob).isFlying() || !this.mob.m_20096_()) {
            this.mob.f_20883_ = Mth.m_14189_((float)(this.bodyLerpSpeed * 1.5f), (float)this.mob.f_20883_, (float)this.mob.m_146908_());
            this.lastStableYHeadRot = this.mob.f_20885_;
            this.headStableTime = 0;
            return;
        }
        if (this.m_24884_()) {
            this.mob.f_20883_ = Mth.m_14189_((float)0.15f, (float)this.mob.f_20883_, (float)this.mob.m_146908_());
            this.lastStableYHeadRot = this.mob.f_20885_;
            this.headStableTime = 0;
        } else if (this.m_24883_()) {
            float angleDiff = Math.abs(Mth.m_14177_((float)(this.mob.f_20885_ - this.lastStableYHeadRot)));
            if (angleDiff > this.angleThreshold) {
                this.headStableTime = 0;
                this.lastStableYHeadRot = this.mob.f_20885_;
                this.rotateBodySmoothly();
            } else {
                ++this.headStableTime;
                if (this.headStableTime > this.stableTimeLimit) {
                    this.rotateHeadTowardsFrontSmoothly();
                }
            }
        }
    }

    private void rotateBodySmoothly() {
        float targetBodyRot = Mth.m_14094_((float)this.mob.f_20883_, (float)this.mob.f_20885_, (float)this.maxHeadYRot);
        this.mob.f_20883_ = Mth.m_14189_((float)this.bodyLerpSpeed, (float)this.mob.f_20883_, (float)targetBodyRot);
    }

    private void rotateHeadTowardsFrontSmoothly() {
        int i = this.headStableTime - this.stableTimeLimit;
        float progress = Mth.m_14036_((float)((float)i / (float)this.stableTimeLimit), (float)0.0f, (float)1.0f);
        float currentMaxRot = this.maxHeadYRot * (1.0f - progress);
        float targetBodyRot = Mth.m_14094_((float)this.mob.f_20883_, (float)this.mob.f_20885_, (float)currentMaxRot);
        this.mob.f_20883_ = Mth.m_14189_((float)(this.bodyLerpSpeed * 0.6f), (float)this.mob.f_20883_, (float)targetBodyRot);
    }

    private boolean m_24883_() {
        return !(this.mob.m_146895_() instanceof Mob);
    }

    private boolean m_24884_() {
        Flyer flyer;
        double x = this.mob.m_20185_() - this.mob.f_19854_;
        double z = this.mob.m_20189_() - this.mob.f_19856_;
        Mob mob = this.mob;
        if (mob instanceof Flyer && (flyer = (Flyer)mob).isFlying()) {
            double y = this.mob.m_20186_() - this.mob.f_19855_;
            return x * x + z * z + y * y > 2.5000003E-7;
        }
        return x * x + z * z > 2.500000277905201E-7;
    }

    public SmoothBodyControl setCanRotate(Condition canRotate) {
        this.canRotate = canRotate;
        return this;
    }
}

