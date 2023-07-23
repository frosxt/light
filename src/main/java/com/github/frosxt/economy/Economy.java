package com.github.frosxt.economy;

import org.bukkit.OfflinePlayer;

public abstract class Economy {

    /**
     * Gets the players available balance
     * @param player The player whose balance to retrieve
     * @return The players available balance
     */
    public abstract double getBalance(OfflinePlayer player);

    /**
     * Checks to see if the player has some balance available
     * @param player The player whose balance to check
     * @param amount The minimum amount this player should have
     * @return true if the player can have this amount withdrawn
     */
    public abstract boolean hasBalance(OfflinePlayer player, double amount);

    /**
     * Withdraw from the player's balance if they can afford it
     * @param player The player whose balance to withdraw from
     * @param amount The amount to withdraw from this player
     * @return true if the amount was withdrawn successfully
     */
    public abstract boolean withdraw(OfflinePlayer player, double amount);

    /**
     * Deposit to the player's balance
     * @param player The player whose balance to deposit to
     * @param amount The amount to deposit to this player
     * @return true if the amount was deposited successfully
     */
    public abstract boolean deposit(OfflinePlayer player, double amount);
}