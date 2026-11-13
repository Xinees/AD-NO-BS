/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.action.ActionState
 *  com.astemir.astemirlib.re.common.action.ActionType
 *  com.astemir.astemirlib.re.common.action.PlayerActions
 *  com.astemir.astemirlib.re.common.action.SyncedActionHolder
 *  com.astemir.astemirlib.re.common.world.item.ItemCombatAction
 *  com.astemir.astemirlib.re.common.world.item.ItemCombatAction$Handler
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EquipmentSlot
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.TamableAnimal
 *  net.minecraft.world.entity.ai.attributes.Attributes
 *  net.minecraft.world.entity.decoration.ArmorStand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 */
package com.eternal_creations.archies_dino_mod.common.items.weapon;

import com.astemir.astemirlib.re.common.action.ActionState;
import com.astemir.astemirlib.re.common.action.ActionType;
import com.astemir.astemirlib.re.common.action.PlayerActions;
import com.astemir.astemirlib.re.common.action.SyncedActionHolder;
import com.astemir.astemirlib.re.common.world.item.ItemCombatAction;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.ADStrings;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.items.util.ADWeaponItem;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.util.ClientUtil;
import com.eternal_creations.archies_dino_mod.util.iPlayerWithMace;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class LushsaurMaceItem
extends ADWeaponItem
implements ItemCombatAction.Handler {
    public static final ADWeaponItem.WeaponStats DEFAULT_STATS = new ADWeaponItem.WeaponStats(10, -3.0f, 2048, false);
    public static final ActionType ATTACK = ActionType.register((ResourceLocation)Constants.prefix("lush_mace.attack"));
    public static final ActionType ATTACK_SPIN = ActionType.register((ResourceLocation)Constants.prefix("attack.mace.spin"));
    public static final int SMASH_EVENT = 2013;
    public static final int PARTICLE_COUNT = 750;
    public static final float SMASH_ATTACK_FALL_THRESHOLD = 1.5f;
    private static final float SMASH_ATTACK_HEAVY_THRESHOLD = 5.0f;
    public static final float SPIN_RADIUS = 3.0f;
    private static final double SPIN_HORIZONTAL_KNOCKBACK = 1.6;
    private static final double SPIN_VERTICAL_KNOCKBACK = 0.45;
    private static final int SPIN_COOLDOWN_TICKS = 600;
    private static final int USE_DURATION_TICKS = 20;
    private static final int ENCHANTMENT_VALUE = 15;
    private final ItemCombatAction combatAction = ItemCombatAction.of().simpleAttackAction(ATTACK).shouldContinueAttack().canAttackCheckActions(new ActionType[]{ATTACK, ATTACK_SPIN}).disableBlockDigging().missTime(15).build();
    private final ItemCombatAction noAttackAction = ItemCombatAction.of().canAttackCheckActions(new ActionType[]{ATTACK_SPIN}).missTime(15).build();

    public LushsaurMaceItem(Item.Properties properties, Supplier<ADWeaponItem.WeaponStats> weaponStatsSupplier) {
        super(properties, weaponStatsSupplier);
    }

    public int m_6473_() {
        return 15;
    }

    @Override
    public boolean m_7579_(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ServerPlayer player;
        if (attacker instanceof ServerPlayer && LushsaurMaceItem.canSmashAttack((LivingEntity)(player = (ServerPlayer)attacker))) {
            ServerLevel serverlevel = player.m_284548_();
            iPlayerWithMace iPlayer = (iPlayerWithMace)player;
            if (iPlayer.archies_Dino_Mod$ignoreFallDamageFromCurrentExplosion() && iPlayer.archies_Dino_Mod$currentExplosionImpactPos() != null) {
                if (Objects.requireNonNull(iPlayer.archies_Dino_Mod$currentExplosionImpactPos()).f_82480_ > player.m_20182_().f_82480_) {
                    iPlayer.archies_Dino_Mod$setCurrentExplosionImpactPos(player.m_20182_());
                }
            } else {
                iPlayer.archies_Dino_Mod$setCurrentExplosionImpactPos(player.m_20182_());
            }
            iPlayer.archies_Dino_Mod$setIgnoreFallDamageFromCurrentExplosion(true);
            player.m_20256_(player.m_20184_().m_193103_(Direction.Axis.Y, (double)0.01f));
            player.f_8906_.m_9829_((Packet)new ClientboundSetEntityMotionPacket((Entity)player));
            if (target.m_20096_()) {
                iPlayer.archies_Dino_Mod$setSpawnExtraParticlesOnFall(true);
            }
            LushsaurMaceItem.knockback((Level)serverlevel, (Player)player, (Entity)target);
        }
        stack.m_41622_(1, attacker, living -> living.m_21166_(EquipmentSlot.MAINHAND));
        if (LushsaurMaceItem.canSmashAttack(attacker)) {
            attacker.m_183634_();
        }
        return true;
    }

    public boolean m_6832_(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.m_150930_(ADItems.LUSH_CORE.get());
    }

    public int m_8105_(ItemStack stack) {
        return 20;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        if (player.m_36335_().m_41519_((Item)this)) {
            return InteractionResultHolder.m_19100_((Object)itemstack);
        }
        player.m_6672_(hand);
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            serverLevel.m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), ADSoundEvents.MACE_SPIN.get(), SoundSource.PLAYERS, 3.0f, 1.0f);
        }
        if (player instanceof SyncedActionHolder) {
            SyncedActionHolder holder = (SyncedActionHolder)player;
            if (level.f_46443_) {
                holder.playActionToServer(ATTACK_SPIN, hand);
            } else {
                holder.playActionSynced(ATTACK_SPIN, hand);
            }
        }
        return InteractionResultHolder.m_19096_((Object)itemstack);
    }

    public float getAttackDamageBonus(DamageSource damageSource) {
        Entity entity = damageSource.m_7640_();
        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            if (!LushsaurMaceItem.canSmashAttack(livingentity)) {
                return 0.0f;
            }
            float distance = livingentity.f_19789_;
            float damageBonus = distance <= 3.0f ? 4.0f * distance : (distance <= 8.0f ? 12.0f + 2.0f * (distance - 3.0f) : 22.0f + distance - 8.0f);
            return damageBonus;
        }
        return 0.0f;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.m_7373_(stack, level, tooltipComponents, isAdvanced);
        ClientUtil.addMultilineTooltip(ADStrings.Description.LUSH_MACE, tooltipComponents, 30);
    }

    private static void executeSpinAttack(Player player) {
        Level level = player.m_9236_();
        player.m_36335_().m_41524_(player.m_21205_().m_41720_(), 600);
        level.m_46796_(2013, player.m_20097_(), 375);
        level.m_6443_(LivingEntity.class, player.m_20191_().m_82400_(3.0), LushsaurMaceItem.spinKnockbackPredicate(player)).forEach(living -> {
            Vec3 diff = living.m_20182_().m_82546_(player.m_20182_());
            Vec3 horizontalDir = new Vec3(diff.f_82479_, 0.0, diff.f_82481_).m_82541_();
            if (horizontalDir.m_82556_() == 0.0) {
                horizontalDir = new Vec3(player.m_20154_().f_82479_, 0.0, player.m_20154_().f_82481_).m_82541_();
            }
            double res = Math.max(0.2, 1.0 - living.m_21133_(Attributes.f_22278_));
            double mx = horizontalDir.f_82479_ * 1.6 * res;
            double my = 0.45 * res;
            double mz = horizontalDir.f_82481_ * 1.6 * res;
            living.m_6469_(living.m_269291_().m_269075_(player), (float)DEFAULT_STATS.damage() * 1.5f);
            living.m_20334_(mx, my, mz);
            living.f_19864_ = true;
            living.f_19812_ = true;
            if (living instanceof ServerPlayer) {
                ServerPlayer serverPlayer = (ServerPlayer)living;
                serverPlayer.f_8906_.m_9829_((Packet)new ClientboundSetEntityMotionPacket((Entity)serverPlayer));
            }
        });
    }

    private static void knockback(Level level, Player player, Entity entity) {
        level.m_46796_(2013, entity.m_20097_(), 750);
        level.m_6443_(LivingEntity.class, entity.m_20191_().m_82400_(3.0), LushsaurMaceItem.smashKnockbackPredicate(player)).forEach(living -> {
            Vec3 vec3 = living.m_20182_().m_82546_(entity.m_20182_());
            double knockbackPower = LushsaurMaceItem.getKnockbackPower(player, living, vec3);
            Vec3 vec31 = vec3.m_82541_().m_82490_(knockbackPower);
            if (knockbackPower > 0.0) {
                living.m_5997_(vec31.f_82479_, (double)0.7f, vec31.f_82481_);
                if (living instanceof ServerPlayer) {
                    ServerPlayer serverplayer = (ServerPlayer)living;
                    serverplayer.f_8906_.m_9829_((Packet)new ClientboundSetEntityMotionPacket((Entity)serverplayer));
                }
            }
        });
    }

    private static Predicate<LivingEntity> spinKnockbackPredicate(Player player) {
        double maxDistSq = 9.0;
        return living -> {
            ArmorStand armorStand;
            TamableAnimal animal;
            return !(living.m_5833_() || living == player || player.m_7307_((Entity)living) || living instanceof TamableAnimal && (animal = (TamableAnimal)living).m_21824_() && player.m_20148_().equals(animal.m_21805_()) || living instanceof ArmorStand && (armorStand = (ArmorStand)living).m_31677_() || !(player.m_20280_((Entity)living) <= maxDistSq));
        };
    }

    private static Predicate<LivingEntity> smashKnockbackPredicate(Player player) {
        return LushsaurMaceItem.spinKnockbackPredicate(player);
    }

    private static double getKnockbackPower(Player player, LivingEntity entity, Vec3 entityPos) {
        return (3.0 - entityPos.m_82553_()) * (double)0.7f * (double)(player.f_19789_ > 5.0f ? 2 : 1) * (1.0 - entity.m_21133_(Attributes.f_22278_));
    }

    public static boolean canSmashAttack(LivingEntity entity) {
        return entity.f_19789_ > 1.5f && !entity.m_21255_();
    }

    @Nullable
    public ItemCombatAction getCombatAction(ItemStack itemStack) {
        return this.getWeaponStats().disableFancyAttacks() ? this.noAttackAction : this.combatAction;
    }

    static {
        PlayerActions.INSTANCE.registerActionState(player -> ActionState.of((ActionType)ATTACK, (float)1.0f, (float)0.2f, (float)0.2f).onTick(tick -> {
            if (tick == 10) {
                player.m_9236_().m_6263_(null, player.m_20185_(), player.m_20186_(), player.m_20189_(), ADSoundEvents.MACE_ATTACK.get(), player.m_5720_(), 0.5f, 1.0f);
                ItemCombatAction.Handler.simulateAttack((LivingEntity)player, (double)2.5, (double)0.5);
            }
        }).requireHolding((LivingEntity)player, itemStack -> itemStack.m_41720_() instanceof LushsaurMaceItem));
        PlayerActions.INSTANCE.registerActionState(player -> ActionState.of((ActionType)ATTACK_SPIN, (float)2.2f, (float)0.2f, (float)0.3f).onTick(tick -> {
            if (tick == 12) {
                LushsaurMaceItem.executeSpinAttack(player);
            }
        }).requireUsing((LivingEntity)player, itemStack -> itemStack.m_41720_() instanceof LushsaurMaceItem));
    }
}

