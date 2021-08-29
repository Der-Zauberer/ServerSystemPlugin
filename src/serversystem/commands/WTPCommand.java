package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.utilities.CommandAssistant;
import serversystem.handler.WorldGroupHandler;
import serversystem.handler.ChatHandler.ErrorMessage;

public class WTPCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		if(assistant.isSenderInstanceOfPlayer()) {
			if(assistant.hasMinArguments(1, args)) {
				if(assistant.isWorld(args[0])) {
					if(!sender.hasPermission("serversystem.command.world.edit") && Config.getWorldPermission(args[0]) != null && !sender.hasPermission(Config.getWorldPermission(args[0]))) {
						ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
					} else {
						WorldGroupHandler.teleportPlayer((Player)sender, Bukkit.getWorld(args[0]));
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		List<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands = assistant.getWorlds();
		}
		assistant.cutArguments(args, commands);
		return commands;
	}

}
