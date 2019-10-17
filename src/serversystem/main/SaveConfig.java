package serversystem.main;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.cityadventure.CityBuildPlot;
import serversystem.utilities.WorldGroup;
import serversystem.utilities.WorldGroupHandler;

public class SaveConfig {
	
	private static File file = new File("plugins/System", "saveconfig.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	public static enum LogTypes{WARNING, REPORT, BAN, KICK};
	
	public SaveConfig() {
		if(!file.exists()) {
			saveConfig();
		}
	}
	
	public static ArrayList<String> getSection(String section) {
		ArrayList<String> list = new ArrayList<>();
		if(config.getConfigurationSection(section) != null) {
			for (String key : config.getConfigurationSection(section).getKeys(true)) {
				list.add(key);
			}
		}
		return list;
	}
	
	public static void saveInventory(Player player, WorldGroup worldgroup) {
		String configarmor = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Armor";
    	String configcontent = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Content";
		for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
			config.set(configarmor + "." + i, player.getInventory().getArmorContents()[i]);
		}
		for (int i = 0; i < player.getInventory().getContents().length; i++) {
			config.set(configcontent + "." + i, player.getInventory().getContents()[i]);
		}
		player.getInventory().clear();
		saveConfig();
	}
	
    public static void loadInventory(Player player, WorldGroup worldgroup) {
    	String configarmor = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Armor";
    	String configcontent = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Inventory.Content";
    	player.getInventory().clear();
    	ItemStack[] content = new ItemStack[4];
        for (int i = 0; i < 4; i++) {
        	if(config.getItemStack(configarmor + "." + i) != null) {
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
    
    public static void saveXp(Player player, WorldGroup worldgroup) {
    	config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Level", player.getLevel());
    	config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Experience", player.getExp());
    	player.setLevel(0);
    	player.setExp(0);
    	saveConfig();
    }
    
    public static void loadXp(Player player, WorldGroup worldgroup) {
    	player.setLevel(config.getInt("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Level"));
    	player.setExp(config.getInt("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Experience"));
    }
    
    public static void saveGamemode(Player player, WorldGroup worldgroup) {
    	switch (player.getGameMode()) {
		case SURVIVAL: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 0);	break;
		case CREATIVE: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 1);	break;
		case ADVENTURE: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 2); break;
		case SPECTATOR: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 3); break;
		default: config.set("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode", 0); break;
		}
    }
    
    public static void loadGamemode(Player player, WorldGroup worldgroup) {
    	if (getSection("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId()).contains("Gamemode")) {
    		switch (config.getInt("WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Gamemode")) {
        	case 0: player.setGameMode(GameMode.SURVIVAL); break;
        	case 1: player.setGameMode(GameMode.CREATIVE); break;
        	case 2: player.setGameMode(GameMode.ADVENTURE); break;
        	case 3: player.setGameMode(GameMode.SPECTATOR); break;
        	default: player.setGameMode(GameMode.ADVENTURE);
        	}
    	} else {
    		player.setGameMode(Config.getWorldGamemode(player.getWorld().getName()));
    	}
    }
    
    public static void saveLocation(Player player) {
    	String path = "WorldGroups." + WorldGroupHandler.getWorldGroup(player).getName() + "." + player.getUniqueId() + ".Location";
		config.set(path +".World", player.getLocation().getWorld().getName());
		config.set(path +".X", player.getLocation().getX());
		config.set(path +".Y", player.getLocation().getY());
		config.set(path +".Z", player.getLocation().getZ());
		config.set(path +".Pitch", player.getLocation().getPitch());
		config.set(path +".Yaw", player.getLocation().getYaw());
		saveConfig();
    }
    
    public static Location loadLocation(Player player, WorldGroup worldgroup) {
    	String path = "WorldGroups." + worldgroup.getName() + "." + player.getUniqueId() + ".Location";
    	if(config.getString(path + ".World") == "" || config.getString(path + ".World") == null) {
    		return null;
    	}
    	Location location = new Location(Bukkit.getWorld(config.getString(path +".World")), config.getDouble(path +".X"), config.getDouble(path +".Y"), config.getDouble(path +".Z"), (float) config.getDouble(path +".Pitch"), (float) config.getDouble(path +".Yaw"));
    	return location;
    }
    
    public static void saveLog(Player player, LogTypes logtypes, String message) {
		String logtype = "";
		switch (logtypes) {
		case WARNING: logtype = "Warning"; break;
		case REPORT: logtype = "Report"; break;
		case BAN: logtype = "Ban"; break;
		case KICK: logtype = "Kick"; break;
		default:break;}
		String times[] = new Timestamp(System.currentTimeMillis()).toString().split(":");
		String time = times[0] + ":" + times[1];
		List<String> list = config.getStringList(player.getUniqueId() + "." + logtype);
		list.add(time + " " + message);
		config.set(player.getUniqueId() + "." + logtype, list);
		saveConfig();
	}
	
	public static List<String> getLog(Player player, LogTypes logtypes) {
		String logtype = "";
		switch (logtypes) {
		case WARNING: logtype = "Warning"; break;
		case REPORT: logtype = "Report"; break;
		case BAN: logtype = "Ban"; break;
		case KICK: logtype = "Kick"; break;
		default:break;}
		return config.getStringList(player.getUniqueId() + "." + logtype);
	}
	
	public static List<String> getLatestLog(Player player, LogTypes logtypes) {
		String logtype = "";
		switch (logtypes) {
		case WARNING: logtype = "Warning"; break;
		case REPORT: logtype = "Report"; break;
		case BAN: logtype = "Ban"; break;
		case KICK: logtype = "Kick"; break;
		default:break;}
		List<String> list = config.getStringList(player.getUniqueId() + "." + logtype);
		if(list.size() > 20) {
			for (int i = 0; i < list.size() - 20; i++) {
				list.remove(i);
			}
		}
		return list;
	}
	
	public static void addCitybuildWorld(World world) {
		List<String> list = config.getStringList("Citybuild.Worlds");
		list.add(world.getName());
		config.set("Citybuild.Worlds", list);
		saveConfig();
	}
	
	public static void removeCitybuildWorld(World world) {
		List<String> list = config.getStringList("Citybuild.Worlds");
		list.remove(world.getName());
		config.set("Citybuild.Worlds", list);
		saveConfig();
	}
	
	public static ArrayList<World> getCitybuildWorlds() {
		List<String> list = config.getStringList("Citybuild.Worlds");
		ArrayList<World> worlds = new ArrayList<>();
		for (String string : list) {
			worlds.add(Bukkit.getWorld(string));
		}
		return worlds;
	}
	
	public static void addCitybuildPlot(CityBuildPlot plot) {
		config.set("Citybuild.Plots." + plot.getId() + ".Position1.Word", plot.getPosition1().getWorld());
		config.set("Citybuild.Plots." + plot.getId() + ".Position1.X", plot.getPosition1().getX());
		config.set("Citybuild.Plots." + plot.getId() + ".Position1.Y", plot.getPosition1().getY());
		config.set("Citybuild.Plots." + plot.getId() + ".Position1.Z", plot.getPosition1().getZ());
		config.set("Citybuild.Plots." + plot.getId() + ".Position2.Word", plot.getPosition2().getWorld());
		config.set("Citybuild.Plots." + plot.getId() + ".Position2.X", plot.getPosition2().getX());
		config.set("Citybuild.Plots." + plot.getId() + ".Position2.Y", plot.getPosition2().getY());
		config.set("Citybuild.Plots." + plot.getId() + ".Position2.Z", plot.getPosition2().getZ());
		config.set("Citybuild.Plots." + plot.getId() + ".Owner", plot.getOwner().getUniqueId());
		List<String> trustedPlayers = new ArrayList<String>();
		for (Player player : plot.getTrustedPlayers()) {
			trustedPlayers.add(player.getUniqueId().toString());
		}
		List<String> bannedPlayers = new ArrayList<String>();
		for (Player player : plot.getBannedPlayers()) {
			bannedPlayers.add(player.getUniqueId().toString());
		}
		config.set("Citybuild.Plots." + plot.getId() + ".Trusted", trustedPlayers);
		config.set("Citybuild.Plots." + plot.getId() + ".Banned", bannedPlayers);
		saveConfig();
	}
	
	public static ArrayList<CityBuildPlot> getCitybuildPlots() {
		ArrayList<CityBuildPlot> plots = new ArrayList<>();
		for (String plotId : getSection("Citybuild.Plots.")) {
			Location location1 = new Location(Bukkit.getWorld(config.getString("Citybuild.Plots." + plotId + ".Position1.Word")), config.getDouble("Citybuild.Plots." + plotId + ".Position1.X"), config.getDouble("Citybuild.Plots." + plotId + ".Position1.Y"), config.getDouble("Citybuild.Plots." + plotId + ".Position1.Z"));
			Location location2 = new Location(Bukkit.getWorld(config.getString("Citybuild.Plots." + plotId + ".Position2.Word")), config.getDouble("Citybuild.Plots." + plotId + ".Position2.X"), config.getDouble("Citybuild.Plots." + plotId + ".Position2.Y"), config.getDouble("Citybuild.Plots." + plotId + ".Position2.Z"));
			CityBuildPlot plot = new CityBuildPlot(location1, location2);
			plot.setOwner(Bukkit.getPlayer(UUID.fromString(config.getString("Citybuild.Plots." + plotId + ".Owner"))));
			ArrayList<Player> trustedPlayers = new ArrayList<Player>();
			for (String player : config.getStringList("Citybuild.Plots." + plotId + ".Trusted")) {
				trustedPlayers.add(Bukkit.getPlayer(UUID.fromString(player)));
			}
			plot.getTrustedPlayers().addAll(trustedPlayers);
			ArrayList<Player> bannedPlayers = new ArrayList<Player>();
			for (String player : config.getStringList("Citybuild.Plots." + plotId + ".Banned")) {
				bannedPlayers.add(Bukkit.getPlayer(UUID.fromString(player)));
			}
			plot.getTrustedPlayers().addAll(bannedPlayers);
			plots.add(plot);
		}
		return plots;
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
