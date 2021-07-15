package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.handler.ChatHandler;
import serversystem.utilities.CommandAssistant;
import serversystem.utilities.ServerWarp;
import serversystem.handler.WarpHandler;
import serversystem.menus.WarpsMenu;
import serversystem.handler.ChatHandler.ErrorMessage;

public class WarpCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		if(args.length == 0 && sender instanceof Player) {
			new WarpsMenu((Player)sender).open();
		} else if(assistant.hasMinArguments(1, args)) {
			if(assistant.isPath(1, "create", 2, args) || assistant.isWarp(args[0])) {
				ServerWarp warp = WarpHandler.getWarp(args[0]);
				if(args.length == 1 && sender instanceof Player) {
					if(!sender.hasPermission("serversystem.command.warp.edit") && ((warp.getPermission() != null && !sender.hasPermission(warp.getPermission())) || (!warp.isGlobal() && warp.getLocation().getWorld() != ((Player)sender).getWorld()))) {
						ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
					} else {
						((Player)sender).teleport(warp.getLocation());
					} 
				} else if(sender instanceof Player && !sender.hasPermission("serversystem.command.warp.edit")) {
					ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
				} else if(sender.hasPermission("serversystem.command.warp.edit")) {
					if(assistant.isPath(1, "teleport", 3, args)) {
						if(assistant.isPlayer(args[2])) {
							Player player = Bukkit.getPlayer(args[2]);
							player.teleport(warp.getLocation());
							if(sender != player) {ChatHandler.sendServerMessage(sender, "Teleported the player " + player.getName() +  " to the warp " + warp.getName() +  "!");} 
						}
					} else if(assistant.isPath(1, "create", 2, args)) {
						if(assistant.isSenderInstanceOfPlayer()) {
							if (warp == null) {
								warp = new ServerWarp(args[0], ((Player)sender).getLocation());
								ChatHandler.sendServerMessage(sender, "The warp " + warp.getName() + " has been added!");					
							} else {
								ChatHandler.sendServerMessage(sender, "The warp " + warp.getName() + " does already exist!");
							}
						}
					} else if(assistant.isPath(1, "remove", 2, args)) {
						warp.remove();
						ChatHandler.sendServerMessage(sender, "The warp " + args[0] + " has been removed successfull!");
					} else if(assistant.isPath(1, "edit", 3, args)) {
						if(assistant.hasMinArguments(3, args)) {
							if(assistant.isPath(2, "material", 4, args)) {
								if(assistant.isMaterial(args[3])) {
									warp.setMaterial(ChatHandler.parseMaterial(args[3]));
									ChatHandler.sendServerMessage(sender, "The material has been set to " + args[3] + " for the warp " + warp.getName() + "!");
								}
							} else if(assistant.isPath(2, "material", 3, args)) {
								ChatHandler.sendServerMessage(sender, "The material is set to " + warp.getMaterial().toString().toLowerCase() + " for the warp " + warp.getName() + "!");
							} else if(assistant.isPath(2, "global", 4, args)) {
								if(assistant.isBoolean(args[3])) {
									warp.setGlobal(ChatHandler.parseBoolean(args[3]));
									ChatHandler.sendServerMessage(sender, "The option global has been set to " + args[3] + " for the warp " + warp.getName() + "!");
								}
							} else if(assistant.isPath(2, "global", 3, args)) {
								ChatHandler.sendServerMessage(sender, "The option global is set to " + Boolean.toString(warp.isGlobal()) + " for the warp " + warp.getName() + "!");
							} else if(assistant.isPath(2, "permission", 4, args)) {
								if(args[3].equals("null")) {
									warp.setPermission(null);
									ChatHandler.sendServerMessage(sender, "The permission has been removed from warp " + warp.getName() + " now!");
								} else {
									warp.setPermission(args[3]);
									ChatHandler.sendServerMessage(sender, "The warp " + warp.getName() + " has the permission " + args[3] + " now!");
								}
							} else if(assistant.isPath(2, "permission", 3, args)) {
								if(warp.getPermission() == null) {
									ChatHandler.sendServerMessage(sender, "The warp " + warp.getName() + " has no permission!");
								} else {
									ChatHandler.sendServerMessage(sender, "The warp " + warp.getName() + " has the permission " + warp.getPermission() + "!");
								}
							} else {
								ChatHandler.sendServerErrorMessage(sender, args[2] + " is not a valid option!");
							}
						}
						
					} else if(args.length > 1 && !args[1].equals("create") && !args[1].equals("remove") && !args[1].equals("edit") && !args[1].equals("teleport")) {
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
		List<String> commands = new ArrayList<>();
		if(sender.hasPermission("serversystem.command.world.edit")) {
			if(args.length == 1) {
				commands = new CommandAssistant(sender).getWarps();
			} else if(args.length == 2) {
				commands.add("teleport");
				commands.add("create");
				commands.add("edit");
				commands.add("remove");
			} else if(args.length == 3 && args[1].equals("teleport")) {
				commands = new CommandAssistant(sender).getPlayer();
			} else if(args.length == 3 && args[1].equals("edit")) {
				commands.add("material");
				commands.add("global");
				commands.add("permission");
			} else if((args.length == 4 && args[1].equals("edit")) && args[2].equals("global")) {
				commands.add("true");
				commands.add("false");
			} else if((args.length == 4 && args[1].equals("edit")) && args[2].equals("material")) {
				for (Material material : Material.values()) {
					commands.add("minecraft:" + material.toString().toLowerCase());
				}
			}
		} else {
			if(args.length == 1) {
				if(sender instanceof Player) {
					commands = new CommandAssistant(sender).getWarps((Player)sender);
				}
			}
		}
		return commands;
	}

}
