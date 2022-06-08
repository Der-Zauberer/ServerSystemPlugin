package serversystem.events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.WorldGroup;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public static void onPlayerDeath(PlayerDeathEvent event) {
		final World world = event.getEntity().getWorld();
		if (Config.getWorldOption(world.getName(), WorldOption.DEATHMESSAGE)) {
			String message = event.getDeathMessage();
			if (Config.getPlayerGroup(event.getEntity()) != null && Config.getGroupPrefix(Config.getPlayerGroup(event.getEntity())) != null && message.startsWith(Config.getGroupPrefix(Config.getPlayerGroup(event.getEntity())))) {
				message = message.substring(Config.getGroupPrefix(Config.getPlayerGroup(event.getEntity())).length());
			}
			if (WorldGroup.isEnabled()) {
				ChatUtil.sendServerWorldGroupMessage(WorldGroup.getWorldGroup(event.getEntity()), message);
			} else {
				ChatUtil.sendServerBroadcastMessage(message);
			}
			
		}
		WorldGroup.getPlayerDeaths().put(event.getEntity(), world);
		event.setDeathMessage("");
	}
	
}
