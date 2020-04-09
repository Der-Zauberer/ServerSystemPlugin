package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import serversystem.utilities.ItemMenu;

public class PlayerMenu extends ItemMenu {
	
	public PlayerMenu(Player player) {
		addItem(0, createItem("Gamemode Survival", Material.IRON_SHOVEL), (itemstack, player1) -> {player.setGameMode(GameMode.SURVIVAL);});
		addItem(2, createItem("Gamemode Adventure", Material.IRON_SWORD), (itemstack, player1) -> {player.setGameMode(GameMode.ADVENTURE);});
		addItem(4, createPotionItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED), (itemstack, player1) -> {addPtionEffect(player, PotionEffectType.SPEED);});
		addItem(6, createPotionItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY), (itemstack, player1) -> {addPtionEffect(player, PotionEffectType.INVISIBILITY);});
		addItem(8, createItem("Kick", Material.BARRIER), (itemstack, player1) -> {player.kickPlayer("Kicked by an operator!");});
		addItem(9, createItem("Gamemode Creative", Material.IRON_PICKAXE), (itemstack, player1) -> {player.setGameMode(GameMode.CREATIVE);});
		addItem(11, createItem("Gamemode Spectator", Material.IRON_HELMET), (itemstack, player1) -> {player.setGameMode(GameMode.SPECTATOR);});
		addItem(13, createPotionItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP), (itemstack, player1) -> {addPtionEffect(player, PotionEffectType.JUMP);});
		addItem(15, createItem("Remove Effects", Material.GLASS_BOTTLE), (itemstack, player1) -> {removeEffects(player);});
		addItem(17, createItem("Kill Player", Material.DIAMOND_SWORD), (itemstack, player1) -> {player.setHealth(0);});
		addItem(27, createPlayerSkullItem(player.getName(), player));
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player1) -> {getPlayerInventory().setItemMenu(new PlayersMenu());});
		addItem(35, createItem("Teleport to " + player.getName(), Material.ENDER_PEARL), (itemstack, player1) -> {player1.teleport(player);});
	}
	
	private void addPtionEffect(Player player, PotionEffectType effect) {
		player.addPotionEffect(new PotionEffect(effect, 12000, 2), true);
	}
	
	private void removeEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
	}

}