package serversystem.config;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import serversystem.handler.ChatHandler;
import serversystem.utilities.ServerWarp;
import serversystem.utilities.WorldGroup;

public class SaveConfig {
	
	private static File file = new File("plugins/ServerSystem", "saveconfig.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	public static enum LogTypes{WARNING, REPORT, BAN, KICK};
	
	public SaveConfig() {
		if(!file.exists()) {
			saveConfig();
		}
	}
	
	public static ArrayList<String> getSection(String section, boolean key) {
		ArrayList<String> list = new ArrayList<>();
		if(config.getConfigurationSection(section) != null) {
			for (String name : config.getConfigurationSection(section).getKeys(key)) {
				list.add(name);
			}
		}
		return list;
	}
	
	public static void setWarp(ServerWarp warp) {
		config.set("Warps." + warp.getName() + ".material", warp.getMaterial().toString().toLowerCase());
		config.set("Warps." + warp.getName() + ".location", warp.getLocation());
		config.set("Warps." + warp.getName() + ".global", warp.isGlobal());
		if(warp.getPermission() != null) {
			config.set("Warps." + warp.getName() + ".permission", warp.getPermission());
		} else {
			config.set("Warps." + warp.getName() + ".permission", null);
		}
		saveConfig();
	}
	
	public static void removeWarp(ServerWarp warp) {
		config.set("Warps." + warp.getName(), null);
		saveConfig();
	}
	
	public static ServerWarp getWarp(String name) {
		ServerWarp warp = new ServerWarp(name, ChatHandler.parseMaterial(config.getString("Warps." + name + ".material")), config.getLocation("Warps." + name + ".location"), config.getBoolean("Warps." + name + ".global"));
		if(config.getString("Warps." + name + ".permission") != null) {
			warp.setPermission(config.getString("Warps." + name + ".permission"));
		}
		return warp;
	}
	
	public static ArrayList<ServerWarp> getWarps() {
		ArrayList<ServerWarp> warps = new ArrayList<>();
		for(String name : getSection("Warps", false)) {
			warps.add(getWarp(name));
		}
		return warps;
	}
	
	public static void saveInventory(Player player, WorldGroup worldgroup) {
		String configarmor = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Armor";
    	String configcontent = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Content";
		for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
			config.set(configarmor + "." + i, player.getInventory().getArmorContents()[i]);
		}
		for (int i = 0; i < player.getInventory().getContents().length; i++) {
			config.set(configcontent + "." + i, player.getInventory().getContents()[i]);
		}
		saveConfig();
	}
	
    public static void loadInventory(Player player, WorldGroup worldgroup) {
    	String configarmor = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Armor";
    	String configcontent = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Content";
    	player.getInventory().clear();
    	ItemStack[] content = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
        	if(config.getItemStack(configarmor + "." + i) != null) {
        		content[i] = config.getItemStack(configarmor + "." + i);
        	}
    	}
        player.getInventory().setArmorContents(content);
        content = new ItemStack[41];
        for (int i = 0; i < 41; i++) {
    		content[i] = config.getItemStack(configcontent + "." + i);
    	}
        player.getInventory().setContents(content);	
	}
    
    public static void saveXp(Player player, WorldGroup worldgroup) {
    	config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Level", player.getLevel());
    	config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Experience", player.getExp());
    	saveConfig();
    }
    
    public static void loadXp(Player player, WorldGroup worldgroup) {
    	player.setLevel(config.getInt("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Level"));
    	player.setExp(config.getInt("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Experience"));
    }
    
    public static void saveGamemode(Player player, WorldGroup worldgroup) {
    	switch (player.getGameMode()) {
		case SURVIVAL: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 0);	break;
		case CREATIVE: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 1);	break;
		case ADVENTURE: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 2); break;
		case SPECTATOR: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 3); break;
		default: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 0); break;
		}
    	saveConfig();
    }
    
    public static void loadGamemode(Player player, WorldGroup worldgroup) {
    	if (getSection("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId(), true).contains("Gamemode")) {
    		switch (config.getInt("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode")) {
        	case 0: player.setGameMode(GameMode.SURVIVAL); break;
        	case 1: player.setGameMode(GameMode.CREATIVE); break;
        	case 2: player.setGameMode(GameMode.ADVENTURE); break;
        	case 3: player.setGameMode(GameMode.SPECTATOR); break;
        	default: player.setGameMode(GameMode.ADVENTURE);
        	}
    	} else {
    		player.setGameMode(Config.getWorldGamemode(player.getWorld().getName()));
    	}
    }
    
    public static void saveLocation(Player player) {
    	String path = "Worlds." + player.getWorld().getName() + "." + player.getUniqueId();
		config.set(path +".X", player.getLocation().getX());
		config.set(path +".Y", player.getLocation().getY());
		config.set(path +".Z", player.getLocation().getZ());
		config.set(path +".Pitch", player.getLocation().getPitch());
		config.set(path +".Yaw", player.getLocation().getYaw());
		config.set(path +".Fly", player.isFlying());
		saveConfig();
    }
    
    public static Location loadLocation(Player player, World world) {
    	if(config.get("Worlds." + world.getName() + "." + player.getUniqueId()) == null) {
    		return null;
    	}
    	String path = "Worlds." + world.getName() + "." + player.getUniqueId();
    	Location location = new Location(world, config.getDouble(path +".X"), config.getDouble(path +".Y"), config.getDouble(path +".Z"), (float) config.getDouble(path +".Pitch"), (float) config.getDouble(path +".Yaw"));
    	return location;
    }
    
    public static boolean loadFlying(Player player, World world) {
    	String path = "Worlds." + world.getName() + "." + player.getUniqueId();
    	return config.getBoolean(path + ".Fly");
    }

    public static void saveLog(Player player, LogTypes logtypes, String message) {
		String logtype = "";
		switch (logtypes) {
		case WARNING: logtype = "Warning"; break;
		case REPORT: logtype = "Report"; break;
		case BAN: logtype = "Ban"; break;
		case KICK: logtype = "Kick"; break;
		default:break;}
		String times[] = new Timestamp(System.currentTimeMillis()).toString().split(":");
		String time = times[0] + ":" + times[1];
		List<String> list = config.getStringList(player.getUniqueId() + "." + logtype);
		list.add(time + " " + message);
		config.set(player.getUniqueId() + "." + logtype, list);
		saveConfig();
	}
	
	public static List<String> getLog(Player player, LogTypes logtypes) {
		String logtype = "";
		switch (logtypes) {
		case WARNING: logtype = "Warning"; break;
		case REPORT: logtype = "Report"; break;
		case BAN: logtype = "Ban"; break;
		case KICK: logtype = "Kick"; break;
		default:break;}
		return config.getStringList(player.getUniqueId() + "." + logtype);
	}
	
	public static List<String> getLatestLog(Player player, LogTypes logtypes) {
		String logtype = "";
		switch (logtypes) {
		case WARNING: logtype = "Warning"; break;
		case REPORT: logtype = "Report"; break;
		case BAN: logtype = "Ban"; break;
		case KICK: logtype = "Kick"; break;
		default:break;}
		List<String> list = config.getStringList(player.getUniqueId() + "." + logtype);
		if(list.size() > 20) {
			for (int i = 0; i < list.size() - 20; i++) {
				list.remove(i);
			}
		}
		return list;
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
