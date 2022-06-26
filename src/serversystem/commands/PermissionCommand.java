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
import serversystem.main.ServerSystem;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.TeamUtil;

public class PermissionCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player && !((Player) sender).hasPermission("serversystem.command.permission")) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
		} else if (args.length < 2) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length > 2) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		} else if (Bukkit.getPlayer(args[0]) == null && Config.getPlayerGroup(args[0]) == null) {
			ChatUtil.sendNotExistErrorMessage(sender, "player", args[0]);
		} else if (!ServerSystem.getGroups().contains(args[1])) {
			ChatUtil.sendNotExistErrorMessage(sender, "group", args[1]);
		} else {
			if (Bukkit.getPlayer(args[0]) != null) {
				final Player player = Bukkit.getPlayer(args[0]);
				Config.setPlayerGroup(player, args[1]);
				PermissionUtil.loadPlayerPermissions(player);
				TeamUtil.addGroupToPlayer(player);
				ChatUtil.sendMessage(sender, "Moved the player " + player.getName() + " in group " + args[1] + "!");
			} else {
				Config.setPlayerGroup(args[0], args[1]);
				ChatUtil.sendMessage(sender, "Moved the player " + args[0] + " in group " + args[1] + "!");
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
				commands.addAll(ChatUtil.getList(ServerSystem.getGroups()));
			}
		}
		return ChatUtil.removeWrong(commands, args);
	}

}