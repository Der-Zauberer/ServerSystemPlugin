package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import serversystem.config.EconomyConfig;
import serversystem.utilities.ItemMenu;

public class BankMenu extends ItemMenu {
	
	public BankMenu(Player player) {
		addItem(20, createItem("Balance " + EconomyConfig.getBankAccount(player), Material.BOOK));
		addItem(22, createItem("Money Transfer", Material.GOLD_INGOT), (itemstack, player1) -> {});
		addItem(24, createItem("Credit", Material.DIAMOND), (itemstack, player1) -> {});
		addItem(44, createItem("Credit", Material.SPECTRAL_ARROW), (itemstack, player1) -> {});
	}

}
