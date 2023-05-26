package me.frost.commons.menus.buttons;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.function.Function;

public class Button {
    private final MaterialData materialData;
    private final ItemMeta itemMeta;

    private int index;
    private int amount = 1;

    private Function<ClickType, Boolean> clickAction;

    public Button(int index, ItemStack itemStack) {
        this.index = index;
        this.materialData = itemStack.getData();
        this.itemMeta = itemStack.getItemMeta();
    }

    public Button(int index, Material material) {
        this(index, new ItemStack(material));
    }

    public void setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(displayName);
    }

    public void setLore(String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
    }

    public void setClickAction(Function<ClickType, Boolean> action) {
        this.clickAction = action;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ItemStack toItemStack() {
        ItemStack itemStack = this.materialData.toItemStack();

        itemStack.setAmount(this.amount);
        itemStack.setItemMeta(this.itemMeta);

        return itemStack;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public Function<ClickType, Boolean> getClickAction() {
        return this.clickAction;
    }
}
