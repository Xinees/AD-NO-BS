package com.eternal_creations.archies_dino_mod;

import com.eternal_creations.archies_dino_mod.common.ADGameEvents;
import com.eternal_creations.archies_dino_mod.common.block.ADBlocks;
import com.eternal_creations.archies_dino_mod.common.block.entity.ADBlockEntityTypes;
import com.eternal_creations.archies_dino_mod.common.effect.ADMobEffects;
import com.eternal_creations.archies_dino_mod.common.entity.ADActionTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADActivities;
import com.eternal_creations.archies_dino_mod.common.entity.ADEntityTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADMemoryModuleTypes;
import com.eternal_creations.archies_dino_mod.common.entity.ADSensorTypes;
import com.eternal_creations.archies_dino_mod.common.items.ADItems;
import com.eternal_creations.archies_dino_mod.common.particle.ADParticles;
import com.eternal_creations.archies_dino_mod.common.sound.ADSoundEvents;
import com.eternal_creations.archies_dino_mod.common.tab.ADCreativeTabs;

public class CommonClass {
    public static void init() {
        ADActionTypes.init();
        ADSoundEvents.init();
        ADBlocks.init();
        ADBlockEntityTypes.init();
        ADEntityTypes.init();
        ADParticles.init();
        ADItems.init();
        ADCreativeTabs.init();
        ADMobEffects.init();
        ADGameEvents.init();
        ADSensorTypes.init();
        ADActivities.init();
        ADMemoryModuleTypes.init();
    }
}
