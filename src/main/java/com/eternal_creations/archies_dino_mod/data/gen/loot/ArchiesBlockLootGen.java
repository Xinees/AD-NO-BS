/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.loot.BlockLootSubProvider
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.storage.loot.LootTable$Builder
 *  net.minecraft.world.level.storage.loot.entries.LootItem
 *  net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer$Builder
 *  net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
 *  net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder
 *  net.minecraft.world.level.storage.loot.functions.LootItemFunction$Builder
 *  net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
 *  net.minecraft.world.level.storage.loot.providers.number.NumberProvider
 *  net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
 *  net.minecraftforge.registries.ForgeRegistries
 */
package com.eternal_creations.archies_dino_mod.data.gen.loot;

import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesBlockLootGen
extends BlockLootSubProvider {
    public ArchiesBlockLootGen() {
        super(Set.of(), FeatureFlags.f_244280_.m_247355_());
    }

    protected void m_245660_() {
        this.dropWhenSilkTouch(ADBlocks.LUSHSAUR_EGG);
        this.dropWhenSilkTouch(ADBlocks.CHORUSOATL_EGG);
        this.dropWhenSilkTouch(ADBlocks.SCULKRAPTOR_EGG);
        this.dropWhenSilkTouch(ADBlocks.OBSIDICERATOPS_EGG);
        this.dropWhenSilkTouch(ADBlocks.THERIMOSS_EGG);
        this.dropSelf(ADBlocks.LUSH_TOTEM);
        this.dropSelf(ADBlocks.BURNING_TOTEM);
        this.dropSelf(ADBlocks.TROPIC_TOTEM);
        this.dropSelf(ADBlocks.SOARING_TOTEM);
        this.dropSelf(ADBlocks.TIDAL_TOTEM);
        this.dropSelf(ADBlocks.ECHO_TOTEM);
        this.dropSelf(ADBlocks.FEARFUL_TOTEM);
        this.dropSelf(ADBlocks.RAW_AMBER_BLOCK);
        this.dropSelf(ADBlocks.POLISHED_AMBER);
        this.dropSelf(ADBlocks.POLISHED_AMBER_SLAB);
        this.dropSelf(ADBlocks.POLISHED_AMBER_STAIRS);
        this.dropSelf(ADBlocks.POLISHED_AMBER_WALL);
        this.dropSelf(ADBlocks.AMBER_BRICKS);
        this.dropSelf(ADBlocks.AMBER_BRICK_SLAB);
        this.dropSelf(ADBlocks.AMBER_BRICK_STAIRS);
        this.dropSelf(ADBlocks.AMBER_BRICK_WALL);
        this.dropSelf(ADBlocks.CRACKED_AMBER_BRICKS);
        this.dropSelf(ADBlocks.CARVED_AMBER);
        this.dropSelf(ADBlocks.AMBER_CHAIN);
        this.dropSelf(ADBlocks.AMBER_LAMP);
        this.add(ADBlocks.AMBER_ORE, this.createAmberOreDrops(ADBlocks.AMBER_ORE, ADItems.AMBER));
    }

    protected <T extends Block, I extends Item> LootTable.Builder createAmberOreDrops(ResourceSupplier<T> block, ResourceSupplier<I> item) {
        return ArchiesBlockLootGen.m_247502_((Block)((Block)block.get()), (LootPoolEntryContainer.Builder)((LootPoolEntryContainer.Builder)this.m_246108_((ItemLike)block.get(), (FunctionUserBuilder)LootItem.m_79579_((ItemLike)((ItemLike)item.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)2.0f, (float)3.0f))).m_79078_((LootItemFunction.Builder)ApplyBonusCount.m_79915_((Enchantment)Enchantments.f_44987_)))));
    }

    protected <T extends Block> void dropSelf(ResourceSupplier<T> block) {
        this.m_245724_((Block)block.get());
    }

    protected <T extends Block> void add(ResourceSupplier<T> block, LootTable.Builder builder) {
        this.f_244441_.put(((Block)block.get()).m_60589_(), builder);
    }

    protected <T extends Block> void dropWhenSilkTouch(ResourceSupplier<T> block) {
        this.m_245854_((Block)block.get(), (Block)block.get());
    }

    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).m_135827_().equals("ec_ad")).collect(Collectors.toList());
    }
}

