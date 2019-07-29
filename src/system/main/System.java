package system.main;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import system.commands.AdminCommand;
import system.commands.BuildCommand;
import system.commands.VanishCommand;
import system.commands.WorldCommand;
import system.events.SystemEvents;

public class System extends JavaPlugin{
	
	private static ArrayList<String> vanishedPlayer = new ArrayList<>();
	private static ArrayList<String> buildPlayers = new ArrayList<>();
	private static Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
	
	private static System instance;
	
	@Override
	public void onEnable() {
		new Config();
		registerEvents();
		registerCommands();
		setInstance(this);
		registerTeams();
	}

	@Override
	public void onDisable() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(isPlayerVanished(player)) {
				vanishPlayer(player);
			}
		}
	}

	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new SystemEvents(), this);
		Bukkit.getPluginManager().registerEvents(new AdminCommand(), this);
		Bukkit.getPluginManager().registerEvents(new BuildCommand(), this);
	}
	
	private void registerCommands() {
		getCommand("admin").setExecutor(new AdminCommand());
		getCommand("build").setExecutor(new BuildCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("world").setExecutor(new WorldCommand());
	}
	
	private void registerTeams() {
		createTeam("RankAdmin", "[Admin] ", ChatColor.RED);
		createTeam("RankTeam", "[Team] ", ChatColor.BLUE);
	}
		
	public static System getInstance() {
		return instance;
	}
	
	public static void setInstance(System instance) {
		System.instance = instance;
	}
	
	public static void vanishPlayer(Player player) {
		vanishPlayer(player, player);
	}
	
	public static void createTeam(String name, String prefix, ChatColor color) {
		if (scoreboard.getTeam(name) == null) {
			scoreboard.registerNewTeam(name).setPrefix(prefix);
			scoreboard.getTeam(name).setColor(color);
		}
	}
	
	public static void removeTeam(String name) {
		scoreboard.getTeam(name).unregister();
	}
	
	public static void addPlayerToTeam(String name, String player) {
		scoreboard.getTeam(name).addEntry(player);
	}
	
	public static void removePlayerFromTeam(String name, String player) {
		scoreboard.getTeam(name).removeEntry(player);
	}
	
	public static ChatColor getPlayerNameColor(Player player) {
		try {
			return player.getScoreboard().getEntryTeam(player.getName()).getColor();
		} catch (NullPointerException exception) {
			return ChatColor.WHITE;
		}
	}
	
	public static void vanishPlayer(Player player, Player sender) {
		if(player == sender) {
			if(vanishedPlayer.contains(player.getUniqueId().toString())) {
				vanishedPlayer.remove(player.getUniqueId().toString());
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					everyPlayer.showPlayer(System.getInstance(), player);
				}
				player.sendMessage(ChatColor.YELLOW + "[Server] You are no longer vanished!");
			} else {
				vanishedPlayer.add(player.getUniqueId().toString());
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					everyPlayer.hidePlayer(System.getInstance(), player);
				}
				player.sendMessage(ChatColor.YELLOW + "[Server] You are now vanished!");
			}
		} else {
			if(vanishedPlayer.contains(player.getUniqueId().toString())) {
				vanishedPlayer.remove(player.getUniqueId().toString());
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					everyPlayer.showPlayer(System.getInstance(), player);
				}
				player.sendMessage(ChatColor.YELLOW + "You are no longer vanished!");
				sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " is no longer vanished!");
			} else {
				vanishedPlayer.add(player.getUniqueId().toString());
				for(Player everyPlayer : Bukkit.getOnlinePlayers()) {
					everyPlayer.hidePlayer(System.getInstance(), player);
				}
				player.sendMessage(ChatColor.YELLOW + "You are now vanished!");
				sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " is now vanished!");
			}
		}
		if(!Bukkit.getOnlinePlayers().contains(player)) {
			sender.sendMessage(ChatColor.YELLOW + "[Server] " + player.getName() + " isn't online!");
		}
	}
	
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
	
	public static boolean isPlayerVanished(Player player) {
		if(vanishedPlayer.contains(player.getUniqueId().toString())) {
			return true;
		}
		return false;
	}
	
	public static boolean isPlayerBuildmode(Player player) {
		if(buildPlayers.contains(player.getUniqueId().toString())) {
			return true;
		}
		return false;
	}
	
}
