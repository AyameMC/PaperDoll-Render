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
import org.ayamemc.ayamepaperdoll.mixininterface.GuiGraphicsMixinInterface;
import org.lwjgl.glfw.GLFW;

import java.awt.geom.Rectangle2D;

import static org.ayamemc.ayamepaperdoll.AyamePaperDoll.CONFIGS;

public class VisualConfigEditorScreen extends Screen {
    private final Screen lastScreen;
    private final PaperDollRenderer paperDollRenderer = PaperDollRenderer.getInstance();

    private static final int LINE_COLOR = 0xA6_FFFFFF;
    private static final int BORDER_MARGIN = 10;

    protected VisualConfigEditorScreen(Screen lastScreen) {
        super(Component.empty());
        this.lastScreen = lastScreen;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 十字的水平线，- 1能居中点，大概
        guiGraphics.hLine(0, width, (height / 2) - 1, LINE_COLOR);
        // 十字的垂直线
        guiGraphics.vLine((width / 2), -1, height, LINE_COLOR);

        // 底下的线
        guiGraphics.hLine(0, width, (height - BORDER_MARGIN), LINE_COLOR);
        // 顶上的线
        guiGraphics.hLine(0, width, BORDER_MARGIN, LINE_COLOR);
        // 左边的线
        guiGraphics.vLine((width - BORDER_MARGIN), -1, height, LINE_COLOR);
        // 右边的线
        guiGraphics.vLine(BORDER_MARGIN, -1, height, LINE_COLOR);
        paperDollRenderer.render(partialTick, guiGraphics);
        // follow convention in LayeredDrawer#renderInternal
        guiGraphics.pose().translate(0, 0, 200);
        GuiGraphicsMixinInterface mixinedGuiGraphics = (GuiGraphicsMixinInterface) guiGraphics;
        Rectangle2D.Double bounds = paperDollRenderer.getRenderBounds();
        if (bounds != null) {
            int color = 0x80FF0000;
            mixinedGuiGraphics.ayame_PaperDoll$fill((float) bounds.x, (float) bounds.y, (float) (bounds.x + bounds.width), (float) (bounds.y + bounds.height), color);
        }
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

//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
//        if (scrollY != 0) {
//            CONFIGS.size.setValue(CONFIGS.size.getValue() + (scrollY / 80));
//            return true;
//        }
//        return false;
//    }

}
