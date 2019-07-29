package system.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor, TabCompleter{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length == 0) {
			String worlds = "";
			for(World world : Bukkit.getWorlds()) {
				worlds = worlds + world.getName() + " ";
			}
			sender.sendMessage(ChatColor.YELLOW + "[Server] Worlds: " + worlds);
		}
		if(args.length == 1) {
			player.teleport(new Location(Bukkit.getWorld(args[0]), Bukkit.getWorld(args[0]).getSpawnLocation().getX(), Bukkit.getWorld(args[0]).getSpawnLocation().getY(), Bukkit.getWorld(args[0]).getSpawnLocation().getZ()));
			sender.sendMessage(ChatColor.YELLOW + "[Server] You are in World " + args[0] +  "!");
		}
		if(args.length == 2) {
			Bukkit.getPlayer(args[1]).teleport(new Location(Bukkit.getWorld(args[0]), Bukkit.getWorld(args[0]).getSpawnLocation().getX(), Bukkit.getWorld(args[0]).getSpawnLocation().getY(), Bukkit.getWorld(args[0]).getSpawnLocation().getZ()));
			Bukkit.getPlayer(args[1]).sendMessage(ChatColor.YELLOW + "[Server] You are in World " + args[0] +  "!");
			sender.sendMessage(ChatColor.YELLOW + "[Server] Player " + Bukkit.getPlayer(args[1]).getName() +  " is in World " + args[0] +  "!");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		ArrayList<String> commands = new ArrayList<>();
		if(args.length == 1) {
			commands.clear();
			for(World world : Bukkit.getWorlds()) {
				commands.add(world.getName());
			}
			return commands;
		} else {
			commands.clear();
			for(Player player : Bukkit.getOnlinePlayers()) {
				commands.add(player.getName());
			}
		}
		return commands;
	}

}
