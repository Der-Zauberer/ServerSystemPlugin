package serversystem.actions;

import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemInteractAction {

	public abstract void onAction(PlayerInteractEvent event);
	
}
