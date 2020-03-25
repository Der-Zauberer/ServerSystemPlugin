package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import serversystem.utilities.ItemMenu;

public class WalletMenu extends ItemMenu {
	
	public WalletMenu(Player player) {
		addItem(19, createItem("Passport", Material.BOOK));
		addItem(21, createItem("Bank Account", Material.ENCHANTED_BOOK));
		addItem(23, createItem("Health Indurance", Material.RED_BED));
		addItem(25, createItem("Discount Card", Material.GOLD_NUGGET));
	}

}
