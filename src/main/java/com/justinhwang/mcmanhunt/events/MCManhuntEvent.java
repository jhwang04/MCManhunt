package com.justinhwang.mcmanhunt.events;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import sun.java2d.pipe.NullPipe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MCManhuntEvent implements Listener {
    public MCManhunt plugin;

    public MCManhuntEvent(MCManhunt plugin) {this.plugin = plugin;}
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if(plugin.config.getBoolean("enabled") == true) {

            //if the speedrunner moves in the overworld (compass doesn't work in nether or end)
            if(plugin.config.getString("runner").equals(e.getPlayer().getName())) {
                Location loc = e.getPlayer().getLocation();
                String worldName = loc.getWorld().getName();
                String configWorldName = plugin.config.getString("overworld");
                plugin.runnerLocation = loc;

                if(plugin.lastRunnerLocationInDimension == null) {
                    plugin.lastRunnerLocationInDimension = loc;
                }

                //
                //
                // NOTE FROM YESTERDAY
                // SET LAST RUNNER LOC EVERY TIME PLAYER MOVES, BUT ONLY WHEN WORLD CHANGES
                // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //
                //
                //
            }
        }
    }


    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        if(p.getName().equals(plugin.getConfig().getString("runner"))) {
            if(!e.getFrom().getWorld().getName().equals(e.getTo().getWorld().getName())) {
                plugin.lastRunnerLocationInDimension = e.getFrom();
            }
        }
    }

    @EventHandler
    public void onTrackerClick(PlayerInteractEvent e) {
        ItemStack item = null;
        Player p = e.getPlayer();

        try {
            item = e.getItem();

        } catch(NullPointerException error) {
            //do nothing
        }
        if(item != null && p.getWorld().getName().contains(plugin.getConfig().getString("overworld")) && item.getType() == Material.COMPASS && plugin.getConfig().getList("hunters").contains(p.getName())) {
            p.sendMessage(ChatColor.GREEN + "Tracking " + plugin.getConfig().getString("runner") + "...");
            CompassMeta meta = (CompassMeta) item.getItemMeta();
            if(p.getWorld().getName().equals(plugin.runnerLocation.getWorld().getName())) {
                meta.setLodestone(plugin.runnerLocation);
            } else {
                meta.setLodestone(plugin.lastRunnerLocationInDimension);
            }
            meta.setLodestoneTracked(false);
            item.setItemMeta(meta);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if(plugin.isPlayerHunter(p)) {
            plugin.giveCompass(p);
        }
    }
}
