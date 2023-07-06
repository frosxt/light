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

package com.github.frosxt.messages;

import com.github.frosxt.builders.PlaceholderBuilder;
import com.github.frosxt.colour.ColouredString;
import com.github.frosxt.utils.support.XSound;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Message {
    private final JavaPlugin instance;

    @Getter
    private final boolean messageEnabled;
    @Getter
    private final boolean soundEnabled;
    @Getter
    private final boolean titleEnabled;
    @Getter
    private final boolean actionBarEnabled;

    @Getter
    private final XSound sound;
    @Getter
    private final String title;
    @Getter
    private final String subtitle;
    @Getter
    private final String actionBar;
    @Getter
    private final List<String> message;

    @Getter
    private final float volume;
    @Getter
    private final float pitch;
    @Getter
    private final int fadeInTicks;
    @Getter
    private final int stayTicks;
    @Getter
    private final int fadeOutTicks;
    @Getter
    private final int barDuration;

    public Message(final JavaPlugin plugin, final MessageCache cache, final String path) {
        this(plugin, cache.getConfig(), path);
    }

    public Message(final JavaPlugin plugin, final FileConfiguration config, final String path) {
        this.instance = plugin;

        this.soundEnabled = config.getBoolean(path + ".sound.enabled", false);
        this.sound = XSound.matchXSound(config.getString(path + ".sound.name", "ENTITY_PLAYER_LEVELUP")).get();
        this.volume = config.getLong(path + ".sound.volume", 1);
        this.pitch = config.getLong(path + ".sound.pitch", 1);

        this.titleEnabled = config.getBoolean(path + ".title.enabled", false);
        this.title = new ColouredString(config.getString(path + ".title.title", "")).toString();
        this.subtitle = new ColouredString(config.getString(path + ".title.subtitle", "")).toString();
        this.fadeInTicks = config.getInt(path + ".title.fade-in-ticks", 20);
        this.stayTicks = config.getInt(path + ".title.stay-ticks", 20);
        this.fadeOutTicks = config.getInt(path + ".title.fade-out-ticks", 20);

        this.actionBarEnabled = config.getBoolean(path + ".actionbar.enabled", false);
        this.actionBar = new ColouredString(config.getString(path + ".actionbar.actionbar", "")).toString();
        this.barDuration = config.getInt(path + ".actionbar.duration", 40);

        this.messageEnabled = config.getBoolean(path + ".message.enabled", true);
        this.message = config.getStringList(path + ".message.message");
    }

    public void sendMessage(final CommandSender commandSender) {
        sendMessage(commandSender, new HashMap<>());
    }

    public void sendMessage(final CommandSender commandSender, final Map<String, String> placeholders) {
        final PlaceholderBuilder placeholderBuilder = new PlaceholderBuilder();
        placeholders.forEach(placeholderBuilder::addPlaceholder);

        if (messageEnabled) {
            for (final String line : message) {
                commandSender.sendMessage(placeholderBuilder.parse(new ColouredString(line).toString()));
            }
        }

        if (!(commandSender instanceof Player)) {
            return;
        }

        final Player player = (Player) commandSender;

        if (soundEnabled) {
            sound.play(player.getLocation(), volume, pitch);
        }

        if (titleEnabled) {
            Titles.sendTitle(player, fadeInTicks, stayTicks, fadeOutTicks, placeholderBuilder.parse(title), placeholderBuilder.parse(subtitle));
        }

        if (actionBarEnabled) {
            ActionBar.sendActionBar(instance, player, placeholderBuilder.parse(actionBar), barDuration);
        }
    }

    public List<String> getMessage() {
        final List<String> messageList = new ArrayList<>();
        for (final String string : this.message) {
            messageList.add(new ColouredString(string).toString());
        }

        return messageList;
    }
}