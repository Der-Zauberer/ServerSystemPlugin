package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
			if (sender instanceof Player) toggleSpeed((Player) sender, sender);
			else ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
		} else if (args.length == 1) {
			if (args[0].chars().allMatch(character -> character >= 48 && character <= 57)) {
				final int speed = Integer.parseInt(args[0]);
				if (speed < 0 || speed > 9) ChatUtil.sendErrorMessage(sender, "Speed amounnt must be between 1 and 9!");
				else if (!(sender instanceof Player)) ChatUtil.sendErrorMessage(sender, ChatUtil.NOT_ENOUGHT_ARGUMENTS);
				else setSpeed((Player) sender, sender, speed);
			} else {
				if (!sender.hasPermission("serversystem.command.speed.other")) ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
				else if (Bukkit.getPlayer(args[0]) != null) toggleSpeed(Bukkit.getPlayer(args[0]), sender);
				else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[0]);
			}
		} else if (args.length == 2) {
			if (!sender.hasPermission("serversystem.command.speed.other")) {
				ChatUtil.sendErrorMessage(sender, ChatUtil.NO_PERMISSION);
			} else if (!(args[0].chars().allMatch(character -> character >= 48 && character <= 57))) {
				ChatUtil.sendErrorMessage(sender, "Speed amount " + args[1] + " must be a number!");
			} else  {
				final int speed = Integer.parseInt(args[0]);
				final Player player = Bukkit.getPlayer(args[1]);
				if (speed < 0 || speed > 9) ChatUtil.sendErrorMessage(sender, "Speed amounnt must be between 1 and 9!");
				else if (player == null) ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[1]);
				else setSpeed(player, sender, speed);
			}
		} else {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (ChatUtil.getCommandLayer(1, args) && sender.hasPermission("serversystem.command.speed.other")) {
			final List<String> list = IntStream.rangeClosed(1, 10).mapToObj(Integer::toString).collect(Collectors.toList());
			ChatUtil.getPlayerList(sender).forEach(list::add);
			return ChatUtil.removeWrong(list, args);
		} else if (ChatUtil.getCommandLayer(1, args)) {
			return IntStream.rangeClosed(1, 10).mapToObj(Integer::toString).collect(Collectors.toList());
		} else if (ChatUtil.getCommandLayer(2, args) && sender.hasPermission("serversystem.command.speed.other")) {
			return ChatUtil.removeWrong(ChatUtil.getPlayerList(sender), args);
		}
		return new ArrayList<>();
	}
	
	private static void toggleSpeed(Player player, CommandSender sender) {
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
	
	private static void setSpeed(Player player, CommandSender sender, int speed) {
		player.setFlySpeed((float) 0.1 * speed);
		ChatUtil.sendMessage(player, "You have speed " + speed + " now!");
		if (sender != player) ChatUtil.sendMessage(sender, player.getName() + " has speed " + speed + " now!");
	}

}