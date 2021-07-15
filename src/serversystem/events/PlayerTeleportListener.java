package serversystem.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import serversystem.config.Config;
import serversystem.config.SaveConfig;
import serversystem.handler.PlayerVanishHandler;
import serversystem.handler.WorldGroupHandler;

public class PlayerTeleportListener implements Listener {
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		World world = event.getTo().getWorld();
		if(event.getPlayer().getWorld() != event.getTo().getWorld()) {
			boolean vanished = PlayerVanishHandler.isPlayerVanished(event.getPlayer());
			if(!Config.hasWorldSpawn(event.getPlayer().getWorld().getName())) {
				SaveConfig.saveLocation(event.getPlayer());
			}
			if(Config.isWorldGroupSystemEnabled()) {
				WorldGroupHandler.getWorldGroup(player).onPlayerLeave(event.getPlayer());
				event.getPlayer().setGameMode(Config.getWorldGamemode(event.getTo().getWorld().getName()));
				if(player.getAllowFlight()) {
					player.setFlying(SaveConfig.loadFlying(player, world));
				}
				WorldGroupHandler.getWorldGroup(world).onPlayerJoin(event.getPlayer());
			} else {
				event.getPlayer().setGameMode(Config.getWorldGamemode(world.getName()));
				if(player.getAllowFlight()) {
					player.setFlying(SaveConfig.loadFlying(player, world));
				}
			}
			if(vanished) {
				PlayerVanishHandler.vanishPlayer(event.getPlayer());
			}
		}
	}

}
