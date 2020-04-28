package serversystem.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import serversystem.utilities.ItemMenu;

public class WorldsMenu extends ItemMenu {
	
	public WorldsMenu() {
		for(int i = 0; i < Bukkit.getWorlds().size() && i < 26; i++) {
			addItem(i, createItem(Bukkit.getWorlds().get(i).getName(), Material.ZOMBIE_HEAD), (itemstack, player) -> {setWorld(itemstack);});
		}
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player) -> {getPlayerInventory().setItemMenu(new AdminMenu());});
	}
	
	private void setWorld(ItemStack itemstack) {
		String name = ChatColor.stripColor(itemstack.getItemMeta().getDisplayName());
		if(Bukkit.getWorld(name) != null) {
			getPlayerInventory().setItemMenu(new WorldMenu(Bukkit.getWorld(name)));
		}
	}

}
