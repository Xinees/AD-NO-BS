/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.AgeableMob
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.eternal_creations.archies_dino_mod.common.block.eggs;

import com.eternal_creations.archies_dino_mod.common.block.entity.OwnableBlockEntity;
import com.eternal_creations.archies_dino_mod.common.block.entity.TickingBlockEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.Pet;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class DinoEggBlock
extends BaseEntityBlock {
    public static final int MAX_HATCH = 4;
    public static final IntegerProperty HATCH = IntegerProperty.m_61631_((String)"hatch", (int)0, (int)4);
    private static final BlockEntityTicker<?> TICKER = (level, blockPos, blockState, blockEntity) -> {
        if (blockEntity instanceof TickingBlockEntity) {
            TickingBlockEntity tickingBlockEntity = (TickingBlockEntity)blockEntity;
            tickingBlockEntity.tick(level, blockPos, blockState);
        }
    };
    private final Supplier<? extends BlockEntityType<?>> blockEntityType;
    private final ResourceSupplier<? extends EntityType<? extends AgeableMob>> entityType;

    public DinoEggBlock(Supplier<? extends BlockEntityType<?>> blockEntityType, ResourceSupplier<? extends EntityType<? extends AgeableMob>> entityType) {
        super(BlockBehaviour.Properties.m_284310_().m_280606_().m_60955_().m_60978_(0.5f).m_278166_(PushReaction.DESTROY).m_60977_());
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)HATCH, (Comparable)Integer.valueOf(0)));
        this.blockEntityType = blockEntityType;
        this.entityType = entityType;
    }

    protected boolean shouldTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (this.isDay(level)) {
            return true;
        }
        return level.f_46441_.m_188503_(36) == 0;
    }

    protected abstract boolean canHatch(BlockState var1, Level var2, BlockPos var3);

    protected boolean isDay(Level level) {
        float timeOfDay = level.m_46942_(1.0f);
        return timeOfDay < 0.69f && timeOfDay > 0.65f;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{HATCH});
    }

    public void m_6240_(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        super.m_6240_(level, player, pos, state, te, stack);
        this.destroyEgg(level, pos, state);
    }

    public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockEntity entity = level.m_7702_(pos);
        if (!level.f_46443_ && placer instanceof Player) {
            Player player = (Player)placer;
            if (entity instanceof OwnableBlockEntity) {
                OwnableBlockEntity ownableBlock = (OwnableBlockEntity)entity;
                ownableBlock.setOwnerUUID(player.m_20148_());
            }
        }
    }

    private void destroyEgg(Level level, BlockPos pos, BlockState state) {
        level.m_5594_(null, pos, ADSoundEvents.DINO_EGG_BREAK.get(), SoundSource.BLOCKS, 0.7f, 0.9f + level.f_46441_.m_188501_() * 0.2f);
        level.m_46961_(pos, false);
        level.m_220407_(GameEvent.f_157794_, pos, GameEvent.Context.m_223722_((BlockState)state));
        level.m_46796_(2001, pos, Block.m_49956_((BlockState)state));
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.f_46443_) {
            ItemStack eggStack = new ItemStack((ItemLike)this);
            if (!player.m_150109_().m_36054_(eggStack)) {
                player.m_36176_(eggStack, false);
            }
            level.m_5594_(null, pos, ADSoundEvents.DINO_EGG_PICKUP.get(), SoundSource.PLAYERS, 0.2f, (level.f_46441_.m_188501_() - level.f_46441_.m_188501_()) * 0.2f + 1.0f);
            level.m_7471_(pos, false);
        }
        return InteractionResult.m_19078_((boolean)level.f_46443_);
    }

    public void m_213898_(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (this.shouldTick(state, (Level)level, pos, random) && this.canHatch(state, (Level)level, pos)) {
            int hatch = (Integer)state.m_61143_((Property)HATCH);
            if (hatch < 4) {
                level.m_5594_(null, pos, ADSoundEvents.DINO_EGG_CRACK.get(), SoundSource.BLOCKS, 0.7f, 0.9f + random.m_188501_() * 0.2f);
                level.m_7731_(pos, (BlockState)state.m_61124_((Property)HATCH, (Comparable)Integer.valueOf(hatch + 1)), 2);
            } else {
                level.m_5594_(null, pos, ADSoundEvents.DINO_EGG_HATCH.get(), SoundSource.BLOCKS, 0.7f, 0.9f + random.m_188501_() * 0.2f);
                level.m_46796_(2001, pos, Block.m_49956_((BlockState)state));
                AgeableMob dinosaur = (AgeableMob)this.entityType.get().m_20615_((Level)level);
                if (dinosaur != null) {
                    dinosaur.m_146762_(-24000);
                    dinosaur.m_7678_((double)pos.m_123341_() + 0.3, (double)pos.m_123342_(), (double)pos.m_123343_() + 0.3, 0.0f, 0.0f);
                    if (dinosaur instanceof Pet) {
                        OwnableBlockEntity ownableBlock;
                        Pet pet = (Pet)dinosaur;
                        BlockEntity blockEntity = level.m_7702_(pos);
                        if (blockEntity instanceof OwnableBlockEntity && (ownableBlock = (OwnableBlockEntity)blockEntity).getOwner() != null) {
                            pet.pet().tame(ownableBlock.getOwner());
                        }
                    }
                    level.m_7967_((Entity)dinosaur);
                }
                level.m_7471_(pos, false);
            }
        }
    }

    public void m_6807_(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)level;
            if (this.canHatch(state, level, pos)) {
                level.m_46796_(2005, pos, 0);
            } else {
                Vec3 posCenter = pos.m_252807_();
                serverLevel.m_8767_((ParticleOptions)ParticleTypes.f_123762_, posCenter.f_82479_, posCenter.f_82480_, posCenter.f_82481_, 16, 0.5, 0.5, 0.5, 0.0);
            }
        }
    }

    public abstract VoxelShape m_5940_(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4);

    @Nullable
    public BlockEntity m_142194_(BlockPos blockPos, BlockState blockState) {
        return this.blockEntityType.get().m_155264_(blockPos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return TICKER;
    }

    public RenderShape m_7514_(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}

