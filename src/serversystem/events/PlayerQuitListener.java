package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.PlayerVanish;
import serversystem.handler.WorldGroupHandler;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		if(Config.isLeaveMessageActiv()) {
			event.setQuitMessage(ChatHandler.getPlayerQuitMessage(event));
		} else {
			event.setQuitMessage("");
		}
		if(PlayerVanish.isPlayerVanished(event.getPlayer())) {
			PlayerVanish.vanishPlayer(event.getPlayer());
		}
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerLeave(event.getPlayer());
	}

}
