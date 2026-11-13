/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.core.function.Condition
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.CriteriaTriggers
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.core.particles.SimpleParticleType
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.players.OldUsersConverter
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.OwnableEntity
 *  net.minecraft.world.entity.ai.attributes.AttributeInstance
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier
 *  net.minecraft.world.entity.ai.attributes.AttributeModifier$Operation
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 */
package com.eternal_creations.archies_dino_mod.common.entity.component;

import com.astemir.astemirlib.re.core.function.Condition;
import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import com.eternal_creations.archies_dino_mod.common.entity.component.EntityComponent;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PetComponent<T extends AgeableMob>
implements EntityComponent {
    private static final UUID BABY_HEALTH_MODIFIER_UUID = UUID.fromString("1d8b9487-1300-4b2b-932b-316e6d1e4e20");
    private static final UUID TAMED_HEALTH_MODIFIER_UUID = UUID.fromString("4a8e21c3-8f0a-41d2-b6e1-912f71881b2e");
    private final Condition canBeTamed;
    @Nullable
    private final Float babyHealthMultiplier;
    @Nullable
    private final Float tamedHealthMultiplier;
    private final T mob;

    public PetComponent(T mob, Definition<T> definition) {
        this.mob = mob;
        this.canBeTamed = definition.canBeTamed;
        this.babyHealthMultiplier = definition.babyHealthMultiplier;
        this.tamedHealthMultiplier = definition.tamedHealthMultiplier;
    }

    protected AttributeModifier createBabyHealthMod() {
        return new AttributeModifier(BABY_HEALTH_MODIFIER_UUID, "Baby health reduction", (double)(-(1.0f - Objects.requireNonNull(this.babyHealthMultiplier).floatValue())), AttributeModifier.Operation.MULTIPLY_BASE);
    }

    protected AttributeModifier createTamedHealthMod() {
        return new AttributeModifier(TAMED_HEALTH_MODIFIER_UUID, "Tamed health bonus", (double)(Objects.requireNonNull(this.tamedHealthMultiplier).floatValue() - 1.0f), AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        UUID ownerUUID = ((OwnableEntity)this.mob).m_21805_();
        if (ownerUUID != null) {
            compound.m_128362_("Owner", ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        String s;
        UUID uuid = null;
        if (compound.m_128403_("Owner")) {
            uuid = compound.m_128342_("Owner");
        } else if (compound.m_128441_("Owner") && !(s = compound.m_128461_("Owner")).isEmpty() && this.mob.m_20194_() != null) {
            uuid = OldUsersConverter.m_11083_((MinecraftServer)this.mob.m_20194_(), (String)s);
        }
        if (uuid != null) {
            try {
                ((Pet)this.mob).setOwnerUUID(uuid);
                ((Pet)this.mob).setTamed(true);
            }
            catch (Throwable ignored) {
                this.untame();
            }
        }
    }

    @Override
    public void customServerAiStep() {
        boolean hasBabyModifier;
        if (this.babyHealthMultiplier == null) {
            return;
        }
        AttributeInstance maxHealthAttr = this.mob.m_21051_(Attributes.f_22276_);
        if (maxHealthAttr == null) {
            return;
        }
        boolean bl = hasBabyModifier = maxHealthAttr.m_22111_(BABY_HEALTH_MODIFIER_UUID) != null;
        if (this.mob.m_6162_()) {
            if (!hasBabyModifier) {
                maxHealthAttr.m_22118_(this.createBabyHealthMod());
                if (this.mob.m_21223_() > this.mob.m_21233_()) {
                    this.mob.m_21153_(this.mob.m_21233_());
                }
            }
        } else if (hasBabyModifier) {
            maxHealthAttr.m_22120_(BABY_HEALTH_MODIFIER_UUID);
            this.mob.m_5634_(this.mob.m_21233_() * 0.5f);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        InteractionResult result;
        Level level = this.mob.m_9236_();
        ItemStack itemStack = player.m_21120_(hand);
        if (this.canBeTamed.check() && !((Pet)this.mob).isTamed() && ((Pet)this.mob).m_6898_(itemStack) && this.mob.m_5448_() == null && (result = ((Pet)this.mob).eatFromHand(player, itemStack)) != InteractionResult.PASS) {
            if (!level.f_46443_) {
                if (!player.m_150110_().f_35937_) {
                    itemStack.m_41774_(1);
                }
                if (((Pet)this.mob).tryToTame(player, this.mob.m_217043_())) {
                    this.tame(player);
                    this.mob.m_21573_().m_26573_();
                    this.mob.m_6710_(null);
                    level.m_7605_(this.mob, (byte)7);
                } else {
                    level.m_7605_(this.mob, (byte)6);
                }
            }
            return InteractionResult.m_19078_((boolean)level.f_46443_);
        }
        if (((Pet)this.mob).isOwnedBy((LivingEntity)player)) {
            InteractionResult eatResult;
            if (((Pet)this.mob).m_6898_(itemStack) && (eatResult = ((Pet)this.mob).eatFromHand(player, itemStack)).m_19077_()) {
                return eatResult;
            }
            if (this.mob.m_6162_() || player.m_6047_() && itemStack.m_41619_()) {
                if (!level.f_46443_) {
                    ((Pet)this.mob).setSitting(!((Pet)this.mob).isSitting());
                }
                return InteractionResult.m_19078_((boolean)level.f_46443_);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 7) {
            this.spawnTamingParticles(true);
        } else if (id == 6) {
            this.spawnTamingParticles(false);
        }
    }

    private void spawnTamingParticles(boolean tamed) {
        SimpleParticleType particleoptions = tamed ? ParticleTypes.f_123750_ : ParticleTypes.f_123762_;
        RandomSource random = this.mob.m_217043_();
        for (int i = 0; i < 7; ++i) {
            double d0 = random.m_188583_() * 0.02;
            double d1 = random.m_188583_() * 0.02;
            double d2 = random.m_188583_() * 0.02;
            this.mob.m_9236_().m_7106_((ParticleOptions)particleoptions, this.mob.m_20208_(1.0), this.mob.m_20187_() + 0.5, this.mob.m_20262_(1.0), d0, d1, d2);
        }
    }

    public void tame(Player player) {
        ((Pet)this.mob).setTamed(true);
        ((Pet)this.mob).setOwnerUUID(player.m_20148_());
        if (!this.mob.m_9236_().m_5776_()) {
            this.applyTamedHealthBonus();
        }
        if (player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)player;
            T t = this.mob;
            if (t instanceof Animal) {
                Animal animal = (Animal)t;
                CriteriaTriggers.f_10590_.m_68829_(serverPlayer, animal);
            }
        }
    }

    public void untame() {
        ((Pet)this.mob).setTamed(false);
        ((Pet)this.mob).setOwnerUUID(null);
        if (!this.mob.m_9236_().f_46443_) {
            this.removeTamedHealthBonus();
        }
    }

    private void applyTamedHealthBonus() {
        if (this.tamedHealthMultiplier == null) {
            return;
        }
        AttributeInstance maxHealthAttr = this.mob.m_21051_(Attributes.f_22276_);
        if (maxHealthAttr != null && maxHealthAttr.m_22111_(TAMED_HEALTH_MODIFIER_UUID) == null) {
            float oldMax = this.mob.m_21233_();
            maxHealthAttr.m_22125_(this.createTamedHealthMod());
            float newMax = this.mob.m_21233_();
            if (newMax > oldMax) {
                this.mob.m_5634_(newMax - oldMax);
            }
        }
    }

    private void removeTamedHealthBonus() {
        if (this.tamedHealthMultiplier == null) {
            return;
        }
        AttributeInstance maxHealthAttr = this.mob.m_21051_(Attributes.f_22276_);
        if (maxHealthAttr != null && maxHealthAttr.m_22111_(TAMED_HEALTH_MODIFIER_UUID) != null) {
            maxHealthAttr.m_22120_(TAMED_HEALTH_MODIFIER_UUID);
            this.mob.m_21153_(Math.min(this.mob.m_21223_(), this.mob.m_21233_()));
        }
    }

    public boolean canBeTamed() {
        return this.canBeTamed.check();
    }

    public static <T extends AgeableMob> Definition<T> of() {
        return new Definition();
    }

    public static class Definition<T extends AgeableMob>
    implements EntityComponent.Definition<T> {
        private Condition canBeTamed = Condition.TRUE;
        @Nullable
        private Float babyHealthMultiplier = null;
        @Nullable
        private Float tamedHealthMultiplier = null;

        public Definition<T> canBeTamed(Condition canBeTamed) {
            this.canBeTamed = canBeTamed;
            return this;
        }

        public Definition<T> setBabyHealthMultiplier(float multiplier) {
            this.babyHealthMultiplier = Float.valueOf(multiplier);
            return this;
        }

        public Definition<T> setTamedHealthMultiplier(float multiplier) {
            this.tamedHealthMultiplier = Float.valueOf(multiplier);
            return this;
        }

        @Override
        public PetComponent<T> build(T mob) {
            return new PetComponent<T>(mob, this);
        }
    }
}

