package serversystem.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import serversystem.events.PlayerTeleportListener;
import serversystem.utilities.ChatUtil;

public class BackCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Location location = PlayerTeleportListener.getLastLocations().get(player);
			if (location != null) player.teleport(location);
			else ChatUtil.sendErrorMessage(sender, "There is no location to teleport back!");
		} else {
			ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_PLAYER);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return new ArrayList<>();
	}
	
}
