/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityDimensions
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.entity.PartEntity
 */
package com.eternal_creations.archies_dino_mod.entity;

import com.eternal_creations.archies_dino_mod.common.entity.base.IMultipartEntity;
import com.eternal_creations.archies_dino_mod.common.entity.base.IPartEntity;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ForgeEntityPart<T extends Entity>
extends PartEntity<T>
implements IPartEntity<T> {
    private EntityDimensions size;
    private final T parent;

    public ForgeEntityPart(T parent, float width, float height) {
        super(parent);
        this.parent = parent;
        this.setBoundingBoxSize(width, height);
    }

    public void setBoundingBoxSize(float width, float height) {
        this.size = EntityDimensions.m_20395_((float)width, (float)height);
        this.m_6210_();
    }

    protected void m_8097_() {
    }

    protected void m_7378_(CompoundTag tag) {
    }

    protected void m_7380_(CompoundTag tag) {
    }

    public boolean m_6469_(DamageSource source, float amount) {
        return ((IMultipartEntity)this.getParent()).hurtFromPart(this, source, amount);
    }

    public InteractionResult m_6096_(Player player, InteractionHand hand) {
        return ((IMultipartEntity)this.getParent()).interactFromPart(this, player, hand);
    }

    public void m_20254_(int secondsOnFire) {
        this.getParent().m_20254_(secondsOnFire);
    }

    public void m_7311_(int remainingFireTicks) {
        this.getParent().m_7311_(remainingFireTicks);
    }

    public int m_20094_() {
        return this.getParent().m_20094_();
    }

    public void m_20095_() {
        this.getParent().m_20095_();
    }

    public boolean m_7306_(Entity other) {
        return this == other || this.getParent() == other;
    }

    public boolean m_6087_() {
        return this.getParent().m_6087_();
    }

    public boolean m_6097_() {
        return this.getParent().m_6097_();
    }

    @Nullable
    public ItemStack m_142340_() {
        return this.getParent().m_142340_();
    }

    public EntityDimensions m_6972_(Pose pose) {
        return this.size;
    }

    public boolean m_142391_() {
        return false;
    }

    @Override
    public T getParent() {
        return this.parent;
    }

    @Override
    public int getPartId() {
        return this.m_19879_();
    }

    @Override
    public void setPartId(int id) {
        this.m_20234_(id);
    }

    @Override
    public <P extends Entity> P asPart() {
        return (P)this;
    }

    @Override
    public void setPartPos(double x, double y, double z) {
        this.f_19854_ = this.m_20185_();
        this.f_19855_ = this.m_20186_();
        this.f_19856_ = this.m_20189_();
        this.f_19790_ = this.m_20185_();
        this.f_19791_ = this.m_20186_();
        this.f_19792_ = this.m_20189_();
        this.m_6034_(x, y, z);
    }

    @Override
    public void resize(float width, float height) {
        if (this.size.f_20377_ != width || this.size.f_20378_ != height) {
            this.setBoundingBoxSize(width, height);
        }
    }
}

