/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.PackOutput
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Items
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.recipe.ArchieTrade;
import com.eternal_creations.archies_dino_mod.data.util.ArchieTradeProvider;
import java.util.List;
import java.util.Optional;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ArchieTradeGen
extends ArchieTradeProvider {
    public ArchieTradeGen(PackOutput output) {
        super(output, "ec_ad");
    }

    @Override
    public void run() {
        int ordinal = 0;
        this.addTradeOffer("guidebook", ++ordinal, Items.f_42517_, 1, (Item)ADItems.GUIDEBOOK.get(), 1, 1, 0);
        this.addTradeOffer("sell_amber", ++ordinal, ADItems.AMBER.get(), 1, 1, Items.f_42616_, 3, 4, 16, 0);
        this.addTradeOffer("buy_spyglass", ++ordinal, Items.f_42616_, 4, 6, Items.f_151059_, 1, 1, 3, 5);
        this.addTradeOffer("buy_compass", ++ordinal, Items.f_42616_, 4, 6, Items.f_42522_, 1, 1, 3, 5);
        this.addTradeOffer("buy_map", ++ordinal, Items.f_42616_, 2, 2, Items.f_42676_, 1, 1, 3, 5);
        this.builders.add(new ArchieTrade(Constants.prefix("craft_sculk_arrow"), new ArchieTrade.SortOrder(++ordinal), Optional.of(Constants.prefix("spot_sculksaur")), new ArchieTrade.TradeStack(Items.f_42412_, 1, 1), Optional.of(new ArchieTrade.TradeStack(ADItems.SCULK_TOXIN.get(), 1, 1)), new ArchieTrade.TradeStack((Item)ADItems.OOZE_TIPPED_ARROW.get(), 1, 1), 3, 5));
        List<ArchieTrade.SortOrder> lootToVanillaLoot = List.of(new ArchieTrade.SortOrder(++ordinal, 1), new ArchieTrade.SortOrder(++ordinal, 1));
        this.addTradeOffer("core_to_sacs", lootToVanillaLoot, ADItems.LUSH_CORE.get(), 1, Items.f_151056_, 3, 12, 5);
        this.addTradeOffer("claw_to_cocoa", lootToVanillaLoot, ADItems.MOSSY_CLAW.get(), 1, Items.f_42533_, 3, 12, 5);
        this.builders.add(new ArchieTrade(Constants.prefix("rib_to_coral"), lootToVanillaLoot, Optional.empty(), new ArchieTrade.TradeStack(ADItems.CORAL_RIB.get(), 1, 1), Optional.empty(), List.of(new ArchieTrade.TradeStack(Items.f_42285_, 4, 4, 1), new ArchieTrade.TradeStack(Items.f_42286_, 4, 4, 1), new ArchieTrade.TradeStack(Items.f_42287_, 4, 4, 1), new ArchieTrade.TradeStack(Items.f_42288_, 4, 4, 1), new ArchieTrade.TradeStack(Items.f_42289_, 4, 4, 1)), 12, 5));
        this.addTradeOffer("talon_to_shard", lootToVanillaLoot, (Item)ADItems.RAPTOR_TALON.get(), 1, 1, Items.f_220224_, 1, 1, 12, 5);
        this.addTradeOffer("horn_to_obsidian", lootToVanillaLoot, ADItems.OBSIDIAN_HORN.get(), 1, Items.f_41999_, 4, 12, 5);
        this.addTradeOffer("chorus_fossil_to_chorus_fruit", lootToVanillaLoot, ADItems.CHORUS_FOSSIL.get(), 1, Items.f_42730_, 3, 12, 5);
        List<ArchieTrade.SortOrder> sellLoot = List.of(new ArchieTrade.SortOrder(++ordinal, 1), new ArchieTrade.SortOrder(++ordinal, 1));
        this.addTradeOffer("sell_core", sellLoot, ADItems.LUSH_CORE.get(), 1, Items.f_42616_, 10, 12, 5);
        this.addTradeOffer("sell_claw", sellLoot, ADItems.MOSSY_CLAW.get(), 1, Items.f_42616_, 8, 12, 5);
        this.addTradeOffer("sell_rib", sellLoot, ADItems.CORAL_RIB.get(), 1, Items.f_42616_, 12, 12, 5);
        this.addTradeOffer("sell_horn", sellLoot, ADItems.OBSIDIAN_HORN.get(), 1, Items.f_42616_, 12, 12, 5);
        this.addTradeOffer("sell_talon", sellLoot, (Item)ADItems.RAPTOR_TALON.get(), 1, Items.f_42616_, 6, 12, 5);
        this.addTradeOffer("sell_chorus", sellLoot, ADItems.CHORUS_FOSSIL.get(), 1, Items.f_42616_, 12, 12, 5);
        List<ArchieTrade.SortOrder> buyLoot = List.of(new ArchieTrade.SortOrder(++ordinal, 1), new ArchieTrade.SortOrder(++ordinal, 1));
        this.addTradeOffer("buy_core", buyLoot, "kill_lushsaur", Items.f_42616_, 16, 20, ADItems.LUSH_CORE.get(), 1, 1, 12, 5);
        this.addTradeOffer("buy_claw", buyLoot, "kill_therimoss", Items.f_42616_, 12, 16, ADItems.MOSSY_CLAW.get(), 1, 1, 12, 5);
        this.addTradeOffer("buy_rib", buyLoot, "kill_coralosaur", Items.f_42616_, 16, 20, ADItems.CORAL_RIB.get(), 1, 1, 12, 5);
        this.addTradeOffer("buy_horn", buyLoot, "kill_obsidiceratops", Items.f_42616_, 16, 20, ADItems.OBSIDIAN_HORN.get(), 1, 1, 12, 5);
        this.addTradeOffer("buy_talon", buyLoot, "kill_sculkraptor", Items.f_42616_, 12, 16, (Item)ADItems.RAPTOR_TALON.get(), 1, 1, 12, 5);
        this.addTradeOffer("buy_chorus", buyLoot, "kill_chorusoatl", Items.f_42616_, 18, 24, ADItems.CHORUS_FOSSIL.get(), 1, 1, 12, 5);
        List<ArchieTrade.SortOrder> buyTameItem = List.of(new ArchieTrade.SortOrder(++ordinal, 1));
        this.addTradeOffer("buy_glow_berries", buyTameItem, "spot_lushsaur", Items.f_42616_, 4, Items.f_151079_, 3, 12, 5);
        this.addTradeOffer("buy_melon", buyTameItem, "spot_therimoss", Items.f_42616_, 4, Items.f_42028_, 4, 12, 5);
        this.addTradeOffer("buy_coral_stew", buyTameItem, "spot_coralosaur", Items.f_42616_, 4, ADItems.CORAL_STEW.get(), 1, 12, 5);
        this.addTradeOffer("buy_magma", buyTameItem, "spot_obsidiceratops", Items.f_42616_, 4, Items.f_42258_, 3, 12, 5);
        this.addTradeOffer("chorus_fruit", buyTameItem, "spot_chorusoatl", Items.f_42616_, 4, Items.f_42730_, 1, 12, 5);
        List<ArchieTrade.SortOrder> eggSellOrders = List.of(new ArchieTrade.SortOrder(++ordinal, 1), new ArchieTrade.SortOrder(++ordinal, 1));
        this.addTradeOffer("egg_to_core", eggSellOrders, "kill_lushsaur", (Item)ADItems.LUSHSAUR_EGG.get(), 1, 1, ADItems.LUSH_CORE.get(), 2, 2, -1, 5);
        this.addTradeOffer("egg_to_fossil", eggSellOrders, "kill_chorusoatl", (Item)ADItems.CHORUSOATL_EGG.get(), 1, 1, ADItems.CHORUS_FOSSIL.get(), 2, 4, -1, 5);
        this.addTradeOffer("egg_to_talon", eggSellOrders, "kill_sculkraptor", (Item)ADItems.SCULKRAPTOR_EGG.get(), 1, 1, (Item)ADItems.RAPTOR_TALON.get(), 2, 4, -1, 5);
        this.addTradeOffer("egg_to_horn", eggSellOrders, "kill_obsidiceratops", (Item)ADItems.OBSIDICERATOPS_EGG.get(), 1, 1, ADItems.OBSIDIAN_HORN.get(), 2, 4, -1, 5);
        this.addTradeOffer("egg_to_claw", eggSellOrders, "kill_therimoss", (Item)ADItems.THERIMOSS_EGG.get(), 1, 1, ADItems.MOSSY_CLAW.get(), 2, 4, -1, 5);
        List<ArchieTrade.SortOrder> eggBuyOrders = List.of(new ArchieTrade.SortOrder(++ordinal, 1), new ArchieTrade.SortOrder(++ordinal, 1));
        this.addTradeOffer("buy_lushsaur_egg", eggBuyOrders, "tame_lushsaur", Items.f_42616_, 32, (Item)ADItems.LUSHSAUR_EGG.get(), 1, -1, 5);
        this.addTradeOffer("buy_therimoss_egg", eggBuyOrders, "tame_therimoss", Items.f_42616_, 32, (Item)ADItems.THERIMOSS_EGG.get(), 1, -1, 5);
        this.addTradeOffer("buy_obsidiceratops_egg", eggBuyOrders, "tame_obsidiceratops", Items.f_42616_, 32, (Item)ADItems.OBSIDICERATOPS_EGG.get(), 1, -1, 5);
        this.addTradeOffer("buy_chorusoatl_egg", eggBuyOrders, "tame_chorusoatl", Items.f_42616_, 32, (Item)ADItems.CHORUSOATL_EGG.get(), 1, -1, 5);
        this.addTradeOffer("buy_sculkraptor_egg", eggBuyOrders, "tame_sculkraptor", Items.f_42616_, 32, (Item)ADItems.SCULKRAPTOR_EGG.get(), 1, -1, 5);
    }
}

