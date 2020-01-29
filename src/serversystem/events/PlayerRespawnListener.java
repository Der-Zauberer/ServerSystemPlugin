package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import serversystem.utilities.WorldGroupHandler;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(!WorldGroupHandler.getWorldGroup(event.getPlayer()).isServerGame()) {
			event.setRespawnLocation(WorldGroupHandler.getWorldGroup(event.getPlayer()).getMainWorld().getSpawnLocation());
		}
	}
	
}
