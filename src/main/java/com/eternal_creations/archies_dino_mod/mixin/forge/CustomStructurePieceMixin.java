/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.ParametersAreNonnullByDefault
 *  net.minecraft.MethodsReturnNonnullByDefault
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.levelgen.structure.BoundingBox
 *  net.minecraft.world.level.levelgen.structure.TemplateStructurePiece
 *  net.minecraft.world.level.levelgen.structure.TerrainAdjustment
 *  net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager
 *  net.minecraftforge.common.world.PieceBeardifierModifier
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 */
package com.eternal_creations.archies_dino_mod.mixin.forge;

import com.eternal_creations.archies_dino_mod.common.structure.piece.CustomStructurePiece;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraftforge.common.world.PieceBeardifierModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mixin(remap=false, value={CustomStructurePiece.class})
public abstract class CustomStructurePieceMixin
extends TemplateStructurePiece
implements PieceBeardifierModifier {
    @Shadow
    @Final
    private CustomStructurePiece.Properties properties;

    public CustomStructurePieceMixin(StructurePieceType type, int genDepth, StructureTemplateManager structureTemplateManager, ResourceLocation location, String templateName, StructurePlaceSettings placeSettings, BlockPos templatePosition) {
        super(type, genDepth, structureTemplateManager, location, templateName, placeSettings, templatePosition);
    }

    public BoundingBox getBeardifierBox() {
        return this.m_73547_();
    }

    public TerrainAdjustment getTerrainAdjustment() {
        return this.properties.adjustment();
    }

    public int getGroundLevelDelta() {
        return this.properties.groundLevelDelta();
    }
}

