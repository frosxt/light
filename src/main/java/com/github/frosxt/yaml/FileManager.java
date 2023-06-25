/*
 *     Light
 *     Copyright (C) 2023 frosxt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License in LICENSE.MD
 *     Please refer to that file for details.
 */

package com.github.frosxt.yaml;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
    @Getter
    private final Map<String, LightConfig> loadedConfigs = new HashMap<>();

    /**
     * This method will load any configs and store them in a HashMap to be referenced from
     *
     * @param lightConfig An instance of the config class to be loaded
     *
     * @see LightConfig
     */
    public void loadConfig(final LightConfig lightConfig) {
        lightConfig.loadFile();

        loadedConfigs.put(lightConfig.getFileName(), lightConfig);
    }
}