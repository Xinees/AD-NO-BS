/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.HumanoidModel
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeDeformation
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 */
package com.eternal_creations.archies_dino_mod.client.model.armor;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ObsidiceratopsArmorModel {
    public static MeshDefinition setupOuter() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.5f, -8.5f, -4.5f, 9.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).m_171514_(36, 27).m_171488_(-1.5f, -12.5f, -9.5f, 3.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).m_171514_(44, 65).m_171488_(-1.5f, -9.5f, -9.5f, 3.0f, 3.0f, 9.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        partdefinition.m_171599_("hat", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        PartDefinition frill = head.m_171599_("frill", CubeListBuilder.m_171558_().m_171514_(68, 0).m_171488_(6.5f, -5.5f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(68, 0).m_171488_(6.5f, -9.5f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(20, 52).m_171488_(6.5f, -13.5f, -1.0f, 3.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(68, 4).m_171488_(7.5f, -15.5f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(20, 56).m_171488_(1.5f, -15.5f, -1.0f, 3.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(20, 56).m_171480_().m_171488_(-4.5f, -15.5f, -1.0f, 3.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(68, 4).m_171480_().m_171488_(-9.5f, -15.5f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(20, 52).m_171480_().m_171488_(-9.5f, -13.5f, -1.0f, 3.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(68, 0).m_171480_().m_171488_(-8.5f, -9.5f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(68, 0).m_171480_().m_171488_(-8.5f, -5.5f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.5f));
        frill.m_171599_("frill_body", CubeListBuilder.m_171558_().m_171514_(20, 60).m_171488_(-1.0f, -2.0f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(62, 40).m_171488_(-6.0f, -2.0f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(32, 34).m_171488_(-9.0f, -4.0f, -1.0f, 13.0f, 11.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)2.5f, (float)-9.5f, (float)0.0f));
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171488_(-5.0f, 0.0f, -3.0f, 10.0f, 12.0f, 6.0f, new CubeDeformation(-0.1f)).m_171514_(0, 18).m_171488_(-5.5f, -0.5f, -3.5f, 11.0f, 9.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(0, 52).m_171488_(-1.5f, -2.5f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.0f)).m_171514_(36, 10).m_171488_(-1.75f, -2.75f, -3.1f, 6.1f, 11.0f, 6.2f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)5.0f, (float)2.0f, (float)0.0f));
        PartDefinition rightArm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(32, 47).m_171488_(-4.35f, -2.75f, -3.1f, 6.1f, 11.0f, 6.2f, new CubeDeformation(0.0f)).m_171514_(56, 47).m_171488_(-3.5f, -2.5f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-5.0f, (float)2.0f, (float)0.0f));
        rightArm.m_171599_("group3", CubeListBuilder.m_171558_().m_171514_(48, 27).m_171488_(-2.0f, -5.0f, -1.0f, 2.0f, 5.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-4.25f, (float)-0.75f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(62, 27).m_171488_(-3.1f, 5.15f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(56, 27).m_171488_(-1.1f, 0.15f, -4.0f, 2.0f, 2.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(20, 64).m_171488_(-2.9f, 5.15f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(56, 30).m_171488_(-0.9f, 0.15f, -4.0f, 2.0f, 2.0f, 1.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }

    public static MeshDefinition setupInner() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(36, 0).m_171488_(-5.0f, 8.1f, -3.0f, 10.0f, 4.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(60, 10).m_171488_(-2.6f, 0.1f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(60, 10).m_171480_().m_171488_(-2.4f, 0.1f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }
}

