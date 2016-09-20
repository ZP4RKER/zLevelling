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

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class AddType implements CommandExecutor {

    private JavaPlugin plugin;
    private ConfigManager manager;

    public AddType(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("addtype")) {
            if(sender instanceof ConsoleCommandSender) {
                if(args.length == 2) {
                    PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", args[0]).findUnique();
                    if(playerData == null) {
                        sender.sendMessage(MSG.ARG);
                        return false;
                    }
                    List<String> types = playerData.getTypes();
                    switch(args[1].toLowerCase()) {
                        case "air":
                            types.add("air");
                            break;
                        case "earth":
                            types.add("earth");
                            break;
                        case "water":
                            types.add("water");
                            break;
                        case "fire":
                            types.add("fire");
                            break;
                        default:
                            sender.sendMessage(MSG.ARG);
                            return false;
                    }
                    playerData.setTypes(types);
                    plugin.getDatabase().save(playerData);
                    return true;
                }
            } else if(sender instanceof Player) {
                Player player = (Player) sender;
                if(player.hasPermission("zlevelling.addtype")) {
                    if(args.length == 1) {
                        PlayerData playerData = plugin.getDatabase().find(PlayerData.class).where().ieq("playerName", player.getName()).findUnique();
                        List<String> types = playerData.getTypes();
                        switch(args[0].toLowerCase()) {
                            case "air":
                                types.add("air");
                                break;
                            case "earth":
                                types.add("earth");
                                break;
                            case "water":
                                types.add("water");
                                break;
                            case "fire":
                                types.add("fire");
                                break;
                            default:
                                player.sendMessage(MSG.ARG);
                                return false;
                        }
                        playerData.setTypes(types);
                        plugin.getDatabase().save(playerData);
                        player.sendMessage("§2Added type " + args[0]);
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