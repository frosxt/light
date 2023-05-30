package me.frost.commons.menus;

import me.frost.commons.menus.listeners.ButtonListener;
import me.frost.commons.menus.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * An instance of this class must be registered before creating any menus!
 */
public class MenuHandler {
    private static MenuHandler instance;
    private final Map<Player, Menu> menus = new HashMap<>();

    public MenuHandler(final JavaPlugin plugin) {
        instance = this;

        plugin.getServer().getPluginManager().registerEvents(new ButtonListener(), plugin);
        // plugin.getServer().getPluginManager().registerEvents(new MenuListener(), plugin);
    }

    public Map<Player, Menu> getMenus() {
        return menus;
    }

    public Menu findMenu(final Player player) {
        return menus.get(player);
    }

    public static MenuHandler getInstance() {
        return instance;
    }
}