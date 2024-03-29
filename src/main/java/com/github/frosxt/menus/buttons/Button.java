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

package com.github.frosxt.menus.buttons;

import com.github.frosxt.colour.ColouredString;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Button implements Cloneable {
    private final Material material;
    private final ItemMeta meta;

    private String displayName;
    private String[] lore;
    private int amount;
    private byte data;

    private Consumer<InventoryClickEvent> clickAction = event -> event.setCancelled(true);

    public Button(final Material material) {
        this(new ItemStack(material));
    }

    public Button(final ItemStack itemStack) {
        this.material = itemStack.getType();
        this.meta = itemStack.getItemMeta();

        this.data = itemStack.getData().getData();
        this.amount = itemStack.getAmount();
    }

    @Override
    public Button clone() {
        return new Button(this.material)
                .setDisplayName(this.getDisplayName())
                .setAmount(this.getAmount())
                .setClickAction(this.getClickAction())
                .setLore(this.getLore())
                .setData(this.getData());
    }

    public ItemStack toItemStack() {
        final ItemStack item = new ItemStack(this.getMaterial(), this.getAmount(), this.getData());
        final ItemMeta meta;

        if (this.meta == null) {
            meta = item.getItemMeta();
        } else {
            meta = this.meta;
        }

        if (meta != null) {
            if (getDisplayName() != null) {
                meta.setDisplayName(new ColouredString(getDisplayName()).toString());
            }

            if (this.getLore() != null) {
                meta.setLore(Arrays.stream(this.getLore())
                        .map(string -> new ColouredString(string).toString())
                        .collect(Collectors.toList())
                );
            }

            item.setItemMeta(meta);
        }

        return item;
    }

    public Button setDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    public Button setLore(final String[] lore) {
        this.lore = lore;
        return this;
    }

    public Button setClickAction(final Consumer<InventoryClickEvent> clickAction) {
        this.clickAction = clickAction;
        return this;
    }

    public Button setAmount(final int amount) {
        this.amount = amount;
        return this;
    }

    public Button setData(final byte data) {
        this.data = data;
        return this;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String[] getLore() {
        return this.lore;
    }

    public int getAmount() {
        return this.amount;
    }

    public byte getData() {
        return this.data;
    }

    public Consumer<InventoryClickEvent> getClickAction() {
        return this.clickAction;
    }

    public Material getMaterial() {
        return this.material;
    }
}
