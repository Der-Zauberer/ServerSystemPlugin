package serversystem.extraitems;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Test {
	
	private static ItemStack itemstack = new ItemStack(Material.CARROT_ON_A_STICK);
	
	public static ItemStack getItem() {
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName("§rUltra Sword");
		itemmeta.setLore(Arrays.asList("Type: Weapon"));
		itemmeta.setCustomModelData(1);
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}

}
