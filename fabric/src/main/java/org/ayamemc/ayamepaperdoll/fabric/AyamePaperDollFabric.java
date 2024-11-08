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

package org.ayamemc.ayamepaperdoll.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import org.ayamemc.ayamepaperdoll.AyamePaperDoll;
import org.ayamemc.ayamepaperdoll.CommonInterfaceInstances;
import org.ayamemc.ayamepaperdoll.handler.EventHandler;

public class AyamePaperDollFabric implements ClientModInitializer {
    public Screen currentScreen;
    @Override
    public void onInitializeClient() {
        // Run our Fabric setup.
        CommonInterfaceInstances.keyHelper = KeyBindingHelper::getBoundKeyOf;
        KeyMapping showPaperDollKey = KeyBindingHelper.registerKeyBinding(AyamePaperDoll.SHOW_PAPERDOLL_KEY);
        KeyMapping openConfigGuiKey = KeyBindingHelper.registerKeyBinding(AyamePaperDoll.OPEN_CONFIG_GUI);

        AyamePaperDoll.init();
        // consumeClick
        ClientTickEvents.END_CLIENT_TICK.register((minecraft) -> {
            EventHandler.consumeClick();
        });
        HudRenderCallback.EVENT.register((EventHandler::renderPaperDoll));
    }
}
