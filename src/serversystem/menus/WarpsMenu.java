package serversystem.menus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.PlayerInventory;
import serversystem.utilities.ServerWarp;

public class WarpsMenu extends PlayerInventory {

	public WarpsMenu(Player player) {
		super(player, calculateSize(ServerWarp.getWarps().size()), "Warps");
		setItemOption(ItemOption.FIXED);
		int i = 0;
		for (ServerWarp warp : ServerWarp.getWarps(player)) {
			if (i < 45) {
				if (warp.isGlobal() || warp.getLocation().getWorld() == player.getWorld()) {
					setItem(i, createItem(ChatColor.RESET + warp.getName(), warp.getMaterial()), (itemstack) -> teleportPlayer(player, itemstack));
				}
				i++;
			} else {
				return;
			}
		}
	}
	
	private void teleportPlayer(Player player, ItemStack itemstack) {
		ServerWarp warp = ServerWarp.getWarp(ChatColor.stripColor(itemstack.getItemMeta().getDisplayName()));
		if (warp != null) {
			player.teleport(warp.getLocation());
		}
	}
	
}
