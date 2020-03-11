package serversystem.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
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
			config.set("Server.title.text", "");
			config.set("Server.title.color", "");
			config.set("Server.subtitle.color", "");
			config.set("Server.title.text", "");
			config.set("Server.lobby", false);
			config.set("Server.lobbyworld", "world");
			config.set("DisabledPermissions", "");
			config.set("Worldload", "");
			config.set("Groups.player", "");
			saveConfig();
		}
		for(World world : Bukkit.getWorlds()) {
			addWorld(world.getName());
			Bukkit.getWorld(world.getName()).setPVP(hasWorldPVP(world.getName()));
		}
	}
	
	public static ArrayList<String> getSection(String section) {
		ArrayList<String> list = new ArrayList<>();
		for (String key : config.getConfigurationSection(section).getKeys(true)) {
			list.add(key);
		}
		return list;
	}
	
	public static void addPlayer(Player player) {
		if(!config.getBoolean("Players." + player.getUniqueId() + ".exists")) {
			config.set("Players." + player.getUniqueId() + ".name", player.getName());
			config.set("Players." + player.getUniqueId() + ".exists", true);
			config.set("Players." + player.getUniqueId() + ".group", "player");
			saveConfig();
		}
	}
	
	public static void setPlayerGroup(Player player, String group) {
		config.set("Players." + player.getUniqueId() + ".group", group);
		saveConfig();
	}
	
	public static String getPlayerGroup(Player player) {
		return config.getString("Players." + player.getUniqueId() + ".group");
	}
	
	public static void addGroup(String group) {
		config.set("Groups." + group, "");
		saveConfig();
	}
	
	public static void removeGroup(String group) {
		config.set("Groups." + group, null);
		saveConfig();
	}
	
	public static void addGroupPermission(String group, String permission) {
		List<String> list = getGroupPermissions(group);
		list.add(permission);
		config.set("Groups." + group, list);
		saveConfig();
	}
	
	public static List<String> getGroupPermissions(String group) {
		return config.getStringList("Groups." + group);
	}
	
	public static List<String> getPlayerPermissions(Player player) {
		return getGroupPermissions(getPlayerGroup(player));
	}
	
	public static void addDisabledPermission(String permission) {
		List<String> list = getDisabledPermission();
		list.add(permission);
		config.set("DisabledPermissions", list);
		saveConfig();
	}
	
	public static void removeDisabledPermission(String permission) {
		List<String> list = getDisabledPermission();
		list.remove(permission);
		config.set("DisabledPermissions", list);
		saveConfig();
	}
	
	public static List<String> getDisabledPermission() {
		return config.getStringList("DisabledPermissions");
	}
	
	public static void addWorld(String world) {
		if(!config.getBoolean("Worlds." + world + ".exists")) {
			config.set("Worlds." + world + ".exists", true);
			config.set("Worlds." + world + ".group", world);
			config.set("Worlds." + world + ".worldspawn", false);
			config.set("Worlds." + world + ".protect", false);
			config.set("Worlds." + world + ".pvp", true);
			config.set("Worlds." + world + ".damage", true);
			config.set("Worlds." + world + ".huger", true);
			config.set("Worlds." + world + ".explosion", true);
			config.set("Worlds." + world + ".gamemode", 2);
			saveConfig();
		}
	}
	
	public static void setWorldGroup(String world, String group) {
		config.set("Worlds." + world + ".group", group);
		saveConfig();
	}
	
	public static String getWorldGroup(String world) {
		return config.getString("Worlds." + world + ".group");
	}
	
	public static boolean hasWorldSpawn(String world) {
		return config.getBoolean("Worlds." + world + ".worldspawn");
	}
	
	public static void setWorldProtection(String world, boolean protect) {
		config.set("Worlds." + world + ".protect", protect);
		saveConfig();
	}
	
	public static boolean hasWorldProtection(String world) {
		return config.getBoolean("Worlds." + world + ".protect");
	}
	
	public static void setWorldPVP(String world, boolean pvp) {
		Bukkit.getWorld(world).setPVP(pvp);
		config.set("Worlds." + world + ".pvp", pvp);
		saveConfig();
	}
	
	public static boolean hasWorldPVP(String world) {
		return config.getBoolean("Worlds." + world + ".pvp");
	}
	
	public static void setWorldDamage(String world, boolean damage) {
		config.set("Worlds." + world + ".damage", damage);
		saveConfig();
	}
	
	public static boolean hasWorldDamage(String world) {
		return config.getBoolean("Worlds." + world + ".damage");
	}
	
	public static void setWorldHunger(String world, boolean hunger) {
		config.set("Worlds." + world + ".hunger", hunger);
		saveConfig();
	}
	
	public static boolean hasWorldHunger(String world) {
		return config.getBoolean("Worlds." + world + ".hunger");
	}
	
	public static void setWorldExplosion(String world, boolean explosion) {
		config.set("Worlds." + world + ".explosion", explosion);
		saveConfig();
	}
	
	public static boolean hasWorldExplosion(String world) {
		return config.getBoolean("Worlds." + world + ".explosion");
	}
	
	public static void setWorldGamemode(String world, GameMode gamemode) {
		switch (gamemode) {
		case SURVIVAL: config.set("Worlds." + world + ".gamemode", 0); break;
		case CREATIVE: config.set("Worlds." + world + ".gamemode", 1); break;
		case ADVENTURE: config.set("Worlds." + world + ".gamemode", 2); break;
		case SPECTATOR: config.set("Worlds." + world + ".gamemode", 3); break;
		default: break;
		}
		saveConfig();
	}
	
	public static GameMode getWorldGamemode(String world) {
		switch (config.getInt("Worlds." + world + ".gamemode")) {
		case 0: return GameMode.SURVIVAL;
		case 1: return GameMode.CREATIVE;
		case 2: return GameMode.ADVENTURE;
		case 3: return GameMode.SPECTATOR;
		}
		return GameMode.ADVENTURE;
	}
	
	public static void setJoinMessageActive(boolean joinmessage) {
		config.set("Server.joinmessage", joinmessage);
	}
	
	public static boolean isJoinMessageActiv() {
		return config.getBoolean("Server.joinmessage");
	}
	
	public static void setLeaveMessageActive(boolean leavemessage) {
		config.set("Server.leavemessage", leavemessage);
	}
	
	public static boolean isLeaveMessageActiv() {
		return config.getBoolean("Server.leavemessage");
	}
	
	public static void addLoadWorld(String world) {
		List<String> list = getLoadWorlds();
		list.add(world);
		config.set("Worldload", list);
		saveConfig();
	}
	
	public static void removeLoadWorld(String string) {
		List<String> list = getLoadWorlds();
		list.remove(string);
		config.set("Worldload", list);
		saveConfig();
	}
	
	public static List<String> getLoadWorlds() {
		return config.getStringList("Worldload");
	}
	
	public static void getTitle(String title, String color) {
		config.set("Server.title.text", title);
		config.set("Server.title.color", color);
		saveConfig();
	}

	public static String getTitle() {
		return config.getString("Server.title.text");
	}
	
	public static String getTitleColor() {
		return config.getString("Server.title.color");
	}
	
	public static void setSubtitle(String title, String color) {
		config.set("Server.title.text", title);
		config.set("Server.title.color", color);
		saveConfig();
	}

	public static String getSubtitle() {
		return config.getString("Server.subtitle.text");
	}
	
	public static String getSubtitleColor() {
		return config.getString("Server.subtitle.color");
	}
	
	public static boolean lobbyExists() {
		return config.getBoolean("Server.lobby");
	}
	
	public static World getLobbyWorld() {
		if(Bukkit.getWorld(config.getString("Server.lobbyworld")) != null) {
			return Bukkit.getWorld(config.getString("Server.lobbyworld"));
		}
		return null;
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
