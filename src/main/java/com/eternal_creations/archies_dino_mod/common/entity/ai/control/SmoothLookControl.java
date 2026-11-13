/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.function.Condition
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.ai.control.LookControl
 *  org.jetbrains.annotations.NotNull
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.control;

import com.astemir.astemirlib.re.core.function.Condition;
import java.util.Optional;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import org.jetbrains.annotations.NotNull;

public class SmoothLookControl
extends LookControl {
    protected double smoothX;
    protected double smoothY;
    protected double smoothZ;
    protected boolean hasTarget = false;
    protected final float maxSpeedLimit;
    protected final double interpolationSpeed;
    private Condition canRotate;

    public SmoothLookControl(Mob mob) {
        this(mob, 15.0f, 0.2);
    }

    public SmoothLookControl(Mob mob, float maxSpeedLimit, double interpolationSpeed) {
        super(mob);
        this.maxSpeedLimit = maxSpeedLimit;
        this.interpolationSpeed = interpolationSpeed;
        this.canRotate = Condition.TRUE;
    }

    public void m_24950_(double x, double y, double z, float deltaYaw, float deltaPitch) {
        super.m_24950_(x, y, z, deltaYaw, deltaPitch);
        if (!this.hasTarget) {
            this.smoothX = this.f_24937_.m_20185_();
            this.smoothY = this.f_24937_.m_20188_();
            this.smoothZ = this.f_24937_.m_20189_();
            this.hasTarget = true;
        }
    }

    public void m_8128_() {
        if (this.canRotate.check() && this.f_186068_ > 0) {
            this.smoothX += (this.f_24941_ - this.smoothX) * this.interpolationSpeed;
            this.smoothY += (this.f_24942_ - this.smoothY) * this.interpolationSpeed;
            this.smoothZ += (this.f_24943_ - this.smoothZ) * this.interpolationSpeed;
            this.f_24938_ = Math.min(this.f_24938_, this.maxSpeedLimit);
            this.f_24939_ = Math.min(this.f_24939_, this.maxSpeedLimit);
        } else {
            this.hasTarget = false;
        }
        super.m_8128_();
    }

    @NotNull
    protected Optional<Float> m_180897_() {
        if (!this.canRotate.check()) {
            return Optional.empty();
        }
        double d0 = this.smoothX - this.f_24937_.m_20185_();
        double d1 = this.smoothY - this.f_24937_.m_20188_();
        double d2 = this.smoothZ - this.f_24937_.m_20189_();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        return !(Math.abs(d1) > 1.0E-5) && !(Math.abs(d3) > 1.0E-5) ? Optional.empty() : Optional.of(Float.valueOf((float)(-(Mth.m_14136_((double)d1, (double)d3) * 57.29577951308232))));
    }

    @NotNull
    protected Optional<Float> m_180896_() {
        if (!this.canRotate.check()) {
            return Optional.empty();
        }
        double d0 = this.smoothX - this.f_24937_.m_20185_();
        double d1 = this.smoothZ - this.f_24937_.m_20189_();
        return !(Math.abs(d1) > 1.0E-5) && !(Math.abs(d0) > 1.0E-5) ? Optional.empty() : Optional.of(Float.valueOf((float)(Mth.m_14136_((double)d1, (double)d0) * 57.29577951308232) - 90.0f));
    }

    protected void m_142586_() {
        this.f_24937_.f_20885_ = Mth.m_14094_((float)this.f_24937_.f_20885_, (float)this.f_24937_.f_20883_, (float)this.f_24937_.m_8085_());
    }

    public SmoothLookControl setCanRotate(Condition canRotate) {
        this.canRotate = canRotate;
        return this;
    }
}

