package serversystem.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.utilities.PlayerInventory;

public class SettingsMenu extends PlayerInventory {

	public SettingsMenu(Player player) {
		super(player, 36, "Settings");
		setItemOption(ItemOption.FIXED);
		setItem(1, createItem("JoinMessage", Material.OAK_SIGN), (itemstack) -> {});
		setItem(3, createItem("LeaveMessage", Material.OAK_SIGN), (itemstack) -> {});
		setItem(5, createItem("DefaultGamemode", Material.IRON_PICKAXE), (itemstack) -> {});
		setItem(7, createItem("Gamemode", Material.IRON_PICKAXE), (itemstack) -> {});
		setItem(10, createBooleanItem("JoinMessage", Config.isJoinMessageActiv()), (itemstack) -> {setJoinMessage();});
		setItem(12, createBooleanItem("LeaveMessage", Config.isLeaveMessageActiv()), (itemstack) -> {setLeaveMessage();});
		setItem(31, createItem("Back", Material.SPECTRAL_ARROW), (itemstack) -> {new AdminMenu(player).open();});
	}
	
	private void setJoinMessage() {
		setItem(10, createBooleanItem("JoinMessage", !Config.isJoinMessageActiv()), (itemstack) -> {setJoinMessage();});
		Config.setJoinMessageActive(!Config.isJoinMessageActiv());
	}
	
	private void setLeaveMessage() {
		setItem(10, createBooleanItem("LeaveMessage", !Config.isLeaveMessageActiv()), (itemstack) -> {setLeaveMessage();});
		Config.setLeaveMessageActive(!Config.isLeaveMessageActiv());
	}
	
}
