package serversystem.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.config.Config.WorldOption;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.WorldGroup;

public class WorldCommand implements CommandExecutor, TabCompleter{
	
	private enum Option {TELEPORT, ADD, REMOVE, INFO, EDIT}
	private enum EditOption {WORLD_GROUP, GAMEMODE, PERMISSION}
	
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
			final Option option = ChatUtil.getEnumValue(args[1], Option.values());
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
					Config.removeWorld(world);
					ChatUtil.sendMessage(sender, "The world " + world.getName() + " will be removed after a restart!");
				} else {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				}
			} else if (option == Option.INFO) {
				if (args.length > 2) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else {
					ChatUtil.sendSeperator(sender);
					ChatUtil.sendMessage(sender, "World " + world.getName() + ":");
					ChatUtil.sendMessage(sender, "  world_group: " + Config.getWorldGroup(world));
					ChatUtil.sendMessage(sender, "  permission: " + (Config.getWorldPermission(world) != null ? Config.getWorldPermission(world) : "-"));
					ChatUtil.sendMessage(sender, "  gamemode: " + Config.getWorldGamemode(world).name().toLowerCase());
					ChatUtil.sendMessage(sender, "  damage: " + Config.getWorldOption(world, WorldOption.DAMAGE));
					ChatUtil.sendMessage(sender, "  hunger: " + Config.getWorldOption(world, WorldOption.HUNGER));
					ChatUtil.sendMessage(sender, "  pvp: " + Config.getWorldOption(world, WorldOption.PVP));
					ChatUtil.sendMessage(sender, "  explosion: " + Config.getWorldOption(world, WorldOption.EXPLOSION));
					ChatUtil.sendMessage(sender, "  protection: " + Config.getWorldOption(world, WorldOption.PROTECTION));
					ChatUtil.sendMessage(sender, "  world_spawn: " + Config.getWorldOption(world, WorldOption.WORLD_SPAWN));
					ChatUtil.sendMessage(sender, "  death_message: " + Config.getWorldOption(world, WorldOption.DEATH_MESSAGE));
					ChatUtil.sendSeperator(sender);
				}
			} else if (option == Option.EDIT) {
				if (args.length > 4) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (args.length < 3) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
				} else {
					EditOption editOption = ChatUtil.getEnumValue(args[2], EditOption.values());
					WorldOption worldOption = ChatUtil.getEnumValue(args[2], WorldOption.values());
					if (worldOption != null) {
						ChatUtil.<Boolean>processInput(sender, args, 3, "warp", world.getName(), worldOption.name().toLowerCase(), false, input -> {
							if (input.equals("true")) return new Boolean(true);
							else if (input.equals("false")) return new Boolean(false);
							else return null;
						}, input -> true , input -> {}, bool -> Config.setWorldOption(world, worldOption, bool), () -> Config.getWorldOption(world, worldOption));
					} else if (editOption != null) {
						if (editOption == EditOption.WORLD_GROUP) {
							ChatUtil.<String>processInput(sender, args, 3, "warp", world.getName(), editOption.name().toLowerCase(), false, input -> input, input -> true , input -> {}, group -> Config.setWorldGroup(world, group), () ->  Config.getWorldGroup(world));
						} else if (editOption == EditOption.PERMISSION) {
							ChatUtil.<String>processInput(sender, args, 3, "warp", world.getName(), editOption.name().toLowerCase(), false, input -> input, input -> true , input -> {}, permission -> Config.setWorldPermission(world, permission), () ->  Config.getWorldPermission(world));
						} else if (editOption == EditOption.GAMEMODE) {
							ChatUtil.<GameMode>processInput(sender, args, 3, "warp", world.getName(), editOption.name().toLowerCase(), true, input -> {
								return ChatUtil.getEnumValue(input, GameMode.values());
							}, input -> true , input -> {}, gamemode -> Config.setWorldGamemode(world, gamemode), () ->  Config.getWorldGamemode(world));
						}
					} else  {
						ChatUtil.sendNotExistErrorMessage(sender, "option", args[2]);
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
			if (sender instanceof Player && !sender.hasPermission("serversystem.command.world.edit")) {
				commands.addAll(Bukkit.getWorlds().stream().filter(world -> Config.getWorldPermission(world) != null && sender.hasPermission(Config.getWorldPermission(world))).map(World::getName).collect(Collectors.toList()));
			} else {
				commands.addAll(ChatUtil.getStringList(Bukkit.getWorlds(), World::getName));
			}
		} else if (sender.hasPermission("serversystem.command.world.edit")) {
			World world = Bukkit.getWorld(args[0]);
			if (ChatUtil.getCommandLayer(2, args)) {
				if (world == null) commands.add(Option.ADD.name().toLowerCase());
				else commands.addAll(ChatUtil.getEnumList(Option.values()));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("teleport")) {
				commands.addAll(ChatUtil.getPlayerList(sender));
			} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("edit")) {
				commands.addAll(ChatUtil.getEnumList(EditOption.values()));
				commands.addAll(ChatUtil.getEnumList(WorldOption.values()));
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
