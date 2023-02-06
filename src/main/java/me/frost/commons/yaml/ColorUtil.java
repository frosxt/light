package me.frost.commons.yaml;

import org.bukkit.ChatColor;

public class ColorUtil {
    public static String colorMessage(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
