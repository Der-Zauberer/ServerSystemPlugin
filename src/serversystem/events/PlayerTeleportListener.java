package serversystem.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import serversystem.commands.BuildCommand;
import serversystem.commands.VanishCommand;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
import serversystem.config.Config.WorldOption;
import serversystem.config.SaveConfig;
import serversystem.utilities.WorldGroup;

public class PlayerTeleportListener implements Listener {
	
	@EventHandler
	public static void onPlayerTeleport(PlayerTeleportEvent event) {
		final Player player = event.getPlayer();
		final World world = event.getTo().getWorld();
		if (player.getWorld() == world) return;
		boolean vanished = VanishCommand.isVanished(player);
		if ((event.getCause() == TeleportCause.NETHER_PORTAL || event.getCause() == TeleportCause.END_PORTAL) && !Config.getConfigOption(ConfigOption.ENABLE_PORTALS)) {
			event.setCancelled(true);
			return;
		} else {
			if(!Config.getWorldOption(world, WorldOption.WORLD_SPAWN)) SaveConfig.saveLocation(event.getPlayer());
		}
		if (BuildCommand.isInBuildmode(player)) BuildCommand.toggleBuildMode(player);
		if (vanished) VanishCommand.toggleVanish(player, false);
		if (WorldGroup.isEnabled()) {
			WorldGroup.getWorldGroup(player).quit(event.getPlayer());
			event.getPlayer().setGameMode(Config.getWorldGamemode(event.getTo().getWorld()));
			if(player.getAllowFlight()) player.setFlying(SaveConfig.loadFlying(player, world));
			WorldGroup.getWorldGroup(world).join(player);
		} else {
			event.getPlayer().setGameMode(Config.getWorldGamemode(world));
			if(player.getAllowFlight()) player.setFlying(SaveConfig.loadFlying(player, world));
		}
		if (vanished) VanishCommand.toggleVanish(player, false);
		if (player.getFlySpeed() > 0.2) player.setFlySpeed((float) 0.1);
	}

}
