package com.github.frosxt.economy.provider;

import org.bukkit.OfflinePlayer;

public interface IEconomy {

    /**
     * Gets the name of this economy
     * @return The name of the economy
     */
    String getName();

    /**
     * Gets the players available balance
     * @param player The player whose balance to retrieve
     * @return The players available balance
     */
    double getBalance(OfflinePlayer player);

    /**
     * Checks to see if the player has some balance available
     * @param player The player whose balance to check
     * @param amount The minimum amount this player should have
     * @return true if the player can have this amount withdrawn
     */
    boolean hasBalance(OfflinePlayer player, double amount);

    /**
     * Withdraw from the player's balance if they can afford it
     * @param player The player whose balance to withdraw from
     * @param amount The amount to withdraw from this player
     * @return true if the amount was withdrawn successfully
     */
    boolean withdraw(OfflinePlayer player, double amount);

    /**
     * Deposit to the player's balance
     * @param player The player whose balance to deposit to
     * @param amount The amount to deposit to this player
     * @return true if the amount was deposited successfully
     */
    boolean deposit(OfflinePlayer player, double amount);
}