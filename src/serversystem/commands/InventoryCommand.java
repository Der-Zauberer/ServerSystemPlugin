package serversystem.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import serversystem.handler.ChatHandler;
import serversystem.handler.PlayerVanish;
import serversystem.handler.ChatHandler.ErrorMessage;

public class InventoryCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 0) {
				ChatHandler.sendServerErrorMessage(sender, "Not enought arguments!");
			} else if (Bukkit.getPlayer(args[0]) != null) {
				((Player) sender).openInventory(Bukkit.getPlayer(args[0]).getInventory());
			} else {
				ChatHandler.sendServerErrorMessage(sender, ErrorMessage.PLAYERNOTONLINE);
			}
		} else {
			ChatHandler.sendServerErrorMessage(sender, ErrorMessage.ONLYPLAYER);
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> commands = new ArrayList<>();
		commands.clear();
		if(args.length == 1) {
			commands.clear();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!PlayerVanish.isPlayerVanished(player)) {
					commands.add(player.getName());
				}
			}
		}
		return commands;
	}
	
}
