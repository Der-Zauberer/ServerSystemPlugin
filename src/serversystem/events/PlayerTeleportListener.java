package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import serversystem.config.Config;
import serversystem.config.SaveConfig;
import serversystem.handler.PlayerVanish;
import serversystem.handler.WorldGroupHandler;

public class PlayerTeleportListener implements Listener {
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if(event.getPlayer().getWorld() != event.getTo().getWorld()) {
			boolean vanished = PlayerVanish.isPlayerVanished(event.getPlayer());
			if(!Config.hasWorldSpawn(event.getPlayer().getWorld().getName())) {
				SaveConfig.saveLocation(event.getPlayer());
			}
			if(Config.isWorldGroupSystemEnabled()) {
				WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerLeave(event.getPlayer());
				event.getPlayer().setGameMode(Config.getWorldGamemode(event.getTo().getWorld().getName()));
				WorldGroupHandler.getWorldGroup(event.getTo().getWorld()).onPlayerJoin(event.getPlayer());
			} else {
				event.getPlayer().setGameMode(Config.getWorldGamemode(event.getTo().getWorld().getName()));
			}
			if(vanished) {
				PlayerVanish.vanishPlayer(event.getPlayer());
			}
		}
	}

}
