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

package org.ayamemc.ayamepaperdoll.neoforge;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.ayamemc.ayamepaperdoll.AyamePaperDoll;
import org.ayamemc.ayamepaperdoll.CommonInterfaceInstances;
import org.ayamemc.ayamepaperdoll.config.ConfigScreen;
import org.ayamemc.ayamepaperdoll.handler.EventHandler;

@Mod(value = AyamePaperDoll.MOD_ID, dist = Dist.CLIENT)
public final class AyamePaperDollNeoForge {
    public AyamePaperDollNeoForge(IEventBus modBus) {
        // Run our NeoForge setup.
        CommonInterfaceInstances.keyHelper = KeyMapping::getKey;

        AyamePaperDoll.init();

        modBus.addListener(AyamePaperDollNeoForge::registerKeyMapping);

        NeoForge.EVENT_BUS.addListener(AyamePaperDollNeoForge::renderPaperDoll);
        NeoForge.EVENT_BUS.addListener(AyamePaperDollNeoForge::onClientTick);

        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, lastScreen) -> new ConfigScreen(lastScreen, AyamePaperDoll.CONFIGS.getOptions())
        );

    }

    private static void renderPaperDoll(RenderGuiEvent.Post event) {
        final GuiGraphics guiGraphics = event.getGuiGraphics();
        final DeltaTracker partialTick = event.getPartialTick();
        EventHandler.renderPaperDoll(guiGraphics, partialTick);
    }

    private static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(AyamePaperDoll.SHOW_PAPERDOLL_KEY);
        event.register(AyamePaperDoll.OPEN_CONFIG_GUI);
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        EventHandler.keyPressed();
    }
}