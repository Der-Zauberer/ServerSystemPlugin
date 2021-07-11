package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.handler.PlayerBuildMode;
import serversystem.utilities.CommandAssistant;

public class BuildCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistent = new CommandAssistant(sender);
		if(args.length == 0) {
			if(assistent.isSenderInstanceOfPlayer(true)) {
				PlayerBuildMode.buildmodePlayer((Player) sender);
			}
		} else {
			if(assistent.isPlayer(args[0])) {
				PlayerBuildMode.buildmodePlayer(Bukkit.getPlayer(args[0]), sender);
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 1) {
			return new CommandAssistant(sender).getPlayer();
		} else {
			return new ArrayList<>();
		}
	}
	
}
