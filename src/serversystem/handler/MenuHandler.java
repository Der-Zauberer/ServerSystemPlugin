package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import serversystem.utilities.PlayerInventory;

public class MenuHandler {
	
	private static ArrayList<PlayerInventory> inventorymenus = new ArrayList<>();
	
	public static void addInventory(PlayerInventory inventory) {
		inventorymenus.add(inventory);
	}
	
	public static void removeInventory(PlayerInventory inventory) {
		inventorymenus.remove(inventory);
	}
	
	public static void executeClicked(InventoryClickEvent event, Inventory inventory, ItemStack item, Player player) {
		for(PlayerInventory playerinventory : inventorymenus) {
			if(playerinventory.getInventory() == inventory) {
				playerinventory.onItemClicked(item, player);
				event.setCancelled(true);
			}
		}
	}

}
