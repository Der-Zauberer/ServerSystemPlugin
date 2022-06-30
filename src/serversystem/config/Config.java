package serversystem.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.ServerGroup;
import serversystem.utilities.ServerList;
import serversystem.utilities.ServerWarp;

public class Config {
	
	private static final File file = new File("plugins/ServerSystem", "config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public enum ConfigOption{JOIN_MESSAGE, QUIT_MESSAGE, ENABLE_PORTALS, ENABLE_WORLD_GROUPS, GLOBAL_CHAT_AND_TABLIST, GLOBAL_INVENTORY, LOBBY}
	public enum TitleTypeOption{TITLE, SUBTITLE, TABLIST_TITLE, TABLIST_SUBTITLE}
	public enum WorldOption{DAMAGE, HUNGER, PVP, EXPLOSION, PROTECTION, WORLD_SPAWN, DEATH_MESSAGE}
	
	static {
		loadConfig();
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
		setDefault("disabled_permissions", new ArrayList<String>());
		setDefault("disabled_blocks", new ArrayList<String>());
		setDefault("worlds", "");
		setDefault("groups", "");
		setDefault("warps", "");
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
		if (Bukkit.getWorld(config.getString("lobby_world")) != null) return Bukkit.getWorld(config.getString("lobby_world"));
		return null;
	}
	
	public static void setTitle(TitleTypeOption type, String text, String color) {
		config.set(type.toString().toLowerCase().replace('_', '.') + ".text", text);
		config.set(type.toString().toLowerCase().replace('_', '.') + ".color", color);
	}
	
	public static String getTitle(TitleTypeOption type) {
		final String text = config.getString(type.toString().toLowerCase().replace('_', '.') + ".text");
		if (text != null) {
			final ChatColor color = ChatUtil.getEnumValue(config.getString(type.name().toLowerCase().replace('_', '.') + ".color"), ChatColor.values(), ChatColor.WHITE);
			return color + text;
		}
		return null;
	}
	
	public static String getMessagePrefix() {
		final String prefix = config.getString("message.prefix");
		final ChatColor color = ChatUtil.getEnumValue(config.getString("message.prefix_color"), ChatColor.values(), ChatColor.WHITE);
		if (prefix != null && !prefix.isEmpty()) return color + prefix; else return color + "server";
	}
	
	public static ChatColor getMessageColor() {
		return ChatUtil.getEnumValue(config.getString("message.color"), ChatColor.values(), ChatColor.WHITE);
	}
	
	public static ChatColor getErrorColor() {
		return ChatUtil.getEnumValue(config.getString("message.error_color"), ChatColor.values(), ChatColor.WHITE);
	}
	
	public static List<String> getDisabledPermissions() {
		return config.getStringList("disabled_permissions");
	}
	
	public static List<String> getDisabledBlocks() {
		return config.getStringList("disabled_blocks");
	}
	
	public static void addWorld(World world) {
		if (config.get("worlds." + world.getName()) == null) {
			config.set("worlds." + world.getName() + ".group", world.getName());
			config.set("worlds." + world.getName() + ".permission", "");
			config.set("worlds." + world.getName() + ".gamemode", 2);
			config.set("worlds." + world.getName() + ".damage", true);
			config.set("worlds." + world.getName() + ".hunger", true);
			config.set("worlds." + world.getName() + ".pvp", world.getPVP());
			config.set("worlds." + world.getName() + ".explosion", true);
			config.set("worlds." + world.getName() + ".protection", false);
			config.set("worlds." + world.getName() + ".world_spawn", false);
			config.set("worlds." + world.getName() + ".death_message", true);
			saveConfig();
		}
	}
	
	public static void removeWorld(World world) {
		config.set("worlds." + world.getName(), null);
	}
	
	public static void setWorldGroup(World world, String worldGroup) {
		if (worldGroup != null && !worldGroup.isEmpty()) config.set("worlds." + world.getName() + ".group", worldGroup);
		else config.set("worlds." + world.getName() + ".group", world.getName());
		saveConfig();
	}
	
	public static String getWorldGroup(World world) {
		return config.getString("worlds." + world.getName() + ".group");
	}
	
	public static void setWorldPermission(World world, String permission) {
		if (permission == null) config.set("worlds." + world.getName() + ".permission", "");
		else config.set("worlds." + world.getName() + ".permission", permission);
		saveConfig();
	}
	
	public static String getWorldPermission(World world) {
		final String permission = config.getString("worlds." + world.getName() + ".permission");
		if (permission != null && !permission.isEmpty()) return permission;
		return null;
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
	
	public static void setWorldOption(World world, WorldOption option, boolean bool) {
		config.set("worlds." + world.getName() + "." + option.toString().toLowerCase(), bool);
		if (option == WorldOption.PVP) world.setPVP(bool);
	}
	
	public static boolean getWorldOption(World world, WorldOption option) {
		return config.getBoolean("worlds." + world.getName() + "." + option.toString().toLowerCase());
	}
	
	public static List<String> getWorlds() {
		final List<String> worlds = new ArrayList<>();
		getSection("worlds", false).forEach(worlds::add);
		return worlds;
	}
	
	public static void saveGroup(ServerGroup group) {
		final String path = "groups." + group.getName() + ".";
		config.set(path + "priority", ChatUtil.getValue(group.getPriority(), 99, 1, 99));
		config.set(path + "color", group.getColor().name().toLowerCase());
		config.set(path + "prefix", ChatUtil.getValue(group.getPrefix(), ""));
		config.set(path + "parent", group.getParent() != null ? group.getParent().getName() : "");
		config.set(path + "permissions", group.getPermissions());
		saveConfig();
	}
	
	public static void removeGroup(ServerGroup group) {
		config.set("groups." + group.getName(), null);
		saveConfig();
	}
	
	public static ServerGroup loadGroup(String group) {
		final String path = "groups." + group + ".";
		if (config.get("groups." + group) != null) {
			final int priority = ChatUtil.getValue(config.getInt(path + "priority"), 99, 0, 99);
			final ChatColor color = ChatUtil.getEnumValue(config.getString(path + "color"), ChatColor.values(), ChatColor.WHITE);
			final String prefix = config.getString(path + "prefix");
			final List<String> permissions = ChatUtil.getValue(config.getStringList(path + "permissions"), new ArrayList<>());
			final ServerGroup serverGroup = new ServerGroup(group, priority, color, prefix, permissions);
			return serverGroup;
		}
		return null;
	}
	
	public static void loadGroups(ServerList<ServerGroup> groups) {
		groups.clear();
		final ArrayList<ServerGroup> groupList = new ArrayList<>();
		final HashMap<ServerGroup, String> parents = new HashMap<>();
		if (Config.getSection("groups", false) != null) Config.getSection("groups", false).stream().map(Config::loadGroup).filter(group -> group != null).forEach(group -> {
			groupList.add(group);
			String parent;
			if (group.getParent() == null && (parent = config.getString("groups." + group.getName() + ".parent")) != null && !parent.isEmpty() && !group.getName().equals(parent)) {
				parents.put(group, parent);
			}		
		});
		for (ServerGroup serverGroup : parents.keySet()) {
			groupList.stream().filter(parent -> parent.getName().equals(parents.get(serverGroup))).findFirst().ifPresent(serverGroup::setParent);
		}
		groupList.forEach(groups::add);
	}
	
	public static void saveWarp(ServerWarp warp) {
		final String path = "warps." + warp.getName() + ".";
		config.set(path + "location", warp.getLocation());
		config.set(path + "material", warp.getMaterial().name().toLowerCase());
		config.set(path + "global", warp.isGlobal());
		config.set(path + "permission", ChatUtil.getValue(warp.getPermission(), ""));
		saveConfig();
	}

	public static void removeWarp(ServerWarp warp) {
		config.set("warps." + warp.getName(), null);
		saveConfig();
	}

	public static ServerWarp loadWarp(String warp) {
		final String path = "warps." + warp + ".";
		if (config.get("warps." + warp) != null && config.getLocation(path + "location") != null) {
			ServerWarp serverWarp = new ServerWarp(warp, config.getLocation(path + "location"));
			serverWarp.setMaterial(ChatUtil.getEnumValue(config.getString(path + "material"), Material.values(), Material.ENDER_PEARL));
			final String global = config.getString(path + "global");
			serverWarp.setGlobal(global != null && global.equals("false") ? false : true);
			final String permission = config.getString(path + "prefix");
			serverWarp.setPermission(permission != null && !permission.isEmpty() ? permission : null);
			return serverWarp;
		}
		return null;
		
	}

	public static void loadWarps(ServerList<ServerWarp> warps) {
		if (Config.getSection("warps", false) != null) Config.getSection("warps", false).stream().map(Config::loadWarp).filter(warp -> warp != null).forEach(warps::add);
	}
	
	public static void addPlayer(Player player) {
		if (config.get("players." + player.getUniqueId()) == null) {
			config.set("players." + player.getUniqueId() + ".name", player.getName());
			config.set("players." + player.getUniqueId() + ".group", "player");
			config.set("players." + player.getUniqueId() + ".permissions", new ArrayList<String>());
			saveConfig();
		} else if (!config.getString("players." + player.getUniqueId() + ".name").equals(player.getName())) {
			config.set("players." + player.getUniqueId() + ".name", player.getName());
			saveConfig();
		}
	}
	
	public static void setPlayerGroup(Player player, String group) {
		if (group == null) group = "player";
		config.set("players." + player.getUniqueId() + ".group", group);
		saveConfig();
	}
	
	public static void setPlayerGroup(String player, String group) {
		if (group == null) group = "player";
		Player playerObject = Bukkit.getPlayer(player);
		if (playerObject != null) {
			setPlayerGroup(playerObject, group);
			return;
		}
		for (String key : getSection("players", false)) {
			if (config.getString("players." + key + ".name").equals(player)) {
				config.set("players." + key + ".group", group);
				saveConfig();
				return;
			}
		}
	}
	
	public static String getPlayerGroup(Player player) {
		final String group = config.getString("players." + player.getUniqueId() + ".group");
		if (group != null) return group;
		else return "player";
	}
	
	public static String getPlayerGroup(String player) {
		final Player playerObject = Bukkit.getPlayer(player);
		if (playerObject != null) return getPlayerGroup(playerObject);
		for (String key : getSection("players", false)) {
			if (config.getString("players." + key + ".name").equals(player)) {
				return config.getString("players." + key + ".group");
			}
		}
		return "player";
	}
	
	public static void setPlayerSpecificPermissions(Player player, List<String> permissions) {
		if (permissions == null) permissions = new ArrayList<>();
		config.set("players." + player.getUniqueId() + ".permissions", permissions);
		saveConfig();
	}
	
	public static void setPlayerSpecificPermissions(String player, List<String> permissions) {
		if (permissions == null) permissions = new ArrayList<>();
		Player playerObject = Bukkit.getPlayer(player);
		if (playerObject != null) {
			setPlayerSpecificPermissions(playerObject, permissions);
			return;
		}
		for (String key : getSection("players", false)) {
			if (config.getString("players." + key + ".name").equals(player)) {
				config.set("players." + key + ".permissions", permissions);
				saveConfig();
				return;
			}
		}
	}
	
	public static List<String> getPlayerSpecificPermissions(Player player) {
		final List<String> permissions = config.getStringList("players." + player.getUniqueId() + ".permissions");
		if (permissions != null) return permissions;
		else return new ArrayList<>();
	}
	
	public static List<String> getPlayerSpecificPermissions(String player) {
		final Player playerObject = Bukkit.getPlayer(player);
		if (playerObject != null) return getPlayerSpecificPermissions(playerObject);
		for (String key : getSection("players", false)) {
			if (config.getString("players." + key + ".name").equals(player)) {
				return config.getStringList("players." + key + ".permissions");
			}
		}
		return new ArrayList<>();
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
	
	public static void loadConfig() {
		try {
			config = YamlConfiguration.loadConfiguration(file);
		} catch (IllegalArgumentException exception) {}
	}

}
