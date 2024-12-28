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

package org.ayamemc.ayamepaperdoll.mixininterface;

import net.minecraft.client.renderer.RenderType;

public interface GuiGraphicsMixinInterface {
    /**
     * Fills a rectangle with the specified color using the given coordinates as the boundaries.
     *
     * @param minX  the minimum x-coordinate of the rectangle.
     * @param minY  the minimum y-coordinate of the rectangle.
     * @param maxX  the maximum x-coordinate of the rectangle.
     * @param maxY  the maximum y-coordinate of the rectangle.
     * @param color the color to fill the rectangle with.
     */
    void ayame_PaperDoll$fill(float minX, float minY, float maxX, float maxY, int color);
    /**
     * Fills a rectangle with the specified color and z-level using the given coordinates as the boundaries.
     *
     * @param minX  the minimum x-coordinate of the rectangle.
     * @param minY  the minimum y-coordinate of the rectangle.
     * @param maxX  the maximum x-coordinate of the rectangle.
     * @param maxY  the maximum y-coordinate of the rectangle.
     * @param z     the z-level of the rectangle.
     * @param color the color to fill the rectangle with.
     */
    void ayame_PaperDoll$fill(float minX, float minY, float maxX, float maxY, float z, int color);
    /**
     * Fills a rectangle with the specified color and z-level using the given render type and coordinates as the boundaries.
     *
     * @param renderType the render type to use.
     * @param minX       the minimum x-coordinate of the rectangle.
     * @param minY       the minimum y-coordinate of the rectangle.
     * @param maxX       the maximum x-coordinate of the rectangle.
     * @param maxY       the maximum y-coordinate of the rectangle.
     * @param z          the z-level of the rectangle.
     * @param color      the color to fill the rectangle with.
     */
    void ayame_PaperDoll$fill(RenderType renderType, float minX, float minY, float maxX, float maxY, float z, int color);

}
