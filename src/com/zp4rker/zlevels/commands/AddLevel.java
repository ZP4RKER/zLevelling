package com.zp4rker.zlevels.commands;

import com.zp4rker.zlevels.MSG;
import com.zp4rker.zlevels.PlayerData;
import com.zp4rker.zlevels.config.Config;
import com.zp4rker.zlevels.config.ConfigManager;
import com.zp4rker.zlevels.util.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class AddLevel implements CommandExecutor {

    JavaPlugin plugin;
    ConfigManager manager;

    public AddLevel(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("addlevel")) {
            if(sender instanceof ConsoleCommandSender) {
                if(args.length == 2) {
                    PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", args[0]).findUnique();
                    if(playerData != null) {
                        int level = playerData.getLevel();
                        int amount = Integer.parseInt(args[1]);
                        level += amount;
                        playerData.setLevel(level);
                        plugin.getDatabase().save(playerData);
                        Level.refreshPlayerLevels(playerData);
                        sender.sendMessage("§6" + args[0] + " just got " + amount + " levels!");
                        try {
                            Player player = Bukkit.getPlayer(args[0]);
                            player.sendMessage("§6You just got §2+" + amount + " levels!");
                        } catch(NullPointerException e) {}
                        return true;
                    } else {
                        sender.sendMessage(MSG.PLAYER);
                    }
                } else {
                    sender.sendMessage(MSG.ARG);
                }
            } else if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("zlevelling.addlevel")) {
                    if(args.length == 2) {
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", args[0]).findUnique();
                        if(playerData != null) {
                            int level = playerData.getLevel();
                            int amount = Integer.parseInt(args[1]);
                            level += amount;
                            playerData.setLevel(level);
                            plugin.getDatabase().save(playerData);
                            Level.refreshPlayerLevels(playerData);
                            sender.sendMessage("§6" + args[0] + " just got " + amount + " levels!");
                            try {
                                Player receiver = Bukkit.getPlayer(args[0]);
                                receiver.sendMessage("§6You just got §2+" + amount + " levels!");
                            } catch(NullPointerException e) {}
                            return true;
                        } else {
                            sender.sendMessage(MSG.PLAYER);
                        }
                    } else {
                        sender.sendMessage(MSG.ARG);
                    }
                } else {
                    sender.sendMessage(MSG.PERM);
                    return true;
                }
            }
        }
        return false;
    }

}
