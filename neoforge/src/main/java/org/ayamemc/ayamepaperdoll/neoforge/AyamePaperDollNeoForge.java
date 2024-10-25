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

package org.ayamemc.ayamepaperdoll.neoforge;

import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.ayamemc.ayamepaperdoll.AyamePaperDoll;
import org.ayamemc.ayamepaperdoll.CommonInterfaceInstances;
import org.ayamemc.ayamepaperdoll.config.ConfigScreen;

@Mod(value = AyamePaperDoll.MOD_ID, dist = Dist.CLIENT)
public final class AyamePaperDollNeoForge {
    public AyamePaperDollNeoForge(IEventBus modBus) {
        // Run our NeoForge setup.
        CommonInterfaceInstances.keyHelper = KeyMapping::getKey;
        AyamePaperDoll.init();
        modBus.addListener(AyamePaperDollNeoForge::registerKeyMapping);
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, lastScreen) -> new ConfigScreen(lastScreen, AyamePaperDoll.CONFIGS.getOptions())
        );

    }

    private static void registerKeyMapping(RegisterKeyMappingsEvent event) {
        event.register(AyamePaperDoll.SHOW_PAPERDOLL_KEY);
        event.register(AyamePaperDoll.OPEN_CONFIG_GUI);
    }
}
