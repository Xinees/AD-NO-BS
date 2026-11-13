/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 */
package com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc;

import com.eternal_creations.archies_dino_mod.client.screen.guidebook.ui.misc.Rect2f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.List;

public class TransformStack {
    private final List<TransformOperation> operations = new ArrayList<TransformOperation>();
    private int size = 0;

    public void reset() {
        this.size = 0;
    }

    public boolean isIdentity() {
        return this.size == 0;
    }

    private TransformOperation nextOperation(TransformType type) {
        if (this.size >= this.operations.size()) {
            this.operations.add(new TransformOperation());
        }
        TransformOperation transformOperation = this.operations.get(this.size++);
        transformOperation.type = type;
        return transformOperation;
    }

    public TransformStack translate(float dx, float dy) {
        if (dx != 0.0f || dy != 0.0f) {
            TransformOperation transformOperation = this.nextOperation(TransformType.TRANSLATE);
            transformOperation.x = dx;
            transformOperation.y = dy;
        }
        return this;
    }

    public TransformStack scale(float scale, float px, float py) {
        if (scale != 1.0f) {
            TransformOperation transformOperation = this.nextOperation(TransformType.SCALE);
            transformOperation.x = scale;
            transformOperation.y = scale;
            transformOperation.px = px;
            transformOperation.py = py;
        }
        return this;
    }

    public TransformStack scale(float sx, float sy, float px, float py) {
        if (sx != 1.0f || sy != 1.0f) {
            TransformOperation transformOperation = this.nextOperation(TransformType.SCALE);
            transformOperation.x = sx;
            transformOperation.y = sy;
            transformOperation.px = px;
            transformOperation.py = py;
        }
        return this;
    }

    public TransformStack rotate(float degrees, float px, float py) {
        if (degrees != 0.0f) {
            TransformOperation transformOperation = this.nextOperation(TransformType.ROTATE);
            transformOperation.z = degrees;
            transformOperation.px = px;
            transformOperation.py = py;
        }
        return this;
    }

    public void apply(PoseStack pose, Rect2f bounds) {
        block5: for (int i = 0; i < this.size; ++i) {
            TransformOperation transformOperation = this.operations.get(i);
            switch (transformOperation.type) {
                case TRANSLATE: {
                    pose.m_252880_(transformOperation.x, transformOperation.y, 0.0f);
                    continue block5;
                }
                case SCALE: {
                    pose.m_252880_(transformOperation.px, transformOperation.py, 0.0f);
                    pose.m_85841_(transformOperation.x, transformOperation.y, 1.0f);
                    pose.m_252880_(-transformOperation.px, -transformOperation.py, 0.0f);
                    continue block5;
                }
                case ROTATE: {
                    pose.m_252880_(transformOperation.px, transformOperation.py, 0.0f);
                    pose.m_252781_(Axis.f_252403_.m_252977_(transformOperation.z));
                    pose.m_252880_(-transformOperation.px, -transformOperation.py, 0.0f);
                }
            }
        }
    }

    private static class TransformOperation {
        TransformType type;
        float x;
        float y;
        float z;
        float px;
        float py;

        private TransformOperation() {
        }
    }

    private static enum TransformType {
        TRANSLATE,
        SCALE,
        ROTATE;

    }
}

