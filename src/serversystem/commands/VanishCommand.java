package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.handler.PlayerVanishHandler;
import serversystem.utilities.CommandAssistant;

public class VanishCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistent = new CommandAssistant(sender);
		if (args.length == 0) {
			if (assistent.isSenderInstanceOfPlayer(true)) {
				PlayerVanishHandler.vanishPlayer((Player) sender);
			}
		} else {
			if (assistent.isPlayer(args[0])) {
				PlayerVanishHandler.vanishPlayer(Bukkit.getPlayer(args[0]), sender);
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		List<String> commands = new ArrayList<>();
		if (args.length == 1) {
			commands = assistant.getPlayers();
		}
		assistant.cutArguments(args, commands);
		return commands;
	}

}
