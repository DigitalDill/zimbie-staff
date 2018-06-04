package com.mariouris.zimbie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mariouris.zimbie.Main;

@SuppressWarnings("unused")
public class ReturnCommand implements CommandExecutor {
	private final Main plugin;
	private final String permission;
	
	public ReturnCommand(Main plugin, String permission) {
		this.plugin = plugin;
		this.permission = permission;
	}
	
	Player user;
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(permission)) { sender.sendMessage(plugin.tc("&cYou do not have permission to run this command!")); return true; }
		if(!plugin.inStaff.containsKey(plugin.getPlayer(sender))) { sender.sendMessage(plugin.tc("&cYou aren't in staff mode!")); return true; }
		if(args.length == 0) { user = plugin.getPlayer(sender); } else if(Bukkit.getPlayer(args[0]) != null) { user = Bukkit.getPlayer(args[0]); } else { sender.sendMessage(plugin.tc("&cI couldn't find that player! Did you spell their name wrong?")); return true; }
		if(plugin.telePos.get(user) != null) {
			plugin.returnPlayer(user);
			sender.sendMessage(plugin.tc("&6Player has been returned to last known location."));
		} else {
			sender.sendMessage(plugin.tc("&cThat player hasn't been teleported anywhere! Keeping them there."));
		}
		return true;
	}
	
}
