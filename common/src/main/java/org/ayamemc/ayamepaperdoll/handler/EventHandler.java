/*
 *     Highly configurable paper doll mod, well integrated with Ayame.
 *     Copyright (C) 2024  LucunJi(Original author), HappyRespawnanchor, pertaz(Port to Architectury)
 *
 *     This file is part of PaperDoll Render.
 *
 *     PaperDoll Render is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     PaperDoll Render is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with PaperDoll Render.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayamepaperdoll.handler;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.ayamemc.ayamepaperdoll.AyamePaperDoll;
import org.ayamemc.ayamepaperdoll.hud.ExtraPlayerHud;

public class EventHandler {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final ExtraPlayerHud extraPlayerHud = new ExtraPlayerHud(minecraft);

    public static void renderPaperDoll(GuiGraphics guiGraphics, DeltaTracker partialTick) {
        if (!minecraft.options.hideGui
                && !(AyamePaperDoll.CONFIGS.hideUnderDebug.getValue() && minecraft.getDebugOverlay().showDebugScreen())
                && minecraft.screen == null) {
            extraPlayerHud.render(partialTick.getGameTimeDeltaPartialTick(true));
        }
        // follow convention in LayeredDrawer#renderInternal
        guiGraphics.pose().translate(0, 0, 200);
    }

}
