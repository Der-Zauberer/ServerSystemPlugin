package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import serversystem.config.Config;

public class TeamUtil {
	
	private static final ArrayList<String> groups = new ArrayList<>();
	
	public static final String TEAMVANISH = "00Vanish";
	
	private TeamUtil() {}
	
	static {
		if (Config.getSection("Groups", false) != null) {
			for (String group : Config.getSection("Groups", false)) {
				if (Config.getGroupID(group) != null) {
					createTeam(Config.getGroupID(group), Config.getGroupPrefix(group), Config.getGroupColor(group));
				}
			}
		}
		createTeam(TEAMVANISH, "[VANISH] ", ChatColor.GRAY);
		groups.add(TEAMVANISH);
	}
	
	public static void resetTeams() {
		for (String group : groups) {
			if (getMainScoreboard().getTeam(group) != null) {
				resetTeam(group);
			}
		}
		groups.clear();
	}
	
	public static void createTeam(String id, String prefix, ChatColor color) {
		if (getMainScoreboard().getTeam(id) == null) {
			getMainScoreboard().registerNewTeam(id).setPrefix(prefix);
			getMainScoreboard().getTeam(id).setColor(color);
			groups.add(id);
		}
	}
	
	public static void removeTeam(String team) {
		getMainScoreboard().getTeam(team).unregister();
		groups.remove(team);
	}
	
	private static void resetTeam(String team) {
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
		groups.sort(String::compareToIgnoreCase);
		if (Config.getGroupID(Config.getPlayerGroup(player)) != null) {
			addPlayerToTeam(Config.getGroupID(Config.getPlayerGroup(player)), player);
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
	
	private static Scoreboard getMainScoreboard() {
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}

}
