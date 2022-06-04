package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.PermissionHandler;
import serversystem.handler.WorldGroupHandler;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public static void onPlayerJoin(PlayerJoinEvent event) {
		ChatHandler.sendPlayerJoinMessage(event);
		Config.addPlayer(event.getPlayer());
		PermissionHandler.loadPlayerPermissions(event.getPlayer());
		event.getPlayer().setGameMode(Config.getWorldGamemode(event.getPlayer().getWorld().getName()));
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerJoin(event.getPlayer());
		if (Config.lobbyExists() && Config.getLobbyWorld() != null) {
			event.getPlayer().teleport(Config.getLobbyWorld().getSpawnLocation());
		}
		if (Config.getTitle() != null && Config.getSubtitle() != null) {
			ChatHandler.sendTitle(event.getPlayer(), ChatHandler.parseColor(Config.getTitleColor()) + Config.getTitle(), ChatHandler.parseColor(Config.getSubtitleColor()) + Config.getSubtitle());
		}
		event.getPlayer().setPlayerListHeader(ChatHandler.parseColor(Config.getTablistTitleColor()) + Config.getTablistTitle());
		event.getPlayer().setPlayerListFooter(ChatHandler.parseColor(Config.getTablistSubtitleColor()) + Config.getTablistSubtitle());
	}
}
