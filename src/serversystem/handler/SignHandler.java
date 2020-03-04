package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import serversystem.utilities.ServerSign;

public class SignHandler {
	
	private static ArrayList<ServerSign> serversign = new ArrayList<>();
	
	public static void registerServerSign(ServerSign serversign) {
		SignHandler.serversign.add(serversign);
	}
	
	public static void executeServerSign(Player player, Sign sign, String label, String args) {
		for (ServerSign serversign : serversign) {
			if(serversign.getLabel().equalsIgnoreCase(label)) {
				serversign.onAction(player, sign, args);
			}
		}
	}
	
	public static boolean placeServerSign(Player player, String label, String args) {
		for (ServerSign serversign : serversign) {
			if(serversign.getLabel().equalsIgnoreCase(label)) {
				return serversign.onPlace(player, args);
			}
		}
		return false;
	}

}
