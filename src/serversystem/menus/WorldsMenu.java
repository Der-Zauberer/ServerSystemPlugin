package serversystem.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;
import serversystem.utilities.PlayerInventory;

public class WorldsMenu extends PlayerInventory {
	
	public WorldsMenu(Player player) {
		super(player, calculateSize(), "Worlds");
		setItemOption(ItemOption.FIXED);
		setItem(calculateSize() - 5, createItem("Back", Material.SPECTRAL_ARROW), (itemstack) -> {new AdminMenu(player).open();});
		for(int i = 0; i < Bukkit.getWorlds().size() && i < 45; i++) {
			setItem(i, createItem(Bukkit.getWorlds().get(i).getName(), Material.ZOMBIE_HEAD), (itemstack) -> {setWorld(itemstack, player);});
		}
	}
	
	private void setWorld(ItemStack itemstack, Player player) {
		String name = ChatColor.stripColor(itemstack.getItemMeta().getDisplayName());
		if(Bukkit.getWorld(name) != null) {
			new WorldMenu(player, Bukkit.getWorld(name)).open();
		}
	}
	
	private static int calculateSize() {
		int worlds = Bukkit.getWorlds().size();
		int difference = 9 - (worlds % 9);
		int size = worlds + difference + 9;
		if(size > 54) {
			size = 54;
		}
		return size;
	}

}
