package serversystem.actions;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryClickAction {
	
	public abstract void onAction(InventoryClickEvent event);

}
