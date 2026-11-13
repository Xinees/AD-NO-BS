/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.entity.ai.goal.MoveToBlockGoal
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 */
package com.eternal_creations.archies_dino_mod.common.entity.ai.goal;

import com.eternal_creations.archies_dino_mod.common.entity.base.EggLaying;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.util.ResourceSupplier;
import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class GoalLayEgg<T extends Animal>
extends MoveToBlockGoal {
    private final T animal;
    private final ResourceSupplier<Block> eggBlock;
    private final BiPredicate<LevelReader, BlockPos> predicate;
    private int layEggCounter;

    public GoalLayEgg(T animal, double speedModifier, ResourceSupplier<Block> eggBlock, BiPredicate<LevelReader, BlockPos> predicate) {
        super(animal, speedModifier, 16);
        this.animal = animal;
        this.eggBlock = eggBlock;
        this.predicate = predicate;
    }

    public boolean m_8036_() {
        return ((EggLaying)this.animal).hasEgg() && super.m_8036_();
    }

    public boolean m_8045_() {
        return super.m_8045_() && ((EggLaying)this.animal).hasEgg();
    }

    public void m_8056_() {
        super.m_8056_();
        this.layEggCounter = 0;
    }

    public void m_8041_() {
        super.m_8041_();
        this.layEggCounter = 0;
        ((EggLaying)this.animal).setLayingEgg(false);
    }

    public void m_8037_() {
        super.m_8037_();
        BlockPos blockpos = this.animal.m_20183_();
        if (!this.animal.m_20069_() && this.m_25625_()) {
            if (this.layEggCounter < 1) {
                ((EggLaying)this.animal).setLayingEgg(true);
            } else if (this.layEggCounter > this.m_183277_(200)) {
                Level level = this.animal.m_9236_();
                level.m_5594_(null, blockpos, ADSoundEvents.DINO_EGG_LAY.get(), SoundSource.BLOCKS, 0.3f, 0.9f + level.f_46441_.m_188501_() * 0.2f);
                BlockPos blockpos1 = this.f_25602_.m_7494_();
                BlockState blockstate = this.eggBlock.get().m_49966_();
                level.m_7731_(blockpos1, blockstate, 3);
                level.m_220407_(GameEvent.f_157797_, blockpos1, GameEvent.Context.m_223719_(this.animal, (BlockState)blockstate));
                ((EggLaying)this.animal).setHasEgg(false);
                ((EggLaying)this.animal).setLayingEgg(false);
                this.animal.m_27601_(600);
            }
            if (((EggLaying)this.animal).isLayingEgg()) {
                ++this.layEggCounter;
            }
        }
    }

    public double m_8052_() {
        return 4.0;
    }

    protected boolean m_6465_(LevelReader level, BlockPos pos) {
        return level.m_46859_(pos.m_7494_()) && this.predicate.test(level, pos);
    }
}

