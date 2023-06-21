package com.github.frosxt.sparkcommons.commands;

import com.github.frosxt.sparkcommons.colour.ColouredString;
import com.github.frosxt.sparkcommons.commands.error.ArgumentError;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand extends Command implements ArgumentParser {
    private final String name;
    private final List<String> aliases;
    private List<SubCommand> subCommands;

    public AbstractCommand(final String name, final List<String> aliases) {
        super(name);
        this.setAliases(aliases);
        this.name = name;
        this.aliases = aliases;
    }

    public AbstractCommand(final String name) {
        this(name, new ArrayList<>());
    }

    public abstract boolean onCommand(CommandSender commandSender, String[] arguments);

    public void register() {
        CommandHandler.registerCommand(this);
    }

    public void unregister() {
        CommandHandler.unregisterCommand(this);
    }

    @Override
    public boolean isRegistered() {
        return CommandHandler.getKnownCommands().containsKey(getName());
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
        } catch (final Throwable throwable) {
            sender.sendMessage(new ColouredString("&cThere was an error processing your command!").toString());
            throwable.printStackTrace();
            if (verbose.get()) {
                sender.sendMessage(new ColouredString("&6Caused By: " + throwable.getClass().getName() + ": " + throwable.getMessage()).toString());
                boolean flip = false;

                for (final StackTraceElement element : throwable.getStackTrace()) {
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
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] arguments) throws IllegalArgumentException {
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
        for (final SubCommand subCommand2 : this.subCommands) {
            if (subCommand2.getName().equalsIgnoreCase(firstArgument)) {
                if (!sender.hasPermission(subCommand2.getPermission())) {
                    continue;
                }
                tab = subCommand2.onTabComplete(sender, arguments);
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
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    public List<SubCommand> getSubCommands() {
        return this.subCommands;
    }

    public void setSubCommands(final List<SubCommand> subCommands) {
        this.subCommands = subCommands;
    }
}