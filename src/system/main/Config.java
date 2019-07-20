package system.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	
	private static File file = new File("plugins/DZSystem", "config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public Config() {
		saveConfig();
	}
	
	public static void registerWorld(String world) {
		config.set("Worlds." + world + ".exists", true);
		config.set("Worlds." + world + ".protect", false);
		config.set("Worlds." + world + ".pvp", true);
		saveConfig();
	}
	
	public static boolean worldExists(String world) {
		return config.getBoolean("Worlds." + world + ".exists");
	}
	
	public static void registerPlayer(String player) {
		UUID playerUUID = Bukkit.getPlayer(player).getUniqueId();
		config.set("Player." + playerUUID + ".exists", true);
		config.set("Player." + playerUUID + ".name", player);
		config.set("Player." + playerUUID + ".role", "default");
		config.set("Player." + playerUUID + ".online", true);
		config.set("Player." + playerUUID + ".coins", 0);
		saveConfig();
	}
	
	public static boolean playerExists(String player) {
		UUID playerUUID = Bukkit.getPlayer(player).getUniqueId();
		return config.getBoolean("Player." + playerUUID + ".exists");
	}
	
	public static void setPlayerOnline(String player, boolean online) {
		UUID playerUUID = Bukkit.getPlayer(player).getUniqueId();
		config.set("Player." + playerUUID + ".online", online);
		saveConfig();
	}
	
	public static void protectWorld(String world, boolean protect) {
		config.set("Worlds." + world + ".protect", protect);
		saveConfig();
	}
	
	public static boolean isWorldProtected(String world) {
		return config.getBoolean("Worlds." + world + ".protect");
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
