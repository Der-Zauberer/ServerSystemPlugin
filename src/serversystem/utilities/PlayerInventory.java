package serversystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
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
		ItemStack itemstack = new ItemStack(material);
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(name);
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	
	public ItemStack createPotionItem(String name, Color color, PotionEffectType effect) {
		ItemStack itemstack = new ItemStack(Material.POTION);
		PotionMeta potionmeta = (PotionMeta) itemstack.getItemMeta();
		potionmeta.setDisplayName(name);
		potionmeta.setColor(color);
		potionmeta.addCustomEffect(new PotionEffect(effect, 3600, 2), true);
		itemstack.setItemMeta(potionmeta);
		return itemstack;
	}
	
	public ItemStack createBooleanItem(String name, boolean defaults) {
		ItemStack itemstack;
		ItemMeta itemmeta;
		if(defaults) {
			itemstack = new ItemStack(Material.GREEN_DYE);
			itemmeta = itemstack.getItemMeta();
			itemmeta.setDisplayName(name + ": True");
		} else {
			itemstack = new ItemStack(Material.RED_DYE);
			itemmeta = itemstack.getItemMeta();
			itemmeta.setDisplayName(name + ": False");
		}
		itemstack.setItemMeta(itemmeta);
		return itemstack;
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

	public void onItemClicked(ItemStack item, Player player, int slot) {
		
	}

}
