package com.mariouris.zimbie;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.BanEntry;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.mariouris.zimbie.commands.BringCommand;
import com.mariouris.zimbie.commands.ReturnCommand;
import com.mariouris.zimbie.commands.StaffCommand;
import com.mariouris.zimbie.commands.TeleportCommand;

/**
 * Hello world!
 *
 */
@SuppressWarnings("unused")
public class Main extends JavaPlugin {
	public Map<Player, Location> telePos = new HashMap<Player, Location>();
	public Map<Player, Boolean> inStaff = new HashMap<Player, Boolean>();
	public Map <Player, ItemStack[][]> staffInv = new HashMap <Player, ItemStack[][]>();
	public Logger logger = getLogger();
	public Map<Player, String> banReason = new HashMap<Player, String>();
	public Map<Player, Date> banDate = new HashMap<Player, Date>();
	
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new ZimbieListener(this), this);
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		
		getCommand("staff").setExecutor(new StaffCommand(this, "zimbie.staff"));
		getCommand("bring").setExecutor(new BringCommand(this, "zimbie.bring"));
		getCommand("return").setExecutor(new ReturnCommand(this, "zimbie.return"));
		getCommand("tp").setExecutor(new TeleportCommand(this, "zimbie.tp"));
	}
	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.isFlying() && player.getGameMode() != GameMode.CREATIVE) { player.setFlying(false); player.setAllowFlight(false); }
			if(inStaff.containsKey(player)) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.showPlayer(this, player);
				}
				restoreInv(player);
				if(player.getGameMode() != GameMode.CREATIVE) {
					player.setAllowFlight(false);
				}
			}
			if(telePos.containsKey(player)) {
				returnPlayer(player);
			}
		}
		for(Player player : this.banReason.keySet()) {
			Bukkit.getBanList(Type.NAME).addBan(player.getName(), this.tc(this.banReason.get(player)), this.banDate.get(player), "");
		}
	}
	
	public Player getPlayer(CommandSender sender) {
		return Bukkit.getPlayer(sender.getName());
	}
	public String tc(String m) {
		return ChatColor.translateAlternateColorCodes('&', m);
	}
	public void addTele(Player player, Location location) {
		telePos.put(player, location);
	}
	public void removeTele(Player player) {
		telePos.remove(player);
	}
	public void saveInv(Player p) {
		ItemStack[][] store = new ItemStack[2][1];
		store[0] = p.getInventory().getContents();
		store[1] = p.getInventory().getArmorContents();
		this.staffInv.put(p, store);
		p.getInventory().clear();
	}
	public void restoreInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setContents(this.staffInv.get(p)[0]);
		p.getInventory().setArmorContents(this.staffInv.get(p)[1]);
		this.staffInv.remove(p);
		p.updateInventory();
	}
	public void returnPlayer(Player p) {
		if(!this.telePos.containsKey(p)) return;
		Location location = this.telePos.get(p);
		p.teleport(location);
		this.removeTele(p);
	}
}
