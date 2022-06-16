package serversystem.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.ServerWarp;
import serversystem.utilities.WorldGroup;

public class SaveConfig {

	private static final File file = new File("plugins/ServerSystem", "save_config.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

	static {
		setDefault("warps", "");
		setDefault("worlds", "");
		setDefault("world_groups", "");
		if (!file.exists()) {
			saveConfig();
		}
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

	public static void setWarp(ServerWarp warp) {
		config.set("warps." + warp.getName() + ".material", warp.getMaterial().toString().toLowerCase());
		config.set("warps." + warp.getName() + ".location", warp.getLocation());
		config.set("warps." + warp.getName() + ".global", warp.isGlobal());
		if (warp.getPermission() != null) {
			config.set("warps." + warp.getName() + ".permission", warp.getPermission());
		} else {
			config.set("warps." + warp.getName() + ".permission", null);
		}
		saveConfig();
	}

	public static void removeWarp(ServerWarp warp) {
		config.set("warps." + warp.getName(), null);
		saveConfig();
	}

	public static ServerWarp getWarp(String name) {
		try {
			final ServerWarp warp = new ServerWarp(name, config.getLocation("warps." + name + ".location"));
			warp.setMaterial(ChatUtil.parseMaterial(config.getString("warps." + name + ".material")));
			warp.setGlobal(config.getBoolean("warps." + name + ".global"));
			if (config.getString("warps." + name + ".permission") != null) {
				warp.setPermission(config.getString("warps." + name + ".permission"));
			}
			return warp;
		} catch (Exception exception) {
			Bukkit.getLogger().warning("Something went wrong while loadig the warp " + name + "!");
			return null;
		}
		
	}

	public static ArrayList<ServerWarp> getWarps() {
		ArrayList<ServerWarp> warps = new ArrayList<>();
		if (getSection("warps", false) != null) {
			for (String name : getSection("warps", false)) {
				warps.add(getWarp(name));
			}
		}
		return warps;
	}
	
	public static void saveLocation(Player player) {
		final String path = "worlds." + player.getWorld().getName() + "." + player.getUniqueId();
		config.set(path + ".name", player.getDisplayName());
		config.set(path + ".x", player.getLocation().getX());
		config.set(path + ".y", player.getLocation().getY());
		config.set(path + ".z", player.getLocation().getZ());
		config.set(path + ".pitch", player.getLocation().getPitch());
		config.set(path + ".yaw", player.getLocation().getYaw());
		config.set(path + ".fly", player.isFlying());
		saveConfig();
	}

	public static Location loadLocation(Player player, World world) {
		try {
			if (config.get("worlds." + world.getName() + "." + player.getUniqueId()) == null) return null;
			final String path = "worlds." + world.getName() + "." + player.getUniqueId();
			final Location location = new Location(world, config.getDouble(path + ".x"), config.getDouble(path + ".y"), config.getDouble(path + ".z"), (float) config.getDouble(path + ".pitch"), (float) config.getDouble(path + ".yaw"));
			return location;
		} catch (Exception exception) {
			Bukkit.getLogger().warning("Something went wrong while loadig location of " + player.getDisplayName() + "!");
			return null;
		}
	}

	public static boolean loadFlying(Player player, World world) {
		try {
			final String path = "worlds." + world.getName() + "." + player.getUniqueId();
			return config.getBoolean(path + ".fly");
		} catch (Exception exception) {
			Bukkit.getLogger().warning("Something went wrong while loadig fly mode of " + player.getDisplayName() + "!");
			return false;
		}
	}
	
	public static void savePlayerProfile(Player player, WorldGroup worldGroup) {
		config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".name", player.getDisplayName());
		saveXp(player, worldGroup);
		saveGamemode(player, worldGroup);
		saveInventory(player, worldGroup);
		saveConfig();
	}
	
	public static void loadPlayerProfile(Player player, WorldGroup worldGroup) {
		if (config.get("world_groups." + worldGroup.getName() + "." + player.getUniqueId()) != null) {
			loadXp(player, worldGroup);
			loadGamemode(player, worldGroup);
			loadInventory(player, worldGroup);
		} else {
			savePlayerProfile(player, worldGroup);
		}
	}

	private static void saveXp(Player player, WorldGroup worldGroup) {
		config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".level", player.getLevel());
		config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".experience", player.getExp());
	}

	private static void loadXp(Player player, WorldGroup worldGroup) {
		player.setLevel(config.getInt("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".level"));
		player.setExp(config.getInt("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".experience"));
	}

	private static void saveGamemode(Player player, WorldGroup worldGroup) {
		switch (player.getGameMode()) {
		case SURVIVAL: config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".gamemode", 0); break;
		case CREATIVE: config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".gamemode", 1); break;
		case ADVENTURE: config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".gamemode", 2); break;
		case SPECTATOR: config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".gamemode", 3); break;
		default: config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".gamemode", 0); break;
		}
	}

	private static void loadGamemode(Player player, WorldGroup worldGroup) {
		switch (config.getInt("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".gamemode")) {
		case 0: player.setGameMode(GameMode.SURVIVAL); break;
		case 1: player.setGameMode(GameMode.CREATIVE); break;
		case 2: player.setGameMode(GameMode.ADVENTURE); break;
		case 3: player.setGameMode(GameMode.SPECTATOR); break;
		default: player.setGameMode(GameMode.ADVENTURE); break;
		}
	}
	
	private static void saveInventory(Player player, WorldGroup worldGroup) {
		final String configarmor = "world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".inventory.armor";
		final String configcontent = "world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".inventory.content";
		for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
			config.set(configarmor + "." + i, player.getInventory().getArmorContents()[i]);
		}
		for (int i = 0; i < player.getInventory().getContents().length; i++) {
			config.set(configcontent + "." + i, player.getInventory().getContents()[i]);
		}
	}

	private static void loadInventory(Player player, WorldGroup worldGroup) {
		final String configarmor = "world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".inventory.armor";
		final String configcontent = "world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".inventory.content";
		player.getInventory().clear();
		ItemStack[] content = new ItemStack[4];
		for (int i = 0; i < 4; i++) {
			if (config.getItemStack(configarmor + "." + i) != null) {
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

	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException exception) {
			Bukkit.getLogger().warning("Something went wrong while saving plugins/ServerSystem/save_config.yml!");
		}
	}

}
