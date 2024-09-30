/*
 *     Highly configurable paper doll mod, well integrated with Ayame.
 *     Copyright (C) 2024  LucunJi(Original author), CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
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

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A container that does not render but provides inner widgets to
 * {@link net.minecraft.client.gui.components.tabs.TabNavigationBar} instances
 */
public class Tab implements net.minecraft.client.gui.components.tabs.Tab {
    private final Component title;
    private final List<AbstractWidget> children;

    public Tab(Component title) {
        this.title = title;
        this.children = new ArrayList<>();
    }

    public void addChild(AbstractWidget child) {
        this.children.add(child);
    }

    @Override
    public Component getTabTitle() {
        return this.title;
    }

    /**
     * Used to load/unload children when switching tab
     */
    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        children.forEach(consumer);
    }

    /**
     * Seems useless here
     */
    @Override
    public void doLayout(ScreenRectangle tabArea) {
    }
}
