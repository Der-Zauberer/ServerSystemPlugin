package serversystem.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class ChatUtil implements Listener {
	
	public static final String ONLY_CONSOLE = "This command can only be used by the console!";
	public static final String ONLY_PLAYER = "This command can only be used by players!";
	public static final String NO_PERMISSION = "You have no permission to do that!";
	public static final String NOT_ENOUGHT_ARGUMENTS = "Not enough arguments!";
	public static final String TO_MANY_ARGUMENTS = "To many arguments!";
	
	private static final ListenerClass listener = new ListenerClass();
	
	private static final ChatColor messageColor = Config.getMessageColor();
	private static final ChatColor errorColor = Config.getErrorColor();
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
	
	public static <T> void processInput(CommandSender sender, String args[], int offset, String type, String name, String option, boolean removable, Function<String, T> parseObject, Function<T, Boolean> faildAction, Consumer<T> setter, Supplier<?> getter) {
		if (args.length == offset) {
			String value = getter.get() != null ? getter.get().toString() : "not set";
			sendMessage(sender, "The " + option + " of the " + type + " " + name + " is " + value + "!");
		} else if (args.length == offset + 1) {
			T object = parseObject.apply(args[offset]);
			if (removable && args[offset].equals("remove")) {
				setter.accept(null);
				sendMessage(sender, "The " + option + " of the " + type + " " + name + " has been removed!");
			} else if (object != null && faildAction.apply(object)) {
				setter.accept(object);
				sendMessage(sender, "The " + option + " of the " + type + " " + name + " has been set to " + args[offset] + "!");
			} else if (object == null) {
				sendNotExistErrorMessage(sender, option, args[offset]);
			}
		} else {
			sendErrorMessage(sender, TO_MANY_ARGUMENTS);
		}
	}
	
	public static boolean processListInput(CommandSender sender, String args[], int offset, String option, List<String> list) {
		if (args.length == offset || (args.length == offset + 1 && !args[offset].equals("list"))) {
			sendErrorMessage(sender, NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == offset + 2 || (args.length == offset + 1 && args[offset].equals("list"))) {
			if (args[offset].equals("list")) {
				ChatUtil.sendMessage(sender, "The list " + option + " has the following entries:");
				for (int i = 0; i < 30 && i < list.size() ; i++) ChatUtil.sendMessage(sender, "    " + list.get(i));
				if (list.size() > 29) ChatUtil.sendMessage(sender, "    " + "And " + (list.size() - 29) + "more...");
				return false;
			}
			String string = args[offset + 1];
			if (args[offset].equals("add")) {
				if (!list.contains(string)) {
					list.add(string);
					ChatUtil.sendMessage(sender, "Added " + string + " to the list " + option + "!");
					return true;
				} else {
					ChatUtil.sendErrorMessage(sender, string + " is already in the list " + option + "!");
				}
			} else if (args[offset].equals("remove")) {
				if (list.contains(string)) {
					list.remove(string);
					ChatUtil.sendMessage(sender, "Removed " + string + " from the list " + option + "!");
					return true;
				} else {
					ChatUtil.sendErrorMessage(sender, string + " is not in the list " + option + "!");
				}
			}
		} else {
			sendErrorMessage(sender, TO_MANY_ARGUMENTS);
		}
		return false;
	}
	
	public static <T> T getValue(T value, T standard) {
		return Optional.ofNullable(value).orElse(standard);
	}
	
	public static <T extends Comparable<T>> T getValue(T value, T standard, T min, T max) {
		return Optional.ofNullable(value).filter(x -> x.compareTo(min) >= 0 && x.compareTo(max) <= 0).orElse(standard);
	}
	
	public static <T extends Enum<T>> T getValue(String string, T t[], T standard) {
		return Arrays.asList(t).stream().filter(constant -> constant.name().toLowerCase().equals(string)).findFirst().orElse(standard);
	}
	
	public static <T extends Enum<T>> T getValue(String string, T t[]) {
		return Arrays.asList(t).stream().filter(constant -> constant.name().toLowerCase().equals(string)).findFirst().orElse(null);
	}
	
	public static boolean getCommandLayer(int layer, String args[]) {
		return args.length == layer && (layer < 2 || !args[layer - 2].isEmpty());
	}
	
	public static List<String> getPlayerList(CommandSender sender) {
		return Bukkit.getOnlinePlayers().stream().filter(everyPlayer -> !(sender instanceof Player) || sender == everyPlayer || !VanishCommand.isVanished(everyPlayer)).map(player -> player.getName()).collect(Collectors.toList());
	}
	
	public static <T extends Enum<?>> List<String> getEnumList(T t[]) {
		return Arrays.stream(t).map(value -> value.name().toLowerCase()).collect(Collectors.toList());
	}
	
	public static <T> List<String> getList(List<T> list, Function<T, String> map) {
		return list.stream().map(map).collect(Collectors.toList());
	}
	
	public static List<String> getList(ServerList<?> list) {
		return list.stream().map(ServerEntity::getName).collect(Collectors.toList());
	}
	
	public static List<String> removeWrong(List<String> list, String args[]) {
		list.removeIf(commandString -> !commandString.startsWith(args[args.length - 1]));
		return list;
	}
	
	public static ListenerClass getListener() {
		return listener;
	}
	
	private static class ListenerClass implements Listener {
		
		@EventHandler
		public static void onChat(AsyncPlayerChatEvent event) {
			event.setCancelled(true);
			sendChatMessage(event.getPlayer(), event.getMessage());
		}
		
	}

}
