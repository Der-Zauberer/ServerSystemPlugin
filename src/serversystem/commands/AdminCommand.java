package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import serversystem.menus.AdminMenu;
import serversystem.utilities.CommandAssistant;

import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(new CommandAssistant(sender).isSenderInstanceOfPlayer()) {
			new AdminMenu((Player)sender).open();
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<String>();
	}

}
