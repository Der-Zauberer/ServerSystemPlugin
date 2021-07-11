package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.handler.ChatHandler;
import serversystem.utilities.CommandAssistant;
import serversystem.handler.WorldGroupHandler;

public class WTPCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		if(assistant.isSenderInstanceOfPlayer()) {
			if(assistant.hasMinArguments(1, args)) {
				if(assistant.isBoolean(args[0])) {
					WorldGroupHandler.teleportPlayer((Player)sender, Bukkit.getWorld(args[0]));
					ChatHandler.sendServerMessage(sender, "You are in world " + args[0] +  "!");
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) {
			return new CommandAssistant(sender).getWorlds();
		}
		return new ArrayList<>();
	}

}
