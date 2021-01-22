package serversystem.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.handler.WorldGroupHandler;

public class WTPCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 1) {
				if(Bukkit.getWorld(args[0]) != null) {
					WorldGroupHandler.teleportPlayer((Player)sender, Bukkit.getWorld(args[0]));
					ChatHandler.sendServerMessage(sender, "You are in world " + args[0] +  "!");
				} else {
					ChatHandler.sendServerErrorMessage(sender, "The world " + args[0] +  " does not exist!");
				}
			} else {
				ChatHandler.sendServerErrorMessage(sender, "Not enought arguments!");
			}
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.ONLYPLAYER);
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			for (World world : Bukkit.getWorlds()) {
				commands.add(world.getName());
			}
		} else {
			commands.clear();
		}
		return commands;
	}

}
