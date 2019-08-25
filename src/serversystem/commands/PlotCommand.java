package serversystem.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import serversystem.utilities.WorldGroupHandler;

public class PlotCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		return false;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			commands.add("pos1");
			commands.add("pos2");
			commands.add("create");
			commands.add("claim");
			commands.add("owner");
			commands.add("trust");
			commands.add("bann");
		}
		if(args.length == 2 && (args[0].equals("owner") || args[0].equals("trust") || args[0].equals("bann"))) {
			for (Player player : WorldGroupHandler.getWorldGroup((Player) sender).getPlayers()) {
				commands.clear();
				commands.add(player.getName());
			}
		}
		return commands;
	}

}
