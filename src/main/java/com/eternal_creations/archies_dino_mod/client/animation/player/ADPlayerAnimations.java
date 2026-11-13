/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.client.animation.player;

import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.ChorusScytheAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.LushsaurMaceAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.RibBowAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.ScorchingHornAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.SculkBowAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.TherimossClawsAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnChorusoatlPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnCoralosaurPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnLushsaurPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnObsidiceratopsPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnTherimossPlayerAnimations;

public class ADPlayerAnimations {
    public static void init() {
        OnLushsaurPlayerAnimations.init(1);
        OnChorusoatlPlayerAnimations.init(1);
        OnObsidiceratopsPlayerAnimations.init(1);
        OnCoralosaurPlayerAnimations.init(1);
        OnTherimossPlayerAnimations.init(1);
        ChorusScytheAnimations.init(2);
        LushsaurMaceAnimations.init(2);
        RibBowAnimations.init(2);
        SculkBowAnimations.init(2);
        TherimossClawsAnimations.init(2);
        ScorchingHornAnimations.init(2);
    }
}

