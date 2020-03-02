package serversystem.menus;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import serversystem.handler.MenuHandler;
import serversystem.main.Config;
import serversystem.utilities.PlayerInventory;

public class AdminMenu extends PlayerInventory{

	public AdminMenu(Player player) {
		super(player, 36, "Admin");
		open();
		MenuHandler.addInventory(this);
		setItem(createItem("Gamemode Survival", Material.IRON_SHOVEL), 0);
		setItem(createItem("Gamemode Creative", Material.IRON_PICKAXE), 9);
		setItem(createItem("Gamemode Adventure", Material.IRON_SWORD), 18);
		setItem(createItem("Gamemode Spectator", Material.IRON_HELMET), 27);
		setItem(createItem("Time Morning", Material.CLOCK), 2);
		setItem(createItem("Time Day", Material.CLOCK), 11);
		setItem(createItem("Time Night", Material.CLOCK), 20);
		setItem(createItem("Time Midnight", Material.CLOCK), 29);
		setItem(createPotionItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED), 4);
		setItem(createPotionItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP), 13);
		setItem(createPotionItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY), 22);
		setItem(createItem("Effect Clear", Material.GLASS_BOTTLE), 31);
		setItem(createItem("Weather Clear", Material.SUNFLOWER), 6);
		setItem(createItem("Weather Rain", Material.WATER_BUCKET), 15);
		setItem(createItem("Weather Thunderstorm", Material.HOPPER), 24);
		setItem(createItem("Server Settings", Material.SKELETON_SKULL), 8);
		setItem(createItem("World Settings", Material.ZOMBIE_HEAD), 17);
		setItem(createItem("Player", Material.PLAYER_HEAD), 35);
	}
	
	public void openServerSettings() {
		clear();
		setItem(createItem("JoinMessage", Material.OAK_SIGN), 10);
		setItem(createItem("LeaveMessage", Material.OAK_SIGN), 12);
		setItem(createItem("DefaultGamemode", Material.IRON_PICKAXE), 14);
		setItem(createItem("Gamemode", Material.IRON_PICKAXE), 16);
		setItem(createBooleanItem("JoinMessage", Config.isJoinMessageActiv()), 19);
		setItem(createBooleanItem("LeaveMessage", Config.isLeaveMessageActiv()), 21);
	}
	
	@Override
	public void onItemClicked(ItemStack item, Player player, int slot) {
		if(item.equals(createItem("Gamemode Survival", Material.IRON_SHOVEL))) {
			player.setGameMode(GameMode.SURVIVAL);
		} else if(item.equals(createItem("Gamemode Creative", Material.IRON_PICKAXE))) {
			player.setGameMode(GameMode.CREATIVE);
		} else if(item.equals(createItem("Gamemode Adventure", Material.IRON_SWORD))) {
			player.setGameMode(GameMode.ADVENTURE);
		} else if(item.equals(createItem("Gamemode Spectator", Material.IRON_HELMET))) {
			player.setGameMode(GameMode.SPECTATOR);
		} else if(item.equals(createItem("Time Morning", Material.CLOCK))) {
			player.getWorld().setTime(0);
		} else if(item.equals(createItem("Time Day", Material.CLOCK))) {
			player.getWorld().setTime(6000);
		} else if(item.equals(createItem("Time Night", Material.CLOCK))) {
			player.getWorld().setTime(13000);
		} else if(item.equals(createItem("Time Midnight", Material.CLOCK))) {
			player.getWorld().setTime(18000);
		} else if(item.equals(createPotionItem("Effect Speed", Color.BLUE, PotionEffectType.SPEED))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3600, 2), true);
		} else if(item.equals(createPotionItem("Effect Jump Boost", Color.GREEN, PotionEffectType.JUMP))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 3600, 2), true);
		} else if(item.equals(createPotionItem("Effect Invisibilitiy", Color.PURPLE, PotionEffectType.INVISIBILITY))) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 2), true);
		} else if(item.equals(createItem("Effect Clear", Material.GLASS_BOTTLE))) {
			for (PotionEffect effect : player.getActivePotionEffects())
				player.removePotionEffect(effect.getType());
		} else if(item.equals(createItem("Weather Clear", Material.SUNFLOWER))) {
			player.getWorld().setStorm(false);
		} else if(item.equals(createItem("Weather Rain", Material.WATER_BUCKET))) {
			player.getWorld().setStorm(true);
			player.getWorld().setThundering(false);
		} else if(item.equals(createItem("Weather Thunderstorm", Material.HOPPER))) {
			player.getWorld().setStorm(true);
			player.getWorld().setThundering(true);
		} else if(item.equals(createItem("Server Settings", Material.SKELETON_SKULL))) {
			openServerSettings();
		}
	}

}
