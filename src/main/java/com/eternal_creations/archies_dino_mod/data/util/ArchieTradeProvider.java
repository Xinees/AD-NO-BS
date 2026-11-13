/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.gson.JsonElement
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.CachedOutput
 *  net.minecraft.data.DataProvider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 *  org.slf4j.Logger
 */
package com.eternal_creations.archies_dino_mod.data.util;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.items.util.ADArmorItem;
import com.eternal_creations.archies_dino_mod.common.recipe.ArchieTrade;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ArchieTradeProvider
implements DataProvider {
    private final PackOutput output;
    protected final String modId;
    protected final List<ArchieTrade> builders = new ArrayList<ArchieTrade>();

    public ArchieTradeProvider(PackOutput output, String modId) {
        this.output = output;
        this.modId = modId;
    }

    public abstract void run();

    public final CompletableFuture<?> m_213708_(CachedOutput cache) {
        this.builders.clear();
        this.run();
        ImmutableList.Builder futuresBuilder = new ImmutableList.Builder();
        this.builders.forEach(trade -> {
            JsonElement obj = this.serializeToJson((ArchieTrade)trade);
            Path path = this.createPath(trade.m_6423_());
            futuresBuilder.add((Object)DataProvider.m_253162_((CachedOutput)cache, (JsonElement)obj, (Path)path));
        });
        return CompletableFuture.allOf((CompletableFuture[])futuresBuilder.build().toArray(CompletableFuture[]::new));
    }

    protected void addTradeOffer(String name, int index, Item cost, int minCost, int maxCost, Item reward, int minReward, int maxReward, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), new ArchieTrade.SortOrder(index), Optional.empty(), new ArchieTrade.TradeStack(cost, minCost, maxCost), Optional.empty(), new ArchieTrade.TradeStack(reward, minReward, maxReward), maxUses, rewardEXP));
    }

    protected void addTradeOffer(String name, List<ArchieTrade.SortOrder> sort, Item cost, int minCost, int maxCost, Item reward, int minReward, int maxReward, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), sort, Optional.empty(), new ArchieTrade.TradeStack(cost, minCost, maxCost), Optional.empty(), new ArchieTrade.TradeStack(reward, minReward, maxReward), maxUses, rewardEXP));
    }

    protected void addTradeOffer(String name, int index, Item cost, int costCount, Item reward, int rewardCount, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), new ArchieTrade.SortOrder(index), Optional.empty(), new ArchieTrade.TradeStack(cost, costCount, costCount), Optional.empty(), new ArchieTrade.TradeStack(reward, rewardCount, rewardCount), maxUses, rewardEXP));
    }

    protected void addTradeOffer(String name, List<ArchieTrade.SortOrder> sort, Item cost, int costCount, Item reward, int rewardCount, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), sort, Optional.empty(), new ArchieTrade.TradeStack(cost, costCount, costCount), Optional.empty(), new ArchieTrade.TradeStack(reward, rewardCount, rewardCount), maxUses, rewardEXP));
    }

    protected void addTradeOffer(String name, int index, String requirement, Item cost, int minCost, int maxCost, Item reward, int minReward, int maxReward, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), new ArchieTrade.SortOrder(index), Optional.of(Constants.prefix(requirement)), new ArchieTrade.TradeStack(cost, minCost, maxCost), Optional.empty(), new ArchieTrade.TradeStack(reward, minReward, maxReward), maxUses, rewardEXP));
    }

    protected void addTradeOffer(String name, int index, String requirement, Item cost, int costCount, Item reward, int rewardCount, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), new ArchieTrade.SortOrder(index), Optional.of(Constants.prefix(requirement)), new ArchieTrade.TradeStack(cost, costCount, costCount), Optional.empty(), new ArchieTrade.TradeStack(reward, rewardCount, rewardCount), maxUses, rewardEXP));
    }

    protected void addTradeOffer(String name, List<ArchieTrade.SortOrder> sort, String requirement, Item cost, int minCost, int maxCost, Item reward, int minReward, int maxReward, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), sort, Optional.of(Constants.prefix(requirement)), new ArchieTrade.TradeStack(cost, minCost, maxCost), Optional.empty(), new ArchieTrade.TradeStack(reward, minReward, maxReward), maxUses, rewardEXP));
    }

    protected void addTradeOffer(String name, List<ArchieTrade.SortOrder> sort, String requirement, Item cost, int costCount, Item reward, int rewardCount, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(ResourceLocation.fromNamespaceAndPath((String)this.modId, (String)name), sort, Optional.of(Constants.prefix(requirement)), new ArchieTrade.TradeStack(cost, costCount, costCount), Optional.empty(), new ArchieTrade.TradeStack(reward, rewardCount, rewardCount), maxUses, rewardEXP));
    }

    public <T extends ADArmorItem> void addRepairOffer(List<ArchieTrade.SortOrder> sort, String requirement, ResourceSupplier<T> armour, int minCost, int maxCost, int maxUses, int rewardEXP) {
        this.builders.add(new ArchieTrade(armour.id().m_246208_("repair_"), sort, Optional.of(Constants.prefix(requirement)), new ArchieTrade.TradeStack((Item)armour.get(), 1, 1), Optional.of(new ArchieTrade.TradeStack(Items.f_42616_, minCost, maxCost)), new ArchieTrade.TradeStack((Item)armour.get(), 1, 1), maxUses, rewardEXP));
    }

    private Path createPath(ResourceLocation name) {
        return this.output.m_245114_().resolve("data/" + name.m_135827_() + "/recipes/archie_trade/" + name.m_135815_() + ".json");
    }

    private JsonElement serializeToJson(ArchieTrade trade) {
        return (JsonElement)ArchieTrade.CODEC.encodeStart((DynamicOps)JsonOps.INSTANCE, (Object)trade).getOrThrow(true, arg_0 -> ((Logger)Constants.LOG).error(arg_0));
    }

    public String m_6055_() {
        return this.modId + " Crumble Horn Transformations";
    }
}

