package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.config.Config;
import serversystem.handler.WorldGroupHandler;
import serversystem.utilities.PlayerInventory;

public class WorldMenu extends PlayerInventory {
	
	public WorldMenu(Player player, World world) {
		super(player, 36, "World: " + world.getName());
		setItemOption(ItemOption.FIXED);
		setItem(0, createItem("Damage", Material.SHIELD));
		setItem(2, createItem("Explosion", Material.TNT));
		setItem(4, createItem("Hunger", Material.COOKED_BEEF));
		setItem(6, createItem("Protection", Material.GOLDEN_PICKAXE));
		setItem(8, createItem("PVP", Material.IRON_SWORD));
		setItem(9, createBooleanItem("Damage", Config.hasWorldDamage(world.getName())), (itemstack) -> {setWorldDamage(itemstack, player);});
		setItem(11, createBooleanItem("Explosion", Config.hasWorldExplosion(world.getName())), (itemstack) -> {setWorldExplosion(itemstack, player);});
		setItem(13, createBooleanItem("Hunger", Config.hasWorldHunger(world.getName())), (itemstack) -> {setWorldHunger(itemstack, player);});
		setItem(15, createBooleanItem("Protection", Config.hasWorldProtection(world.getName())), (itemstack) -> {setWorldProtection(itemstack, player);});
		setItem(17, createBooleanItem("PVP", Config.hasWorldPVP(world.getName())), (itemstack) -> {setWorldPVP(itemstack, player);});
		setItem(27, createItem(world.getName(), Material.ZOMBIE_HEAD));
		setItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack) -> {new WorldsMenu(player).open();});
		setItem(35, createItem("Teleport to " + world.getName(), Material.ENDER_PEARL), (itemstack) -> {WorldGroupHandler.teleportPlayer(player, world);});
	}
	
	private void setWorldDamage(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Damage", true)) || itemstack.equals(createBooleanItem("Damage", false))) {
			setItem(9, createBooleanItem("Damage", !Config.hasWorldDamage(player.getWorld().getName())), (itemstack1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldDamage(player.getWorld().getName(), !Config.hasWorldDamage(player.getWorld().getName()));
		}
	}
	
	private void setWorldExplosion(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Explosion", true)) || itemstack.equals(createBooleanItem("Explosion", false))) {
			setItem(11, createBooleanItem("Explosion", !Config.hasWorldExplosion(player.getWorld().getName())), (itemstack1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldExplosion(player.getWorld().getName(), !Config.hasWorldExplosion(player.getWorld().getName()));
		}
	}
	
	private void setWorldHunger(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Hunger", true)) || itemstack.equals(createBooleanItem("Hunger", false))) {
			setItem(13, createBooleanItem("Hunger", !Config.hasWorldHunger(player.getWorld().getName())), (itemstack1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldHunger(player.getWorld().getName(), !Config.hasWorldHunger(player.getWorld().getName()));
		}
	}
	
	private void setWorldProtection(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Protection", true)) || itemstack.equals(createBooleanItem("Protection", false))) {
			setItem(15, createBooleanItem("Protection", !Config.hasWorldProtection(player.getWorld().getName())), (itemstack1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldProtection(player.getWorld().getName(), !Config.hasWorldProtection(player.getWorld().getName()));
		}
	}
	
	private void setWorldPVP(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("PVP", true)) || itemstack.equals(createBooleanItem("PVP", false))) {
			setItem(17, createBooleanItem("PVP", !Config.hasWorldPVP(player.getWorld().getName())), (itemstack1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldPVP(player.getWorld().getName(), !Config.hasWorldPVP(player.getWorld().getName()));
		}
	}

}
