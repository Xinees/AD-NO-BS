/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMultimap
 *  com.google.common.collect.ImmutableMultimap$Builder
 *  com.google.common.collect.Multimap
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.Util
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.model.geom.EntityModelSet
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.ExtraCodecs
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ArmorItem
 *  net.minecraft.world.item.ArmorItem$Type
 *  net.minecraft.world.item.ArmorMaterial
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.items.util;

import com.eternal_creations.archies_dino_mod.client.model.armor.ADArmorModel;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.networking.serverbound.UseArmorAbilityMessage;
import com.eternal_creations.archies_dino_mod.platform.Services;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ADArmorItem
extends ArmorItem {
    private static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = (EnumMap)Util.m_137469_(new EnumMap(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        map.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        map.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        map.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });
    private static final EquipmentSlot[] SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

    public ADArmorItem(ADArmorMaterial material, ArmorItem.Type type, Item.Properties properties) {
        super((ArmorMaterial)material, type, properties.m_41487_(1));
    }

    public ADArmorMaterial getMaterial() {
        return (ADArmorMaterial)super.m_40401_();
    }

    public int m_40404_() {
        return this.getMaterial().m_7366_(this.f_265916_);
    }

    public float m_40405_() {
        return this.getMaterial().m_6651_();
    }

    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
        if (equipmentSlot != this.f_265916_.m_266308_()) {
            return ImmutableMultimap.of();
        }
        ImmutableMultimap.Builder builder = ImmutableMultimap.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(this.f_265916_);
        builder.put((Object)Attributes.f_22284_, (Object)new AttributeModifier(uuid, "Armor modifier", (double)this.m_40404_(), AttributeModifier.Operation.ADDITION));
        builder.put((Object)Attributes.f_22285_, (Object)new AttributeModifier(uuid, "Armor toughness", (double)this.m_40405_(), AttributeModifier.Operation.ADDITION));
        float knockbackRes = this.getMaterial().stats.get().knockbackResistance();
        if (knockbackRes > 0.0f) {
            builder.put((Object)Attributes.f_22278_, (Object)new AttributeModifier(uuid, "Armor knockback resistance", (double)this.getMaterial().stats.get().knockbackResistance(), AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    public void m_6883_(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        LivingEntity living;
        if (this.f_265916_ == ArmorItem.Type.HELMET && entity instanceof LivingEntity && ItemStack.m_41728_((ItemStack)stack, (ItemStack)(living = (LivingEntity)entity).m_6844_(EquipmentSlot.HEAD)) && ADArmorItem.checkFullSet(living, ((Object)((Object)this)).getClass())) {
            this.tickWholeSet(level, living);
        }
        super.m_6883_(stack, level, entity, slotId, isSelected);
    }

    public void tickWholeSet(Level level, LivingEntity living) {
    }

    public static Map<EquipmentSlot, ItemStack> getArmorSet(LivingEntity living) {
        if (!(living instanceof Player)) {
            return new HashMap<EquipmentSlot, ItemStack>();
        }
        ItemStack helm = living.m_6844_(EquipmentSlot.HEAD);
        Item item = helm.m_41720_();
        if (item instanceof ADArmorItem) {
            ADArmorItem helmet = (ADArmorItem)item;
            HashMap<EquipmentSlot, ItemStack> equipment = new HashMap<EquipmentSlot, ItemStack>();
            equipment.put(EquipmentSlot.HEAD, helm);
            for (EquipmentSlot slot : SLOTS) {
                ADArmorItem armorItem;
                ItemStack stack;
                Item item2;
                if (slot == EquipmentSlot.HEAD || !((item2 = (stack = living.m_6844_(slot)).m_41720_()) instanceof ADArmorItem) || !(armorItem = (ADArmorItem)item2).getMaterial().equals(helmet.getMaterial())) continue;
                equipment.put(slot, stack);
            }
            return equipment;
        }
        return new HashMap<EquipmentSlot, ItemStack>();
    }

    public static boolean checkFullSet(LivingEntity living, Class<? extends ADArmorItem> clazz) {
        for (EquipmentSlot slot : SLOTS) {
            ArmorItem armorItem;
            Item item = living.m_6844_(slot).m_41720_();
            if (item instanceof ArmorItem && clazz.isInstance(armorItem = (ArmorItem)item)) continue;
            return false;
        }
        return true;
    }

    public boolean hasAbility() {
        return false;
    }

    public void executeInputPress(Player player) {
        if (this.hasAbility()) {
            this.activateClientSetAbility(player);
        } else {
            player.m_5661_((Component)Component.m_237115_((String)ADStrings.Messages.ARMOR_NO_ABILITY), true);
        }
    }

    public void activateClientSetAbility(Player player) {
        float partialTick = Minecraft.m_91087_().m_91297_();
        Services.NETWORK.sendToServer(new UseArmorAbilityMessage(player.m_20318_(partialTick), player.m_20252_(partialTick)));
    }

    public boolean activateServerSetAbility(ServerPlayer player, Vec3 pos, Vec3 viewVector) {
        return false;
    }

    public int getSetAbilityCooldownDuration(ServerPlayer player) {
        return 0;
    }

    public abstract ResourceLocation getArmorTexture();

    public abstract ModelLayerLocation getLayerLocation();

    public ADArmorModel initArmorModel() {
        EntityModelSet models = Minecraft.m_91087_().m_167973_();
        ModelPart root = models.m_171103_(this.getLayerLocation());
        return new ADArmorModel(root);
    }

    public static class ADArmorMaterial
    implements ArmorMaterial {
        private final Supplier<ArmorStats> stats;
        private final SoundEvent event;
        private final String name;
        private final Ingredient repairIngredient;

        public ADArmorMaterial(Supplier<ArmorStats> stats, SoundEvent event, String name, Ingredient repairIngredient) {
            this.stats = stats;
            this.event = event;
            this.name = name;
            this.repairIngredient = repairIngredient;
        }

        public int m_266425_(ArmorItem.Type type) {
            return switch (type) {
                default -> throw new IncompatibleClassChangeError();
                case ArmorItem.Type.HELMET -> this.stats.get().helmet().durability();
                case ArmorItem.Type.CHESTPLATE -> this.stats.get().chestplate().durability();
                case ArmorItem.Type.LEGGINGS -> this.stats.get().leggings().durability();
                case ArmorItem.Type.BOOTS -> this.stats.get().boots().durability();
            };
        }

        public int m_7366_(ArmorItem.Type type) {
            return switch (type) {
                default -> throw new IncompatibleClassChangeError();
                case ArmorItem.Type.HELMET -> this.stats.get().helmet().defense();
                case ArmorItem.Type.CHESTPLATE -> this.stats.get().chestplate().defense();
                case ArmorItem.Type.LEGGINGS -> this.stats.get().leggings().defense();
                case ArmorItem.Type.BOOTS -> this.stats.get().boots().defense();
            };
        }

        public int m_6646_() {
            return this.stats.get().enchantmentValue();
        }

        public SoundEvent m_7344_() {
            return this.event;
        }

        public Ingredient m_6230_() {
            return this.repairIngredient;
        }

        public String m_6082_() {
            return this.name;
        }

        public float m_6651_() {
            return this.stats.get().toughness();
        }

        public float m_6649_() {
            return this.stats.get().knockbackResistance();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object obj) {
            if (!(obj instanceof ADArmorMaterial)) return false;
            ADArmorMaterial material = (ADArmorMaterial)obj;
            if (!material.name.equals(this.name)) return false;
            return true;
        }
    }

    public record ArmorStats(SlotStats helmet, SlotStats chestplate, SlotStats leggings, SlotStats boots, int enchantmentValue, float toughness, float knockbackResistance) {
        public static final Codec<ArmorStats> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)SlotStats.CODEC.fieldOf("helmet").forGetter(ArmorStats::helmet), (App)SlotStats.CODEC.fieldOf("chestplate").forGetter(ArmorStats::chestplate), (App)SlotStats.CODEC.fieldOf("leggings").forGetter(ArmorStats::leggings), (App)SlotStats.CODEC.fieldOf("boots").forGetter(ArmorStats::boots), (App)ExtraCodecs.f_144629_.fieldOf("enchantment_value").forGetter(ArmorStats::enchantmentValue), (App)Codec.FLOAT.fieldOf("toughness").forGetter(ArmorStats::toughness), (App)Codec.FLOAT.fieldOf("knockback_resistance").forGetter(ArmorStats::knockbackResistance)).apply((Applicative)instance, ArmorStats::new));
    }

    public record SlotStats(int defense, int durability) {
        public static final Codec<SlotStats> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)ExtraCodecs.f_144629_.fieldOf("defense").forGetter(SlotStats::defense), (App)ExtraCodecs.f_144629_.fieldOf("durability").forGetter(SlotStats::durability)).apply((Applicative)instance, SlotStats::new));
    }
}

