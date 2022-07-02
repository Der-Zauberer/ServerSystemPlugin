package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PlayerInventory;

public class AdminMenu extends PlayerInventory {

	public AdminMenu(Player player) {
		super(player, 4, "Admin");
		setFixed(true);
		setItem(0, new ExtendedItemStack("Gamemode Survival", Material.IRON_SPADE), event -> player.setGameMode(GameMode.SURVIVAL));
		setItem(9, new ExtendedItemStack("Gamemode Creative", Material.IRON_PICKAXE), event -> player.setGameMode(GameMode.CREATIVE));
		setItem(18, new ExtendedItemStack("Gamemode Adventure", Material.IRON_SWORD), event -> player.setGameMode(GameMode.ADVENTURE));
		setItem(27, new ExtendedItemStack("Gamemode Spectator", Material.IRON_HELMET), event -> player.setGameMode(GameMode.SPECTATOR));
		setItem(2, new ExtendedItemStack("Time Morning", Material.WATCH), event -> player.getWorld().setTime(0));
		setItem(11, new ExtendedItemStack("Time Day", Material.WATCH), event -> player.getWorld().setTime(6000));
		setItem(20, new ExtendedItemStack("Time Night", Material.WATCH), event -> player.getWorld().setTime(13000));
		setItem(29, new ExtendedItemStack("Time Midnight", Material.WATCH), event -> player.getWorld().setTime(18000));
		setItem(4, new ExtendedItemStack("Effect Speed", Material.POTION).addPotionMeta(Color.BLUE, new PotionEffect(PotionEffectType.SPEED, 3600, 2)), event -> addPtionEffect(player, PotionEffectType.SPEED));
		setItem(13, new ExtendedItemStack("Effect Jump Boost", Material.POTION).addPotionMeta(Color.BLUE, new PotionEffect(PotionEffectType.JUMP, 3600, 2)), event -> addPtionEffect(player, PotionEffectType.JUMP));
		setItem(22, new ExtendedItemStack("Effect Invisibilitiy", Material.POTION).addPotionMeta(Color.BLUE, new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 2)), event -> addPtionEffect(player, PotionEffectType.INVISIBILITY));
		setItem(31, new ExtendedItemStack("Remove Effects", Material.GLASS_BOTTLE), event -> removeEffects(player));
		setItem(6, new ExtendedItemStack("Weather Clear", Material.DOUBLE_PLANT), event -> {
			player.getWorld().setStorm(false);
		});
		setItem(15, new ExtendedItemStack("Weather Rain", Material.WATER_BUCKET), event -> {
			player.getWorld().setStorm(true); 
			player.getWorld().setThundering(false);
		});
		setItem(24, new ExtendedItemStack("Weather Thunderstorm", Material.HOPPER), event -> {
			player.getWorld().setStorm(true); 
			player.getWorld().setThundering(true);
		});
		setItem(8, new ExtendedItemStack("World Settings", Material.LAPIS_BLOCK), event -> new WorldListMenu(player).open());
		setItem(17, new ExtendedItemStack("Players", Material.REDSTONE_BLOCK), event -> new PlayerListMenu(player).open());
	}
	
	private void addPtionEffect(Player player, PotionEffectType effect) {
		player.addPotionEffect(new PotionEffect(effect, 12000, 2));
	}
	
	private void removeEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

}
