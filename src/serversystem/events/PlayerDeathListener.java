package serversystem.events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import serversystem.config.Config;
import serversystem.handler.WorldGroupHandler;
import serversystem.utilities.ChatUtil;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public static void onPlayerDeath(PlayerDeathEvent event) {
		World world = event.getEntity().getWorld();
		if (Config.hasWorldDeathMessage(world.getName())) {
			String message = event.getDeathMessage();
			if (Config.getPlayerGroup(event.getEntity()) != null && Config.getGroupPrefix(Config.getPlayerGroup(event.getEntity())) != null && message.startsWith(Config.getGroupPrefix(Config.getPlayerGroup(event.getEntity())))) {
				message = message.substring(Config.getGroupPrefix(Config.getPlayerGroup(event.getEntity())).length());
			}
			if (WorldGroupHandler.isEnabled()) {
				ChatUtil.sendServerWorldGroupMessage(WorldGroupHandler.getWorldGroup(event.getEntity()), message);
			} else {
				ChatUtil.sendServerBroadcastMessage(message);
			}
			
		}
		WorldGroupHandler.playerdeaths.put(event.getEntity(), world);
		event.setDeathMessage("");
	}
	
}
