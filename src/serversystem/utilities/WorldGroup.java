package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import serversystem.commands.BuildCommand;
import serversystem.commands.VanishCommand;
import serversystem.config.SaveConfig;
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
			for (Player everyplayer : world.getPlayers()) {
				everyplayer.showPlayer(ServerSystem.getInstance(), player);
				player.showPlayer(ServerSystem.getInstance(), everyplayer);
			}
		}
		SaveConfig.loadInventory(player, this);
		SaveConfig.loadXp(player, this);
		SaveConfig.loadGamemode(player, this);
		TeamHandler.addRoleToPlayer(player);
	}

	public void onPlayerLeave(Player player) {
		currentPlayers--;
		if (VanishCommand.isVanished(player)) {
			VanishCommand.toggleVanish(player);
		}
		TeamHandler.removePlayerFromTeam(player);
		if (BuildCommand.isInBuildmode(player)) {
			BuildCommand.toggleBuildMode(player);
		}
		SaveConfig.saveInventory(player, this);
		SaveConfig.saveXp(player, this);
		SaveConfig.saveGamemode(player, this);
		player.getInventory().clear();
		player.setLevel(0);
		player.setExp(0);
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
