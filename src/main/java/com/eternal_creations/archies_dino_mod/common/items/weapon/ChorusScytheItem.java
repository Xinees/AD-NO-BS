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
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResultHolder
 *  net.minecraft.world.entity.Entity
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
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
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
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ChorusScytheItem
extends ADWeaponItem
implements ItemCombatAction.Handler {
    public static final ADWeaponItem.WeaponStats DEFAULT_STATS = new ADWeaponItem.WeaponStats(11, -2.8f, 2048, false);
    private static final int USE_DURATION_TICKS = 32;
    private static final int ENCHANTMENT_VALUE = 15;
    private static final int SPIN_COOLDOWN_TICKS = 400;
    private static final float SPIN_RADIUS = 6.0f;
    private static final double KNOCKBACK_HORIZONTAL_VELOCITY = 3.2;
    private static final double KNOCKBACK_VERTICAL_VELOCITY = 1.35;
    private static final double MIN_KNOCKBACK_FACTOR = 0.25;
    private static final long COMBO_RESET_TICKS = 30L;
    private static final int ATTACK_SIMULATION_TICK = 10;
    private static final float ATTACK_SIMULATION_RANGE = 2.8f;
    private static final float ATTACK_SIMULATION_STEP = 0.5f;
    private static final float BASIC_ATTACK_EXIT_TIME = 0.8f;
    private static final float ATTACK1_DURATION = 1.25f;
    private static final float ATTACK2_DURATION = 1.2917f;
    private static final float SPIN_ANIMATION_DURATION = 1.875f;
    private static final float SPIN_EXIT_TIME = 0.8f;
    private static final int SPIN_IMPACT_TICK = 8;
    private static final int SPIN_AMBIENT_START_TICK = 5;
    private static final float COBWEB_DIG_SPEED = 15.0f;
    private static final float SWORD_EFFICIENT_DIG_SPEED = 1.5f;
    private static final float DEFAULT_DIG_SPEED = 1.0f;
    private static final double AMBIENT_PARTICLE_RADIUS = 2.5;
    private static final int AMBIENT_PARTICLE_COUNT = 8;
    private static final int IMPACT_RING_PARTICLE_STEPS = 36;
    private static final float SOUND_VOLUME = 1.0f;
    private static final float SOUND_PITCH = 1.0f;
    private static final float SPIN_SOUND_VOLUME = 3.0f;
    public static final ActionType ATTACK1 = ActionType.register((ResourceLocation)Constants.prefix("scythe_attack_1"));
    public static final ActionType ATTACK2 = ActionType.register((ResourceLocation)Constants.prefix("scythe_attack_2"));
    public static final ActionType SPIN = ActionType.register((ResourceLocation)Constants.prefix("scythe_spin"));
    private static int comboIndex = 0;
    private static long lastAttackTime = 0L;
    private final ItemCombatAction combatAction = ItemCombatAction.of().onStartAttack((player, itemStack, hand) -> {
        long currentTick = player.m_9236_().m_46467_();
        if (currentTick < lastAttackTime || currentTick - lastAttackTime > 30L) {
            comboIndex = 0;
        }
        ActionType actionType = comboIndex == 0 ? ATTACK1 : ATTACK2;
        comboIndex = (comboIndex + 1) % 2;
        lastAttackTime = currentTick;
        if (player instanceof SyncedActionHolder) {
            SyncedActionHolder holder = (SyncedActionHolder)player;
            holder.playActionToServer(actionType, hand);
        }
    }).shouldContinueAttack().canAttackCheckActions(new ActionType[]{ATTACK1, ATTACK2, SPIN}).canDigBlocks((player, itemStack, hand, pos) -> itemStack.m_41735_(player.m_9236_().m_8055_(pos))).build();
    private final ItemCombatAction noAttackAction = ItemCombatAction.of().shouldContinueAttack().canAttackCheckActions(new ActionType[]{SPIN}).canDigBlocks((player, itemStack, hand, pos) -> itemStack.m_41735_(player.m_9236_().m_8055_(pos))).build();

    private static void registerBasicAttackAction(ActionType actionType, float duration) {
        PlayerActions.INSTANCE.registerActionState(player -> ActionState.of((ActionType)actionType, (float)duration, (float)0.2f, (float)0.4f).exitProgress(0.8f).onStart(() -> ChorusScytheItem.playAttackSound(player)).onTick(tick -> {
            if (tick == 10) {
                ItemCombatAction.Handler.simulateAttack((LivingEntity)player, (double)2.8f, (double)0.5);
            }
        }).requireHolding((LivingEntity)player, itemStack -> itemStack.m_41720_() instanceof ChorusScytheItem));
    }

    public ChorusScytheItem(Item.Properties properties, Supplier<ADWeaponItem.WeaponStats> weaponStatsSupplier) {
        super(properties, weaponStatsSupplier);
    }

    @Override
    public boolean canSweep() {
        return true;
    }

    public int m_6473_() {
        return 15;
    }

    public boolean m_6832_(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.m_150930_(ADItems.CHORUS_FOSSIL.get());
    }

    public float m_8102_(ItemStack stack, BlockState state) {
        if (state.m_60713_(Blocks.f_50033_)) {
            return 15.0f;
        }
        return state.m_204336_(BlockTags.f_278398_) ? 1.5f : 1.0f;
    }

    public void m_7373_(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.m_7373_(stack, level, tooltipComponents, isAdvanced);
        ClientUtil.addMultilineTooltip(ADStrings.Description.CHORUS_SCYTHE, tooltipComponents, 20);
    }

    public boolean m_8096_(BlockState block) {
        return block.m_60713_(Blocks.f_50033_);
    }

    public int m_8105_(ItemStack stack) {
        return 32;
    }

    public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        if (player.m_36335_().m_41519_((Item)this)) {
            return InteractionResultHolder.m_19100_((Object)itemstack);
        }
        player.m_6672_(hand);
        if (player instanceof SyncedActionHolder) {
            SyncedActionHolder holder = (SyncedActionHolder)player;
            if (level.f_46443_) {
                holder.playActionToServer(SPIN, hand);
            } else {
                holder.playActionSynced(SPIN, hand);
            }
        }
        return InteractionResultHolder.m_19096_((Object)itemstack);
    }

    @Nullable
    public ItemCombatAction getCombatAction(ItemStack itemStack) {
        return this.getWeaponStats().disableFancyAttacks() ? this.noAttackAction : this.combatAction;
    }

    private static void handleSpinAttack(Player player, int tick) {
        Level level = player.m_9236_();
        if (tick == 8 || tick == 15 || tick == 25) {
            ChorusScytheItem.playSpinSound(player);
        }
        if (!level.f_46443_ && level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            if (tick > 5) {
                ChorusScytheItem.spawnAmbientSpinParticles(serverLevel, player);
            }
        }
        if (tick == 8) {
            ChorusScytheItem.executeSpinImpact(player);
        }
    }

    private static void executeSpinImpact(Player player) {
        Level level = player.m_9236_();
        if (!level.f_46443_ && level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            ChorusScytheItem.spawnImpactRingParticles(serverLevel, player);
        }
        player.m_36335_().m_41524_(player.m_21205_().m_41720_(), 400);
        level.m_6443_(LivingEntity.class, player.m_20191_().m_82400_(6.0), ChorusScytheItem.knockbackPredicate(player)).forEach(target -> ChorusScytheItem.applySpinKnockback(player, target));
    }

    private static void applySpinKnockback(Player player, LivingEntity target) {
        Vec3 diff = target.m_20182_().m_82546_(player.m_20182_());
        Vec3 horizontalDir = new Vec3(diff.f_82479_, 0.0, diff.f_82481_).m_82541_();
        if (horizontalDir.m_82556_() == 0.0) {
            horizontalDir = new Vec3(player.m_20154_().f_82479_, 0.0, player.m_20154_().f_82481_).m_82541_();
        }
        double resistanceFactor = 1.0 - target.m_21133_(Attributes.f_22278_);
        double appliedRes = Math.max(0.25, resistanceFactor);
        double motionX = horizontalDir.f_82479_ * 3.2 * appliedRes;
        double motionY = 1.35 * appliedRes;
        double motionZ = horizontalDir.f_82481_ * 3.2 * appliedRes;
        target.m_6469_(target.m_269291_().m_269075_(player), (float)DEFAULT_STATS.damage() * 1.5f);
        target.m_20334_(motionX, motionY, motionZ);
        target.f_19864_ = true;
        target.f_19812_ = true;
        Level level = target.m_9236_();
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            ChorusScytheItem.spawnImpactParticles(serverLevel, target);
            ChorusScytheItem.spawnPurpleTrail(serverLevel, target.m_20182_(), new Vec3(motionX, motionY, motionZ));
        }
        if (target instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)target;
            serverPlayer.f_8906_.m_9829_((Packet)new ClientboundSetEntityMotionPacket((Entity)serverPlayer));
        }
    }

    private static Predicate<LivingEntity> knockbackPredicate(Player player) {
        double maxDistSq = 36.0;
        return living -> !living.m_5833_() && living != player && !player.m_7307_((Entity)living) && !ChorusScytheItem.isPlayerPet(player, living) && !ChorusScytheItem.isMarkerArmorStand(living) && player.m_20280_((Entity)living) <= maxDistSq;
    }

    private static boolean isPlayerPet(Player player, LivingEntity entity) {
        TamableAnimal animal;
        return entity instanceof TamableAnimal && (animal = (TamableAnimal)entity).m_21824_() && player.m_20148_().equals(animal.m_21805_());
    }

    private static boolean isMarkerArmorStand(LivingEntity entity) {
        ArmorStand armorStand;
        return entity instanceof ArmorStand && (armorStand = (ArmorStand)entity).m_31677_();
    }

    private static void playAttackSound(Player player) {
        Level level = player.m_9236_();
        level.m_214150_(player, player.m_20185_(), player.m_20186_(), player.m_20189_(), ADSoundEvents.SCYTHE_ATTACK.get(), SoundSource.PLAYERS, 1.0f, 1.0f, level.f_46441_.m_188505_());
    }

    private static void playSpinSound(Player player) {
        Level level = player.m_9236_();
        level.m_214150_(player, player.m_20185_(), player.m_20186_(), player.m_20189_(), ADSoundEvents.SCYTHE_SPIN.get(), SoundSource.PLAYERS, 3.0f, 1.0f, level.f_46441_.m_188505_());
    }

    private static void spawnAmbientSpinParticles(ServerLevel level, Player player) {
        for (int i = 0; i < 8; ++i) {
            double angle = level.f_46441_.m_188500_() * 2.0 * Math.PI;
            double px = player.m_20185_() + Math.cos(angle) * 2.5;
            double pz = player.m_20189_() + Math.sin(angle) * 2.5;
            double py = player.m_20186_() + 0.5 + (level.f_46441_.m_188500_() - 0.5);
            level.m_8767_((ParticleOptions)ParticleTypes.f_123799_, px, py, pz, 1, 0.05, 0.05, 0.05, 0.01);
            level.m_8767_((ParticleOptions)ParticleTypes.f_123760_, px, py, pz, 1, 0.1, 0.1, 0.1, 0.0);
        }
    }

    private static void spawnImpactRingParticles(ServerLevel level, Player player) {
        double ringRadius = 4.5;
        double angleStep = 10.0;
        for (int i = 0; i < 36; ++i) {
            double angle = Math.toRadians((double)i * angleStep);
            double px = player.m_20185_() + Math.cos(angle) * ringRadius;
            double pz = player.m_20189_() + Math.sin(angle) * ringRadius;
            level.m_8767_((ParticleOptions)ParticleTypes.f_123799_, px, player.m_20186_() + 0.2, pz, 2, 0.1, 0.2, 0.1, 0.02);
        }
    }

    private static void spawnImpactParticles(ServerLevel level, LivingEntity target) {
        double y = target.m_20186_() + 0.5;
        level.m_8767_((ParticleOptions)ParticleTypes.f_123799_, target.m_20185_(), y, target.m_20189_(), 15, 0.3, 0.3, 0.3, 0.05);
        level.m_8767_((ParticleOptions)ParticleTypes.f_123760_, target.m_20185_(), y, target.m_20189_(), 20, 0.4, 0.4, 0.4, 0.5);
    }

    private static void spawnPurpleTrail(ServerLevel level, Vec3 startPos, Vec3 velocity) {
        int steps = 12;
        for (int i = 0; i <= steps; ++i) {
            double progress = (double)i / (double)steps;
            double tx = startPos.f_82479_ + velocity.f_82479_ * progress * 0.6;
            double ty = startPos.f_82480_ + 0.5 + velocity.f_82480_ * progress * 0.6;
            double tz = startPos.f_82481_ + velocity.f_82481_ * progress * 0.6;
            level.m_8767_((ParticleOptions)ParticleTypes.f_123799_, tx, ty, tz, 2, 0.05, 0.05, 0.05, 0.01);
            level.m_8767_((ParticleOptions)ParticleTypes.f_123760_, tx, ty, tz, 2, 0.1, 0.1, 0.1, 0.05);
            level.m_8767_((ParticleOptions)ParticleTypes.f_123771_, tx, ty, tz, 1, 0.02, 0.02, 0.02, 0.0);
        }
    }

    static {
        ChorusScytheItem.registerBasicAttackAction(ATTACK1, 1.25f);
        ChorusScytheItem.registerBasicAttackAction(ATTACK2, 1.2917f);
        PlayerActions.INSTANCE.registerActionState(player -> ActionState.of((ActionType)SPIN, (float)1.875f, (float)0.2f, (float)0.4f).exitProgress(0.8f).onTick(tick -> ChorusScytheItem.handleSpinAttack(player, tick)).requireUsing((LivingEntity)player, itemStack -> itemStack.m_41720_() instanceof ChorusScytheItem));
    }
}

