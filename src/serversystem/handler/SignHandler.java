package serversystem.handler;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import serversystem.utilities.ServerSign;

public class SignHandler implements Listener {
	
	private static ArrayList<ServerSign> serversign = new ArrayList<>();
	
	public static void registerServerSign(ServerSign serversign) {
		SignHandler.serversign.add(serversign);
	}
	
	public static void executeServerSign(Player player, Sign sign, String label, String args) {
		for (ServerSign serversign : serversign) {
			if (serversign.getLabel().equalsIgnoreCase(label)) {
				serversign.onAction(player, sign, args);
			}
		}
	}
	
	public static boolean placeServerSign(Player player, String label, String args) {
		for (ServerSign serversign : serversign) {
			if (serversign.getLabel().equalsIgnoreCase(label)) {
				return serversign.onPlace(player, args);
			}
		}
		return false;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign) event.getClickedBlock().getState();
			if (sign.getLine(0) != null && sign.getLine(3) != null) {
				String label = ChatColor.stripColor(sign.getLine(1)).substring(1, sign.getLine(1).length() -1);
				executeServerSign(event.getPlayer(), sign, label, ChatColor.stripColor(sign.getLine(2)));
			}
		}
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		if (event.getLine(0) != null && event.getLine(3) != null) {
			if (event.getLine(1).contains("[") || event.getLine(1).contains("]")) {	
				if (event.getPlayer().hasPermission("serversystem.tools.signeddit")) {
					String label = event.getLine(1).substring(1, event.getLine(1).length() -1);
					if (placeServerSign(event.getPlayer(), label, event.getLine(2))) {
						event.setLine(2, "�2" + event.getLine(2));
					} else {
						event.setLine(2, "�4" + event.getLine(2));
					}
				} else if(!event.getPlayer().hasPermission("serversystem.tools.signeddit")){
					event.setLine(1, "�4Permissions");
					event.setLine(2, "�4required!");
				}
			}	
		}
	}
	
}
