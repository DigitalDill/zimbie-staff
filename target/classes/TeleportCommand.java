package com.mariouris.zimbie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mariouris.zimbie.Main;

@SuppressWarnings("unused")
public class TeleportCommand implements CommandExecutor {
	private final Main plugin;
	private final String permission;
	
	public TeleportCommand(Main plugin, String permission) {
		this.plugin = plugin;
		this.permission = permission;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(permission)) { sender.sendMessage(plugin.tc("&cYou do not have permission to run this command!")); return true; }
		if(!plugin.inStaff.containsKey(plugin.getPlayer(sender))) { sender.sendMessage(plugin.tc("&cYou aren't in staff mode!")); return true; }
		if(args.length != 1) {
			sender.sendMessage(plugin.tc("&cProper usage is /tp <player>"));
			return true;
		}
		if(Bukkit.getPlayer(args[0]) != null) {
			Player victim = Bukkit.getPlayer(args[0]);
			plugin.getPlayer(sender).teleport(victim);
			sender.sendMessage(plugin.tc("&6Teleported you to " + victim.getName()));
		} else {
			sender.sendMessage(plugin.tc("&cThat player could not be found! Did you spell their name incorrectly?"));
		}
		return true;
	}
	
}
