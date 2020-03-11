package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import serversystem.config.EconomyConfig;
import serversystem.handler.MenuHandler;
import serversystem.utilities.ChatMessage;
import serversystem.utilities.PlayerInventory;

public class BankMenu extends PlayerInventory {

	public BankMenu(Player player, int number, String name) {
		super(player, 45, "Bank Account");
		open();
		MenuHandler.addInventory(this);
		mainBankAccount();
	}

	public void mainBankAccount() {
		clear();
		setItem(createItem("balance", Material.BOOK), 20);
		setItem(createItem("transfer to...", Material.GOLD_INGOT), 22);
		setItem(createItem("credit", Material.DIAMOND), 24);
		setItem(createItem("back", Material.STICK), 44);
	}
	
	public void mainBankCredit() {
		clear();
		setItem(createItem("get 500$", Material.IRON_NUGGET), 10);
		setItem(createItem("get 2.500$", Material.IRON_INGOT), 12);
		setItem(createItem("get 5.000$", Material.GOLD_NUGGET), 14);
		setItem(createItem("get 10.000$", Material.GOLD_INGOT), 16);
		setItem(createItem("payback 500$", Material.IRON_NUGGET), 28);
		setItem(createItem("payback 2.500$", Material.IRON_INGOT), 30);
		setItem(createItem("payback 5.000$", Material.GOLD_NUGGET), 32);
		setItem(createItem("payback 10.000$", Material.GOLD_INGOT), 34);
		setItem(createItem("back", Material.STICK), 44);
	}
	
