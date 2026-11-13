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
public class CoralosaurArmorModel {
    public static MeshDefinition setupOuter() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        PartDefinition head = partdefinition.m_171599_("head", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.5f, -8.5f, -4.5f, 9.0f, 9.0f, 9.0f, new CubeDeformation(0.0f)).m_171514_(56, 43).m_171488_(-4.5f, -7.5f, -10.5f, 9.0f, 4.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(68, 39).m_171488_(-1.5f, -8.5f, -6.5f, 3.0f, 1.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        head.m_171599_("head_coral1", CubeListBuilder.m_171558_().m_171514_(60, 26).m_171488_(-2.0f, -4.0f, 0.0f, 4.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-2.5f, (float)-8.5f, (float)3.5f));
        head.m_171599_("head_coral2", CubeListBuilder.m_171558_().m_171514_(64, 71).m_171488_(-2.0f, -4.0f, 0.0f, 4.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.m_171423_((float)4.5f, (float)-8.5f, (float)0.5f, (float)0.0f, (float)-1.5708f, (float)0.0f));
        partdefinition.m_171599_("hat", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_().m_171514_(0, 33).m_171488_(-5.0f, 0.0f, -3.0f, 10.0f, 12.0f, 6.0f, new CubeDeformation(-0.1f)).m_171514_(0, 18).m_171488_(-5.5f, -0.5f, -3.5f, 11.0f, 8.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        PartDefinition shell = body.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(36, 0).m_171488_(-3.0f, -5.0f, -4.0f, 11.0f, 11.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-2.5f, (float)4.5f, (float)7.5f));
        shell.m_171599_("shell_coral1", CubeListBuilder.m_171558_().m_171514_(24, 51).m_171488_(0.0f, -3.0f, 0.0f, 0.0f, 6.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        shell.m_171599_("shell_coral2", CubeListBuilder.m_171558_().m_171514_(56, 71).m_171488_(0.0f, -3.0f, 0.0f, 0.0f, 6.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)6.0f, (float)2.0f, (float)0.0f));
        partdefinition.m_171599_("left_arm", CubeListBuilder.m_171558_().m_171514_(56, 53).m_171480_().m_171488_(-1.5f, -2.5f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(32, 43).m_171488_(-1.75f, -2.75f, -3.0f, 6.0f, 12.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)5.0f, (float)2.0f, (float)0.0f));
        PartDefinition right_arm = partdefinition.m_171599_("right_arm", CubeListBuilder.m_171558_().m_171514_(0, 51).m_171488_(-4.25f, -2.75f, -3.0f, 6.0f, 12.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(56, 53).m_171488_(-3.5f, -2.5f, -2.5f, 5.0f, 13.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-5.0f, (float)2.0f, (float)0.0f));
        right_arm.m_171599_("arm_coral", CubeListBuilder.m_171558_().m_171514_(44, 61).m_171488_(0.0f, -7.0f, -3.0f, 0.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)-4.25f, (float)-2.75f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(66, 0).m_171488_(-3.0f, 5.35f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(0, 69).m_171488_(-2.5f, 5.1f, -2.5f, 5.0f, 7.0f, 5.0f, new CubeDeformation(0.0f)).m_171514_(32, 33).m_171488_(-3.0f, 12.35f, -5.0f, 8.0f, 0.0f, 10.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(0, 69).m_171480_().m_171488_(-2.5f, 5.1f, -2.5f, 5.0f, 7.0f, 5.0f, new CubeDeformation(0.0f)).m_171555_(false).m_171514_(68, 26).m_171488_(-3.0f, 5.35f, -3.0f, 6.0f, 7.0f, 6.0f, new CubeDeformation(0.0f)).m_171514_(32, 33).m_171480_().m_171488_(-5.0f, 12.35f, -5.0f, 8.0f, 0.0f, 10.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }

    public static MeshDefinition setupInner() {
        MeshDefinition meshdefinition = HumanoidModel.m_170681_((CubeDeformation)CubeDeformation.f_171458_, (float)0.0f);
        PartDefinition partdefinition = meshdefinition.m_171576_();
        PartDefinition body = partdefinition.m_171599_("body", CubeListBuilder.m_171558_(), PartPose.m_171419_((float)0.0f, (float)-12.0f, (float)0.0f));
        PartDefinition lower_back = body.m_171599_("lower_back", CubeListBuilder.m_171558_().m_171514_(36, 15).m_171488_(-5.0f, -26.0f, -10.51f, 10.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)34.5f, (float)7.0f));
        PartDefinition tail = lower_back.m_171599_("tail", CubeListBuilder.m_171558_().m_171514_(70, 13).m_171488_(-2.5f, -1.0f, 0.0f, 5.0f, 8.0f, 3.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)-25.0f, (float)-3.5f));
        PartDefinition tail2 = tail.m_171599_("tail2", CubeListBuilder.m_171558_().m_171514_(50, 26).m_171488_(-1.5f, 0.0f, -1.0f, 3.0f, 4.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)7.0f, (float)1.0f));
        tail2.m_171599_("tail3", CubeListBuilder.m_171558_().m_171514_(36, 26).m_171488_(-3.5f, -2.0f, 0.0f, 7.0f, 7.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)0.0f, (float)2.0f, (float)0.0f));
        partdefinition.m_171599_("left_leg", CubeListBuilder.m_171558_().m_171514_(24, 61).m_171488_(-2.5f, -0.1f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)), PartPose.m_171419_((float)1.9f, (float)-12.0f, (float)0.0f));
        partdefinition.m_171599_("right_leg", CubeListBuilder.m_171558_().m_171514_(24, 61).m_171480_().m_171488_(-2.5f, -0.1f, -2.5f, 5.0f, 12.0f, 5.0f, new CubeDeformation(0.0f)).m_171555_(false), PartPose.m_171419_((float)-1.9f, (float)-12.0f, (float)0.0f));
        return meshdefinition;
    }
}

