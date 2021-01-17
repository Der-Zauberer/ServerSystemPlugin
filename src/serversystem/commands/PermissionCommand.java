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
import serversystem.handler.PlayerPermission;
import serversystem.handler.PlayerVanish;
import serversystem.handler.TeamHandler;

public class PermissionCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		System.out.println(args.length);
		if(args.length == 3) {
			if(args[0].equals("group")) {
				if(args[1].equals("add") && args[2] != null) {
					if(!Config.getSection("Groups").contains(args[2])) {
						Config.addGroup(args[2]);
						ChatHandler.sendServerMessage(sender, "Added the group " + args[2] + "!");
					} else {
						ChatHandler.sendServerErrorMessage(sender, "The group " + args[2] + " is already added!");
					}
				}else if(args[1].equals("remove") && args[2] != null) {
					if (Config.getSection("Groups").contains(args[2])) {
						Config.removeGroup(args[2]);
						ChatHandler.sendServerMessage(sender, "Removed the group " + args[2] + "!");
					} else {
						ChatHandler.sendServerErrorMessage(sender, "The group " + args[2] + " does not exist!");
					}
					
				} else {
					ChatHandler.sendServerErrorMessage(sender, "The option " + args[1] + " does not exist!");
				}
			} else if(args[0].equals("player")) {
				if(args[1] != null && args[2] != null) {
					if(Bukkit.getPlayer(args[1]) != null && Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[1]))) {
						if(Config.getSection("Groups").contains(args[2])) {
							Player player = Bukkit.getPlayer(args[1]);
							PlayerPermission.removeConfigPermissions(player);
							Config.setPlayerGroup(player, args[2]);
							PlayerPermission.addConfigPermissions(player);
							PlayerPermission.reloadPlayerPermissions(player);
							TeamHandler.addRoleToPlayer(player);
							ChatHandler.sendServerMessage(sender, "Moved the player " + args[1] + " in group " + args[2] + "!");
						} else {
							ChatHandler.sendServerErrorMessage(sender, "The group " + args[2] + " does not exist!");
						}
					} else {
						ChatHandler.sendServerErrorMessage(sender, "The player " + args[0] + " is not online!");
					}
					
				} else {
					ChatHandler.sendServerErrorMessage(sender, "Not enought arguments!");
				}
			} else {
				ChatHandler.sendServerErrorMessage(sender, "The option " + args[0] + " does not exist!");
			}
		} else { 
			ChatHandler.sendServerErrorMessage(sender, "Not enought arguments!");
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			commands.add("group");
			commands.add("player");
			return commands;
		} else if(args.length == 2 && args[0].equals("group")) {
			commands.clear();
			commands.add("add");
			commands.add("remove");
		} else if(args.length == 3 && args[0].equals("group") && args[1].equals("remove")) {
			commands.clear();
			commands = Config.getSection("Groups");
		} else if(args.length == 3 && args[0].equals("player")) {
			commands.clear();
			commands = Config.getSection("Groups");
		} else if(args.length == 2 && args[0].equals("player")) {
			commands.clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!PlayerVanish.isPlayerVanished(player)) {
					commands.add(player.getName());
				}
			}
		}
		return commands;
	}
	
}