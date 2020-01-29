package serversystem.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if(event.getLine(0) != null && event.getLine(3) != null) {
			if(event.getLine(1).equals("[World]") && event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
				String labelbody = event.getLine(2);
				event.setLine(2, "§2" + labelbody);
				if(Bukkit.getWorld(labelbody) == null) {
					event.setLine(2, "§4" + labelbody);
				}
			} else if (event.getLine(1).equals("[Command]") && event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
				String labelbody = event.getLine(2);
				event.setLine(2, "§2" + labelbody);
				if(Bukkit.getServer().getPluginCommand(labelbody) != null) {
					event.setLine(2, "§4" + labelbody);
				}
			} else if(!event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
				event.setLine(1, "§4Permissions");
				event.setLine(2, "§4required!");
			}
		}
	}

}
