package system.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerTeam {
	
	public static void createTeam(String name, String prefix, ChatColor color) {
		if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam(name) == null) {
			Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(name).setPrefix(prefix);
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam(name).setColor(color);
		}
	}
	
	public static void removeTeam(String team) {
		Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team).unregister();
	}
	
	public static void addPlayerToTeam(String team, String player) {
		Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team).addEntry(player);
	}
	
	public static void addPlayerToTeam(String team, Player player) {
		Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team).addEntry(player.getName());
	}
	
	public static void removePlayerFromTeam(String team, String player) {
		Bukkit.getScoreboardManager().getMainScoreboard().getTeam(team).removeEntry(player);
	}
	
	public static void removePlayerFromTeam(Player player) {
		try {
			Bukkit.getScoreboardManager().getMainScoreboard().getTeam(Bukkit.getPlayer(player.getName()).getScoreboard().getEntryTeam(player.getName()).getName()).removeEntry(player.getName());
		} catch (Exception exception) {}
	}
	
	public static void addRankTeam(Player player) {
		if(player.hasPermission("system.rank.admin")) {
			addPlayerToTeam("RankAdmin", player);
		}else if(player.hasPermission("system.rank.team")) {
			addPlayerToTeam("RankTeam", player);
		} else {
			removePlayerFromTeam(player);
		}
	}
	
	public static ChatColor getPlayerNameColor(Player player) {
		try {
			return player.getScoreboard().getEntryTeam(player.getName()).getColor();
		} catch (NullPointerException exception) {
			return ChatColor.WHITE;
		}
	}

}
