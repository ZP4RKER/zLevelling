package com.zp4rker.zlevels.commands;

import com.zp4rker.zlevels.MSG;
import com.zp4rker.zlevels.PlayerData;
import com.zp4rker.zlevels.config.Config;
import com.zp4rker.zlevels.config.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class GetLevel implements CommandExecutor {

    JavaPlugin plugin;
    ConfigManager manager;

    public GetLevel(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("level")) {
            if(sender instanceof ConsoleCommandSender) {
                if(args.length == 1) {
                    PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", args[0]).findUnique();
                    if(playerData != null) {
                        int level = playerData.getLevel();
                        sender.sendMessage("§6 The player §4" + args[0] + "§6's level is " + level);
                    } else {
                        sender.sendMessage(MSG.ARG);
                    }
                } else {
                    sender.sendMessage(MSG.ARG);
                }
            } else if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("zlevelling.level")) {
                    if(args.length == 0) {
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();
                        int level = playerData.getLevel();
                        int xp = playerData.getXp();
                        int left = (level * 250) - xp;
                        player.sendMessage("§6Current Level: §2" + level);
                        player.sendMessage("§6Current XP: §2" + xp);
                        player.sendMessage("§6XP to next level: §2" + left);
                        return true;
                    } else {
                        player.sendMessage(MSG.ARG);
                    }
                } else {
                    player.sendMessage(MSG.PERM);
                    return true;
                }
            }
        }
        return false;
    }

}
