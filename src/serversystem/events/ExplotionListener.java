package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import serversystem.config.Config;

public class ExplotionListener implements Listener {
	
	@EventHandler
	public void onExplotion(EntityExplodeEvent event) {
		if(!Config.hasWorldExplosion(event.getEntity().getWorld().getName())) {
			event.blockList().clear();
		}
	}

}
