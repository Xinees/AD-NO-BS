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
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.ExtraCodecs
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ai.attributes.Attribute
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Vanishable
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 */
package com.eternal_creations.archies_dino_mod.common.items.util;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ADWeaponItem
extends Item
implements Vanishable {
    private final Supplier<WeaponStats> weaponStats;

    public ADWeaponItem(Item.Properties properties, Supplier<WeaponStats> weaponStats) {
        super(properties.m_41487_(1).m_41503_(weaponStats.get().durability()));
        this.weaponStats = weaponStats;
    }

    public int m_41462_() {
        return this.getWeaponStats().durability();
    }

    public boolean m_41465_() {
        return this.m_41462_() > 0;
    }

    public WeaponStats getWeaponStats() {
        return this.weaponStats.get();
    }

    public int m_142158_(ItemStack stack) {
        return Math.round(13.0f - (float)stack.m_41773_() * 13.0f / (float)this.m_41462_());
    }

    public int m_142159_(ItemStack stack) {
        float f = Math.max(0.0f, ((float)this.m_41462_() - (float)stack.m_41773_()) / (float)this.m_41462_());
        return Mth.m_14169_((float)(f / 3.0f), (float)1.0f, (float)1.0f);
    }

    protected ImmutableMultimap.Builder<Attribute, AttributeModifier> getAttributeBuilder() {
        ImmutableMultimap.Builder builder = ImmutableMultimap.builder();
        builder.put((Object)Attributes.f_22281_, (Object)new AttributeModifier(f_41374_, "Weapon modifier", (double)Math.max(1, this.weaponStats.get().damage() - 1), AttributeModifier.Operation.ADDITION));
        builder.put((Object)Attributes.f_22283_, (Object)new AttributeModifier(f_41375_, "Weapon modifier", (double)this.weaponStats.get().attackSpeed(), AttributeModifier.Operation.ADDITION));
        return builder;
    }

    public Multimap<Attribute, AttributeModifier> m_7167_(EquipmentSlot equipmentSlot) {
        return equipmentSlot == EquipmentSlot.MAINHAND ? this.getAttributeBuilder().build() : super.m_7167_(equipmentSlot);
    }

    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.m_41622_(1, attacker, living -> living.m_21166_(EquipmentSlot.MAINHAND));
        return true;
    }

    public boolean m_6813_(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (state.m_60800_((BlockGetter)level, pos) != 0.0f) {
            stack.m_41622_(2, entityLiving, living -> living.m_21166_(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    public boolean m_6777_(BlockState state, Level level, BlockPos pos, Player player) {
        return !player.m_7500_();
    }

    public boolean canSweep() {
        return false;
    }

    public record WeaponStats(int damage, float attackSpeed, int durability, boolean disableFancyAttacks) {
        public static final Codec<WeaponStats> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)ExtraCodecs.f_144629_.fieldOf("damage").forGetter(WeaponStats::damage), (App)Codec.FLOAT.fieldOf("attack_speed").forGetter(WeaponStats::attackSpeed), (App)ExtraCodecs.f_144629_.fieldOf("durability").forGetter(WeaponStats::durability), (App)Codec.BOOL.fieldOf("disable_fancy_attacks").forGetter(WeaponStats::disableFancyAttacks)).apply((Applicative)instance, WeaponStats::new));
    }
}

