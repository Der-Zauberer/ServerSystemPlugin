package serversystem.menus;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PlayerInventory;

public class WorldListMenu extends PlayerInventory {
	
	public WorldListMenu(Player player) {
		super(player, 4, "Worlds");
		setFixed(true);
		
		ArrayList<ItemStack> items = new ArrayList<>();
		for (World world : Bukkit.getWorlds()) {
			items.add(new ExtendedItemStack(world.getName(), Material.ZOMBIE_HEAD));
		}
		setItemList(items, event -> setWorld(event.getCurrentItem(), player), event -> new AdminMenu(player).open());
	}
	
	private void setWorld(ItemStack itemstack, Player player) {
		String name = ChatColor.stripColor(itemstack.getItemMeta().getDisplayName());
		if (Bukkit.getWorld(name) != null) new WorldMenu(player, Bukkit.getWorld(name)).open();
	}

}
