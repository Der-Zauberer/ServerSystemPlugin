package serversystem.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.WorldGroup;

public class WorldCommand implements CommandExecutor, TabCompleter{
	
	private enum Option {TELEPORT, ADD, REMOVE, EDIT}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (!(sender instanceof Player)) ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
			else if (Bukkit.getWorld(args[0]) == null) ChatUtil.sendNotExistErrorMessage(sender, "world", args[0]);
			else if (Config.getWorldPermission(Bukkit.getWorld(args[0])) != null && !sender.hasPermission(Config.getWorldPermission(Bukkit.getWorld(args[0])))) ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			else WorldGroup.teleportPlayer((Player) sender, Bukkit.getWorld(args[0]));
		} else {
			final World world = Bukkit.getWorld(args[0]);
			final Option option = ChatUtil.getValue(args[1], Option.values());
			if (sender instanceof Player && !sender.hasPermission("serversystem.command.world.edit")) {
				ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			} else if (option == null) {
				ChatUtil.sendNotExistErrorMessage(sender, "option", args[1]);
			} else if (world == null && option != Option.ADD) {
				ChatUtil.sendNotExistErrorMessage(sender, "world", args[0]);
			} else if (option == Option.TELEPORT) {
				if (args.length == 2) {
					if (sender instanceof Player) WorldGroup.teleportPlayer((Player) sender, world);
					else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS); 
				} else if (args.length == 3) {
					if (Bukkit.getPlayer(args[2]) != null) WorldGroup.teleportPlayer(Bukkit.getPlayer(args[2]), world);
					else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[3]);
				} else {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				}
			} else if (option == Option.ADD) {
				if (args.length > 2) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (world != null) {
					ChatUtil.sendErrorMessage(sender, "The world " + world.getName() + " is already loaded!");
				} else {
					ChatUtil.sendMessage(sender, "The world " + args[0] + " will be added, please wait a moment!");
					WorldGroup.createWorld(args[0]);
					ChatUtil.sendMessage(sender, "The world " + args[0] + " has been successfully loaded!");
				}
			} else if (option == Option.REMOVE) {
				if (args.length < 3) {
					Config.removeLoadWorld(world.getName());
					ChatUtil.sendMessage(sender, "The world " + world.getName() + " will be removed after a restart!");
				} else {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				}
			} else if (option == Option.EDIT) {
				WorldOption worldOption = ChatUtil.getValue(args.length > 2 ? args[2] : null, WorldOption.values());
				if (args.length > 4) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (args.length < 3) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
				} else if (worldOption == null && !args[2].equalsIgnoreCase("world_group") && !args[2].equalsIgnoreCase("permission") && !args[2].equalsIgnoreCase("gamemode")) {
					ChatUtil.sendNotExistErrorMessage(sender, "option", args[2]);
				} else {
					if (worldOption != null) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The option " + worldOption.toString().toLowerCase() + " for the world " + world.getName() + " is " + Config.getWorldOption(world, worldOption) + "!");
						} else if (!args[3].equalsIgnoreCase("true") && !args[3].equalsIgnoreCase("false")) {
							ChatUtil.sendErrorMessage(sender, args[3] + " is not a valid boolean!");
						} else {
							final boolean bool = args[3].equalsIgnoreCase("true") ? true : false;
							Config.setWorldOption(world, worldOption, bool);
							ChatUtil.sendMessage(sender, "Set option " + worldOption.toString().toLowerCase() + " for the world " + world.getName() + " to " + bool + "!");
						}
					} else if (args[2].equalsIgnoreCase("world_group")) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The world_group for the world " + world.getName() + " is " + Config.getWorldGroup(world) + "!");
						} else {
							Config.setWorldGroup(world, args[3]);
							ChatUtil.sendMessage(sender, "Set world_group for the world " + world.getName() + " to " + args[3] + "!");
							ChatUtil.sendMessage(sender, "The server needs a restart to change the world_group!");
						}
					} else if (args[2].equalsIgnoreCase("permission")) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The permission for the world " + world.getName() + " is " + Config.getWorldPermission(world) + "!");
						} else {
							Config.setWorldPermission(world, args[3]);
							ChatUtil.sendMessage(sender, "Set permission for the world " + world.getName() + " to " + args[3] + "!");
						}
					} else if (args[2].equalsIgnoreCase("gamemode")) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The gamemode for the world " + world.getName() + " is " + Config.getWorldGamemode(world).toString().toLowerCase() + "!");
						} else {
							final GameMode gameMode = ChatUtil.getValue(args[3], GameMode.values());
							if (gameMode != null) {
								Config.setWorldGamemode(world, gameMode);
								ChatUtil.sendMessage(sender, "Set gamemode for the world " + world.getName() + " to " + gameMode.toString().toLowerCase() + "!");
							} else {
								ChatUtil.sendNotExistErrorMessage(sender, "gamemode", args[3]);
							}
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
			commands.addAll(ChatUtil.getList(Bukkit.getWorlds(), world -> world.getName()));
		} else if (sender.hasPermission("serversystem.command.world.edit")) {
			if (ChatUtil.getCommandLayer(2, args)) {
				commands.addAll(ChatUtil.getEnumList(Option.values()));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("teleport")) {
				commands.addAll(ChatUtil.getPlayerList(sender));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("edit")) {
				commands.addAll(ChatUtil.getEnumList(WorldOption.values()));
				commands.addAll(Arrays.asList("world_group", "gamemode", "permission"));
			} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && !args[2].equals("gamemode") && !args[2].equals("permission")) {
				commands.addAll(Arrays.asList("true", "false"));
			} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("gamemode")) {
				commands.addAll(ChatUtil.getEnumList(GameMode.values()));
			}
		}
		return ChatUtil.removeWrong(commands, args);
	}
	
}
