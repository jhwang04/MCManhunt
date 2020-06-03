package com.justinhwang.mcmanhunt.commands;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCManhuntCommand implements CommandExecutor {

    MCManhunt plugin;

    public MCManhuntCommand(MCManhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length != 0) {
            String arg = args[0];
            if(arg.equals("enable") || arg.equals("true") || arg.equals("yes") || arg.equals("1")) {
                plugin.config.set("enabled", true);
                sender.sendMessage(ChatColor.GREEN + "MCManhunt has been enabled");
            } else if(arg.equals("disable") || arg.equals("false") || arg.equals("no") || arg.equals("0")) {
                plugin.config.set("enabled", false);
                sender.sendMessage(ChatColor.GREEN + "MCManhunt has been disabled");
            } else if(arg.equals("query") || arg.equals("check")) {
                sender.sendMessage("MCManhunt.isEnabled is set to " + plugin.config.getBoolean("enabled"));
            } else {
                sendError(sender);
            }
        } else {
            sender.sendMessage("MCManhunt.isEnabled is set to " + plugin.config.getBoolean("enabled"));
        }
        plugin.saveConfig();
        return true;
    }

    public void sendError(CommandSender p) {
        p.sendMessage(ChatColor.RED + "Usage: /manhunt <enable/disable/query>");
    }
}
