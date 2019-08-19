package serversystem.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import serversystem.menus.AdminMenu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminCommand implements CommandExecutor, Listener{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			new AdminMenu(player);
		}
		return true;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !event.getView().getTitle().equals("Admin")) {
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Gamemode Survival")) {
			event.setCancelled(true);
			event.setCancelled(true);
			event.getWhoClicked().setGameMode(GameMode.SURVIVAL);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Gamemode Creative")) {
			event.setCancelled(true);
			event.getWhoClicked().setGameMode(GameMode.CREATIVE);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Gamemode Adventure")) {
			event.setCancelled(true);
			event.getWhoClicked().setGameMode(GameMode.ADVENTURE);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Gamemode Spectator")) {
			event.setCancelled(true);
			event.getWhoClicked().setGameMode(GameMode.SPECTATOR);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Morning")) {
			event.setCancelled(true);
			event.getWhoClicked().getWorld().setTime(0);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Day")) {
			event.setCancelled(true);
			event.getWhoClicked().getWorld().setTime(6000);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Night")) {
			event.setCancelled(true);
			event.getWhoClicked().getWorld().setTime(13000);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Midnight")) {
			event.setCancelled(true);
			event.getWhoClicked().getWorld().setTime(18000);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Speed")) {
			event.setCancelled(true);
			event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 2), true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Jump Boost")) {
			event.setCancelled(true);
			event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3600, 2), true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Invisibilitiy")) {
			event.setCancelled(true);
			event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 2), true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Clear")) {
			event.setCancelled(true);
			for (PotionEffect effect : event.getWhoClicked().getActivePotionEffects())
		        event.getWhoClicked().removePotionEffect(effect.getType());
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Weather Clear")) {
			event.setCancelled(true);
			event.getWhoClicked().getWorld().setStorm(false);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Weather Rain")) {
			event.setCancelled(true);
			event.getWhoClicked().getWorld().setStorm(true);
			event.getWhoClicked().getWorld().setThundering(false);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Weather Thunderstorm")) {
			event.setCancelled(true);
			event.getWhoClicked().getWorld().setStorm(true);
			event.getWhoClicked().getWorld().setThundering(true);
			return;
		}
		event.setCancelled(true);
	}

}
