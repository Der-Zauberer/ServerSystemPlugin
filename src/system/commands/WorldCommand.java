package system.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		if(args.length == 0) {
			String worlds = "";
			for(World world : Bukkit.getWorlds()) {
				worlds = worlds + world.getName() + " ";
			}
			sender.sendMessage(ChatColor.YELLOW + "[Server] Worlds: " + worlds);
		}else if(Bukkit.getWorld(args[0]) != null) {
			player.teleport(new Location(Bukkit.getWorld(args[0]), Bukkit.getWorld(args[0]).getSpawnLocation().getX(), Bukkit.getWorld(args[0]).getSpawnLocation().getY(), Bukkit.getWorld(args[0]).getSpawnLocation().getZ()));
			sender.sendMessage(ChatColor.YELLOW + "[Server] You are in World " + args[0] +  "!");
		}else {
			sender.sendMessage(ChatColor.YELLOW + "[Server] This world does not exists!");
		}
		return true;
	}

}
