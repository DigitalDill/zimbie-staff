package com.mariouris.zimbie.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mariouris.zimbie.Main;

@SuppressWarnings("unused")
public class TemplateCommand implements CommandExecutor {
	private final Main plugin;
	private final String permission;
	
	public TemplateCommand(Main plugin, String permission) {
		this.plugin = plugin;
		this.permission = permission;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(permission)) { sender.sendMessage(plugin.tc("&cYou do not have permission to run this command!")); return true; }
		return true;
	}
	
}
