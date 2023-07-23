package com.github.frosxt.economy.impl;

import com.github.frosxt.economy.AbstractEconomy;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomy extends AbstractEconomy {
    private final Economy vault;

    public VaultEconomy() {
        final RegisteredServiceProvider<Economy> serviceProvider =
                Bukkit.getServicesManager().getRegistration(Economy.class);

        if (serviceProvider == null) {
            this.vault = null;
            return;
        }

        this.vault = serviceProvider.getProvider();
    }

    @Override
    public double getBalance(final OfflinePlayer player) {
        if (vault == null) {
            return 0;
        }

        return vault.getBalance(player);
    }

    @Override
    public boolean hasBalance(final OfflinePlayer player, final double amount) {
        return vault != null && vault.has(player, amount);
    }

    @Override
    public boolean withdraw(final OfflinePlayer player, final double amount) {
        return vault != null && vault.withdrawPlayer(player, amount).transactionSuccess();
    }

    @Override
    public boolean deposit(final OfflinePlayer player, final double amount) {
        return vault != null && vault.depositPlayer(player, amount).transactionSuccess();
    }
}