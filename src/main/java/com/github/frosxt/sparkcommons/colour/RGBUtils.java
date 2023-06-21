package com.github.frosxt.sparkcommons.colour;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Written by <a href="https://github.com/CoasterFreakDE">
 * CoasterFreakDE &lt;https://github.com/CoasterFreakDE&gt;</a>
 */
public final class RGBUtils {
    private static RGBUtils instance;

    private final Pattern hex = Pattern.compile("#[0-9a-fA-F]{6}");
    private final Pattern fix2 = Pattern.compile("\\{#[0-9a-fA-F]{6}\\}");
    private final Pattern fix3 = Pattern.compile("\\&x[\\&0-9a-fA-F]{12}");
    private final Pattern gradient1 = Pattern.compile("<#[0-9a-fA-F]{6}>[^<]*</#[0-9a-fA-F]{6}>");
    private final Pattern gradient2 = Pattern.compile("\\{#[0-9a-fA-F]{6}>\\}[^\\{]*\\{#[0-9a-fA-F]{6}<\\}");

    // Private instructor to stop instantiation
    private RGBUtils() {
    }

    public static RGBUtils getInstance() {
        if (instance == null) {
            instance = new RGBUtils();
        }
        return instance;
    }

    private String toChatColor(final String hexCode) {
        final StringBuilder magic = new StringBuilder("§x");
        for (final char c : hexCode.substring(1).toCharArray()) {
            magic.append('§').append(c);
        }
        return magic.toString();
    }

    private String toHexString(final int red, final int green, final int blue) {
        final StringBuilder sb = new StringBuilder(Integer.toHexString((red << 16) + (green << 8) + blue));
        while (sb.length() < 6) sb.insert(0, "0");
        return sb.toString();
    }

    private String applyFormats(String textInput) {
        textInput = fixFormat1(textInput);
        textInput = fixFormat2(textInput);
        textInput = fixFormat3(textInput);
        textInput = setGradient1(textInput);
        textInput = setGradient2(textInput);
        return textInput;
    }

    public String toChatColorString(String textInput) {
        textInput = applyFormats(textInput);
        final Matcher m = hex.matcher(textInput);
        while (m.find()) {
            final String hexCode = m.group();
            textInput = textInput.replace(hexCode, toChatColor(hexCode));
        }
        return textInput;
    }

    //&#RRGGBB
    private String fixFormat1(final String text) {
        return text.replace("&#", "#");
    }

    //{#RRGGBB}
    private String fixFormat2(String input) {
        final Matcher m = fix2.matcher(input);
        while (m.find()) {
            final String hexcode = m.group();
            input = input.replace(hexcode, "#" + hexcode.substring(2, 8));
        }
        return input;
    }

    //&x&R&R&G&G&B&B
    private String fixFormat3(String text) {
        text = text.replace('\u00a7', '&');
        final Matcher m = fix3.matcher(text);
        while (m.find()) {
            final String hexcode = m.group();
            // text = text.replace(hexcode, "#" + String(charArrayOf(hexcode[3], hexcode[5], hexcode[7], hexcode[9], hexcode[11], hexcode[13])));
            final StringBuilder fixed = new StringBuilder("#");
            fixed.append(hexcode.charAt(3));
            fixed.append(hexcode.charAt(5));
            fixed.append(hexcode.charAt(7));
            fixed.append(hexcode.charAt(9));
            fixed.append(hexcode.charAt(11));
            fixed.append(hexcode.charAt(13));
            text = text.replace(hexcode, fixed);
        }
        return text;
    }

    //<#RRGGBB>Text</#RRGGBB>
    private String setGradient1(String input) {
        final Matcher m = gradient1.matcher(input);
        while (m.find()) {
            final String format = m.group();
            final TextColour start = new TextColour(format.substring(2, 8));
            final String message = format.substring(9, format.length() - 10);
            final TextColour end = new TextColour(format.substring(format.length() - 7, format.length() - 1));
            input = input.replace(format, asGradient(start, message, end));
        }
        return input;
    }

    //{#RRGGBB>}text{#RRGGBB<}
    private String setGradient2(String input) {
        final Matcher m = gradient2.matcher(input);
        while (m.find()) {
            final String format = m.group();
            final int length = format.length();
            final TextColour start = new TextColour(format.substring(2, 8));
            final String message = format.substring(10, length - 10);
            final TextColour end = new TextColour(format.substring(length - 8, length - 2));
            input = input.replace(format, asGradient(start, message, end));
        }
        return input;
    }

    private String asGradient(final TextColour start, final String text, final TextColour end) {
        final StringBuilder sb = new StringBuilder();
        final int length = text.length();
        for (int i = 0; i < length; i++) {
            final int red = (int) (start.getRed() + ((float) (end.getRed() - start.getRed())) / (length - 1) * i);
            final int green = (int) (start.getGreen() + ((float) (end.getGreen() - start.getGreen())) / (length - 1) * i);
            final int blue = (int) (start.getBlue() + ((float) (end.getBlue() - start.getBlue())) / (length - 1) * i);
            sb.append("#").append(toHexString(red, green, blue)).append(text.charAt(i));
        }
        return sb.toString();
    }
}