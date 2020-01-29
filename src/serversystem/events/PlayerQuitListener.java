package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import serversystem.main.Config;
import serversystem.utilities.PlayerVanish;
import serversystem.utilities.WorldGroupHandler;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent event) {
		if(!Config.isLeaveMessageActiv()) {
			event.setQuitMessage("");
		}
		if(PlayerVanish.isPlayerVanished(event.getPlayer())) {
			PlayerVanish.vanishPlayer(event.getPlayer());
		}
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerLeave(event.getPlayer());
	}

}
