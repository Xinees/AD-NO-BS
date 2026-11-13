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
public class LushsaurArmorModel {
    public static MeshDefinition setupOuter() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.5f, -8.5f, -4.5f, 9.0f, 9.0f, 9.0f, new CubeDeformation(0.1f)).m_171514_(0, 18).m_171488_(-4.5f, -8.5f, -4.5f, 9.0f, 9.0f, 9.0f, new CubeDeformation(0.5f)).m_171514_(72, 73).m_171488_(-1.5f, -7.5f, -5.5f, 3.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(72, 49).m_171488_(-2.0f, -11.0f, -2.0f, 4.0f, 3.0f, 4.0f, new CubeDeformation(0.0f)).m_171514_(0, 79).m_171488_(4.5f, -5.5f, -0.5f, 2.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).m_171514_(0, 79).m_171480_().m_171488_(-6.5f, -5.5f, -0.5f, 2.0f, 3.0f, 3.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(10, 79).m_171488_(-2.5f, -8.5f, -5.5f, 5.0f, 2.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        partdefinition.m_171599_("hat", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(36, 0).m_171488_(-5.0f, 0.0f, -3.0f, 10.0f, 7.0f, 6.0f, new CubeDeformation(0.1f)), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        PartDefinition shell = body.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(36, 13).m_171488_(-6.0f, -23.0f, 2.0f, 12.0f, 14.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(0, 36).m_171488_(-6.0f, -25.0f, 4.0f, 12.0f, 16.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)24.0f, (float)0.0f));
        shell.m_171599_("spike", CubeListBuilder.m_171558_().m_171514_(0, 73).m_171488_(-2.0f, -3.0f, -1.0f, 4.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)3.0f, (float)-11.0f, (float)9.0f));
        shell.m_171599_("spike2", CubeListBuilder.m_171558_().m_171514_(12, 73).m_171488_(-2.0f, 7.0f, 0.0f, 4.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-3.0f, (float)-21.0f, (float)8.0f));
        shell.m_171599_("spike3", CubeListBuilder.m_171558_().m_171514_(24, 73).m_171488_(-2.0f, -3.0f, -1.0f, 4.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)3.0f, (float)-16.0f, (float)9.0f));
        shell.m_171599_("spike4", CubeListBuilder.m_171558_().m_171514_(36, 73).m_171488_(-2.0f, 2.0f, 0.0f, 4.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-3.0f, (float)-21.0f, (float)8.0f));
        shell.m_171599_("spike5", CubeListBuilder.m_171558_().m_171514_(48, 73).m_171488_(-2.0f, -3.0f, -1.0f, 4.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)3.0f, (float)-21.0f, (float)9.0f));
        shell.m_171599_("spike6", CubeListBuilder.m_171558_().m_171514_(60, 73).m_171488_(-2.0f, -3.0f, 0.0f, 4.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-3.0f, (float)-21.0f, (float)8.0f));
        partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(32, 39).m_171488_(-1.5f, -2.0f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)).m_171514_(64, 13).m_171488_(-2.0f, -2.5f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(68, 0).m_171488_(-2.0f, -2.5f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.5f)), PartPose.m_171419_((float)5.0f, (float)2.0f, (float)0.0f));
        partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(68, 25).m_171488_(-4.0f, -2.5f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(52, 39).m_171488_(-3.5f, -2.0f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-5.0f, (float)2.0f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(72, 37).m_171488_(-3.0f, 6.35f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(20, 79).m_171488_(-1.0f, 10.4f, -3.75f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(28, 79).m_171488_(-1.0f, 10.4f, 1.75f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(36, 79).m_171488_(1.75f, 10.4f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171514_(44, 79).m_171488_(-3.75f, 10.4f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(72, 37).m_171480_().m_171488_(-3.0f, 6.35f, -3.0f, 6.0f, 6.0f, 6.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(20, 79).m_171480_().m_171488_(-1.0f, 10.4f, -3.75f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(28, 79).m_171480_().m_171488_(-1.0f, 10.4f, 1.75f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(36, 79).m_171480_().m_171488_(-3.75f, 10.4f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(44, 79).m_171480_().m_171488_(1.75f, 10.4f, -1.0f, 2.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }

    public static MeshDefinition setupInner() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(36, 29).m_171488_(-5.0f, 8.0f, -3.0f, 10.0f, 4.0f, 6.0f, new CubeDeformation(0.1f)), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(0, 56).m_171488_(-2.5f, -0.1f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)).m_171514_(20, 56).m_171488_(-2.5f, 0.0f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.25f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(40, 56).m_171488_(-2.5f, 0.0f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.25f)).m_171514_(60, 56).m_171488_(-2.5f, -0.1f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }
}

