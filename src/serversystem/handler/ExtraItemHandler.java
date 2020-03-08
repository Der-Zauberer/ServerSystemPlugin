package serversystem.handler;

import java.util.ArrayList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.ItemFunction;

public class ExtraItemHandler implements Listener {
	
	private static ArrayList<ItemFunction> itemfunctions = new ArrayList<>();
	
	public static void registerItemFunction(ItemFunction itemfunction) {
		itemfunctions.add(itemfunction);
	}
	
	public static ItemFunction getItemFunction(ItemStack itemstack) {
		for (ItemFunction itemfunction : itemfunctions) {
			if(itemfunction.getType().equals(itemstack.getType())) {
				if(itemfunction.getCustomModelData() == itemstack.getItemMeta().getCustomModelData()) {
					return itemfunction;
				}
			}
		}
		return null;
	}
	
	public static boolean hasItemFunction(ItemStack itemstack) {
		if(getItemFunction(itemstack) != null) {
			return true;
		}
		return false;
	}
	
	public static void executeItemFunction(PlayerInteractEvent event) {

	}
	
}
