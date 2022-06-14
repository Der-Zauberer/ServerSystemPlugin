package serversystem.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import serversystem.utilities.ChatUtil;

public class Config {
	
	private static final File file = new File("plugins/ServerSystem", "config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public enum ConfigOption{JOIN_MESSAGE, QUIT_MESSAGE, ENABLE_WORLD_GROUPS, ENABLE_PORTALS, GLOBAL_CHAT_AND_TABLIST, LOBBY}
	public enum TitleTypeOption{TITLE, SUBTITLE, TABLIST_TITLE, TABLIST_SUBTITLE}
	public enum WorldOption{DAMAGE, HUNGER, PVP, EXPLOSION, PROTECTION, WORLD_SPAWN, DEATH_MESSAGE}
	
	public Config() {
		setDefault("join_message", true);
		setDefault("leave_message", true);
		setDefault("enable_world_groups", false);
		setDefault("enable_portals", true);
		setDefault("global_chat_and_tablist", false);
		setDefault("lobby", false);
		setDefault("lobby_world", "world");
		setDefault("title.text", "");
		setDefault("title.color", "");
		setDefault("subtitle.text", "");
		setDefault("subtitle.color", "");
		setDefault("tablist.title.text", "");
		setDefault("tablist.title.color", "");
		setDefault("tablist.subtitle.text", "");
		setDefault("tablist.subtitle.color", "");
		setDefault("message.prefix", "[Server]");
		setDefault("message.prefix_color", "yellow");
		setDefault("message.color", "yellow");
		setDefault("message.error_color", "red");
		setDefault("disabled_permissions", "");
		setDefault("disabled_blocks", "");
		setDefault("load_worlds", "");
		setDefault("worlds", "");
		setDefault("groups", "");
		setDefault("players", "");
		for(World world : Bukkit.getWorlds()) {
			addWorld(world);
		}
		saveConfig();
	}
	
	private static void setDefault(String key, Object value) {
		if (config.get(key) == null) config.set(key, value);
	}
	
	public static ArrayList<String> getSection(String section, boolean keys) {
		try {
			final ArrayList<String> list = new ArrayList<>();
			for (String key : config.getConfigurationSection(section).getKeys(keys)) {
				list.add(key);
			}
			return list;
		} catch (NullPointerException exception) {
			return null;
		}
	}
	
	public static void setConfigOption(ConfigOption option, boolean value) {
		config.set(option.toString().toLowerCase(), value);
	}
	
	public static boolean getConfigOption(ConfigOption option) {
		return config.getBoolean(option.toString().toLowerCase());
	}
	
	public static World getLobbyWorld() {
		if(Bukkit.getWorld(config.getString("lobby_world")) != null) return Bukkit.getWorld(config.getString("lobby_world"));
		return null;
	}
	
	public static void setTitle(TitleTypeOption type, String text, String color) {
		config.set(type.toString().toLowerCase().replace('_', '.') + ".text", text);
		config.set(type.toString().toLowerCase().replace('_', '.') + ".color", color);
	}
	
	public static String getTitle(TitleTypeOption type) {
		final String text = config.getString(type.toString().toLowerCase().replace('_', '.') + ".text");
		if (text != null) {
			final ChatColor color = ChatUtil.parseColor(config.getString(type.toString().toLowerCase().replace('_', '.') + ".color"));
			return color + text;
		}
		return null;
	}
	
	public static String getMessagePrefix() {
		final String prefix = config.getString("message.prefix");
		final ChatColor color = ChatUtil.parseColor(config.getString("message.prefix_color"));
		if (prefix != null && !prefix.isEmpty()) return color + prefix; else return color + "server";
	}
	
	public static ChatColor getMessageColor() {
		return ChatUtil.parseColor(config.getString("message.color"));
	}
	
	public static ChatColor getErrorMessageColor() {
		return ChatUtil.parseColor(config.getString("message.error_color"));
	}
	
	public static List<String> getDisabledPermissions() {
		return config.getStringList("disabled_permissions");
	}
	
	public static List<String> getDisabledBlocks() {
		return config.getStringList("disabled_blocks");
	}
	
	public static void addLoadWorld(String world) {
		List<String> list = getLoadWorlds();
		list.add(world);
		config.set("load_worlds", list);
		saveConfig();
	}
	
	public static void removeLoadWorld(String string) {
		final List<String> list = getLoadWorlds();
		list.remove(string);
		config.set("load_worlds", list);
		saveConfig();
	}
	
	public static List<String> getLoadWorlds() {
		return config.getStringList("load_worlds");
	}
	
	public static void removeGroup(String group) {
		config.set("groups." + group, null);
		saveConfig();
	}
	
	public static String getGroupID(String group) {
		return config.getString("groups." + group + ".id");
	}
	
	public static ChatColor getGroupColor(String group) {
		return ChatUtil.parseColor(config.getString("groups." + group + ".color"));
	}
	
	public static String getGroupPrefix(String group) {
		final String prefix = config.getString("groups." + group + ".prefix");
		if (prefix != null) return prefix; else return "";
	}
	
	public static String getGroupParent(String group) {
		return config.getString("groups." + group + ".parent");
	}
	
	public static List<String> getGroupPermissions(String group) {
		final ArrayList<String> permissions = new ArrayList<>();
		final ArrayList<String> groups = new ArrayList<>();
		groups.add(group);
		while (getGroupParent(group) != null) {
			group = getGroupParent(group);
			groups.add(group);
		}
		for (int i = groups.size(); i > 0; i--) {
			permissions.addAll(config.getStringList("groups." + groups.get(i - 1) + ".permissions"));
		}
		return permissions;
	}
	
	public static List<String> getPlayerPermissions(Player player) {
		final List<String> list = new ArrayList<>();
		if (getGroupPermissions(getPlayerGroup(player)) != null) {
			list.addAll(getGroupPermissions(getPlayerGroup(player)));
		}
		if (getPlayerSpecificPermissions(player) != null) {
			list.addAll(getPlayerSpecificPermissions(player));
		}
		return list;
	}
	
	public static void addWorld(World world) {
		if(config.get("worlds." + world.getName()) == null) {
			config.set("worlds." + world.getName() + ".group", world.getName());
			config.set("worlds." + world.getName() + ".damage", true);
			config.set("worlds." + world.getName() + ".hunger", true);
			config.set("worlds." + world.getName() + ".pvp", world.getPVP());
			config.set("worlds." + world.getName() + ".explosion", true);
			config.set("worlds." + world.getName() + ".protection", false);
			config.set("worlds." + world.getName() + ".world_spawn", false);
			config.set("worlds." + world.getName() + ".death_message", true);
			config.set("worlds." + world.getName() + ".gamemode", 0);
			saveConfig();
		}
	}
	
	public static void setWorldGroup(World world, String worldGroup) {
		config.set("worlds." + world.getName() + ".group", worldGroup);
		saveConfig();
	}
	
	public static String getWorldGroup(World world) {
		return config.getString("worlds." + world.getName() + ".group");
	}
	
	public static void setWorldOption(World world, WorldOption option, boolean bool) {
		config.set("worlds." + world.getName() + "." + option.toString().toLowerCase(), bool);
	}
	
	public static boolean getWorldOption(World world, WorldOption option) {
		return config.getBoolean("worlds." + world.getName() + "." + option.toString().toLowerCase());
	}
	
	public static void setWorldGamemode(World world, GameMode gamemode) {
		switch (gamemode) {
		case SURVIVAL: config.set("worlds." + world.getName() + ".gamemode", 0); break;
		case CREATIVE: config.set("worlds." + world.getName() + ".gamemode", 1); break;
		case ADVENTURE: config.set("worlds." + world.getName() + ".gamemode", 2); break;
		case SPECTATOR: config.set("worlds." + world.getName() + ".gamemode", 3); break;
		default: break;
		}
		saveConfig();
	}
	
	public static GameMode getWorldGamemode(World world) {
		switch (config.getInt("worlds." + world + ".gamemode")) {
		case 0: return GameMode.SURVIVAL;
		case 1: return GameMode.CREATIVE;
		case 2: return GameMode.ADVENTURE;
		case 3: return GameMode.SPECTATOR;
		default: break;
		}
		return GameMode.ADVENTURE;
	}
	
	public static void setWorldPermission(World world, String permission) {
		config.set("worlds." + world.getName() + ".permission", permission);
		saveConfig();
	}
	
	public static void removeWorldPermission(World world) {
		config.set("worlds." + world.getName() + ".permission", null);
		saveConfig();
	}
	
	public static boolean hasWorldPermission(World world) {
		return config.getString("worlds." + world.getName() + ".permission") != null;
	}
	
	public static String getWorldPermission(World world) {
		return config.getString("worlds." + world.getName() + ".permission");
	}
	
	public static void addPlayer(Player player) {
		if (config.get("players." + player.getUniqueId()) == null) {
			config.set("players." + player.getUniqueId() + ".name", player.getName());
			config.set("players." + player.getUniqueId() + ".group", "player");
			saveConfig();
		} else if (!config.getString("players." + player.getUniqueId() + ".name").equals(player.getName())) {
			config.set("players." + player.getUniqueId() + ".name", player.getName());
			saveConfig();
		}
	}
	
	public static void setPlayerGroup(Player player, String group) {
		config.set("players." + player.getUniqueId() + ".group", group);
		saveConfig();
	}
	
	public static void setPlayerGroup(String player, String group) {
		for (String key : getSection("players", false)) {
			if (config.getString("players." + key + ".name").equals(player)) {
				config.set("players." + key + ".group", group);
				saveConfig();
				return;
			}
		}
	}
	
	public static String getPlayerGroup(Player player) {
		return config.getString("players." + player.getUniqueId() + ".group");
	}
	
	public static String getPlayerGroup(String player) {
		for (String key : getSection("players", false)) {
			if (config.getString("players." + key + ".name").equals(player)) {
				return config.getString("players." + key + ".group");
			}
		}
		return null;
	}
	
	private static List<String> getPlayerSpecificPermissions(Player player) {
		return config.getStringList("players." + player.getUniqueId() + ".permissions");
	}
	
	public static boolean isPlayerExisting(String player) {
		for (String key : getSection("players", false)) {
			if (config.getString("players." + key + ".name").equals(player)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<String> getPlayers() {
		final List<String> players = new ArrayList<>();
		for (String key : getSection("players", false)) {
			players.add(config.getString("players." + key + ".name"));
		}
		return players;
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException exception) {
			Bukkit.getLogger().warning("Something went wrong while saving config.yml!");
		}
	}
	
	public static void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}

}
