/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.data.PackOutput
 *  net.minecraft.data.loot.LootTableProvider
 *  net.minecraft.data.loot.LootTableProvider$SubProviderEntry
 *  net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
 */
package com.eternal_creations.archies_dino_mod.data.gen;

import com.eternal_creations.archies_dino_mod.data.gen.loot.ArchiesBlockLootGen;
import com.eternal_creations.archies_dino_mod.data.gen.loot.ArchiesEntityLootGen;
import java.util.List;
import java.util.Set;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class ArchiesLootGen
extends LootTableProvider {
    public ArchiesLootGen(PackOutput output) {
        super(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ArchiesBlockLootGen::new, LootContextParamSets.f_81421_), new LootTableProvider.SubProviderEntry(ArchiesEntityLootGen::new, LootContextParamSets.f_81415_)));
    }
}

