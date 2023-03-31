package me.frost.commons.colour;

import me.frost.commons.colour.enums.ColourType;
import me.frost.commons.services.LegacyCheckService;
import org.bukkit.ChatColor;

public class ColouredString {
    private final ColourType type;
    private final String text;

    public ColouredString(String text) {
        this.text = text;

        if (new LegacyCheckService().isNewHex()) {
            this.type = ColourType.HEX;
        } else {
            this.type = ColourType.MC;
        }
    }

    public ColouredString(String text, ColourType type) {
        this.text = text;
        this.type = type;
    }

    /**
     * Translates the string's content to colour
     */
    @Override
    public String toString() {
        String result;

        if (type == ColourType.MC) {
            result = ChatColor.translateAlternateColorCodes('&', text);
        } else {
            if (!(new LegacyCheckService().isNewHex())) {
                result = ChatColor.translateAlternateColorCodes('&', text);
            } else {
                result = ChatColor.translateAlternateColorCodes('&', RGBUtils.getInstance().toChatColorString(text));
            }
        }

        return result;
    }
}