package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import serversystem.utilities.PlayerInventory;

public class PlayerMenu extends PlayerInventory {
	
	public PlayerMenu(Player player, Player target) {
		super(player, 36, "Player: " + target.getName());
		setItemOption(ItemOption.FIXED);
		setItem(0, createItem("Gamemode Survival", Material.IRON_SHOVEL), (itemstack) -> {target.setGameMode(GameMode.SURVIVAL);});
		setItem(2, createItem("Gamemode Adventure", Material.IRON_SWORD), (itemstack) -> {target.setGameMode(GameMode.ADVENTURE);});
		setItem(4, createPotionItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED), (itemstack) -> {addPotionEffect(player, PotionEffectType.SPEED);});
		setItem(6, createPotionItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY), (itemstack) -> {addPotionEffect(target, PotionEffectType.INVISIBILITY);});
		setItem(8, createItem("Kick", Material.BARRIER), (itemstack) -> {player.kickPlayer("Kicked by an operator!");});
		setItem(9, createItem("Gamemode Creative", Material.IRON_PICKAXE), (itemstack) -> {target.setGameMode(GameMode.CREATIVE);});
		setItem(11, createItem("Gamemode Spectator", Material.IRON_HELMET), (itemstack) -> {target.setGameMode(GameMode.SPECTATOR);});
		setItem(13, createPotionItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP), (itemstack) -> {addPotionEffect(target, PotionEffectType.JUMP);});
		setItem(15, createItem("Remove Effects", Material.GLASS_BOTTLE), (itemstack) -> {removeEffects(player);});
		setItem(17, createItem("Kill Player", Material.DIAMOND_SWORD), (itemstack) -> {target.setHealth(0);});
		setItem(27, createPlayerSkullItem(target.getName(), target));
		setItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack) -> {player.closeInventory(); new PlayersMenu(player).open();}); 
		setItem(35, createItem("Teleport to " + target.getName(), Material.ENDER_PEARL), (itemstack) -> {player.teleport(target);});
	}
	
	private void addPotionEffect(Player player, PotionEffectType effect) {
		player.addPotionEffect(new PotionEffect(effect, 12000, 2));
	}
	
	private void removeEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
	}

}