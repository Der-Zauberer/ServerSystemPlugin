package serversystem.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.ServerGroup;

public class Config {
	
	private static final File file = new File("plugins/ServerSystem", "config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public enum ConfigOption{JOIN_MESSAGE, QUIT_MESSAGE, ENABLE_PORTALS, ENABLE_WORLD_GROUPS, GLOBAL_CHAT_AND_TABLIST, GLOBAL_INVENTORY, LOBBY}
	public enum TitleTypeOption{TITLE, SUBTITLE, TABLIST_TITLE, TABLIST_SUBTITLE}
	public enum WorldOption{DAMAGE, HUNGER, PVP, EXPLOSION, PROTECTION, WORLD_SPAWN, DEATH_MESSAGE}
	
	static {
		setDefault("join_message", true);
		setDefault("leave_message", true);
		setDefault("enable_portals", true);
		setDefault("enable_world_groups", false);
		setDefault("global_chat_and_tablist", false);
		setDefault("global_inventory", false);
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
	
	private static ArrayList<String> getSection(String section, boolean keys) {
		final ArrayList<String> list = new ArrayList<>();
		try {
			for (String key : config.getConfigurationSection(section).getKeys(keys)) {
				list.add(key);
			}
		} catch (NullPointerException exception) {}
		return list;
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
	
	public static void removeLoadWorld(String world) {
		final List<String> list = getLoadWorlds();
		list.remove(world);
		config.set("load_worlds", list);
		config.set("worlds." + world, null);
		SaveConfig.removeWorld(world);
		saveConfig();
	}
	
	public static List<String> getLoadWorlds() {
		return config.getStringList("load_worlds");
	}
	
	public static void saveGroup(ServerGroup group) {
		final String path = "groups." + group.getName() + ".";
		config.set(path + "id", group.getId());
		config.set(path + "color", group.getColor().name().toLowerCase());
		config.set(path + "prefix", group.getPrefix() != null ? group.getPrefix() : "");
		if (group.hasParent()) config.set(path + "parent", group.getParent().getName());
		else config.set(path + "parent", null);
		if (!group.getPermissions().isEmpty()) config.set(path + "permissions", group.getPermissions());
		saveConfig();
	}
	
	public static ServerGroup loadGroup(String group) {
		final String path = "groups." + group + ".";
		final String id = config.getString(path + "id");
		if (id != null) {
			final ChatColor color = ChatUtil.parseColor(config.getString(path + "color"));
			final String prefix = config.getString(path + "prefix");
			final List<String> permissions = config.getStringList(path + "permissions");
			final ServerGroup serverGroup = new ServerGroup(id, group, color, prefix, permissions != null ? permissions : new ArrayList<>(), false);
			return serverGroup;
		}
		return null;
	}
	
	public static List<ServerGroup> loadGroups() {
		final List<ServerGroup> groups = new ArrayList<>();
		final HashMap<ServerGroup, String> parents = new HashMap<>();
		for (String groupName : Config.getSection("groups", false)) {
			final ServerGroup group = Config.loadGroup(groupName);
			if (group != null) {
				groups.add(group);
				String parent;
				if (group.getParent() == null && (parent = config.getString("groups." + groupName + ".parent")) != null && !parent.isEmpty()) {
					parents.put(group, parent);
				}
			}
		}
		for (ServerGroup serverGroup : parents.keySet()) {
			for (ServerGroup parent : groups) {
				if (parent.getName().equals(parents.get(serverGroup))) {
					serverGroup.setParent(parent);
					break;
				}
			}
		}
		groups.forEach(group -> group.update());
		return groups;
	}
	
	public static void addWorld(World world) {
		if(config.get("worlds." + world.getName()) == null) {
			config.set("worlds." + world.getName() + ".group", world.getName());
			config.set("worlds." + world.getName() + ".permission", "");
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
	
	public static void setWorldPermission(World world, String permission) {
		if (permission == null || permission.equalsIgnoreCase("null")) config.set("worlds." + world.getName() + ".permission", "");
		else config.set("worlds." + world.getName() + ".permission", permission);
		saveConfig();
	}
	
	public static String getWorldPermission(World world) {
		final String permission = config.getString("worlds." + world.getName() + ".permission");
		if (permission != null && permission.isEmpty()) return null;
		return permission;
	}
	
	public static void setWorldOption(World world, WorldOption option, boolean bool) {
		config.set("worlds." + world.getName() + "." + option.toString().toLowerCase(), bool);
		if (option == WorldOption.PVP) world.setPVP(bool);
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
	
	public static List<String> getPlayerSpecificPermissions(Player player) {
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
			Bukkit.getLogger().warning("Something went wrong while saving plugins/ServerSystem/config.yml!");
		}
	}
	
	public static void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}

}
