package serversystem.handler;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import serversystem.config.Config;

public class TeamHandler {
	
	private static HashMap<String, String> ranks = new HashMap<>();
	
	public static final String TEAMVANISH = "00Vanish";
	
	public static void initializeTeams() {
		if(Config.getSection("Ranks", false) != null) {
			for (String rank : Config.getSection("Ranks", false)) {
				if(Config.getRank(rank) != null) {
					String rankstring[] = Config.getRank(rank);
					createTeam(rank, rankstring[1], ChatHandler.parseColor(rankstring[0]));
					ranks.put(rank, rankstring[2]);
				}
			}
		}
		createTeam(TEAMVANISH, "[VANISH] ", ChatColor.GRAY);
	}
	
	public static void resetTeams() {
		for (String rank : ranks.keySet()) {
			removeTeam(rank);
		}
		removeTeam(TEAMVANISH);
	}
	
	public static void createTeam(String name, String prefix, ChatColor color) {
		if (getMainScoreboard().getTeam(name) == null) {
			getMainScoreboard().registerNewTeam(name).setPrefix(prefix);
			getMainScoreboard().getTeam(name).setColor(color);
		}
	}
	
	public static void removeTeam(String team) {
		getMainScoreboard().getTeam(team).unregister();
	}
	
	public static void addPlayerToTeam(String team, String player) {
		getMainScoreboard().getTeam(team).addEntry(player);
	}
	
	public static void addPlayerToTeam(String team, Player player) {
		getMainScoreboard().getTeam(team).addEntry(player.getName());
	}
	
	public static void removePlayerFromTeam(String team, String player) {
		getMainScoreboard().getTeam(team).removeEntry(player);
	}
	
	public static void removePlayerFromTeam(Player player) {
		try {
			getMainScoreboard().getTeam(getPlayersTeamName(player)).removeEntry(player.getName());
		} catch (Exception exception) {}
	}
	
	public static Team getPlayersTeam(Player player) {
		return player.getScoreboard().getEntryTeam(player.getName());
	}
	
	public static String getPlayersTeamName(Player player) {
		return player.getScoreboard().getEntryTeam(player.getName()).getName();
	}
	
	public static void addRoleToPlayer(Player player) {
		ArrayList<String> sortedranks = new ArrayList<>();
		sortedranks.addAll(ranks.keySet());
		sortedranks.sort(String::compareToIgnoreCase);
		for (String rank : sortedranks) {
			if(player.hasPermission(ranks.get(rank))) {
				addPlayerToTeam(rank, player);
				return;
			}
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
	
	private static Scoreboard getMainScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}

}
