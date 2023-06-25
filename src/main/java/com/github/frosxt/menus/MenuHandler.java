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

package com.github.frosxt.menus;

import com.github.frosxt.menus.listeners.ButtonListener;
import com.github.frosxt.menus.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * An instance of this class must be registered before creating any menus!
 */
public class MenuHandler {
    private static MenuHandler instance;
    private final Map<Player, Menu> menus = new HashMap<>();

    public MenuHandler(final JavaPlugin plugin) {
        instance = this;

        plugin.getServer().getPluginManager().registerEvents(new ButtonListener(), plugin);
        // plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    public Map<Player, Menu> getMenus() {
        return menus;
    }

    public Menu findMenu(final Player player) {
        return menus.get(player);
    }

    public static MenuHandler getInstance() {
        return instance;
    }
}