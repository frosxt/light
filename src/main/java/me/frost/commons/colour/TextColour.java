package me.frost.commons.colour;

/**
 * Written by <a href="https://github.com/CoasterFreakDE">
 * CoasterFreakDE &lt;https://github.com/CoasterFreakDE&gt;</a>
 *
 * @author CoasterFreakDE
 */
public final class TextColour {
    private final int red;
    private final int green;
    private final int blue;

    public TextColour(String hexCode) {
        final int hexColour = Integer.valueOf(hexCode, 16);
        this.red = hexColour >> 16 & 0xFF;
        this.green = hexColour >> 8 & 0xFF;
        this.blue = hexColour & 0xFF;
    }

    public int getRed() {
        return red;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }
}