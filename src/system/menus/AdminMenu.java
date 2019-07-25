package system.menus;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import system.utilities.InventoryMenu;

public class AdminMenu extends InventoryMenu{

	public AdminMenu(Player player) {
		super(player, 36, "Admin");
		addItem("Gamemode Survival", Material.IRON_SHOVEL, 0);
		addItem("Gamemode Creative", Material.IRON_PICKAXE, 9);
		addItem("Gamemode Adventure", Material.IRON_SWORD, 18);
		addItem("Gamemode Spectator", Material.IRON_HELMET, 27);
		addItem("Time Morning", Material.CLOCK, 2);
		addItem("Time Day", Material.CLOCK, 11);
		addItem("Time Night", Material.CLOCK, 20);
		addItem("Time Midnight", Material.CLOCK, 29);
		addItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED, 4);
		addItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP, 13);
		addItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY, 22);
		addItem("Effect Clear", Material.GLASS_BOTTLE, 31);
		addItem("Weather Clear", Material.SUNFLOWER, 6);
		addItem("Weather Rain", Material.WATER_BUCKET, 15);
		addItem("Weather Thunderstorm", Material.HOPPER, 24);
		open();
	}

}
