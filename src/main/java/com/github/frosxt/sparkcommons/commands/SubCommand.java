package com.github.frosxt.sparkcommons.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
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
        return Arrays.asList(" ");
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
