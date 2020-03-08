package serversystem.handler;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.ItemFunction;

public class ItemHandler implements Listener {
	
	private static ArrayList<ItemStack> items = new ArrayList<>();
	private static ArrayList<ItemFunction> itemfunctions = new ArrayList<>();
	
	public static void registerItem(ItemStack itemstack) {
		items.add(itemstack);
	}
	
	public static ItemStack getItem(String name) {
		String realname = ChatColor.stripColor(name);
		String namewithoutspaces = realname.replace("_", " ");
		for (ItemStack item : items) {
			String itemname = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			if(itemname.equalsIgnoreCase(name) || itemname.equalsIgnoreCase(namewithoutspaces)) {
				return item;
			}
		}
		return null;
	}
	
	public static void registerItemFunction(ItemFunction itemfunction) {
		itemfunctions.add(itemfunction);
	}
	
	public static ItemFunction getItemFunction(ItemStack itemstack) {
		for (ItemFunction itemfunction : itemfunctions) {
			if(itemfunction.getType().equals(itemstack.getType())) {
				if(itemstack.getItemMeta().hasCustomModelData() && itemfunction.getCustomModelData() == itemstack.getItemMeta().getCustomModelData()) {
					return itemfunction;
				}
			}
		}
		return null;
	}	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		ItemStack itemstack = event.getPlayer().getInventory().getItemInMainHand();
		ItemFunction itemfunction = getItemFunction(itemstack);
		if(itemfunction != null) {
			itemfunction.onItemUse(event.getPlayer(), itemstack, event.getAction());
		}
	}
	
}
