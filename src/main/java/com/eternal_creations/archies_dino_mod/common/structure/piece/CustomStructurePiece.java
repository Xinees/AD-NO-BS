/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtOps
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.util.StringRepresentable$EnumCodec
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.ServerLevelAccessor
 *  net.minecraft.world.level.StructureManager
 *  net.minecraft.world.level.WorldGenLevel
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.chunk.ChunkGenerator
 *  net.minecraft.world.level.levelgen.Heightmap$Types
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.TemplateStructurePiece
 *  net.minecraft.world.level.levelgen.structure.TerrainAdjustment
 *  net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext
 *  net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager
 *  org.slf4j.Logger
 */
package com.eternal_creations.archies_dino_mod.common.structure.piece;

import com.eternal_creations.archies_dino_mod.Constants;
import com.eternal_creations.archies_dino_mod.common.structure.piece.ADStructurePieces;
import com.eternal_creations.archies_dino_mod.common.structure.processor.BoneProcessor;
import com.eternal_creations.archies_dino_mod.common.structure.processor.CoralProcessor;
import com.eternal_creations.archies_dino_mod.common.structure.processor.CraterProcessor;
import com.eternal_creations.archies_dino_mod.common.structure.processor.LiquidSubmergedLoggedProcessor;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomStructurePiece
extends TemplateStructurePiece {
    private final VerticalPlacement verticalPlacement;
    private final Properties properties;
    protected final StructureTemplateManager structureManager;
    private final BlockPos originalPlacement;
    private final BoundingBox originalBox;

    public CustomStructurePiece(StructureTemplateManager templateManager, BlockPos pos, VerticalPlacement verticalPlacement, Properties properties, ResourceLocation location, Rotation rotation, Mirror mirror, BlockPos blockPos) {
        super(ADStructurePieces.CUSTOM_STRUCTURE_PIECE.get(), 0, templateManager, location, location.toString(), CustomStructurePiece.makeSettings(mirror, rotation, blockPos, properties), pos);
        this.verticalPlacement = verticalPlacement;
        this.properties = properties;
        this.structureManager = templateManager;
        this.originalPlacement = this.f_73658_;
        this.originalBox = CustomStructurePiece.clone(this.f_73383_);
    }

    public CustomStructurePiece(StructurePieceSerializationContext context, CompoundTag tag) {
        this(context, tag, (Properties)Properties.CODEC.parse(new Dynamic((DynamicOps)NbtOps.f_128958_, (Object)tag.m_128423_("Properties"))).getOrThrow(true, arg_0 -> ((Logger)Constants.LOG).error(arg_0)));
    }

    private CustomStructurePiece(StructurePieceSerializationContext context, CompoundTag tag, Properties properties) {
        super(ADStructurePieces.CUSTOM_STRUCTURE_PIECE.get(), tag, context.f_226956_(), location -> CustomStructurePiece.makeSettings(context.f_226956_(), tag, location, properties));
        this.verticalPlacement = VerticalPlacement.byName(tag.m_128461_("VerticalPlacement"));
        this.properties = properties;
        this.structureManager = context.f_226956_();
        this.originalPlacement = this.f_73658_;
        this.originalBox = CustomStructurePiece.clone(this.f_73383_);
    }

    protected void m_183620_(StructurePieceSerializationContext context, CompoundTag tag) {
        super.m_183620_(context, tag);
        tag.m_128359_("Rotation", this.f_73657_.m_74404_().name());
        tag.m_128359_("Mirror", this.f_73657_.m_74401_().name());
        tag.m_128359_("VerticalPlacement", this.verticalPlacement.m_7912_());
        Properties.CODEC.encodeStart((DynamicOps)NbtOps.f_128958_, (Object)this.properties).resultOrPartial(arg_0 -> ((Logger)Constants.LOG).error(arg_0)).ifPresent(tag1 -> tag.m_128365_("Properties", tag1));
    }

    private static StructurePlaceSettings makeSettings(StructureTemplateManager templateManager, CompoundTag tag, ResourceLocation location, Properties properties) {
        StructureTemplate structuretemplate = templateManager.m_230359_(location);
        BlockPos blockpos = new BlockPos(structuretemplate.m_163801_().m_123341_() / 2, 0, structuretemplate.m_163801_().m_123343_() / 2);
        return CustomStructurePiece.makeSettings(Mirror.valueOf((String)tag.m_128461_("Mirror")), Rotation.valueOf((String)tag.m_128461_("Rotation")), blockpos, properties);
    }

    private static StructurePlaceSettings makeSettings(Mirror mirror, Rotation rotation, BlockPos pos, Properties properties) {
        StructurePlaceSettings settings = new StructurePlaceSettings().m_74379_(rotation).m_74377_(mirror).m_74385_(pos).m_74383_((StructureProcessor)(properties.airPocket() ? BlockIgnoreProcessor.f_74046_ : BlockIgnoreProcessor.f_74048_)).m_163782_(properties.keepLiquids() != KeepLiquids.IGNORE_LIQUIDS).m_74383_((StructureProcessor)new ProtectedBlockProcessor(BlockTags.f_144287_));
        if (properties.keepLiquids() != KeepLiquids.IGNORE_LIQUIDS) {
            settings.m_74383_((StructureProcessor)new LiquidSubmergedLoggedProcessor(properties.keepLiquids));
        }
        switch (properties.processor()) {
            case PROCESS_CORAL: {
                settings.m_74383_((StructureProcessor)new CoralProcessor());
                break;
            }
            case PROCESS_BONE: {
                settings.m_74383_((StructureProcessor)new BoneProcessor());
                break;
            }
            case PROCESS_CRATER: {
                settings.m_74383_((StructureProcessor)new CraterProcessor());
                break;
            }
        }
        return settings;
    }

    public void m_213694_(WorldGenLevel level, StructureManager manager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
        super.m_213694_(level, manager, chunkGenerator, random, boundingBox, chunkPos, pos);
        this.f_73658_ = this.originalPlacement;
        this.f_73383_ = CustomStructurePiece.clone(this.originalBox);
        this.f_73657_.m_74381_(this.f_73383_);
    }

    private static BoundingBox clone(BoundingBox box) {
        return new BoundingBox(box.m_162395_(), box.m_162396_(), box.m_162398_(), box.m_162399_(), box.m_162400_(), box.m_162401_());
    }

    public Properties getProperties() {
        return this.properties;
    }

    protected void m_213704_(String s, BlockPos pos, ServerLevelAccessor levelAccessor, RandomSource random, BoundingBox boundingBox) {
    }

    public record Properties(boolean airPocket, KeepLiquids keepLiquids, TerrainAdjustment adjustment, int groundLevelDelta, ArchieProcessor processor) {
        public static final Codec<Properties> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)Codec.BOOL.fieldOf("air_pocket").forGetter(Properties::airPocket), (App)KeepLiquids.CODEC.fieldOf("keep_liquids").forGetter(Properties::keepLiquids), (App)TerrainAdjustment.f_226918_.fieldOf("adjustment").forGetter(Properties::adjustment), (App)Codec.INT.fieldOf("ground_level_delta").forGetter(Properties::groundLevelDelta), (App)ArchieProcessor.CODEC.fieldOf("archie_processor").forGetter(Properties::processor)).apply((Applicative)instance, Properties::new));
    }

    public static enum VerticalPlacement implements StringRepresentable
    {
        ON_LAND_SURFACE("on_land_surface", Heightmap.Types.WORLD_SURFACE_WG),
        ON_OCEAN_FLOOR("on_ocean_floor", Heightmap.Types.OCEAN_FLOOR_WG),
        IN_THE_SKY("in_the_sky", Heightmap.Types.WORLD_SURFACE_WG),
        IN_NETHER("in_nether", Heightmap.Types.WORLD_SURFACE_WG),
        BURIED("buried", Heightmap.Types.WORLD_SURFACE_WG),
        SUNKEN("sunken", Heightmap.Types.OCEAN_FLOOR_WG),
        PARTLY_BURIED("partly_buried", Heightmap.Types.OCEAN_FLOOR_WG),
        IN_MOUNTAIN("in_mountain", Heightmap.Types.WORLD_SURFACE_WG),
        UNDERGROUND("underground", Heightmap.Types.WORLD_SURFACE_WG);

        public static final StringRepresentable.EnumCodec<VerticalPlacement> CODEC;
        private final String name;
        private final Heightmap.Types heightmap;

        private VerticalPlacement(String name, Heightmap.Types heightmap) {
            this.name = name;
            this.heightmap = heightmap;
        }

        public static VerticalPlacement byName(String name) {
            return Objects.requireNonNull((VerticalPlacement)CODEC.m_216455_(name));
        }

        public String m_7912_() {
            return this.name;
        }

        public Heightmap.Types getHeightmap() {
            return this.heightmap;
        }

        static {
            CODEC = StringRepresentable.m_216439_(VerticalPlacement::values);
        }
    }

    public static enum KeepLiquids implements StringRepresentable
    {
        IGNORE_LIQUIDS,
        KEEP_LIQUIDS,
        FORCE_WATER;

        public static final StringRepresentable.EnumCodec<KeepLiquids> CODEC;

        public String m_7912_() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        static {
            CODEC = StringRepresentable.m_216439_(KeepLiquids::values);
        }
    }

    public static enum ArchieProcessor implements StringRepresentable
    {
        NONE,
        PROCESS_CORAL,
        PROCESS_BONE,
        PROCESS_CRATER;

        public static final StringRepresentable.EnumCodec<ArchieProcessor> CODEC;

        public String m_7912_() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        static {
            CODEC = StringRepresentable.m_216439_(ArchieProcessor::values);
        }
    }
}

