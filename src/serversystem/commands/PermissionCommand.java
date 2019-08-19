package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.main.Config;
import serversystem.utilities.PlayerPermission;
import serversystem.utilities.PlayerTeam;

public class PermissionCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args[0] != null && args[1] != null) {
			if(args[0].equals("groups")) {
				if(args[1].equals("add") && args[2] != null) {
					Config.addGroup(args[2]);
					sender.sendMessage(ChatColor.YELLOW + "[Server] " + args[2] + " group is added!");
				}
				if(args[1].equals("remove") && args[2] != null) {
					Config.removeGroup(args[2]);
					sender.sendMessage(ChatColor.YELLOW + "[Server] " + args[2] + " group is removed!");
				}
				if(args[1].equals("permission") && args[2] != null && args[3] != null) {
					Config.addGroupPermission(args[2], args[3]);
					sender.sendMessage(ChatColor.YELLOW + "[Server] Permission " + args[3] + " is added to group " + args[2] + "!");
				}
			}
			if(args[0].equals("players")) {
				if(args[1] != null && args[2] != null) {
					Player player = Bukkit.getPlayer(args[1]);
					PlayerPermission.removeConfigPermissions(player);
					Config.setPlayerGroup(player, args[2]);
					PlayerPermission.addConfigPermissions(player);
					PlayerTeam.addRankTeam(player);
					player.kickPlayer("You have " + args[2] + " permissions!");
					sender.sendMessage(ChatColor.YELLOW + "[Server] PLayer " + args[1] + " is in group " + args[2] + " now!");
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		ArrayList<String> permissions = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			commands.add("groups");
			commands.add("players");
			commands.add("owner");
			return commands;
		} else if(args.length == 2 && args[0].equals("groups")) {
			commands.clear();
			commands.add("add");
			commands.add("remove");
			commands.add("permission");
		} else if(args.length == 3 && args[0].equals("groups")) {
			commands.clear();
			commands = Config.getSection("Groups");
		} else if(args.length == 4 && args[0].equals("groups")) {
			commands.clear();
			for (String string : Config.getSection("Groups")) {
				permissions.addAll(Config.getGroupPermissions(string));
			}
			commands = permissions;
		} else if(args.length == 3 && args[0].equals("players")) {
			commands.clear();
			commands = Config.getSection("Groups");
		} else {
			commands.clear();
			for(Player player : Bukkit.getOnlinePlayers()) {
				commands.add(player.getName());
			}
		}
		return commands;
	}
	

}
