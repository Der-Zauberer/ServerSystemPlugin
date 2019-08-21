package serversystem.main;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
		for (String key : config.getConfigurationSection(section).getKeys(true)) {
			list.add(key);
		}
		return list;
	}
	
	public static void saveInventory(Player player, WorldGroup worldGroup) {
		String configarmor = "WorldGroups." + worldGroup.getName() + "." + player.getUniqueId() + ".Inventory.Armor";
    	String configcontent = "WorldGroups." + worldGroup.getName() + "." + player.getUniqueId() + ".Inventory.Content";
		for (int i = 0; i < player.getInventory().getArmorContents().length; i++) {
			config.set(configarmor + "." + i, player.getInventory().getArmorContents()[i]);
		}
		for (int i = 0; i < player.getInventory().getContents().length; i++) {
			config.set(configcontent + "." + i, player.getInventory().getContents()[i]);
		}
		player.getInventory().clear();
		saveConfig();
	}
	
    public static void getInventory(Player player, WorldGroup worldGroup) {
    	String configarmor = "WorldGroups." + worldGroup.getName() + "." + player.getUniqueId() + ".Inventory.Armor";
    	String configcontent = "WorldGroups." + worldGroup.getName() + "." + player.getUniqueId() + ".Inventory.Content";
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
    
    public static Location getLocation(Player player, WorldGroup worldGroup) {
    	String path = "WorldGroups." + worldGroup.getName() + "." + player.getUniqueId() + ".Location";
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
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
