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

import com.github.frosxt.colour.ColouredString;
import com.github.frosxt.commands.error.ArgumentError;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand extends Command implements ArgumentParser {
    private final JavaPlugin plugin;
    private final String name;
    private final List<String> aliases;
    private List<SubCommand> subCommands;

    public AbstractCommand(final JavaPlugin plugin, final String name, final List<String> aliases) {
        super(name);

        this.plugin = plugin;
        this.setAliases(aliases);
        this.name = name;
        this.aliases = aliases;
    }

    public AbstractCommand(final JavaPlugin plugin, final String name) {
        this(plugin, name, new ArrayList<>());
    }

    public abstract boolean onCommand(CommandSender commandSender, String[] arguments);

    public void register() {
        CommandHandler.registerCommand(this);
    }

    public void unregister() {
        CommandHandler.unregisterCommand(this);
    }

    @Override
    public boolean execute(final CommandSender sender, final String label, String[] arguments) {
        if (arguments.length > 0 && sender.hasPermission("") && arguments[arguments.length - 1].equals("--ve")) {
            final String[] newArgs = new String[arguments.length - 1];
            System.arraycopy(arguments, 0, newArgs, 0, arguments.length - 1);
            arguments = newArgs;
            verbose.set(true);
        }

        commandSender.set(sender);
        currentArguments.addAll(Arrays.asList(arguments));
        boolean result = true;

        try {
            result = this.onCommand(sender, arguments);
        } catch (final ArgumentError error) {
            sender.sendMessage(new ColouredString(error.getFormattedMessage()).toString());
            if (verbose.get()) {
                sender.sendMessage(new ColouredString("&cCaused By: " + error.getException().getClass().getName() + ": " + error.getException().getMessage()).toString());
                boolean flip = false;

                for (final StackTraceElement element : error.getException().getStackTrace()) {
                    sender.sendMessage(new ColouredString(((flip ^= true) ? "&4" : "&c") + element.toString()).toString());
                }
            }
        } catch (final Exception exception) {
            sender.sendMessage(new ColouredString("&cThere was an error processing your command!").toString());
            exception.printStackTrace();
            if (verbose.get()) {
                sender.sendMessage(new ColouredString("&6Caused By: " + exception.getClass().getName() + ": " + exception.getMessage()).toString());
                boolean flip = false;

                for (final StackTraceElement element : exception.getStackTrace()) {
                    sender.sendMessage(new ColouredString(((flip ^= true) ? "&4" : "&c") + element.toString()).toString());
                }
            }
        }

        commandSender.set(null);
        currentArguments.clear();
        verbose.set(false);

        return result;
    }

    @Override
    public @NotNull List<String> tabComplete(final CommandSender sender, final String alias, final String[] arguments) throws IllegalArgumentException {
        List<String> tab = new ArrayList<>();

        if (this.subCommands == null) {
            return tab;
        }

        if (arguments.length == 0 || arguments.length == 1) {
            for (final SubCommand subCommand : this.subCommands) {
                if (!sender.hasPermission(subCommand.getPermission())) {
                    continue;
                }
                tab.add(subCommand.getName());
            }
            return tab;
        }

        final String firstArgument = arguments[0];
        for (final SubCommand subCommand : this.subCommands) {
            if (subCommand.getName().equalsIgnoreCase(firstArgument)) {
                if (!sender.hasPermission(subCommand.getPermission())) {
                    continue;
                }
                tab = subCommand.onTabComplete(sender, arguments);
            }
        }

        final String arg = arguments[arguments.length - 1];
        final ArrayList<String> newTab = new ArrayList<>();
        for (final String argument : tab) {
            if (!argument.toLowerCase().startsWith(arg.toLowerCase())) {
                continue;
            }
            newTab.add(argument);
        }

        if (newTab.isEmpty()) {
            return tab;
        }

        return newTab;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @NotNull List<String> getAliases() {
        return this.aliases;
    }

    public List<SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public void setSubCommands(final List<SubCommand> subCommands) {
        this.subCommands = subCommands;
    }
}