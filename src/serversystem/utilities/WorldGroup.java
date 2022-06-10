package serversystem.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import serversystem.commands.BuildCommand;
import serversystem.commands.VanishCommand;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
import serversystem.config.Config.WorldOption;
import serversystem.config.SaveConfig;
import serversystem.main.ServerSystem;

public class WorldGroup {

	private String name;
	private int currentPlayers;
	private ArrayList<World> worlds;
	
	private static final ArrayList<WorldGroup> worldGroups = new ArrayList<>();
	private static boolean enabled = Config.getConfigOption(ConfigOption.ENABLE_WORLD_GROUPS);
	
	private static HashMap<Player, World> playerDeaths = new HashMap<>();

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
		SaveConfig.loadPlayerProfile(player, this);
		TeamUtil.addRoleToPlayer(player);
	}

	public void onPlayerLeave(Player player) {
		currentPlayers--;
		if (VanishCommand.isVanished(player)) {
			VanishCommand.toggleVanish(player);
		}
		TeamUtil.removePlayerFromTeam(player);
		if (BuildCommand.isInBuildmode(player)) {
			BuildCommand.toggleBuildMode(player);
		}
		SaveConfig.savePlayerProfile(player, this);
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
		final ArrayList<Player> players = new ArrayList<>();
		for (World world : worlds) {
			players.addAll(world.getPlayers());
		}
		return players;
	}
	
	public static void autoCreateWorldGroups() {
		for (World world : Bukkit.getWorlds()) {
			final String worldgroup = Config.getWorldGroup(world);
			if (getWorldGroup(worldgroup) == null) {
				addWorldGroup(new WorldGroup(worldgroup, world));
			} else if (!getWorldGroup(worldgroup).getWorlds().contains(world)) {
				getWorldGroup(worldgroup).addWorld(world);
			}
		}
	}
	
	public static void autoRemoveWorldGroups() {
		final ArrayList<String> worldgroups = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			String worldgroup = Config.getWorldGroup(world);
			if (!worldgroups.contains(worldgroup)) {
				worldgroups.add(worldgroup);
			}
		}
		for (int i = 0; i < worldgroups.size(); i++) {
			WorldGroup worldgroup = worldGroups.get(i);
			if (!worldgroups.contains(worldgroup.getName())) {
				removeWorldGroup(worldgroup);
				worldgroup = null;
			}
		}
	}
	
	public static void teleportPlayer(Player player, World world) {
		if (Config.getWorldOption(world, WorldOption.WORLD_SPAWN) || SaveConfig.loadLocation(player, world) == null) {
			player.teleport(world.getSpawnLocation());
		} else {
			player.teleport(SaveConfig.loadLocation(player, world));
		}
	}
	
	public static void autoSavePlayerStats() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (enabled) {
				SaveConfig.savePlayerProfile(player, getWorldGroup(player));
			}
			SaveConfig.saveLocation(player);
		}
	}
	
	public static WorldGroup getWorldGroup(Player player) {
		for (WorldGroup worldgroup : worldGroups) {
			if (worldgroup.getWorlds().contains(player.getWorld())) {
				return worldgroup;
			}
		}
		return null;
	}
	
	public static WorldGroup getWorldGroup(World world) {
		for (WorldGroup worldgroup : worldGroups) {
			if (worldgroup.getWorlds().contains(world)) {
				return worldgroup;
			}
		}
		return null;
	}
	
	public static WorldGroup getWorldGroup(String string) {
		for (WorldGroup worldgroup : worldGroups) {
			if (worldgroup.getName().equals(string)) {
				return worldgroup;
			}
		}
		return null;
	}
	
	public static void addWorldGroup(WorldGroup worldgroup) {
		worldGroups.add(worldgroup);
	}
	
	public static void removeWorldGroup(WorldGroup worldgroup) {
		worldGroups.remove(worldgroup);
	}
	
	public static void createWorld(String name) {
		Bukkit.getWorlds().add(new WorldCreator(name).createWorld());
		final World world = Bukkit.getWorld(name);
		Config.addWorld(world);
		Config.addLoadWorld(world.getName());
		addWorldGroup(new WorldGroup(world.getName(), world));
	}
	
	public static void createWorld(String name, WorldGroup worldgroup) {
		Bukkit.getWorlds().add(new WorldCreator(name).createWorld());
		final World world = Bukkit.getWorld(name);
		Config.addWorld(world);
		Config.addLoadWorld(world.getName());
		worldgroup.addWorld(world);
	}
	
	public static HashMap<Player, World> getPlayerDeaths() {
		return playerDeaths;
	}
	
	public static ArrayList<WorldGroup> getWorldgroups() {
		return worldGroups;
	}
	
	public static boolean isEnabled() {
		return enabled;
	}

}
