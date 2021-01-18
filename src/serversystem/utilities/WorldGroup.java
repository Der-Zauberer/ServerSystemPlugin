package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import serversystem.config.SaveConfig;
import serversystem.handler.PlayerBuildMode;
import serversystem.handler.PlayerVanish;
import serversystem.handler.TeamHandler;
import serversystem.main.ServerSystem;

public class WorldGroup {
	
	private String name;
	private int currentPlayers;
	private ArrayList<World> worlds;
	
	public WorldGroup(String name, World world) {
		this.name = name;
		worlds = new ArrayList<>();
		worlds.add(world);
	}
	
	public WorldGroup(String name, ArrayList<World> worlds) {
		this.name = name;
		this.worlds = worlds;
	}
	
	public void onPlayerJoin(Player player) {
		currentPlayers++;
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
		SaveConfig.loadInventory(player, this);
		SaveConfig.loadXp(player, this);
		SaveConfig.loadGamemode(player, this);
		SaveConfig.loadFlying(player, this);
		TeamHandler.addRoleToPlayer(player);
	}
	
	public void onPlayerLeave(Player player) {
		currentPlayers--;
		if(PlayerVanish.isPlayerVanished(player)) {
			PlayerVanish.vanishPlayer(player);
		}
		TeamHandler.removePlayerFromTeam(player);
		if (PlayerBuildMode.isPlayerInBuildmode(player)) {
			PlayerBuildMode.buildmodePlayer(player);
		}
		SaveConfig.saveInventory(player, this);
		SaveConfig.saveXp(player, this);
		SaveConfig.saveGamemode(player, this);
		SaveConfig.saveFlying(player, this);
		SaveConfig.saveLocation(player);
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
