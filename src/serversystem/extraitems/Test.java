package serversystem.extraitems;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Test {
	
	private static ItemStack itemstack = new ItemStack(Material.WOODEN_SWORD);
	
	public Test() {
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName("Ultra Sword");
		itemstack.setItemMeta(itemmeta);
	}

}
