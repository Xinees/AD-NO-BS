/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.RegistryAccess
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.ExtraCodecs
 *  net.minecraft.util.Mth
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.random.Weight
 *  net.minecraft.util.random.WeightedEntry
 *  net.minecraft.world.Container
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.level.ItemLike
 */
package com.eternal_creations.archies_dino_mod.common.recipe;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.recipe.ADRecipeSerializers;
import com.eternal_creations.archies_dino_mod.common.recipe.ADRecipeTypes;
import com.eternal_creations.archies_dino_mod.common.recipe.PseudoRecipe;
import com.google.gson.JsonObject;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record ArchieTrade(ResourceLocation id, List<SortOrder> sort, Optional<ResourceLocation> requirement, TradeStack input, Optional<TradeStack> addition, List<TradeStack> output, int maxUses, int rewardEXP) implements PseudoRecipe
{
    public static final Codec<ArchieTrade> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)ResourceLocation.f_135803_.fieldOf("type").forGetter(trade -> Constants.prefix("archie_trade")), (App)ExtraCodecs.m_144637_((Codec)SortOrder.CODEC.listOf()).fieldOf("sort").forGetter(ArchieTrade::sort), (App)ResourceLocation.f_135803_.optionalFieldOf("requirement").forGetter(ArchieTrade::requirement), (App)TradeStack.CODEC.fieldOf("input").forGetter(ArchieTrade::input), (App)TradeStack.CODEC.optionalFieldOf("addition").forGetter(ArchieTrade::addition), (App)ExtraCodecs.m_144637_((Codec)TradeStack.CODEC.listOf()).fieldOf("output").forGetter(ArchieTrade::output), (App)Codec.INT.fieldOf("max_uses").forGetter(ArchieTrade::maxUses), (App)ExtraCodecs.f_144628_.fieldOf("reward_exp").forGetter(ArchieTrade::rewardEXP)).apply((Applicative)instance, ArchieTrade::new));

    public ArchieTrade(ResourceLocation id, SortOrder sort, Optional<ResourceLocation> requirement, TradeStack input, Optional<TradeStack> addition, List<TradeStack> output, int maxUses, int rewardEXP) {
        this(id, List.of(sort), requirement, input, addition, output, maxUses, rewardEXP);
    }

    public ArchieTrade(ResourceLocation id, List<SortOrder> sort, Optional<ResourceLocation> requirement, TradeStack input, Optional<TradeStack> addition, TradeStack output, int maxUses, int rewardEXP) {
        this(id, sort, requirement, input, addition, List.of(output), maxUses, rewardEXP);
    }

    public ArchieTrade(ResourceLocation id, SortOrder sort, Optional<ResourceLocation> requirement, TradeStack input, Optional<TradeStack> addition, TradeStack output, int maxUses, int rewardEXP) {
        this(id, List.of(sort), requirement, input, addition, List.of(output), maxUses, rewardEXP);
    }

    public ArchieTrade(ArchieTrade trade, ResourceLocation id) {
        this(id, trade.sort, trade.requirement, trade.input, trade.addition, trade.output, trade.maxUses, trade.rewardEXP);
    }

    public ItemStack m_5874_(Container container, RegistryAccess registryAccess) {
        return new ItemStack((ItemLike)this.output.get(0).m_5456_(), this.output.get((int)0).min);
    }

    public ItemStack m_8043_(RegistryAccess registryAccess) {
        return new ItemStack((ItemLike)this.output.get(0).m_5456_(), this.output.get((int)0).min);
    }

    public ResourceLocation m_6423_() {
        return this.id;
    }

    public RecipeSerializer<?> m_7707_() {
        return ADRecipeSerializers.ARCHIE_TRADE.get();
    }

    public RecipeType<?> m_6671_() {
        return ADRecipeTypes.ARCHIE_TRADE.get();
    }

    public record TradeStack(Item item, int min, int max, Optional<Integer> weight) implements WeightedEntry,
    ItemLike
    {
        public static final TradeStack EMPTY = new TradeStack(Items.f_41852_, 0, 0);
        public static final Codec<TradeStack> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)BuiltInRegistries.f_257033_.m_194605_().fieldOf("item").forGetter(TradeStack::m_5456_), (App)ExtraCodecs.f_144629_.fieldOf("min").forGetter(TradeStack::min), (App)ExtraCodecs.f_144629_.fieldOf("max").forGetter(TradeStack::max), (App)ExtraCodecs.f_144629_.optionalFieldOf("weight").forGetter(TradeStack::weight)).apply((Applicative)instance, TradeStack::new));

        public TradeStack(Item item, int min, int max, int weight) {
            this(item, min, max, Optional.of(weight));
        }

        public TradeStack(Item item, int min, int max) {
            this(item, min, max, Optional.empty());
        }

        public ItemStack resolve(RandomSource random) {
            if (this.min >= this.max) {
                return new ItemStack((ItemLike)this.item, this.min);
            }
            return new ItemStack((ItemLike)this.item, Mth.m_216287_((RandomSource)random, (int)this.min, (int)this.max));
        }

        public Item m_5456_() {
            return this.item;
        }

        public Weight m_142631_() {
            return Weight.m_146282_((int)this.weight.orElse(1));
        }
    }

    public record SortOrder(int ordinal, Optional<Integer> weight) {
        public static final Codec<SortOrder> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.INT.fieldOf("ordinal").forGetter(SortOrder::ordinal), (App)ExtraCodecs.f_144629_.optionalFieldOf("weight").forGetter(SortOrder::weight)).apply((Applicative)instance, SortOrder::new));

        public SortOrder(int ordinal) {
            this(ordinal, Optional.empty());
        }

        public SortOrder(int ordinal, int weight) {
            this(ordinal, Optional.of(weight));
        }
    }

    public record WeightedTrade(int weight, ResourceLocation id, TradeStack input, Optional<TradeStack> addition, TradeStack output, int maxUses, int rewardEXP) implements WeightedEntry
    {
        public Weight m_142631_() {
            return Weight.m_146282_((int)this.weight);
        }
    }

    public static class Serializer
    implements RecipeSerializer<ArchieTrade> {
        public ArchieTrade fromJson(ResourceLocation id, JsonObject object) {
            return new ArchieTrade(CODEC.decode((DynamicOps)JsonOps.INSTANCE, (Object)object).result().map(Pair::getFirst).orElseThrow(), id);
        }

        public ArchieTrade fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            return new ArchieTrade((ArchieTrade)buffer.m_271872_(CODEC), id);
        }

        public void toNetwork(FriendlyByteBuf buffer, ArchieTrade recipe) {
            buffer.m_272073_(CODEC, (Object)recipe);
        }
    }
}

