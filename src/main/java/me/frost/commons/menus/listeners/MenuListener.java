package me.frost.commons.menus.listeners;

import me.frost.commons.menus.MenuHandler;
import me.frost.commons.menus.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Menu menu = MenuHandler.getInstance().findMenu(player);

        if (menu == null) {
            return;
        }

        menu.onClose(event);
    }
}