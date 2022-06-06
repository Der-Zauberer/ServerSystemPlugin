package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.config.Config;
import serversystem.handler.WorldGroupHandler;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PlayerInventory;

public class WorldMenu extends PlayerInventory {
	
	public WorldMenu(Player player, World world) {
		super(player, 4, "World: " + world.getName());
		setFixed(true);
		setItem(0, new ExtendedItemStack("Damage", Material.SHIELD));
		setItem(2, new ExtendedItemStack("Explosion", Material.TNT));
		setItem(4, new ExtendedItemStack("Hunger", Material.COOKED_BEEF));
		setItem(6, new ExtendedItemStack("Protection", Material.GOLDEN_PICKAXE));
		setItem(8, new ExtendedItemStack("PVP", Material.IRON_SWORD));
		setItem(9, new ExtendedItemStack("Damage", (Config.hasWorldDamage(world.getName())) ? Material.GREEN_DYE : Material.RED_DYE), event -> {
			Config.setWorldDamage(world.getName(), invertItemStack(event.getCurrentItem(), 9, Config.hasWorldDamage(world.getName())));
		});
		setItem(11, new ExtendedItemStack("Explosion", (Config.hasWorldExplosion(world.getName())) ? Material.GREEN_DYE : Material.RED_DYE), event -> {
			Config.setWorldExplosion(world.getName(), invertItemStack(event.getCurrentItem(), 11, Config.hasWorldExplosion(world.getName())));
		});
		setItem(13, new ExtendedItemStack("Hunger", (Config.hasWorldHunger(world.getName())) ? Material.GREEN_DYE : Material.RED_DYE), event -> {
			Config.setWorldHunger(world.getName(), invertItemStack(event.getCurrentItem(), 13, Config.hasWorldHunger(world.getName())));
		});
		setItem(15, new ExtendedItemStack("Protection", (Config.hasWorldProtection(world.getName())) ? Material.GREEN_DYE : Material.RED_DYE), event -> {
			Config.setWorldProtection(world.getName(), invertItemStack(event.getCurrentItem(), 15, Config.hasWorldProtection(world.getName())));
		});
		setItem(17, new ExtendedItemStack("PVP", (Config.hasWorldPVP(world.getName())) ? Material.GREEN_DYE : Material.RED_DYE), event -> {
			Config.setWorldPVP(world.getName(), invertItemStack(event.getCurrentItem(), 17, Config.hasWorldPVP(world.getName())));
		});
		setItem(27, new ExtendedItemStack(world.getName(), Material.ZOMBIE_HEAD));
		setItem(31, new ExtendedItemStack("Back", Material.SPECTRAL_ARROW), event -> new WorldListMenu(player).open());
		setItem(35, new ExtendedItemStack("Teleport to " + world.getName(), Material.ENDER_PEARL), event -> WorldGroupHandler.teleportPlayer(player, world));
	}
	
	private boolean invertItemStack(ItemStack itemStack, int slot, boolean option) {
		this.setItem(slot, new ExtendedItemStack(itemStack.getItemMeta().getDisplayName(), (!option) ? Material.GREEN_DYE : Material.RED_DYE), this.getInventoryClickAction(slot));
		return !option;
	}

}
