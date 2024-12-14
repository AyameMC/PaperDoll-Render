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

package org.ayamemc.ayamepaperdoll.handler;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.ayamemc.ayamepaperdoll.AyamePaperDoll;
import org.ayamemc.ayamepaperdoll.config.ConfigScreen;
import org.ayamemc.ayamepaperdoll.hud.ExtraPlayerHud;

import static org.ayamemc.ayamepaperdoll.AyamePaperDoll.CONFIGS;

public class EventHandler {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final ExtraPlayerHud extraPlayerHud = new ExtraPlayerHud(minecraft);
    public static Screen lastScreen;

    public static void renderPaperDoll(GuiGraphics guiGraphics, DeltaTracker partialTick) {
        if (
                !minecraft.options.hideGui &&
                !(AyamePaperDoll.CONFIGS.hideUnderDebug.getValue() && minecraft.getDebugOverlay().showDebugScreen())
                && (minecraft.screen == null || !CONFIGS.hideOnScreenOpen.getValue())
        ) {
            extraPlayerHud.render(partialTick.getGameTimeDeltaPartialTick(true), guiGraphics);
        }
        // follow convention in LayeredDrawer#renderInternal
        guiGraphics.pose().translate(0, 0, 200);
    }

    public static void keyPressed() {
        while (AyamePaperDoll.SHOW_PAPERDOLL_KEY.consumeClick()) {
            CONFIGS.enabled.setValue(!CONFIGS.enabled.getValue());
        }
        while (AyamePaperDoll.OPEN_CONFIG_GUI.consumeClick()) {
            minecraft.setScreen(new ConfigScreen(lastScreen, AyamePaperDoll.CONFIGS.getOptions()));
        }
    }
}
