package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import serversystem.handler.ServerSignHandler;

public class SignChangeListener implements Listener {
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if(event.getLine(0) != null && event.getLine(3) != null) {
			if(event.getLine(1).contains("[") || event.getLine(1).contains("]")) {	
				if(event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
					String label = event.getLine(1).substring(1, event.getLine(1).length() -1);
					if(ServerSignHandler.placeServerSign(event.getPlayer(), label, event.getLine(2))) {
						event.setLine(2, "§2" + event.getLine(2));
					} else {
						event.setLine(2, "§4" + event.getLine(2));
					}
				} else if(!event.getPlayer().hasPermission("serversystem.tools.signeddit")){
					event.setLine(1, "§4Permissions");
					event.setLine(2, "§4required!");
				}
				
			}	
			
//			if(event.getLine(1).equals("[World]") && event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
//				String labelbody = event.getLine(2);
//				event.setLine(2, "§2" + labelbody);
//				if(Bukkit.getWorld(labelbody) == null) {
//					event.setLine(2, "§4" + labelbody);
//				}
//			} else if (event.getLine(1).equals("[Command]") && event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
//				String labelbody = event.getLine(2);
//				event.setLine(2, "§2" + labelbody);
//				if(Bukkit.getServer().getPluginCommand(labelbody) != null) {
//					event.setLine(2, "§4" + labelbody);
//				}
//			} else if(!event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
//				event.setLine(1, "§4Permissions");
//				event.setLine(2, "§4required!");
//			}
		}
	}

}
