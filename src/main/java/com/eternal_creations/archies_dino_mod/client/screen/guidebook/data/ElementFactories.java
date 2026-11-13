/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.world.item.ItemStack
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.data;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.GuidebookData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.XmlReader;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIElement;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.animation.HoverAnimation;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.AbstractWidget;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.Button;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.Container;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.ImageView;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.RecipeViewer;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.widget.TextLabel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import org.w3c.dom.Element;

public class ElementFactories {
    private static final Map<String, TagParser> PARSERS = new HashMap<String, TagParser>();

    private static <T extends AbstractWidget, B extends AbstractWidget.Builder<B, T>> UIElement.Factory parseContainer(XmlReader xml, String id, Function<float[], B> builderFactory) {
        DimensionSpec dimSpec = ElementFactories.parseDimensions(xml, true);
        BaseSpec baseSpec = ElementFactories.parseBase(xml, id);
        List<UIElement.Factory> childFactories = ElementFactories.parseChildren(xml, id);
        return (parentWidth, parentHeight) -> {
            float[] d = dimSpec.resolve(parentWidth, parentHeight);
            AbstractWidget.Builder builder = (AbstractWidget.Builder)builderFactory.apply(d);
            baseSpec.apply(builder);
            UIElement container = builder.build();
            for (UIElement.Factory childFactory : childFactories) {
                UIElement child = childFactory.create(d[2], d[3]);
                if (child == null) continue;
                ((AbstractWidget)container).addChild((UIElement)child);
            }
            return container;
        };
    }

    private static DimensionSpec parseDimensions(XmlReader xml, boolean inheritParentSize) {
        float x = xml.getFloat("x", 0.0f);
        float y = xml.getFloat("y", 0.0f);
        Float width = xml.getFloatAny(null, "w", "width");
        Float height = xml.getFloatAny(null, "h", "height");
        return new DimensionSpec(x, y, width, height, inheritParentSize);
    }

    private static BaseSpec parseBase(XmlReader xml, String id) {
        HoverAnimation hoverAnimation = xml.getAs("hover-animation", HoverAnimation::parse, null);
        boolean absolute = xml.getBoolean("absolute", false);
        Rect2f.Mutable hitbox = Rect2f.of(xml.getFloat("hitbox-x", 0.0f), xml.getFloat("hitbox-y", 0.0f), xml.getFloat("hitbox-w", 0.0f), xml.getFloat("hitbox-h", 0.0f));
        ResourceLocation popupTexture = GuidebookData.resolve(id, xml.getString("popup"));
        List<UIElement.Factory> popupChildren = popupTexture != null ? ElementFactories.parsePopupChildren(xml, id) : List.of();
        return new BaseSpec(hoverAnimation, absolute, hitbox, popupTexture, popupChildren);
    }

    public static void register(String tag, TagParser parser) {
        PARSERS.put(tag.toLowerCase(), parser);
    }

    public static UIElement.Factory parseElementFactory(Element element, String id) {
        TagParser parser = PARSERS.get(element.getTagName().toLowerCase());
        return parser != null ? parser.parse(XmlReader.of(element), id) : null;
    }

    public static List<UIElement.Factory> parseChildren(XmlReader parent, String id) {
        ArrayList<UIElement.Factory> list = new ArrayList<UIElement.Factory>();
        for (Element child : parent.getChildren()) {
            UIElement.Factory factory;
            if (child.getTagName().equalsIgnoreCase("popup-content") || (factory = ElementFactories.parseElementFactory(child, id)) == null) continue;
            list.add(factory);
        }
        return list;
    }

    public static List<UIElement.Factory> parsePopupChildren(XmlReader parent, String id) {
        for (Element child : parent.getChildren()) {
            if (!child.getTagName().equalsIgnoreCase("popup-content")) continue;
            return ElementFactories.parseChildren(XmlReader.of(child), id);
        }
        return List.of();
    }

