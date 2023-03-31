package me.frost.commons.services;

import me.frost.commons.utils.StringUtils;
import org.bukkit.Bukkit;

public class LegacyCheckService {
    private final String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

    /**
     * Checks if the server is running a legacy version of Minecraft with only one hand
     * @return true if the server is running 1.8
     */
    public boolean isLegacy() {
        return getVersionNumber() == 8;
    }

    /**
     * Checks if the server is running a legacy version of Minecraft with multiple hands
     * @return true if the server is running 1.9 - 1.15
     */
    public boolean isLegacyHands() {
        return getVersionNumber() >= 9 && getVersionNumber() < 16;
    }

    /**
     * Checks if the server is running a newer version of Minecraft supporting HEX color codes
     * @return true if the server is running 1.16+
     */
    public boolean isNewHex() {
        return getVersionNumber() >= 16;
    }

    /**
     * Gets the number of the version that the server is running
     * @return the version number
     */
    private int getVersionNumber() {
        String number = String.valueOf(version.charAt(2));
        String number2 = String.valueOf(version.charAt(3));
        if (StringUtils.use(number2).isInteger()) {
            number += number2;
        }
        return Integer.parseInt(number);
    }
}