package serversystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.handler.ChatHandler;
import serversystem.handler.PermissionHandler;
import serversystem.handler.TeamHandler;
import serversystem.utilities.CommandAssistant;

public class PermissionReloadComnmand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(new CommandAssistant(sender).hasPermissionOrIsConsole("serversystem.command.permission")) {
			Config.reloadConfig();
			for (Player player : Bukkit.getOnlinePlayers()) {
				PermissionHandler.loadPlayerPermissions(player);
				TeamHandler.addRoleToPlayer(player);
				System.out.println(Config.getPlayerGroup(player));
			}
			
			ChatHandler.sendServerMessage(sender, "Reloaded all permissions!");
		}
		return true;
	}
	
	

}
