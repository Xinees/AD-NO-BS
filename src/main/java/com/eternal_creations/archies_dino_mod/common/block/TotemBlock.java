/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.astemir.astemirlib.re.common.function.PositionSupplier
 *  com.astemir.astemirlib.re.common.world.particle.ParticleEmitter
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Position
 *  net.minecraft.core.Vec3i
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.EntityBlock
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.material.PushReaction
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 */
package com.eternal_creations.archies_dino_mod.common.block;

import com.astemir.astemirlib.re.common.function.PositionSupplier;
import com.astemir.astemirlib.re.common.world.particle.ParticleEmitter;
import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TotemBlock
extends HorizontalDirectionalBlock
implements SimpleWaterloggedBlock,
EntityBlock {
    public static final int FIND_RANGE = 40;
    private static final int INTERVAL = 80;
    private static final int DURATION = 300;
    private static final VoxelShape SHAPE = Block.m_49796_((double)3.0, (double)0.0, (double)3.0, (double)13.0, (double)16.0, (double)13.0);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.f_54117_;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.f_61362_;
    public static final BooleanProperty POWERED = BlockStateProperties.f_61448_;
    private final ResourceSupplier<? extends EntityType<? extends Entity>> dinoType;
    private final Supplier<SoundEvent> sound;
    private final ResourceLocation mastery;
    private final Supplier<? extends MobEffect> effect;
    private final int amplifier;
    private final TagKey<Item> food;

    public TotemBlock(ResourceSupplier<? extends EntityType<? extends Entity>> dinoType, Supplier<SoundEvent> sound, ResourceLocation mastery, Supplier<? extends MobEffect> effect, int amplifier, TagKey<Item> food, BlockBehaviour.Properties properties) {
        super(properties);
        this.dinoType = dinoType;
        this.sound = sound;
        this.mastery = mastery;
        this.effect = effect;
        this.amplifier = amplifier;
        this.food = food;
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)FACING, (Comparable)Direction.NORTH)).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(false))).m_61124_((Property)POWERED, (Comparable)Boolean.valueOf(false)));
    }

    public TotemBlock(ResourceSupplier<? extends EntityType<? extends Entity>> dinoType, Supplier<SoundEvent> sound, String mastery, Supplier<? extends MobEffect> effect, int amplifier, TagKey<Item> food) {
        this(dinoType, sound, Constants.prefix(mastery), effect, amplifier, food, BlockBehaviour.Properties.m_284310_().m_60955_().m_60978_(0.5f).m_278166_(PushReaction.DESTROY).m_60918_(SoundType.f_56724_).m_60953_(state -> (Boolean)state.m_61143_((Property)POWERED) != false ? 5 : 0));
    }

    public ResourceSupplier<? extends EntityType<? extends Entity>> getDinoType() {
        return this.dinoType;
    }

    public Supplier<SoundEvent> getSound() {
        return this.sound;
    }

    public ResourceLocation getMastery() {
        return this.mastery;
    }

    public Supplier<? extends MobEffect> getEffect() {
        return this.effect;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public void m_6402_(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.f_46443_) {
            ADParticles.TOTEM_PLACE.get().spawnClient(level, PositionSupplier.of((Position)Vec3.m_82512_((Vec3i)pos)), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
            ADParticles.TOTEM_PLACE_PARTICLE_UNDER.get().spawnClient(level, PositionSupplier.of((Position)Vec3.m_82539_((Vec3i)pos).m_82520_(0.0, 0.05, 0.0)), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
        }
    }

    public InteractionResult m_6227_(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack;
        level.m_5594_(null, pos, this.getSound().get(), SoundSource.BLOCKS, 1.0f, 1.0f);
        if (level.f_46443_) {
            ADParticles.TOTEM_SHINE.get().spawnClient(level, PositionSupplier.of((Position)Vec3.m_82539_((Vec3i)pos)), ParticleEmitter.DEFAULT_ROTATION, 1.0f);
        }
        if (!((Boolean)state.m_61143_((Property)POWERED)).booleanValue() && !level.f_46443_ && (stack = player.m_21120_(hand)).m_204117_(this.food)) {
            if (!player.m_7500_()) {
                stack.m_41774_(1);
            }
            level.m_7731_(pos, (BlockState)state.m_61124_((Property)POWERED, (Comparable)Boolean.valueOf(true)), 3);
        }
        return InteractionResult.SUCCESS;
    }

    public VoxelShape m_5940_(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    protected void m_7926_(StateDefinition.Builder<Block, BlockState> builder) {
        builder.m_61104_(new Property[]{FACING}).m_61104_(new Property[]{WATERLOGGED}).m_61104_(new Property[]{POWERED});
    }

    @Nullable
    public BlockState m_5573_(BlockPlaceContext context) {
        return (BlockState)((BlockState)this.m_49966_().m_61124_((Property)FACING, (Comparable)context.m_8125_().m_122424_())).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(context.m_43725_().m_6425_(context.m_8083_()).m_76152_() == Fluids.f_76193_));
    }

    public BlockState m_7417_(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (((Boolean)state.m_61143_((Property)WATERLOGGED)).booleanValue()) {
            level.m_186469_(currentPos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)level));
        }
        return super.m_7417_(state, facing, facingState, level, currentPos, facingPos);
    }

    public FluidState m_5888_(BlockState state) {
        return (Boolean)state.m_61143_((Property)WATERLOGGED) != false ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
    }

    public boolean m_8133_(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.m_8133_(state, level, pos, id, param);
        BlockEntity blockentity = level.m_7702_(pos);
        return blockentity != null && blockentity.m_7531_(id, param);
    }

    @Nullable
    public BlockEntity m_142194_(BlockPos pos, BlockState state) {
        return ADBlockEntityTypes.TOTEM_BLOCK.get().m_155264_(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, BlockState state, BlockEntityType<T> type) {
        return TotemBlock.createTickerHelper(type, ADBlockEntityTypes.TOTEM_BLOCK.get(), (tickLevel, tickPos, tickState, totem) -> {
            if (tickLevel.m_46467_() % 80L == 0L && ((Boolean)tickState.m_61143_((Property)POWERED)).booleanValue()) {
                for (Player player : tickLevel.m_6907_()) {
                    if (!(player.m_20238_(Vec3.m_82512_((Vec3i)tickPos)) <= 1600.0)) continue;
                    player.m_7292_(new MobEffectInstance(this.getEffect().get(), 300, this.getAmplifier(), true, true));
                }
            }
        });
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? ticker : null;
    }
}

