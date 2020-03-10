package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import serversystem.handler.MenuHandler;
import serversystem.utilities.PlayerInventory;

public class WalletMenu extends PlayerInventory{

	public WalletMenu(Player player) {
		super(player, 45, player.getName() + "'s Wallet");
		open();
		MenuHandler.addInventory(this);
		openMainWallet();
	}
	
	public void openMainWallet() {
		clear();
		setItem(createItem("Passport", Material.BOOK), 19);
		setItem(createItem("Bank Account", Material.ENCHANTED_BOOK), 21);
		setItem(createItem("Health Indurance", Material.RED_BED), 23);
		setItem(createItem("Discount Card", Material.GOLD_NUGGET), 25);
	}
	
	public void mainPassport() {
		clear();
		setItem(createItem("show Passport", Material.BOOK), 20);
		setItem(createItem("show other Passport", Material.ENCHANTED_BOOK), 24);
		setItem(createItem("back", Material.STICK), 44);
	}
	
	public void mainHealthInsurance() {
		clear();
		
		setItem(createItem("back", Material.STICK), 44);
	}
	
	public void mainDiscountCard() {
		clear();
		
		setItem(createItem("back", Material.STICK), 44);
	}

	@Override
	public void onItemClicked(Inventory inventory, ItemStack item, Player player, int slot) {
		//Passport
		if (item.equals(createItem("Passport", Material.BOOK))) {
			
		}
		//Bank Account
		else if (item.equals(createItem("Bank Account", Material.ENCHANTED_BOOK))) {
			new BankMenu(player, slot, null);
		}
		//Health Insurance
		else if (item.equals(createItem("Health Insurance", Material.RED_BED))) {
			
		}
		//Discount Card
		else if (item.equals(createItem("Discount Card", Material.GOLD_NUGGET))) {
			
		}
		//utilities
		else if (item.equals(createItem("back", Material.STICK))) {
			openMainWallet();
		}
	}
	
}
