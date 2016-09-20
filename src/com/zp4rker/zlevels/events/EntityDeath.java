package com.zp4rker.zlevels.events;

import com.zp4rker.zlevels.PlayerData;
import com.zp4rker.zlevels.config.Config;
import com.zp4rker.zlevels.config.ConfigManager;
import com.zp4rker.zlevels.util.Level;
import com.zp4rker.zlevels.util.Perm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class EntityDeath implements Listener {

    JavaPlugin plugin;
    ConfigManager manager;
    Config levels;

    public EntityDeath(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
        levels = manager.getNewConfig("levels.yml");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

        Entity entity = event.getEntity();
        Entity Damager = event.getEntity().getKiller();

        if(Damager instanceof Player) {

            Player player = (Player) Damager;

            PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();

            int level = playerData.getLevel();

            int xp = playerData.getXp();

            if(entity instanceof Player) {
                xp += 150;
                player.sendMessage("§6 You just got 150xp!");
            } else {
                xp += 20;
                player.sendMessage("§6 You just got 20xp!");
            }

            playerData.setLevel(level);
            playerData.setXp(xp);

            plugin.getDatabase().save(playerData);

            Perm.reloadPerms();

        }

    }

}