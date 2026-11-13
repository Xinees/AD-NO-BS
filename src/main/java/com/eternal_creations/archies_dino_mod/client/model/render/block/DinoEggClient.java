/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.client.model.AstemirModel
 *  com.astemir.astemirlib.re.client.model.core.system.ModelSystem$Listener
 *  com.astemir.astemirlib.re.client.model.core.texture.ModelTexture
 *  com.astemir.astemirlib.re.client.model.render.component.BufferComponent
 *  com.astemir.astemirlib.re.client.model.render.component.LightComponent
 *  com.astemir.astemirlib.re.client.model.render.renderer.AstemirBlockEntityRenderer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer
 *  com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer
 *  com.astemir.astemirlib.re.client.shader.post.PostRenderType
 *  com.astemir.astemirlib.re.client.shader.post.pass.BloomPass
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.properties.Property
 */
package com.eternal_creations.archies_dino_mod.client.model.render.block;

import com.astemir.astemirlib.re.client.model.AstemirModel;
import com.astemir.astemirlib.re.client.model.core.system.ModelSystem;
import com.astemir.astemirlib.re.client.model.core.texture.ModelTexture;
import com.astemir.astemirlib.re.client.model.render.component.BufferComponent;
import com.astemir.astemirlib.re.client.model.render.component.LightComponent;
import com.astemir.astemirlib.re.client.model.render.renderer.AstemirBlockEntityRenderer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.BaseRenderLayer;
import com.astemir.astemirlib.re.client.model.render.renderer.layer.LayerContainer;
import com.astemir.astemirlib.re.client.shader.post.PostRenderType;
import com.astemir.astemirlib.re.client.shader.post.pass.BloomPass;
import com.eternal_creations.archies_dino_mod.common.block.eggs.DinoEggBlock;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityChorusoatlEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityLushsaurEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityObsidiceratopsEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntitySculkraptorEgg;
import com.eternal_creations.archies_dino_mod.common.block.entity.egg.BlockEntityTherimossEgg;
import java.util.function.Function;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DinoEggClient<T extends BlockEntity>
implements ModelSystem.Listener<T> {
    public static final String LUSHSAUR = "lushsaur_egg";
    public static final String CHORUSOATL = "chorusoatl_egg";
    public static final String OBSIDICERATOPS = "obsidiceratops_egg";
    public static final String SCULKRAPTOR = "sculkraptor_egg";
    public static final String THERIMOSS = "therimoss_egg";

    public static void setupLushsaur(AstemirModel<BlockEntityLushsaurEgg> model, LayerContainer<BlockEntityLushsaurEgg> layers) {
        DinoEggClient.setupModel(model, layers, LUSHSAUR);
    }

    public static void setupChorusoatl(AstemirModel<BlockEntityChorusoatlEgg> model, LayerContainer<BlockEntityChorusoatlEgg> layers) {
        DinoEggClient.setupModel(model, layers, CHORUSOATL);
    }

    public static void setupObsidiceratops(AstemirModel<BlockEntityObsidiceratopsEgg> model, LayerContainer<BlockEntityObsidiceratopsEgg> layers) {
        for (int i = 0; i <= 4; ++i) {
            int finalI = i;
            layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)("obsidiceratops_egg_" + i), RenderType::m_110458_)).condition(egg -> (Integer)egg.m_58900_().m_61143_((Property)DinoEggBlock.HATCH) == finalI).light((LightComponent)LightComponent.of((int)10)));
        }
        ModelTexture emissive = ModelTexture.of(model, (String)"emissive", RenderType::m_110488_);
        layers.add(BaseRenderLayer.of((ModelTexture)emissive).light((LightComponent)LightComponent.of((int)15)).buffer(BufferComponent.postEffect((Function)PostRenderType.MODEL_UNLIT, (boolean)true, (Class[])new Class[]{BloomPass.class})));
    }

    public static void setupSculkraptor(AstemirModel<BlockEntitySculkraptorEgg> model, LayerContainer<BlockEntitySculkraptorEgg> layers) {
        DinoEggClient.setupModel(model, layers, SCULKRAPTOR);
    }

    public static void setupTherimoss(AstemirModel<BlockEntityTherimossEgg> model, LayerContainer<BlockEntityTherimossEgg> layers) {
        DinoEggClient.setupModel(model, layers, THERIMOSS);
    }

    public static <T extends BlockEntity> void setupModel(AstemirModel<T> model, LayerContainer<T> layers, String name) {
        for (int i = 0; i <= 4; ++i) {
            DinoEggClient.addLayer(model, layers, name, i);
        }
    }

    protected static <T extends BlockEntity> void addLayer(AstemirModel<T> model, LayerContainer<T> layers, String name, int i) {
        layers.add(BaseRenderLayer.of((ModelTexture)ModelTexture.of(model, (String)(name + "_" + i), RenderType::m_110458_)).condition(egg -> (Integer)egg.m_58900_().m_61143_((Property)DinoEggBlock.HATCH) == i));
    }

    public static <T extends BlockEntity> AstemirBlockEntityRenderer<T> createRenderer(AstemirModel<T> model) {
        return AstemirBlockEntityRenderer.of(block -> model, DinoEggClient::new);
    }
}

