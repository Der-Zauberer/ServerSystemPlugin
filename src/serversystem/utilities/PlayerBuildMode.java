package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class PlayerBuildMode {
	
	private static ArrayList<Player> buildPlayers = new ArrayList<>();
	
	public static void buildmodePlayer(Player player) {
		buildmodePlayer(player, player);
	}
	
	public static void buildmodePlayer(Player player, Player sender) {
		if(buildPlayers.contains(player)) {
			buildPlayers.remove(player);
			ChatMessage.sendServerMessage(sender, "You can no longer build!");
			if(player != sender) {
				ChatMessage.sendServerMessage(sender, player.getName() + " can no longer build!");
			}
		} else {
			buildPlayers.add(player);
			ChatMessage.sendServerMessage(sender, "You can build now!");
			if(player != sender) {
				ChatMessage.sendServerMessage(sender, player.getName() + " can build now!");
			}
		}
	}
	
	public static boolean isPlayerBuildmode(Player player) {
		if(buildPlayers.contains(player)) {
			return true;
		}
		return false;
	}

}
