/*
 *     Light
 *     Copyright (C) 2023 frosxt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License in LICENSE.MD
 *     Please refer to that file for details.
 */

package com.github.frosxt.utils;

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
