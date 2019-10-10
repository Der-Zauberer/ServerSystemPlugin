package serversystem.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.utilities.PlayerVanish;
import serversystem.utilities.ChatMessage;

public class VanishCommand implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			PlayerVanish.vanishPlayer((Player) sender);
			if(PlayerVanish.isPlayerVanished((Player) sender)) {
				ChatMessage.sendServerMessage(sender, "You are vanished now!");
			} else {
				ChatMessage.sendServerMessage(sender, "You are no longer vanished!");
			}
		}else if(Bukkit.getPlayer(args[0]) != null) {
			PlayerVanish.vanishPlayer(Bukkit.getServer().getPlayer(args[0]), (Player) sender);
			
			if(PlayerVanish.isPlayerVanished(Bukkit.getPlayer(args[0]))) {
			 	if(sender != Bukkit.getServer().getPlayer(args[0])) {ChatMessage.sendServerMessage(sender, Bukkit.getServer().getPlayer(args[0]).getName() + " is vanished now!");} 
				ChatMessage.sendServerMessage(Bukkit.getServer().getPlayer(args[0]), "You are vanished now!");
			} else {
				if(sender != Bukkit.getServer().getPlayer(args[0])) {ChatMessage.sendServerMessage(sender, Bukkit.getServer().getPlayer(args[0]).getName() + " is no longer vanished!");} 
				ChatMessage.sendServerMessage(Bukkit.getServer().getPlayer(args[0]), "You are no longer vanished!");
			}
		} else if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {
			ChatMessage.sendServerErrorMessage(sender, "The player " + args[0] + " is not online!");
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
