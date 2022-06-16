package serversystem.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import serversystem.commands.VanishCommand;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
import serversystem.config.Config.WorldOption;
import serversystem.config.SaveConfig;
import serversystem.main.ServerSystem;

public class WorldGroup {

	private final String name;
	private final ArrayList<Player> players;
	private final ArrayList<World> worlds;
	
	private static final ArrayList<WorldGroup> worldGroups = new ArrayList<>();
	private static boolean enabled = Config.getConfigOption(ConfigOption.ENABLE_WORLD_GROUPS);
	
	private static HashMap<Player, World> playerDeaths = new HashMap<>();

	public WorldGroup(String name, World world) {
		this.name = name;
		players = new ArrayList<>();
		worlds = new ArrayList<>();
		worlds.add(world);
	}

	public WorldGroup(String name, ArrayList<World> worlds) {
		this.name = name;
		players = new ArrayList<>();
		this.worlds = worlds;
	}

	public void join(Player player) {
		players.add(player);
		for (Player everyPlayer : Bukkit.getOnlinePlayers()) {
			everyPlayer.hidePlayer(ServerSystem.getInstance(), player);
			player.hidePlayer(ServerSystem.getInstance(), everyPlayer);
		}
		for (Player everyPlayer : ChatUtil.getVisiblePlayers(player, false)) {
			everyPlayer.showPlayer(ServerSystem.getInstance(), player);
			if (!VanishCommand.isVanished(everyPlayer)) player.showPlayer(ServerSystem.getInstance(), everyPlayer);
		}
		SaveConfig.loadPlayerProfile(player, this);
		TeamUtil.addRoleToPlayer(player);
	}

	public void quit(Player player) {
		TeamUtil.removePlayerFromTeam(player);
		SaveConfig.savePlayerProfile(player, this);
		players.remove(player);
	}

	public String getName() {
		return name;
	}
	public ArrayList<World> getWorlds() {
		return worlds;
	}

	public void addWorld(World world) {
		worlds.add(world);
	}

	public void removeWorld(World world) {
		worlds.remove(world);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public static void autoCreateWorldGroups() {
		for (World world : Bukkit.getWorlds()) {
			final String worldGroup = Config.getWorldGroup(world);
			if (getWorldGroup(worldGroup) == null) {
				addWorldGroup(new WorldGroup(worldGroup, world));
			} else if (!getWorldGroup(worldGroup).getWorlds().contains(world)) {
				getWorldGroup(worldGroup).addWorld(world);
			}
		}
	}
	
	public static void autoRemoveWorldGroups() {
		final ArrayList<String> worldGroups = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			String worldgroup = Config.getWorldGroup(world);
			if (!worldGroups.contains(worldgroup)) {
				worldGroups.add(worldgroup);
			}
		}
		for (int i = 0; i < worldGroups.size(); i++) {
			WorldGroup worldgroup = WorldGroup.worldGroups.get(i);
			if (!worldGroups.contains(worldgroup.getName())) {
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
			if (enabled) SaveConfig.savePlayerProfile(player, getWorldGroup(player));
			SaveConfig.saveLocation(player);
		}
	}
	
	public static WorldGroup getWorldGroup(Player player) {
		for (WorldGroup worldgroup : worldGroups) {
			if (worldgroup.getPlayers().contains(player)) return worldgroup;
		}
		return null;
	}
	
	public static WorldGroup getWorldGroup(World world) {
		for (WorldGroup worldgroup : worldGroups) {
			if (worldgroup.getWorlds().contains(world)) return worldgroup;
		}
		return null;
	}
	
	public static WorldGroup getWorldGroup(String name) {
		for (WorldGroup worldgroup : worldGroups) {
			if (worldgroup.getName().equals(name)) return worldgroup;
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
