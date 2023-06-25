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

package com.github.frosxt.builders;

import com.github.frosxt.colour.ColouredString;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(final Material material) {
        this(material, 1);
    }

    public ItemBuilder(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(final Material material, final int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(final Material material, final int amount, final short data) {
        itemStack = new ItemStack(material, amount, data);
    }

    @Override
    public ItemBuilder clone() {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder setData(final short data) {
        itemStack.setDurability(data);
        return this;
    }

    public ItemBuilder setName(final String name) {
        final ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(new ColouredString(name).toString());
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(final Enchantment enchantment, final int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(final Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder addItemFlag(final ItemFlag flag) {
        final ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).addItemFlags(flag);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeItemFlag(final ItemFlag flag) {
        final ItemMeta meta = itemStack.getItemMeta();
        if (Objects.requireNonNull(meta).hasItemFlag(flag)) {
            meta.removeItemFlags(flag);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setUnbreakable() {
        final ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setUnbreakable(true);
        itemStack.setItemMeta(meta);
        return this;
    }


    public ItemBuilder setLore(final String... lore) {
        final ItemMeta meta = itemStack.getItemMeta();
        final List<String> l = new ArrayList<>();

        for (final String string : lore) {
            l.add(new ColouredString(string).toString());
        }

        Objects.requireNonNull(meta).setLore(l);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(final List<String> lore) {
        final ItemMeta meta = itemStack.getItemMeta();

        final List<String> l = new ArrayList<>();
        lore.forEach(string -> l.add(new ColouredString(string).toString()));

        Objects.requireNonNull(meta).setLore(l);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder removeLoreLine(final String line) {
        final ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();
        if (Objects.requireNonNull(meta).hasLore()) {
            lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
        }
        if (!lore.contains(line)) {
            return this;
        }
        lore.remove(line);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder removeLoreLine(final int index) {
        final ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();
        if (Objects.requireNonNull(meta).hasLore()) {
            lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
        }
        if (index < 0 || index > lore.size()) {
            return this;
        }
        lore.remove(index);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addLoreLine(final String line) {
        final ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();
        if (Objects.requireNonNull(meta).hasLore()) {
            lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
        }
        lore.add(new ColouredString(line).toString());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addLoreLine(final String line, final int lineNumber) {
        final ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();
        if (Objects.requireNonNull(meta).hasLore()) {
            lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
        }
        lore.set(lineNumber, new ColouredString(line).toString());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder parsePlaceholders(final PlaceholderBuilder placeholderBuilder) {
        final ItemMeta meta = itemStack.getItemMeta();

        final String newName = placeholderBuilder.parse(meta.getDisplayName());
        Objects.requireNonNull(meta).setDisplayName(new ColouredString(newName).toString());

        final List<String> parsedLore = new ArrayList<>();
        for (final String lore : Objects.requireNonNull(meta.getLore())) {
            final String line = placeholderBuilder.parse(lore);
            parsedLore.add(new ColouredString(line).toString());
        }

        meta.setLore(parsedLore);

        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
