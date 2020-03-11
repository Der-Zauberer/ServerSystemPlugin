package serversystem.utilities;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class ItemBuilder {
	
	private String displayname;
	private Material material;
	private List<String> lore;
	private int custommodeldata;
	private ItemStack itemstack;
	
	public ItemBuilder() {
		displayname = ChatColor.RESET + "Example Soward";
		material = Material.WOODEN_SWORD;
		custommodeldata = 0;
	}
	
	public ItemBuilder(Material material) {
		displayname = ChatColor.RESET + "Example Soward";
		this.material = material;
		custommodeldata = 0;
	}
	
	public ItemBuilder(String displayname) {
		this.displayname = displayname;
		material = Material.WOODEN_SWORD;
		custommodeldata = 0;
	}
	
	public ItemBuilder(String displayname, Material material) {
		this.displayname = displayname;
		this.material = material;
	}
	
	public void setDisplayName(String displayname) {
		this.displayname = displayname;
	}
	
	public String getDisplayName() {
		return displayname;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setLore(List<String> lore) {
		this.lore = lore;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public void setCustomModelData(int costummodeldata) {
		this.custommodeldata = costummodeldata;
	}
	
	public int getCustommodeldata() {
		return custommodeldata;
	}
	
	public void addPotionMeta(Color color, PotionEffect potioneffect) {
		if(itemstack != null && material == Material.POTION) {
			PotionMeta potionmeta = (PotionMeta) itemstack.getItemMeta();
			potionmeta.setColor(color);
			potionmeta.addCustomEffect(potioneffect, true);
			itemstack.setItemMeta(potionmeta);
		}
	}
	
	public ItemStack buildItem() {
		ItemStack itemstack = new ItemStack(material);
		ItemMeta itemmeta = itemstack.getItemMeta();
		itemmeta.setDisplayName(displayname);
		if(lore != null) {
			itemmeta.setLore(lore);
		}
		itemmeta.setCustomModelData(custommodeldata);
		itemstack.setItemMeta(itemmeta);
		this.itemstack = itemstack;
		return itemstack;
	}
	
	public ItemMeta getItemMeta() {
		if(itemstack == null) {
			return buildItem().getItemMeta();
		}
		return itemstack.getItemMeta();
	}
	
	public ItemStack getItemStack() {
		if(itemstack == null) {
			return buildItem();
		}
		return itemstack;
	}

}
