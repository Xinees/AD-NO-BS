/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.data;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.CategoryData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.ElementFactories;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.data.PageData;
import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.UIElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public record GuidebookData(List<CategoryData> categories, String badgesPath) {
    private static final String BASE_PATH = "textures/gui/guidebook/categories/%s/%s.png";
    private static final String FALLBACK_PATH = "guidebook/en_us.xml";

    public static GuidebookData loadData() {
        GuidebookData guidebookData;
        block8: {
            String lang = Minecraft.m_91087_().m_91102_().m_264236_();
            ResourceLocation langPath = Constants.prefix("guidebook/" + lang + ".xml");
            ResourceLocation fallbackPath = Constants.prefix(FALLBACK_PATH);
            ResourceManager rm = Minecraft.m_91087_().m_91098_();
            ResourceLocation targetPath = rm.m_213713_(langPath).isPresent() ? langPath : fallbackPath;
            InputStream stream = rm.m_215595_(targetPath);
            try {
                guidebookData = GuidebookData.parseXML(stream);
                if (stream == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (stream != null) {
                        try {
                            stream.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    e.printStackTrace(System.err);
                    return new GuidebookData(List.of(), null);
                }
            }
            stream.close();
        }
        return guidebookData;
    }

    private static GuidebookData parseXML(InputStream stream) throws ParserConfigurationException, IOException, SAXException {
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        doc.getDocumentElement().normalize();
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getElementsByTagName("category");
        ArrayList<CategoryData> categories = new ArrayList<CategoryData>();
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if (!(node instanceof Element)) continue;
            Element element = (Element)node;
            categories.add(GuidebookData.parseCategory(element));
        }
        return new GuidebookData(categories, root.getAttribute("badges"));
    }

    private static PageData parsePage(String id, Element element) {
        PageData page = new PageData(GuidebookData.resolve(id, element.getAttribute("src")));
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Element childElement;
            UIElement.Factory factory;
            Node node = children.item(i);
            if (!(node instanceof Element) || (factory = ElementFactories.parseElementFactory(childElement = (Element)node, id)) == null) continue;
            page.addElementFactory(factory);
        }
        return page;
    }

    private static CategoryData parseCategory(Element element) {
        String id = element.getAttribute("id");
        CategoryData categoryData = new CategoryData(id, GuidebookData.resolve(id, "background"), GuidebookData.resolve(id, "markers/on"), GuidebookData.resolve(id, "markers/off"));
        if (element.hasAttribute("no-markers") && Boolean.parseBoolean(element.getAttribute("no-markers"))) {
            categoryData.disableMarkers();
        }
        NodeList pages = element.getElementsByTagName("page");
        for (int i = 0; i < pages.getLength(); ++i) {
            Node node = pages.item(i);
            if (!(node instanceof Element)) continue;
            Element pageElement = (Element)node;
            categoryData.addPage(GuidebookData.parsePage(id, pageElement));
        }
        return categoryData;
    }

    public static ResourceLocation resolve(String categoryId, String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return null;
        }
        String clean = relativePath.endsWith(".png") ? relativePath.substring(0, relativePath.length() - 4) : relativePath;
        return Constants.prefix(BASE_PATH.formatted(categoryId, clean));
    }
}

