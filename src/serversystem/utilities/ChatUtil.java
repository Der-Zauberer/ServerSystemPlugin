package serversystem.utilities;

import java.util.ArrayList;
import java.util.List;

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

import serversystem.commands.VanishCommand;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
import serversystem.config.Config.TitleTypeOption;
import serversystem.config.Config.WorldOption;

public class ChatUtil implements Listener {
	
	public static final String ONLY_CONSOLE = "This command can only be used by the console!";
	public static final String ONLY_PLAYER = "This command can only be used by players!";
	public static final String NO_PERMISSION = "Not enough arguments!";
	public static final String NOT_ENOUGHT_ARGUMENTS = "You have no permission to do that!";
	
	private static final ChatUtil instance = new ChatUtil();
	
	private static final ChatColor messageColor = Config.getMessageColor();
	private static final ChatColor errorColor = Config.getErrorMessageColor();
	private static final String prefix = Config.getMessagePrefix();
	
	public static enum TitleType{TITLE, SUBTITLE, ACTIONBAR}
	
	private ChatUtil() {}
	
	public static void sendMessage(Player player, String message) {
		player.sendMessage(prefix + messageColor + " " + message);
	}
	
	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(prefix + messageColor + " " + message);
	}
	
	public static void sendErrorMessage(Player player, String message) {
		player.sendMessage(prefix + errorColor + " " + message);
	}
	
	public static void sendErrorMessage(CommandSender sender, String message) {
		sender.sendMessage(prefix + errorColor + " " + message);
	}
	
	public static void sendPlayerNotOnlineErrorMessage(CommandSender sender, String player) {
		sender.sendMessage(prefix + errorColor + " The player " +  player + " is not online!");
	}
	
	public static void sendNotExistErrorMessage(CommandSender sender, String type, String name) {
		sender.sendMessage(prefix + errorColor + " The " + type + " " + name + " does not exist");
	}
	
	public static void sendBroadcastMessage(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(prefix + messageColor + " " + message);
		}
		Bukkit.getConsoleSender().sendMessage(prefix + messageColor + " " + message);
	}
	
	public static void sendWorldGroupMessage(WorldGroup worldgroup, String message) {
		if (Config.getConfigOption(ConfigOption.ENABLE_WORLD_GROUPS)) {
			for (Player player : worldgroup.getPlayers()) {
				player.sendMessage(prefix + messageColor + " " + message);
			}
			Bukkit.getConsoleSender().sendMessage("[" + worldgroup.getName() + "] " + prefix + messageColor + " " + message);
		} else {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(prefix + messageColor + " " + message);
			}
			Bukkit.getConsoleSender().sendMessage(prefix + messageColor + " " + message);
		}
		
	}
	
	public static void sendPlayerJoinMessage(PlayerJoinEvent event) {
		if (Config.getConfigOption(ConfigOption.JOIN_MESSAGE)) {
			event.setJoinMessage(prefix + messageColor + " " + event.getPlayer().getName() + " joined the game!");
		} else {
			event.setJoinMessage("");
		}
	}

	public static void sendQuitMessage(PlayerQuitEvent event) {
		if (Config.getConfigOption(ConfigOption.QUIT_MESSAGE)) {
			event.setQuitMessage(prefix + messageColor + " " + event.getPlayer().getName() + " left the game!");
		} else {
			event.setQuitMessage("");
		}
	}
	
	public static void sendChatMessage(Player player, String message) {
		for (Player players : getVisiblePlayers(player, false)) {
			players.sendMessage(TeamUtil.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
		}
		Bukkit.getConsoleSender().sendMessage(TeamUtil.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
	}
	
	public static void sendPrivateMessage(Player sender, Player receiver, String message) {		
		sender.sendMessage(TeamUtil.getPlayerNameColor(sender) + "Me" + ChatColor.WHITE + " -> " + TeamUtil.getPlayerNameColor(receiver) + receiver.getName() + ChatColor.WHITE + ": " + ChatColor.GRAY + message);
		if (sender != receiver) {
			receiver.sendMessage(TeamUtil.getPlayerNameColor(sender) + sender.getPlayer().getName() + ChatColor.WHITE + " -> " + TeamUtil.getPlayerNameColor(receiver) + "Me" + ChatColor.WHITE + ": " + ChatColor.GRAY + message);
		}
		Bukkit.getConsoleSender().sendMessage("[Private] " + TeamUtil.getPlayerNameColor(sender) + sender.getPlayer().getName() + ChatColor.WHITE + " -> " + TeamUtil.getPlayerNameColor(receiver) + receiver.getName() + ChatColor.WHITE + ": " + ChatColor.GRAY + message);
	}
	
	public static void sendTeamMessage(Player player, String message) {
		for (String players : player.getScoreboard().getEntryTeam(player.getName()).getEntries()) {
			if (Bukkit.getPlayer(players) != null) {
				Bukkit.getPlayer(players).sendMessage(TeamUtil.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
			}
		}
		Bukkit.getConsoleSender().sendMessage("[" + player.getScoreboard().getEntryTeam(player.getName()).getName() + "] " + TeamUtil.getPlayerNameColor(player) + player.getName() + ChatColor.WHITE + ": " + message);
	}
	
	public static void sendTitle(Player player, String title, String subtitle) {
		player.sendTitle(title, subtitle, 10, 100, 10);
	}
	
	public static void sendServerTitle(Player player) {
		final String title = Config.getTitle(TitleTypeOption.TITLE);
		final String subtitle = Config.getTitle(TitleTypeOption.SUBTITLE);
		if (title != null && subtitle != null) ChatUtil.sendTitle(player, title, subtitle);
	}
	
	public static void sendServerTablistTitle(Player player) {
		final String tablistTitle = Config.getTitle(TitleTypeOption.TABLIST_TITLE);
		final String tablistSubtitle = Config.getTitle(TitleTypeOption.TABLIST_SUBTITLE);
		if (tablistTitle != null) player.setPlayerListHeader(tablistTitle);
		if (tablistSubtitle != null) player.setPlayerListFooter(tablistSubtitle);
	}
	
	public static List<Player> getVisiblePlayers(Player player, boolean excludeVanished) {
		final List<Player> players = new ArrayList<>();
		if (VanishCommand.isVanished(player)) excludeVanished = false;
		for (Player everyPlayer : (!Config.getConfigOption(ConfigOption.GLOBAL_CHAT_AND_TABLIST) && Config.getConfigOption(ConfigOption.ENABLE_WORLD_GROUPS)) ? WorldGroup.getWorldGroup(player).getPlayers() : Bukkit.getOnlinePlayers()) {
			if (!excludeVanished || !VanishCommand.isVanished(everyPlayer)) players.add(everyPlayer);
		}
		return players;
	}
	
	public static List<String> getReachableChatPlayers(CommandSender sender) {
		final List<String> players = new ArrayList<>();
		for (Player everyPlayer : Bukkit.getOnlinePlayers()) {
			if (!(sender instanceof Player) || !VanishCommand.isVanished(everyPlayer)) players.add(everyPlayer.getName());
		}
		return players;
	}
	
	public static List<String> cutArguments(String args[], List<String> commands) {
		final String command = args[args.length - 1];
		final List<String> output = new ArrayList<>();
		if (!command.isEmpty() && !command.equals("")) {
			for (String string : commands) {
				if (string.startsWith(command)) output.add(string);
			}
			return output;
		} else {
			return commands;
		}
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
			if (material.startsWith("minecraft:")) material = material.substring(10);
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
	
	public static ChatUtil getInstance() {
		return instance;
	}
	
	@EventHandler
	public static void onChat(AsyncPlayerChatEvent event) {
		event.setCancelled(true);
		sendChatMessage(event.getPlayer(), event.getMessage());
	}

}
