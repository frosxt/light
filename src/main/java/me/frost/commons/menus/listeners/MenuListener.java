/**package me.frost.commons.menus.listeners;

import me.frost.commons.menus.MenuHandler;
import me.frost.commons.menus.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Optional;

public class MenuListener implements Listener {
    private final MenuHandler handler = MenuHandler.getInstance();


    @EventHandler
    public void onClose(final InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Optional<Menu> menu = Optional.ofNullable(handler.findMenu(player));

        menu.ifPresent(v -> v.handleClose(event));
    }
}**/