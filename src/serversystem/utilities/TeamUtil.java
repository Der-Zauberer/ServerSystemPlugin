package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import serversystem.commands.VanishCommand;
import serversystem.config.Config;

public class TeamUtil {
	
	private static final ArrayList<String> teams = new ArrayList<>();
	
	public static final String TEAMVANISH = "00Vanish";
	
	private TeamUtil() {}
	
	static {
		createTeams();
	}
	
	public static void createTeams() {
		createTeam(TEAMVANISH, "[VANISH]", ChatColor.GRAY);
		teams.add(TEAMVANISH);
	}
	
	public static void resetTeams() {
		for (String group : teams) {
			if (getMainScoreboard().getTeam(group) != null) {
				resetTeam(group);
			}
		}
		teams.clear();
	}
	
	public static void createTeam(String id, String prefix, ChatColor color) {
		Team team = getMainScoreboard().registerNewTeam(id);
		if (prefix != null && !prefix.isEmpty()) team.setPrefix(prefix + " ");
		team.setColor(color);
		teams.add(id);
	}
	
	public static void removeTeam(String team) {
		getMainScoreboard().getTeam(team).unregister();
		teams.remove(team);
	}
	
	private static void resetTeam(String team) {
		getMainScoreboard().getTeam(team).unregister();
	}
	
	public static void addPlayerToTeam(String team, String player) {
		getMainScoreboard().getTeam(team).addEntry(player);
	}
	
	public static void removePlayerFromTeam(String team, String player) {
		getMainScoreboard().getTeam(team).removeEntry(player);
	}
	
	public static void removePlayerFromTeam(Player player) {
		try {
			getMainScoreboard().getTeam(getPlayersTeam(player).getName()).removeEntry(player.getName());
		} catch (Exception exception) {}
	}
	
	public static Team getPlayersTeam(Player player) {
		return player.getScoreboard().getEntryTeam(player.getName());
	}
	
	public static Team getTeam(String team) {
		return getMainScoreboard().getTeam(team);
	}
	
	public static void addGroupToPlayer(Player player) {
		if (VanishCommand.isVanished(player)) {
			addPlayerToTeam(TEAMVANISH, player.getName());
		} else {
			ServerGroup group = ServerGroup.getGroup(Config.getPlayerGroup(player));
			if (group != null) addPlayerToTeam(group.getId(), player.getName());
			else removePlayerFromTeam(player);
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
