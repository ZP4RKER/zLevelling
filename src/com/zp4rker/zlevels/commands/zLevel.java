package com.zp4rker.zlevels.commands;

import com.zp4rker.zlevels.MSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class zLevel implements CommandExecutor {

    public JavaPlugin plugin;

    public zLevel(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("zlevel")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("reload")) {
                    if(sender instanceof ConsoleCommandSender || sender.hasPermission("zlevelling.admin")) {
                        plugin.getPluginLoader().disablePlugin(plugin);
                        plugin.getPluginLoader().enablePlugin(plugin);
                        sender.sendMessage(MSG.RELOAD);
                        return true;
                    } else {
                        sender.sendMessage(MSG.PERM);
                        return true;
                    }
                } else if(args[0].equalsIgnoreCase("info")) {
                    MSG.INFO(sender);
                    return true;
                } else {
                    sender.sendMessage(MSG.ARG);
                }
            } else {
                sender.sendMessage(MSG.ARG);
            }
        }
        return false;
    }
}
