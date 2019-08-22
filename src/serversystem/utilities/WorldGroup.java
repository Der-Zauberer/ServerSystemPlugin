package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import serversystem.main.SaveConfig;
import serversystem.main.ServerSystem;

public class WorldGroup {
	
	private String name;
	private Objective belowNameObjective;
	private Objective sidebarObjective;
	private Objective playerListObjective;
	private ArrayList<World> worlds;
	
	public WorldGroup(String name, World mainworld) {
		this.name = name;
		worlds = new ArrayList<>();
		worlds.add(mainworld);
	}
	
	public void onPlayerJoin(Player player) {
		for (Player everyplayer : Bukkit.getOnlinePlayers()) {
			everyplayer.hidePlayer(ServerSystem.getInstance(), player);
			player.hidePlayer(ServerSystem.getInstance(), everyplayer);
		}
		for (World world : worlds) {
			for(Player everyplayer : world.getPlayers()) {
				everyplayer.showPlayer(ServerSystem.getInstance(), player);
				player.showPlayer(ServerSystem.getInstance(), everyplayer);
			}
		}
		if(belowNameObjective != null) {belowNameObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);}
		if(sidebarObjective != null) {sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);}
		if(playerListObjective != null) {playerListObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);}
		SaveConfig.getInventory(player, this);
		PlayerTeam.addRankTeam(player);
	}
	
	public void onPlayerLeave(Player player) {
		if(PlayerVanish.isPlayerVanished(player)) {
			PlayerVanish.vanishPlayer(player);
		}
		PlayerTeam.removePlayerFromTeam(player);
		PlayerScoreboard.removePlayerFromDisplaySlot(player);
		if (PlayerBuildMode.isPlayerBuildmode(player)) {
			PlayerBuildMode.buildmodePlayer(player);
		}
		SaveConfig.saveInventory(player, this);
		SaveConfig.saveLocation(player);
	}
	
	public void addObjective(Objective objective, DisplaySlot displayslot) {
		switch (displayslot) {
		case BELOW_NAME: belowNameObjective = objective; break;
		case SIDEBAR: sidebarObjective = objective; break;
		case PLAYER_LIST: playerListObjective = objective; break;
		default:break;}
	}
	
	public Objective getObjective(DisplaySlot displayslot) {
		switch (displayslot) {
		case BELOW_NAME: return belowNameObjective;
		case SIDEBAR: return sidebarObjective;
		case PLAYER_LIST: return playerListObjective;
		default:break;}
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public ArrayList<World> getWorlds() {
		return worlds;
	}
	
	public World getMainWorld(){
		return worlds.get(0);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addWorld(World world) {
		worlds.add(world);
	}
	
	public void removeWorld(World world) {
		worlds.remove(world);
	}
	
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<>();
		for (World world : worlds) {
			players.addAll(world.getPlayers());
		}
		return players;
	}

}
