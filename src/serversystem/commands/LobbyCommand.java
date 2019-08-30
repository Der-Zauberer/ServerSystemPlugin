package serversystem.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import serversystem.main.Config;
import serversystem.utilities.ServerMessage;

public class LobbyCommand implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player && Config.lobbyExists() && Config.getLobbyWorld() != null) {
			((Player)sender).teleport(Config.getLobbyWorld().getSpawnLocation());
		} else {
			ServerMessage.sendErrorMessage(sender, "Lobby does not exist!");
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<String>();
		return commands;
	}

}
