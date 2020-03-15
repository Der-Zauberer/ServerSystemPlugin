package serversystem.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import serversystem.utilities.InventoryMenu;

public class PlayersMenu extends InventoryMenu {
	
	public PlayersMenu() {
		int i = 0;
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player) -> {getPlayerInventory().setInventoryMenu(new AdminMenu());});
		for (Player players : Bukkit.getOnlinePlayers()) {
			if(i < 26) {
				addItem(i, createItem("Player: " + players.getName(), Material.PLAYER_HEAD), (itemstack, player) -> {setPlayer(itemstack, player);});
				i++;
			} else {
				return;
			}
		}
	}
	
	private void setPlayer(ItemStack itemstack, Player player) {
		String playername[] = itemstack.getItemMeta().getDisplayName().split(" ");
		if(playername.length == 2 && Bukkit.getPlayer(playername[1]) != null) {
			getPlayerInventory().setInventoryMenu(new PlayerMenu(Bukkit.getPlayer(playername[1])));
		}
	}

}
