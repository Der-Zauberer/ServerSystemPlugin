package serversystem.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.utilities.WorldGroup;

public class ChatHandler implements Listener {
	
	private static ChatColor messagecolor = parseColor(Config.getMessageColor());
	private static ChatColor errorcolor = parseColor(Config.getErrorMessageColor());
	private static String servername = parseColor(Config.getMessagePrefixColor()) + Config.getMessagePrefix();
	
	public static enum ErrorMessage{ONLYCONSOLE, ONLYPLAYER, NOPERMISSION, NOTENOUGHARGUMENTS}
	public static enum TitleType{TITLE, SUBTITLE, ACTIONBAR}
	
	public static void sendServerMessage(Player player, String message) {
		player.sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendServerMessage(CommandSender sender, String message) {
		sender.sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendServerErrorMessage(Player player, String message) {
		player.sendMessage(servername + errorcolor + " " + message);
	}
	
	public static void sendServerErrorMessage(CommandSender sender, String message) {
		sender.sendMessage(servername + errorcolor + " " + message);
	}
	
	public static void sendServerErrorMessage(Player player, ErrorMessage errormessage) {
		switch (errormessage) {
		case ONLYCONSOLE: player.sendMessage(servername + errorcolor + " This command can only be used by the console!"); break;
		case ONLYPLAYER: player.sendMessage(servername + errorcolor + " This command can only be used by players!"); break;
		case NOTENOUGHARGUMENTS: player.sendMessage(servername + errorcolor + " Not enough arguments!"); break;
		case NOPERMISSION: player.sendMessage(servername + errorcolor + " You have no permission to do that!"); break;
		default: break;
		}
	}
	
	public static void sendServerErrorMessage(CommandSender sender, ErrorMessage errormessage) {
		switch (errormessage) {
		case ONLYCONSOLE: sender.sendMessage(servername + errorcolor + " This command can only be used by the console!"); break;
		case ONLYPLAYER: sender.sendMessage(servername + errorcolor + " This command can only be used by players!"); break;
		case NOTENOUGHARGUMENTS: sender.sendMessage(servername + errorcolor + " Not enough arguments!"); break;
		case NOPERMISSION: sender.sendMessage(servername + errorcolor + " You have no permission to do that!"); break;
		default: break;
		}
	}
	
	public static void sendServerBroadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(servername + messagecolor + " " + message);
		}
		Bukkit.getConsoleSender().sendMessage(servername + messagecolor + " " + message);
	}
	
	public static void sendServerWorldGroupMessage(WorldGroup worldgroup, String message) {
		if (Config.isWorldGroupSystemEnabled()) {
			for (Player player : worldgroup.getPlayers()) {
				player.sendMessage(servername + messagecolor + " " + message);
			}
			Bukkit.getConsoleSender().sendMessage("[" + worldgroup.getName() + "] " + servername + messagecolor + " " + message);
		} else {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(servername + messagecolor + " " + message);
			}
			Bukkit.getConsoleSender().sendMessage(servername + messagecolor + " " + message);
		}
		
	}
	
	public static void sendTitle(Player player, String title, String subtitle) {
		player.sendTitle(title, subtitle, 10, 100, 10);
	}
	
	public static String getPlayerJoinMessage(PlayerJoinEvent event) {
		return servername + messagecolor + " " + event.getPlayer().getName() + " joined the game!";
	}
	
	public static String getPlayerQuitMessage(PlayerQuitEvent event) {
		return servername + messagecolor + " " + event.getPlayer().getName() + " left the game!";
	}
	
	public static void sendPlayerChatMessage(Player player, String message) {
		if (Config.isWorldGroupSystemEnabled()) {
			for (Player players : WorldGroupHandler.getWorldGroup(player).getPlayers()) {
				players.sendMessage(TeamHandler.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
			}
			Bukkit.getConsoleSender().sendMessage("[" + WorldGroupHandler.getWorldGroup(player).getName() + "] " + TeamHandler.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
		} else {
			for (Player players : Bukkit.getOnlinePlayers()) {
				players.sendMessage(TeamHandler.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
			}
			Bukkit.getConsoleSender().sendMessage(TeamHandler.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
		}
		
	}
	
	public static void sendPlayerPrivateMessage(Player sender, Player receiver, String message) {		
		sender.sendMessage(TeamHandler.getPlayerNameColor(sender) + "Me" + ChatColor.WHITE + " -> " + TeamHandler.getPlayerNameColor(receiver) + receiver.getName() + ChatColor.WHITE + ": " + ChatColor.GRAY + message);
		if (sender != receiver) {
			receiver.sendMessage(TeamHandler.getPlayerNameColor(sender) + sender.getPlayer().getName() + ChatColor.WHITE + " -> " + TeamHandler.getPlayerNameColor(receiver) + "Me" + ChatColor.WHITE + ": " + ChatColor.GRAY + message);
		}
		Bukkit.getConsoleSender().sendMessage("[Private] " + TeamHandler.getPlayerNameColor(sender) + sender.getPlayer().getName() + ChatColor.WHITE + " -> " + TeamHandler.getPlayerNameColor(receiver) + receiver.getName() + ChatColor.WHITE + ": " + ChatColor.GRAY + message);
	}
	
	public static void sendPlayerTeamMessage(Player player, String message) {
		for (String players : player.getScoreboard().getEntryTeam(player.getName()).getEntries()) {
			if (Bukkit.getPlayer(players) != null) {
				Bukkit.getPlayer(players).sendMessage(TeamHandler.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
			}
		}
		Bukkit.getConsoleSender().sendMessage("[" + player.getScoreboard().getEntryTeam(player.getName()).getName() + "] " + TeamHandler.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
	}
	
	public static ChatColor parseColor(String color) {
		try {
			color = color.toUpperCase();
			return ChatColor.valueOf(color);
		} catch (IllegalArgumentException exception) {
			return ChatColor.WHITE;
		}
	}
	
	public static Material parseMaterial(String material) {
		try {
			if(material.startsWith("minecraft:")) material = material.substring(10);
			material = material.toUpperCase();
			return Material.valueOf(material);
		} catch (IllegalArgumentException exception) {
			return Material.BARRIER;
		}
		
	}
	
	public static WorldOption parseWorldOption(String option) {
		option = option.toUpperCase();
		return WorldOption.valueOf(option);
	}
	
	public static Boolean parseBoolean(String bool) {
		return bool.equalsIgnoreCase("true");
	}
	
	public static GameMode parseGamemode(String gamemode) {
		gamemode = gamemode.toUpperCase();
		return GameMode.valueOf(gamemode);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		sendPlayerChatMessage(event.getPlayer(), event.getMessage());
	}

}
