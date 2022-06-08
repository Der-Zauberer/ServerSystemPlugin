package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PlayerInventory;
import serversystem.utilities.WorldGroup;

public class WorldMenu extends PlayerInventory {
	
	public WorldMenu(Player player, World world) {
		super(player, 4, "World: " + world.getName());
		setFixed(true);
		setItem(0, new ExtendedItemStack("Damage", Material.SHIELD));
		setItem(2, new ExtendedItemStack("Hunger", Material.COOKED_BEEF));
		setItem(4, new ExtendedItemStack("PVP", Material.IRON_SWORD));
		setItem(6, new ExtendedItemStack("Explosion", Material.TNT));
		setItem(8, new ExtendedItemStack("Protection", Material.GOLDEN_PICKAXE));
		createBooleanItem(9, world, WorldOption.DAMAGE);
		createBooleanItem(11, world, WorldOption.HUNGER);
		createBooleanItem(13, world, WorldOption.PVP);
		createBooleanItem(15, world, WorldOption.EXPLOSION);
		createBooleanItem(17, world, WorldOption.PROTECTION);
		setItem(27, new ExtendedItemStack(world.getName(), Material.ZOMBIE_HEAD));
		setItem(31, new ExtendedItemStack("Back", Material.SPECTRAL_ARROW), event -> new WorldListMenu(player).open());
		setItem(35, new ExtendedItemStack("Teleport to " + world.getName(), Material.ENDER_PEARL), event -> WorldGroup.teleportPlayer(player, world));
	}
	
	private void createBooleanItem(int slot, World world, WorldOption option) {
		final String name = option.toString().substring(0, 1).toUpperCase() + option.toString().substring(1).toLowerCase();
		setItem(slot, new ExtendedItemStack(name, (Config.getWorldOption(world.getName(), option)) ? Material.GREEN_DYE : Material.RED_DYE), event -> {
			Config.setWorldOption(world.getName(), option, invertItemStack(event.getCurrentItem(), slot, Config.getWorldOption(world.getName(), option)));
		});
	}
	
	private boolean invertItemStack(ItemStack itemStack, int slot, boolean option) {
		this.setItem(slot, new ExtendedItemStack(itemStack.getItemMeta().getDisplayName(), (!option) ? Material.GREEN_DYE : Material.RED_DYE), this.getInventoryClickAction(slot));
		return !option;
	}

}
