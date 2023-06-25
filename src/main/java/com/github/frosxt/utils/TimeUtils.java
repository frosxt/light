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

public class TimeUtils {

    // Private constructor to prevent instantiation of utility class
    private TimeUtils() {
        throw new UnsupportedOperationException("TimeUtils is a utility class and cannot be instantiated!");
    }

    public static String formatTime(final long milliseconds) {
        final long days = milliseconds / 1000L / 60L / 60L / 24L;
        final long hours = milliseconds / 1000L / 60L / 60L - days * 24L;
        final long minutes = milliseconds / 1000L / 60L - days * 24L * 60L - hours * 60L;
        final long seconds = milliseconds / 1000L - days * 24L * 60L * 60L - hours * 60L * 60L - minutes * 60L;

        return getString(days, hours, minutes, seconds);
    }

    private static String getString(final long days, final long hours, final long minutes, final long seconds) {
        final StringBuilder sb = new StringBuilder();

        if (days >= 1L) {
            sb.append(days).append(" day").append(days > 1L ? "s" : "");
        }
        if (hours >= 1L) {
            if (!sb.toString().isEmpty()) {
                sb.append(" ");
            }
            sb.append(hours).append(" hour").append(hours > 1L ? "s" : "");
        }
        if (minutes >= 1L) {
            if (!sb.toString().isEmpty()) {
                sb.append(" ");
            }
            sb.append(minutes).append(" minute").append(minutes > 1L ? "s" : "");
        }
        if (seconds >= 1L) {
            if (!sb.toString().isEmpty()) {
                sb.append(" ");
            }
            sb.append(seconds).append(" second").append(seconds > 1L ? "s" : "");
        }
        if (sb.toString().isEmpty()) {
            return "just started";
        }

        return sb.toString();
    }
}
