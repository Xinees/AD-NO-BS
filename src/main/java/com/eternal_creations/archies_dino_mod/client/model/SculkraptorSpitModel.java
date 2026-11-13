/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.model.HierarchicalModel
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.world.entity.Entity
 */
package com.eternal_creations.archies_dino_mod.client.model;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SculkraptorSpitModel<T extends Entity>
extends HierarchicalModel<T> {
    private final ModelPart root;

    public SculkraptorSpitModel(ModelPart root) {
        this.root = root;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.m_171576_();
        PartDefinition main = partDefinition.m_171599_("main", CubeListBuilder.m_171558_().m_171514_(0, 6).m_171481_(-1.0f, 1.0f, -1.0f, 2.0f, 2.0f, 2.0f), PartPose.f_171404_);
        main.m_171599_("shape", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-1.5f, 0.5f, -1.5f, 3.0f, 3.0f, 3.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_((MeshDefinition)meshDefinition, (int)16, (int)16);
    }

    public void m_6973_(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public ModelPart m_142109_() {
        return this.root;
    }
}

