/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package com.eternal_creations.archies_dino_mod.mixin;

import com.eternal_creations.archies_dino_mod.common.items.weapon.LushsaurMaceItem;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(value={Player.class})
public abstract class PlayerAttackDamageBonusMixin
extends LivingEntity {
    protected PlayerAttackDamageBonusMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyVariable(method={"attack"}, at=@At(value="INVOKE", target="Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getKnockbackBonus(Lnet/minecraft/world/entity/LivingEntity;)I"), name={"f"})
    private float getAttackDamageBonus(float damage) {
        ItemStack stack = this.m_21205_();
        Item item = stack.m_41720_();
        if (item instanceof LushsaurMaceItem) {
            LushsaurMaceItem maceItem = (LushsaurMaceItem)item;
            return damage + maceItem.getAttackDamageBonus(this.m_269291_().m_269075_((Player)this));
        }
        return damage;
    }
}

