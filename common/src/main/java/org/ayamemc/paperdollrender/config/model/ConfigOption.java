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

package org.ayamemc.paperdollrender.config.model;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
public interface ConfigOption<T> {
    T getDefaultValue();

    /**
     * Invalid {@code value} must be logged as an {@link org.slf4j.event.Level#WARN}
     *
     * @return a valid new value
     */
    T validate(T oldValue, T newValue);

    /**
     * @return if the value is changed
     */
    boolean setValue(T newValue);

    default boolean isValueDefault() {
        return Objects.equals(this.getValue(), this.getDefaultValue());
    }

    T getValue();

    ResourceLocation getCategory();

    ResourceLocation getId();

    Component getName();

    Component getDescription();

    Class<T> getType();
}
