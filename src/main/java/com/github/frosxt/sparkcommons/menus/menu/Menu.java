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

package com.github.frosxt.sparkcommons.menus.menu;

import com.github.frosxt.sparkcommons.builders.ItemBuilder;
import com.github.frosxt.sparkcommons.colour.ColouredString;
import com.github.frosxt.sparkcommons.menus.MenuHandler;
import com.github.frosxt.sparkcommons.menus.buttons.Button;
import com.github.frosxt.sparkcommons.menus.buttons.FillingType;
import com.github.frosxt.sparkcommons.utils.support.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu {
    private final Player player;
    private String title;
    private final int size;
    private Inventory inventory;

    public Button[] buttons;

    private ItemStack fillerItem;
    private final List<FillingType> fillers = new ArrayList<>();

    public Menu(final Player player, final String title, final int size) {
        this.player = player;
        this.title = title;
        this.size = size;
        this.fillerItem = new ItemBuilder(XMaterial.matchXMaterial("GRAY_STAINED_GLASS_PANE").get().parseItem()).setName(" ").build();

        this.buttons = new Button[this.size];

        registerMenu();
    }

    public void updateMenu(final Button[] buttons) {
        final Inventory inventory = this.inventory == null ? Bukkit.createInventory(null, getSize(), new ColouredString(getTitle()).toString()) : this.inventory;

        clearMenu(inventory);

        setup();

        final Button[] fillerButtons = getFillerButtons();

        for (int i = 0; i < fillerButtons.length; i++) {
            if (fillerButtons[i] != null) {
                this.buttons[i] = fillerButtons[i];
            }
        }

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                inventory.setItem(i, buttons[i].toItemStack());
            }
        }

        if (inventory != this.inventory) {
            this.inventory = inventory;

            player.closeInventory();
            player.openInventory(inventory);
        } else {
            player.updateInventory();
        }


        registerMenu();
    }

    public void updateMenu() {
        this.updateMenu(this.getButtons());
    }

    public void clearMenu(final Inventory inventory) {
        if (inventory == null) {
            return;
        }

        for (int i = 0; i < this.size; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
    }

    public void registerMenu() {
        MenuHandler.getInstance().getMenus().put(getPlayer(), this);
    }

    public abstract void setup();

    public void redirect(final Menu menu) {
        menu.updateMenu();
        this.registerMenu();
    }

    public void click(final InventoryClickEvent event) {
        try {
            final Button button = buttons[event.getSlot()];

            if (button == null) {
                event.setCancelled(true);
                return;
            }

            if (button.getClickAction() != null) {
                button.getClickAction().accept(event);
            }
        } catch (final IndexOutOfBoundsException ignored) {
            // Exception ignored
        }
    }

    public Menu setFillerItem(final ItemStack itemStack) {
        this.fillerItem = itemStack;

        return this;
    }

    public Button[] getFillerButtons() {
        final Button[] buttons = new Button[getSize()];

        for (final FillingType fillingType : fillers) {
            final Button[] fillers = fillingType.fillMenu(this);

            for (int i = 0; i < fillers.length; i++) {
                if (fillers[i] != null) {
                    this.buttons[i] = fillers[i];
                }
            }
        }

        return buttons;
    }

    public void setButton(final int slot, final Button button) {
        buttons[slot] = button;
    }

    public void setTemporaryButton(final JavaPlugin plugin, final int slot, final Button button, final int seconds) {
        inventory.setItem(slot, button.toItemStack());
        Bukkit.getScheduler().runTaskLater(plugin, () -> updateMenu(), seconds * 20L);
    }

    public void handleClose(final InventoryCloseEvent event) {
        MenuHandler.getInstance().getMenus().remove(event.getPlayer());
    }

    public void addFiller(final FillingType type) {
        this.fillers.add(type);
    }

    public Button[] getButtons() {
        return this.buttons;
    }

    public int getSize() {
        return Math.min(this.size, 54);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = new ColouredString(title).toString();
    }

    public Player getPlayer() {
        return this.player;
    }

    public ItemStack getFillerItem() {
        return this.fillerItem;
    }
}