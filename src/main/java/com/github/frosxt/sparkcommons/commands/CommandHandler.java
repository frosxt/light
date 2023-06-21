package com.github.frosxt.sparkcommons.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private static SimpleCommandMap simpleCommandMap;

    private static void setupSimpleCommandMap() {
        final SimplePluginManager simplePluginManager = (SimplePluginManager) Bukkit.getServer().getPluginManager();
        Field field = null;

        try {
            field = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (final Exception e) {
            e.printStackTrace();
        }

        field.setAccessible(true);

        try {
            simpleCommandMap = (SimpleCommandMap) field.get(simplePluginManager);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Command> getKnownCommands() {
        try {
            final Field field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);

            return (Map) field.get(simpleCommandMap);
        }
        catch (final Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }

    public static void registerCommand(final AbstractCommand command) {
        simpleCommandMap.register("Spark", command);
    }

    public static void unregisterCommand(final AbstractCommand command) {
        command.unregister(CommandHandler.simpleCommandMap);
        final HashMap<String, Command> knownCommands = new HashMap<>(getKnownCommands());

        for (final Map.Entry<String, Command> entry : knownCommands.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(command.getName())) {
                continue;
            }

            getKnownCommands().remove(entry.getKey());
        }
    }

    static {
        setupSimpleCommandMap();
    }
}