/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.stats.Stats
 *  net.minecraft.util.ExtraCodecs
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.projectile.AbstractArrow
 *  net.minecraft.world.entity.projectile.AbstractArrow$Pickup
 *  net.minecraft.world.item.ArrowItem
 *  net.minecraft.world.item.BowItem
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.ProjectileWeaponItem
 *  net.minecraft.world.item.enchantment.Enchantment
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.items.util;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class ADBowItem
extends BowItem {
    private final Supplier<BowStats> bowStats;

    public ADBowItem(Item.Properties properties, Supplier<BowStats> bowStats) {
        super(properties.m_41487_(1).m_41503_(bowStats.get().durability()));
        this.bowStats = bowStats;
    }

    public int m_41462_() {
        return this.bowStats.get().durability();
    }

    public boolean m_41465_() {
        return this.m_41462_() > 0;
    }

    public int m_142158_(ItemStack stack) {
        return Math.round(13.0f - (float)stack.m_41773_() * 13.0f / (float)this.m_41462_());
    }

    public int m_142159_(ItemStack stack) {
        float f = Math.max(0.0f, ((float)this.m_41462_() - (float)stack.m_41773_()) / (float)this.m_41462_());
        return Mth.m_14169_((float)(f / 3.0f), (float)1.0f, (float)1.0f);
    }

    public void m_5551_(ItemStack stack, Level level, LivingEntity living, int timeLeft) {
        if (living instanceof Player) {
            Player player = (Player)living;
            boolean infinite = player.m_150110_().f_35937_ || EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44952_, (ItemStack)stack) > 0;
            ItemStack arrowStack = this.getProjectile(player, stack);
            if (!arrowStack.m_41619_() || infinite) {
                int useDuration;
                float power;
                if (arrowStack.m_41619_()) {
                    arrowStack = this.generateInfiniteArrowStack();
                }
                if ((power = ADBowItem.m_40661_((int)(useDuration = this.m_8105_(stack) - timeLeft))) >= 0.1f) {
                    boolean pickup;
                    boolean bl = pickup = infinite && arrowStack.m_150930_(Items.f_42412_);
                    if (!level.f_46443_) {
                        int k;
                        int j;
                        ArrowItem arrowItem = (ArrowItem)(arrowStack.m_41720_() instanceof ArrowItem ? arrowStack.m_41720_() : Items.f_42412_);
                        AbstractArrow arrow = this.createArrowFromItem(arrowItem, player, arrowStack);
                        arrow.m_37251_((Entity)player, player.m_146909_(), player.m_146908_(), 0.0f, power * 3.0f, 1.0f);
                        if (power == 1.0f) {
                            arrow.m_36762_(true);
                        }
                        if ((j = this.getPower(stack)) > 0) {
                            arrow.m_36781_(arrow.m_36789_() + (double)j * 0.5 + 0.5);
                        }
                        if ((k = EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44989_, (ItemStack)stack)) > 0) {
                            arrow.m_36735_(k);
                        }
                        if (EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44990_, (ItemStack)stack) > 0) {
                            arrow.m_20254_(100);
                        }
                        stack.m_41622_(1, (LivingEntity)player, plyr -> plyr.m_21190_(player.m_7655_()));
                        if (pickup || player.m_150110_().f_35937_ && (arrowStack.m_150930_(Items.f_42737_) || arrowStack.m_150930_(Items.f_42738_))) {
                            arrow.f_36705_ = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        level.m_7967_((Entity)arrow);
                    }
                    level.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_11687_, SoundSource.PLAYERS, 1.0f, 1.0f / (level.m_213780_().m_188501_() * 0.4f + 1.2f) + power * 0.5f);
                    if (!pickup && !player.m_150110_().f_35937_) {
                        arrowStack.m_41774_(1);
                        if (arrowStack.m_41619_()) {
                            player.m_150109_().m_36057_(arrowStack);
                        }
                    }
                    player.m_36246_(Stats.f_12982_.m_12902_((Object)this));
                }
            }
        }
    }

    protected int getPower(ItemStack stack) {
        return EnchantmentHelper.m_44843_((Enchantment)Enchantments.f_44988_, (ItemStack)stack) + this.bowStats.get().power();
    }

    public ItemStack getProjectile(Player player, ItemStack shootable) {
        Item item = shootable.m_41720_();
        if (item instanceof ProjectileWeaponItem) {
            ProjectileWeaponItem bow = (ProjectileWeaponItem)item;
            Predicate predicate = bow.m_6442_();
            ItemStack itemstack = ProjectileWeaponItem.m_43010_((LivingEntity)player, (Predicate)predicate);
            if (itemstack.m_41619_()) {
                predicate = bow.m_6437_();
                for (int i = 0; i < player.m_150109_().m_6643_(); ++i) {
                    ItemStack itemstack1 = player.m_150109_().m_8020_(i);
                    if (!predicate.test(itemstack1)) continue;
                    return itemstack1;
                }
                return player.m_150110_().f_35937_ ? this.generateInfiniteArrowStack() : ItemStack.f_41583_;
            }
            return itemstack;
        }
        return ItemStack.f_41583_;
    }

    protected ItemStack generateInfiniteArrowStack() {
        return new ItemStack((ItemLike)Items.f_42412_);
    }

    protected AbstractArrow createArrowFromItem(ArrowItem item, Player player, ItemStack stack) {
        return item.m_6394_(player.m_9236_(), stack, (LivingEntity)player);
    }

    public record BowStats(int power, int durability) {
        public static final Codec<BowStats> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)ExtraCodecs.f_144629_.fieldOf("bonus_power").forGetter(BowStats::power), (App)ExtraCodecs.f_144629_.fieldOf("durability").forGetter(BowStats::durability)).apply((Applicative)instance, BowStats::new));
    }
}

