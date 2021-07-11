package serversystem.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.WorldGroupHandler;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if(event.getEntity() instanceof Player && WorldGroupHandler.isEnabled()) {
			World world = event.getEntity().getWorld();
			if(Config.hasWorldDeathMessage(world.getName())) {
				ChatHandler.sendServerWorldGroupMessage(WorldGroupHandler.getWorldGroup(event.getEntity()), event.getDeathMessage());
			}
			WorldGroupHandler.playerdeaths.put(event.getEntity(), world);
		}
		event.setDeathMessage("");
	}
	
}
