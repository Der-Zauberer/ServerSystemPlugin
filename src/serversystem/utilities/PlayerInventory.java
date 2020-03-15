package serversystem.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import serversystem.handler.InventoryHandler;

public class PlayerInventory {
	
	public enum ItemOption{DRAGABLE, GETABLE, FIXED};
	
	private Inventory inventory;
	private ItemOption itemoption;
	private Player player;
	private InventoryMenu inventorymenu;
	
	public PlayerInventory(Player player, int number, String name) {
		this.player = player;
		inventory = Bukkit.createInventory(player, number, name);
		InventoryHandler.addInventory(this);
	}
	
	public static ItemStack createItem(String name, Material material) {
		return new ItemBuilder(name, material).buildItem();
	}
	
	public static ItemStack createPotionItem(String name, Color color, PotionEffectType potioneffect) {
		ItemBuilder itembuilder = new ItemBuilder(name, Material.POTION);
		itembuilder.buildItem();
		itembuilder.addPotionMeta(color, new PotionEffect(potioneffect, 3600, 2));
		return itembuilder.getItemStack();
	}
	
	public static ItemStack createBooleanItem(String name, boolean defaults) {
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
	
	public void setInventoryMenu(InventoryMenu inventorymenu) {
		inventory.clear();
		this.inventorymenu = inventorymenu;
		inventorymenu.setPlayerInventory(this);
		for(ItemStack itemstack : inventorymenu.getItems().keySet()) {
			setItem(itemstack, inventorymenu.getItems().get(itemstack));
		}
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
	
	public void onItemClicked(ItemStack itemstack, Player player) {
		inventorymenu.onItemClick(itemstack, player);
	}

	public void setItemOption(ItemOption itemoption) {
		this.itemoption = itemoption;
	}
	
	public ItemOption getItemOption() {
		return itemoption;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
}
