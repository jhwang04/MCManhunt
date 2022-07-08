package com.justinhwang.mcmanhunt.events;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

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

    @EventHandler
    public void onBruteAggro(EntityTargetLivingEntityEvent e) {
        Entity entity = e.getEntity();
        if(entity instanceof PiglinBrute) {
            if(!plugin.config.getBoolean("allowBrutes")) {
                ((PiglinBrute) entity).remove();
            }
        }
    }

    @EventHandler
    public void onPiglinTrade(PiglinBarterEvent e) {
        if(!plugin.config.getBoolean("legacyTrades"))
            return;

        int n = (int) (Math.random() * 423);
        ItemStack r = new ItemStack(Material.DIRT, 1);

        if(n < 5) {
            r = new ItemStack(Material.ENCHANTED_BOOK);
            r.addEnchantment(Enchantment.SOUL_SPEED, randInt(3));
        } else if(n < 13) {
            r = new ItemStack(Material.IRON_BOOTS);
            r.addEnchantment(Enchantment.SOUL_SPEED, randInt(3));
        } else if(n < 23)
            r = new ItemStack(Material.IRON_NUGGET, 9 + randInt(27));
        else if(n < 33) {
            r = new ItemStack(Material.POTION);
            PotionMeta meta = (PotionMeta) r.getItemMeta();
            meta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
            r.setItemMeta(meta);
        } else if(n < 43) {
            r = new ItemStack(Material.SPLASH_POTION);
            PotionMeta meta = (PotionMeta) r.getItemMeta();
            meta.setBasePotionData(new PotionData(PotionType.FIRE_RESISTANCE, false, false));
            r.setItemMeta(meta);
        } else if(n < 63)
            r = new ItemStack(Material.QUARTZ, 8 + randInt(8));
        else if(n < 83)
            r = new ItemStack(Material.GLOWSTONE_DUST, 4 + randInt(7));
        else if(n < 103)
            r = new ItemStack(Material.MAGMA_CREAM, 2 + randInt(4));
        else if(n < 123)
            r = new ItemStack(Material.ENDER_PEARL, 4 + randInt(4));
        else if(n < 143)
            r = new ItemStack(Material.STRING, 8 + randInt(16));
        else if(n < 183)
            r = new ItemStack(Material.FIRE_CHARGE, 1 + randInt(4));
        else if(n < 223)
            r = new ItemStack(Material.GRAVEL, 8 + randInt(8));
        else if(n < 263)
            r = new ItemStack(Material.LEATHER, 4 + randInt(6));
        else if(n < 303)
            r = new ItemStack(Material.NETHER_BRICK, 4 + randInt(12));
        else if(n < 343)
            r = new ItemStack(Material.OBSIDIAN, 1);
        else if(n < 383)
            r = new ItemStack(Material.CRYING_OBSIDIAN, 1 + randInt(2));
        else if(n < 423)
            r = new ItemStack(Material.SOUL_SAND, 4 + randInt(12));

        e.getOutcome().set(0, r);
    }

    private int randInt(int max) {
        return (int) (Math.random() * (max + 1));
    }
}
