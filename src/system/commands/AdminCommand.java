package system.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminCommand implements CommandExecutor, Listener{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("admin") && sender.hasPermission("system..admin")) {
			Inventory inventory = Bukkit.createInventory(null, 36, "Admin");
			inventory.setItem(0, createItemStack(Material.IRON_SHOVEL, "Survival"));
			inventory.setItem(9, createItemStack(Material.IRON_PICKAXE, "Creative"));
			inventory.setItem(18, createItemStack(Material.IRON_SWORD, "Adventure"));
			inventory.setItem(27, createItemStack(Material.IRON_HELMET, "Spectator"));
			inventory.setItem(2, createItemStack(Material.CLOCK, "Time Morning"));
			inventory.setItem(11, createItemStack(Material.CLOCK, "Time Day"));
			inventory.setItem(20, createItemStack(Material.CLOCK, "Time Night"));
			inventory.setItem(29, createItemStack(Material.CLOCK, "Time Midnight"));
			Player player = (Player) sender;
			player.openInventory(inventory);
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) {
			return;
		}
		if(event.getCurrentItem().getType() == Material.IRON_SHOVEL) {
			event.getWhoClicked().setGameMode(GameMode.SURVIVAL);
			event.setCancelled(true);
		}else if(event.getCurrentItem().getType() == Material.IRON_PICKAXE) {
			event.getWhoClicked().setGameMode(GameMode.CREATIVE);
			event.setCancelled(true);
		}else if(event.getCurrentItem().getType() == Material.IRON_SWORD) {
			event.getWhoClicked().setGameMode(GameMode.ADVENTURE);
			event.setCancelled(true);
		}else if(event.getCurrentItem().getType() == Material.IRON_HELMET) {
			event.getWhoClicked().setGameMode(GameMode.SPECTATOR);
			event.setCancelled(true);
		}else if(event.getCurrentItem().getType() == Material.CLOCK) {
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Morning")) {
				event.getWhoClicked().getWorld().setTime(0);
				event.setCancelled(true);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Day")) {
				event.getWhoClicked().getWorld().setTime(6000);
				event.setCancelled(true);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Night")) {
				event.getWhoClicked().getWorld().setTime(13000);
				event.setCancelled(true);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Time Midnight")) {
				event.getWhoClicked().getWorld().setTime(18000);
				event.setCancelled(true);
			}
		}
	}
	
	public ItemStack createItemStack(Material material, String name) {
		ItemStack itemstack = new ItemStack(material);
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(name);
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}

}
