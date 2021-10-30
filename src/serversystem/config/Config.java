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
	
	private static File file = new File("plugins/ServerSystem", "config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public enum WorldOption{DAMAGE, PVP, PROTECTION, EXPLOSION, HUNGER, DEATHMESSAGE};
	
	public Config() {
		if(config.get("Server.joinmessage") == null) {
			config.set("Server.joinmessage", true);
		}
		if(config.get("Server.leavemessage") == null) {
			config.set("Server.leavemessage", true);
		}
		if(config.get("Server.title.text") == null) {
			config.set("Server.title.text", "");
		}
		if(config.get("Server.title.color") == null) {
			config.set("Server.title.color", "");
		}
		if(config.get("Server.subtitle.text") == null) {
			config.set("Server.subtitle.text", "");
		}
		if(config.get("Server.subtitle.color") == null) {
			config.set("Server.subtitle.color", "");
		}
		if(config.get("Server.tablist.title.text") == null) {
			config.set("Server.tablist.title.text", "");
		}
		if(config.get("Server.tablist.title.color") == null) {
			config.set("Server.tablist.title.color", "");
		}
		if(config.get("Server.tablist.subtitle.text") == null) {
			config.set("Server.tablist.subtitle.text", "");
		}
		if(config.get("Server.tablist.subtitle.color") == null) {
			config.set("Server.tablist.subtitle.color", "");
		}
		if(config.get("Server.message.prefix") == null) {
			config.set("Server.message.prefix", "[Server]");
		}
		if(config.get("Server.message.prefixcolor") == null) {
			config.set("Server.message.prefixcolor", "yellow");
		}
		if(config.get("Server.message.color") == null) {
			config.set("Server.message.color", "yellow");
		}
		if(config.get("Server.message.errorcolor") == null) {
			config.set("Server.message.errorcolor", "red");
		}
		if(config.get("Server.lobby") == null) {
			config.set("Server.lobby", false);
		}
		if(config.get("Server.lobbyworld") == null) {
			config.set("Server.lobbyworld", "world");
		}
		if(config.get("Server.enableworldgroups") == null) {
			config.set("Server.enableworldgroups", true);
		}
		if(config.get("Server.enableportals") == null) {
			config.set("Server.enableportals", true);
		}
		if(config.get("DisabledPermissions") == null) {
			config.set("DisabledPermissions", "");
		}
		if(config.get("DisabledBlocks") == null) {
			config.set("DisabledBlocks", "");
		}
		if(config.get("Worldload") == null) {
			config.set("Worldload", "");
		}
		if(config.get("Groups") == null) {
			config.set("Groups", "");
		}
		for(World world : Bukkit.getWorlds()) {
			addWorld(world.getName());
			Bukkit.getWorld(world.getName()).setPVP(hasWorldPVP(world.getName()));
		}
		saveConfig();
	}
	
	public static ArrayList<String> getSection(String section, boolean keys) {
		try {
			ArrayList<String> list = new ArrayList<>();
			for (String key : config.getConfigurationSection(section).getKeys(keys)) {
				list.add(key);
			}
			return list;
		} catch (NullPointerException exception) {
			return null;
		}
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
	
	public static void setTablistTitle(String title, String color) {
		config.set("Server.tablist.title.text", title);
		config.set("Server.tablist.title.color", color);
		saveConfig();
	}

	public static String getTablistTitle() {
		return config.getString("Server.tablist.title.text");
	}
	
	public static String getTablistTitleColor() {
		return config.getString("Server.tablist.title.color");
	}
	
	public static void setTablistSubtitle(String title, String color) {
		config.set("Server.tablist.title.text", title);
		config.set("Server.tablist.title.color", color);
		saveConfig();
	}

	public static String getTablistSubtitle() {
		return config.getString("Server.tablist.subtitle.text");
	}
	
	public static String getTablistSubtitleColor() {
		return config.getString("Server.tablist.subtitle.color");
	}
	
	public static String getMessagePrefix() {
		return config.getString("Server.message.prefix");
	}
	
	public static String getMessagePrefixColor() {
		return config.getString("Server.message.prefixcolor");
	}
	
	public static String getMessageColor() {
		return config.getString("Server.message.color");
	}
	
	public static String getErrorMessageColor() {
		return config.getString("Server.message.errorcolor");
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
	
	public static boolean isWorldGroupSystemEnabled() {
		return config.getBoolean("Server.enableworldgroups");
	}
	
	public static boolean arePortalsEnabled() {
		return config.getBoolean("Server.enableportals");
	}
	
	public static List<String> getDisabledPermissions() {
		return config.getStringList("DisabledPermissions");
	}
	
	public static List<String> getDisabledBlocks() {
		return config.getStringList("DisabledBlocks");
	}
	
	public static void addToLoadWorld(String world) {
		List<String> list = getLoadWorlds();
		list.add(world);
		config.set("Worldload", list);
		saveConfig();
	}
	
	public static void removeFromLoadWorld(String string) {
		List<String> list = getLoadWorlds();
		list.remove(string);
		config.set("Worldload", list);
		saveConfig();
	}
	
	public static List<String> getLoadWorlds() {
		return config.getStringList("Worldload");
	}
	
	public static void removeGroup(String group) {
		config.set("Groups." + group, null);
		saveConfig();
	}
	
	public static String getGroupID(String group) {
		return config.getString("Groups." + group + ".id");
	}
	
	public static String getGroupColor(String group) {
		return config.getString("Groups." + group + ".color");
	}
	
	public static String getGroupPrefix(String group) {
		return config.getString("Groups." + group + ".prefix");
	}
	
	public static String getGroupParent(String group) {
		return config.getString("Groups." + group + ".parent");
	}
	
	public static List<String> getGroupPermissions(String group) {
		ArrayList<String> permissions = new ArrayList<>();
		ArrayList<String> groups = new ArrayList<>();
		groups.add(group);
		while (getGroupParent(group) != null) {
			group = getGroupParent(group);
			groups.add(group);
		}
		for(int i = groups.size(); i > 0; i--) {
			permissions.addAll(config.getStringList("Groups." + groups.get(i - 1) + ".permissions"));
		}
		return permissions;
	}
	
	public static List<String> getPlayerPermissions(Player player) {
		List<String> list = new ArrayList<>();
		if(getGroupPermissions(getPlayerGroup(player)) != null) {
			list.addAll(getGroupPermissions(getPlayerGroup(player)));
		}
		if(getPlayerSpecificPermissions(player) != null) {
			list.addAll(getPlayerSpecificPermissions(player));
		}
		return list;
	}
	
	public static void addWorld(String world) {
		if(config.get("Worlds." + world) == null) {
			config.set("Worlds." + world + ".group", world);
			config.set("Worlds." + world + ".worldspawn", false);
			config.set("Worlds." + world + ".protect", false);
			config.set("Worlds." + world + ".pvp", true);
			config.set("Worlds." + world + ".damage", true);
			config.set("Worlds." + world + ".huger", true);
			config.set("Worlds." + world + ".explosion", true);
			config.set("Worlds." + world + ".deathmessage", true);
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
	
	public static void setWorldDeathMessage(String world, boolean deathmessage) {
		config.set("Worlds." + world + ".deathmessage", deathmessage);
		saveConfig();
	}
	
	public static boolean hasWorldDeathMessage(String world) {
		return config.getBoolean("Worlds." + world + ".deathmessage");
	}
	
	public static void setWorldOption(String world, WorldOption option, boolean bool) {
		switch (option) {
		case DAMAGE: setWorldDamage(world, bool); break;
		case PVP: setWorldPVP(world, bool); break;
		case PROTECTION: setWorldProtection(world, bool); break;
		case EXPLOSION: setWorldExplosion(world, bool); break;
		case HUNGER: setWorldHunger(world, bool); break;
		case DEATHMESSAGE: setWorldDeathMessage(world, bool); break;
		default:break;
		}
	}
	
	public static boolean getWorldOption(String world, WorldOption option) {
		switch (option) {
		case DAMAGE: return hasWorldDamage(world);
		case PVP: return hasWorldPVP(world);
		case PROTECTION: return hasWorldProtection(world);
		case EXPLOSION: return hasWorldExplosion(world);
		case HUNGER: return hasWorldHunger(world);
		case DEATHMESSAGE: return hasWorldDeathMessage(world);
		default:break;
		}
		return false;
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
	
	public static void setWorldPermission(String world, String permission) {
		config.set("Worlds." + world + ".permission", permission);
		saveConfig();
	}
	
	public static void removeWorldPermission(String world) {
		config.set("Worlds." + world + ".permission", null);
		saveConfig();
	}
	
	public static boolean hasWorldPermission(String world) {
		if(config.getString("Worlds." + world + ".permission") != null) {
			return true;
		}
		return false;
	}
	
	public static String getWorldPermission(String world) {
		return config.getString("Worlds." + world + ".permission");
	}
	
	public static void addPlayer(Player player) {
		if(config.get("Players." + player.getUniqueId()) == null) {
			config.set("Players." + player.getUniqueId() + ".name", player.getName());
			config.set("Players." + player.getUniqueId() + ".group", "player");
			saveConfig();
		} else if(!config.getString("Players." + player.getUniqueId() + ".name").equals(player.getName())) {
			config.set("Players." + player.getUniqueId() + ".name", player.getName());
			saveConfig();
		}
	}
	
	public static void setPlayerGroup(Player player, String group) {
		config.set("Players." + player.getUniqueId() + ".group", group);
		saveConfig();
	}
	
	public static void setPlayerGroup(String player, String group) {
		for(String key : getSection("Players", false)) {
			if(config.getString("Players." + key + ".name").equals(player)) {
				config.set("Players." + key + ".group", group);
				saveConfig();
				return;
			}
		}
	}
	
	public static String getPlayerGroup(Player player) {
		return config.getString("Players." + player.getUniqueId() + ".group");
	}
	
	public static String getPlayerGroup(String player) {
		for(String key : getSection("Players", false)) {
			if(config.getString("Players." + key + ".name").equals(player)) {
				return config.getString("Players." + key + ".group");
			}
		}
		return null;
	}
	
	private static List<String> getPlayerSpecificPermissions(Player player) {
		return config.getStringList("Players." + player.getUniqueId() + ".permissions");
	}
	
	public static boolean isPlayerExisting(String player) {
		for(String key : getSection("Players", false)) {
			if(config.getString("Players." + key + ".name").equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<String> getPlayers() {
		List<String> players = new ArrayList<>();
		for(String key : getSection("Players", false)) {
			players.add(config.getString("Players." + key + ".name"));
		}
		return players;
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}

}
