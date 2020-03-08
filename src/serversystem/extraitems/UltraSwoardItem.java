package serversystem.extraitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.ItemBuilder;

public class UltraSwoardItem {
	
	public ItemStack getItem() {
		ItemBuilder itembuilder = new ItemBuilder();
		itembuilder.setDisplayName(ChatColor.RESET + "UltraSwoard");
		itembuilder.setMaterial(Material.WOODEN_SWORD);
		itembuilder.setCustomModelData(1);
		return itembuilder.buildItem();
	}

}