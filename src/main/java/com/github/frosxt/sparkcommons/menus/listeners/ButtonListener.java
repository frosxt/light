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

package com.github.frosxt.sparkcommons.menus.listeners;

import com.github.frosxt.sparkcommons.menus.MenuHandler;
import com.github.frosxt.sparkcommons.menus.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Optional;

public class ButtonListener implements Listener {
    private final MenuHandler handler = MenuHandler.getInstance();

    @EventHandler
    public void onButtonClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Optional<Menu> menu = Optional.ofNullable(handler.findMenu(player));

        if (menu.isPresent() && event.getCurrentItem() != null) {
            menu.get().click(event);
        }
    }

    @EventHandler
    public void onClose(final InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Optional<Menu> menu = Optional.ofNullable(handler.findMenu(player));

        menu.ifPresent(v -> v.handleClose(event));
    }
}