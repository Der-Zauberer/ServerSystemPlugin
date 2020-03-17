package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import serversystem.utilities.ItemMenu;

public class PlayerMenu extends ItemMenu {
	
	public PlayerMenu(Player player) {
		addItem(27, createPlayerSkullItem(player.getName(), player));
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player1) -> {getPlayerInventory().setItemMenu(new PlayersMenu());});
		addItem(35, createItem("Teleport to " + player.getName(), Material.ENDER_PEARL), (itemstack, player1) -> {player1.teleport(player);});
	}

}
