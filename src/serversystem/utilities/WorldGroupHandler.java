package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import serversystem.main.Config;
import serversystem.main.SaveConfig;

public class WorldGroupHandler {
	
	private static ArrayList<WorldGroup> worldgroups = new ArrayList<>();
	
	public static void teleportPlayer(Player player, World world) {
		if(!Config.hasWorldSpawn(world.getName()) && SaveConfig.getLocation(player, WorldGroupHandler.getWorldGroup(world)) != null) {
			player.teleport(SaveConfig.getLocation(player, WorldGroupHandler.getWorldGroup(world)));
		} else {
			player.teleport(new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY(), world.getSpawnLocation().getZ()));
		}
	}
	
	public static void teleportToWorldSpawn(Player player, WorldGroup worldgroup) {
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
}
