package me.frost.commons.menus.listeners;

import me.frost.commons.menus.MenuHandler;
import me.frost.commons.menus.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ButtonListener implements Listener {
    private final MenuHandler handler = MenuHandler.getInstance();

    @EventHandler
    public void onButtonClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Menu menu = handler.findMenu(player);

        if (menu != null && event.getCurrentItem() != null) {
            event.setCancelled(menu.click(event.getClick(), event.getSlot()));
        }
    }
}