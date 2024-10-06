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

package org.ayamemc.paperdollrender.config.persistence;

import org.ayamemc.paperdollrender.config.model.ConfigOption;

import java.nio.file.Path;
import java.util.List;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface ConfigPersistence {
    Path getPath();

    /**
     * Save config.
     * <br/>
     * To prevent crashing, it should log an error instead of throwing when it fails.
     *
     * @return save is successful
     */
    boolean save(List<? extends ConfigOption<?>> options);

    /**
     * Load config.
     * <br/>
     * To prevent crashing, it should log an error instead of throwing when it fails.
     *
     * @return load is successful
     */
    boolean load(List<? extends ConfigOption<?>> options);
}
