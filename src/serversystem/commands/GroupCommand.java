package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.CommandAssistant;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.ServerGroup;
import serversystem.utilities.TeamUtil;

public class GroupCommand implements CommandExecutor, TabCompleter, CommandAssistant {
	
	private enum Option {ADD, REMOVE, PLAYER, EDIT}
	private enum EditOption {TABLIST_PRIORITY, COLOR, PREFIX, PARENT, PERMISSION}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && !((Player) sender).hasPermission("serversystem.command.permission")) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		} else if (args.length < 2) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else {
			final ServerGroup group = ServerGroup.getGroup(args[0]);
			final Option option = ChatUtil.getEnumValue(Option.values(), args[1]);
			if (group == null && option != Option.ADD) {
				ChatUtil.sendNotExistErrorMessage(sender, "group", args[0]);
			} else if (option == null) {
				ChatUtil.sendNotExistErrorMessage(sender, "option", args[1]);
			} else if (option == Option.ADD) {
				if (args.length > 2) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else if (group != null) {
					ChatUtil.sendMessage(sender, "The warp " + group.getName() + " does already exist!");
				} else {
					ServerGroup newGroup = new ServerGroup(args[0]);
					ServerGroup.getGroups().add(newGroup);
					ChatUtil.sendMessage(sender, "The group " + newGroup.getName() + " has been added!");		
				}
			} else if (option == Option.REMOVE) {
				if (args.length < 3) {
					ServerGroup.getGroups().remove(group);
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
				} else if (args.length > 4) {
					ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
				} else {
					EditOption editOption =  ChatUtil.getEnumValue(EditOption.values(), args[2]);
					if (editOption == null) {
						ChatUtil.sendNotExistErrorMessage(sender, "option", args[2]);
					} else if (editOption == EditOption.TABLIST_PRIORITY) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The option " + editOption.toString().toLowerCase() + " for the group " + group.getName() + " is " + group.getPriority() + "!");
						} else {
							
						}
					} else if (editOption == EditOption.COLOR) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The option " + editOption.toString().toLowerCase() + " for the group " + group.getName() + " is " + group.getColor().toString().toLowerCase() + "!");
						} else {
							
						}
					} else if (editOption == EditOption.PREFIX) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The option " + editOption.toString().toLowerCase() + " for the group " + group.getName() + " is " + group.getPrefix() + "!");
						} else {
							
						}
					} else if (editOption == EditOption.PARENT) {
						if (args.length == 3) {
							ChatUtil.sendMessage(sender, "The option " + editOption.toString().toLowerCase() + " for the group " + group.getName() + " is " + group.getParent().getName() + "!");
						} else {
							
						}
					} else if (editOption == EditOption.PERMISSION) {
						
					}
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<>();
	}
	
}
