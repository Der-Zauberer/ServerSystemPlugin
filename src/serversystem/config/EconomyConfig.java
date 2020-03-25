package serversystem.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class EconomyConfig {
	
	private static File file = new File("plugins/System", "economyConfig.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public EconomyConfig() {
		if(!file.exists()) {
			saveConfig();
		}
	}
	
	public static void addPlayer(Player player) {
		if(!config.getBoolean("Players." + player.getUniqueId() + ".exists")) {
			config.set("Players." + player.getUniqueId() + ".name", player.getName());
			config.set("Players." + player.getUniqueId() + ".exists", true);
			config.set("Players." + player.getUniqueId() + ".bankAccount", 1000);
			config.set("Players." + player.getUniqueId() + ".creditAmount", 0);
			config.set("Players." + player.getUniqueId() + ".healthInsurance", false);
			config.set("Players." + player.getUniqueId() + ".discountCard", false);
			saveConfig();
		}
	}
	
	public static void setBankAccount(Player player, int money) {
		config.set("Players." + player.getUniqueId() + ".bankAccount",getBankAccount(player)+money);
		saveConfig();
	}
	
	public static int getBankAccount(Player player) {
		return config.getInt("Players." + player.getUniqueId() + ".bankAccount");
	}
	
	public static void setCreditValue(Player player, int value) {
		config.set("Players." + player.getUniqueId() + ".creditAmount",getCreditValue(player)+value);
		saveConfig();
	}
	
	public static int getCreditValue(Player player) {
		return config.getInt("Players." + player.getUniqueId() + ".creditAmount");
	}
	
	public static void setHealthInsurance(Player player, boolean value) {
		config.set("Players." + player.getUniqueId() + ".healthInsurance",value);
		saveConfig();
	}
	
	public static boolean getHealthInsurance(Player player) {
		return config.getBoolean("Players." + player.getUniqueId() + ".healthInsurance");
	}
	
	public static void setDiscountCard(Player player, boolean value) {
		config.set("Players." + player.getUniqueId() + ".discountCard", value);
		saveConfig();
	}
	
	public static boolean getDiscountCard(Player player) {
		return config.getBoolean("Players." + player.getUniqueId() + ".discountCard");
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
