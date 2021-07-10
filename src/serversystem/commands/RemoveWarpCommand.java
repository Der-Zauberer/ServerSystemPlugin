package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import serversystem.handler.ChatHandler;
import serversystem.handler.WarpHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.utilities.ServerWarp;

public class RemoveWarpCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length > 0) {
			if(WarpHandler.getWarp(args[0]) != null) {
				WarpHandler.removeWarp(WarpHandler.getWarp(args[0]));
				ChatHandler.sendServerMessage(sender, "The warp " + args[0] + " has been removed successfull!");
			} else {
				ChatHandler.sendServerErrorMessage(sender, "The warp " + args[0] + " does not exist!");
			}
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOTENOUGHARGUMENTS);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		if(args.length == 0) {
			for(ServerWarp warp : WarpHandler.getWarps()) {
				commands.add(warp.getName());
			}
		} else {
			commands.clear();
		}
		return commands;
	}

}
