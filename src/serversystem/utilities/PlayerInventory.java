package serversystem.utilities;

import java.util.HashMap;
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

	public enum ItemOption {
		DRAGABLE,
		GETABLE,
		FIXED
	};

	private Inventory inventory;
	private ItemOption itemoption;
	private Player player;
	private HashMap<ItemStack, ActionEvent> events = new HashMap<>();

	public PlayerInventory(Player player, int number, String name) {
		this.player = player;
		inventory = Bukkit.createInventory(player, number, name);
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

	public static ItemStack createPlayerSkullItem(String name, Player player) {
		ItemBuilder itembuilder = new ItemBuilder(name, Material.PLAYER_HEAD);
		itembuilder.buildItem();
		itembuilder.addPlayerSkullMeta(player);
		return itembuilder.getItemStack();
	}

	public static ItemStack createBooleanItem(String name, boolean defaults) {
		ItemBuilder itembuilder;
		if (defaults) itembuilder = new ItemBuilder(name + " true", Material.GREEN_DYE);
		else itembuilder = new ItemBuilder(name + " false", Material.RED_DYE);
		return itembuilder.buildItem();
	}

	public void setItem(int slot, ItemStack itemstack, ActionEvent event) {
		inventory.setItem(slot, itemstack);
		events.put(itemstack, event);
	}

	public void setItem(int slot, ItemStack itemstack) {
		inventory.setItem(slot, itemstack);
	}

	public void setEvent(ItemStack itemstack, ActionEvent event) {
		events.put(itemstack, event);
	}

	public void onItemClicked(ItemStack itemstack) {
		if (events.containsKey(itemstack)) events.get(itemstack).executeOnAction(itemstack);
	}

	public void open() {
		player.openInventory(inventory);
		InventoryHandler.addInventory(this);
	}

	public void close() {
		player.closeInventory();
		InventoryHandler.removeInventory(this);
	}

	public void clear() {
		inventory.clear();
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

	public static int calculateSize(int slots) {
		int difference = 9 - (slots % 9);
		int size = slots + difference;
		if (size > 54) size = 54;
		return size;
	}

}
