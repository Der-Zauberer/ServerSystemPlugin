package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldGroupHandler {
	
	private static ArrayList<WorldGroup> worldgroups = new ArrayList<>();
	
	public static void teleportPlayer(Player player, World world) {
		getWorldGroup(player).onPlayerLeave(player);
		player.teleport(new Location(world, world.getSpawnLocation().getX(), world.getSpawnLocation().getY(), world.getSpawnLocation().getZ()));
		getWorldGroup(player).onPlayerJoin(player);
		
	}
	
	public static void teleportPlayer(Player player, Location location) {
		getWorldGroup(player).onPlayerLeave(player);
		player.teleport(location);
		getWorldGroup(player).onPlayerJoin(player);
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
