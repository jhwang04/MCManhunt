package com.justinhwang.mcmanhunt;

import com.justinhwang.mcmanhunt.commands.*;
import com.justinhwang.mcmanhunt.events.MCManhuntEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MCManhunt extends JavaPlugin {

    public FileConfiguration config;
    public Location runnerLocation;
    public Location lastRunnerLocationInDimension;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        config = this.getConfig();

        getCommand("manhunt").setExecutor(new MCManhuntCommand(this));

        getCommand("compass").setExecutor(new CompassCommand(this));

        getCommand("setmanhuntworld").setExecutor(new SetOverworldCommand(this));

        getCommand("hunters").setExecutor(new HuntersCommand(this));

        getCommand("runner").setExecutor(new RunnerCommand(this));

        getServer().getPluginManager().registerEvents(new MCManhuntEvent(this), this);

        runnerLocation = null;

        getLogger().info("MCManhunt has been enabled");
    }

    public void giveCompass(Player p) {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = compass.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Tracking " + config.getString("runner"));
        compass.setItemMeta(meta);
        p.getWorld().dropItem(p.getLocation(), compass);
    }

    public boolean isPlayerHunter(Player p) {
        List<String> names = (List<String>) config.getList("hunters");
        for(String name : names) {
            if (name.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }
}
