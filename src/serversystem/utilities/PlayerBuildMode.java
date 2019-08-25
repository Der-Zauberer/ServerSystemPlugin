package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.entity.Player;

public class PlayerBuildMode {
	
	private static ArrayList<String> buildPlayers = new ArrayList<>();
	
	public static void buildmodePlayer(Player player) {
		buildmodePlayer(player, player);
	}
	
	public static void buildmodePlayer(Player player, Player sender) {
		if(player == sender) {
			if(buildPlayers.contains(player.getUniqueId().toString())) {
				buildPlayers.remove(player.getUniqueId().toString());
			} else {
				buildPlayers.add(player.getUniqueId().toString());
			}
		} else {
			if(buildPlayers.contains(player.getUniqueId().toString())) {
				buildPlayers.remove(player.getUniqueId().toString());
			} else {
				buildPlayers.add(player.getUniqueId().toString());
			}
		}
	}
	
	public static boolean isPlayerBuildmode(Player player) {
		if(buildPlayers.contains(player.getUniqueId().toString())) {
			return true;
		}
		return false;
	}

}
