package com.mariouris.zimbie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mariouris.zimbie.Main;

@SuppressWarnings("unused")
public class BringCommand implements CommandExecutor {
	private final Main plugin;
	private final String permission;
	
	public BringCommand(Main plugin, String permission) {
		this.plugin = plugin;
		this.permission = permission;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(permission)) { sender.sendMessage(plugin.tc("&cYou do not have permission to run this command!")); return true; }
		if(!plugin.inStaff.containsKey(plugin.getPlayer(sender))) { sender.sendMessage(plugin.tc("&cYou aren't in staff mode!")); return true; }
		if(args.length == 1) {
			if (Bukkit.getPlayer(args[0]) != null) {
				plugin.addTele(Bukkit.getPlayer(args[0]), Bukkit.getPlayer(args[0]).getLocation());
				Bukkit.getPlayer(args[0]).teleport(plugin.getPlayer(sender));
				sender.sendMessage(plugin.tc("&6Brought the player " + Bukkit.getPlayer(args[0]).getName()));
				return true;
			} else {
				sender.sendMessage(plugin.tc("&cCould not bring that player! Did you spell their name incorrectly?"));
				return true;
			}
		} else {
			sender.sendMessage(plugin.tc("&cProper usage is /bring <player>"));
			return true;
		}
	}
	
}
