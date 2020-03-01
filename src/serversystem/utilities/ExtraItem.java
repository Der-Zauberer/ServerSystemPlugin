package serversystem.utilities;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class ExtraItem extends ItemStack {
	
	private String displayname;
	private String id;
	private List<String> description;
	
	public ExtraItem(String id, Material material) {
		super(material);
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setDisplayName(String displayname) {
		this.displayname = displayname;
		ItemMeta itemmeta = this.getItemMeta();
		itemmeta.setDisplayName(displayname);
		this.setItemMeta(itemmeta);
	}
	
	public String getDisplayName() {
		ItemMeta itemmeta = this.getItemMeta();
		this.displayname = itemmeta.getDisplayName();
		return ChatColor.stripColor(displayname);
	}
	
	public void setDescription(List<String> description) {
		this.description = description;
		ItemMeta itemmeta = this.getItemMeta();
		itemmeta.setLore(description);
		this.setItemMeta(itemmeta);
	}
	
	public List<String> getDescription() {
		ItemMeta itemmeta = this.getItemMeta();
		this.description = itemmeta.getLore();
		return description;
	}
	
	public abstract void onItemInteract(PlayerInteractEvent event, Player player);

}
