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
import org.bukkit.permissions.PermissionAttachmentInfo;
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
			}
			if(args[0].equals("players")) {
				if(args[1] != null && args[2] != null && Bukkit.getPlayer(args[1]) != null) {
					Player player = Bukkit.getPlayer(args[1]);
					PlayerPermission.removeConfigPermissions(player);
					Config.setPlayerGroup(player, args[2]);
					PlayerPermission.addConfigPermissions(player);
					PlayerTeam.addRankTeam(player);
					if(Bukkit.getPlayer(args[1]).isOp()) {
						Bukkit.getPlayer(args[1]).setOp(false);
						Bukkit.getPlayer(args[1]).setOp(true);
					} else {
						Bukkit.getPlayer(args[1]).setOp(true);
						Bukkit.getPlayer(args[1]).setOp(false);
					}
					sender.sendMessage(ChatColor.YELLOW + "[Server] PLayer " + args[1] + " is in group " + args[2] + " now!");
				}
			}
			if(args[0].equals("permission") && args[1] != null && args[2] != null && args[3] != null) {
				if(Bukkit.getPlayer(args[2]) != null) {
					if(args[1].equals("add")) {
						PlayerPermission.addPermission(Bukkit.getPlayer(args[2]), args[3]);
						if(Bukkit.getPlayer(args[2]).isOp()) {
							Bukkit.getPlayer(args[2]).setOp(false);
							Bukkit.getPlayer(args[2]).setOp(true);
						} else {
							Bukkit.getPlayer(args[2]).setOp(true);
							Bukkit.getPlayer(args[2]).setOp(false);
						}
						sender.sendMessage(ChatColor.YELLOW + "[Server] Add permission " + args[3] + " to player " + args[2] + "!");
					}
					if(args[1].equals("remove")) {
						PlayerPermission.removePermission(Bukkit.getPlayer(args[2]), args[3]);
						sender.sendMessage(ChatColor.YELLOW + "[Server] Remove permission " + args[3] + " from player " + args[2] + "!");
					}
				} else {
					if(args[1].equals("add")) {
						Config.getGroupPermissions(args[2]).add(args[3]);
						Config.saveConfig();
						sender.sendMessage(ChatColor.YELLOW + "[Server] Add permission " + args[3] + " to group " + args[2] + "!");
					}
					if(args[1].equals("remove")) {
						Config.getGroupPermissions(args[2]).remove(args[3]);
						Config.saveConfig();
						sender.sendMessage(ChatColor.YELLOW + "[Server] Remove permission " + args[3] + " from group " + args[2] + "!");
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			commands.add("groups");
			commands.add("permission");
			commands.add("players");
			return commands;
		} else if(args.length == 2 && args[0].equals("groups") || args.length == 2 && args[0].equals("permission")) {
			commands.clear();
			commands.add("add");
			commands.add("remove");
		} else if(args.length == 3 && args[0].equals("groups")) {
			commands.clear();
			commands = Config.getSection("Groups");
		} else if(args.length == 3 && args[0].equals("permission")) {
			commands.clear();
			commands = Config.getSection("Groups");
			for(Player player : Bukkit.getOnlinePlayers()) {
				commands.add(player.getName());
			}
		} else if (args.length == 4 && args[0].equals("permission") && args[1].equals("remove")) {
			commands.clear();
			if(Bukkit.getPlayer(args[2]) != null) {
				for (PermissionAttachmentInfo info : Bukkit.getPlayer(args[2]).getEffectivePermissions()) {
					commands.add(info.getPermission());
				}
			} else {
				commands.clear();
				for (String string : Config.getSection("Groups")) {
					commands.addAll(Config.getGroupPermissions(string));
				}
			}
		} else if (args.length == 4 && args[0].equals("permission") && args[1].equals("add")) {
			commands.clear();
//			if(Bukkit.getPlayer(args[2]) != null) {
//				for (PermissionAttachmentInfo info : Bukkit.getPlayer(args[2]).getEffectivePermissions()) {
//					commands.remove(info.getPermission());
//				}	
//			} else {
//				commands.removeAll(Config.getGroupPermissions(args[2]));
//			}
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
