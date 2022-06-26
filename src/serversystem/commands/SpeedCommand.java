package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.utilities.ChatUtil;

public class SpeedCommand implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) toggleFlight((Player) sender);
			else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (Bukkit.getPlayer(args[0]) != null) toggleFlight(Bukkit.getPlayer(args[0]), sender);
			else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[0]);
		} else {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (ChatUtil.getCommandLayer(1, args)) return ChatUtil.removeWrong(ChatUtil.getPlayerList(sender), args);
		return new ArrayList<>();
	}
	
	private static void toggleFlight(Player player) {
		toggleFlight(player, player);
	}
	
	private static void toggleFlight(Player player, CommandSender sender) {
		if (player.getFlySpeed() > 0.2) {
			player.setFlySpeed((float) 0.1);
			ChatUtil.sendMessage(player, "You have no longer speed!");
			if (sender != player) ChatUtil.sendMessage(sender, player.getName() + " has no longer speed!");
		} else {
			player.setFlySpeed((float) 0.4);
			ChatUtil.sendMessage(player, "You have speed now!");
			if (sender != player) ChatUtil.sendMessage(sender, player.getName() + " has speed now!");
		}
	}

}