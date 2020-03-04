package serversystem.menus;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import serversystem.config.Config;
import serversystem.handler.MenuHandler;
import serversystem.utilities.PlayerInventory;

public class AdminMenu extends PlayerInventory{

	public AdminMenu(Player player) {
		super(player, 36, "Admin");
		open();
		MenuHandler.addInventory(this);
		openMainInventory();
	}
	
	public void openMainInventory() {
		clear();
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
		setItem(createItem("JoinMessage", Material.OAK_SIGN), 1);
		setItem(createItem("LeaveMessage", Material.OAK_SIGN), 3);
		setItem(createItem("DefaultGamemode", Material.IRON_PICKAXE), 5);
		setItem(createItem("Gamemode", Material.IRON_PICKAXE), 7);
		setItem(createBooleanItem("JoinMessage", Config.isJoinMessageActiv()), 10);
		setItem(createBooleanItem("LeaveMessage", Config.isLeaveMessageActiv()), 12);
		setItem(createItem("Back", Material.SPECTRAL_ARROW), 31);
	}
	
	public void openWorldSettings(String world) {
		clear();
		setItem(createItem("Damage", Material.SHIELD), 0);
		setItem(createItem("Explosion", Material.TNT), 2);
		setItem(createItem("Hunger", Material.COOKED_BEEF), 4);
		setItem(createItem("Protect", Material.GOLDEN_PICKAXE), 6);
		setItem(createItem("PVP", Material.IRON_SWORD), 8);
		setItem(createBooleanItem("Damage", Config.hasWorldDamage(world)), 9);
		setItem(createBooleanItem("Explosion", Config.hasWorldExplosion(world)), 11);
		setItem(createBooleanItem("Hunger", Config.hasWorldHunger(world)), 13);
		setItem(createBooleanItem("Protect", Config.hasWorldProtect(world)), 15);
		setItem(createBooleanItem("PVP", Config.hasWorldPVP(world)), 17);
		setItem(createItem("World: " + world, Material.ZOMBIE_HEAD), 27);
		setItem(createItem("Back", Material.SPECTRAL_ARROW), 31);
	}
	
	public void openPlayerSettings(String player) {
		clear();
		setItem(createItem("Teleport to Player", Material.ENDER_PEARL), 9);
		setItem(createItem("Kick", Material.BARRIER), 16);
		setItem(createItem("Bann", Material.BARRIER), 17);
		setItem(createItem("Player: " + player, Material.PLAYER_HEAD), 27);
		setItem(createItem("Back", Material.SPECTRAL_ARROW), 31);
	}

	public void openWorlds() {
		clear();
		setItem(createItem("Back", Material.SPECTRAL_ARROW), 31);
		for(int i = 0; i < Bukkit.getWorlds().size() && i < 26; i++) {
			setItem(createItem("World: " + Bukkit.getWorlds().get(i).getName(), Material.ZOMBIE_HEAD) ,i);
		}
	}
	
	public void openPlayers() {
		clear();
		setItem(createItem("Back", Material.SPECTRAL_ARROW), 31);
		int i = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if(i < 26) {
				setItem(createItem("Player: " + player.getName(), Material.PLAYER_HEAD) ,i);
			} else {
				return;
			}
		}
	}
	
	@Override
	public void onItemClicked(Inventory inventory, ItemStack item, Player player, int slot) {
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
		} else if(item.equals(createItem("World Settings", Material.ZOMBIE_HEAD))) {
			openWorlds();
		} else if(item.equals(createItem("Player", Material.PLAYER_HEAD))) {
			openPlayers();
		} else if(item.equals(createItem("Back", Material.SPECTRAL_ARROW))) {
			openMainInventory();
		} else if(item.equals(createBooleanItem("JoinMessage", true)) || item.equals(createBooleanItem("JoinMessage", false))) {
			setItem(createBooleanItem("JoinMessage", !Config.isJoinMessageActiv()), 10);
			Config.setJoinMessageActive(!Config.isJoinMessageActiv());
		} else if(item.equals(createBooleanItem("LeaveMessage", true)) || item.equals(createBooleanItem("LeaveMessage", false))) {
			setItem(createBooleanItem("LeaveMessage", !Config.isLeaveMessageActiv()), 12);
			Config.setJoinMessageActive(!Config.isLeaveMessageActiv());
		} else if(item.getItemMeta().getDisplayName().startsWith("World: ") && item.getType() == Material.ZOMBIE_HEAD) {
			String worldname[] = item.getItemMeta().getDisplayName().split(" ");
			if(worldname.length == 2 && Bukkit.getWorld(worldname[1]) != null) {
				openWorldSettings(worldname[1]);
			}
		} else if(item.equals(createBooleanItem("Damage", true)) || item.equals(createBooleanItem("Damage", false))) {
			setItem(createBooleanItem("Damage", !Config.hasWorldDamage(player.getWorld().getName())), 9);
			Config.setWorldDamage(player.getWorld().getName(), !Config.hasWorldDamage(player.getWorld().getName()));
		} else if(item.equals(createBooleanItem("Explosion", true)) || item.equals(createBooleanItem("Explosion", false))) {
			setItem(createBooleanItem("Explosion", !Config.hasWorldExplosion(player.getWorld().getName())), 11);
			Config.setWorldExplosion(player.getWorld().getName(), !Config.hasWorldExplosion(player.getWorld().getName()));
		} else if(item.equals(createBooleanItem("Hunger", true)) || item.equals(createBooleanItem("Hunger", false))) {
			setItem(createBooleanItem("Hunger", !Config.hasWorldHunger(player.getWorld().getName())), 13);
			Config.setWorldHunger(player.getWorld().getName(), !Config.hasWorldHunger(player.getWorld().getName()));
		} else if(item.equals(createBooleanItem("Protect", true)) || item.equals(createBooleanItem("Protect", false))) {
			setItem(createBooleanItem("Protect", !Config.hasWorldProtect(player.getWorld().getName())), 15);
			Config.setWorldProtect(player.getWorld().getName(), !Config.hasWorldProtect(player.getWorld().getName()));
		} else if(item.equals(createBooleanItem("PVP", true)) || item.equals(createBooleanItem("PVP", false))) {
			setItem(createBooleanItem("PVP", !Config.hasWorldPVP(player.getWorld().getName())), 17);
			Config.setWorldPVP(player.getWorld().getName(), !Config.hasWorldPVP(player.getWorld().getName()));
		} else if(item.getItemMeta().getDisplayName().startsWith("Player: ") && item.getType() == Material.PLAYER_HEAD) {
			String playername[] = item.getItemMeta().getDisplayName().split(" ");
			if(playername.length == 2 && Bukkit.getPlayer(playername[1]) != null) {
				openPlayerSettings(playername[1]);
			}
		}
	}

}
