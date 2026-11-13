/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.HumanoidArmorModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.decoration.ArmorStand
 */
package com.eternal_creations.archies_dino_mod.client.model.armor;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ADArmorModel
extends HumanoidArmorModel<LivingEntity> {
    public ADArmorModel(ModelPart root) {
        super(root);
    }

    public void m_6973_(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entityIn instanceof ArmorStand) {
            ArmorStand entityarmorstand = (ArmorStand)entityIn;
            this.f_102808_.f_104203_ = (float)Math.PI / 180 * entityarmorstand.m_31680_().m_123156_();
            this.f_102808_.f_104204_ = (float)Math.PI / 180 * entityarmorstand.m_31680_().m_123157_();
            this.f_102808_.f_104205_ = (float)Math.PI / 180 * entityarmorstand.m_31680_().m_123158_();
            this.f_102808_.m_104227_(0.0f, 1.0f, 0.0f);
            this.f_102810_.f_104203_ = (float)Math.PI / 180 * entityarmorstand.m_31685_().m_123156_();
            this.f_102810_.f_104204_ = (float)Math.PI / 180 * entityarmorstand.m_31685_().m_123157_();
            this.f_102810_.f_104205_ = (float)Math.PI / 180 * entityarmorstand.m_31685_().m_123158_();
            this.f_102812_.f_104203_ = (float)Math.PI / 180 * entityarmorstand.m_31688_().m_123156_();
            this.f_102812_.f_104204_ = (float)Math.PI / 180 * entityarmorstand.m_31688_().m_123157_();
            this.f_102812_.f_104205_ = (float)Math.PI / 180 * entityarmorstand.m_31688_().m_123158_();
            this.f_102811_.f_104203_ = (float)Math.PI / 180 * entityarmorstand.m_31689_().m_123156_();
            this.f_102811_.f_104204_ = (float)Math.PI / 180 * entityarmorstand.m_31689_().m_123157_();
            this.f_102811_.f_104205_ = (float)Math.PI / 180 * entityarmorstand.m_31689_().m_123158_();
            this.f_102814_.f_104203_ = (float)Math.PI / 180 * entityarmorstand.m_31691_().m_123156_();
            this.f_102814_.f_104204_ = (float)Math.PI / 180 * entityarmorstand.m_31691_().m_123157_();
            this.f_102814_.f_104205_ = (float)Math.PI / 180 * entityarmorstand.m_31691_().m_123158_();
            this.f_102814_.m_104227_(1.9f, 11.0f, 0.0f);
            this.f_102813_.f_104203_ = (float)Math.PI / 180 * entityarmorstand.m_31694_().m_123156_();
            this.f_102813_.f_104204_ = (float)Math.PI / 180 * entityarmorstand.m_31694_().m_123157_();
            this.f_102813_.f_104205_ = (float)Math.PI / 180 * entityarmorstand.m_31694_().m_123158_();
            this.f_102813_.m_104227_(-1.9f, 11.0f, 0.0f);
            this.f_102809_.m_104315_(this.f_102808_);
        } else {
            super.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        }
    }
}

