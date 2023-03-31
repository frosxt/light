package me.frost.commons.builders;

import me.frost.commons.colour.ColouredString;
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

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(Material material, int amount, short data) {
        itemStack = new ItemStack(material, amount, data);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder setData(short data) {
        itemStack.setDurability(data);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(new ColouredString(name).toString());
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).addItemFlags(flag);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag flag) {
        ItemMeta meta = itemStack.getItemMeta();
        if (Objects.requireNonNull(meta).hasItemFlag(flag)) {
            meta.removeItemFlags(flag);
            itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setUnbreakable() {
        ItemMeta meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setUnbreakable(true);
        itemStack.setItemMeta(meta);
        return this;
    }


    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> l = new ArrayList<>();

        for (String string : lore) {
            l.add(new ColouredString(string).toString());
        }

        Objects.requireNonNull(meta).setLore(l);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();

        List<String> l = new ArrayList<>();
        lore.forEach(string -> l.add(new ColouredString(string).toString()));

        Objects.requireNonNull(meta).setLore(l);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        ItemMeta meta = itemStack.getItemMeta();

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

    public ItemBuilder removeLoreLine(int index) {
        ItemMeta meta = itemStack.getItemMeta();

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

    public ItemBuilder addLoreLine(String line) {
        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();
        if (Objects.requireNonNull(meta).hasLore()) {
            lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
        }
        lore.add(new ColouredString(line).toString());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addLoreLine(String line, int lineNumber) {
        ItemMeta meta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();
        if (Objects.requireNonNull(meta).hasLore()) {
            lore = new ArrayList<>(Objects.requireNonNull(meta.getLore()));
        }
        lore.set(lineNumber, new ColouredString(line).toString());
        meta.setLore(lore);
        itemStack.setItemMeta(meta);

        return this;
    }


    public ItemStack build() {
        return itemStack;
    }
}
