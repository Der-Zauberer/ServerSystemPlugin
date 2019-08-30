package serversystem.games;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import serversystem.utilities.PlayerTeam;
import serversystem.utilities.ServerGame;
import serversystem.utilities.WorldGroup;

public class MurderGame extends ServerGame{
	
	public MurderGame(WorldGroup worldgroup, int minPlayers, int maxPlayers, int startPlayers, int startTime) {
		super(worldgroup, minPlayers, maxPlayers, startPlayers, startTime, "Murder");
		PlayerTeam.createTeam(worldgroup.getName() + "Murder", "", ChatColor.WHITE);
		PlayerTeam.createTeam(worldgroup.getName() + "Detective", "", ChatColor.WHITE);
		PlayerTeam.createTeam(worldgroup.getName() + "Innocent", "", ChatColor.WHITE);
	}
	
	@Override
	public void onGameStart() {
		super.onGameStart();
		Random random = new Random(getWorldgroup().getPlayers().size());
		PlayerTeam.addPlayerToTeam(getWorldgroup().getName() + "Murder", getWorldgroup().getPlayers().get(random.nextInt()));
		for (Player player : getWorldgroup().getPlayers()) {
			if(player.getScoreboard().getPlayerTeam(player).getName().equals(getWorldgroup().getName() + "Murder")) {
				
			}
		}
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		PlayerTeam.removeTeam(getWorldgroup().getName() + "Murder");
		PlayerTeam.removeTeam(getWorldgroup().getName() + "Murder");
		PlayerTeam.removeTeam(getWorldgroup().getName() + "Murder");
		super.finalize();
	}

}
