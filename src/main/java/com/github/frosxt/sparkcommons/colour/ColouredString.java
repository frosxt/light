package com.github.frosxt.sparkcommons.colour;

import com.github.frosxt.sparkcommons.colour.enums.ColourType;
import com.github.frosxt.sparkcommons.services.LegacyCheckService;
import org.bukkit.ChatColor;

/*
 * Written by <a href="https://github.com/CoasterFreakDE">
 * CoasterFreakDE &lt;https://github.com/CoasterFreakDE&gt;</a>
 */
public class ColouredString {
    private final ColourType type;
    private final String text;

    public ColouredString(final String text) {
        this.text = text;

        if (new LegacyCheckService().isNewHex()) {
            this.type = ColourType.HEX;
        } else {
            this.type = ColourType.MC;
        }
    }

    public ColouredString(final String text, final ColourType type) {
        this.text = text;
        this.type = type;
    }

    /**
     * Translates the string's content to colour
     */
    @Override
    public String toString() {
        final String result;

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