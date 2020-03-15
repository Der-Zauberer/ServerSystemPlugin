package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import serversystem.utilities.InventoryMenu;

public class PlayerMenu extends InventoryMenu {
	
	public PlayerMenu(Player player) {
		addItem(27, createItem("Player: " + player.getName(), Material.PLAYER_HEAD));
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player1) -> {getPlayerInventory().setInventoryMenu(new PlayersMenu());});
	}

}
