/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.core.system.TransformSystem
 *  com.astemir.astemirlib.re.client.model.render.pipeline.ModelRenderContext
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.RenderLayer
 *  com.astemir.astemirlib.re.core.math.Transform
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.ItemInHandRenderer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 */
package com.eternal_creations.archies_dino_mod.client.particle;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.core.system.TransformSystem;
import com.astemir.astemirlib.re.client.model.render.pipeline.ModelRenderContext;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.RenderLayer;
import com.astemir.astemirlib.re.core.math.Transform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemLayer<T extends LivingEntity>
implements RenderLayer<T> {
    private final ItemInHandRenderer itemInHandRenderer;
    private final AstemirModel<T> model;
    private final Function<T, ItemStack> itemGetter;
    private final ItemDisplayContext context;
    private final int holdBoneId;
    protected final float translationX;
    protected final float translationY;
    protected final float translationZ;
    protected final float yRot;
    protected final float xRot;
    protected final boolean rightHanded;

    public ItemLayer(AstemirModel<T> model, Function<T, ItemStack> itemGetter, ItemDisplayContext context, String holdBoneId, float translationX, float translationY, float translationZ, float yRot, float xRot, boolean rightHanded) {
        this.itemInHandRenderer = Minecraft.m_91087_().f_91063_.f_109055_;
        this.model = model;
        this.itemGetter = itemGetter;
        this.context = context;
        this.holdBoneId = model.getBoneIdByName(holdBoneId);
        this.translationX = translationX;
        this.translationY = translationY;
        this.translationZ = translationZ;
        this.yRot = yRot;
        this.xRot = xRot;
        this.rightHanded = rightHanded;
    }

    public void render(T instance, TransformSystem<?> system, ModelRenderContext context) {
        if (this.holdBoneId == -1) {
            return;
        }
        ItemStack stack = this.itemGetter.apply(instance);
        if (!stack.m_41619_()) {
            PoseStack poseStack = context.getPoseStack();
            poseStack.m_85836_();
            Transform transform = system.getBoneTransform(this.holdBoneId);
            poseStack.m_85850_().m_252922_().mul(transform.pose());
            poseStack.m_252781_(Axis.f_252529_.m_252977_(this.xRot));
            poseStack.m_252781_(Axis.f_252436_.m_252977_(this.yRot));
            poseStack.m_252880_(this.translationX, this.translationY, this.translationZ);
            this.itemInHandRenderer.m_269530_(instance, stack, this.context, this.rightHanded, poseStack, context.getBufferSource(), context.getPackedLight());
            poseStack.m_85849_();
        }
    }

    public AstemirModel<T> getModel() {
        return this.model;
    }
}

