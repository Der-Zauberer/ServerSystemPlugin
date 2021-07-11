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
import serversystem.utilities.CommandAssistant;
import serversystem.utilities.ServerWarp;

public class SetWarpCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		if(assistant.isSenderInstanceOfPlayer()) {
			if(assistant.hasMinArguments(0, args)) {
				ServerWarp warp = null;
				if(args.length > 3) {
					warp = new ServerWarp(args[0], ChatHandler.parseMaterial(args[1]), ((Player)sender).getLocation(), ChatHandler.parseBoolean(args[2]));
					if(!args[3].equals("null")) {
						warp.setPermission(args[3]);
					}
				} else if(args.length > 2) {
					if(assistant.isBoolean(args[2])) {
						warp = new ServerWarp(args[0], ChatHandler.parseMaterial(args[1]), ((Player)sender).getLocation(), ChatHandler.parseBoolean(args[2]));
					}
				} else if(args.length > 1) {
					if(assistant.isMaterial(args[1])) {
						warp = new ServerWarp(args[0], ChatHandler.parseMaterial(args[1]), ((Player)sender).getLocation(), true);
					}
				} else {
					warp = new ServerWarp(args[0], Material.ENDER_PEARL, ((Player)sender).getLocation(), true);
				}
				if(warp != null) {
					WarpHandler.setWarp(warp);
					ChatHandler.sendServerMessage(sender, "The warp " + warp.getName() + " has been added!");
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
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
