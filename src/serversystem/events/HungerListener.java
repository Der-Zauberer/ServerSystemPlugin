package serversystem.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import serversystem.config.Config;
import serversystem.config.Config.WorldOption;

public class HungerListener implements Listener {
	
	@EventHandler
	public static void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player && !Config.getWorldOption(event.getEntity().getWorld(), WorldOption.HUNGER)) {
			event.setCancelled(true);
		}
	}

}
