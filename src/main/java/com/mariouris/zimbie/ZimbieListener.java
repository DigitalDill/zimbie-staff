package com.mariouris.zimbie;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

@SuppressWarnings("unused")
public class ZimbieListener implements Listener {
	private final Main plugin;
	
	public ZimbieListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockBread(BlockBreakEvent e) {
		if(plugin.inStaff.containsKey(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(plugin.tc("&cYou cannot break blocks in staff mode!"));
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(plugin.inStaff.containsKey(e.getPlayer())) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(plugin.tc("&cYou cannot place blocks in staff mode!"));
		}
	}
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(plugin.inStaff.containsKey(Bukkit.getPlayer(e.getEntity().getName()))) {
			Bukkit.getPlayer(e.getEntity().getName()).setHealth(20);
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if(plugin.inStaff.containsKey(Bukkit.getPlayer(e.getEntity().getName()))) {
			e.setFoodLevel(20);
		}
	}
	@EventHandler
	public void onEntityDEntity(EntityDamageByEntityEvent e) {
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(plugin.inStaff.containsKey(p) && e.getDamager() == p) {
				e.setCancelled(true);
				if(e.getEntity().getType() == EntityType.PLAYER) {
					Bukkit.getPlayer(e.getEntity().getName()).playEffect(Bukkit.getPlayer(e.getEntity().getName()).getLocation(), Effect.ANVIL_LAND, null);
					Bukkit.getPlayer(e.getEntity().getName()).sendMessage(plugin.tc("&c" + p.getName() + " just attacked you while they were in staff mode! Report this to higher-ups if you think this was on purpose."));
				}
			}
		}
	}
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		if(plugin.getServer().getBanList(Type.NAME).isBanned(e.getPlayer().getName())) {
			e.setResult(Result.KICK_BANNED);
			e.setKickMessage(plugin.tc(plugin.getServer().getBanList(Type.NAME).getBanEntry(e.getPlayer().getName()).getReason()));
			
		}
	}
	
}
