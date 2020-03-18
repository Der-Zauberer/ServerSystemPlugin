package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import serversystem.config.EconomyConfig;
import serversystem.handler.MenuHandler;
import serversystem.utilities.ChatMessage;
import serversystem.utilities.PlayerInventory;

public class CasinoMenu extends PlayerInventory{

	public CasinoMenu(Player player) {
		super(player, 45, "Online-Casino");
		open();
		MenuHandler.addInventory(this);
		openMainCasinoMenu();
	}
	
	//Premium Version with higher bets
	
	public void openMainCasinoMenu() {
		clear();
		setItem(createItem("Horse Races", Material.GOLDEN_HORSE_ARMOR), 19);
		setItem(createItem("buy Premium-Membership for 50.000$", Material.DIAMOND), 44);
	}
	
	public void horseRaces() {
		clear();
		setItem(createItem("Horse 1", Material.GREEN_STAINED_GLASS_PANE), 10);
		setItem(createItem("Horse 2", Material.BLACK_STAINED_GLASS_PANE), 12);
		setItem(createItem("Horse 3", Material.BROWN_STAINED_GLASS_PANE), 14);
		setItem(createItem("Horse 4", Material.CYAN_STAINED_GLASS_PANE), 16);
		setItem(createItem("Horse 5", Material.GRAY_STAINED_GLASS_PANE), 19);
		setItem(createItem("Horse 6", Material.YELLOW_STAINED_GLASS_PANE), 21);
		setItem(createItem("Horse 7", Material.RED_STAINED_GLASS_PANE), 23);
		setItem(createItem("Horse 8", Material.WHITE_STAINED_GLASS_PANE), 25);
	}
	
	public void standardChooseBetAmount() {
		clear();
	}
	
	public void premiumChooseBetAmount() {
		clear();
	}
	
	@Override
	public void onItemClicked(Inventory inventory, ItemStack item, Player player, int slot) {
		if (item.equals(createItem("buy Premium-Membership for 50.000$", Material.DIAMOND))) {
			if (EconomyConfig.loadCasinoMembership(player) == false && EconomyConfig.loadBankAccount(player) >= 50000) { 
				EconomyConfig.saveCasinoMembership(player, true);
				EconomyConfig.saveBankAccount(player, -50000);
				ChatMessage.sendServerMessage(player, "Your Premium-Membership Purchase was succesfull!");
			}
			else if (EconomyConfig.loadBankAccount(player) < 50000) {
				ChatMessage.sendServerMessage(player, "You dont have enough money!");
			}
			else if (EconomyConfig.loadCasinoMembership(player) == true){
				ChatMessage.sendServerMessage(player, "You already owned the Premium-Membership!");
			}
		}
		else if (item.equals(createItem("Horse Races", Material.GOLDEN_HORSE_ARMOR))) {
			
		}
		
	}
}
