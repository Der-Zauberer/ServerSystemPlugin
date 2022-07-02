package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import serversystem.commands.VanishCommand;
import serversystem.config.SaveConfig;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.WorldGroup;

public class PlayerQuitListener implements Listener {
	
	@EventHandler
	public static void onPlayerQuit(PlayerQuitEvent event) {
		ChatUtil.sendQuitMessage(event);
		if (VanishCommand.isVanished(event.getPlayer())) VanishCommand.toggleVanish(event.getPlayer(), false);
		if (event.getPlayer().getFlySpeed() > 0.2) event.getPlayer().setFlySpeed((float) 0.1);
		SaveConfig.saveLocation(event.getPlayer());
		if (WorldGroup.isEnabled()) WorldGroup.getWorldGroup(event.getPlayer()).quit(event.getPlayer());
		PermissionUtil.resetPlayerPermissions(event.getPlayer());
	}

}
