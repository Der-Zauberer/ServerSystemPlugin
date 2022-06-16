package serversystem.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import serversystem.config.Config;
import serversystem.config.Config.ConfigOption;
import serversystem.utilities.ChatUtil;

public class LobbyCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) ChatUtil.sendErrorMessage(sender, ChatUtil.ONLY_PLAYER);
		else if (args.length != 0) ChatUtil.sendErrorMessage(sender, ChatUtil.TO_MANY_ARGUMENTS);
		else if (!Config.getConfigOption(ConfigOption.LOBBY) || Config.getLobbyWorld() == null) ChatUtil.sendErrorMessage(sender, "There is no lobby on this server!");
		else ((Player) sender).teleport(Config.getLobbyWorld().getSpawnLocation());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		return new ArrayList<String>();
	}

}
