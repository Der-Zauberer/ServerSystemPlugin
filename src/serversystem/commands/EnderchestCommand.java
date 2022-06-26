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

public class EnderchestCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_PLAYER);
			return true;
		} else if (args.length > 1) {
			ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
			return true;
		}
		final Player player = (Player) sender;
		if (args.length == 0) player.openInventory(player.getEnderChest());
		else if (Bukkit.getPlayer(args[0]) != null) player.openInventory(Bukkit.getPlayer(args[0]).getEnderChest());
		else ChatUtil.sendPlayerNotOnlineErrorMessage(sender, args[0]);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (ChatUtil.getCommandLayer(1, args)) return ChatUtil.removeWrong(ChatUtil.getPlayerList(sender), args);
		return new ArrayList<>();
	}

}