	@Override
	public void onItemClicked(Inventory inventory, ItemStack item, Player player, int slot) {
		if (item.equals(createItem("balance", Material.BOOK))) {
			ChatMessage.sendServerMessage(player, "Bank-Account: " + EconomyConfig.loadBankAccount(player));
			ChatMessage.sendServerMessage(player, "Credit-Value: " + EconomyConfig.loadCreditValue(player));
		}
		else if (item.equals(createItem("transfer to...", Material.GOLD_INGOT))) {
			ChatMessage.sendServerMessage(player, "Please use /transfer <name> <amount>"); //grund der ueberweisung
		}
		else if (item.equals(createItem("credit", Material.DIAMOND))) {
			mainBankCredit();
		}
			else if (item.equals(createItem("get 500$", Material.IRON_NUGGET))) {
				if (EconomyConfig.loadCreditValue(player) <= -10000) {
					bankPrintMessage(player, "You have reached your max Credit-Value of 10.000$");
				}
				else if (EconomyConfig.loadCreditValue(player) < -9500) {
					bankPrintMessage(player, "Your Credit is smaller than 500$");
				}
				else {
					EconomyConfig.saveCreditValue(player, -500);
					EconomyConfig.saveBankAccount(player, 500);
					bankPrintMessage(player, "Your Credit transfer was succesfull");
				}
			}
				else if (item.equals(createItem("get 2.500$", Material.IRON_INGOT))) {
					if (EconomyConfig.loadCreditValue(player) <= -10000) {
						bankPrintMessage(player, "You have reached your max Credit-Value of 10.000$");
					}
					else if (EconomyConfig.loadCreditValue(player) < -7500) {
						bankPrintMessage(player, "Your Credit is smaller than 2.500$");
					}
					else {
						EconomyConfig.saveCreditValue(player, -2500);
						EconomyConfig.saveBankAccount(player, 2500);
						bankPrintMessage(player, "Your Credit transfer was succesfull");
					}
				}
				else if (item.equals(createItem("get 5.000$", Material.GOLD_NUGGET))) {
					if (EconomyConfig.loadCreditValue(player) <= -10000) {
						ChatMessage.sendServerMessage(player, "Bank: You have reached your max Credit-Value of 10.000$");
					}
					else if (EconomyConfig.loadCreditValue(player) < -5000) {
						ChatMessage.sendServerMessage(player, "Bank: Your Credit is smaller than 5.000$");
					}
					else {
						EconomyConfig.saveCreditValue(player, -5000);
						EconomyConfig.saveBankAccount(player, 5000);
						ChatMessage.sendServerMessage(player, "Bank: Your Credit transfer was succesfull");
					}
				}
				else if (item.equals(createItem("get 10.000$", Material.GOLD_INGOT))) {
					if (EconomyConfig.loadCreditValue(player) <= -10000) {
						ChatMessage.sendServerMessage(player, "Bank: You have reached your max Credit-Value of 10.000$");
					}
					else if (EconomyConfig.loadCreditValue(player) < -10000) {
						ChatMessage.sendServerMessage(player, "Bank: Your Credit is smaller than 10.000$");
					}
					else {
						EconomyConfig.saveCreditValue(player, -10000);
						EconomyConfig.saveBankAccount(player, 10000);
						ChatMessage.sendServerMessage(player, "Bank: Your Credit transfer was succesfull");
					}
				}
				else if (item.equals(createItem("payback 500$", Material.IRON_NUGGET))) {
					if (EconomyConfig.loadCreditValue(player) == 0) {
						ChatMessage.sendServerMessage(player, "Bank: You dont have a Credit");
					}
					else if (EconomyConfig.loadCreditValue(player) > -500) {
						ChatMessage.sendServerMessage(player, "Bank: Your Credit is smaller than 500$");
					}
					else {
						EconomyConfig.saveCreditValue(player, 500);
						EconomyConfig.saveBankAccount(player, -500);
						ChatMessage.sendServerMessage(player, "Bank: Your Payback was succesfull");
					}
				}
				else if (item.equals(createItem("payback 2.500$", Material.IRON_INGOT))) {
					if (EconomyConfig.loadCreditValue(player) == 0) {
						ChatMessage.sendServerMessage(player, "Bank: You dont have a Credit");
					}
					else if (EconomyConfig.loadCreditValue(player) > -2500) {
						ChatMessage.sendServerMessage(player, "Bank: Your Credit is smaller than 2.500$");
					}
					else {
						EconomyConfig.saveCreditValue(player, 2500);
						EconomyConfig.saveBankAccount(player, -2500);
						ChatMessage.sendServerMessage(player, "Bank: Your Payback was succesfull");
					}
				}
				else if (item.equals(createItem("payback 5.000$", Material.GOLD_NUGGET))) {
					if (EconomyConfig.loadCreditValue(player) == 0) {
						ChatMessage.sendServerMessage(player, "Bank: You dont have a Credit");
					}
					else if (EconomyConfig.loadCreditValue(player) > -5000) {
						ChatMessage.sendServerMessage(player, "Bank: Your Credit is smaller than 5.000$");
					}
					else {
						EconomyConfig.saveCreditValue(player, 5000);
						EconomyConfig.saveBankAccount(player, -5000);
						ChatMessage.sendServerMessage(player, "Bank: Your Payback was succesfull");
					}
				}
				else if (item.equals(createItem("payback 10.000$", Material.GOLD_INGOT))) {
					if (EconomyConfig.loadCreditValue(player) == 0) {
						ChatMessage.sendServerMessage(player, "Bank: You dont have a Credit");
					}
					else if (EconomyConfig.loadCreditValue(player) > -10000) {
						ChatMessage.sendServerMessage(player, "Bank: Your Credit is smaller than 10.000$");
					}
					else {
						EconomyConfig.saveCreditValue(player, 10000);
						EconomyConfig.saveBankAccount(player, -10000);
						ChatMessage.sendServerMessage(player, "Bank: Your Payback was succesfull");
					}
				}
				else if (item.equals(createItem("back", Material.STICK))) {
					new WalletMenu(player);
				}
	}
	
	

	private void bankPrintMessage(Player player, String message) {
		ChatMessage.sendServerMessage(player, "Bank: " + message);
	}
}