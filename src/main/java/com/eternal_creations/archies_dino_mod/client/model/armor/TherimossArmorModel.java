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
public class TherimossArmorModel {
    public static MeshDefinition setupOuter() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.5f, -8.5f, -4.5f, 9.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).m_171514_(60, 64).m_171488_(-4.5f, -12.5f, -4.5f, 9.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(36, 0).m_171488_(-3.5f, -8.5f, -11.5f, 7.0f, 9.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        partdefinition.m_171599_("hat", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-5.0f, -0.2f, -3.0f, 10.0f, 12.0f, 6.0f, new CubeDeformation(-0.1f)).m_171514_(0, 18).m_171488_(-5.5f, -0.5f, -3.5f, 11.0f, 8.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(56, 46).m_171480_().m_171488_(-1.5f, -2.5f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(32, 46).m_171488_(-1.75f, -2.75f, -3.0f, 6.0f, 12.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)5.0f, (float)2.0f, (float)0.0f));
        partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(0, 51).m_171488_(-4.25f, -2.75f, -3.0f, 6.0f, 12.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(56, 46).m_171488_(-3.5f, -2.5f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-5.0f, (float)2.0f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(36, 16).m_171488_(-3.0f, 5.35f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(64, 0).m_171488_(-2.5f, 5.1f, -2.5f, 5.0f, 7.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(64, 0).m_171480_().m_171488_(-2.5f, 5.1f, -2.5f, 5.0f, 7.0f, 5.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(60, 16).m_171488_(-3.0f, 5.35f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }

    public static MeshDefinition setupInner() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(32, 33).m_171488_(-5.0f, 8.5f, -3.51f, 10.0f, 6.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(40, 64).m_171488_(-2.5f, -0.25f, -2.5f, 5.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(40, 64).m_171480_().m_171488_(-2.5f, -0.25f, -2.5f, 5.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }
}

