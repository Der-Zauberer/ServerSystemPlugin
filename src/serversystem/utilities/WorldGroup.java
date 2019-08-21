package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import serversystem.main.SaveConfig;
import serversystem.main.ServerSystem;

public class WorldGroup {
	
	private String name;
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
		SaveConfig.getInventory(player, this);
		PlayerTeam.addRankTeam(player);
	}
	
	public void onPlayerLeave(Player player) {
		if(PlayerVanish.isPlayerVanished(player)) {
			PlayerVanish.vanishPlayer(player);
		}
		PlayerTeam.removePlayerFromTeam(player);
		PlayerScoreboard.removePlayerFromDisplaySlot(player);
		SaveConfig.saveInventory(player, this);
		SaveConfig.saveLocation(player);
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

}
