package serversystem.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import serversystem.config.Config;
import serversystem.config.SaveConfig;
import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.handler.PlayerVanish;
import serversystem.handler.WorldGroupHandler;

public class PlayerTeleportListener implements Listener {
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		Player player = event.getPlayer();
		World world = event.getTo().getWorld();
		if(event.getPlayer().getWorld() != event.getTo().getWorld()) {
			if(Config.hasWorldPermission(event.getTo().getWorld().getName())) {
				if(!event.getPlayer().hasPermission(Config.getWorldPermission(event.getTo().getWorld().getName()))) {
					ChatHandler.sendServerErrorMessage(event.getPlayer(), ErrorMessage.NOPERMISSION);
					event.setCancelled(true);
				}
			}
			boolean vanished = PlayerVanish.isPlayerVanished(event.getPlayer());
			if(!Config.hasWorldSpawn(event.getPlayer().getWorld().getName())) {
				SaveConfig.saveLocation(event.getPlayer());
			}
			if(Config.isWorldGroupSystemEnabled()) {
				WorldGroupHandler.getWorldGroup(player).onPlayerLeave(event.getPlayer());
				event.getPlayer().setGameMode(Config.getWorldGamemode(event.getTo().getWorld().getName()));
				WorldGroupHandler.getWorldGroup(world).onPlayerJoin(event.getPlayer());
			} else {
				event.getPlayer().setGameMode(Config.getWorldGamemode(world.getName()));
			}
			if(vanished) {
				PlayerVanish.vanishPlayer(event.getPlayer());
			}
		}
	}

}
