package com.eternal_creations.archies_dino_mod.client.animation.player;

import com.eternal_creations.archies_dino_mod.client.animation.player.attachable.LushsaurMaceAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnChorusoatlPlayerAnimations;
import com.eternal_creations.archies_dino_mod.client.animation.player.riding.OnLushsaurPlayerAnimations;

public class ADPlayerAnimations {
    public static void init() {
        OnLushsaurPlayerAnimations.init(1);
        OnChorusoatlPlayerAnimations.init(1);
        LushsaurMaceAnimations.init(2);
    }
}
