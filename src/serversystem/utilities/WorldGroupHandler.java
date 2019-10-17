package serversystem.utilities;

import java.util.ArrayList;
import org.bukkit.World;
import org.bukkit.entity.Player;
import serversystem.main.Config;
import serversystem.main.SaveConfig;

public class WorldGroupHandler {
	
	private static ArrayList<WorldGroup> worldgroups = new ArrayList<>();
	
	public static void teleportPlayer(Player player, World world) {
		if(WorldGroupHandler.getWorldGroup(world).isServerGame()) {
			if(WorldGroupHandler.getWorldGroup(world).getServerGame().getLobby() != null) {
				player.teleport(WorldGroupHandler.getWorldGroup(world).getServerGame().getLobby());
			} else {
				player.teleport(WorldGroupHandler.getWorldGroup(world).getMainWorld().getSpawnLocation());
			}
		} else {
			if(!Config.hasWorldSpawn(world.getName()) && SaveConfig.loadLocation(player, WorldGroupHandler.getWorldGroup(world)) != null) {
				player.teleport(SaveConfig.loadLocation(player, WorldGroupHandler.getWorldGroup(world)));
			} else {
				player.teleport(world.getSpawnLocation());
			}
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