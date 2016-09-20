package com.zp4rker.zlevels;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class MSG {

    private static Plugin plugin;

    public MSG(Plugin plugin) {
        this.plugin = plugin;
    }

    public static final String PERM = ChatColor.RED + "You don't have permission to do that!";
    public static final String ARG = ChatColor.RED + "Invalid Arguments!";
    public static final String PLAYER = ChatColor.RED + "That player has never joined this server!";
    public static final String OFFLINE = ChatColor.RED + "That player is not online!";

    public static final String RELOAD = ChatColor.GREEN + "zLevelling Reloaded!";

    public static final void INFO(CommandSender player) {
        player.sendMessage(ChatColor.GREEN + "zLevelling " + plugin.getDescription().getVersion());
        player.sendMessage(ChatColor.GREEN + plugin.getDescription().getDescription());
        player.sendMessage(ChatColor.GREEN + "made by " + plugin.getDescription().getAuthors().get(0));
    }

}
