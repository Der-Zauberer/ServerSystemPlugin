package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.WorldGroup;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event) {
		ChatUtil.sendPlayerJoinMessage(event);
		Config.addPlayer(event.getPlayer());
		PermissionUtil.loadPlayerPermissions(event.getPlayer());
		event.getPlayer().setGameMode(Config.getWorldGamemode(event.getPlayer().getWorld()));
		if (WorldGroup.isEnabled()) WorldGroup.getWorldGroup(event.getPlayer().getWorld()).join(event.getPlayer());
		if (Config.getConfigOption(ConfigOption.LOBBY) && Config.getLobbyWorld() != null) {
			event.getPlayer().teleport(Config.getLobbyWorld().getSpawnLocation());
		}
		ChatUtil.sendServerTitle(event.getPlayer());
		ChatUtil.sendServerTablistTitle(event.getPlayer());
	}
}
