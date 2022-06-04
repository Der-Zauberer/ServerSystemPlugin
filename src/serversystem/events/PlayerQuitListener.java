package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import serversystem.commands.VanishCommand;
import serversystem.handler.ChatHandler;
import serversystem.handler.PermissionHandler;
import serversystem.handler.WorldGroupHandler;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event) {
		ChatHandler.sendPlayerQuitMessage(event);
		if (VanishCommand.isVanished(event.getPlayer())) {
			VanishCommand.toggleVanish(event.getPlayer());
		}
		if (event.getPlayer().getFlySpeed() > 0.2) {
			event.getPlayer().setFlySpeed((float) 0.1);
		}
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerLeave(event.getPlayer());
		PermissionHandler.resetPlayerPermissions(event.getPlayer());
	}

}
