package serversystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import serversystem.handler.MenuHandler;

public class InventoryClickListener implements Listener {
	
	@EventHandler
	public void onInventoryClicked(InventoryClickEvent event) {
		if(event.getCurrentItem() != null) {
			MenuHandler.executeClicked(event, event.getInventory(), event.getCurrentItem(), (Player) event.getWhoClicked());
		}
		
	}

}
