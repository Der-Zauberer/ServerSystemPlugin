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
import serversystem.handler.ChatHandler;
import serversystem.handler.ChatHandler.ErrorMessage;
import serversystem.handler.PermissionHandler;
import serversystem.handler.TeamHandler;

public class PermissionCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player) || sender.hasPermission("serversystem.command.permission")) {
			if(args.length == 2) {
				if(Bukkit.getPlayer(args[0]) != null || Config.getPlayerGroup(args[0]) != null) {
					if(Config.getSection("Groups", false) != null && Config.getSection("Groups", false).contains(args[1])) {
						if(Bukkit.getPlayer(args[0]) != null) {
							Player player = Bukkit.getPlayer(args[0]);
							PermissionHandler.removeConfigPermissions(player);
							Config.setPlayerGroup(player, args[1]);
							PermissionHandler.addConfigPermissions(player);
							PermissionHandler.reloadPlayerPermissions(player);
							TeamHandler.addRoleToPlayer(player);
							ChatHandler.sendServerMessage(sender, "Moved the player " + args[0] + " in group " + args[1] + "!");
						} else {
							Config.setPlayerGroup(args[0], args[1]);
							ChatHandler.sendServerMessage(sender, "Moved the player " + args[0] + " in group " + args[1] + "!");
						}
					}
				} else {
					ChatHandler.sendServerErrorMessage(sender, ErrorMessage.PLAYERNOTONLINE);
				}
			} else {
				ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOTENOUGHARGUMENTS);
			}
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.NOPERMISSION);
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		commands.clear();
		if(args.length == 1) {
			commands = Config.getPlayers();
		} else if(args.length == 2) {
			if(Config.getSection("Groups", false) != null) {
				commands = Config.getSection("Groups", false);
			}
		}
		return commands;
	}
	
}