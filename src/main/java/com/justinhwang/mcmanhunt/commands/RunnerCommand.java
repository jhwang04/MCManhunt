package com.justinhwang.mcmanhunt.commands;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RunnerCommand implements CommandExecutor {
    MCManhunt plugin;

    public RunnerCommand(MCManhunt plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 0) {
            String name = strings[0];
            plugin.config.set("runner", name);
            commandSender.sendMessage(ChatColor.GREEN + "The speedrunner has been set to " + name);
        } else {
            commandSender.sendMessage(ChatColor.RED + "Usage: /runner <name of speedrunner>");
        }
        plugin.saveConfig();
        return true;
    }
}
