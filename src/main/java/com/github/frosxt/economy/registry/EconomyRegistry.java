package com.github.frosxt.economy.registry;

import com.github.frosxt.economy.provider.IEconomy;

import java.util.Collection;

public interface EconomyRegistry {
    public void addEconomy(IEconomy economy);

    public boolean hasEconomy(String economy);

    public IEconomy getEconomy(String economy);

    public Collection<IEconomy> getEconomies();
}