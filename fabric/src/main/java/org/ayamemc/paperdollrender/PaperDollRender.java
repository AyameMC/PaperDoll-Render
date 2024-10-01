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

package org.ayamemc.paperdollrender;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.paperdollrender.api.config.persistence.ConfigPersistence;
import org.ayamemc.paperdollrender.api.config.persistence.GsonConfigPersistence;
import org.ayamemc.paperdollrender.config.Configs;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaperDollRender implements ClientModInitializer {

    public static final String MOD_ID = "paperdollrender";
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static final String MOD_NAME = FabricLoader.getInstance().getModContainer(PaperDollRender.MOD_ID).get().getMetadata().getName();
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final KeyMapping CONFIG_KEY = new KeyMapping(
            "key.%s.openMenu".formatted(MOD_ID),
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_F8,
            "key.%s.category".formatted(MOD_ID));
    public static final Configs CONFIGS = new Configs();
    public static final ConfigPersistence CONFIG_PERSISTENCE = new GsonConfigPersistence(FabricLoader.getInstance().getConfigDir().resolve(PaperDollRender.MOD_ID + "_v3.json"));

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitializeClient() {
        PaperDollRender.CONFIG_PERSISTENCE.load(PaperDollRender.CONFIGS.getOptions());
        KeyBindingHelper.registerKeyBinding(CONFIG_KEY);
    }
}
