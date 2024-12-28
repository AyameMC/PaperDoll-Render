/*
 *     Highly configurable PaperDoll mod. Forked from Extra Player Renderer.
 *     Copyright (C) 2024  LucunJi(Original author), HappyRespawnanchor
 *
 *     This file is part of Ayame PaperDoll.
 *
 *     Ayame PaperDoll is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Ayame PaperDoll is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Ayame PaperDoll.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayamepaperdoll.mixin.patch;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.ayamemc.ayamepaperdoll.mixininterface.GuiGraphicsMixinInterface;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements GuiGraphicsMixinInterface {

    @Shadow
    @Final
    private MultiBufferSource.BufferSource bufferSource;

    @Shadow
    @Final
    private PoseStack pose;


    @Override
    public void ayame_PaperDoll$fill(float minX, float minY, float maxX, float maxY, int color) {
        this.ayame_PaperDoll$fill(minX, minY, maxX, maxY, 0, color);
    }


    @Override
    public void ayame_PaperDoll$fill(float minX, float minY, float maxX, float maxY, float z, int color) {
        this.ayame_PaperDoll$fill(RenderType.gui(), minX, minY, maxX, maxY, z, color);
    }


    @Override
    public void ayame_PaperDoll$fill(RenderType renderType, float minX, float minY, float maxX, float maxY, float z, int color) {
        Matrix4f matrix4f = this.pose.last().pose();
        if (minX < maxX) {
            float i = minX;
            minX = maxX;
            maxX = i;
        }

        if (minY < maxY) {
            float i = minY;
            minY = maxY;
            maxY = i;
        }

        VertexConsumer vertexConsumer = this.bufferSource.getBuffer(renderType);
        vertexConsumer.addVertex(matrix4f, minX, minY, z).setColor(color);
        vertexConsumer.addVertex(matrix4f, minX, maxY, z).setColor(color);
        vertexConsumer.addVertex(matrix4f, maxX, maxY, z).setColor(color);
        vertexConsumer.addVertex(matrix4f, maxX, minY, z).setColor(color);
    }
}
