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

package org.ayamemc.ayamepaperdoll.config;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayamepaperdoll.hud.PaperDollRenderer;
import org.lwjgl.glfw.GLFW;

import static org.ayamemc.ayamepaperdoll.AyamePaperDoll.CONFIGS;

public class VisualConfigEditorScreen extends Screen {
    private final Screen lastScreen;
    private final PaperDollRenderer paperDollRenderer = PaperDollRenderer.getInstance();

    protected VisualConfigEditorScreen(Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
    }


    @SuppressWarnings("DataFlowIssue")
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (minecraft.level != null) {
            this.renderBlurredBackground();
        }
        paperDollRenderer.render(partialTick, guiGraphics);
    }


    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onClose() {
        this.minecraft.setScreen(lastScreen);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        boolean onDrag = false;
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            CONFIGS.offsetX.setValue(CONFIGS.offsetX.getValue() + (deltaX * 0.001));
            CONFIGS.offsetY.setValue(CONFIGS.offsetY.getValue() + (deltaY * 0.001));
            onDrag = true;
        }
        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            CONFIGS.rotationY.setValue(CONFIGS.rotationY.getValue() + deltaX);
            onDrag = true;
        }
        return onDrag;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY != 0) {
            CONFIGS.size.setValue(CONFIGS.size.getValue() + (scrollY / 80));
            return true;
        }
        return false;
    }

}
