package serversystem.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import serversystem.handler.ServerSignHandler;

public class PlayerInteractListener implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if(sign.getLine(0) != null && sign.getLine(3) != null) {
				String label = sign.getLine(1).substring(1, sign.getLine(1).length() -1);
				ServerSignHandler.executeServerSign(event.getPlayer(), sign, label, sign.getLine(2));
			}
		}
	}

}