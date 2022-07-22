package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.utilities.ChatUtil;

public class FlyCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) toggleFlight((Player) sender);
			else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (!sender.hasPermission("serversystem.command.fly.other")) ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			else if (Bukkit.getPlayer(args[0]) != null) toggleFlight(Bukkit.getPlayer(args[0]), sender);
			else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[0]);
		} else {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (ChatUtil.getCommandLayer(1, args) && sender.hasPermission("serversystem.command.fly.other")) {
			return ChatUtil.removeWrong(ChatUtil.getPlayerList(sender), args);
		}
		return new ArrayList<>();
	}
	
	private static void toggleFlight(Player player) {
		toggleFlight(player, player);
	}
	
	private static void toggleFlight(Player player, CommandSender sender) {
		if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
			if (sender != player)  ChatUtil.sendErrorMessage(player, player.getName() + " is in a gamemode, that allows to fly by default!");
			else ChatUtil.sendErrorMessage(player, "You are in a gamemode, that allows to fly by default!");
		} else if (player.getAllowFlight()) {
			player.setAllowFlight(false);
			ChatUtil.sendMessage(player, "You are not allowed to fly anymore!");
			if (sender != player) ChatUtil.sendMessage(sender, player.getName() + " is not allowed to fly anymore!");
		} else {
			player.setAllowFlight(true);
			ChatUtil.sendMessage(player, "You are allowed to fly now!");
			if (sender != player) ChatUtil.sendMessage(sender, player.getName() + " is allowed to fly now!");
		}
	}

}
