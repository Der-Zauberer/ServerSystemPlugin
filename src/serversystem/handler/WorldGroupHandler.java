package serversystem.handler;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.config.SaveConfig;
import serversystem.utilities.WorldGroup;

public class WorldGroupHandler {
	
	public enum WorldSetting{DAMAGE, EXPLOSION, HUNGER, PROTECTION, PVP}
	private static ArrayList<WorldGroup> worldgroups = new ArrayList<>();
	private static boolean enabled = Config.isWorldGroupSystemEnabled();
	
	public static HashMap<Player, World> playerdeaths = new HashMap<>();
	
	public static void autoCreateWorldGroups() {
		for (World world : Bukkit.getWorlds()) {
			String worldgroup = Config.getWorldGroup(world.getName());
			if(getWorldGroup(worldgroup) == null) {
				addWorldGroup(new WorldGroup(worldgroup, world));
			} else {
				if(!getWorldGroup(worldgroup).getWorlds().contains(world)) {
					getWorldGroup(worldgroup).addWorld(world);
				}
			}
		}
	}
	
	public static void autoRemoveWorldGroups() {
		ArrayList<String> worldgroups = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			String worldgroup = Config.getWorldGroup(world.getName());
			if(!worldgroups.contains(worldgroup)) {
				worldgroups.add(worldgroup);
			}
		}
		for (int i = 0; i < worldgroups.size(); i++) {
			WorldGroup worldgroup = WorldGroupHandler.worldgroups.get(i);
			if(!worldgroups.contains(worldgroup.getName())) {
				removeWorldGroup(worldgroup);
				worldgroup = null;
			}
		}
	}
	
	public static void teleportPlayer(Player player, World world) {
		if(Config.hasWorldSpawn(world.getName()) || SaveConfig.loadLocation(player, world) == null) {
			player.teleport(world.getSpawnLocation());
		} else {
			player.teleport(SaveConfig.loadLocation(player, world));
		}
	}
	
	public static void autoSavePlayerStats() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(enabled) {
				SaveConfig.saveGamemode(player, WorldGroupHandler.getWorldGroup(player));
				SaveConfig.saveInventory(player, WorldGroupHandler.getWorldGroup(player));
				SaveConfig.saveXp(player, WorldGroupHandler.getWorldGroup(player));
			}
			SaveConfig.saveLocation(player);
		}
	}
	
	public static WorldGroup getWorldGroup(Player player) {
		for(WorldGroup worldgroup : worldgroups) {
			if(worldgroup.getWorlds().contains(player.getWorld())) {
				return worldgroup;
			}
		}
		return null;
	}
	
	public static WorldGroup getWorldGroup(World world) {
		for(WorldGroup worldgroup : worldgroups) {
			if(worldgroup.getWorlds().contains(world)) {
				return worldgroup;
			}
		}
		return null;
	}
	
	public static WorldGroup getWorldGroup(String string) {
		for(WorldGroup worldgroup : worldgroups) {
			if(worldgroup.getName().equals(string)) {
				return worldgroup;
			}
		}
		return null;
	}
	
	public static void addWorldGroup(WorldGroup worldgroup) {
		worldgroups.add(worldgroup);
	}
	
	public static void removeWorldGroup(WorldGroup worldgroup) {
		worldgroups.remove(worldgroup);
	}
	
	public static void createWorld(String name) {
		Bukkit.getWorlds().add(new WorldCreator(name).createWorld());
		World world = Bukkit.getWorld(name);
		Config.addWorld(world.getName());
		Config.addToLoadWorld(world.getName());
		WorldGroupHandler.addWorldGroup(new WorldGroup(world.getName(), world));
	}
	
	public static void createWorld(String name, WorldGroup worldgroup) {
		Bukkit.getWorlds().add(new WorldCreator(name).createWorld());
		World world = Bukkit.getWorld(name);
		Config.addWorld(world.getName());
		Config.addToLoadWorld(world.getName());
		worldgroup.addWorld(world);
	}
	
	public static void removeWorld(String world) {
		Config.removeFromLoadWorld(world);
	}
	
	public static void removeWorld(World world) {
		Config.removeFromLoadWorld(world.getName());
	}
	
	public static ArrayList<WorldGroup> getWorldgroups() {
		return worldgroups;
	}
	
	public static boolean isEnabled() {
		return enabled;
	}
	
}