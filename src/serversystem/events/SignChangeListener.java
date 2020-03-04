package serversystem.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import serversystem.handler.SignHandler;

public class SignChangeListener implements Listener {
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if(event.getLine(0) != null && event.getLine(3) != null) {
			if(event.getLine(1).contains("[") || event.getLine(1).contains("]")) {	
				if(event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
					String label = event.getLine(1).substring(1, event.getLine(1).length() -1);
					if(SignHandler.placeServerSign(event.getPlayer(), label, event.getLine(2))) {
						event.setLine(2, "§2" + event.getLine(2));
					} else {
						event.setLine(2, "§4" + event.getLine(2));
					}
				} else if(!event.getPlayer().hasPermission("serversystem.tools.signeddit")){
					event.setLine(1, "§4Permissions");
					event.setLine(2, "§4required!");
				}
				
			}	
		}
	}

}
