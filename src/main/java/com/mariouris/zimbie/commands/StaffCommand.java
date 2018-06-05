package com.mariouris.zimbie.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.mariouris.zimbie.Main;

@SuppressWarnings("unused")
public class StaffCommand implements CommandExecutor {
	private final Main plugin;
	private final String permission;
	
	public StaffCommand(Main plugin, String permission) {
		this.plugin = plugin;
		this.permission = permission;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission(permission)) { sender.sendMessage(plugin.tc("&cYou do not have permission to run this command!")); return true; }
		if(args.length != 0) { sender.sendMessage(plugin.tc("&cProper usage is /staff")); return true; }
		Map <Player, ItemStack[][]> staffInv = plugin.staffInv;
		if(staffInv.get(plugin.getPlayer(sender)) == null) {
			plugin.addTele(plugin.getPlayer(sender), plugin.getPlayer(sender).getLocation());
			plugin.saveInv(plugin.getPlayer(sender));
			plugin.getPlayer(sender).getInventory().clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.hidePlayer(plugin.getPlayer(sender));
				if(plugin.inStaff.containsKey(player)) {
					player.showPlayer(plugin.getPlayer(sender));
					plugin.getPlayer(sender).showPlayer(player);
				}
			}
			plugin.getPlayer(sender).setAllowFlight(true);
			plugin.getPlayer(sender).setFlying(true);
			sender.sendMessage(plugin.tc("&6You have entered staff mode. You can now fly and are vanished."));
			plugin.inStaff.put(plugin.getPlayer(sender), true);
			return true;
		} else {
			plugin.restoreInv(plugin.getPlayer(sender));
			if(plugin.telePos.containsKey(plugin.getPlayer(sender))) { plugin.returnPlayer(plugin.getPlayer(sender)); }
			if(plugin.getPlayer(sender).getGameMode() != GameMode.CREATIVE) {
				plugin.getPlayer(sender).setAllowFlight(false);
				plugin.getPlayer(sender).setFlying(false);
			}
			plugin.inStaff.remove(plugin.getPlayer(sender));
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.showPlayer(plugin.getPlayer(sender));
			}
			sender.sendMessage(plugin.tc("&6You have left staff mode. You are no longer flying or vanished."));
			return true;
		}
	}
	
}
