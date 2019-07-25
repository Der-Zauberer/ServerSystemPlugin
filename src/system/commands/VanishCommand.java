package system.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import system.main.System;

public class VanishCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length == 0) {
			System.vanishPlayer((Player) sender);
		}else if(Bukkit.getServer().getPlayer(args[0]) != null) {
			System.vanishPlayer(Bukkit.getServer().getPlayer(args[0]), (Player) sender);
		}
		return true;
	}
	
	

}