    static {
        ElementFactories.register("image", (xml, id) -> {
            ResourceLocation src = xml.hasAttribute("src") ? GuidebookData.resolve(id, xml.getString("src")) : null;
            DimensionSpec dimSpec = ElementFactories.parseDimensions(xml, true);
            float srcX = xml.getFloat("src-x", 0.0f);
            float srcY = xml.getFloat("src-y", 0.0f);
            Float explicitUWidth = xml.getFloatOrNull("src-width");
            Float explicitVHeight = xml.getFloatOrNull("src-height");
            float texW = xml.getFloat("texture-width", 0.0f);
            float texH = xml.getFloat("texture-height", 0.0f);
            BaseSpec baseSpec = ElementFactories.parseBase(xml, id);
            return (parentWidth, parentHeight) -> {
                float[] d = dimSpec.resolve(parentWidth, parentHeight);
                ImageView.Builder builder = ImageView.builder(d[0], d[1], d[2], d[3]).texture(src);
                if (src != null) {
                    builder.uv(srcX, srcY);
                    builder.spriteSize(explicitUWidth != null ? explicitUWidth.floatValue() : d[2], explicitVHeight != null ? explicitVHeight.floatValue() : d[3]);
                    builder.textureSize(texW, texH);
                }
                baseSpec.apply(builder);
                return builder.build();
            };
        });
        ElementFactories.register("button", (xml, id) -> {
            DimensionSpec dimSpec = ElementFactories.parseDimensions(xml, true);
            BaseSpec baseSpec = ElementFactories.parseBase(xml, id);
            return (parentWidth, parentHeight) -> {
                float[] d = dimSpec.resolve(parentWidth, parentHeight);
                Button.Builder builder = Button.builder(d[0], d[1], d[2], d[3]);
                baseSpec.apply(builder);
                return builder.build();
            };
        });
        ElementFactories.register("label", (xml, id) -> {
            Component text = xml.asComponent();
            DimensionSpec dimSpec = ElementFactories.parseDimensions(xml, false);
            float scale = xml.getFloat("scale", 1.0f);
            float scaleX = xml.getFloat("scale-x", scale);
            float scaleY = xml.getFloat("scale-y", scale);
            TextLabel.Alignment align = xml.getEnum("align", TextLabel.Alignment.class, TextLabel.Alignment.LEFT);
            BaseSpec baseSpec = ElementFactories.parseBase(xml, id);
            return (parentWidth, parentHeight) -> {
                float[] d = dimSpec.resolve(parentWidth, parentHeight);
                TextLabel.Builder builder = TextLabel.builder(text, d[0], d[1], d[2]).scale(scaleX, scaleY).align(align);
                baseSpec.apply(builder);
                return builder.build();
            };
        });
        ElementFactories.register("recipe", (xml, id) -> {
            ItemStack item = xml.getItem("item").m_7968_();
            return ElementFactories.parseContainer(xml, id, d -> RecipeViewer.builder(item, d[0], d[1]));
        });
        ElementFactories.register("container", (xml, id) -> ElementFactories.parseContainer(xml, id, d -> Container.builder(d[0], d[1], d[2], d[3])));
    }

    private record DimensionSpec(float x, float y, Float explicitWidth, Float explicitHeight, boolean inheritParentSize) {
        public float[] resolve(float parentWidth, float parentHeight) {
            float width;
            float f = this.explicitWidth != null ? this.explicitWidth.floatValue() : (width = this.inheritParentSize ? parentWidth : 0.0f);
            float height = this.explicitHeight != null ? this.explicitHeight.floatValue() : (this.inheritParentSize ? parentHeight : 10.0f);
            return new float[]{this.x, this.y, width, height};
        }
    }

    private record BaseSpec(HoverAnimation hover, boolean absolute, Rect2f hitbox, ResourceLocation popupTexture, List<UIElement.Factory> popupChildren) {
        public <B extends AbstractWidget.Builder<B, ?>> void apply(B builder) {
            if (this.hover != null) {
                builder.hoverAnimation(this.hover);
            }
            if (this.absolute) {
                builder.absolute(true);
            }
            if (!this.hitbox.isIdentity()) {
                builder.hitbox(this.hitbox);
            }
            if (this.popupTexture != null) {
                builder.clicked((ctx, clicked) -> {
                    ctx.popupManager().openImage(this.popupTexture, ctx.bookWidth(), ctx.bookHeight(), this.popupChildren);
                    ctx.playSound(SoundEvents.f_11713_);
                    return true;
                });
            }
        }
    }

    @FunctionalInterface
    public static interface TagParser {
        public UIElement.Factory parse(XmlReader var1, String var2);
    }
}

