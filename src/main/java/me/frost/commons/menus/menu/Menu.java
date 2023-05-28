package me.frost.commons.menus.menu;

import me.frost.commons.colour.ColouredString;
import me.frost.commons.menus.MenuHandler;
import me.frost.commons.menus.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Menu {
    private final Player player;
    private String title;
    private final int size;
    private Inventory inventory;

    private final List<Button> buttons = new ArrayList<>();

    public Menu(final Player player, final String title, final int size) {
        this.player = player;
        this.title = title;
        this.size = size;

        registerMenu();
    }

    public abstract void onClose(InventoryCloseEvent event);

    public void updateMenu(final List<Button> buttons) {
        final Inventory inventory = this.inventory == null ? Bukkit.createInventory(null, getSize(), new ColouredString(getTitle()).toString()) : this.inventory;

        clearMenu(inventory);

        buttons.stream()
                .filter(button -> button != null && button.toItemStack() != null)
                .forEach(button -> inventory.setItem(button.getIndex(), button.toItemStack()));

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

    public boolean click(final ClickType clickType, final int index) {
        final Optional<Button> button = getButtons().stream()
                .filter(current -> current.getIndex() == index)
                .findFirst();

        if (button.isPresent() && button.get().getClickAction() != null) {
            return button.get().getClickAction().apply(clickType);
        }

        return false;
    }

    public void fillMenu(final ItemStack fillerItem) {
        for (int i = 0; i < getSize(); i++) {
            buttons.add(new Button(i, fillerItem));
        }
    }

    public void fillMenuBorder(final ItemStack fillerItem) {
        for (int i = 0; i < getSize(); i++) {
            if (i < 9 || i >= getSize() - 9 || i % 9 == 0 || i % 9 == 8) {
                buttons.add(new Button(i, fillerItem));
            }
        }
    }

    public List<Button> getButtons() {
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
}