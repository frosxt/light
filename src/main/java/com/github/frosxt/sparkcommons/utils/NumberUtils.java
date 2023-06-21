/*
 *     SparkCommons
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

package com.github.frosxt.sparkcommons.utils;

public final class NumberUtils {

    // Private constructor to prevent instantiation of utility class
    private NumberUtils() {
        throw new UnsupportedOperationException("NumberUtils is a utility class and cannot be instantiated!");
    }

    public static boolean isDouble(final String args) {
        try {
            Double.parseDouble(args);
            return true;
        }
        catch (final Exception ignored) {
            return false;
        }
    }

    public static boolean isFloat(final String args) {
        try {
            Float.parseFloat(args);
            return true;
        } catch (final Exception ignored) {
            return false;
        }
    }

    public static boolean isInteger(final String args) {
        try {
            Integer.parseInt(args);
            return true;
        }
        catch (final Exception ignored) {
            return false;
        }
    }

    public static boolean isLong(final String args) {
        try {
            Long.parseLong(args);
            return true;
        }
        catch (final Exception ignored) {
            return false;
        }
    }

    public static double roundNumber(final double number) {
        return ((int) (number * 100.0)) / 100.0;
    }

    public static double getPercentage(final double result, final double max) {
        return (result / max) * 100;
    }
}