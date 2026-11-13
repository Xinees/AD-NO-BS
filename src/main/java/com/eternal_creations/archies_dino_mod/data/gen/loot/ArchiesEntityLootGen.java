/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.data.loot.EntityLootSubProvider
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.flag.FeatureFlags
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.storage.loot.LootPool
 *  net.minecraft.world.level.storage.loot.LootTable
 *  net.minecraft.world.level.storage.loot.entries.EmptyLootItem
 *  net.minecraft.world.level.storage.loot.entries.LootItem
 *  net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer$Builder
 *  net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction
 *  net.minecraft.world.level.storage.loot.functions.LootItemFunction$Builder
 *  net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction
 *  net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction$Builder
 *  net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
 *  net.minecraft.world.level.storage.loot.providers.number.ConstantValue
 *  net.minecraft.world.level.storage.loot.providers.number.NumberProvider
 *  net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
 *  net.minecraftforge.registries.ForgeRegistries
 */
package com.eternal_creations.archies_dino_mod.data.gen.loot;

import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesEntityLootGen
extends EntityLootSubProvider {
    public ArchiesEntityLootGen() {
        super(FeatureFlags.f_244280_.m_247355_());
    }

    public void m_246942_() {
        this.m_245309_(ADEntityTypes.ARCHIE.get(), LootTable.m_79147_());
        this.m_245309_(ADEntityTypes.LUSHSAUR.get(), LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.LUSH_CORE.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)1.0f, (float)3.0f))).m_79078_((LootItemFunction.Builder)LootingEnchantFunction.m_165229_((NumberProvider)UniformGenerator.m_165780_((float)0.0f, (float)1.0f))))));
        this.m_245309_(ADEntityTypes.CHORUSOATL.get(), LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.CHORUS_FOSSIL.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)1.0f, (float)3.0f))).m_79078_((LootItemFunction.Builder)LootingEnchantFunction.m_165229_((NumberProvider)UniformGenerator.m_165780_((float)0.0f, (float)1.0f))))));
        this.m_245309_(ADEntityTypes.OBSIDICERATOPS.get(), LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.OBSIDIAN_HORN.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)1.0f, (float)3.0f))).m_79078_((LootItemFunction.Builder)LootingEnchantFunction.m_165229_((NumberProvider)UniformGenerator.m_165780_((float)0.0f, (float)1.0f))))));
        this.m_245309_(ADEntityTypes.SCULKRAPTOR.get(), LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.RAPTOR_TALON.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)1.0f, (float)3.0f))).m_79078_((LootItemFunction.Builder)LootingEnchantFunction.m_165229_((NumberProvider)UniformGenerator.m_165780_((float)0.0f, (float)1.0f))))));
        this.m_245309_(ADEntityTypes.THERIMOSS.get(), LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.MOSSY_CLAW.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)1.0f, (float)3.0f))).m_79078_((LootItemFunction.Builder)LootingEnchantFunction.m_165229_((NumberProvider)UniformGenerator.m_165780_((float)0.0f, (float)1.0f))))));
        this.m_245309_(ADEntityTypes.CORALOSAUR.get(), LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.CORAL_RIB.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)1.0f, (float)3.0f))).m_79078_((LootItemFunction.Builder)LootingEnchantFunction.m_165229_((NumberProvider)UniformGenerator.m_165780_((float)0.0f, (float)1.0f))))));
        this.m_245309_(ADEntityTypes.SCULKSAUR.get(), LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.SCULK_TOXIN.get())).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)5.0f, (float)6.0f))))).m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_220224_).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)2.0f, (float)3.0f))))).m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)2.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42419_).m_79707_(2).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)1.0f, (float)2.0f)))).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42415_).m_79707_(5).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)3.0f, (float)4.0f)))).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42417_).m_79707_(8).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)5.0f, (float)9.0f))))).m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42517_).m_79707_(4).m_79078_((LootItemFunction.Builder)EnchantWithLevelsFunction.m_165196_((NumberProvider)UniformGenerator.m_165780_((float)30.0f, (float)39.0f)).m_80499_())).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42612_).m_79707_(4).m_79078_((LootItemFunction.Builder)SetItemCountFunction.m_165412_((NumberProvider)UniformGenerator.m_165780_((float)20.0f, (float)32.0f)))).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)((ItemLike)ADItems.SCULK_BOW.get())).m_79707_(1).m_79078_((LootItemFunction.Builder)new SetEnchantmentsFunction.Builder().m_165374_(Enchantments.f_44952_, (NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_165374_(Enchantments.f_44989_, (NumberProvider)ConstantValue.m_165692_((float)4.0f)).m_165374_(Enchantments.f_44961_, (NumberProvider)ConstantValue.m_165692_((float)3.0f)).m_165374_(Enchantments.f_44988_, (NumberProvider)ConstantValue.m_165692_((float)5.0f)))).m_79076_((LootPoolEntryContainer.Builder)EmptyLootItem.m_79533_().m_79707_(30))).m_79161_(LootPool.m_79043_().m_165133_((NumberProvider)ConstantValue.m_165692_((float)1.0f)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_220218_)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42584_)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42450_)).m_79076_((LootPoolEntryContainer.Builder)LootItem.m_79579_((ItemLike)Items.f_42656_))));
    }

    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return ForgeRegistries.ENTITY_TYPES.getValues().stream().filter(block -> Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(block)).m_135827_().equals("ec_ad"));
    }
}

