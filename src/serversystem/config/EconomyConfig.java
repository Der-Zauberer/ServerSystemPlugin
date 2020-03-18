package serversystem.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class EconomyConfig {
	
	private static int bankAccount = 1000;
	private static int creditValue = 0;
	private static boolean casinoMembership = false;
	private static boolean healthInsurance = false;
	private static boolean discountCard = false;
	private static File file = new File("plugins/System", "economyConfig.yml");
	public static FileConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public EconomyConfig(Player player) {
		if(!file.exists()) {
			saveConfig();
			addPlayer(player);
		}
	}
	
	public static void addPlayer(Player player) {
		if(!config.getBoolean("Players." + player.getUniqueId() + ".exists")) {
			config.set("Players." + player.getUniqueId() + ".name", player.getName());
			config.set("Players." + player.getUniqueId() + ".exists", true);
			config.set("Players." + player.getUniqueId() + ".bankAccount", bankAccount);
			config.set("Players." + player.getUniqueId() + ".creditAmount", creditValue);
			config.set("Players." + player.getUniqueId() + ".healthInsurance", healthInsurance);
			config.set("Players." + player.getUniqueId() + ".discountCard", discountCard);
			config.set("Players." + player.getUniqueId() + ".casinoMembership", casinoMembership);
			saveConfig();
		}
	}
	
	public static int loadBankAccount(Player player) {
		return (int) config.get("Players." + player.getUniqueId() + ".bankAccount");
	}
	
	public static int loadCreditValue(Player player) {
		return (int) config.get("Players." + player.getUniqueId() + ".creditAmount");
	}
	
	public static boolean loadHealthInsurance(Player player) {
		return (boolean) config.get("Players." + player.getUniqueId() + ".healthInsurance");
	}
	
	public static boolean loadDiscountCard(Player player) {
		return (boolean) config.get("Players." + player.getUniqueId() + ".discountCard");
	}
	
	public static boolean loadCasinoMembership(Player player) {
		return (boolean) config.get("Players." + player.getUniqueId() + ".casinoMembership");
	}
	
	public static void saveBankAccount(Player player, int money) {
		config.set("Players." + player.getUniqueId() + ".bankAccount",loadBankAccount(player)+money);
		saveConfig();
	}
	
	public static void saveCreditValue(Player player, int value) {
		config.set("Players." + player.getUniqueId() + ".creditAmount",loadCreditValue(player)+value);
		saveConfig();
	}
	
	public static void saveHealthInsurance(Player player, boolean value) {
		config.set("Players." + player.getUniqueId() + ".healthInsurance",value);
		saveConfig();
	}
	
	public static void saveDiscountCard(Player player, boolean value) {
		config.set("Players." + player.getUniqueId() + ".discountCard", value);
		saveConfig();
	}
	
	public static void saveCasinoMembership(Player player, boolean value) {
		config.set("Players." + player.getUniqueId() + ".casinoMembership", value);
		saveConfig();
	}
	
	public static void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
