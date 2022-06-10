package serversystem.utilities;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import serversystem.actions.ItemInteractAction;

public class ExtendedItemStack extends ItemStack implements Listener {
	
	private ItemInteractAction interactAction;
	private ItemInteractAction leftClickAction;
	private ItemInteractAction rightClickAction;
	
	private static final ExtendedItemStack instance = new ExtendedItemStack();
	private static final ArrayList<NamespacedKey> namespacedKeys = new ArrayList<>();
	private static final ArrayList<ExtendedItemStack> extendedItemStacks = new ArrayList<>();
	
	private ExtendedItemStack() {}
	
	public ExtendedItemStack(String displayName, Material material, int customModelData) {
		super(material);
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + displayName);
		itemMeta.setCustomModelData(customModelData);
		setItemMeta(itemMeta);
	}
	
	public ExtendedItemStack(String displayName, Material material) {
		super(material);
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + displayName);
		setItemMeta(itemMeta);
	}
	
	public ExtendedItemStack setDisplayName(String displayName) {
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET + displayName);
		setItemMeta(itemMeta);
		return this;
	}
	
	public ExtendedItemStack setCustomModelData(int customModelData) {
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.setCustomModelData(customModelData);
		setItemMeta(itemMeta);
		return this;
	}
	
	public ExtendedItemStack setLore(String string) {
		final String[] list = string.split("\n");
		final List<String> lore = new ArrayList<>();
		for (String line : list) lore.add(line);
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.setLore(lore);
		setItemMeta(itemMeta);
		return this;
	}
	
	public ExtendedItemStack setLore(List<String> lore) {
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.setLore(lore);
		setItemMeta(itemMeta);
		return this;
	}
	
	public ExtendedItemStack removeLore() {
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.setLore(new ArrayList<String>());
		setItemMeta(itemMeta);
		return this;
	}
	
	public ExtendedItemStack addItemFlag(ItemFlag flag) {
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.addItemFlags(flag);
		setItemMeta(itemMeta);
		return this;
	}
	
	public ExtendedItemStack removeItemFlag(ItemFlag flag) {
		final ItemMeta itemMeta = getItemMeta();
		itemMeta.removeItemFlags(flag);
		setItemMeta(itemMeta);
		return this;
	}
	
	public ExtendedItemStack addPotionMeta(Color color, PotionEffect effect) {
		if(getType() == Material.POTION) {
			final PotionMeta potionMeta = (PotionMeta) getItemMeta();
			potionMeta.setColor(color);
			potionMeta.addCustomEffect(effect, true);
			setItemMeta(potionMeta);
		}
		return this;
	}
	
	public ExtendedItemStack addPlayerSkullMeta(Player player) {
		if(getType() == Material.PLAYER_HEAD) {
			final SkullMeta skullMeta = (SkullMeta) getItemMeta();
			skullMeta.setOwningPlayer(player);
			setItemMeta(skullMeta);
		}
		return this;
	}
	
	public void setInteractAction(ItemInteractAction interactAction) {
		this.interactAction = interactAction;
	}
	
	public ItemInteractAction getInteractAction() {
		return interactAction;
	}
	
	public void setLeftClickAction(ItemInteractAction leftClickAction) {
		this.leftClickAction = leftClickAction;
	}
	
	public ItemInteractAction getLeftClickAction() {
		return leftClickAction;
	}
	
	public void setRightClickAction(ItemInteractAction rightClickAction) {
		this.rightClickAction = rightClickAction;
	}
	
	public ItemInteractAction getRightClickAction() {
		return rightClickAction;
	}
	
	public static void registerItem(ExtendedItemStack item) {
		extendedItemStacks.add(item);
	}
	
	public static ArrayList<ExtendedItemStack> getItems() {
		return extendedItemStacks;
	}
	
	public static ArrayList<ItemStack> getItemsAsItemStack() {
		final ArrayList<ItemStack> itemStacks = new ArrayList<>();
		extendedItemStacks.forEach(item -> itemStacks.add(item));
		return itemStacks;
	}
	
	public static void registerNameSpaceKey(NamespacedKey namespacedKey) {
		namespacedKeys.add(namespacedKey);
	}
	
	public static ArrayList<NamespacedKey> getNamespacedKeys() {
		return namespacedKeys;
	}
	
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event) {
		final ExtendedItemStack itemStack;																																						
		if ((itemStack = getItem(event.getItem())) != null) {
			if (itemStack.getInteractAction() != null) itemStack.getInteractAction().onAction(event);
			if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (itemStack.getLeftClickAction() != null) itemStack.getLeftClickAction().onAction(event);
			} else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (itemStack.getRightClickAction() != null) itemStack.getRightClickAction().onAction(event);
			}
		}
	}
	
	public static ExtendedItemStack getItem(ItemStack itemStack) {
		for (ExtendedItemStack item : extendedItemStacks) {
			if (itemStack != null && item.getType() == itemStack.getType() && itemStack.hasItemMeta() && itemStack.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == itemStack.getItemMeta().getCustomModelData()) {
				return item;
			}
		}
		return null;
	}
	
	public static boolean isItem(ItemStack itemstack, Material material, int custommodeldata) {
		if(itemstack != null && itemstack.getType() == material && itemstack.hasItemMeta() && itemstack.getItemMeta().hasCustomModelData() && itemstack.getItemMeta().getCustomModelData() == custommodeldata) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ExtendedItemStack getInstance() {
		return instance;
	}
	
}
