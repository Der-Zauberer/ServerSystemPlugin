package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import serversystem.utilities.PlayerInventory;

public class AdminMenu extends PlayerInventory {

	public AdminMenu(Player player) {
		super(player, 36, "Admin");
		setItemOption(ItemOption.FIXED);
		setItem(0, createItem("Gamemode Survival", Material.IRON_SHOVEL), (itemstack) -> {player.setGameMode(GameMode.SURVIVAL);});
		setItem(9, createItem("Gamemode Creative", Material.IRON_PICKAXE), (itemstack) -> {player.setGameMode(GameMode.CREATIVE);});
		setItem(18, createItem("Gamemode Adventure", Material.IRON_SWORD), (itemstack) -> {player.setGameMode(GameMode.ADVENTURE);});
		setItem(27, createItem("Gamemode Spectator", Material.IRON_HELMET), (itemstack) -> {player.setGameMode(GameMode.SPECTATOR);});
		setItem(2, createItem("Time Morning", Material.CLOCK), (itemstack) -> {player.getWorld().setTime(0);});
		setItem(11, createItem("Time Day", Material.CLOCK), (itemstack) -> {player.getWorld().setTime(6000);});
		setItem(20, createItem("Time Night", Material.CLOCK), (itemstack) -> {player.getWorld().setTime(13000);});
		setItem(29, createItem("Time Midnight", Material.CLOCK), (itemstack) -> {player.getWorld().setTime(18000);});
		setItem(4, createPotionItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED), (itemstack) -> {addPtionEffect(player, PotionEffectType.SPEED);});
		setItem(13, createPotionItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP), (itemstack) -> {addPtionEffect(player, PotionEffectType.JUMP);});
		setItem(22, createPotionItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY), (itemstack) -> {addPtionEffect(player, PotionEffectType.INVISIBILITY);});
		setItem(31, createItem("Remove Effects", Material.GLASS_BOTTLE), (itemstack) -> {removeEffects(player);});
		setItem(6, createItem("Weather Clear", Material.SUNFLOWER), (itemstack) -> {player.getWorld().setStorm(false);});
		setItem(15, createItem("Weather Rain", Material.WATER_BUCKET), (itemstack) -> {player.getWorld().setStorm(true); player.getWorld().setThundering(false);});
		setItem(24, createItem("Weather Thunderstorm", Material.HOPPER), (itemstack) -> {player.getWorld().setStorm(true); player.getWorld().setThundering(true);});
		setItem(8, createItem("World Settings", Material.ZOMBIE_HEAD), (itemstack) -> {new WorldsMenu(player).open();});
		setItem(17, createItem("Players", Material.PLAYER_HEAD), (itemstack) -> {new PlayersMenu(player).open();});
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
