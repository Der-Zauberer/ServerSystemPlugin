package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import serversystem.utilities.PlayerInventory;

public class MenuHandler implements Listener {
	
	private static ArrayList<PlayerInventory> inventorymenus = new ArrayList<>();
	
	public static void addInventory(PlayerInventory inventory) {
		inventorymenus.add(inventory);
	}
	
	public static void removeInventory(PlayerInventory inventory) {
		inventorymenus.remove(inventory);
	}
	
	@EventHandler
	public void onInventoryClicked(InventoryClickEvent event) {
		if(event.getCurrentItem() != null) {
			for(PlayerInventory playerinventory : inventorymenus) {
				if(playerinventory.getInventory() == event.getClickedInventory()) {
					playerinventory.onItemClicked(event.getClickedInventory(), event.getCurrentItem(), (Player) event.getWhoClicked(), event.getSlot());
					event.setCancelled(true);
				}
			}
		}
	}

}
