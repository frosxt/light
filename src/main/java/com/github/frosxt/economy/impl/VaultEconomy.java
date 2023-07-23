package com.github.frosxt.economy.impl;

import com.github.frosxt.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultEconomy extends Economy {
    private final net.milkbowl.vault.economy.Economy vault;

    public VaultEconomy() {
        final RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> serviceProvider =
                Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

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