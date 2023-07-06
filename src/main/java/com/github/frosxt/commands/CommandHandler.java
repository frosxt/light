/*
 *     Light
 *     Copyright (C) 2023 frosxt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License in LICENSE.MD
 *     Please refer to that file for details.
 */

package com.github.frosxt.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Objects;

public class CommandHandler {
    private CommandHandler() {
        throw new UnsupportedOperationException("CommandHandler cannot be instantiated!");
    }

    private static CommandMap getCommandMap() {
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);

                return (CommandMap) f.get(Bukkit.getServer());
            }
            return null;
        } catch (final IllegalAccessException | NoSuchFieldException e) {
            return null;
        }
    }

    public static void registerCommand(final JavaPlugin plugin,
                                       final CommandExecutor commandExecutor,
                                       final TabCompleter tabCompleter,
                                       final AbstractCommand command) {

        final PluginCommand pluginCommand = getPluginCommand(plugin, command);
        assert (pluginCommand != null);

        final CommandMap commandMap = getCommandMap();
        assert (commandMap != null);

        commandMap.register("light", command);
        pluginCommand.setExecutor(commandExecutor);

        if (tabCompleter != null) {
            pluginCommand.setTabCompleter(tabCompleter);
        }
    }

    public static void unregisterCommand(final AbstractCommand command) {
        command.unregister(Objects.requireNonNull(getCommandMap()));
        try {
            if (!(Bukkit.getPluginManager() instanceof SimplePluginManager)) {
                return;
            }

            final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
            field.setAccessible(true);

            final CommandMap commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            final Field f = commandMap.getClass().getDeclaredField("knownCommands");
            f.setAccessible(true);

            final HashMap commands = (HashMap) f.get(commandMap);
            commands.remove(command.getName());
            command.getAliases().forEach(commands::remove);
        } catch (final IllegalAccessException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }
    }

    private static PluginCommand getPluginCommand(final JavaPlugin plugin, final AbstractCommand command) {
        try {
            final Constructor constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            final PluginCommand pluginCommand = (PluginCommand) constructor.newInstance(command.getName(), plugin);
            pluginCommand.setAliases(command.getAliases());

            return pluginCommand;
        } catch (final InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}