package system.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import system.menus.AdminMenu;

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
			event.getWhoClicked().setGameMode(GameMode.SURVIVAL);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Gamemode Creative")) {
			event.getWhoClicked().setGameMode(GameMode.CREATIVE);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Gamemode Adventure")) {
			event.getWhoClicked().setGameMode(GameMode.ADVENTURE);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Gamemode Spectator")) {
			event.getWhoClicked().setGameMode(GameMode.SPECTATOR);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Morning")) {
			event.getWhoClicked().getWorld().setTime(0);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Day")) {
			event.getWhoClicked().getWorld().setTime(6000);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Night")) {
			event.getWhoClicked().getWorld().setTime(13000);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Midnight")) {
			event.getWhoClicked().getWorld().setTime(18000);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Speed")) {
			event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 2), true);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Jump Boost")) {
			event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3600, 2), true);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Invisibilitiy")) {
			event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 2), true);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Effect Clear")) {
			for (PotionEffect effect : event.getWhoClicked().getActivePotionEffects())
		        event.getWhoClicked().removePotionEffect(effect.getType());
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Weather Clear")) {
			event.getWhoClicked().getWorld().setStorm(false);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Weather Rain")) {
			event.getWhoClicked().getWorld().setStorm(true);
			event.getWhoClicked().getWorld().setThundering(false);
			event.setCancelled(true);
			return;
		}
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Weather Thunderstorm")) {
			event.getWhoClicked().getWorld().setStorm(true);
			event.getWhoClicked().getWorld().setThundering(true);
			event.setCancelled(true);
			return;
		}
		event.setCancelled(true);
	}

}
