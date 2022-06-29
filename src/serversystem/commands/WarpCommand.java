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
import serversystem.main.ServerSystem;
import serversystem.menus.WarpListMenu;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.ServerWarp;

public class WarpCommand implements CommandExecutor, TabCompleter {
	
	private enum Option {TELEPORT, ADD, REMOVE, EDIT}
	private enum EditOption {MATERIAL, GLOBAL, PERMISSION}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) new WarpListMenu((Player) sender).open();
			else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (!(sender instanceof Player)) ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
			else if (ServerSystem.getWarps().get(args[0]) == null) ChatUtil.sendNotExistErrorMessage(sender, "warp", args[0]);
			else if (ServerSystem.getWarps().get(args[0]).getPermission() != null && !sender.hasPermission(ServerSystem.getWarps().get(args[0]).getPermission())) ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			else ((Player) sender).teleport(ServerSystem.getWarps().get(args[0]).getLocation());
		} else {
			final ServerWarp warp = ServerSystem.getWarps().get(args[0]);
			final Option option = ChatUtil.getEnumValue(args[1], Option.values());
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
					ServerSystem.getWarps().add(new ServerWarp(args[0], ((Player)sender).getLocation()));
					ChatUtil.sendMessage(sender, "The warp " + newWarp.getName() + " has been added!");		
				}
			} else if (option == Option.REMOVE) {
				if (args.length < 3) {
					ServerSystem.getWarps().remove(warp);
					ChatUtil.sendMessage(sender, "The warp " + args[0] + " has been removed successfully!");
				} else {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				}
			} else if (option == Option.EDIT) {
				if (args.length > 4) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (args.length < 3) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
				} else {
					EditOption editOption = ChatUtil.getEnumValue(args[2], EditOption.values());
					if (editOption == null) {
						ChatUtil.sendNotExistErrorMessage(sender, "option", args[2]);
					} else if (editOption == EditOption.MATERIAL) {
						ChatUtil.<Material>processInput(sender, args, 3, "warp", warp.getName(), editOption.name().toLowerCase(), false, input -> {
							return ChatUtil.getEnumValue(input, Material.values());
						}, input -> true, input -> warp.update(), warp::setMaterial, () -> warp.getMaterial().name().toLowerCase());
					} else if (editOption == EditOption.GLOBAL) {
						ChatUtil.<Boolean>processInput(sender, args, 3, "warp", warp.getName(), editOption.name().toLowerCase(), false, input -> {
							if (input.equals("true")) return new Boolean(true);
							else if (input.equals("false")) return new Boolean(false);
							else return null;
						}, input -> true , input -> warp.update(), warp::setGlobal, warp::isGlobal);
					} else if (editOption == EditOption.PERMISSION) {
						ChatUtil.<String>processInput(sender, args, 3, "warp", warp.getName(), editOption.name().toLowerCase(), true, input -> input, input -> true , input -> warp.update(), warp::setPermission, warp::getPermission);
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
			if (sender instanceof Player && !sender.hasPermission("serversystem.command.warp.edit")) {
				commands.addAll(ChatUtil.getStringList(ServerWarp.getWarps((Player) sender), warp -> warp.getName()));
			} else {
				commands.addAll(ChatUtil.getStringList(ServerSystem.getWarps()));
			}
		} else if (sender.hasPermission("serversystem.command.warp.edit")) {
			ServerWarp warp = ServerSystem.getWarps().get(args[0]);
			if (ChatUtil.getCommandLayer(2, args)) {
				if (warp == null) commands.add(Option.ADD.name().toLowerCase());
				else commands.addAll(ChatUtil.getEnumList(Option.values()));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("teleport")) {
				commands.addAll(ChatUtil.getPlayerList(sender));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("edit")) {
				commands.addAll(Arrays.asList("material", "global", "permission"));
			} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("material")) {
				commands.addAll(ChatUtil.getEnumList(Material.values()));
			} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("global")) {
				commands.addAll(Arrays.asList("true", "false"));
			} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("permission")) {
				commands.addAll(PermissionUtil.getBukkitPermissions());
				commands.add("remove");
			}
		}
		return ChatUtil.removeWrong(commands, args);
	}

}
