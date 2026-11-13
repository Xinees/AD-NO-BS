/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.ChatFormatting
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.ComputeFovModifierEvent
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.eternal_creations.archies_dino_mod.client;

import com.eternal_creations.archies_dino_mod.client.ADKeyMappings;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import com.eternal_creations.archies_dino_mod.common.items.util.ADBowItem;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod.EventBusSubscriber(modid="ec_ad", bus=Mod.EventBusSubscriber.Bus.FORGE, value={Dist.CLIENT})
public class ADClientForgeBus {
    @SubscribeEvent
    public static void updateBowFOV(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        if (player.m_6117_() && player.m_21211_().m_41720_() instanceof ADBowItem) {
            float scale = (float)player.m_21252_() / 20.0f;
            scale = scale > 1.0f ? 1.0f : scale * scale;
            event.setNewFovModifier((float)Mth.m_14139_((double)((Double)Minecraft.m_91087_().f_91066_.m_231925_().m_231551_()), (double)1.0, (double)(event.getFovModifier() * (1.0f - scale * 0.15f))));
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.m_91087_();
            if (mc.f_91074_ != null && mc.f_91073_ != null) {
                ADKeyMappings.KEY_MAPPINGS.forEach((keyMapping, consumer) -> {
                    while (keyMapping.m_90859_()) {
                        consumer.accept(mc.f_91074_, mc.f_91073_);
                    }
                });
            }
        }
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        if (event.getItemStack().m_204117_(ADTags.Items.TWO_HANDED)) {
            event.getToolTip().add(Component.m_237115_((String)ADStrings.Description.TWO_HANDED).m_130940_(ChatFormatting.GRAY));
        }
    }
}

