package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import serversystem.menus.AdminMenu;
import serversystem.utilities.ChatUtil;

import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_PLAYER);
		else if (args.length != 0) ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		else new AdminMenu((Player) sender).open();
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<String>();
	}

}
