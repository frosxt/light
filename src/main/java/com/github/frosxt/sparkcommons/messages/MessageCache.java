package com.github.frosxt.sparkcommons.messages;

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