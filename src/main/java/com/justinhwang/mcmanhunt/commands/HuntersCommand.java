package com.justinhwang.mcmanhunt.commands;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HuntersCommand implements CommandExecutor {
    MCManhunt plugin;

    public HuntersCommand(MCManhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length != 0) {
            String s1 = strings[0];

            List<String> names = (List<String>) plugin.config.getList("hunters");
            List<String> uuids = (List<String>) plugin.config.getList("hunterUUIDs");

            if(names == null || uuids == null) {
                names = new ArrayList<String>();
                uuids = new ArrayList<String>();
                plugin.config.set("hunters", names);
                plugin.config.set("hunterUUIDs", uuids);
            }

            if(s1.equals("clear") || s1.equals("reset")) {
                names = new ArrayList<String>();
                uuids = new ArrayList<String>();
                commandSender.sendMessage(ChatColor.GREEN + "The list of hunters has been cleared");
            } else if(s1.equals("add") || s1.equals("set")) {
                if(strings.length > 1) {
                    String s2 = strings[1];
                    names.add(s2);
                    Player addedPlayer = (Player) Bukkit.getServer().getOfflinePlayer(s2);
                    uuids.add(addedPlayer.getUniqueId().toString());
                    commandSender.sendMessage(ChatColor.GREEN + s2 + " has been added to the list of hunters");
                } else {
                    commandSender.sendMessage(ChatColor.RED + "Usage: /hunters add <player>");
                }
            } else if(s1.equals("remove") || s1.equals("rm")) {
                if(strings.length > 1) {
                    String s2 = strings[1];
                    names.remove(s2);
                    Player removedPlayer = (Player) Bukkit.getServer().getOfflinePlayer(s2);
                    uuids.remove(removedPlayer.getUniqueId().toString());
                    commandSender.sendMessage(ChatColor.GREEN + s2 + " has been removed from the list of hunters");
                } else {
                    commandSender.sendMessage(ChatColor.RED + "Usage: /hunters remove <player>");
                }
            } else if(s1.equals("query") || s1.equals("check") || s1.equals("list")) {
                commandSender.sendMessage("The list of hunters is currently " + names);
                commandSender.sendMessage("The list of hunter UUIDs is currently " + uuids);
            } else {
                commandSender.sendMessage(ChatColor.RED + "Usage: /hunters <add/remove/clear>");
            }

            plugin.config.set("hunters", names);
            plugin.config.set("hunterUUIDs", uuids);

        } else {
            commandSender.sendMessage(ChatColor.RED + "Usage: /hunters <add/remove/clear/list>");
        }

        plugin.saveConfig();
        return true;
    }
}
