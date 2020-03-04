package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.World;
import org.bukkit.entity.Player;
import serversystem.main.Config;
import serversystem.main.SaveConfig;
import serversystem.utilities.WorldGroup;

public class WorldGroupHandler {
	
	private static ArrayList<WorldGroup> worldgroups = new ArrayList<>();
	
	public static void teleportPlayer(Player player, World world) {
		WorldGroup worldgroup = getWorldGroup(world);
		if(worldgroup.isServerGame()) {
			if(worldgroup.getServerGame().getLobby() != null) {
				player.teleport(worldgroup.getServerGame().getLobby());
			} else {
				player.teleport(worldgroup.getMainWorld().getSpawnLocation());
			}
		} else {
			if(!Config.hasWorldSpawn(world.getName()) && SaveConfig.loadLocation(player, worldgroup) != null) {
				player.teleport(SaveConfig.loadLocation(player, worldgroup));
			} else {
				player.teleport(world.getSpawnLocation());
			}
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
	
}