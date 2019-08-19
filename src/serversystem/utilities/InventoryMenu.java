package serversystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InventoryMenu implements Listener{
	
	private Inventory inventory;
	private Player player;
	
	public InventoryMenu(Player player, int number,  String name) {
		this.player = player;
		inventory = Bukkit.createInventory(player, number, name);
	}
	
	public ItemStack addItem(String name, Material material, int position) {
		ItemStack itemstack = new ItemStack(material);
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(name);
		itemstack.setItemMeta(itemmeta);
		inventory.setItem(position, itemstack);
		return itemstack;
	}
	
	public ItemStack addItem(String name, Color color, PotionEffectType effect, int position) {
		ItemStack itemstack = new ItemStack(Material.POTION);
		PotionMeta potionmeta = (PotionMeta) itemstack.getItemMeta();
		potionmeta.setDisplayName(name);
		potionmeta.setColor(color);
		potionmeta.addCustomEffect(new PotionEffect(effect, 3600, 2), true);
		itemstack.setItemMeta(potionmeta);
		inventory.setItem(position, itemstack);
		return itemstack;
	}
	
	public void open() {
		player.openInventory(inventory);
	}
	
	public void close() {
		player.closeInventory();
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

}
