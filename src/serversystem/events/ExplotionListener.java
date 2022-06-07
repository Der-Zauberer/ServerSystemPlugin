package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import serversystem.config.Config;
import serversystem.config.Config.WorldOption;

public class ExplotionListener implements Listener {
	
	@EventHandler
	public static void onEntityExplode(EntityExplodeEvent event) {
		if (!Config.getWorldOption(event.getEntity().getWorld().getName(), WorldOption.EXPLOSION)) event.blockList().clear();
	}

}
