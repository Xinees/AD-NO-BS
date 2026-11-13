/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.Advancement
 *  net.minecraft.advancements.AdvancementProgress
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientAdvancements
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.ServerAdvancementManager
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.random.WeightedRandomList
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.trading.MerchantOffer
 *  net.minecraft.world.item.trading.MerchantOffers
 *  org.jetbrains.annotations.Contract
 */
package com.eternal_creations.archies_dino_mod.common.entity.mob.archie;

import com.eternal_creations.archies_dino_mod.common.entity.mob.archie.ArchieEntity;
import com.eternal_creations.archies_dino_mod.common.recipe.ADRecipeTypes;
import com.eternal_creations.archies_dino_mod.common.recipe.ArchieTrade;
import com.eternal_creations.archies_dino_mod.mixin.client.ClientAdvancementsAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import org.jetbrains.annotations.Contract;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchieTrades {
    private static final List<ResourceLocation> ALREADY_ADDED = new ArrayList<ResourceLocation>();

    public static void updateTrades(ArchieEntity archie, @Nullable Player player) {
        ALREADY_ADDED.clear();
        MerchantOffers merchantOffers = archie.m_6616_();
        RandomSource random = RandomSource.m_216335_((long)archie.m_20148_().getMostSignificantBits());
        TreeMap<Integer, List> map = new TreeMap<Integer, List>();
        for (ArchieTrade trade : archie.m_9236_().m_7465_().m_44013_(ADRecipeTypes.ARCHIE_TRADE.get())) {
            if (trade.requirement().isPresent() && !ArchieTrades.hasAdvancement(player, trade.requirement().get())) continue;
            for (ArchieTrade.SortOrder sortOrder : trade.sort()) {
                List list = map.getOrDefault(sortOrder.ordinal(), new ArrayList());
                List<ArchieTrade.TradeStack> stacks = trade.output();
                ArchieTrade.TradeStack output = stacks.size() == 1 ? stacks.get(0) : WeightedRandomList.m_146328_(stacks).m_216829_(random.m_213769_()).orElse(stacks.get(0));
                list.add(new ArchieTrade.WeightedTrade(sortOrder.weight().orElse(-1), trade.id(), trade.input(), trade.addition(), output, trade.maxUses(), trade.rewardEXP()));
                map.put(sortOrder.ordinal(), list);
            }
        }
        map.forEach((integer, archieTrades) -> {
            ArrayList<ArchieTrade.WeightedTrade> trades = new ArrayList<ArchieTrade.WeightedTrade>();
            for (ArchieTrade.WeightedTrade trade : archieTrades) {
                if (ALREADY_ADDED.contains(trade.id())) continue;
                if (trade.weight() <= 0) {
                    ArchieTrades.addTradeOffer(merchantOffers, trade, random);
                    continue;
                }
                trades.add(trade);
            }
            if (!trades.isEmpty()) {
                if (trades.size() == 1) {
                    ArchieTrades.addTradeOffer(merchantOffers, (ArchieTrade.WeightedTrade)trades.get(0), random);
                }
                ArchieTrades.addTradeOffer(merchantOffers, WeightedRandomList.m_146328_(trades).m_216829_(random.m_213769_()).orElse((ArchieTrade.WeightedTrade)trades.get(0)), random);
            }
        });
    }

    @Contract(value="null, _ -> false")
    public static boolean hasAdvancement(@Nullable Player player, ResourceLocation name) {
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            ServerAdvancementManager manager = serverPlayer.m_284548_().m_7654_().m_129889_();
            Advancement advancement = manager.m_136041_(name);
            return advancement != null && serverPlayer.m_8960_().m_135996_(advancement).m_8193_();
        }
        if (player != null && player.m_9236_().f_46443_) {
            ClientPacketListener connection = Minecraft.m_91087_().m_91403_();
            if (connection == null) {
                return false;
            }
            ClientAdvancements advancements = connection.m_105145_();
            Advancement advancement = advancements.m_104396_().m_139337_(name);
            if (advancement == null) {
                return false;
            }
            AdvancementProgress progress = ((ClientAdvancementsAccessor)advancements).archiesDinoMod$getProgress().get(advancement);
            return progress != null && progress.m_8193_();
        }
        return false;
    }

    public static void addTradeOffer(MerchantOffers merchantOffers, ArchieTrade.WeightedTrade trade, RandomSource random) {
        if (trade.addition().isEmpty()) {
            merchantOffers.add((Object)new MerchantOffer(trade.input().resolve(random), trade.output().resolve(random), trade.maxUses() <= 0 ? Integer.MAX_VALUE : trade.maxUses(), trade.rewardEXP(), 1.0f));
            ALREADY_ADDED.add(trade.id());
        } else {
            ItemStack output;
            ItemStack input = trade.input().resolve(random);
            if (input.m_150930_((output = trade.output().resolve(random)).m_41720_()) && input.m_41763_()) {
                input.m_41721_((int)((double)input.m_41776_() * 0.75));
            }
            merchantOffers.add((Object)new MerchantOffer(input, trade.addition().get().resolve(random), output, Integer.MAX_VALUE, 5, 1.0f));
            ALREADY_ADDED.add(trade.id());
        }
    }
}

