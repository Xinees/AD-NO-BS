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
public class SculksaurArmorModel {
    public static MeshDefinition setupOuter() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 24).m_171488_(-4.5f, -8.45f, -4.5f, 9.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).m_171514_(0, 0).m_171488_(-4.5f, -14.425f, -4.5f, 9.0f, 15.0f, 9.0f, new CubeDeformation(0.25f)), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        PartDefinition jaws = head.m_171599_("jaws", CubeListBuilder.m_171558_().m_171514_(68, 16).m_171488_(-7.35f, -6.8f, -2.45f, 7.0f, 9.0f, 4.0f, new CubeDeformation(0.0f)).m_171514_(72, 2).m_171488_(-7.35f, -6.81f, -2.45f, 7.0f, 9.0f, 5.0f, new CubeDeformation(-0.1f)), PartPose.m_171419_((float)3.85f, (float)-1.65f, (float)-6.05f));
        jaws.m_171599_("lower_teeth_left", CubeListBuilder.m_171558_().m_171514_(40, 58).m_171488_(-0.25f, -3.75f, -1.1f, 0.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171423_((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.1309f));
        jaws.m_171599_("lower_teeth_right", CubeListBuilder.m_171558_().m_171514_(40, 58).m_171480_().m_171488_(0.25f, -3.75f, -1.1f, 0.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171423_((float)-7.7f, (float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f, (float)-0.1309f));
        PartDefinition tendrils = head.m_171599_("tendrils", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)-2.75f, (float)4.95f));
        tendrils.m_171599_("tendril1", CubeListBuilder.m_171558_().m_171514_(68, 29).m_171488_(0.0f, -1.35f, -0.5f, 0.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)1.65f, (float)0.0f));
        tendrils.m_171599_("tendril2", CubeListBuilder.m_171558_().m_171514_(68, 35).m_171488_(0.0f, -1.35f, -0.5f, 0.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-1.65f, (float)0.0f));
        tendrils.m_171599_("tendril3", CubeListBuilder.m_171558_().m_171514_(68, 41).m_171488_(0.0f, -1.35f, -0.5f, 0.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-4.95f, (float)0.0f));
        partdefinition.m_171599_("hat", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(36, 34).m_171488_(-5.0f, 9.2f, -3.0f, 10.0f, 4.0f, 6.0f, new CubeDeformation(0.1f)).m_171514_(36, 16).m_171488_(-5.0f, 1.19f, -3.0f, 10.0f, 12.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(36, 0).m_171488_(-5.5f, 0.35f, -3.5f, 11.0f, 9.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-13.2f, (float)0.0f));
        partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(0, 42).m_171488_(-1.5f, -1.45f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.2f)).m_171514_(44, 44).m_171488_(-1.5f, -2.325f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.2f)), PartPose.m_171419_((float)5.5f, (float)2.2f, (float)0.0f));
        partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(44, 57).m_171488_(-4.5f, -2.325f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.2f)).m_171514_(0, 42).m_171480_().m_171488_(-3.5f, -1.45f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.2f)).m_171555_(false), PartPose.m_171419_((float)-5.5f, (float)2.2f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(20, 44).m_171488_(-3.0f, 4.35f, -3.0f, 6.0f, 8.0f, 6.0f, new CubeDeformation(0.1f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(20, 44).m_171480_().m_171488_(-3.0f, 4.35f, -3.0f, 6.0f, 8.0f, 6.0f, new CubeDeformation(0.1f)).m_171555_(false), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }

    public static MeshDefinition setupInner() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(36, 34).m_171488_(-5.0f, 9.2f, -3.0f, 10.0f, 4.0f, 6.0f, new CubeDeformation(0.1f)), PartPose.m_171419_((float)0.0f, (float)-13.2f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(20, 58).m_171488_(-2.25f, 0.09f, -2.75f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.1f)), PartPose.m_171419_((float)2.09f, (float)-13.2f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 60).m_171488_(-2.25f, 0.09f, -2.75f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.1f)), PartPose.m_171419_((float)-2.09f, (float)-13.2f, (float)0.0f));
        return meshdefinition;
    }
}

