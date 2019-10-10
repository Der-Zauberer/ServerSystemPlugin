package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import serversystem.utilities.PlayerInventory;

public class AdminMenu extends PlayerInventory{

	public AdminMenu(Player player) {
		super(player, 36, "Admin");
		open();
		addItem("Gamemode Survival", Material.IRON_SHOVEL, 0);
		addItem("Gamemode Creative", Material.IRON_PICKAXE, 9);
		addItem("Gamemode Adventure", Material.IRON_SWORD, 18);
		addItem("Gamemode Spectator", Material.IRON_HELMET, 27);
		addItem("Time Morning", Material.CLOCK, 2);
		addItem("Time Day", Material.CLOCK, 11);
		addItem("Time Night", Material.CLOCK, 20);
		addItem("Time Midnight", Material.CLOCK, 29);
		addPotionItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED, 4);
		addPotionItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP, 13);
		addPotionItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY, 22);
		addItem("Effect Clear", Material.GLASS_BOTTLE, 31);
		addItem("Weather Clear", Material.SUNFLOWER, 6);
		addItem("Weather Rain", Material.WATER_BUCKET, 15);
		addItem("Weather Thunderstorm", Material.HOPPER, 24);
		addItem("Server Settings", Material.SKELETON_SKULL, 8);
		addItem("World Settings", Material.ZOMBIE_HEAD, 17);
		addItem("Player", Material.PLAYER_HEAD, 35);
	}
	
	public void openServerSettings() {
		clear();
		addItem("JoinMessage", Material.OAK_SIGN, 0);
		addItem("LeaveMessage", Material.OAK_SIGN, 1);
		addItem("DefaultGamemode", Material.IRON_PICKAXE, 2);
		addItem("Gamemode", Material.IRON_PICKAXE, 3);
	}

}
