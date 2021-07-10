package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.handler.ChatHandler;
import serversystem.handler.WarpHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.utilities.ServerWarp;

public class SetWarpCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length > 0) {
				ServerWarp warp = null;
				if(args.length > 3) {
					warp = new ServerWarp(args[0], ChatHandler.parseMaterial(args[1]), ((Player)sender).getLocation(), ChatHandler.parseBoolean(args[2]));
					warp.setPermission(args[3]);
				} else if(args.length > 2) {
					if(args[2].equals("true") || args[2].equals("false")) {
						warp = new ServerWarp(args[0], ChatHandler.parseMaterial(args[1]), ((Player)sender).getLocation(), ChatHandler.parseBoolean(args[2]));
					} else {
						ChatHandler.sendServerErrorMessage(sender, args[2] + " is not a valid boolean!");
					}
				} else if(args.length > 1) {
					if(ChatHandler.parseMaterial(args[2]) != null) {
						warp = new ServerWarp(args[0], ChatHandler.parseMaterial(args[1]), ((Player)sender).getLocation(), true);
					} else {
						ChatHandler.sendServerErrorMessage(sender, args[1] + " is not a valid item!");
					}
				} else {
					warp = new ServerWarp(args[0], Material.ENDER_PEARL, ((Player)sender).getLocation(), true);
				}
				WarpHandler.setWarp(warp);
				ChatHandler.sendServerMessage(sender, "The warp " + warp.getName() + " has been added!");
			} else {
				ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOTENOUGHARGUMENTS);
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
		if(args.length == 2) {
			for (Material material : Material.values()) {
				commands.add("minecraft:" + material.toString().toLowerCase());
			}
		} else if(args.length == 3) {
			commands.add("true");
			commands.add("false");
		}
		return commands;
	}

}
