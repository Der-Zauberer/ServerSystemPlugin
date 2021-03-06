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
import serversystem.handler.PlayerBuildMode;
import serversystem.handler.PlayerVanish;
import serversystem.handler.ChatHandler.ErrorMessage;

public class BuildCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			PlayerBuildMode.buildmodePlayer((Player) sender);
		}else if(args.length == 1 && Bukkit.getPlayer(args[0]) != null) {
			PlayerBuildMode.buildmodePlayer(Bukkit.getPlayer(args[0]), (Player) sender);
		}else if(args.length == 1 && !Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.PLAYERNOTONLINE);
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!PlayerVanish.isPlayerVanished(player)) {
					commands.add(player.getName());
				}
			}
		} else {
			commands.clear();
		}
		return commands;
	}
	
}
