package serversystem.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.menus.WarpListMenu;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.ServerWarp;

public class WarpCommand implements CommandExecutor, TabCompleter {
	
	private enum Option {TELEPORT, ADD, REMOVE, EDIT}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) new WarpListMenu((Player) sender).open();
			else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (!(sender instanceof Player)) ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
			else if (ServerWarp.getWarp(args[0]) == null) ChatUtil.sendNotExistErrorMessage(sender, "warp", args[0]);
			else if (ServerWarp.getWarp(args[0]).getPermission() != null && !sender.hasPermission(ServerWarp.getWarp(args[0]).getPermission())) ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			else ((Player) sender).teleport(ServerWarp.getWarp(args[0]).getLocation());
		} else {
			final ServerWarp warp = ServerWarp.getWarp(args[0]);
			final Option option = ChatUtil.getValue(args[1], Option.values());
			if (sender instanceof Player && !sender.hasPermission("serversystem.command.warp.edit")) {
				ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			} else if (option == null) {
				ChatUtil.sendNotExistErrorMessage(sender, "option", args[1]);
			} else if (warp == null && option != Option.ADD) {
				ChatUtil.sendNotExistErrorMessage(sender, "warp", args[0]);
			} else if (option == Option.TELEPORT) {
				if (args.length == 2) {
					if (sender instanceof Player) ((Player) sender).teleport(warp.getLocation());
					else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS); 
				} else if (args.length == 3) {
					if (Bukkit.getPlayer(args[2]) != null) Bukkit.getPlayer(args[2]).teleport(warp.getLocation());
					else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[3]);
				} else {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				}
			} else if (option == Option.ADD) {
				if (args.length > 2) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (warp != null) {
					ChatUtil.sendMessage(sender, "The warp " + warp.getName() + " does already exist!");
				} else {
					ServerWarp newWarp = new ServerWarp(args[0], ((Player)sender).getLocation());
					ServerWarp.addWarp(newWarp);
					ChatUtil.sendMessage(sender, "The warp " + newWarp.getName() + " has been added!");		
				}
			} else if (option == Option.REMOVE) {
				if (args.length < 3) {
					ServerWarp.removeWarp(warp);
					ChatUtil.sendMessage(sender, "The warp " + args[0] + " has been removed successfully!");
				} else {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				}
			} else if (option == Option.EDIT) {
				if (args.length > 4) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (args.length < 3) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
				} else if (!args[2].equalsIgnoreCase("material") && !args[2].equalsIgnoreCase("global") && !args[2].equalsIgnoreCase("permission")) {
					ChatUtil.sendNotExistErrorMessage(sender, "option", args[2]);
				} else {
					if (args[2].equalsIgnoreCase("material")) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The material for the warp " + warp.getName() + " is " + warp.getMaterial().toString().toLowerCase() + "!");							
						} else {
							final Material material = ChatUtil.getValue(args[3].replace("minecraft:", ""), Material.values());
							if (material != null) {
								warp.setMaterial(material);
								ChatUtil.sendMessage(sender, "The material has been set to " + warp.getMaterial().toString().toLowerCase() + " for the warp " + warp.getName() + "!");
							} else {
								ChatUtil.sendNotExistErrorMessage(sender, "material", args[3].replace("minecraft:", ""));
							}
						}
					} else if (args[2].equalsIgnoreCase("global")) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The option global for the warp " + warp.getName() + " is " + warp.isGlobal() + "!");
						} else if (!args[3].equalsIgnoreCase("true") && !args[3].equalsIgnoreCase("false")) {
							ChatUtil.sendErrorMessage(sender, args[3] + " is not a valid boolean!");
						} else {
							final boolean bool = args[3].equalsIgnoreCase("true") ? true : false;
							warp.setGlobal(bool);
							ChatUtil.sendMessage(sender, "The option global has been set to " + bool + " for the warp " + warp.getName() + "!");
						
						}
					} else if (args[2].equalsIgnoreCase("permission")) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The permission for the warp " + warp.getName() + " is " + warp.getPermission() + "!");
						} else {
							warp.setPermission(args[3]);
							ChatUtil.sendMessage(sender, "Set permission for the warp " + warp.getName() + " to " + args[3] + "!");
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		final List<String> commands = new ArrayList<>();
		if (ChatUtil.getCommandLayer(1, args)) {
			commands.addAll(ChatUtil.getList(sender instanceof Player ? ServerWarp.getWarps((Player) sender) : ServerWarp.getWarps(), warp -> warp.getName()));
		} else if (sender.hasPermission("serversystem.command.warp.edit")) {
			if (ChatUtil.getCommandLayer(2, args)) {
				commands.addAll(ChatUtil.getEnumList(Option.values()));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("teleport")) {
				commands.addAll(ChatUtil.getPlayerList(sender));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("edit")) {
				commands.addAll(Arrays.asList("material", "global", "permission"));
			} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("global")) {
				commands.addAll(Arrays.asList("true", "false"));
			} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("material")) {
				commands.addAll(ChatUtil.getEnumList(Material.values()));
			}
		}
		return ChatUtil.removeWrong(commands, args);
	}

}
