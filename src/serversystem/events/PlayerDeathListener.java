package serversystem.events;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.ServerGroup;
import serversystem.utilities.WorldGroup;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public static void onPlayerDeath(PlayerDeathEvent event) {
		final World world = event.getEntity().getWorld();
		if (Config.getWorldOption(world, WorldOption.DEATH_MESSAGE)) {
			String message = event.getDeathMessage();
			final ServerGroup group = ServerGroup.getGroup(event.getEntity());
			if (group != null && !group.getPrefix().isEmpty()) message = message.substring(group.getPrefix().length() + 1);
			if (WorldGroup.isEnabled()) {
				ChatUtil.sendWorldGroupMessage(WorldGroup.getWorldGroup(event.getEntity()), message);
			} else {
				ChatUtil.sendBroadcastMessage(message);
			}
		}
		WorldGroup.getPlayerDeaths().put(event.getEntity(), world);
		event.setDeathMessage("");
	}
	
}
