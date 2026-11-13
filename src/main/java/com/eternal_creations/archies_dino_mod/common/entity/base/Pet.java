/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.Mob
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.eternal_creations.archies_dino_mod.common.entity.component.PetComponent;
import java.util.UUID;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface Pet
extends OwnableEntity {
    default public InteractionResult eatFromHand(Player player, ItemStack itemStack) {
        Pet pet = this;
        if (pet instanceof AgeableMob) {
            AgeableMob mob = (AgeableMob)pet;
            Level level = mob.m_9236_();
            if (this.m_6898_(itemStack)) {
                if (this.isTamed()) {
                    int age = mob.m_146764_();
                    if (mob instanceof Animal) {
                        Animal animal = (Animal)mob;
                        if (age == 0 && animal.m_5957_()) {
                            animal.m_27595_(player);
                        } else if (mob.m_6162_()) {
                            animal.m_146740_(Animal.m_216967_((int)(-age)), true);
                        }
                    }
                    if (!player.m_150110_().f_35937_) {
                        itemStack.m_41774_(1);
                    }
                }
                return InteractionResult.m_19078_((boolean)level.f_46443_);
            }
        }
        return InteractionResult.PASS;
    }

    default public boolean isRiddenByOwner() {
        Mob mob = (Mob)this;
        LivingEntity passenger = mob.m_6688_();
        return passenger != null && this.isOwnedBy(passenger);
    }

    default public boolean tryToTame(Player player, RandomSource random) {
        return random.m_188503_(5) == 0;
    }

    default public boolean isOwnedBy(LivingEntity entity) {
        return entity == this.m_269323_();
    }

    public PetComponent<?> pet();

    public void setOwnerUUID(UUID var1);

    public boolean isTamed();

    public void setTamed(boolean var1);

    public boolean m_6898_(ItemStack var1);

    public boolean isSitting();

    public void setSitting(boolean var1);
}

