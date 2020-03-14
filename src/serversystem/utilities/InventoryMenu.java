package serversystem.utilities;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryMenu {
	
	private String name;
	private HashMap<ItemStack, Integer> slots = new HashMap<>();
	
	public InventoryMenu(String name) {
		this.name = name;
	}
	
	public void addItem(ItemStack itemstack, int slot) {
		slots.put(itemstack, slot);
	}
	
	public void onItemClick(ItemStack itemstack, Player player) {
		
	}
	
	public HashMap<ItemStack, Integer> getItems() {
		return slots;
	}
	
	public String getName() {
		return name;
	}

}
