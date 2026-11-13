/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.Util
 *  net.minecraft.client.renderer.RenderStateShard$EmptyTextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TextureStateShard
 *  net.minecraft.client.renderer.RenderStateShard$TransparencyStateShard
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.RenderType$CompositeState
 *  net.minecraft.resources.ResourceLocation
 */
package com.eternal_creations.archies_dino_mod.client.model.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ADRenderTypes
extends RenderType {
    protected static final RenderStateShard.TransparencyStateShard EYES_ALPHA_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("eyes_alpha_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });
    private static final Function<ResourceLocation, RenderType> EYES_NO_CULL = Util.m_143827_(location -> {
        RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(location, false, false);
        return ADRenderTypes.m_173215_((String)"eyes_no_cull", (VertexFormat)DefaultVertexFormat.f_85812_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)false, (boolean)true, (RenderType.CompositeState)RenderType.CompositeState.m_110628_().m_173292_(f_173073_).m_173290_((RenderStateShard.EmptyTextureStateShard)renderstateshard$texturestateshard).m_110685_(f_110135_).m_110687_(f_110115_).m_110661_(f_110110_).m_110691_(false));
    });
    private static final Function<ResourceLocation, RenderType> TRANSLUCENT_EYES = Util.m_143827_(location -> {
        RenderType.CompositeState state = RenderType.CompositeState.m_110628_().m_173292_(f_173073_).m_173290_((RenderStateShard.EmptyTextureStateShard)new RenderStateShard.TextureStateShard(location, false, false)).m_110685_(EYES_ALPHA_TRANSPARENCY).m_110671_(f_110152_).m_110661_(f_110110_).m_110663_(f_110112_).m_110691_(true);
        return RenderType.m_173215_((String)"translucent_eyes", (VertexFormat)DefaultVertexFormat.f_85812_, (VertexFormat.Mode)VertexFormat.Mode.QUADS, (int)256, (boolean)true, (boolean)false, (RenderType.CompositeState)state);
    });

    public ADRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    public static RenderType eyesTranslucent(ResourceLocation texture) {
        return TRANSLUCENT_EYES.apply(texture);
    }

    public static RenderType eyesNoCull(ResourceLocation texture) {
        return EYES_NO_CULL.apply(texture);
    }
}

