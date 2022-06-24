package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.CommandAssistant;

public class InventoryCommand implements CommandExecutor, TabCompleter, CommandAssistant {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		if (!(sender instanceof Player)) ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_PLAYER);
		 else if (args.length < 1) ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		 else if (args.length > 1) ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		 else ((Player) sender).openInventory(Bukkit.getPlayer(args[0]).getInventory());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (getLayer(1, args)) return removeWrong(getPlayerList(sender), args);
		return new ArrayList<>();
	}

}
