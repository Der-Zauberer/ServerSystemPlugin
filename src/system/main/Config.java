package system.main;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Config {
	
	private static File file = new File("plugins/System", "config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public Config() {
		if(!file.exists()) {
			config.set("Server.joinmessage", true);
			config.set("Server.leavemessage", true);
			config.set("Server.defaultgamemode", false);
			config.set("Server.gamemode", 0);
			config.set("Server.achivements", true);
			config.set("Lobby.exist", false);
		}
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
	
	public static boolean isJoinMessageActiv() {
		return config.getBoolean("Server.joinmessage");
	}
	
	public static boolean isLeaveMessageActiv() {
		return config.getBoolean("Server.leavemessage");
	}
	
	public static boolean hasDefaultGamemode() {
		return config.getBoolean("Server.defaultgamemode");
	}
	
	public static GameMode getDefaultGamemode() {
		switch(config.getInt("Server.gamemode"))  {
		case 0: return GameMode.SURVIVAL;
		case 1: return GameMode.CREATIVE;
		case 2: return GameMode.ADVENTURE;
		case 3: return GameMode.SPECTATOR;
		}
		return GameMode.SURVIVAL;
	}
	
	public static void setAchivements(boolean achivements) {
		config.set("Server.achivements", achivements);
	}
	
	public static boolean hasAchivements() {
		return config.getBoolean("Server.achivements");
	}
	
	public static void setLobby(Player player) {
		setLobby(player.getLocation());
	}
	
	public static void setLobby(Location location) {
		config.set("Lobby.exist", true);
		config.set("Lobby.World", location.getWorld().getName());
		config.set("Lobby.X", location.getX());
		config.set("Lobby.Y", location.getY());
		config.set("Lobby.Z", location.getZ());
		config.set("Lobby.Pitch", location.getPitch());
		config.set("Lobby.Yaw", location.getYaw());
		saveConfig();
	}
	
	public static Location getLobby() {
		Location location = new Location(Bukkit.getWorld(config.getString("Lobby.World")), config.getDouble("Lobby.X"), config.getDouble("Lobby.Y"), config.getDouble("Lobby.Z"), (float) config.getDouble("Lobby.Pitch"), (float) config.getDouble("Lobby.Yaw"));
		return location;
	}
	
	public static boolean lobbyExists() {
		return config.getBoolean("Lobby.exist");
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
