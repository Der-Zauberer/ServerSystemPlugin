package serversystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import serversystem.config.Config;

public class EntityDamageListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			if(!Config.hasWorldDamage(event.getEntity().getWorld().getName())) {
				if (event.getCause() == DamageCause.VOID) {
					event.getEntity().teleport(event.getEntity().getWorld().getSpawnLocation());
				}
				event.setCancelled(true);
			}
		}
	}

}
