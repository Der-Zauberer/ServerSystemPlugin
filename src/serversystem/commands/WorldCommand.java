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
import serversystem.utilities.PlayerVanish;
import serversystem.utilities.ServerMessage;
import serversystem.utilities.WorldGroupHandler;

public class WorldCommand implements CommandExecutor, TabCompleter{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length == 0) {
			String worlds = "";
			for(World world : Bukkit.getWorlds()) {
				worlds = worlds + world.getName() + " ";
			}
			ServerMessage.sendMessage(sender, "Worlds: " + worlds);
		}
		if(args.length == 1) {
			if(Bukkit.getWorld(args[0]) != null) {
				WorldGroupHandler.teleportPlayer(player, Bukkit.getWorld(args[0]));
				ServerMessage.sendMessage(sender, "You are in world " + args[0] +  "!");
			} else {
				ServerMessage.sendErrorMessage(sender, "The world " + args[0] +  " does not exists!");
			}	
		}
		if(args.length == 2 && Bukkit.getPlayer(args[1]) != null) {
			if(Bukkit.getWorld(args[0]) != null) {
				WorldGroupHandler.teleportPlayer(Bukkit.getPlayer(args[1]), Bukkit.getWorld(args[0]));
				if(sender != Bukkit.getServer().getPlayer(args[1])) {ServerMessage.sendMessage(sender, "The player " + args[1] +  " is in world " + args[0] +  "!");} 
				ServerMessage.sendMessage(Bukkit.getPlayer(args[1]), "You are in world " + args[0] +  "!");
			} else {
				ServerMessage.sendErrorMessage(sender, "The world " + args[0] +  " does not exists!");
			}
			if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
				ServerMessage.sendErrorMessage(sender, "The player " + args[1] + " is not online!");
			}
		} else if(!Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
			ServerMessage.sendErrorMessage(sender, "The player " + args[1] + " is not online!");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			for(World world : Bukkit.getWorlds()) {
				commands.add(world.getName());
			}
		} else if(args.length == 2) {
			commands.clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!PlayerVanish.isPlayerVanished(player)) {
					commands.add(player.getName());
				}
			}
		}
		return commands;
	}
	
}
