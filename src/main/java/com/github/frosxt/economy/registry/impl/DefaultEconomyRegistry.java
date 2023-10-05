package com.github.frosxt.economy.registry.impl;

import com.github.frosxt.economy.provider.IEconomy;
import com.github.frosxt.economy.provider.impl.ExperienceEconomy;
import com.github.frosxt.economy.provider.impl.VaultEconomy;
import com.github.frosxt.economy.registry.EconomyRegistry;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultEconomyRegistry implements EconomyRegistry {
    private final Map<String, IEconomy> economyMap = new HashMap<>();
    private static DefaultEconomyRegistry instance;

    public DefaultEconomyRegistry() {
        registerDefaultEconomies();
        instance = this;
    }

    @Override
    public void addEconomy(final IEconomy economy) {
        economyMap.put(economy.getName().toLowerCase(), economy);
    }

    @Override
    public boolean hasEconomy(final String economy) {
        return economyMap.containsKey(economy.toLowerCase());
    }

    @Override
    public IEconomy getEconomy(final String economy) {
        return economyMap.get(economy.toLowerCase());
    }

    @Override
    public Collection<IEconomy> getEconomies() {
        return economyMap.values();
    }

    private void registerDefaultEconomies() {
        Arrays.asList(new VaultEconomy(), new ExperienceEconomy()).forEach(economy -> economyMap.put(economy.getName().toLowerCase(), economy));
    }

    public static DefaultEconomyRegistry getInstance() {
        return instance;
    }
}