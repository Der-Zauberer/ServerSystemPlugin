package serversystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInventory {
	
	private Inventory inventory;
	private Player player;
	
	public PlayerInventory(Player player, int number, String name) {
		this.player = player;
		inventory = Bukkit.createInventory(player, number, name);
	}
	
	public ItemStack createItem(String name, Material material) {
		return new ItemBuilder(name, material).buildItem();
	}
	
	public ItemStack createPotionItem(String name, Color color, PotionEffectType potioneffect) {
		ItemBuilder itembuilder = new ItemBuilder(name, Material.POTION);
		itembuilder.buildItem();
		itembuilder.addPotionMeta(color, new PotionEffect(potioneffect, 3600, 2));
		return itembuilder.getItemStack();
	}
	
	public ItemStack createBooleanItem(String name, boolean defaults) {
		ItemBuilder itembuilder;
		if(defaults) {
			itembuilder = new ItemBuilder(name + ": True", Material.GREEN_DYE);
		} else {
			itembuilder = new ItemBuilder(name + ": False", Material.RED_DYE);
		}
		return itembuilder.buildItem();
	}
	
	public void setItem(ItemStack item, int position) {
		inventory.setItem(position, item);
	}
	
	public void open() {
		player.openInventory(inventory);
	}
	
	public void close() {
		player.closeInventory();
	}
	
	public void clear() {
		inventory.clear();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public void onItemClicked(Inventory inventory, ItemStack item, Player player, int slot) {}

}
