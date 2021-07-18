package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import serversystem.handler.WorldGroupHandler;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		if(WorldGroupHandler.isEnabled()) {
			event.setRespawnLocation(WorldGroupHandler.playerdeaths.get(event.getPlayer()).getSpawnLocation());
			WorldGroupHandler.playerdeaths.remove(event.getPlayer());
		}
	}
	
}
