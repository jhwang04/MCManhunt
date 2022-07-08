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
            } else if(arg.equals("help")) {
                sendError(sender);
            } else if(arg.equals("config")) {
                if(args.length > 1) {
                    String arg2 = args[1];
                    if (arg2.equals("brutes")) {
                        if(args.length > 2) {
                            String arg3 = args[2];
                            if (arg3.equals("enable")) {
                                plugin.config.set("allowBrutes", true);
                                sender.sendMessage("Piglin Brutes will spawn & attack (vanilla behavior).");
                            } else if (arg3.equals("disable")) {
                                plugin.config.set("allowBrutes", false);
                                sender.sendMessage("Piglin Brutes will despawn when angered.");
                            } else if (arg3.equals("query")) {
                                sender.sendMessage("AllowBrutes is set to " + plugin.config.getBoolean("allowBrutes"));
                            } else {
                                sender.sendMessage(ChatColor.RED + "Usage: /manhunt config brutes <enable/disable/query>");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage: /manhunt config brutes <enable/disable/query>");
                        }
                    } else if (arg2.equals("trades")) {
                        if(args.length > 2) {
                            String arg3 = args[2];
                            if (arg3.equals("enable")) {
                                plugin.config.set("legacyTrades", true);
                                sender.sendMessage("Trades will now use 1.16.1 loot tables");
                            } else if (arg3.equals("disable")) {
                                plugin.config.set("legacyTrades", false);
                                sender.sendMessage("Trades will use 1.16.2+ loot tables");
                            } else if (arg3.equals("query")) {
                                sender.sendMessage("LegacyTrades is set to " + plugin.config.getBoolean("legacyTrades"));
                            } else {
                                sender.sendMessage(ChatColor.RED + "Usage: /manhunt config trades <enable/disable/query>");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "Usage: /manhunt config trades <enable/disable/query>");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Usage: /manhunt config <brutes/trades>");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Usage: /manhunt config <brutes/trades>");
                }
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
        p.sendMessage(ChatColor.RED + "Usage: /manhunt <enable/disable/query/help/config>");
    }
}
