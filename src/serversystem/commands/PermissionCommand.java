package serversystem.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.main.ServerSystem;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.TeamUtil;

public class PermissionCommand implements CommandExecutor, TabCompleter {
	
	private enum Option {GROUP, PERMISSIONS}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && !((Player) sender).hasPermission("serversystem.command.permission")) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		} else if (args.length < 2) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (!Config.getPlayers().contains(args[0])) {
			ChatUtil.sendNotExistErrorMessage(sender, "player", args[0]);
		} else {
			String playerName = args[0];
			Player player = Bukkit.getPlayer(args[0]);
			Option option = ChatUtil.getValue(args[1], Option.values());
			if (option == null) {
				ChatUtil.sendNotExistErrorMessage(sender, "option", args[1]);
			} else if (option == Option.GROUP) {
				ChatUtil.<String>processInput(sender, args, 2, "player", playerName, option.name().toLowerCase(), false, input -> {
					if (input.equals("player") || ServerSystem.getGroups().get(input) != null) return input;
					else return null;
				}, input -> true , input -> {}, group -> {
					Config.setPlayerGroup(playerName, group);
					if (player != null) {
						PermissionUtil.loadPlayerPermissions(player);
						TeamUtil.addGroupToPlayer(player);
					}
				}, () ->  {
					final String group = Config.getPlayerGroup(player);
					if (ServerSystem.getGroups().get(group) != null || group.equals("player")) return group;
					else return null;
				});
			} else if (option == Option.PERMISSIONS) {
				final List<String> permissions = Config.getPlayerSpecificPermissions(playerName);
				ChatUtil.processListInput(sender, args, 2, option.name().toLowerCase(), permissions, () -> {
					Config.setPlayerSpecificPermissions(playerName, permissions);
					if (player != null) PermissionUtil.loadPlayerPermissions(player);
				});
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		final List<String> commands = new ArrayList<>();
		if (sender.hasPermission("serversystem.command.permission")) {
			if (ChatUtil.getCommandLayer(1, args)) {
				commands.addAll(Config.getPlayers());
			} else if (ChatUtil.getCommandLayer(2, args)) {
				commands.addAll(ChatUtil.getEnumList(Option.values()));
			} else if (ChatUtil.getCommandLayer(3, args)) {
				if (args[1].equals("group")) {
					commands.addAll(ChatUtil.getList(ServerSystem.getGroups()));
					commands.add("player");
				} else if (args[1].equals("permissions")) {
					commands.addAll(Arrays.asList("add", "remove", "list"));
				}
			} else if (ChatUtil.getCommandLayer(4, args) && args[1].equals("permissions")) {
				final String player = args[0];
				if (args[2].equals("add")) {
					commands.addAll(PermissionUtil.getBukkitPermissions());
					commands.addAll(Config.getPlayerSpecificPermissions(player));
				} else if (args[2].equals("remove")) {
					commands.addAll(Config.getPlayerSpecificPermissions(player));
				}
			}
		}
		return ChatUtil.removeWrong(commands, args);
	}

}