/*
 *     SparkCommons
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

package com.github.frosxt.sparkcommons.yaml;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class FileManager {
    @Getter
    private final Map<String, SparkConfig> loadedConfigs = new HashMap<>();

    /**
     * This method will load any configs and store them in a HashMap to be referenced from
     *
     * @param sparkConfig An instance of the config class to be loaded
     *
     * @see SparkConfig
     */
    public void loadConfig(final SparkConfig sparkConfig) {
        sparkConfig.loadFile();

        loadedConfigs.put(sparkConfig.getFileName(), sparkConfig);
    }
}