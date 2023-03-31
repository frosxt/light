package me.frost.commons.utils;

public final class NumberUtils {

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