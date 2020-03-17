package serversystem.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.ItemMenu;

public class PlayersMenu extends ItemMenu {
	
	public PlayersMenu() {
		int i = 0;
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player) -> {getPlayerInventory().setItemMenu(new AdminMenu());});
		for (Player player : Bukkit.getOnlinePlayers()) {
			if(i < 26) {
				addItem(i, createPlayerSkullItem(player.getName(), player), (itemstack, player1) -> {setPlayer(itemstack, player);});
				i++;
			} else {
				return;
			}
		}
	}
	
	private void setPlayer(ItemStack itemstack, Player player) {
		String name = itemstack.getItemMeta().getDisplayName();
		if(Bukkit.getPlayer(name) != null) {
			getPlayerInventory().setItemMenu(new PlayerMenu(Bukkit.getPlayer(name)));
		}
	}

}
