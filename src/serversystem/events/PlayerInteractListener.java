package serversystem.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import serversystem.menus.AdminMenu;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(event.getItem().getType() == Material.NETHER_STAR && event.getPlayer().hasPermission("serversystem.tools.adminstar")) {
				new AdminMenu(event.getPlayer()).open();
			}
		}
	}

}
