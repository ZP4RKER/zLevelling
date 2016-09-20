package com.zp4rker.zlevels.events;

import com.zp4rker.zlevels.PlayerData;
import com.zp4rker.zlevels.config.Config;
import com.zp4rker.zlevels.config.ConfigManager;
import com.zp4rker.zlevels.util.Perm;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class PlayerJoin implements Listener {

    private JavaPlugin plugin;
    private ConfigManager manager;
    private Config levels;

    public PlayerJoin(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
        levels = manager.getNewConfig("levels.yml");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();

        if(playerData == null) {
            playerData = new PlayerData();
            playerData.setPlayerName(player.getName());
            playerData.setLevel(0);
            playerData.setXp(0);
        }

        plugin.getDatabase().save(playerData);

        Perm.reloadPerms();

    }

}