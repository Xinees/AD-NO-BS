/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance
 *  net.minecraft.advancements.critereon.ContextAwarePredicate
 *  net.minecraft.advancements.critereon.DeserializationContext
 *  net.minecraft.advancements.critereon.EntityPredicate
 *  net.minecraft.advancements.critereon.EntityPredicate$Builder
 *  net.minecraft.advancements.critereon.SerializationContext
 *  net.minecraft.advancements.critereon.SimpleCriterionTrigger
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.storage.loot.LootContext
 */
package com.eternal_creations.archies_dino_mod.common.trigger;

import com.eternal_creations.archies_dino_mod.Constants;
import com.google.gson.JsonObject;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpottedEntityTrigger
extends SimpleCriterionTrigger<SpottedEntityTrigger.TriggerInstance> {
    static final ResourceLocation ID = Constants.prefix("spotted_entity");

    public ResourceLocation m_7295_() {
        return ID;
    }

    public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext deserializationContext) {
        ContextAwarePredicate contextawarepredicate = EntityPredicate.m_285855_((JsonObject)json, (String)"entity", (DeserializationContext)deserializationContext);
        return new TriggerInstance(predicate, contextawarepredicate);
    }

    public void trigger(ServerPlayer player, Entity entity) {
        LootContext lootcontext = EntityPredicate.m_36616_((ServerPlayer)player, (Entity)entity);
        this.m_66234_(player, instance -> instance.matches(lootcontext));
    }

    public static class TriggerInstance
    extends AbstractCriterionTriggerInstance {
        private final ContextAwarePredicate entity;

        public TriggerInstance(ContextAwarePredicate player, ContextAwarePredicate entity) {
            super(ID, player);
            this.entity = entity;
        }

        public static TriggerInstance spottedEntity(EntityPredicate.Builder entityPredicateBuilder) {
            return new TriggerInstance(ContextAwarePredicate.f_285567_, EntityPredicate.m_285787_((EntityPredicate)entityPredicateBuilder.m_36662_()));
        }

        public boolean matches(LootContext lootContext) {
            return this.entity.m_285831_(lootContext);
        }

        public JsonObject m_7683_(SerializationContext conditions) {
            JsonObject jsonobject = super.m_7683_(conditions);
            jsonobject.add("entity", this.entity.m_286026_(conditions));
            return jsonobject;
        }
    }
}

