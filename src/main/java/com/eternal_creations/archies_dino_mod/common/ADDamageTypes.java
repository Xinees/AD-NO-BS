/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  net.minecraft.core.Holder
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.worldgen.BootstapContext
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.damagesource.DamageType
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.Level
 *  org.lwjgl.system.NonnullDefault
 */
package com.eternal_creations.archies_dino_mod.common;

import com.eternal_creations.archies_dino_mod.Constants;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class ADDamageTypes {
    public static final ResourceKey<DamageType> PUS_ARROW = ADDamageTypes.create("pus_arrow");

    protected static ResourceKey<DamageType> create(String name) {
        return ResourceKey.m_135785_((ResourceKey)Registries.f_268580_, (ResourceLocation)Constants.prefix(name));
    }

    public static DamageSource getDamageSource(Level level, ResourceKey<DamageType> type) {
        return ADDamageTypes.getEntityDamageSource(level, type, null);
    }

    public static DamageSource getEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker) {
        return ADDamageTypes.getIndirectEntityDamageSource(level, type, attacker, attacker);
    }

    public static DamageSource getIndirectEntityDamageSource(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker, @Nullable Entity indirectAttacker) {
        return new DamageSource((Holder)level.m_9598_().m_175515_(Registries.f_268580_).m_246971_(type), attacker, indirectAttacker);
    }

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.m_255272_(PUS_ARROW, (Object)new DamageType(ADDamageTypes.named("pusArrow"), 0.0f));
    }

    protected static String named(String name) {
        return "ec_ad." + name;
    }
}

