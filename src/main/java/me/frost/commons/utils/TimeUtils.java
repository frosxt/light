package me.frost.commons.utils;

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
