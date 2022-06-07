package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import serversystem.utilities.WorldGroup;

public class PlayerRespawnListener implements Listener {

	@EventHandler
	public static void onPlayerRespawn(PlayerRespawnEvent event) {
		if (WorldGroup.isEnabled()) {
			event.setRespawnLocation(WorldGroup.getPlayerDeaths().get(event.getPlayer()).getSpawnLocation());
			WorldGroup.getPlayerDeaths().remove(event.getPlayer());
		}
	}
	
}
