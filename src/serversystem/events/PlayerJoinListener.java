package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import net.minecraft.server.v1_14_R1.PacketPlayOutTitle.EnumTitleAction;
import serversystem.config.Config;
import serversystem.handler.PlayerPacketHandler;
import serversystem.handler.PlayerPermission;
import serversystem.handler.WorldGroupHandler;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent event) {
		if(!Config.isJoinMessageActiv()) {
			event.setJoinMessage("");
		}
		Config.addPlayer(event.getPlayer());
		PlayerPermission.removeConfigDisablePermissions(event.getPlayer());
		PlayerPermission.addConfigPermissions(event.getPlayer());
		PlayerPermission.reloadPlayerPermissions(event.getPlayer());
		if(Config.lobbyExists() && Config.getLobbyWorld() != null) {
			event.getPlayer().teleport(Config.getLobbyWorld().getSpawnLocation());
		}
		WorldGroupHandler.getWorldGroup(event.getPlayer()).onPlayerJoin(event.getPlayer());
		event.getPlayer().setGameMode(Config.getWorldGamemode(event.getPlayer().getWorld().getName()));
		if(Config.getTitle() != null) {PlayerPacketHandler.sendTitle(event.getPlayer(), EnumTitleAction.TITLE, Config.getTitle(), Config.getTitleColor(), 100);}
		if(Config.getSubtitle() != null) {PlayerPacketHandler.sendTitle(event.getPlayer(), EnumTitleAction.SUBTITLE, Config.getSubtitle(), Config.getSubtitleColor(), 100);}
	}

}
