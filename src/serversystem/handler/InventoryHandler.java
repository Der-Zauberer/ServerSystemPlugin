package serversystem.handler;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import serversystem.utilities.PlayerInventory;
import serversystem.utilities.PlayerInventory.ItemOption;

public class InventoryHandler implements Listener {

	private static ArrayList<PlayerInventory> playerinventory = new ArrayList<>();

	public static void addInventory(PlayerInventory inventory) {
		playerinventory.add(inventory);
	}

	public static void removeInventory(PlayerInventory inventory) {
		playerinventory.remove(inventory);
	}

	@EventHandler
	public void onInventoryClicked(InventoryClickEvent event) {
		if (event.getCurrentItem() != null) {
			try {
				for (PlayerInventory inventory : playerinventory) {
					if (inventory.getInventory() == event.getClickedInventory() || (event.getWhoClicked().getOpenInventory().getTopInventory() == inventory.getInventory() && event.getClickedInventory() == event.getWhoClicked().getInventory() && inventory.getItemOption() == ItemOption.FIXED)) {
						if (inventory.getItemOption() == ItemOption.FIXED) {
							inventory.onItemClicked(event.getCurrentItem());
							event.setCancelled(true);
						} else if (inventory.getItemOption() == ItemOption.GETABLE) {
							event.getWhoClicked().getInventory().addItem(event.getCurrentItem());
							event.setCancelled(true);
						}
					}
				}
			} catch (ConcurrentModificationException exception) {}
		}
	}

}