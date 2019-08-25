package serversystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerMessage {
	
	private static ChatColor messagecolor = ChatColor.YELLOW;
	private static ChatColor errorcolor = ChatColor.RED;
	private static String servername = ChatColor.YELLOW + "[Server]";
	
	public static void sendMessage(Player player, String message) {
		player.sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		((Player) sender).sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendErrorMessage(Player player, String message) {
		player.sendMessage(servername + errorcolor + " " + message);
	}
	
	public static void sendErrorMessage(CommandSender sender, String message) {
		((Player) sender).sendMessage(servername + errorcolor + " " + message);
	}
	
	public static void sendBroadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(servername + messagecolor + " " + message);
		}
	}

}
