package com.github.frosxt.economy.provider.impl;

import com.github.frosxt.economy.provider.IEconomy;
import com.github.frosxt.utils.ExperienceUtils;
import org.bukkit.OfflinePlayer;

public class ExperienceEconomy implements IEconomy {
    @Override
    public String getName() {
        return "Experience";
    }

    @Override
    public double getBalance(final OfflinePlayer player) {
        if (!player.isOnline() || player.getPlayer() == null) {
            return 0.0D;
        }

        return ExperienceUtils.getTotalExperience(player.getPlayer());
    }

    @Override
    public boolean hasBalance(final OfflinePlayer player, final double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean withdraw(final OfflinePlayer player, final double amount) {
        if (!player.isOnline() || player.getPlayer() == null) {
            return false;
        }

        ExperienceUtils.setTotalExperience(player.getPlayer(), (int) (getBalance(player) - amount));
        return true;
    }

    @Override
    public boolean deposit(final OfflinePlayer player, final double amount) {
        if (!player.isOnline() || player.getPlayer() == null) {
            return false;
        }

        ExperienceUtils.setTotalExperience(player.getPlayer(), (int) (getBalance(player) + amount));
        return true;
    }
}
