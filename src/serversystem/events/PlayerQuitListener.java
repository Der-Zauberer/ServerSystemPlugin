package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.PermissionHandler;
import serversystem.handler.PlayerVanishHandler;
import serversystem.handler.WorldGroupHandler;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if(Config.isLeaveMessageActiv()) {
			event.setQuitMessage(ChatHandler.getPlayerQuitMessage(event));
		} else {
			event.setQuitMessage("");
		}
		if(PlayerVanishHandler.isPlayerVanished(event.getPlayer())) {
			PlayerVanishHandler.vanishPlayer(event.getPlayer());
		}
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerLeave(event.getPlayer());
		PermissionHandler.resetPlayerPermissions(event.getPlayer());
	}

}
