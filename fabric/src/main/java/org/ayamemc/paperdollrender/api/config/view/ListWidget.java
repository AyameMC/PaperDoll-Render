/*
 *     Highly configurable paper doll mod, well integrated with Ayame.
 *     Copyright (C) 2024  LucunJi(Original author), CrystalNeko, HappyRespawnanchor
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

package org.ayamemc.paperdollrender.api.config.view;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.resources.ResourceLocation;

import static org.ayamemc.paperdollrender.PaperDollRender.id;

public class ListWidget extends ContainerObjectSelectionList<ListWidget.ListEntry> implements Retextured {

    private int rowWidth;

    public ListWidget(int width, int height, int top, int entryHeight) {
        super(Minecraft.getInstance(), width, height, top, entryHeight);
        this.rowWidth = super.getRowWidth();
    }

    @Override
    public int addEntry(ListEntry entry) {
        return super.addEntry(entry);
    }

    @Override
    public int getRowWidth() {
        return this.rowWidth;
    }

    public void setRowWidth(int rowWidth) {
        this.rowWidth = rowWidth;
    }

    @Override
    public ResourceLocation retexture(ResourceLocation oldTexture) {
        return id(oldTexture.getPath());
    }

    public static abstract class ListEntry extends ContainerObjectSelectionList.Entry<ListEntry> {
    }
}
