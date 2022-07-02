package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PlayerInventory;

public class PlayerMenu extends PlayerInventory {
	
	public PlayerMenu(Player player, Player target) {
		super(player, 4, "Player: " + target.getName());
		setFixed(true);
		setItem(0, new ExtendedItemStack("Gamemode Survival", Material.IRON_SPADE), event -> target.setGameMode(GameMode.SURVIVAL));
		setItem(2, new ExtendedItemStack("Gamemode Adventure", Material.IRON_SWORD), event -> target.setGameMode(GameMode.ADVENTURE));
		setItem(4, new ExtendedItemStack("Effect Speed", Material.POTION).addPotionMeta(Color.BLUE, new PotionEffect(PotionEffectType.SPEED, 3600, 2)), event -> addPotionEffect(player, PotionEffectType.SPEED));
		setItem(6, new ExtendedItemStack("Effect Invisibilitiy", Material.POTION).addPotionMeta(Color.PURPLE, new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 2)), event -> addPotionEffect(target, PotionEffectType.INVISIBILITY));
		setItem(8, new ExtendedItemStack("Kick", Material.BARRIER), event -> player.kickPlayer("Kicked by an operator!"));
		setItem(9, new ExtendedItemStack("Gamemode Creative", Material.IRON_PICKAXE), event -> target.setGameMode(GameMode.CREATIVE));
		setItem(11, new ExtendedItemStack("Gamemode Spectator", Material.IRON_HELMET), event -> target.setGameMode(GameMode.SPECTATOR));
		setItem(13, new ExtendedItemStack("Effect Jump Boost", Material.POTION).addPotionMeta(Color.GREEN, new PotionEffect(PotionEffectType.JUMP, 3600, 2)), event -> addPotionEffect(target, PotionEffectType.JUMP));
		setItem(15, new ExtendedItemStack("Remove Effects", Material.GLASS_BOTTLE), event -> removeEffects(player));
		setItem(17, new ExtendedItemStack("Kill Player", Material.DIAMOND_SWORD), event -> target.setHealth(0));
		setItem(27,  new ExtendedItemStack(target.getName(), Material.REDSTONE_BLOCK));
		setItem(31, new ExtendedItemStack("Back", Material.SPECTRAL_ARROW), event -> new PlayerListMenu(player).open()); 
		setItem(35, new ExtendedItemStack("Teleport to " + target.getName(), Material.ENDER_PEARL), event -> player.teleport(target));
	}
	
	private void addPotionEffect(Player player, PotionEffectType effect) {
		player.addPotionEffect(new PotionEffect(effect, 12000, 2));
	}
	
	private void removeEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

}