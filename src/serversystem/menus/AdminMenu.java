package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import serversystem.utilities.ItemMenu;

public class AdminMenu extends ItemMenu {

	public AdminMenu() {
		addItem(0, createItem("Gamemode Survival", Material.IRON_SHOVEL), (itemstack, player) -> {player.setGameMode(GameMode.SURVIVAL);});
		addItem(9, createItem("Gamemode Creative", Material.IRON_PICKAXE), (itemstack, player) -> {player.setGameMode(GameMode.CREATIVE);});
		addItem(18, createItem("Gamemode Adventure", Material.IRON_SWORD), (itemstack, player) -> {player.setGameMode(GameMode.ADVENTURE);});
		addItem(27, createItem("Gamemode Spectator", Material.IRON_HELMET), (itemstack, player) -> {player.setGameMode(GameMode.SPECTATOR);});
		addItem(2, createItem("Time Morning", Material.CLOCK), (itemstack, player) -> {player.getWorld().setTime(0);});
		addItem(11, createItem("Time Day", Material.CLOCK), (itemstack, player) -> {player.getWorld().setTime(6000);});
		addItem(20, createItem("Time Night", Material.CLOCK), (itemstack, player) -> {player.getWorld().setTime(13000);});
		addItem(29, createItem("Time Midnight", Material.CLOCK), (itemstack, player) -> {player.getWorld().setTime(18000);});
		addItem(4, createPotionItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED), (itemstack, player) -> {addPtionEffect(player, PotionEffectType.SPEED);});
		addItem(13, createPotionItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP), (itemstack, player) -> {addPtionEffect(player, PotionEffectType.JUMP);});
		addItem(22, createPotionItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY), (itemstack, player) -> {addPtionEffect(player, PotionEffectType.INVISIBILITY);});
		addItem(31, createItem("Remove Effects", Material.GLASS_BOTTLE), (itemstack, player) -> {removeEffects(player);});
		addItem(6, createItem("Weather Clear", Material.SUNFLOWER), (itemstack, player) -> {player.getWorld().setStorm(false);});
		addItem(15, createItem("Weather Rain", Material.WATER_BUCKET), (itemstack, player) -> {player.getWorld().setStorm(true); player.getWorld().setThundering(false);});
		addItem(24, createItem("Weather Thunderstorm", Material.HOPPER), (itemstack, player) -> {player.getWorld().setStorm(true); player.getWorld().setThundering(true);});
		addItem(8, createItem("Server Settings", Material.SKELETON_SKULL), (itemstack, player) -> {getPlayerInventory().setItemMenu(new ServerSettingsMenu());});
		addItem(17, createItem("World Settings", Material.ZOMBIE_HEAD), (itemstack, player) -> {getPlayerInventory().setItemMenu(new WorldsMenu());});
		addItem(35, createItem("Players", Material.PLAYER_HEAD), (itemstack, player) -> {getPlayerInventory().setItemMenu(new PlayersMenu());});
	}
	
	private void addPtionEffect(Player player, PotionEffectType effect) {
		player.addPotionEffect(new PotionEffect(effect, 12000, 2), true);
	}
	
	private void removeEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
	}

}
