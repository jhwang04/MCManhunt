package com.justinhwang.mcmanhunt.commands;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.List;

public class CompassCommand implements CommandExecutor {

    MCManhunt plugin;

    public CompassCommand(MCManhunt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            List<String> uuids = (List<String>) plugin.config.getList("hunterUUIDs");
            if(uuids.contains(p.getUniqueId()) || p.isOp()) {
                if(p.getLocation().getWorld().getName().contains(plugin.config.getString("overworld"))) {
                    commandSender.sendMessage(ChatColor.GREEN + "Abracadabra! A compass has been summoned!");
                    plugin.giveCompass((Player) commandSender);
                } else {
                    commandSender.sendMessage(ChatColor.RED + "You can only use this in the manhunt worlds!");
                }
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "Only players can run this command! Scram!");
        }
        plugin.saveConfig();
        return true;
    }
}
