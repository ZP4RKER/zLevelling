package com.zp4rker.zlevels;

import com.zp4rker.zlevels.commands.*;
import com.zp4rker.zlevels.config.Config;
import com.zp4rker.zlevels.config.ConfigManager;
import com.zp4rker.zlevels.events.EntityDeath;
import com.zp4rker.zlevels.events.PlayerJoin;
import com.zp4rker.zlevels.util.Level;
import com.zp4rker.zlevels.util.Perm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class zLevelling extends JavaPlugin {

    public void onEnable() {

        new MSG(this);
        new Perm(this);
        new Level(this);

        getLogger().info("zLevelling Enabled!");

        ConfigManager manager = new ConfigManager(this);
        Config levels = manager.getNewConfig("levels.yml");

        if(levels.get("levels.1") == null) {
            for(int i = 1; i <= 100; i++) {

                List<String> permsair = new ArrayList<>();
                permsair.add("permission.node");
                List<String> permsearth = new ArrayList<>();
                permsearth.add("permission.node");
                List<String> permswater = new ArrayList<>();
                permswater.add("permission.node");
                List<String> permsfire = new ArrayList<>();
                permsfire.add("permission.node");

                levels.set("levels." + i + ".air", permsair);
                levels.set("levels." + i + ".earth", permsearth);
                levels.set("levels." + i + ".water", permswater);
                levels.set("levels." + i + ".fire", permsfire);
                levels.saveConfig();
            }
        }

        registerEvents(this, new PlayerJoin(this), new EntityDeath(this));

        getCommand("zlevel").setExecutor(new zLevel(this));
        getCommand("addtype").setExecutor(new AddType(this));
        getCommand("level").setExecutor(new GetLevel(this));
        getCommand("addlevel").setExecutor(new AddLevel(this));
        getCommand("addxp").setExecutor(new AddXP(this));

        Perm.reloadPerms();

        fixLevels();

        setupDatabase();

    }

    public void onDisable() {

        getLogger().info("zLevelling Disabled");

    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public void setupDatabase() {
        try {
            getDatabase().find(PlayerData.class).findRowCount();
        } catch(PersistenceException e) {
            getLogger().info("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }

    public void fixLevels() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();
            Level.refreshPlayerLevels(playerData);
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(PlayerData.class);
        return list;
    }

}