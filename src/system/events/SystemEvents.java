package system.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import system.main.Config;

public class SystemEvents implements Listener{

	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		if(!Config.playerExists(event.getPlayer().getName())) {
			Config.registerPlayer(event.getPlayer().getName());
		}
		Config.setPlayerOnline(event.getPlayer().getName(), true);
		if(!Config.worldExists(event.getPlayer().getWorld().getName())) {
			Config.registerWorld(event.getPlayer().getWorld().getName());
		}
		event.setJoinMessage("");
	}
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent event) {
		Config.setPlayerOnline(event.getPlayer().getName(), false);
		event.setQuitMessage("");
	}
	
}
