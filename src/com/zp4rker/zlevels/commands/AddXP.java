package com.zp4rker.zlevels.commands;

import com.zp4rker.zlevels.config.Config;
import com.zp4rker.zlevels.config.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class AddXP implements CommandExecutor {

    JavaPlugin plugin;
    ConfigManager manager;

    public AddXP(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = new ConfigManager(plugin);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("addxp")) {
            if(sender instanceof ConsoleCommandSender) {
            } else if(sender instanceof Player) {
                Player player = (Player) sender;
            }
        }
        return false;
    }

}
