package com.zp4rker.zlevels.util;

import com.zp4rker.zlevels.PlayerData;
import com.zp4rker.zlevels.config.Config;
import com.zp4rker.zlevels.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unused")
public class Perm {

    public static JavaPlugin plugin;

    private static ConfigManager manager;
    private static Config levels;


    public Perm(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
        levels = manager.getNewConfig("levels.yml");
    }

    public static HashMap<Player, PermissionAttachment> attachments = new HashMap<>();

    public static void add(Player player, String perm) {

        PermissionAttachment attachment = attachments.get(player);

        if(attachment == null) {
            attachment = player.addAttachment(plugin);
        }

        attachment.setPermission(perm, true);

        attachments.put(player, attachment);

    }

    public static void reset(Player player) {
        try {
            player.removeAttachment(attachments.get(player));
        } catch(IllegalArgumentException e) {}
    }

    public static void reloadPerms() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();
            int level = playerData.getLevel();
            List<String> types = playerData.getTypes();
            List<String> perms = new ArrayList<>();
            if(types.isEmpty()) {
                return;
            } else {
                for(String type : types) {
                    for(Object Perm : levels.getList("levels." + level + "." + type)) {
                        String perm = Perm.toString();
                        perms.add(perm);
                    }
                }
            }
            for(String perm : perms) {
                Perm.reset(player);
                Perm.add(player, perm);
            }
        }
    }

}
