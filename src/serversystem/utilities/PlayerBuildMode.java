package serversystem.utilities;

import java.util.ArrayList;

import org.bukkit.ChatColor;
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
				player.sendMessage(ChatColor.YELLOW + "[Server] You can no longer build!");
			} else {
				buildPlayers.add(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "[Server] You can build now!");
			}
		} else {
			if(buildPlayers.contains(player.getUniqueId().toString())) {
				buildPlayers.remove(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "[Server] You can no longer build!");
				sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " can no longer build!");
			} else {
				buildPlayers.add(player.getUniqueId().toString());
				player.sendMessage(ChatColor.YELLOW + "[Server] You can build now!");
				sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " can build now!");
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
