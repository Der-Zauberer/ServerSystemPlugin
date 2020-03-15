package serversystem.utilities;

import java.util.HashMap;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class InventoryMenu {
	
	private PlayerInventory playerinventory;
	private HashMap<ItemStack, Integer> slots = new HashMap<>();
	private HashMap<ItemStack, ActionEvent> events = new HashMap<>();
	
	public static ItemStack createItem(String name, Material material) {
		return PlayerInventory.createItem(name, material);
	}
	
	public static ItemStack createPotionItem(String name, Color color, PotionEffectType potioneffect) {
		return PlayerInventory.createPotionItem(name, color, potioneffect);
	}
	
	public static ItemStack createBooleanItem(String name, boolean defaults) {
		return PlayerInventory.createBooleanItem(name, defaults);
	}
	
	public void addItem(int slot, ItemStack itemstack, ActionEvent event) {
		slots.put(itemstack, slot);
		events.put(itemstack, event);
		if(playerinventory != null) {
			playerinventory.setItem(itemstack, slot);
		}
	}
	
	public void addItem(int slot, ItemStack itemstack) {
		slots.put(itemstack, slot);
		if(playerinventory != null) {
			playerinventory.setItem(itemstack, slot);
		}
	}
	
	public void onItemClick(ItemStack itemstack, Player player) {
		if(events.containsKey(itemstack)) {
			events.get(itemstack).executeOnAction(itemstack ,player);
		}
	}
	
	public HashMap<ItemStack, Integer> getItems() {
		return slots;
	}
	
	public PlayerInventory getPlayerInventory() {
		return playerinventory;
	}
	
	public void setPlayerInventory(PlayerInventory playerinventory) {
		this.playerinventory = playerinventory;
	}

}
