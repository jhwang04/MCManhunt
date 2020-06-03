package com.justinhwang.mcmanhunt.events;

import com.justinhwang.mcmanhunt.MCManhunt;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
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
                if(worldName.equals(configWorldName)) {
                    plugin.runnerLocation = loc;
                }
            }

            List<String> uuidList;

            try {
                uuidList = (List<String>) plugin.config.getList("hunterUUIDs");

                for(String uuid : uuidList) {
                    Player p = Bukkit.getPlayer(UUID.fromString(uuid));
                    p.setCompassTarget(plugin.runnerLocation);
                }
            } catch (NullPointerException error) {
                //do nothing
            }
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
