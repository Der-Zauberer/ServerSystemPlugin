package serversystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerScoreboard {
	
	private static Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
	
	public static void setMurderScoreboard() {
		Objective objective = scoreboard.registerNewObjective("murder", "dummy", "");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Murder");
		objective.getScore(" §1§f ").setScore(6);
		objective.getScore(" §fRole: ").setScore(5);
		objective.getScore("§4 ").setScore(4);
		objective.getScore(" §0§f ").setScore(3);
		objective.getScore(" §fPlayers: ").setScore(2);
		objective.getScore("§2 ").setScore(1);
		objective.getScore(" ").setScore(0);
		Team scoreRole = scoreboard.registerNewTeam("scRole");
		Team scorePlayers = scoreboard.registerNewTeam("scPlayers"); 
		scoreRole.addEntry("§4 ");
		scorePlayers.addEntry("§2 ");
	}
	
	public static void setMurderToPlayer(Player player) {
		scoreboard.getTeam("scRole").setSuffix("Murder");
		scoreboard.getTeam("scPlayers").setSuffix("2");
		player.setScoreboard(scoreboard);
	}
	
	public static void removePlayerFromDisplaySlot(Player player) {
		player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		player.getScoreboard().clearSlot(DisplaySlot.BELOW_NAME);
		player.getScoreboard().clearSlot(DisplaySlot.PLAYER_LIST);
		
	}

}
