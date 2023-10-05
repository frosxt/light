package com.github.frosxt.utils;

import org.bukkit.entity.Player;

public class ExperienceUtils {

    private ExperienceUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void setTotalExperience(final Player player, final int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Experience cannot be negative!");
        }

        player.setExp(0.0F);
        player.setLevel(0);
        player.setTotalExperience(0);

        int amount = exp;
        while (amount > 0) {
            final int expToLevel = getExpAtLevel(player.getLevel());
            if ((amount -= expToLevel) >= 0) {
                player.giveExp(expToLevel);
                continue;
            }

            player.giveExp(amount += expToLevel);
            amount = 0;
        }
    }

    public static int getExpAtLevel(final int level) {
        if (level <= 15) {
            return 2 * level + 7;
        }
        if (level <= 30) {
            return 5 * level - 38;
        }

        return 9 * level - 158;
    }

    public static int getTotalExperience(final Player player) {
        int exp = Math.round(getExpAtLevel(player.getLevel()) * player.getExp());
        int currentLevel = player.getLevel();

        while (currentLevel > 0) {
            exp += getExpAtLevel(--currentLevel);
        }
        if (exp < 0) {
            exp = Integer.MAX_VALUE;
        }

        return exp;
    }
}