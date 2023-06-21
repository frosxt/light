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

package com.github.frosxt.sparkcommons.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SubCommand {
    private final String name;
    private final String permission;
    private final List<String> aliases;

    public SubCommand(final String name, final String permission) {
        this.name = name;
        this.permission = permission;
        this.aliases = new ArrayList<>();
    }

    public SubCommand(final String name, final String permission, final List<String> aliases) {
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
    }

    public abstract void onCommand(CommandSender commandSender, String[] arguments);

    public List<String> onTabComplete(final CommandSender commandSender, final String[] arguments) {
        return Collections.singletonList(" ");
    }

    public String getName() {
        return this.name;
    }

    public String getPermission() {
        return this.permission;
    }

    public List<String> getAliases() {
        return this.aliases;
    }
}
