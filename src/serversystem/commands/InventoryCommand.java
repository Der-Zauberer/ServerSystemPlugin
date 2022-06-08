package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.utilities.CommandAssistant;

public class InventoryCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		final CommandAssistant assistant = new CommandAssistant(sender);
		if (assistant.isSenderInstanceOfPlayer() && assistant.hasMinArguments(1, args) && assistant.isPlayer(args[0])) {
			((Player) sender).openInventory(Bukkit.getPlayer(args[0]).getInventory());
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		final CommandAssistant assistant = new CommandAssistant(sender);
		List<String> commands = new ArrayList<>();
		if (args.length == 1) commands = assistant.getPlayers();
		commands = assistant.cutArguments(args, commands);
		return commands;
	}

}
