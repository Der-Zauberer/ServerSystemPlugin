package serversystem.menus;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.ExtendedItemStack;
import serversystem.utilities.PlayerInventory;
import serversystem.utilities.ServerWarp;

public class WarpListMenu extends PlayerInventory {

	public WarpListMenu(Player player) {
		super(player, 4, "Warps");
		setFixed(true);
		final ArrayList<ItemStack> items = new ArrayList<>();
		for (ServerWarp warp : ServerWarp.getWarps(player)) {
			items.add(new ExtendedItemStack(warp.getName(), warp.getMaterial()));
		}
		setItemList(items, event -> teleportPlayer(player, event.getCurrentItem()));
	}
	
	private void teleportPlayer(Player player, ItemStack itemstack) {
		final ServerWarp warp = ServerWarp.getWarp(ChatColor.stripColor(itemstack.getItemMeta().getDisplayName()));
		if (warp != null) player.teleport(warp.getLocation());
	}
	
}
