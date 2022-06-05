package serversystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.utilities.ChatUtil;
import serversystem.utilities.CommandAssistant;
import serversystem.utilities.PermissionUtil;
import serversystem.utilities.TeamUtil;

public class PermissionReloadComnmand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (new CommandAssistant(sender).hasPermissionOrIsConsole("serversystem.command.permission")) {
			Config.reloadConfig();
			for (Player player : Bukkit.getOnlinePlayers()) {
				PermissionUtil.loadPlayerPermissions(player);
				TeamUtil.addRoleToPlayer(player);
				System.out.println(Config.getPlayerGroup(player));
			}

			ChatUtil.sendServerMessage(sender, "Reloaded all permissions!");
		}
		return true;
	}

}
