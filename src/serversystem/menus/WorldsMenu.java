package serversystem.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.InventoryMenu;

public class WorldsMenu extends InventoryMenu {
	
	public WorldsMenu() {
		for(int i = 0; i < Bukkit.getWorlds().size() && i < 26; i++) {
			addItem(i, createItem("World: " + Bukkit.getWorlds().get(i).getName(), Material.ZOMBIE_HEAD), (itemstack, player) -> {setWorld(itemstack);});
		}
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player) -> {getPlayerInventory().setInventoryMenu(new AdminMenu());});
	}
	
	private void setWorld(ItemStack itemstack) {
		String worldname[] = itemstack.getItemMeta().getDisplayName().split(" ");
		if(worldname.length == 2 && Bukkit.getWorld(worldname[1]) != null) {
			getPlayerInventory().setInventoryMenu(new WorldMenu(Bukkit.getWorld(worldname[1])));
		}
	}

}
