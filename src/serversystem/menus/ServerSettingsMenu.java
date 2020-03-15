package serversystem.menus;

import org.bukkit.Material;
import serversystem.config.Config;
import serversystem.utilities.InventoryMenu;

public class ServerSettingsMenu extends InventoryMenu {

	public ServerSettingsMenu() {
		addItem(1, createItem("JoinMessage", Material.OAK_SIGN), (itemstack, player) -> {});
		addItem(3, createItem("LeaveMessage", Material.OAK_SIGN), (itemstack, player) -> {});
		addItem(5, createItem("DefaultGamemode", Material.IRON_PICKAXE), (itemstack, player) -> {});
		addItem(7, createItem("Gamemode", Material.IRON_PICKAXE), (itemstack, player) -> {});
		addItem(10, createBooleanItem("JoinMessage", Config.isJoinMessageActiv()), (itemstack, player) -> {setJoinMessage();});
		addItem(12, createBooleanItem("LeaveMessage", Config.isLeaveMessageActiv()), (itemstack, player) -> {setLeaveMessage();});
		addItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack, player) -> {getPlayerInventory().setInventoryMenu(new AdminMenu());});
	}
	
	private void setJoinMessage() {
		addItem(10, createBooleanItem("JoinMessage", !Config.isJoinMessageActiv()), (itemstack, player) -> {setJoinMessage();});
		Config.setJoinMessageActive(!Config.isJoinMessageActiv());
	}
	
	private void setLeaveMessage() {
		addItem(10, createBooleanItem("LeaveMessage", !Config.isLeaveMessageActiv()), (itemstack, player) -> {setLeaveMessage();});
		Config.setLeaveMessageActive(!Config.isLeaveMessageActiv());
	}
	
}
