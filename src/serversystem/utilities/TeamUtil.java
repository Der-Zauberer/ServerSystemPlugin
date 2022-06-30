package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import serversystem.commands.VanishCommand;

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
	
	public static Team createTeam(String id, String prefix, ChatColor color) {
		Team team;
		if ((team = getMainScoreboard().getTeam(id)) == null) {
			team = getMainScoreboard().registerNewTeam(id);
			if (prefix != null && !prefix.isEmpty()) team.setPrefix(prefix + " ");
			team.setColor(color);
			teams.add(id);
		}
		if (!teams.contains(team.getName())) teams.add(team.getName());
		return team;
	}
	
	public static void removeTeam(Team team) {
		if (teams.contains(team.getName())) teams.remove(team.getName());
		try {team.unregister();} catch (IllegalStateException exception) {}
	}
	
	public static void removeTeam(String name) {
		Team team;
		if ((team = getMainScoreboard().getTeam(name)) != null) {
			if (teams.contains(name)) teams.remove(name);
			team.unregister();
		}
	}
	
	private static void resetTeam(String name) {
		Team team;
		if ((team = getMainScoreboard().getTeam(name)) != null) team.unregister();
	}
	
	public static void addPlayerToTeam(Team team, String player) {
		try {team.addEntry(player);} catch (IllegalStateException exception) {}
	}
	
	public static void addPlayerToTeam(String team, String player) {
		final Team teamObject = getMainScoreboard().getTeam(team);
		if (teamObject != null) teamObject.addEntry(player);
	}
	
	public static void removePlayerFromTeam(String team, String player) {
		final Team teamObject = getMainScoreboard().getTeam(team);
		if (teamObject != null) teamObject.removeEntry(player);
	}
	
	public static void removePlayerFromTeam(Team team, String player) {
		try {team.removeEntry(player);} catch (IllegalStateException exception) {}
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
			ServerGroup group = ServerGroup.getGroupByPlayer(player);
			if (group != null && group.getTeam() != null) addPlayerToTeam(group.getTeam(), player.getName());
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
