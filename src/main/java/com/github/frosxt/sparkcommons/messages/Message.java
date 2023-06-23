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

package com.github.frosxt.sparkcommons.messages;

import com.github.frosxt.sparkcommons.builders.PlaceholderBuilder;
import com.github.frosxt.sparkcommons.colour.ColouredString;
import com.github.frosxt.sparkcommons.utils.support.XSound;
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

    private final boolean messageEnabled;
    private final boolean soundEnabled;
    private final boolean titleEnabled;
    private final boolean actionBarEnabled;

    private final XSound sound;
    private final String title;
    private final String subtitle;
    private final String actionBar;
    private final List<String> message;

    private final float volume;
    private final float pitch;
    private final int fadeInTicks;
    private final int stayTicks;
    private final int fadeOutTicks;
    private final int barDuration;

    public Message(final JavaPlugin plugin, final MessageCache cache, final String path) {
        this.instance = plugin;
        final FileConfiguration config = cache.getConfig();

        this.soundEnabled = config.getBoolean(path + ".sound.enabled", false);
        this.sound = XSound.matchXSound(config.getString(path + ".sound.name", "ENTITY_PLAYER_LEVELUP")).get();
        this.volume = config.getLong(path + ".sound.volume", 1);
        this.pitch = config.getLong(path + ".sound.pitch", 1);

        this.titleEnabled = config.getBoolean(path + ".title.enabled", true);
        this.title = new ColouredString(config.getString(path + ".title.title", "")).toString();
        this.subtitle = new ColouredString(config.getString(path + ".title.subtitle", "")).toString();
        this.fadeInTicks = config.getInt(path + ".title.fade-in-ticks", 20);
        this.stayTicks = config.getInt(path + ".title.stay-ticks", 20);
        this.fadeOutTicks = config.getInt(path + ".title.fade-out-ticks", 20);

        this.actionBarEnabled = config.getBoolean(path + ",actionbar.enabled", true);
        this.actionBar = new ColouredString(config.getString(path + ".actionbar.actionbar", "")).toString();
        this.barDuration = config.getInt(path + ".actionbar.duration", 40);

        this.messageEnabled = config.getBoolean(path + ".message.enabled", true);
        this.message = config.getStringList(path + ".message.message");
    }

    public Message(final JavaPlugin plugin, final FileConfiguration config, final String path) {
        this.instance = plugin;

        this.soundEnabled = config.getBoolean(path + ".sound.enabled", false);
        this.sound = XSound.matchXSound(config.getString(path + ".sound.name", "ENTITY_PLAYER_LEVELUP")).get();
        this.volume = config.getLong(path + ".sound.volume", 1);
        this.pitch = config.getLong(path + ".sound.pitch", 1);

        this.titleEnabled = config.getBoolean(path + ".title.enabled", true);
        this.title = new ColouredString(config.getString(path + ".title.title", "")).toString();
        this.subtitle = new ColouredString(config.getString(path + ".title.subtitle", "")).toString();
        this.fadeInTicks = config.getInt(path + ".title.fade-in-ticks", 20);
        this.stayTicks = config.getInt(path + ".title.stay-ticks", 20);
        this.fadeOutTicks = config.getInt(path + ".title.fade-out-ticks", 20);

        this.actionBarEnabled = config.getBoolean(path + "actionbar.enabled", true);
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

    public boolean isSoundEnabled() {
        return this.soundEnabled;
    }

    public boolean isTitleEnabled() {
        return this.titleEnabled;
    }

    public boolean isActionBarEnabled() {
        return this.actionBarEnabled;
    }

    public boolean isMessageEnabled() {
        return this.messageEnabled;
    }

    public XSound getSound() {
        return this.sound;
    }

    public String getActionBar() {
        return this.actionBar;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubTitle() {
        return this.subtitle;
    }

    public float getVolume() {
        return this.volume;
    }

    public float getPitch() {
        return this.pitch;
    }

    public int getFadeInTicks() {
        return this.fadeInTicks;
    }

    public int getStayTicks() {
        return this.stayTicks;
    }

    public int getFadeOutTicks() {
        return this.fadeOutTicks;
    }

    public int getBarDuration() {
        return this.barDuration;
    }

    public List<String> getMessage() {
        final List<String> messageList = new ArrayList<>();
        for (final String string : this.message) {
            messageList.add(new ColouredString(string).toString());
        }

        return messageList;
    }
}