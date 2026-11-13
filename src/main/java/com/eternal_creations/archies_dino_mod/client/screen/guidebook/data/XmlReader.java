/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$Action
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlReader {
    private final Element element;

    private XmlReader(Element element) {
        this.element = element;
    }

    public static XmlReader of(Element element) {
        return new XmlReader(element);
    }

    public Element raw() {
        return this.element;
    }

    public boolean hasAttribute(String key) {
        return this.element.hasAttribute(key);
    }

    public boolean hasAnyAttribute(String ... keys) {
        for (String key : keys) {
            if (!this.element.hasAttribute(key)) continue;
            return true;
        }
        return false;
    }

    public <T> T getAs(String key, Function<String, T> resolver, T defaultValue) {
        return this.hasAttribute(key) ? resolver.apply(this.getString(key)) : defaultValue;
    }

    public String getString(String key) {
        return this.element.getAttribute(key).trim();
    }

    public String getString(String key, String defaultValue) {
        return this.hasAttribute(key) ? this.getString(key) : defaultValue;
    }

    public String getStringAny(String defaultValue, String ... keys) {
        for (String key : keys) {
            if (!this.hasAttribute(key)) continue;
            return this.getString(key);
        }
        return defaultValue;
    }

    public float getFloat(String key) {
        return Float.parseFloat(this.element.getAttribute(key));
    }

    public float getFloat(String key, float defaultValue) {
        return this.hasAttribute(key) ? this.getFloat(key) : defaultValue;
    }

    public Float getFloatAny(Float defaultValue, String ... keys) {
        for (String key : keys) {
            if (!this.hasAttribute(key)) continue;
            return Float.valueOf(this.getFloat(key));
        }
        return defaultValue;
    }

    public Float getFloatOrNull(String key) {
        if (this.hasAttribute(key)) {
            return Float.valueOf(this.getFloat(key));
        }
        return null;
    }

    public Integer getInt(String key) {
        return Integer.parseInt(this.element.getAttribute(key));
    }

    public int getInt(String key, int defaultValue) {
        return this.hasAttribute(key) ? this.getInt(key) : defaultValue;
    }

    public Boolean getBoolean(String key) {
        return this.element.hasAttribute(key) ? Boolean.valueOf(Boolean.parseBoolean(this.element.getAttribute(key))) : null;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Boolean val = this.getBoolean(key);
        return val != null ? val : defaultValue;
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumClass) {
        return this.getEnum(key, enumClass, null);
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumClass, T defaultValue) {
        String value = this.getString(key);
        if (value == null) {
            return defaultValue;
        }
        for (Enum constant : (Enum[])enumClass.getEnumConstants()) {
            if (!constant.name().equalsIgnoreCase(value)) continue;
            return (T)constant;
        }
        return defaultValue;
    }

    public Item getItem(String key) {
        return this.getItem(key, Items.f_41852_);
    }

    public Item getItem(String key, Item defaultValue) {
        String id = this.getString(key);
        if (id == null) {
            return defaultValue;
        }
        ResourceLocation rl = ResourceLocation.m_135820_((String)id);
        return rl != null ? (Item)BuiltInRegistries.f_257033_.m_7745_(rl) : defaultValue;
    }

    public List<Element> getChildren() {
        ArrayList<Element> list = new ArrayList<Element>();
        NodeList nodes = this.element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if (!(node instanceof Element)) continue;
            Element child = (Element)node;
            list.add(child);
        }
        return list;
    }

    public Component asComponent() {
        Style baseStyle = this.extractStyle(this.element, Style.f_131099_);
        if (this.hasAttribute("text")) {
            return Component.m_237113_((String)this.getString("text")).m_130948_(baseStyle);
        }
        if (this.hasAttribute("translate")) {
            return Component.m_237115_((String)this.getString("translate")).m_130948_(baseStyle);
        }
        if (this.hasAttribute("keybind")) {
            return Component.m_237117_((String)this.getString("keybind")).m_130948_(baseStyle);
        }
        return this.parseComponentRecursive(this.element, baseStyle);
    }

    private MutableComponent parseComponentRecursive(Node node, Style currentStyle) {
        MutableComponent root = Component.m_237119_();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == 3) {
                String raw = child.getTextContent();
                if (raw.isBlank()) continue;
                String cleaned = raw.replaceAll("\\s+", " ");
                if (i == 0 && root.m_7360_().isEmpty()) {
                    cleaned = cleaned.stripLeading();
                }
                if (cleaned.isEmpty()) continue;
                root.m_7220_((Component)Component.m_237113_((String)cleaned).m_130948_(currentStyle));
                continue;
            }
            if (!(child instanceof Element)) continue;
            Element el = (Element)child;
            String tag = el.getTagName().toLowerCase();
            if (tag.equals("br")) {
                root.m_7220_((Component)Component.m_237113_((String)"\n"));
                continue;
            }
            XmlReader xml = XmlReader.of(el);
            Style childStyle = this.extractStyle(el, currentStyle);
            if (tag.equals("keybind")) {
                String keyName = xml.getStringAny(null, "id");
                if (keyName == null || keyName.isBlank()) {
                    keyName = el.getTextContent().trim();
                }
                if (keyName.isEmpty()) continue;
                root.m_7220_((Component)Component.m_237117_((String)keyName).m_130948_(childStyle));
                continue;
            }
            if (tag.equals("translate") || xml.hasAttribute("translate")) {
                String key = xml.getStringAny(null, "id");
                if (key == null || key.isBlank()) {
                    key = el.getTextContent().trim();
                }
                if (key.isEmpty()) continue;
                if (this.hasElementChildNodes(el)) {
                    ArrayList<MutableComponent> args = new ArrayList<MutableComponent>();
                    NodeList innerNodes = el.getChildNodes();
                    for (int j = 0; j < innerNodes.getLength(); ++j) {
                        Node inner = innerNodes.item(j);
                        if (!(inner instanceof Element)) continue;
                        Element innerEl = (Element)inner;
                        args.add(this.parseComponentRecursive(innerEl, childStyle));
                    }
                    root.m_7220_((Component)Component.m_237110_((String)key, (Object[])args.toArray()).m_130948_(childStyle));
                    continue;
                }
                root.m_7220_((Component)Component.m_237115_((String)key).m_130948_(childStyle));
                continue;
            }
            root.m_7220_((Component)this.parseComponentRecursive(el, childStyle));
        }
        return root;
    }

    private boolean hasElementChildNodes(Element el) {
        NodeList list = el.getChildNodes();
        for (int i = 0; i < list.getLength(); ++i) {
            if (list.item(i).getNodeType() != 1) continue;
            return true;
        }
        return false;
    }

    private Style extractStyle(Element element, Style base) {
        String href;
        String tag;
        XmlReader xml = XmlReader.of(element);
        Style style = base;
        Object color = xml.getStringAny(null, "color", "hex");
        if (color != null && !((String)color).isEmpty()) {
            TextColor textColor;
            if (!((String)color).startsWith("#")) {
                color = "#" + (String)color;
            }
            if ((textColor = TextColor.m_131268_((String)color)) != null) {
                style = style.m_131148_(textColor);
            }
        }
        if (((tag = element.getTagName().toLowerCase()).equals("a") || tag.equals("link")) && (href = xml.getString("href")) != null) {
            style = style.m_131142_(new ClickEvent(ClickEvent.Action.OPEN_URL, href));
        }
        if (tag.equals("b") || tag.equals("bold")) {
            style = style.m_131136_(Boolean.valueOf(true));
        }
        if (tag.equals("i") || tag.equals("italic")) {
            style = style.m_131155_(Boolean.valueOf(true));
        }
        if (tag.equals("u") || tag.equals("underlined")) {
            style = style.m_131162_(Boolean.valueOf(true));
        }
        return style;
    }
}

