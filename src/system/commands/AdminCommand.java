package system.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminCommand implements CommandExecutor, Listener{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Inventory inventory = Bukkit.createInventory(player, 36, "Admin");
			inventory.setItem(0, createItemStack(Material.IRON_SHOVEL, "Gamemode Survival"));
			inventory.setItem(9, createItemStack(Material.IRON_PICKAXE, "Gamemode Creative"));
			inventory.setItem(18, createItemStack(Material.IRON_SWORD, "Gamemode Adventure"));
			inventory.setItem(27, createItemStack(Material.IRON_HELMET, "Gamemode Spectator"));
			inventory.setItem(2, createItemStack(Material.CLOCK, "Time Morning"));
			inventory.setItem(11, createItemStack(Material.CLOCK, "Time Day"));
			inventory.setItem(20, createItemStack(Material.CLOCK, "Time Night"));
			inventory.setItem(29, createItemStack(Material.CLOCK, "Time Midnight"));
			inventory.setItem(4, createItemStack(PotionType.SPEED, "Effect Speed", Color.BLUE, PotionEffectType.SPEED));
			inventory.setItem(13, createItemStack(PotionType.JUMP, "Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP));
			inventory.setItem(22, createItemStack(PotionType.INVISIBILITY, "Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY));
			inventory.setItem(31, createItemStack(Material.GLASS_BOTTLE, "Effect Clear"));
			player.openInventory(inventory);
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
	}
	
	public ItemStack createItemStack(Material material, String name) {
		ItemStack itemstack = new ItemStack(material);
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(name);
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public ItemStack createItemStack(PotionType potiontype, String name, Color color, PotionEffectType effect) {
		ItemStack itemstack = new ItemStack(Material.POTION);
		PotionMeta potionmeta = (PotionMeta) itemstack.getItemMeta();
		potionmeta.setDisplayName(name);
		potionmeta.setColor(color);
		potionmeta.addCustomEffect(new PotionEffect(effect, 3600, 2), true);
		itemstack.setItemMeta(potionmeta);
		return itemstack;
	}

}
