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
import serversystem.config.Config.ConfigOption;
import serversystem.utilities.WorldGroup;

public class SaveConfig {

	private static final File file = new File("plugins/ServerSystem", "save_config.yml");
	public static FileConfiguration config;

	static {
		loadConfig();
		setDefault("worlds", "");
		setDefault("world_groups", "");
		if (!file.exists()) {
			saveConfig();
		}
	}
	
	private static void setDefault(String key, Object value) {
		if (config.get(key) == null) config.set(key, value);
	}

	private static ArrayList<String> getSection(String section, boolean keys) {
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
	
	public static void removeWorld(String world) {
		config.set("worlds." + world, null);
		saveConfig();
	}
	
	public static void autoRemoveWorldGroups() {
		for (String name : getSection("warps", false)) {
			if (WorldGroup.getWorldGroup(name) == null) config.set("world_groups." + name, null);
		}
		saveConfig();
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
		int gamemode = 2;
		switch (player.getGameMode()) {
			case SURVIVAL: gamemode = 0; break;
			case CREATIVE: gamemode = 1; break;
			case ADVENTURE: gamemode = 2; break;
			case SPECTATOR: gamemode = 3; break;
			default: break;
		}
		config.set(path + ".gamemode", gamemode);
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
	
	public static GameMode loadGamemode(Player player, World world) {
		try {
			final String path = "worlds." + world.getName() + "." + player.getUniqueId();
				if (config.get(path + ".gamemode") != null) {
					switch (config.getInt(path + ".gamemode")) {
					case 0: return GameMode.SURVIVAL;
					case 1: return GameMode.CREATIVE;
					case 2: return GameMode.ADVENTURE;
					case 3: return GameMode.SPECTATOR;
					default: return Config.getWorldGamemode(world);
				}
			} else {
				return Config.getWorldGamemode(world);
			}
		} catch (Exception exception) {
			Bukkit.getLogger().warning("Something went wrong while loadig gamemode mode of " + player.getDisplayName() + "!");
			return Config.getWorldGamemode(world);
		}
	}
	
	public static void savePlayerProfile(Player player, WorldGroup worldGroup) {
		config.set("world_groups." + worldGroup.getName() + "." + player.getUniqueId() + ".name", player.getDisplayName());
		saveGamemode(player, worldGroup);
		if (!Config.getConfigOption(ConfigOption.GLOBAL_INVENTORY)) {
			saveXp(player, worldGroup);
			saveInventory(player, worldGroup);
			player.getInventory().clear();
			player.setLevel(0);
			player.setExp(0);
		}
		saveConfig();
	}
	
	public static void loadPlayerProfile(Player player, WorldGroup worldGroup) {
		if (config.get("world_groups." + worldGroup.getName() + "." + player.getUniqueId()) != null) {
			loadGamemode(player, worldGroup);
			if (!Config.getConfigOption(ConfigOption.GLOBAL_INVENTORY)) {
				loadXp(player, worldGroup);
				loadInventory(player, worldGroup);
			}
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
	
	public static void loadConfig() {
		try {
			config = YamlConfiguration.loadConfiguration(file);
		} catch (IllegalArgumentException exception) {}
	}

}
