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
import serversystem.utilities.TeamUtil;

public class PermissionCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		CommandAssistant assistant = new CommandAssistant(sender);
		if (assistant.hasPermissionOrIsConsole("serversystem.command.permission")) {
			if (assistant.hasMinArguments(2, args)) {
				if (Bukkit.getPlayer(args[0]) != null || Config.getPlayerGroup(args[0]) != null) {
					if (Config.getSection("Groups", false) != null && Config.getSection("Groups", false).contains(args[1])) {
						if (Bukkit.getPlayer(args[0]) != null) {
							Player player = Bukkit.getPlayer(args[0]);
							Config.setPlayerGroup(player, args[1]);
							PermissionUtil.loadPlayerPermissions(player);
							TeamUtil.addRoleToPlayer(player);
							ChatUtil.sendServerMessage(sender, "Moved the player " + player.getName() + " in group " + args[1] + "!");
						} else {
							Config.setPlayerGroup(args[0], args[1]);
							ChatUtil.sendServerMessage(sender, "Moved the player " + args[0] + " in group " + args[1] + "!");
						}
					}
				} else {
					ChatUtil.sendServerErrorMessage(sender, "The player " + args[0] + " does not exist!");
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		if (args.length == 1 && sender.hasPermission("serversystem.command.permission")) {
			commands = Config.getPlayers();
		} else if (args.length == 2 && sender.hasPermission("serversystem.command.permission")) {
			if (Config.getSection("Groups", false) != null) {
				commands = Config.getSection("Groups", false);
			}
		}
		commands = new CommandAssistant(sender).cutArguments(args, commands);
		return commands;
	}

}