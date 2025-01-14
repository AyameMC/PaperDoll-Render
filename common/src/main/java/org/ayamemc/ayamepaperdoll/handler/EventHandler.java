/*
 *     Highly configurable PaperDoll mod. Forked from Extra Player Renderer.
 *     Copyright (C) 2024-2025  LucunJi(Original author), HappyRespawnanchor
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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.ayamemc.ayamepaperdoll.AyamePaperDoll;
import org.ayamemc.ayamepaperdoll.config.ConfigScreen;
import org.ayamemc.ayamepaperdoll.config.VisualConfigEditorScreen;
import org.ayamemc.ayamepaperdoll.hud.PaperDollRenderer;

import static org.ayamemc.ayamepaperdoll.AyamePaperDoll.CONFIGS;

public class EventHandler {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final PaperDollRenderer paperDollRenderer = PaperDollRenderer.getInstance();
    public static Screen lastScreen;

    @SuppressWarnings("DataFlowIssue")
    public static void renderPaperDoll(GuiGraphics guiGraphics, DeltaTracker partialTick) {
        final Player player = minecraft.player;
        final Pose playerPose = player.getPose();
        if (
                !minecraft.options.hideGui &&
                        !(AyamePaperDoll.CONFIGS.hideUnderDebug.getValue() && minecraft.getDebugOverlay().showDebugScreen()) &&
                        (minecraft.screen == null || !CONFIGS.hideOnScreenOpen.getValue()) &&
                        !(minecraft.screen instanceof ConfigScreen) &&
                        !(minecraft.screen instanceof VisualConfigEditorScreen) &&
                        hasBedrockLikeAction(player, playerPose)


        ) {
            paperDollRenderer.renderWithTranslate(partialTick.getGameTimeDeltaPartialTick(true), guiGraphics);
        }
    }

    private static long lastInactiveTime = 0;
    private static boolean previousState = false;

    public static boolean hasBedrockLikeAction(Player player, Pose playerPose) {
        boolean currentState = playerPose == Pose.SWIMMING ||
                playerPose == Pose.CROUCHING ||
                player.getAbilities().flying ||
                player.isSprinting() ||
                playerPose == Pose.FALL_FLYING ||
                player.ayame_paperdoll$isSitting();

        if (currentState) {
            // 状态为 true 时立即返回 true，并重置时间戳
            previousState = true;
            lastInactiveTime = 0;
        } else {
            // 如果状态从 true 变为 false，记录当前时间戳
            if (previousState && lastInactiveTime == 0) {
                lastInactiveTime = System.currentTimeMillis();
            }

            // 检查是否超过 1 秒延迟
            if (lastInactiveTime > 0 && System.currentTimeMillis() - lastInactiveTime >= 500) {
                previousState = false;
                lastInactiveTime = 0; // 重置时间戳
            }
        }

        return previousState;
    }


    public static void keyPressed() {
        while (AyamePaperDoll.SHOW_PAPERDOLL_KEY.consumeClick()) {
            CONFIGS.displayPaperDoll.setValue(!CONFIGS.displayPaperDoll.getValue());
        }
        while (AyamePaperDoll.OPEN_CONFIG_GUI.consumeClick()) {
            minecraft.setScreen(new ConfigScreen(lastScreen, AyamePaperDoll.CONFIGS.getOptions()));
        }
    }
}
