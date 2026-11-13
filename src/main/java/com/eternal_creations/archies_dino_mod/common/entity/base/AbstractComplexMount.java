/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  net.minecraft.network.syncher.EntityDataSerializers
 *  net.minecraft.network.syncher.SynchedEntityData
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.world.Container
 *  net.minecraft.world.ContainerListener
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.SlotAccess
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.EnchantmentHelper
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3d
 */
package com.eternal_creations.archies_dino_mod.common.entity.base;

import com.eternal_creations.archies_dino_mod.common.entity.base.AbstractComplexMob;
import com.eternal_creations.archies_dino_mod.common.entity.base.Mount;
import com.eternal_creations.archies_dino_mod.common.entity.component.MountComponent;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractComplexMount
extends AbstractComplexMob
implements Mount {
    private static final EntityDataAccessor<Boolean> DATA_MOB_SADDLE = SynchedEntityData.m_135353_(AbstractComplexMount.class, (EntityDataSerializer)EntityDataSerializers.f_135035_);
    private final MountComponent<?> mountComponent;
    private final Vector3d cameraPosition = new Vector3d();
    protected SimpleContainer inventory;

    protected AbstractComplexMount(EntityType<? extends AbstractComplexMob> entityType, Level level) {
        super(entityType, level);
        this.mountComponent = this.componentHolder.addComponent(this.createMount());
        this.createInventory();
    }

    @Override
    public void m_5757_(Container container) {
        boolean bl = this.m_6254_();
        this.updateContainerEquipment();
        if (this.f_19797_ > 20 && !bl && this.m_6254_()) {
            this.m_5496_(this.m_246265_(), 0.5f, 1.0f);
        }
    }

    public SoundEvent m_246265_() {
        return ADSoundEvents.DINO_EQUIP.get();
    }

    public boolean m_6741_() {
        return this.m_6084_() && !this.m_6162_() && this.isTamed();
    }

    public SimpleContainer getInventory() {
        return this.inventory;
    }

    protected abstract MountComponent<?> createMount();

    @Override
    protected void m_8097_() {
        super.m_8097_();
        this.f_19804_.m_135372_(DATA_MOB_SADDLE, (Object)false);
    }

    @Nullable
    public LivingEntity m_6688_() {
        Player player;
        if (!this.m_6254_()) {
            return null;
        }
        Entity entity = this.m_146895_();
        return entity instanceof Player ? (player = (Player)entity) : null;
    }

    protected float m_6108_() {
        return !this.m_6069_() && this.m_20096_() ? 0.995f : super.m_6108_();
    }

    protected boolean canFloatWhileMounted() {
        return !this.m_6162_();
    }

    public double m_20204_() {
        return this.f_19862_ || this.f_185931_ ? super.m_20204_() : (double)this.m_20206_() * 0.34;
    }

    @Override
    protected void m_274498_(Player player, Vec3 motion) {
        super.m_274498_(player, motion);
        if (this.canFloatWhileMounted()) {
            if (this.m_20069_() && this.m_204036_(FluidTags.f_13131_) > this.m_20204_()) {
                if (this.m_217043_().m_188501_() < 0.8f) {
                    this.m_20256_(this.m_20184_().m_82520_(0.0, (double)0.04f, 0.0));
                }
            } else if (this.m_20077_() && !this.m_203441_(this.m_9236_().m_6425_(this.m_20183_())) && this.m_204036_(FluidTags.f_13132_) > this.m_20204_() && this.m_217043_().m_188501_() < 0.8f) {
                this.m_20256_(this.m_20184_().m_82520_(0.0, (double)0.04f, 0.0));
            }
        }
    }

    @Override
    public InteractionResult m_6071_(Player player, InteractionHand hand) {
        ItemStack itemStack = player.m_21120_(hand);
        if (itemStack.m_150930_(Items.f_42751_)) {
            if (!this.m_9236_().f_46443_) {
                this.pet().tame(player);
                this.f_21344_.m_26573_();
                this.m_6710_(null);
                this.m_9236_().m_7605_((Entity)this, (byte)7);
                this.setSaddled(!player.m_36341_());
                this.m_9236_().m_6269_(null, (Entity)this, this.m_246265_(), SoundSource.NEUTRAL, 0.5f, 1.0f);
            }
            return InteractionResult.m_19078_((boolean)this.m_9236_().f_46443_);
        }
        return this.componentHolder.mobInteract(player, hand);
    }

    @Override
    public void setSaddled(boolean saddled) {
        this.inventory.m_6836_(0, saddled ? new ItemStack((ItemLike)Items.f_42450_) : ItemStack.f_41583_);
    }

    public boolean m_6254_() {
        return (Boolean)this.f_19804_.m_135370_(DATA_MOB_SADDLE);
    }

    @Override
    public boolean canMountRotateBody() {
        return this.canRotate();
    }

    @Override
    public boolean canMountRotateHead() {
        return this.canRotate();
    }

    @Override
    public Vector3d getCameraPosition() {
        return this.cameraPosition;
    }

    @Override
    public void setCameraPosition(float x, float y, float z) {
        this.cameraPosition.set((double)x, (double)y, (double)z);
    }

    @Override
    public MountComponent<?> mount() {
        return this.mountComponent;
    }

    protected int getInventorySize() {
        return 1;
    }

    protected void createInventory() {
        SimpleContainer simpleContainer = this.inventory;
        this.inventory = new SimpleContainer(this.getInventorySize());
        if (simpleContainer != null) {
            simpleContainer.m_19181_((ContainerListener)this);
            int i = Math.min(simpleContainer.m_6643_(), this.inventory.m_6643_());
            for (int j = 0; j < i; ++j) {
                ItemStack itemStack = simpleContainer.m_8020_(j);
                if (itemStack.m_41619_()) continue;
                this.inventory.m_6836_(j, itemStack.m_41777_());
            }
        }
        this.inventory.m_19164_((ContainerListener)this);
        this.updateContainerEquipment();
    }

    protected void updateContainerEquipment() {
        if (!this.m_9236_().f_46443_) {
            this.f_19804_.m_135381_(DATA_MOB_SADDLE, (Object)(!this.inventory.m_8020_(0).m_41619_() ? 1 : 0));
        }
    }

    @Override
    protected void m_5907_() {
        super.m_5907_();
        if (this.inventory != null) {
            for (int i = 0; i < this.inventory.m_6643_(); ++i) {
                ItemStack itemStack = this.inventory.m_8020_(i);
                if (itemStack.m_41619_() || EnchantmentHelper.m_44924_((ItemStack)itemStack)) continue;
                this.m_19983_(itemStack);
            }
        }
    }

    protected SlotAccess createEquipmentSlotAccess(final int slot, final Predicate<ItemStack> stackFilter) {
        return new SlotAccess(){

            public ItemStack m_142196_() {
                return AbstractComplexMount.this.inventory.m_8020_(slot);
            }

            public boolean m_142104_(ItemStack carried) {
                if (!stackFilter.test(carried)) {
                    return false;
                }
                AbstractComplexMount.this.inventory.m_6836_(slot, carried);
                AbstractComplexMount.this.updateContainerEquipment();
                return true;
            }
        };
    }

    public SlotAccess m_141942_(int slot) {
        int i = slot - 400;
        if (i == 0 && i < this.inventory.m_6643_()) {
            return this.createEquipmentSlotAccess(i, itemStack -> itemStack.m_41619_() || itemStack.m_150930_(Items.f_42450_));
        }
        int j = slot - 500 + this.getInventorySize();
        return j >= this.getInventorySize() && j < this.inventory.m_6643_() ? SlotAccess.m_147292_((Container)this.inventory, (int)j) : super.m_141942_(slot);
    }
}

