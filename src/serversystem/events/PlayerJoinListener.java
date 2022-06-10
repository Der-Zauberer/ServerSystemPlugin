package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
import serversystem.config.Config.TitleTypeOption;
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
		WorldGroup.getWorldGroup(event.getPlayer()).onPlayerJoin(event.getPlayer());
		if (Config.getConfigOption(ConfigOption.LOBBY) && Config.getLobbyWorld() != null) {
			event.getPlayer().teleport(Config.getLobbyWorld().getSpawnLocation());
		}
		final String title = Config.getTitle(TitleTypeOption.TITLE);
		final String subtitle = Config.getTitle(TitleTypeOption.SUBTITLE);
		if (title != null && subtitle != null) ChatUtil.sendTitle(event.getPlayer(), title, subtitle);
		final String tablistTitle = Config.getTitle(TitleTypeOption.TABLIST_TITLE);
		final String tablistSubtitle = Config.getTitle(TitleTypeOption.TABLIST_SUBTITLE);
		if (tablistTitle != null) event.getPlayer().setPlayerListHeader(tablistTitle);
		if (tablistSubtitle != null) event.getPlayer().setPlayerListFooter(tablistSubtitle);
	}
}
