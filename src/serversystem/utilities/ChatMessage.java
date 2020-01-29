package serversystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import serversystem.handler.WorldGroupHandler;

public class ChatMessage {
	
	private static ChatColor messagecolor = ChatColor.YELLOW;
	private static ChatColor errorcolor = ChatColor.RED;
	private static String servername = ChatColor.YELLOW + "[Server]";
	
	public static void sendServerMessage(Player player, String message) {
		player.sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendServerMessage(CommandSender sender, String message) {
		((Player) sender).sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendServerErrorMessage(Player player, String message) {
		player.sendMessage(servername + errorcolor + " " + message);
	}
	
	public static void sendServerErrorMessage(CommandSender sender, String message) {
		((Player) sender).sendMessage(servername + errorcolor + " " + message);
	}
	
	public static void sendServerBroadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(servername + messagecolor + " " + message);
		}
		Bukkit.getConsoleSender().sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendServerWorldGroupMessage(WorldGroup worldgroup, String message) {
		for(Player player : worldgroup.getPlayers()) {
			player.sendMessage(servername + messagecolor + " " + message);
		}
		Bukkit.getConsoleSender().sendMessage("[" + worldgroup.getName() + "] " + servername + messagecolor + " " + message);
	}
	
	public static void sendPlayerDeathMessage(Player player) {
		for(Player players : WorldGroupHandler.getWorldGroup(player).getPlayers()) {
			players.sendMessage(player.getName() + " died!");
		}
		Bukkit.getConsoleSender().sendMessage("[" + WorldGroupHandler.getWorldGroup(player).getName() + "] " + player.getName() + " died!");
	}
	
	public static void sendPlayerChatMessage(Player player, String message) {
		for (Player players : WorldGroupHandler.getWorldGroup(player).getPlayers()) {
			players.sendMessage(PlayerTeam.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
		}
		Bukkit.getConsoleSender().sendMessage("[" + WorldGroupHandler.getWorldGroup(player).getName() + "] " + PlayerTeam.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
	}
	
	public static void sendPlayerPrivateMessage(Player sender, Player receiver, String message) {		
		sender.sendMessage(PlayerTeam.getPlayerNameColor(sender) + sender.getName() + ChatColor.WHITE + " -> " + PlayerTeam.getPlayerNameColor(receiver) + "Mir" + ChatColor.WHITE + " :" + ChatColor.GRAY + message);
		receiver.sendMessage(PlayerTeam.getPlayerNameColor(sender) + sender.getPlayer().getName() + ChatColor.WHITE + " -> " + PlayerTeam.getPlayerNameColor(receiver) + "Mir" + ChatColor.WHITE + " :" + ChatColor.GRAY + message);
		Bukkit.getConsoleSender().sendMessage("[Private] " + PlayerTeam.getPlayerNameColor(sender) + sender.getPlayer().getName() + ChatColor.WHITE + " -> " + PlayerTeam.getPlayerNameColor(receiver) + receiver.getName() + ChatColor.WHITE + " :" + ChatColor.GRAY + message);
	}
	
	public static void sendPlayerTeamMessage(Player player, String message) {
		for (String players : player.getScoreboard().getEntryTeam(player.getName()).getEntries()) {
			if(Bukkit.getPlayer(players) != null) {
				Bukkit.getPlayer(players).sendMessage(PlayerTeam.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
			}
		}
		Bukkit.getConsoleSender().sendMessage("[" + WorldGroupHandler.getWorldGroup(player).getName() + "] [" + player.getScoreboard().getEntryTeam(player.getName()).getName() + "] " + PlayerTeam.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
	}


}
