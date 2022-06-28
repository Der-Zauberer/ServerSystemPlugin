package serversystem.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.main.ServerSystem;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.ServerGroup;
import serversystem.utilities.TeamUtil;

public class GroupCommand implements CommandExecutor, TabCompleter {
	
	private enum Option {ADD, REMOVE, PLAYER, EDIT}
	private enum EditOption {PRIORITY, COLOR, PREFIX, PARENT, PERMISSION}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && !((Player) sender).hasPermission("serversystem.command.group")) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		} else if (args.length < 2) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else {
			final ServerGroup group = ServerSystem.getGroups().get(args[0]);
			final Option option = ChatUtil.getValue(args[1], Option.values());
			if (group == null && option != Option.ADD) {
				ChatUtil.sendNotExistErrorMessage(sender, "group", args[0]);
			} else if (option == null) {
				ChatUtil.sendNotExistErrorMessage(sender, "option", args[1]);
			} else if (option == Option.ADD) {
				if (args.length > 2) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (group != null) {
					ChatUtil.sendMessage(sender, "The group " + group.getName() + " does already exist!");
				} else {
					ServerGroup newGroup = new ServerGroup(args[0]);
					ServerSystem.getGroups().add(newGroup);
					ChatUtil.sendMessage(sender, "The group " + newGroup.getName() + " has been added!");		
				}
			} else if (option == Option.REMOVE) {
				if (args.length < 3) {
					ServerSystem.getGroups().remove(group);
					ServerGroup.reloadAll();
					ChatUtil.sendMessage(sender, "The group " + args[0] + " has been removed successfully!");
				} else {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				}
			} else if (option == Option.PLAYER) {
				final String playerName = args[2];
				if (args.length < 3) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
				} else if (args.length > 3) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (!Config.getPlayers().contains(args[2])) {
					ChatUtil.sendNotExistErrorMessage(sender, playerName, args[2]);
				} else {
					Config.setPlayerGroup(playerName, group.getName());
					Player player = Bukkit.getPlayer(playerName);
					if (player != null) {
						PermissionUtil.resetPlayerPermissions(player);
						TeamUtil.addGroupToPlayer(player);
					}
					ChatUtil.sendMessage(sender, "Moved the player " + playerName + " in group " + group.getName() + "!");
				}
			} else if (option == Option.EDIT) {
				if (args.length < 3) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
				} else if (args.length > 4 && !args[2].equals("permission")) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else {
					EditOption editOption =  ChatUtil.getValue(args[2], EditOption.values());
					if (editOption == null) {
						ChatUtil.sendNotExistErrorMessage(sender, "option", args[2]);
					} else if (editOption == EditOption.PRIORITY) {
						ChatUtil.<Integer>processInput(sender, args, 3, "group", group.getName(), editOption.name().toLowerCase(), false, input -> {
							Integer integer = null;
							try {
								integer = Integer.parseInt(input);
							} catch (NumberFormatException exception) {}
							return integer;
						}, input -> {
							if (0 > input || input > 99) {
								ChatUtil.sendErrorMessage(sender, "The number " + input + " is out of range 1-99!");
								return false;
							}
							return true;
						}, input -> group.update(), group::setPriority, group::getPriority);
					} else if (editOption == EditOption.COLOR) {
						ChatUtil.<ChatColor>processInput(sender, args, 3, "group", group.getName(), editOption.name().toLowerCase(), false, input -> ChatUtil.getValue(input, ChatColor.values()), input -> true, input -> group.update(), group::setColor, () -> group.getColor().name().toLowerCase());
					} else if (editOption == EditOption.PREFIX) {
						ChatUtil.<String>processInput(sender, args, 3, "group", group.getName(), editOption.name().toLowerCase(), true, input -> input, input -> true, input -> group.update(), group::setPrefix, group::getPrefix);
					} else if (editOption == EditOption.PARENT) {
						ChatUtil.<ServerGroup>processInput(sender, args, 3, "group", group.getName(), editOption.name().toLowerCase(), true, ServerSystem.getGroups()::get, input -> {
							if (!input.getName().equals(group.getName())) {
								return true;
							} else {
								ChatUtil.sendErrorMessage(sender, "The parent can not be the group itself!");
								return false;
							}
						}, input -> group.update(), group::setParent, () -> group.getParent().getName());
					} else if (editOption == EditOption.PERMISSION) {
						ChatUtil.processListInput(sender, args, 3, editOption.name().toLowerCase(), group.getPermissions(), group::update);
					}
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		final List<String> commands = new ArrayList<>();
		if (sender.hasPermission("serversystem.command.group")) {
			if (ChatUtil.getCommandLayer(1, args)) {
				commands.addAll(ChatUtil.getList(ServerSystem.getGroups()));
			} else if (args.length == 2 && ServerSystem.getGroups().get(args[0]) == null) {
				commands.add(Option.ADD.name().toLowerCase());
			} else if (args.length > 1 && ServerSystem.getGroups().get(args[0]) != null) {
				ServerGroup group = ServerSystem.getGroups().get(args[0]);
				if (ChatUtil.getCommandLayer(2, args)) {
					commands.addAll(ChatUtil.getEnumList(Option.values()));
				} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("teleport")) {
					commands.addAll(ChatUtil.getPlayerList(sender));
				} else if (ChatUtil.getCommandLayer(3, args) && args[1].equals("edit")) {
					commands.addAll(ChatUtil.getEnumList(EditOption.values()));
				} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("priority")) {
					for (int i = 1; i < 100; i++) commands.add((i < 10 ? "0" : "") + Integer.toString(i));
				} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("color")) {
					commands.addAll(ChatUtil.getEnumList(ChatColor.values()));
				} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("prefix")) {
					commands.add("remove");
				} else if ((ChatUtil.getCommandLayer(4, args) && args[1].equals("edit")) && args[2].equals("parent")) {
					commands.addAll(ChatUtil.getList(ServerSystem.getGroups()));
					commands.remove(group.getName());
					commands.add("remove");
				} else if ((ChatUtil.getCommandLayer(4, args) || (ChatUtil.getCommandLayer(5, args)) && args[1].equals("edit")) && args[2].equals("permission")) {
					if (ChatUtil.getCommandLayer(4, args)) {
						commands.addAll(Arrays.asList("add", "remove", "list"));
					} else if (ChatUtil.getCommandLayer(5, args) && args[3].equals("add") && group != null) {
						commands.addAll(PermissionUtil.getBukkitPermissions());
						commands.addAll(group.getPermissions());
					} else if (ChatUtil.getCommandLayer(5, args) && args[3].equals("remove") && group != null) {
						commands.addAll(group.getPermissions());
					}
				}
			}
		}
		return ChatUtil.removeWrong(commands, args);
	}
	
}
