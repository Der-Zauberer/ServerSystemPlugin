package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import serversystem.entities.ServerGroup;
import serversystem.utilities.ChatUtil;

public class PermissionReloadCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && !((Player) sender).hasPermission("serversystem.command.permission")) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		} else if (args.length != 0) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		} else {
			ServerGroup.reloadAll();
			ChatUtil.sendMessage(sender, "Reloaded all permissions!");
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<String>();
	}

}
