package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.config.SaveConfig;
import serversystem.utilities.WorldGroup;

public class WorldGroupHandler {
	
	public enum WorldSetting{DAMAGE, EXPLOSION, HUNGER, PROTECTION, PVP}
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
	
	public static void deactivateWorld(World world) {
		Config.removeLoadWorld(world.getName());
	}
	
	public static void setWorldSettings(World world, WorldSetting worldsetting, boolean value) {
		setWorldSettings(world.getName(), worldsetting, value);
	}
	
	public static void setWorldSettings(String world, WorldSetting worldsetting, boolean value) {
		switch (worldsetting) {
		case DAMAGE: Config.setWorldDamage(world, value); break;
		case EXPLOSION: Config.setWorldExplosion(world, value); break;
		case HUNGER: Config.setWorldHunger(world, value); break;
		case PROTECTION: Config.setWorldProtection(world, value); break;
		case PVP: Config.setWorldPVP(world, value); break;
		}
	}
	
	public static boolean getWorldSettings(World world, WorldSetting worldsetting) {
		return getWorldSettings(world.getName(), worldsetting);
	}
	
	public static boolean getWorldSettings(String world, WorldSetting worldsetting) {
		switch (worldsetting) {
		case DAMAGE: return Config.hasWorldDamage(world);
		case EXPLOSION: return Config.hasWorldExplosion(world);
		case HUNGER: return Config.hasWorldHunger(world);
		case PROTECTION: return Config.hasWorldProtection(world);
		case PVP: return Config.hasWorldPVP(world);
		}
		return false;
	}
	
	public static void setWorldGamemode(World world, GameMode gamemode) {
		setWorldGamemode(world.getName(), gamemode);
	}
	
	public static void setWorldGamemode(String world, GameMode gamemode) {
		Config.setWorldGamemode(world, gamemode);
	}
	
	public static GameMode getWorldGamemode(World world, GameMode gamemode) {
		return getWorldGamemode(world.getName(), gamemode);
	}
	
	public static GameMode getWorldGamemode(String world, GameMode gamemode) {
		return Config.getWorldGamemode(world);
	}
	
}