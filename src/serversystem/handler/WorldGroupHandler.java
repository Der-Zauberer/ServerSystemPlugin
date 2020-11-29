package serversystem.handler;

import java.util.ArrayList;
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
	
	public static void initializeWorldGroups() {
		if(enabled) {
			for (World world : Bukkit.getWorlds()) {
				WorldGroupHandler.addWorldGroup(new WorldGroup(world.getName(), world));
			}
		}
	}
	
	public static void teleportPlayer(Player player, World world) {
		if(enabled) {
			WorldGroup worldgroup = getWorldGroup(world);
			if(!Config.hasWorldSpawn(world.getName()) && SaveConfig.loadLocation(player, worldgroup) != null && SaveConfig.loadLocation(player, worldgroup) != null) {
				player.teleport(SaveConfig.loadLocation(player, worldgroup));
			} else {
				player.teleport(world.getSpawnLocation());
			}
		} else {
			player.teleport(world.getSpawnLocation());
		}
	}
	
	public static void teleportToWorldGroupSpawn(Player player, WorldGroup worldgroup) {
			player.getPlayer().teleport(worldgroup.getMainWorld().getSpawnLocation());
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
		Config.addLoadWorld(world.getName());
		WorldGroupHandler.addWorldGroup(new WorldGroup(world.getName(), world));
	}
	
	public static void createWorld(String name, WorldGroup worldgroup) {
		Bukkit.getWorlds().add(new WorldCreator(name).createWorld());
		World world = Bukkit.getWorld(name);
		Config.addWorld(world.getName());
		Config.addLoadWorld(world.getName());
		worldgroup.addWorld(world);
	}
	
	public static void removeWorld(String world) {
		Config.removeLoadWorld(world);
	}
	
	public static void removeWorld(World world) {
		Config.removeLoadWorld(world.getName());
	}
	
}