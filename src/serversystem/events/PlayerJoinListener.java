package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import serversystem.config.Config;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.WorldGroup;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event) {
		ChatUtil.sendPlayerJoinMessage(event);
		Config.addPlayer(event.getPlayer());
		PermissionUtil.loadPlayerPermissions(event.getPlayer());
		event.getPlayer().setGameMode(Config.getWorldGamemode(event.getPlayer().getWorld().getName()));
		WorldGroup.getWorldGroup(event.getPlayer()).onPlayerJoin(event.getPlayer());
		if (Config.lobbyExists() && Config.getLobbyWorld() != null) {
			event.getPlayer().teleport(Config.getLobbyWorld().getSpawnLocation());
		}
		if (Config.getTitle() != null && Config.getSubtitle() != null) {
			ChatUtil.sendTitle(event.getPlayer(), ChatUtil.parseColor(Config.getTitleColor()) + Config.getTitle(), ChatUtil.parseColor(Config.getSubtitleColor()) + Config.getSubtitle());
		}
		event.getPlayer().setPlayerListHeader(ChatUtil.parseColor(Config.getTablistTitleColor()) + Config.getTablistTitle());
		event.getPlayer().setPlayerListFooter(ChatUtil.parseColor(Config.getTablistSubtitleColor()) + Config.getTablistSubtitle());
	}
}
