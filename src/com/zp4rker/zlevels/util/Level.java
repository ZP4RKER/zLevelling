package com.zp4rker.zlevels.util;

import com.zp4rker.zlevels.PlayerData;
import org.bukkit.plugin.java.JavaPlugin;


@SuppressWarnings("unused")
public class Level {

    private static JavaPlugin plugin;

    public Level(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void refreshPlayerLevels(PlayerData playerData) {

        int level = playerData.getLevel();
        int xp = playerData.getXp();

        while(xp >= (level * 250)) {
            int amountOver = xp - (level * 250);
            level++;
            xp = amountOver;
        }

        playerData.setXp(xp);
        playerData.setLevel(level);

        plugin.getDatabase().save(playerData);

    }

}
