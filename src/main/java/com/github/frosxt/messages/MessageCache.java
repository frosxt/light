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

package com.github.frosxt.messages;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MessageCache {
    private final JavaPlugin instance;
    private final FileConfiguration config;
    private final Map<String, Message> storedMessages;

    public MessageCache(final JavaPlugin plugin, final FileConfiguration config) {
        this.instance = plugin;
        this.config = config;
        this.storedMessages = new HashMap<>();
    }

    public MessageCache sendMessage(final Player player, final String path) {
        if (containsMessage(path)) {
            getMessage(path).sendMessage(player);
        }

        return this;
    }

    public MessageCache sendMessage(final Player player, final String path, final Map<String, String> placeholders) {
        if (containsMessage(path)) {
            getMessage(path).sendMessage(player, placeholders);
        }

        return this;
    }

    public boolean containsMessage(final String path) {
        return storedMessages.containsKey(path.toLowerCase());
    }

    public Message getMessage(final String path) {
        return storedMessages.get(path);
    }

    public MessageCache storeMessage(final String path) {
        storedMessages.put(path.toLowerCase(), new Message(instance, this, path));

        return this;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}