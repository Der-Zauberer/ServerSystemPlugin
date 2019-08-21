package serversystem.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.WorldGroup;

public class SaveConfig {
	
	private static File file = new File("plugins/System", "saveconfig.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public SaveConfig() {
		saveConfig();
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
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
