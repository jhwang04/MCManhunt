package com.justinhwang.mcmanhunt.commands;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetOverworldCommand implements CommandExecutor {
    MCManhunt plugin;

    public SetOverworldCommand(MCManhunt plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 0) {
            String world = strings[0];
            plugin.config.set("overworld", world);
            commandSender.sendMessage(ChatColor.GREEN + "Overworld has been set to " + world);
        } else {
            commandSender.sendMessage(ChatColor.RED + "Usage: /setoverworld <name of overworld>");
        }
        plugin.saveConfig();
        return true;
    }
}
