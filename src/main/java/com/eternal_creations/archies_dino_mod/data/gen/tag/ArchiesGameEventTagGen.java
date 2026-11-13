/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.HolderLookup$Provider
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.tags.GameEventTagsProvider
 *  net.minecraft.tags.GameEventTags
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraftforge.common.data.ExistingFileHelper
 *  org.jetbrains.annotations.Nullable
 */
package com.eternal_creations.archies_dino_mod.data.gen.tag;

import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.ADTags;
import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.tags.GameEventTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ArchiesGameEventTagGen
extends GameEventTagsProvider {
    public ArchiesGameEventTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, "ec_ad", existingFileHelper);
    }

    protected void m_6577_(HolderLookup.Provider provider) {
        this.m_206424_(GameEventTags.f_144302_).m_255179_((Object[])new GameEvent[]{ADGameEvents.PUS_ARROW_AGGRO.get(), ADGameEvents.SCULKSAUR_ARMOR_DISTRACTION.get(), ADGameEvents.TRACKER_AGGRO.get()});
        this.m_206424_(GameEventTags.f_215853_).m_255179_((Object[])new GameEvent[]{ADGameEvents.PUS_ARROW_AGGRO.get(), ADGameEvents.SCULKSAUR_ARMOR_DISTRACTION.get(), ADGameEvents.TRACKER_AGGRO.get()});
        this.m_206424_(ADTags.GameEvents.SCULKSAUR_CAN_LISTEN).addTags(new TagKey[]{GameEventTags.f_215853_});
        this.m_206424_(ADTags.GameEvents.SCULKRAPTOR_WILD_CAN_LISTEN).addTags(new TagKey[]{GameEventTags.f_215853_}).m_255245_((Object)ADGameEvents.SCULKRAPTOR_NEST_AGGRO.get());
        this.m_206424_(ADTags.GameEvents.SCULKRAPTOR_TAMED_CAN_LISTEN).m_255245_((Object)ADGameEvents.PUS_ARROW_AGGRO.get());
        this.m_206424_(ADTags.GameEvents.SCULKRAPTOR_IGNORE_COOLDOWN).m_255179_((Object[])new GameEvent[]{ADGameEvents.SCULKRAPTOR_NEST_AGGRO.get(), ADGameEvents.PUS_ARROW_AGGRO.get()});
    }
}

