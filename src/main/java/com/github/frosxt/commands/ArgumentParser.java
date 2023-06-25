/*
 *     SparkCommons
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

import com.github.frosxt.commands.error.ArgumentError;
import com.github.frosxt.commands.error.InstanceError;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public interface ArgumentParser {
    List<String> currentArguments = new ArrayList<>();
    AtomicBoolean verbose = new AtomicBoolean(false);
    AtomicReference<CommandSender> commandSender = new AtomicReference<>();

    default Player asPlayer(final int index) {
        try {
            final Player player = Bukkit.getPlayer(currentArguments.get(index));
            if (player == null) {
                throw new NullPointerException("Player was not found!");
            }

            return player;
        } catch (final Exception exception) {
            throw new ArgumentError(exception.getMessage(), "&c" + currentArguments.get(index) + " isn't online!", exception);
        }
    }

    default int asInt(final int index) {
        try {
            return Integer.parseInt(currentArguments.get(index));
        } catch (final NumberFormatException exception) {
            throw new ArgumentError(exception.getMessage(), "&c" + currentArguments.get(index) + " isn't a valid number!", exception);
        }
    }

    default double asDouble(final int index) {
        try {
            return Double.parseDouble(currentArguments.get(index));
        } catch (final NumberFormatException exception) {
            throw new ArgumentError(exception.getMessage(), "&c" + currentArguments.get(index) + " isn't a valid number!", exception);
        }
    }

    default long asLong(final int index) {
        try {
            return Long.parseLong(currentArguments.get(index));
        } catch (final NumberFormatException exception) {
            throw new ArgumentError(exception.getMessage(), "&c" + currentArguments.get(index) + " isn't a valid number!", exception);
        }
    }

    default void assertPlayer() {
        if (commandSender.get() instanceof Player) {
            return;
        }
        throw new InstanceError("CommandSender not instance of Player", "&cOnly players can use this command!");
    }

    default void assertConsole() {
        if (commandSender.get() instanceof ConsoleCommandSender) {
            return;
        }
        throw new InstanceError("CommandSender not instance of ConsoleCommandSender", "&cOnly console can use this command!");
    }

    default void assertArguments(final int length) {
        this.assertArguments(length, "Argument length was incorrect!");
    }

    default void assertArguments(final int length, final String string) {
        if (currentArguments.size() >= length) {
            return;
        }

        try {
            throw new IllegalArgumentException(string);
        } catch (final Exception exception) {
            throw new ArgumentError(exception.getMessage(), "&cYou only specified " + currentArguments.size() + " arguments but " + length + " is required!", exception);
        }
    }
}