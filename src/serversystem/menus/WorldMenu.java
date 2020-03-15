package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import serversystem.config.Config;
import serversystem.utilities.InventoryMenu;

public class WorldMenu extends InventoryMenu {
	
	public WorldMenu(World world) {
		addItem(0, createItem("Damage", Material.SHIELD));
		addItem(2, createItem("Explosion", Material.TNT));
		addItem(4, createItem("Hunger", Material.COOKED_BEEF));
		addItem(6, createItem("Protection", Material.GOLDEN_PICKAXE));
		addItem(8, createItem("PVP", Material.IRON_SWORD));
		addItem(9, createBooleanItem("Damage", Config.hasWorldDamage(world.getName())), (itemstack, player) -> {setWorldDamage(itemstack, player);});
		addItem(11, createBooleanItem("Explosion", Config.hasWorldExplosion(world.getName())), (itemstack, player) -> {setWorldExplosion(itemstack, player);});
		addItem(13, createBooleanItem("Hunger", Config.hasWorldHunger(world.getName())), (itemstack, player) -> {setWorldHunger(itemstack, player);});
		addItem(15, createBooleanItem("Protection", Config.hasWorldProtection(world.getName())), (itemstack, player) -> {setWorldProtection(itemstack, player);});
		addItem(17, createBooleanItem("PVP", Config.hasWorldPVP(world.getName())), (itemstack, player) -> {setWorldPVP(itemstack, player);});
		addItem(27, createItem("World: " + world.getName(), Material.ZOMBIE_HEAD));
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player) -> {getPlayerInventory().setInventoryMenu(new WorldsMenu());});
	}
	
	private void setWorldDamage(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Damage", true)) || itemstack.equals(createBooleanItem("Damage", false))) {
			addItem(9, createBooleanItem("Damage", !Config.hasWorldDamage(player.getWorld().getName())), (itemstack1, player1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldDamage(player.getWorld().getName(), !Config.hasWorldDamage(player.getWorld().getName()));
		}
	}
	
	private void setWorldExplosion(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Explosion", true)) || itemstack.equals(createBooleanItem("Explosion", false))) {
			addItem(11, createBooleanItem("Explosion", !Config.hasWorldExplosion(player.getWorld().getName())), (itemstack1, player1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldExplosion(player.getWorld().getName(), !Config.hasWorldExplosion(player.getWorld().getName()));
		}
	}
	
	private void setWorldHunger(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Hunger", true)) || itemstack.equals(createBooleanItem("Hunger", false))) {
			addItem(13, createBooleanItem("Hunger", !Config.hasWorldHunger(player.getWorld().getName())), (itemstack1, player1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldHunger(player.getWorld().getName(), !Config.hasWorldHunger(player.getWorld().getName()));
		}
	}
	
	private void setWorldProtection(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("Protection", true)) || itemstack.equals(createBooleanItem("Protection", false))) {
			addItem(15, createBooleanItem("Protection", !Config.hasWorldProtection(player.getWorld().getName())), (itemstack1, player1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldProtection(player.getWorld().getName(), !Config.hasWorldProtection(player.getWorld().getName()));
		}
	}
	
	private void setWorldPVP(ItemStack itemstack, Player player) {
		if(itemstack.equals(createBooleanItem("PVP", true)) || itemstack.equals(createBooleanItem("PVP", false))) {
			addItem(17, createBooleanItem("PVP", !Config.hasWorldPVP(player.getWorld().getName())), (itemstack1, player1) -> {setWorldDamage(itemstack, player);});
			Config.setWorldPVP(player.getWorld().getName(), !Config.hasWorldPVP(player.getWorld().getName()));
		}
	}

}
