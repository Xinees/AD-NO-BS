/*
 * Decompiled with CFR 0.152.
 */
package com.eternal_creations.archies_dino_mod.common.trigger;

import com.eternal_creations.archies_dino_mod.common.trigger.SpottedEntityTrigger;
import com.eternal_creations.archies_dino_mod.mixin.CriteriaTriggersAccessor;

public class ADCriteriaTriggers {
    public static final SpottedEntityTrigger SPOTTED_ENTITY = CriteriaTriggersAccessor.invokeRegister(new SpottedEntityTrigger());

    public static void init() {
    }
}

