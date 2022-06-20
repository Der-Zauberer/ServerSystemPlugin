package serversystem.actions;

import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemInteractAction {
	
	void onAction(PlayerInteractEvent event);
	
}
