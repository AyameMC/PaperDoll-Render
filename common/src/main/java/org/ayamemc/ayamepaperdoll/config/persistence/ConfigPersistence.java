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

package org.ayamemc.ayamepaperdoll.config.persistence;

import org.ayamemc.ayamepaperdoll.config.model.ConfigOption;

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
