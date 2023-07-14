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
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private CommandHandler() {
        throw new UnsupportedOperationException("CommandHandler cannot be instantiated!");
    }

    private static SimpleCommandMap simpleCommandMap;

    private static void setupSimpleCommandMap() {
        final SimplePluginManager simplePluginManager = (SimplePluginManager) (Bukkit.getServer().getPluginManager());
        Field field = null;
        try {
            field = SimplePluginManager.class.getDeclaredField("commandMap");
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
        field.setAccessible(true);
        try {
            simpleCommandMap = (SimpleCommandMap) field.get(simplePluginManager);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Map<String, Command> getKnownCommands() {
        try {
            final Field field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);
            return (Map) field.get(simpleCommandMap);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static void registerCommand(final AbstractCommand command) {
        simpleCommandMap.register("Light", command);
    }

    public static void unregisterCommand(final AbstractCommand command) {
        command.unregister(simpleCommandMap);

        final HashMap<String, Command> knownCommands = new HashMap<>(CommandHandler.getKnownCommands());
        for (final Map.Entry<String, Command> entry : knownCommands.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(command.getName())) {
                continue;
            }

            CommandHandler.getKnownCommands().remove(entry.getKey());
        }
    }

    static {
        CommandHandler.setupSimpleCommandMap();
    }
}