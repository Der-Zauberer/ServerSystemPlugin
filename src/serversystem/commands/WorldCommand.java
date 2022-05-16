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
		if (assistant.hasMinArguments(1, args)) {
			if (assistant.isPath(1, "create", 2, args) || assistant.isWorld(args[0])) {
				World world = Bukkit.getWorld(args[0]);
				if (args.length == 1 && sender instanceof Player) {
					if (!sender.hasPermission("serversystem.command.world.edit") && Config.getWorldPermission(world.getName()) != null && !sender.hasPermission(Config.getWorldPermission(world.getName()))) {
						ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
					} else {
						WorldGroupHandler.teleportPlayer((Player)sender, world);
					} 
				} else if (sender instanceof Player && !sender.hasPermission("serversystem.command.world.edit")) {
					ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
				} else if (sender.hasPermission("serversystem.command.world.edit")) {
					if (assistant.isPath(1, "teleport", 3, args)) {
						if (assistant.isPlayer(args[2])) {
							Player player = Bukkit.getPlayer(args[2]);
							WorldGroupHandler.teleportPlayer(player, world);
							if (sender != player) {ChatHandler.sendServerMessage(sender, "Teleported the player " + player.getName() +  " to the world " + world.getName() +  "!");} 
						}
					} else if (assistant.isPath(1, "create", 2, args)) {
						if (world == null) {
							ChatHandler.sendServerMessage(sender, "The world " + args[0] + " will be created, please wait a moment!");
							WorldGroupHandler.createWorld(args[0]);
							ChatHandler.sendServerMessage(sender, "The world " + args[0] + " has been successfully created!");
						} else {
							ChatHandler.sendServerMessage(sender, "The world " + world.getName() + " is already loaded!");
						}
					} else if (assistant.isPath(1, "remove", 2, args)) {
						WorldGroupHandler.removeWorld(world.getName());
						ChatHandler.sendServerMessage(sender, "The world " + world.getName() + " will be removed after a restart!");
					} else if (assistant.isPath(1, "edit", 3, args)) {
						if (args.length == 3) {
							if (!args[2].equals("gamemode") && !args[2].equals("permission")) {
								if (assistant.isWorldOption(args[2])) {
									Config.getWorldOption(world.getName(), ChatHandler.parseWorldOption(args[2]));
									ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.getWorldOption(world.getName(), ChatHandler.parseWorldOption(args[2])) + " for the world " + world.getName() + "!");
								}
							} else {
								if (args[2].equals("gamemode")) {
									ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.getWorldGamemode(world.getName()).toString().toLowerCase() + " for the world " + world.getName() + "!");
								} else if(args[2].equals("permission")) {
									if (Config.getWorldPermission(world.getName()) != null) {
										ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is set to " + Config.getWorldPermission(world.getName()) + " for the world " + world.getName() + "!");
									} else {
										ChatHandler.sendServerMessage(sender, "The option " + args[2] + " is not set for the world " + world.getName() + "!");
									}
								}
							}
						}else {
							if (!args[2].equals("gamemode") && !args[2].equals("permission")) {
								if(assistant.isBoolean(args[3])) {
									if(assistant.isWorldOption(args[2])) {
										Config.setWorldOption(world.getName(), ChatHandler.parseWorldOption(args[2]), ChatHandler.parseBoolean(args[3]));
										ChatHandler.sendServerMessage(sender, "The option " + args[2] + " has been set to " + args[3] + " for the world " + world.getName() + "!");
									}
								}
							} else {
								if (args[2].equals("gamemode")) {
									if(assistant.isGameMode(args[3])) {
										Config.setWorldGamemode(world.getName(), ChatHandler.parseGamemode(args[3]));
										ChatHandler.sendServerMessage(sender, "The gamemode has been set to " + ChatHandler.parseGamemode(args[3]).toString().toLowerCase() + " for the world " + world.getName() + "!");
									}
								} else if (args[2].equals("permission")) {
									if (args[3].equals("null")) {
										Config.removeWorldPermission(world.getName());
										ChatHandler.sendServerMessage(sender, "The permission has been removed from world " + world.getName() + " now!");
									} else {
										Config.setWorldPermission(world.getName(), args[3]);
										ChatHandler.sendServerMessage(sender, "The world " + world.getName() + " has the permission " + args[3] + " now!");
									}
								}
							}
						}
					} else if (args.length > 1 && !args[1].equals("create") && !args[1].equals("remove") && !args[1].equals("edit") && !args[1].equals("teleport")) {
						ChatHandler.sendServerErrorMessage(sender, args[1] + " is not a valid option!");
					} else {
						ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOTENOUGHARGUMENTS);
					}
				}
			}
		}
		return true;
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		List<String> commands = new ArrayList<>();
		if(sender.hasPermission("serversystem.command.world.edit")) {
			if (args.length == 1) {
				commands = assistant.getWorlds();
			} else if (args.length == 2) {
				commands.add("teleport");
				commands.add("create");
				commands.add("edit");
				commands.add("remove");
			} else if (args.length == 3 && args[1].equals("teleport")) {
				commands = assistant.getPlayers();
			} else if (args.length == 3 && args[1].equals("edit")) {
				commands.add("protection");
				commands.add("pvp");
				commands.add("damage");
				commands.add("hunger");
				commands.add("explosion");
				commands.add("gamemode");
				commands.add("deathmessage");
				commands.add("permission");
			} else if ((args.length == 4 && args[1].equals("edit")) && !args[2].equals("gamemode") && !args[2].equals("permission")) {
				commands.add("true");
				commands.add("false");
			} else if ((args.length == 4 && args[1].equals("edit")) && args[2].equals("gamemode")) {
				commands.add("survival");
				commands.add("creative");
				commands.add("adventure");
				commands.add("spectator");
			}
		} else {
			if(args.length == 1) {
				commands = assistant.getWorlds(sender);
			}
		}
		commands = assistant.cutArguments(args, commands);
		return commands;
	}
	
}
