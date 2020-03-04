package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import serversystem.handler.TeamHandler;
import serversystem.main.SaveConfig;
import serversystem.main.ServerSystem;

public class WorldGroup {
	
	private String name;
	private int currentPlayers;
	private Objective belowNameObjective;
	private Objective sidebarObjective;
	private Objective playerListObjective;
	private ArrayList<World> worlds;
	private ServerGame servergame;
	
	public WorldGroup(String name, World mainworld) {
		this.name = name;
		worlds = new ArrayList<>();
		worlds.add(mainworld);
	}
	
	public WorldGroup(String name, World mainworld, ServerGame servergame) {
		this(name, mainworld);
		this.servergame = servergame;
	}
	
	public void onPlayerJoin(Player player) {
		currentPlayers++;
		if(isServerGame()) {
			servergame.onPlayerJoin(player);
		}
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
		SaveConfig.loadInventory(player, this);
		SaveConfig.loadXp(player, this);
		SaveConfig.loadGamemode(player, this);
		TeamHandler.addPlayerToRole(player);
	}
	
	public void onPlayerLeave(Player player) {
		currentPlayers--;
		if(PlayerVanish.isPlayerVanished(player)) {
			PlayerVanish.vanishPlayer(player);
		}
		TeamHandler.removePlayerFromTeam(player);
		PlayerScoreboard.removePlayerFromDisplaySlot(player);
		if (PlayerBuildMode.isPlayerBuildmode(player)) {
			PlayerBuildMode.buildmodePlayer(player);
		}
		SaveConfig.saveInventory(player, this);
		SaveConfig.saveXp(player, this);
		SaveConfig.saveGamemode(player, this);
		if (!isServerGame()) {
			SaveConfig.saveLocation(player);
		}
		if(isServerGame()) {
			servergame.onPlayerLeave(player);
		}
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
	
	public int getCurrentPlayers() {
		return currentPlayers;
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
	
	public boolean isServerGame() {
		if(servergame != null) {
			return true;
		}
		return false;
	}
	
	public void setServerGame(ServerGame servergame) {
		this.servergame = servergame;
	}
	
	public ServerGame getServerGame() {
		return servergame;
	}
	
	public ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<>();
		for (World world : worlds) {
			players.addAll(world.getPlayers());
		}
		return players;
	}

}
