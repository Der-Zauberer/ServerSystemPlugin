package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import serversystem.handler.ChatMessage;
import serversystem.handler.ChatMessage.ErrorMessage;
import serversystem.menus.AdminMenu;
import serversystem.utilities.PlayerInventory;
import serversystem.utilities.PlayerInventory.ItemOption;

import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor, TabCompleter{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			PlayerInventory playerinventory = new PlayerInventory((Player) sender, 36, "Admin");
			playerinventory.setItemOption(ItemOption.FIXED);
			playerinventory.setItemMenu(new AdminMenu());
			playerinventory.open();
		} else {
			ChatMessage.sendServerErrorMessage(sender, ErrorMessage.ONLYPLAYER);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<String>();
		return commands;
	}

}
