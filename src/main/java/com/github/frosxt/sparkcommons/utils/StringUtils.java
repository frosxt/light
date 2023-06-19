package com.github.frosxt.sparkcommons.utils;

public final class StringUtils {
    private final String context;

    // Private constructor to stop instantiation
    private StringUtils(final String context) {
        this.context = context;
    }

    public static StringUtils use(final String context) {
        return new StringUtils(context);
    }

    public boolean containsIgnoreCase(final String match) {
        final int length = match.length();
        if (length == 0) {
            return true;
        }

        final char firstLowercase = Character.toLowerCase(match.charAt(0));
        final char firstUppercase = Character.toUpperCase(match.charAt(0));

        for (int i = context.length() - length; i >= 0; i--) {
            final char character = context.charAt(i);
            if (character != firstLowercase && character != firstUppercase) {
                continue;
            }

            if (context.regionMatches(true, i, match, 0, length)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInteger() {
        return NumberUtils.isInteger(context);
    }

    public boolean isDouble() {
        return NumberUtils.isDouble(context);
    }

    public boolean isLong() {
        return NumberUtils.isLong(context);
    }

    public boolean isFloat() {
        return NumberUtils.isFloat(context);
    }
}
