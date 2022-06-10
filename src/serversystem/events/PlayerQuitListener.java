package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import serversystem.commands.VanishCommand;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.WorldGroup;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event) {
		ChatUtil.sendPlayerQuitMessage(event);
		if (VanishCommand.isVanished(event.getPlayer())) VanishCommand.toggleVanish(event.getPlayer(), false);
		if (event.getPlayer().getFlySpeed() > 0.2) event.getPlayer().setFlySpeed((float) 0.1);
		WorldGroup.getWorldGroup(event.getPlayer()).quit(event.getPlayer());
		PermissionUtil.resetPlayerPermissions(event.getPlayer());
	}

}
