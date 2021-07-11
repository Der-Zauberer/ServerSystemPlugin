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
import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.utilities.CommandAssistant;
import serversystem.handler.WorldGroupHandler;

public class WorldCommand implements CommandExecutor, TabCompleter{
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		if(assistant.isPath(0, "list", 1, args)) {
			String worlds = "";
			for(World world : Bukkit.getWorlds()) {
				worlds = worlds + world.getName() + " ";
			}
			ChatHandler.sendServerMessage(sender, "Worlds: " + worlds);
		} else if(assistant.isPath(0, "teleport", 2, 2, args)) {
			if(assistant.isSenderInstanceOfPlayer(true)) {
				if(assistant.isWorld(args[1])) {
					WorldGroupHandler.teleportPlayer((Player)sender, Bukkit.getWorld(args[1]));
					ChatHandler.sendServerMessage(sender, "You are in world " + args[1] +  "!");
				}	
			}
		} else if(assistant.isPath(0, "teleport", 3, args)) {
			if(assistant.isWorld(args[1]) && assistant.isPlayer(args[2])) {
				WorldGroupHandler.teleportPlayer(Bukkit.getPlayer(args[2]), Bukkit.getWorld(args[1]));
				if(sender != Bukkit.getServer().getPlayer(args[2])) {ChatHandler.sendServerMessage(sender, "The player " + args[2] +  " is in world " + args[1] +  "!");} 
				ChatHandler.sendServerMessage(Bukkit.getPlayer(args[2]), "You are in world " + args[1] +  "!");
			}
		} else if (assistant.isPath(0, "create", 2, args)) {
			if (Bukkit.getWorld(args[1]) == null) {
				WorldGroupHandler.createWorld(args[1]);
				ChatHandler.sendServerMessage(sender, "The world " + args[1] + " has been successfully created!");
			} else {
				ChatHandler.sendServerMessage(sender, "The world " + args[1] + " is already loaded!");
			}
		} else if(assistant.isPath(0, "remove", 2, args)) {
			if(assistant.isWorld(args[1])) {
				WorldGroupHandler.removeWorld(args[1]);
				ChatHandler.sendServerMessage(sender, "The world " + args[1] + " will be removed after a restart!");
			}
		} else if (assistant.isPath(0, "edit", 3, args)) {
			if(assistant.isWorld(args[1])) {
				if(args.length == 3) {
					if(!args[2].equals("gamemode") && !args[2].equals("permission")) {
						if(assistant.isWorldOption(args[2])) {
							Config.getWorldOption(args[1], ChatHandler.parseWorldOption(args[2]));
							ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.getWorldOption(args[1], ChatHandler.parseWorldOption(args[2])) + " for the world " + args[1] + "!");
						}
					} else {
						if(args[2].equals("gamemode")) {
							ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.getWorldGamemode(args[1]).toString().toLowerCase() + " for the world " + args[1] + "!");
						} else if(args[2].equals("permission")) {
							if(Config.getWorldPermission(args[1]) != null) {
								ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.getWorldPermission(args[1]) + " for the world " + args[1] + "!");
							} else {
								ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is not set for the world " + args[1] + "!");
							}
						}
					}
				}else {
					if(!args[2].equals("gamemode") && !args[2].equals("permission")) {
						if(assistant.isBoolean(args[3])) {
							if(assistant.isWorldOption(args[2])) {
								Config.setWorldOption(args[1], ChatHandler.parseWorldOption(args[2]), ChatHandler.parseBoolean(args[3]));
								ChatHandler.sendServerMessage(sender, "The option " + args[2] + " has been set to " + args[3] + " for the world " + args[1] + "!");
							}
						}
					} else {
						if(args[2].equals("gamemode")) {
							if(assistant.isGameMode(args[3])) {
								Config.setWorldGamemode(args[1], ChatHandler.parseGamemode(args[3]));
								ChatHandler.sendServerMessage(sender, "The gamemode has been set to " + ChatHandler.parseGamemode(args[3]).toString().toLowerCase() + " for the world " + args[1] + "!");
							}
						} else if(args[2].equals("permission")) {
							if(args[3].equals("null")) {
								Config.removeWorldPermission(args[1]);
								ChatHandler.sendServerMessage(sender, "The permission has been removed from world " + args[1] + " now!");
							} else {
								Config.setWorldPermission(args[1], args[3]);
								ChatHandler.sendServerMessage(sender, "The world " + args[1] + " has the permission " + args[3] + " now!");
							}
						}
					}
				}
			}
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOTENOUGHARGUMENTS);
		}
		return true;
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.add("teleport");
			commands.add("create");
			commands.add("list");
			commands.add("edit");
			commands.add("remove");
		} else if((args.length == 2 && args[0].equals("teleport")) ||  (args.length == 2 && args[0].equals("edit")) ||  (args.length == 2 && args[0].equals("remove"))) {
			commands = new CommandAssistant(sender).getWorlds();
		} else if(args.length == 3 && args[0].equals("teleport")) {
			commands = new CommandAssistant(sender).getPlayer();
		} else if(args.length == 3 && args[0].equals("edit")) {
			commands.add("protection");
			commands.add("pvp");
			commands.add("damage");
			commands.add("hunger");
			commands.add("explosion");
			commands.add("gamemode");
			commands.add("deathmessage");
			commands.add("permission");
		} else if((args.length == 4 && args[0].equals("edit")) && !args[2].equals("gamemode") && !args[2].equals("permission")) {
			commands.add("true");
			commands.add("false");
		} else if((args.length == 4 && args[0].equals("edit")) && args[2].equals("gamemode")) {
			commands.add("survival");
			commands.add("creative");
			commands.add("adventure");
			commands.add("spectator");
		}
		return commands;
	}
	
}
