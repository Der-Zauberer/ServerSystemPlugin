package system.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	
	private static File file = new File("plugins/System", "config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public Config() {
		saveConfig();
	}
	
	public static void registerWorld(String world) {
		config.set("Worlds." + world + ".exists", true);
		config.set("Worlds." + world + ".protect", false);
		config.set("Worlds." + world + ".pvp", true);
		config.set("Worlds." + world + ".lobby", false);
		saveConfig();
	}
	
	public static boolean worldExists(String world) {
		return config.getBoolean("Worlds." + world + ".exists");
	}
	
	public static void setWorldProtected(String world, boolean protect) {
		config.set("Worlds." + world + ".protect", protect);
		saveConfig();
	}
	
	public static boolean isWorldProtected(String world) {
		return config.getBoolean("Worlds." + world + ".protect");
	}
	
	public static void setWorldPVP(String world, boolean pvp) {
		Bukkit.getWorld(world).setPVP(pvp);
		config.set("Worlds." + world + ".pvp", pvp);
		saveConfig();
	}
	
	public static boolean isWorldPVP(String world) {
		return config.getBoolean("Worlds." + world + ".pvp");
	}
	
	public static void setWorldLobby(String world, boolean lobby) {
		Bukkit.getWorld(world).setPVP(lobby);
		config.set("Worlds." + world + ".lobby", lobby);
		saveConfig();
	}
	
	public static boolean isWorldLobby(String world) {
		return config.getBoolean("Worlds." + world + ".lobby");
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
