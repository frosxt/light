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

import com.github.frosxt.builders.ItemBuilder;
import com.github.frosxt.utils.support.XMaterial;
import com.google.common.io.Files;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 * This class will be used to load a new config file
 */
public abstract class LightConfig {
    @Getter
    private final JavaPlugin plugin;

    @Getter
    private final String fileName;

    @Getter
    private final String path;

    @Getter
    private FileConfiguration config;

    private final File file;

    /**
     * @param fileName This is the name of the config file
     * @param path This is the path for the config file to load to
     */
    public LightConfig(final JavaPlugin plugin, final String fileName, final String path) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.path = path;
        this.file = new File(path + File.separator + fileName + ".yml");
    }

    /**
     * This method will load a new config file
     */
    public void loadFile() {
        if (!file.exists()) {
            plugin.saveResource(path + File.separator + fileName + ".yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.loadFromString(Files.toString(file, StandardCharsets.UTF_8));
        } catch (final IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    public ItemStack getItemStackFromConfig(final String path) {
        final ItemBuilder itemBuilder = new ItemBuilder(XMaterial.matchXMaterial(config.getString(path + ".material")).get().parseMaterial())
                .setName(config.getString(path + ".name"))
                .setLore(config.getStringList(path + ".lore"))
                .setData((short) config.getInt(path + ".data"));

        return itemBuilder.build();
    }
}