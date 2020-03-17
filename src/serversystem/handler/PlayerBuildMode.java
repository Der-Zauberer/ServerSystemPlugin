package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class PlayerBuildMode {
	
	private static ArrayList<Player> buildplayers = new ArrayList<>();
	
	public static void buildmodePlayer(Player player) {
		buildmodePlayer(player, player);
	}
	
	public static void buildmodePlayer(Player player, Player sender) {
		if(buildplayers.contains(player)) {
			buildplayers.remove(player);
			ChatMessage.sendServerMessage(sender, "You can no longer build!");
			if(player != sender) {
				ChatMessage.sendServerMessage(sender, player.getName() + " can no longer build!");
			}
		} else {
			buildplayers.add(player);
			ChatMessage.sendServerMessage(sender, "You can build now!");
			if(player != sender) {
				ChatMessage.sendServerMessage(sender, player.getName() + " can build now!");
			}
		}
	}
	
	public static boolean isPlayerInBuildmode(Player player) {
		if(buildplayers.contains(player)) {
			return true;
		}
		return false;
	}

}
