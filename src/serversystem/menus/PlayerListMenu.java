package serversystem.menus;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PlayerInventory;

public class PlayerListMenu extends PlayerInventory {
	
	public PlayerListMenu(Player player) {
		super(player, 4, "Player");
		setFixed(true);
		final ArrayList<ItemStack> items = new ArrayList<>();
		for (Player target : Bukkit.getOnlinePlayers()) {
			items.add(new ExtendedItemStack(target.getDisplayName(), Material.PLAYER_HEAD).addPlayerSkullMeta(target));
		}
		setItemList(items, event -> setPlayer(event.getCurrentItem(), player), event -> new AdminMenu(player).open());
	}
	
	private void setPlayer(ItemStack itemstack, Player player) {
		final String name = ChatColor.stripColor(itemstack.getItemMeta().getDisplayName());
		if (Bukkit.getPlayer(name) != null) {
			new PlayerMenu(player, Bukkit.getPlayer(name)).open();
		}
	}

}
