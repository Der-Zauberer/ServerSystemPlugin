package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.menus.WarpsMenu;
import serversystem.utilities.ServerWarp;
import serversystem.handler.WarpHandler;

public class WarpCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length > 0) {
				if(WarpHandler.getWarp(args[0]) != null) {
					WarpHandler.getWarp(args[0]).warpPlayer((Player)sender);
				} else {
					ChatHandler.sendServerErrorMessage(sender, "The warp " + args[0] + " does not exist!");
				}
			} else {
				new WarpsMenu((Player)sender).open();
			}
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.ONLYPLAYER);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		commands.clear();
		if(args.length == 1 && sender instanceof Player) {
			for(ServerWarp warp : WarpHandler.getWarps((Player)sender)) {
				commands.add(warp.getName());
			}
		}
		return commands;
	}

}
