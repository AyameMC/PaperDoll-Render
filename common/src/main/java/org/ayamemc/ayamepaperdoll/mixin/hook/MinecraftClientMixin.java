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

package org.ayamemc.ayamepaperdoll.mixin.hook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.ayamemc.ayamepaperdoll.AyamePaperDoll;
import org.ayamemc.ayamepaperdoll.config.ConfigScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {
    @Unique
    @Nullable
    public Screen ayame_PaperDoll$currentScreen;

    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Unique
    private Boolean ayame_PaperDoll$getPaperDollEnabled() {
        return AyamePaperDoll.CONFIGS.enabled.getValue();
    }
    @Unique
    private void ayame_PaperDoll$setPaperDollEnabled(Boolean enabled) {
        AyamePaperDoll.CONFIGS.enabled.setValue(enabled);
    }

    @Inject(method = "handleKeybinds", at = @At("RETURN"))
    private void onHandleInputEventsFinish(CallbackInfo ci) {
        while (AyamePaperDoll.SHOW_PAPERDOLL_KEY.consumeClick()) {
            ayame_PaperDoll$setPaperDollEnabled(!ayame_PaperDoll$getPaperDollEnabled());
        }
        while (AyamePaperDoll.OPEN_CONFIG_GUI.consumeClick()) {
            this.setScreen(new ConfigScreen(this.ayame_PaperDoll$currentScreen, AyamePaperDoll.CONFIGS.getOptions()));
        }
    }
}
